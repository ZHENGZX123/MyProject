package cn.kiway.autoreply.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.autoreply.activity.MainActivity;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.util.Constant;
import cn.kiway.autoreply.util.UploadUtil;
import cn.kiway.autoreply.util.Utils;
import cn.kiway.wx.reply.utils.ZbusUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

import static cn.kiway.autoreply.entity.Action.TYPE_IMAGE;
import static cn.kiway.autoreply.entity.Action.TYPE_LINK;
import static cn.kiway.autoreply.entity.Action.TYPE_SET_FORWARDING;
import static cn.kiway.autoreply.entity.Action.TYPE_TEST;
import static cn.kiway.autoreply.entity.Action.TYPE_TRANSMIT;
import static cn.kiway.autoreply.entity.Action.TYPE_TXT;
import static cn.kiway.autoreply.util.Constant.APPID;
import static cn.kiway.autoreply.util.Constant.clientUrl;

public class AutoReplyService extends AccessibilityService {

    public static int MSG_CLEAR_ACTION = 1;

    public static AutoReplyService instance;
    private final Object o = new Object();

    //事件map
    public HashMap<Long, Action> actions = new HashMap<>();
    //当前执行的事件id
    private long currentActionID = -1;
    private boolean actioningFlag;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");

        instance = this;
        installationPush(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_CLEAR_ACTION) {
                release();
            }
        }
    };

    private void launchWechat() {
        try {
            actions.get(currentActionID).intent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回到系统桌面
     */
    private void back2Home() {
//        Intent home = new Intent(Intent.ACTION_MAIN);
//        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        home.addCategory(Intent.CATEGORY_HOME);
//        startActivity(home);
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
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    String topic = "kiway_wx_reply_push_" + robotId + "#" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsg(topic, new MessageHandler() {

                        @Override
                        public void handle(Message message, MqClient mqClient) {
                            new Thread() {
                                @Override
                                public void run() {
                                    String temp = message.getBodyString();
                                    Log.d("test", "zbus receive = " + temp);

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
                                        JSONArray array = new JSONArray(temp);
                                        JSONObject o = array.getJSONObject(0);//TODO暂时拿第一个
                                        long id = o.optLong("id");
                                        if (id == 0) {
                                            Log.d("test", "没有id！！！");
                                            return;
                                        }
                                        int returnType = o.getInt("returnType");
                                        String returnMessage = o.getString("returnMessage");
                                        if (TextUtils.isEmpty(returnMessage)) {
                                            returnMessage = "测试回复";
                                        }
                                        Action action = actions.get(id);
                                        if (action == null) {
                                            Log.d("test", "action null , error!!!");
                                            return;
                                        }
                                        Log.d("test", "开始处理action = " + id);
                                        String finalReturnMessage = returnMessage;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (returnType == TYPE_TXT) {
                                                    currentActionID = id;
                                                    handler.sendEmptyMessageDelayed(MSG_CLEAR_ACTION, 10000);
                                                    action.reply = finalReturnMessage;
                                                    launchWechat();
                                                } else if (returnType == TYPE_IMAGE || returnType == TYPE_TEST) {
                                                    Log.d("test", "do nothing");
                                                }
                                            }
                                        });
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
        handler.removeMessages(MSG_CLEAR_ACTION);
        back2Home();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    currentActionID = -1;
                    actioningFlag = false;
                    Log.d("test", "唤醒。。。");
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
                    if (!ticker.contains(":")) {
                        continue;
                    }
                    String[] cc = ticker.split(":");
                    String sender = cc[0].trim();
                    String content = cc[1].trim();
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

                    //1.预先加入map
                    long id = System.currentTimeMillis();
                    PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                    Action action = new Action();
                    action.sender = sender;
                    action.content = content;
                    action.intent = intent;
                    if (sender.equals("朋友圈使者") && content.startsWith("[链接]")) {
                        //需要转发到朋友圈
                        action.receiveType = TYPE_LINK;
                    } else if (sender.equals("转发使者") && content.startsWith("设置转发对象：")) {
                        action.receiveType = TYPE_SET_FORWARDING;
                    } else if (sender.equals("转发使者") && !content.equals("[语音]") && !content.equals("[动画表情]")) {
                        //需要转发该消息
                        action.receiveType = TYPE_TRANSMIT;
                    } else if (content.equals("[图片]")) {
                        //保存给易敏即可
                        action.receiveType = TYPE_IMAGE;
                    } else {
                        //文字直接回复
                        action.receiveType = TYPE_TXT;
                    }

                    if (actions.size() > 100000) {
                        actions.clear();
                    }
                    actions.put(id, action);

                    //1.刷新界面
                    int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0) + 1;
                    getSharedPreferences("kiway", 0).edit().putInt("recvCount", recvCount).commit();
                    if (MainActivity.instance != null) {
                        MainActivity.instance.updateServiceCount();
                    }

                    //2.获取答案
                    if (action.receiveType == TYPE_TXT) {
                        //文字的话直接走zbus
                        sendMsgToServer(id, action);
                    } else if (action.receiveType == TYPE_IMAGE) {
                        //图片要先拉起微信,截图上传
                        handler.sendEmptyMessageDelayed(MSG_CLEAR_ACTION, 30000);
                        currentActionID = id;
                        launchWechat();
                    } else if (action.receiveType == TYPE_LINK) {
                        handler.sendEmptyMessageDelayed(MSG_CLEAR_ACTION, 30000);
                        currentActionID = id;
                        launchWechat();
                    } else if (action.receiveType == TYPE_TRANSMIT) {
                        handler.sendEmptyMessageDelayed(MSG_CLEAR_ACTION, 30000);
                        currentActionID = id;
                        launchWechat();
                    } else if (action.receiveType == TYPE_SET_FORWARDING) {
                        String forwarding = action.content.replace("设置转发对象：", "").trim();
                        getSharedPreferences("forwarding", 0).edit().putString("forwarding", forwarding).commit();
                        Toast.makeText(AutoReplyService.instance.getApplicationContext(), "设置转发对象成功", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("maptrix", "窗口状态变化");
                if (currentActionID == -1) {
                    Log.d("maptrix", "没有事件，return1");
                    return;
                }
                if (actioningFlag) {
                    Log.d("maptrix", "事件进行中，return2");
                    return;
                }
                String className = event.getClassName().toString();
                Log.d("test", "className = " + className);
                if (!className.equals("com.tencent.mm.ui.LauncherUI")) {
                    Log.d("maptrix", "return3");
                    return;
                }
                actioningFlag = true;

                int receiveType = actions.get(currentActionID).receiveType;
                boolean uploaded = actions.get(currentActionID).uploaded;
                //1.发送文字回复
                if (receiveType == TYPE_TXT) {
                    sendTxt();
                    release();
                    int replyCount = getSharedPreferences("kiway", 0).getInt("replyCount", 0) + 1;
                    getSharedPreferences("kiway", 0).edit().putInt("replyCount", replyCount).commit();
                    if (MainActivity.instance != null) {
                        MainActivity.instance.updateServiceCount();
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
                    handler.postDelayed(new Runnable() {
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
                } else if (receiveType == TYPE_LINK) {
                    // 找到最后一张链接，点击转发到朋友圈
                    lastFrameLayout = null;
                    Log.d("test", "----------------findLastImageOrLinkMsg------------------");
                    findLastImageOrLinkMsg(getRootInActiveWindow());
                    if (lastFrameLayout == null) {
                        Log.d("test", "没有找到最后一张图片？郁闷。。。");
                        release();
                        return;
                    }
                    lastFrameLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    handler.postDelayed(new Runnable() {
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
                    }, 5000);
                } else if (receiveType == TYPE_TRANSMIT) {
                    // 找到最后一张链接，点击转发到朋友圈
                    Log.d("test", "----------------findLastMsg------------------");
                    String forwarding = getSharedPreferences("forwarding", 0).getString("forwarding", "");
                    if (TextUtils.isEmpty(forwarding)) {
                        Toast.makeText(AutoReplyService.instance.getApplicationContext(), "您还没有设置转发对象", Toast.LENGTH_SHORT).show();
                        release();
                        return;
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lastMsgView = null;
                            findLastMsg(getRootInActiveWindow());
                            if (lastMsgView == null) {
                                Log.d("test", "没有找到最后一条消息。。。");
                                release();
                                return;
                            }
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Action action = actions.get(currentActionID);
                                    int type = action.receiveType;
                                    Log.d("test", "准备长按 type = " + type);
                                    lastMsgView.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                                    if (type == TYPE_TXT) {
                                        lastMsgView.performAction(AccessibilityNodeInfo.ACTION_SELECT);
                                    }
                                    lastMsgView.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                                    Log.d("test", "执行长按事件");
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("test", "=================findTransferButton===============");
                                            boolean find = findTransferButton(getRootInActiveWindow());
                                            if (!find) {
                                                Log.d("test", "findTransferButton失败，长按不出来？");
                                                release();
                                            }
                                        }
                                    }, 2000);
                                }
                            }, 2000);
                        }
                    }, 2000);
                } else {
                    Log.d("test", "没有匹配的消息，直接release");
                    release();
                }
                break;
        }
    }

    //---------------------下面都是找控件--------------------------

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
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().equals("发送给朋友")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //跳页
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //找文本框
                        Log.d("test", " =================findSearchEditText===============");
                        String forwarding = getSharedPreferences("forwarding", 0).getString("forwarding", "");
                        boolean find = findSearchEditText(getRootInActiveWindow(), forwarding);
                        if (!find) {
                            Log.d("test", "findSearchEditText失败");
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findTransferButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSearchEditText(AccessibilityNodeInfo rootNode, String forwarding) {
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
                ClipData clip = ClipData.newPlainText("label", forwarding);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "=============findTargetPeople==============");
                        boolean find = findTargetPeople(getRootInActiveWindow(), forwarding);
                        if (!find) {
                            Log.d("test", "findTargetPeople failure");
                            release();
                        }
                    }
                }, 1000);
                return true;
            }
            if (findSearchEditText(nodeInfo, forwarding)) {
                return true;
            }
        }
        return false;
    }

    private boolean findTargetPeople(AccessibilityNodeInfo rootNode, String forwarding) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(forwarding)) {
                Log.d("test", "click targetPeople = " + forwarding);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "=========findSendButtonInDialog============");
                        boolean find = findSendButtonInDialog(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 1000);
                return true;
            }
            if (findTargetPeople(nodeInfo, forwarding)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSendButtonInDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Log.d("test", "转发完成");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 1000);
                return true;
            }
            if (findSendButtonInDialog(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo lastMsgView = null;

    private boolean findLastMsg(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.view.View") || nodeInfo.getClassName().equals("android.widget.FrameLayout")) {
                lastMsgView = nodeInfo;
            }
            if (findLastMsg(nodeInfo)) {
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        secondLinearLayout = 0;
                        boolean find = findShareButton(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 1000);
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
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().equals("分享到朋友圈")) {
                Log.d("test", "click 分享到朋友圈");
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "----------findSendImageButton------------------");
                        boolean find = findSendImageButton(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "找不到发送按钮，relase");
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
            //Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            //Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
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

    private void sendTxt() {
        Log.d("test", "sendTxt is called");
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        boolean find = findInputEditText(rootNode);
        Log.d("test", "findInputEditText = " + find);
        if (find) {
            AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("发送");
            if (list != null && list.size() > 0) {
                for (AccessibilityNodeInfo n : list) {
                    if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    }
                }
            } else {
                Log.d("test", "find send button false");
            }
        }
    }

    private boolean findInputEditText(AccessibilityNodeInfo rootNode) {
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
                ClipData clip = ClipData.newPlainText("label", actions.get(currentActionID).reply);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }
            if (findInputEditText(nodeInfo)) {
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
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "DCIM");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
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

    private void sendImage() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        boolean find2 = findPlusButton(rootNode);
        Log.d("test", "find2 = " + find2);
        if (!find2) {
            Log.d("test", "找不到加号，release掉");
            release();
        }
    }

    int imageButtonCount = 0;

    //找到最后一个按钮，就是加号，点击一下
    private boolean findPlusButton(AccessibilityNodeInfo rootNode) {
        //Log.d("test", "findPlusButton");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                imageButtonCount++;
                //Log.d("test", "imageButtonCount = " + imageButtonCount);
            }
            if (imageButtonCount == 4) {
                imageButtonCount = 0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.d("test", "---------------------findAlbumButton----------------------------");
                        boolean find = findAlbumButton(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "找不到GridView，release掉");
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findPlusButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    //找到“相册”按钮，点击一下
    private boolean findAlbumButton(AccessibilityNodeInfo rootNode) {
        //Log.d("test", "findAlbumButton");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.GridView")) {
                AccessibilityNodeInfo first = nodeInfo.getChild(0);
                //Log.d("test", "first child = " + first.getClassName());
                first.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "------------------------findFirstPicture-------------------------");
                        boolean find = findFirstPicture(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "找不到第一张图片 relase");
                            release();
                        }
                    }
                }, 3000);
                return true;
            }
            if (findAlbumButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    //找到第一张图片，点击一下勾勾。
    private boolean findFirstPicture(AccessibilityNodeInfo rootNode) {
        //Log.d("test", "findFirstPicture");
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "----------findSendImageButton------------------");
                        boolean find = findSendImageButton(getRootInActiveWindow());
                        if (!find) {
                            Log.d("test", "找不到发送按钮，relase");
                            release();
                        }
                    }
                }, 3000);
                return true;
            }
            if (findFirstPicture(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSendImageButton(AccessibilityNodeInfo rootNode) {
        //Log.d("test", "findSendImageButton");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 3000);
                return true;
            }
            if (findSendImageButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }
}
