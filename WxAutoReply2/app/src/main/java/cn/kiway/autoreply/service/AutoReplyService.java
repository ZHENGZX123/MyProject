package cn.kiway.autoreply.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.kiway.autoreply.KWApplication;
import cn.kiway.autoreply.activity.MainActivity;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.util.Constant;
import cn.kiway.autoreply.util.Utils;
import cn.kiway.autoreply.util.WxUtils;
import cn.kiway.wx.reply.utils.ZbusUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;
import io.zbus.mq.Producer;

import static cn.kiway.autoreply.util.Constant.APPID;
import static cn.kiway.autoreply.util.Constant.clientUrl;
import static cn.kiway.autoreply.util.WxUtils.ImageMessage;
import static cn.kiway.autoreply.util.WxUtils.TextMessage;

public class AutoReplyService extends Service {

    public static AutoReplyService instance;
    KWApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("maptrix", "service oncreate");
        app = (KWApplication) getApplicationContext();
        instance = this;
        installationPush(this, Utils.getIMEI(this), Utils.getIMEI(this));
        getInstance();
    }

    public AutoReplyService getInstance() {
        return instance;
    }

    /**
     * 回到系统桌面
     */
    private void back2Home() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.autoreply");
        startActivity(intent);
    }

    //初始化zbus
    public void initZbus() {
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String userId = Utils.getIMEI(getApplicationContext());
                    if (TextUtils.isEmpty(userId)) {
                        return;
                    }
                    Broker broker = new Broker(Constant.zbusHost + ":" + Constant.zbusPost);
                    Producer p = new Producer(broker);
                    ZbusUtils.init(broker, p);
                    String topic = "kiway_wx_reply_push_" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsg(topic, new MessageHandler() {

                        @Override
                        public void handle(Message message, MqClient mqClient) {
                            new Thread() {
                                @Override
                                public void run() {
                                    String temp = message.getBodyString();
                                    Log.d("test", "zbus receive = " + temp);
                                    try {

                                        JSONObject o = new JSONObject(temp);
                                        long id = o.getLong("id");
                                        String returnMessage = o.getString("returnMessage");
                                        int returnType = o.getInt("returnType");

                                        Log.d("test", "开始处理action = " + id);
                                        String reply = "xxx";//returnMessage;
                                        sendWxMsg(WxUtils.getWxId(o.optString("sender")), returnMessage, TextMessage);
                                        int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0) + 1;
                                        getSharedPreferences("kiway", 0).edit().putInt("recvCount", recvCount).commit();
                                        if (MainActivity.instance != null) {
                                            MainActivity.instance.updateServiceCount();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }.start();
                        }
                    }, Constant.zbusHost + ":" + Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public synchronized void sendMsgToServer(long id, Action action) {
        String name = getSharedPreferences("kiway", 0).getString("name", "");
        String userId = Utils.getIMEI(this);
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer");
                try {
                    String msg = new JSONObject()
                            .put("id", id)
                            .put("sender", action.sender)
                            .put("content", action.content)
                            .put("me", name)
                            .put("imei",userId)
                            .put("talker", action.talker).toString();

                    //topic : 老师的deviceId#userId
                    String userId = Utils.getIMEI(getApplicationContext());

                    String topic = Utils.getIMEI(getApplicationContext()) + "#" + userId;
                    String url = Constant.zbusHost + ":" + Constant.zbusPost;
                    PushMessageVo pushMessageVo = new PushMessageVo();
                    pushMessageVo.setDescription("desc");
                    pushMessageVo.setTitle("title");
                    pushMessageVo.setMessage(msg);
                    pushMessageVo.setAppId(APPID);
                    pushMessageVo.setModule("wx_reply");
                    Set<String> userIds = new HashSet<>();
                    userIds.add(Utils.getIMEI(getApplicationContext()));

                    pushMessageVo.setUserId(userIds);//学生token
                    pushMessageVo.setSenderId(userId);//老师的userId
                    pushMessageVo.setPushType("zbus");

                    Log.d("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
                    ZbusUtils.sendMsg(topic, pushMessageVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("maptrix", "service destroy");
        //uninstallPush(this);
        //ZbusUtils.close();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void installationPush(final Context c, final String userId, final String imei) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            Log.d("test", "userId = " + userId);
            RequestParams param = new RequestParams();
            param.put("appId", APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", imei);//userId
            param.put("module", "student");
            param.put("robotId", robotId);
            Log.d("test", "installationPush param = " + param.toString());
            //StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = clientUrl + "/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    initZbus();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public void uninstallPush(final Context c) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            if (TextUtils.isEmpty(xtoken)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("type", "huawei");
            param.put("imei", Utils.getIMEI(c));
            param.put("token", userId);
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "/device/uninstall";
            Log.d("test", "uninstallPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "uninstallPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "uninstallPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    /**
     * @param wxId    发送人wxId
     * @param content 发送人内容  发送图片content是个地址
     * @param type    发送的消息类型
     */
    public void sendWxMsg(String wxId, String content, int type) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("taskid", System.currentTimeMillis());
            jsonObject.put("content", new JSONObject());
            jsonObject.getJSONObject("content").put("talker", wxId);
            jsonObject.getJSONObject("content").put("timeout", -1);
            jsonObject.put("type", type);//1文字 2图片 3语音 4 视频
            if (type == TextMessage) {//
                jsonObject.getJSONObject("content").put("text", app.wToolSDK.encodeValue
                        (content));
            } else if (type == ImageMessage) {
                jsonObject.getJSONObject("content").put("imagefile", content);
            }
            String result = app.wToolSDK.sendTask(jsonObject.toString());
            Log.e("cccc", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ForwardMx(int msgtype, String msgid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("taskid", System.currentTimeMillis());
            jsonObject.put("content", new JSONObject());
            jsonObject.getJSONObject("content").put("talker", WxUtils.wxRoomId1);
            jsonObject.getJSONObject("content").put("timeout", -1);
            jsonObject.getJSONObject("content").put("msgtype", msgtype);
            jsonObject.getJSONObject("content").put("msgid", msgid);
            jsonObject.put("type", 12);//1文字 2图片 3语音 4 视频
            String result = app.wToolSDK.sendTask(jsonObject.toString());
            Log.e("----", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
