package cn.kiway.robot.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.ReturnMessage;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.FileUtils;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.UploadUtil;
import cn.kiway.robot.util.Utils;
import cn.kiway.wx.reply.utils.ZbusUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

import static cn.kiway.robot.entity.Action.TYPE_COLLECTOR_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_FRIEND_CIRCLER;
import static cn.kiway.robot.entity.Action.TYPE_IMAGE;
import static cn.kiway.robot.entity.Action.TYPE_PUBLIC_ACCONT_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_PUBLIC_ACCOUNT_SET_FORWARDTO;
import static cn.kiway.robot.entity.Action.TYPE_REDPACKAGE;
import static cn.kiway.robot.entity.Action.TYPE_REQUEST_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_SET_FRIEND_CIRCLER_REMARK;
import static cn.kiway.robot.entity.Action.TYPE_TEXT;
import static cn.kiway.robot.entity.Action.TYPE_TRANSFER_MONEY;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.clientUrl;
import static java.lang.System.currentTimeMillis;

public class AutoReplyService extends AccessibilityService {

    public static int MSG_CLEAR_ACTION = 1;
    public static int MSG_ALARM = 2;


    public static AutoReplyService instance;
    private final Object o = new Object();
    private final Object o2 = new Object();

    //事件map
    public HashMap<Long, Action> actions = new HashMap<>();
    //当前执行的事件id
    private long currentActionID = -1;
    private boolean actioningFlag;

    private String senderFromNotification;
    private String forwardto;//当前要转发的对象

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");
        instance = this;
        installationPush(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_CLEAR_ACTION) {
                release();
                return;
            }
            if (msg.what == MSG_ALARM) {
                //这里不要了。。。写了半天，发现实现不了
                mHandler.removeMessages(MSG_ALARM);
                mHandler.sendEmptyMessageDelayed(MSG_ALARM, 60 * 1000);

                int hour = getSharedPreferences("sendHour", 0).getInt("sendHour", 0);
                int minute = getSharedPreferences("sendMinute", 0).getInt("sendMinute", 0);

                Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH) + 1;
                int date = c.get(Calendar.DATE);
                String today = "" + year + month + date;

                int currentHour = c.get(Calendar.HOUR_OF_DAY);
                int currentMinute = c.get(Calendar.MINUTE);
                //Log.d("test", year + " " + month + " " + date + " " + currentHour + " " + currentMinute);
                boolean todaySended = getSharedPreferences("kiway", 0).getBoolean(today, false);
                if (todaySended) {
                    return;
                }
                if (actioningFlag) {
                    return;
                }
                if (currentHour == hour && Math.abs(currentMinute - minute) < 10) {
                    actioningFlag = true;
                    FileUtils.saveFile("" + actioningFlag, "actioningFlag.txt");
                    release();
                }
                return;
            }
        }
    };

    private void launchWechat(long id) {
        mHandler.sendEmptyMessageDelayed(MSG_CLEAR_ACTION, 45000);
        currentActionID = id;
        try {
            actions.get(currentActionID).intent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void backToRobot() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
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
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    String topic = "kiway_wx_reply_push_" + robotId + "#" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsg(topic, new MessageHandler() {

                        @Override
                        public void handle(Message message, MqClient mqClient) {
                            String msg = message.getBodyString();
                            MainActivity.msg = msg;
                            doHandleZbusMsg(msg);
                        }
                    }, Constant.zbusHost + ":" + Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void doHandleZbusMsg(String msg) {
        Log.d("test", "doHandleZbusMsg msg = " + msg);
        new Thread() {
            @Override
            public void run() {
                if (currentActionID != -1) {
                    Log.d("test", "当前有事件在处理，锁定");
                    synchronized (o) {
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    JSONObject o = new JSONObject(msg);
                    long id = o.optLong("id");
                    if (id == 0) {
                        Log.d("test", "没有id！！！");
                        return;
                    }
                    JSONArray returnMessage = o.getJSONArray("returnMessage");
                    Action action = actions.get(id);
                    if (action == null) {
                        Log.d("test", "action null , error!!!");
                        return;
                    }
                    Log.d("test", "开始处理action = " + id);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int size = returnMessage.length();
                            if (size == 0) {
                                return;
                            }
                            action.returnMessages.clear();
                            int textCount = 0;
                            int imageCount = 0;
                            for (int i = 0; i < size; i++) {
                                try {
                                    ReturnMessage rm = new ReturnMessage();
                                    rm.returnType = returnMessage.getJSONObject(i).getInt("returnType");
                                    if (rm.returnType == TYPE_TEXT) {
                                        textCount++;
                                    } else if (rm.returnType == TYPE_IMAGE) {
                                        imageCount++;
                                    }
                                    rm.content = returnMessage.getJSONObject(i).getString("content");
                                    action.returnMessages.add(rm);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d("test", "imageCount = " + imageCount);
                            if (imageCount == 0) {
                                //没有图片的话，可以直接去回复文字
                                launchWechat(id);
                            } else if (imageCount > 0) {
                                Log.d("test", "处理前count = " + action.returnMessages.size());
                                //这里加工一下，只保留1个图片就可以了
                                boolean removed = false;
                                Iterator<ReturnMessage> it = action.returnMessages.iterator();
                                while (it.hasNext()) {
                                    ReturnMessage rm = it.next();
                                    if (rm.returnType == TYPE_IMAGE) {
                                        if (removed) {
                                            it.remove();
                                        }
                                        removed = true;
                                    }
                                }
                                Log.d("test", "处理后count = " + action.returnMessages.size());
                                handleImageMsg(id, action.returnMessages);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void handleImageMsg(long id, ArrayList<ReturnMessage> returnMessages) {
        new Thread() {
            @Override
            public void run() {
                for (ReturnMessage rm : returnMessages) {
                    if (rm.returnType == TYPE_IMAGE) {
                        //1.下载图片
                        Bitmap bmp = ImageLoader.getInstance().loadImageSync(rm.content);
                        //2.保存图片
                        saveImage(getApplication(), bmp);
                    }
                }
                launchWechat(id);
            }
        }.start();
    }

    public synchronized void sendMsgToServer(long id, Action action) {
        String name = getSharedPreferences("kiway", 0).getString("name", "");
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer");
                try {
                    String userId = Utils.getIMEI(getApplicationContext());
                    String installationId = getSharedPreferences("kiway", 0).getString("installationId", "");
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");

                    String msg = new JSONObject()
                            .put("id", id)
                            .put("sender", action.sender)
                            .put("content", action.content)
                            .put("me", name)
                            .toString();

                    //topic : robotId#userId
                    String topic = robotId + "#" + userId;
                    String url = Constant.zbusHost + ":" + Constant.zbusPost;
                    PushMessageVo pushMessageVo = new PushMessageVo();
                    pushMessageVo.setDescription("desc");
                    pushMessageVo.setTitle("title");
                    pushMessageVo.setMessage(msg);
                    pushMessageVo.setAppId(APPID);
                    pushMessageVo.setModule("wx_reply");
                    Set<String> userIds = new HashSet<>();
                    userIds.add(topic);

                    pushMessageVo.setUserId(userIds);
                    pushMessageVo.setSenderId(userId);
                    pushMessageVo.setPushType("zbus");
                    pushMessageVo.setInstallationId(installationId);

                    Log.d("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
                    ZbusUtils.sendMsg(topic, pushMessageVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void release() {
        mHandler.removeMessages(MSG_CLEAR_ACTION);
        backToRobot();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    currentActionID = -1;
                    actioningFlag = false;
                    FileUtils.saveFile("" + actioningFlag, "actioningFlag.txt");
                    o.notify();
                }
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("maptrix", "service destroy");
        //uninstallPush(this);
        //ZbusUtils.close();
    }

    public void installationPush(final Context c) {
        try {
            String userId = Utils.getIMEI(c);
            String imei = Utils.getIMEI(c);

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

            String url = clientUrl + "/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    try {
                        String installationId = new JSONObject(ret).getJSONObject("data").getString("installationId");
                        getSharedPreferences("kiway", 0).edit().putString("installationId", installationId).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d("maptrix", "get event = " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.d("maptrix", "接收到通知");
                if (!getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    Log.d("test", "user do not login");
                    return;
                }
                String fromPKG = event.getPackageName().toString();
                Log.d("test", "fromPKG = " + fromPKG);
                if (fromPKG.equals("com.tencent.mobileqq")) {
                    return;
                }
                List<CharSequence> texts = event.getText();
                if (texts.isEmpty()) {
                    return;
                }
                for (CharSequence text : texts) {
                    Log.d("test", "text = " + text);
                    String txt = text.toString();
                    if (TextUtils.isEmpty(txt)) {
                        continue;
                    }
                    if (event.getParcelableData() == null) {
                        continue;
                    }
                    if (!(event.getParcelableData() instanceof Notification)) {
                        continue;
                    }
                    Notification notification = (Notification) event.getParcelableData();
                    String ticker = notification.tickerText.toString();
                    String sender = "";
                    String content = "";
                    if (ticker.contains(":")) {
                        String[] cc = ticker.split(":");
                        sender = cc[0].trim();
                        content = cc[1].trim();
                        AutoReplyService.this.senderFromNotification = sender;
                        Log.d("test", "sender name = " + sender);
                        Log.d("test", "sender content = " + content);

                        if (Utils.isInfilters(getApplicationContext(), sender)) {
                            Log.d("test", "该昵称被过滤");
                            continue;
                        }
                        if (!Utils.isGetPic(getApplicationContext(), content)) {
                            Log.d("test", "图片接收被过滤");
                            continue;
                        }
                    } else if (ticker.endsWith("请求添加你为朋友")) {
                        sender = "系统";
                        content = ticker;
                    } else {
                        continue;
                    }

                    //1.预先加入map
                    long id = currentTimeMillis();
                    PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                    Action action = new Action();
                    action.sender = sender;
                    action.content = content;
                    action.intent = intent;
                    if (content.startsWith("[微信红包]")) {
                        action.receiveType = TYPE_REDPACKAGE;
                    }
                    //转账
                    else if (content.startsWith("[转账]")) {
                        action.receiveType = TYPE_TRANSFER_MONEY;
                    }
                    //自动加好友
                    else if (content.endsWith("请求添加你为朋友")) {
                        action.receiveType = TYPE_REQUEST_FRIEND;
                    }
                    //需要转发到朋友圈，目前只支持链接
                    else if (sender.equals(Utils.getFCFrom(this)) && content.startsWith("[链接]")) {
                        action.receiveType = TYPE_FRIEND_CIRCLER;
                    } else if (sender.equals(Utils.getFCFrom(this)) && content.startsWith("设置朋友圈备注：")) {
                        action.receiveType = TYPE_SET_FRIEND_CIRCLER_REMARK;
                    }
                    //来自公众号的消息每一条都要转发：图片还没有做，需要测试
                    else if (sender.equals(Utils.getForwardFrom(this)) && content.startsWith("设置转发对象：")) {
                        action.receiveType = TYPE_PUBLIC_ACCOUNT_SET_FORWARDTO;
                    } else if (sender.equals(Utils.getForwardFrom(this)) && !content.equals("[语音]") && !content.equals("[动画表情]")) {
                        action.receiveType = TYPE_PUBLIC_ACCONT_FORWARDING;
                    }
                    //需要转发到“消息收集群”
                    else if (content.startsWith("[图片]") || content.startsWith("[链接]") || content.startsWith("[视频]") || content.startsWith("[文件]") || content.startsWith("[位置]") || content.contains("向你推荐了")) {
                        action.receiveType = TYPE_COLLECTOR_FORWARDING;
                    }
                    //其他文字直接走zbus即可
                    else {
                        action.receiveType = TYPE_TEXT;
                    }

                    if (actions.size() > 100000) {
                        actions.clear();
                    }
                    actions.put(id, action);

                    if (action.receiveType == TYPE_TEXT) {
                        //刷新界面
                        refreshUI1();
                        //文字的话直接走zbus
                        sendMsgToServer(id, action);
                    } else if (action.receiveType == TYPE_IMAGE) {
                        //图片要先拉起微信,截图上传：0408业务调整，暂时没有这种类型了
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_FRIEND_CIRCLER) {
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_PUBLIC_ACCONT_FORWARDING || action.receiveType == TYPE_COLLECTOR_FORWARDING) {
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_PUBLIC_ACCOUNT_SET_FORWARDTO) {
                        String forwardto = action.content.replace("设置转发对象：", "").trim();
                        if (TextUtils.isEmpty(forwardto)) {
                            continue;
                        }
                        getSharedPreferences("forwardto", 0).edit().putString("forwardto", forwardto).commit();
                        toast("设置转发对象成功");
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_SET_FRIEND_CIRCLER_REMARK) {
                        String remark = action.content.replace("设置朋友圈备注：", "").trim();
                        if (TextUtils.isEmpty(remark)) {
                            continue;
                        }
                        getSharedPreferences("FCremark", 0).edit().putString("FCremark", remark).commit();
                        toast("设置朋友圈备注成功");
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_REQUEST_FRIEND) {
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_REDPACKAGE) {
                        launchWechat(id);
                    } else if (action.receiveType == TYPE_TRANSFER_MONEY) {
                        launchWechat(id);
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("maptrix", "窗口状态变化");
                String className = event.getClassName().toString();
                Log.d("test", "className = " + className);
                if (!className.startsWith("com.tencent.mm")) {
                    Log.d("maptrix", "当前不是微信页面return3");
                    return;
                }
                if (currentActionID == -1) {
                    Log.d("maptrix", "没有事件，return1");
                    if (className.equals("com.tencent.mm.plugin.voip.ui.VideoActivity")) {
                        //判断是不是视频通话，挂断并关闭页面即可。
                        findHungupButton(getRootInActiveWindow());
                    }
                    return;
                }
                if (actioningFlag) {
                    Log.d("maptrix", "事件进行中，return2");
                    return;
                }

                actioningFlag = true;
                FileUtils.saveFile("" + actioningFlag, "actioningFlag.txt");

                int receiveType = actions.get(currentActionID).receiveType;
                boolean uploaded = actions.get(currentActionID).uploaded;

                if (receiveType == TYPE_TEXT) {
                    ArrayList<ReturnMessage> returnMessages = actions.get(currentActionID).returnMessages;

                    //1.判断当前是不是首页
                    Log.d("test", "========================checkIsWxHomePage============");
                    weixin = false;
                    tongxunlu = false;
                    faxian = false;
                    wo = false;
                    lastTextView = null;
                    checkIsWxHomePage(getRootInActiveWindow());
                    boolean isWxHomePage = weixin && tongxunlu && faxian && wo;
                    Log.d("test", "isWxHomePage = " + isWxHomePage);
                    if (isWxHomePage) {
                        if (lastTextView == null) {
                            release();
                            return;
                        }
                        lastTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String sender = actions.get(currentActionID).sender;
                                boolean find = findInputEditText(getRootInActiveWindow(), sender);
                                if (!find) {
                                    release();
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("test", "=============findTargetPeople2==============");
                                        boolean find = findTargetPeople2(getRootInActiveWindow(), sender);
                                        if (!find) {
                                            Log.d("test", "findTargetPeople2 failure");
                                            release();
                                        }
                                    }
                                }, 2000);
                            }
                        }, 2000);
                    } else {
                        doSequeSend();
                    }
                } else if (receiveType == TYPE_IMAGE && !uploaded) {
                    // 找到最后一张图片，放大，截屏，上传，得到url后返回
                    lastFrameLayout = null;
                    Log.d("test", "----------------findLastImageOrLinkMsg------------------");
                    findLastImageOrLinkMsg(getRootInActiveWindow());
                    if (lastFrameLayout == null) {
                        Log.d("test", "没有找到最后一张图片？郁闷。。。");
                        release();
                        return;
                    }
                    lastFrameLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //找下载按钮
                            Log.d("test", "-------------------findDownloadButton------------------");
                            boolean find = findDownloadButton(getRootInActiveWindow());
                            if (!find) {
                                Log.d("test", "找不到下载按钮");
                                release();
                            }
                        }
                    }, 1500);
                } else if (receiveType == TYPE_FRIEND_CIRCLER) {
                    // 找到最后一个链接，点击转发到朋友圈
                    lastFrameLayout = null;
                    Log.d("test", "----------------findLastImageOrLinkMsg------------------");
                    findLastImageOrLinkMsg(getRootInActiveWindow());
                    if (lastFrameLayout == null) {
                        Log.d("test", "没有找到最后一个链接？郁闷。。。");
                        release();
                        return;
                    }
                    lastFrameLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //找下载按钮
                            Log.d("test", "-------------------findTopRightButton------------------");
                            boolean find = findTopRightButton(getRootInActiveWindow());
                            if (!find) {
                                Log.d("test", "找不到右上角按钮");
                                release();
                            }
                        }
                    }, 10000);//防止页面加载不完整
                } else if (receiveType == TYPE_PUBLIC_ACCOUNT_SET_FORWARDTO || receiveType == TYPE_SET_FRIEND_CIRCLER_REMARK) {
                    sendTextOnly("设置成功！");
                    release();
                } else if (receiveType == TYPE_PUBLIC_ACCONT_FORWARDING || receiveType == TYPE_COLLECTOR_FORWARDING) {
                    // 找到最后一张链接，点击转发给某人
                    if (receiveType == TYPE_PUBLIC_ACCONT_FORWARDING) {
                        forwardto = getSharedPreferences("forwardto", 0).getString("forwardto", "");
                    } else if (receiveType == TYPE_COLLECTOR_FORWARDING) {
                        forwardto = getSharedPreferences("collector", 0).getString("collector", "");
                    }
                    if (TextUtils.isEmpty(forwardto)) {
                        toast("您还没有设置转发对象");
                        //回复给微信
                        sendTextOnly("您还没有设置转发对象，设置方法：请输入“设置转发对象：昵称”");
                        release();
                        return;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "===============findMsgListView================");
                            listViewNode = null;
                            findMsgListView(getRootInActiveWindow());
                            if (listViewNode == null) {
                                release();
                                return;
                            }
                            lastMsgView = null;
                            Log.d("test", "=================findLastMsgViewInListView====================");
                            findLastMsgViewInListView(listViewNode);
                            if (lastMsgView == null) {
                                Log.d("test", "没有找到最后一条消息。。。");
                                release();
                                return;
                            }
                            //1.如果是名片，判断一下是个人名片还是企业名片.
                            String content = actions.get(currentActionID).content;
                            if (content.contains("向你推荐了")) {
                                lastTextView = null;
                                Log.d("test", "=============getLastTextView=============");
                                getLastTextView(getRootInActiveWindow());
                                String text = lastTextView.getText().toString().trim();
                                boolean isPublic = text.equals("公众号名片");
                                Log.d("test", "isPublic = " + isPublic);
                                if (isPublic) {
                                    //众号号名片
                                    doLongClickLastMsg();
                                } else {
                                    //个人名片
                                    sendTextOnly("个人名片暂时不支持转发");
                                    release();
                                }
                            } else if (content.startsWith("[视频]")) {
                                lastMsgView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String cmd = "input keyevent " + KeyEvent.KEYCODE_BACK;
                                        int ret = RootCmd.execRootCmdSilent(cmd);
                                        Log.d("test", "execRootCmdSilent ret = " + ret);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                doLongClickLastMsg();
                                            }
                                        }, 2000);
                                    }
                                }, 5000);
                            } else {
                                doLongClickLastMsg();
                            }
                        }
                    }, 2000);
                } else if (receiveType == TYPE_REQUEST_FRIEND) {
                    //查找接受按钮，并点击一下
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "=============findAcceptButton===============");
                            boolean find = findAcceptButton(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        }
                    }, 2000);
                } else if (receiveType == TYPE_REDPACKAGE) {
                    Log.d("test", "================TYPE_REDPACKAGE=================");
                    lastTextView = null;
                    findLastRedPackageMsg(getRootInActiveWindow());
                    if (lastTextView == null) {
                        release();
                        return;
                    }
                    AccessibilityNodeInfo parent = lastTextView.getParent();
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //找到“开”按钮
                            findOpenPackageButton(getRootInActiveWindow());
                        }
                    }, 3000);
                } else if (receiveType == TYPE_TRANSFER_MONEY) {
                    Log.d("test", "================TYPE_TRANSFER_MONEY=================");
                    lastTextView = null;
                    findLastTransferMsg(getRootInActiveWindow());
                    if (lastTextView == null) {
                        release();
                        return;
                    }
                    AccessibilityNodeInfo parent = lastTextView.getParent();
                    parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //找到“确认收款”按钮
                            findConfirmationButton(getRootInActiveWindow());
                        }
                    }, 3000);
                } else {
                    Log.d("test", "没有匹配的消息，直接release");
                    release();
                }
                break;
        }
    }

    private void doSequeSend() {
        ArrayList<ReturnMessage> returnMessages = actions.get(currentActionID).returnMessages;

        checkMessageAllDone(returnMessages);

        //2.串行，遍历
        new Thread() {
            @Override
            public void run() {
                int count = returnMessages.size();
                for (int i = 0; i < count; i++) {
                    while (i != 0 && !returnMessages.get(i - 1).returnFinished) {
                        synchronized (o2) {
                            try {
                                o2.wait(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    ReturnMessage rm = returnMessages.get(i);
                    if (rm.returnType == TYPE_TEXT) {
                        sendTextOnly(rm.content);   //sendTextOnly可以认为是同步的。
                        rm.returnFinished = true;
                    } else if (rm.returnType == TYPE_IMAGE) {
                        sendImageOnly(rm);            //sendImageOnly一定是异步的。
                    }
                }
            }
        }.start();
    }

    private void checkMessageAllDone(ArrayList<ReturnMessage> returnMessages) {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!actioningFlag) {
                        break;
                    }
                    boolean allDone = true;
                    for (ReturnMessage rm : returnMessages) {
                        allDone = allDone & rm.returnFinished;
                    }
                    Log.d("test", "allDone = " + allDone);
                    if (allDone) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                release();
                                refreshUI2();
                            }
                        });
                        break;
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private boolean weixin;
    private boolean tongxunlu;
    private boolean faxian;
    private boolean wo;

    private void checkIsWxHomePage(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView")) {
                if (nodeInfo.getText() != null) {
                    if (nodeInfo.getText().toString().equals("微信")) {
                        weixin = true;
                    } else if (nodeInfo.getText().toString().equals("通讯录")) {
                        tongxunlu = true;
                    } else if (nodeInfo.getText().toString().equals("发现")) {
                        faxian = true;
                    } else if (nodeInfo.getText().toString().equals("我")) {
                        wo = true;
                    }
                }
                lastTextView = nodeInfo;
            }
            checkIsWxHomePage(nodeInfo);
        }
    }

    private boolean findTargetPeople2(AccessibilityNodeInfo rootNode, String forwardto) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(forwardto)) {
                Log.d("test", "click targetPeople = " + forwardto);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //跳到聊天页面
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doSequeSend();
                    }
                }, 3000);
                return true;
            }
            if (findTargetPeople2(nodeInfo, forwardto)) {
                return true;
            }
        }
        return false;
    }

    private boolean findConfirmationButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 5000);
                return true;
            }
            if (findConfirmationButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findOpenPackageButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 5000);
                return true;
            }
            if (findOpenPackageButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findLastTransferMsg(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("微信转账")) {
                lastTextView = nodeInfo;
            }
            if (findLastTransferMsg(nodeInfo)) {
                return true;
            }
        }
        return false;
    }


    private boolean findLastRedPackageMsg(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("领取红包")) {
                lastTextView = nodeInfo;
            }
            if (findLastRedPackageMsg(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo lastTextView;

    private boolean getLastTextView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView")) {
                lastTextView = nodeInfo;
            }
            if (getLastTextView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void doLongClickLastMsg() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lastMsgView.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                Log.d("test", "执行长按事件");
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Log.d("test", "=================findTransferButton===============");
                        boolean find = findTransferButton(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "findTransferButton失败，长按不出来，点击了一下");
                            Rect r = new Rect();
                            lastMsgView.getBoundsInScreen(r);
                            // 1.生成点击坐标
                            int x = r.width() / 2 + r.left;
                            int y = r.height() / 2 + r.top;
                            String cmd = "input tap " + x + " " + y;
                            Log.d("test", "cmd = " + cmd);
                            // 2.执行su命令
                            int ret = RootCmd.execRootCmdSilent(cmd);
                            Log.d("test", "execRootCmdSilent ret = " + ret);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //3.点一下返回
                                    String content = actions.get(currentActionID).content;
                                    Log.d("test", "content = " + content);
                                    if (content.startsWith("[图片]") || content.startsWith("[链接]") || content.startsWith("[视频]") || content.startsWith("[文件]") || content.startsWith("[位置]") || content.contains("向你推荐了") || isCallingDialog(getRootInActiveWindow())) {
                                        String cmd = "input keyevent " + KeyEvent.KEYCODE_BACK;
                                        int ret = RootCmd.execRootCmdSilent(cmd);
                                        Log.d("test", "execRootCmdSilent ret = " + ret);
                                    }
                                    //4.再次执行长按
                                    doLongClickLastMsgAgain();
                                }
                            }, 5000);
                        }
                    }
                }, 2000);
            }
        }, 2000);
    }

    private void refreshUI1() {
        int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0) + 1;
        getSharedPreferences("kiway", 0).edit().putInt("recvCount", recvCount).commit();
        if (MainActivity.instance != null) {
            MainActivity.instance.updateServiceCount();
        }
    }

    private void refreshUI2() {
        int replyCount = getSharedPreferences("kiway", 0).getInt("replyCount", 0) + 1;
        getSharedPreferences("kiway", 0).edit().putInt("replyCount", replyCount).commit();
        if (MainActivity.instance != null) {
            MainActivity.instance.updateServiceCount();
        }
    }

    private void toast(String txt) {
        if (AutoReplyService.instance != null) {
            Toast.makeText(AutoReplyService.instance.getApplicationContext(), txt, Toast.LENGTH_LONG).show();
        }
    }

    private void doLongClickLastMsgAgain() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "doLongClickLastMsgAgain");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lastMsgView.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test", "=================findTransferButton===============");
                                boolean find = findTransferButton(getRootInActiveWindow());
                                if (!find) {
                                    release();
                                }
                            }
                        }, 2000);
                    }
                }, 2000);
            }
        }, 5000);
    }


    //---------------------下面都是找控件--------------------------

    //找到挂断按钮
    private boolean findHungupButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("挂断")) {
                AccessibilityNodeInfo prevNode = rootNode.getChild(i - 1);
                prevNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                release();
                return true;
            }
            if (findHungupButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }


    //查询是不是呼叫对话框
    private boolean isCallingDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("添加到手机通讯录")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
            if (isCallingDialog(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    //自动加好友
    private String nickname;
    private String remark;

    private boolean findAcceptButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("接受")) {
                AccessibilityNodeInfo nicknameNode = rootNode.getChild(i - 2);
                nickname = nicknameNode.getText().toString();
                Log.d("test", "nickname = " + nickname);

                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "==============findRemarkEdiText===================");
                        boolean find = findRemarkEdiText(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findAcceptButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findRemarkEdiText(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.EditText")) {
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                remark = Utils.getParentRemark(this);
                ClipData clip = ClipData.newPlainText("label", remark);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "===============findFinishButton==================");
                        boolean find = findFinishButton(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 1000);
                return true;
            }
            if (findRemarkEdiText(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findFinishButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("完成")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //找到发消息，发一段话
                        boolean find = findSendButton(getRootInActiveWindow());
                        if (!find) {
                            release();
                            return;
                        }
                    }
                }, 5000);
                return true;
            }
            if (findFinishButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSendButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发消息")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //找到文本框输入文字发送
                        sendTextOnly("感谢您添加招生客服机器人，您可以按以下关键字发送咨询招生相关问题，谢谢！\n" +
                                "1、计生证明或者计划生育证明\n" +
                                "2、租房或者住房\n" +
                                "3、台湾或者香港\n" +
                                "4、户籍\n" +
                                "5、网上报名\n" +
                                "6、验核材料\n" +
                                "7、录取\n");
                        release();
                        String current = System.currentTimeMillis() + "";
                        uploadFriend(nickname, remark + " " + nickname, current, current);
                    }
                }, 2000);
                return true;
            }
            if (findSendButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }


    //发送给朋友
    private boolean findTransferButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发送给朋友")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //跳页
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //找文本框
                        Log.d("test", " =================findSearchEditText===============");
                        boolean find = findSearchEditText(getRootInActiveWindow(), forwardto);
                        if (!find) {
                            Log.d("test", "findSearchEditText失败");
                            release();
                        }
                    }
                }, 3000);
                return true;
            }
            if (findTransferButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSearchEditText(AccessibilityNodeInfo rootNode, String forwardto) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.EditText")) {
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", forwardto);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "=============findTargetPeople==============");
                        boolean find = findTargetPeople(getRootInActiveWindow(), forwardto);
                        if (!find) {
                            Log.d("test", "findTargetPeople failure");
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findSearchEditText(nodeInfo, forwardto)) {
                return true;
            }
        }
        return false;
    }

    private boolean findTargetPeople(AccessibilityNodeInfo rootNode, String forwardto) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(forwardto)) {
                Log.d("test", "click targetPeople = " + forwardto);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                //弹出发送框框
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int receiveType = actions.get(currentActionID).receiveType;
                        if (receiveType == TYPE_COLLECTOR_FORWARDING) {
                            //这里要额外做一步，找到文本框并粘贴内容
                            String openId = getSharedPreferences("openId", 0).getString("openId", "osP5zwJ-lEdJVGD-_5_WyvQL9Evo");
                            String content = "sender:" + senderFromNotification + ",openid:" + openId;
                            findInputEditText(getRootInActiveWindow(), content);
                        }
                        Log.d("test", "=========findSendButtonInDialog============");
                        boolean find = findSendButtonInDialog(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findTargetPeople(nodeInfo, forwardto)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo lastImageView;

    private boolean findSendButtonInDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Log.d("test", "转发完成");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int receiveType = actions.get(currentActionID).receiveType;
                        if (receiveType == TYPE_COLLECTOR_FORWARDING) {
                            release();
                        } else if (receiveType == TYPE_PUBLIC_ACCONT_FORWARDING) {
                            //给公众号回复一条消息，每天回复一次
                            String today = Utils.getToday();
                            boolean todaySended = getSharedPreferences("kiway", 0).getBoolean(today, false);
                            if (todaySended) {
                                release();
                                return;
                            }
                            //1.找到最后的ImageView
                            lastImageView = null;
                            findLastImageView(getRootInActiveWindow());
                            if (lastImageView == null) {
                                release();
                                return;
                            }
                            lastImageView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            //2.发送文本内容
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String sendContent = getSharedPreferences("sendContent", 0).getString("sendContent", "你好，请问客服在吗？");
                                    sendTextOnly(sendContent);
                                    release();
                                    getSharedPreferences("kiway", 0).edit().putBoolean(today, true).commit();
                                }
                            }, 1000);
                        }
                    }
                }, 4000);
                return true;
            }
            if (findSendButtonInDialog(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findLastImageView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.ImageView")) {
                lastImageView = nodeInfo;
            }
            if (findLastImageView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo listViewNode;

    private boolean findMsgListView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.ListView")) {
                listViewNode = nodeInfo;
                return true;
            }

            if (findMsgListView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo lastMsgView = null;

    //这个函数要想办法区分是个人微信、还是公众号。
    private boolean findLastMsgViewInListView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            //TODO 目前公众号只有文字、链接。个人微信和公众号微信要做到兼容
            //个人微信发来文字类：android.view.View
            //个人微信发来的链接、图片、位置、名片：android.widget.FrameLayout
            //公众号微信发来的文字类：android.view.View
            //公众号微信发来的链接：android.widget.LinearLayout
            if (nodeInfo.getClassName().equals("android.view.View") || nodeInfo.getClassName().equals("android.widget.FrameLayout")) {
                lastMsgView = nodeInfo;
            } else if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getParent().getClassName().equals("android.widget.LinearLayout")) {
                lastMsgView = nodeInfo.getParent();
            }
            if (findLastMsgViewInListView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findTopRightButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //1秒后找gridview
                Log.d("test", "=========findShareButton==============");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        secondLinearLayout = 0;
                        boolean find = findShareButton(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findTopRightButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    int secondLinearLayout = 0;

    private boolean findShareButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("分享到朋友圈")) {
                Log.d("test", "click 分享到朋友圈");
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //输入：这一刻的想法
                        String remark = getSharedPreferences("FCremark", 0).getString("FCremark", "");
                        Log.d("test", "--------------------findMindEditText----------");
                        boolean find = findMindEditText(getRootInActiveWindow(), remark);
                        if (!find) {
                            release();
                        }
                    }
                }, 3000);
                return true;
            }
            if (findShareButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }


    private boolean findMindEditText(AccessibilityNodeInfo rootNode, String mind) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.EditText")) {
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN, true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY, arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", mind);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "----------findSendImageButton------------------");
                        boolean find = findSendImageButton2(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "找不到发送按钮，relase");
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findMindEditText(nodeInfo, mind)) {
                return true;
            }
        }
        return false;
    }

    private boolean findDownloadButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            //Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.view.View")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                File[] files = new File("/mnt/sdcard/tencent/MicroMsg/WeiXin/").listFiles();
                File lastFile = files[files.length - 1];
                Log.d("test", "lastFile = " + lastFile.getName());
                //上传
                new Thread() {
                    @Override
                    public void run() {
                        boolean exception = false;
                        long tempID = currentActionID;
                        try {
                            Log.d("test", "uploading...");
                            String xToken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                            final String ret = UploadUtil.uploadFile(lastFile, clientUrl + "/common/file?x-auth-token=" + xToken, lastFile.getName());
                            Log.d("test", "upload ret = " + ret);
                            JSONObject obj = new JSONObject(ret);
                            String url = obj.optJSONObject("data").optString("url");
                            Log.d("test", "url = " + url);
                            actions.get(currentActionID).uploaded = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            exception = true;
                        }
                        release();
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (!exception) {
                            sendMsgToServer(tempID, actions.get(tempID));
                        }
                    }
                }.start();
                return true;
            }
            if (findDownloadButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    //找到最后的ImageView
    private AccessibilityNodeInfo lastFrameLayout = null;

    private boolean findLastImageOrLinkMsg(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.FrameLayout")) {
                lastFrameLayout = nodeInfo;
            }
            if (findLastImageOrLinkMsg(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    //---------------------------发送文字----------------

    private void sendTextOnly(String reply) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                {
                    Log.d("test", "sendTextOnly is called");
                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                    boolean find = findInputEditText(rootNode, reply);
                    Log.d("test", "findInputEditText = " + find);
                    if (!find) {
                        return;
                    }
                    AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                    List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("发送");
                    if (list != null && list.size() > 0) {
                        for (AccessibilityNodeInfo n : list) {
                            if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                                n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean findInputEditText(AccessibilityNodeInfo rootNode, String reply) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if ("android.widget.EditText".equals(nodeInfo.getClassName())) {
                Bundle arguments = new Bundle();
                arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                        AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                        true);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
                        arguments);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                ClipData clip = ClipData.newPlainText("label", reply);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }
            if (findInputEditText(nodeInfo, reply)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }

    //---------------发送图片---------------------------------------

    public void saveImage(Context context, Bitmap bmp) {
        if (bmp == null) {
            return;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "DCIM");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }


    private AccessibilityNodeInfo lastRelativeLayout;

    private void sendImageOnly(ReturnMessage rm) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "sendImageOnly");
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                Log.d("test", "-------------------------findPlusButton-----------------");
                lastImageButton = null;
                findPlusButton(rootNode, rm);
                if (lastImageButton == null) {
                    rm.returnFinished = true;
                    return;
                }
                lastImageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "---------------------findAlbumButton----------------------------");
                        lastRelativeLayout = null;
                        boolean find = findAlbumButton(getRootInActiveWindow(), rm);
                        if (!find) {
                            Log.d("test", "找不到GridView");
                            if (lastRelativeLayout == null) {
                                rm.returnFinished = true;
                            } else {
                                lastRelativeLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("test", "----------findSendImageButton------------------");
                                        boolean find = findSendImageButton1(getRootInActiveWindow(), rm);
                                        if (!find) {
                                            Log.d("test", "找不到发送按钮，relase");
                                            rm.returnFinished = true;
                                        }
                                    }
                                }, 5000);
                            }
                        }
                    }
                }, 3000);
            }
        });
    }

    private AccessibilityNodeInfo lastImageButton;

    //找到最后一个按钮，就是加号，点击一下
    private boolean findPlusButton(AccessibilityNodeInfo rootNode, ReturnMessage rm) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                AccessibilityNodeInfo prevNode = rootNode.getChild(i - 1);
                if (prevNode != null && prevNode.getClassName().equals("android.widget.ImageButton")) {
                    lastImageButton = nodeInfo;
                }
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (findPlusButton(nodeInfo, rm)) {
                return true;
            }
        }
        return false;
    }

    //找到“相册”按钮，点击一下
    private boolean findAlbumButton(AccessibilityNodeInfo rootNode, ReturnMessage rm) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.GridView")) {
                AccessibilityNodeInfo first = nodeInfo.getChild(0);
                Log.d("test", "first child = " + first.getClassName());
                first.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "------------------------findFirstPicture-------------------------");
                        boolean find = findFirstPicture(getRootInActiveWindow(), rm);
                        if (!find) {
                            Log.d("test", "找不到第一张图片 relase");
                            rm.returnFinished = true;
                        }
                    }
                }, 3000);
                return true;
            }
            if (nodeInfo.getClassName().equals("android.widget.RelativeLayout")) {
                lastRelativeLayout = nodeInfo;
            }
            if (findAlbumButton(nodeInfo, rm)) {
                return true;
            }
        }
        return false;
    }

    //找到第一张图片，点击一下勾勾。
    private boolean findFirstPicture(AccessibilityNodeInfo rootNode, ReturnMessage rm) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.RelativeLayout")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //这里checkbox点击不了，奇怪
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "----------findSendImageButton------------------");
                        boolean find = findSendImageButton1(getRootInActiveWindow(), rm);
                        if (!find) {
                            Log.d("test", "找不到发送按钮，relase");
                            rm.returnFinished = true;
                        }
                    }
                }, 5000);
                return true;
            }
            if (findFirstPicture(nodeInfo, rm)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSendImageButton1(AccessibilityNodeInfo rootNode, ReturnMessage rm) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rm.returnFinished = true;
                    }
                }, 5000);
                return true;
            }
            if (findSendImageButton1(nodeInfo, rm)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSendImageButton2(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 3000);
                return true;
            }
            if (findSendImageButton2(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void uploadFriend(String nickname, String remark, String wxId, String wxNo) {
        try {
            String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);

            String url = clientUrl + "/freind/all";
            Log.d("test", "freind url = " + url);

            JSONArray param = new JSONArray();
            JSONObject o1 = new JSONObject();
            o1.put("nickname", nickname);//昵称
            o1.put("FCremark", remark);//备注
            o1.put("wxId", wxId);//微信id
            o1.put("wxNo", wxNo);//微信号
            o1.put("robotId", robotId);
            param.put(o1);

            Log.d("test", "freind param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "freind onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "freind onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }
}