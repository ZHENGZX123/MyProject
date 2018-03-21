package cn.kiway.autoreply;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.zbus.utils.ZbusUtils;
import cn.kiway.zbus.vo.PushMessageVo;
import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;
import io.zbus.mq.Producer;

public class AutoReplyService extends AccessibilityService {

    public static AutoReplyService instance;

    boolean hasAction = false;
    boolean locked = false;
    boolean background = false;
    private String retContent;
    private KeyguardManager.KeyguardLock kl;
    private Handler handler = new Handler();
    public ArrayList<PendingIntent> intents = new ArrayList<>();
    private boolean stop;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");

        stop = false;
        instance = this;

        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("test", "loop ...");
                    if (intents.size() == 0) {
                        continue;
                    }
                    if (hasAction) {
                        continue;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "send ...");
                            hasAction = true;
                            //1.测试回复
                            //retContent = "自动回复：" + System.currentTimeMillis();
                            //name = "客服一号";
                            //intents.remove(0).send();

                            //2.后台回复
                            getReplayFromServer();
                        }
                    });
                }
            }
        }.start();
    }

    /**
     * 必须重写的方法，响应各种事件。
     *
     * @param event
     */
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d("maptrix", "get event = " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                Log.d("maptrix", "get notification event");
                List<CharSequence> texts = event.getText();
                if (texts.isEmpty()) {
                    return;
                }
                for (CharSequence text : texts) {
                    String content = text.toString();
                    if (!TextUtils.isEmpty(content)) {
                        if (isScreenLocked()) {
                            continue;
                        }
                        locked = false;
                        Log.d("maptrix", "the screen is unlocked");
                        background = true;
                        Log.d("maptrix", "is mm in background");

                        if (event.getParcelableData() != null
                                && event.getParcelableData() instanceof Notification) {
                            Notification notification = (Notification) event
                                    .getParcelableData();
                            String ticker = notification.tickerText.toString();
                            String[] cc = ticker.split(":");
                            String name = cc[0].trim();
                            String scontent = cc[1].trim();

                            Log.d("test", "sender name = " + name);
                            Log.d("test", "sender content = " + scontent);
                        }

                        Log.d("test", "add ...");

                        PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                        intents.add(intent);
                    }
                }

                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("maptrix", "get type window down event");
                if (!hasAction) {
                    Log.d("maptrix", "return1");
                    return;
                }
                String className = event.getClassName().toString();
                if (!className.equals("com.tencent.mm.ui.LauncherUI")) {
                    Log.d("maptrix", "return2");
                    return;
                }

                //发送文字回复
                sendTxt();

                back2Home();
                release();
                hasAction = false;
                break;
        }
    }

    private void sendTxt() {
        if (fill()) {
            send();
        }
    }

    /**
     * 寻找窗体中的“发送”按钮，并且点击。
     */
    @SuppressLint("NewApi")
    private void send() {
        Log.d("test", "send is called");
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText("发送");
            if (list != null && list.size() > 0) {
                for (AccessibilityNodeInfo n : list) {
                    if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            } else {
                List<AccessibilityNodeInfo> liste = nodeInfo
                        .findAccessibilityNodeInfosByText("Send");
                if (liste != null && liste.size() > 0) {
                    for (AccessibilityNodeInfo n : liste) {
                        if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                            n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
            //pressBackButton();
        }
    }

    /**
     * 模拟back按键
     */
    private void pressBackButton() {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private boolean fill() {
        Log.d("test", "fill is called");
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        boolean find = findEditText(rootNode, retContent);
        Log.d("test", "find = " + find);
        return find;
    }

    private boolean findEditText(AccessibilityNodeInfo rootNode, String content) {
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
                ClipData clip = ClipData.newPlainText("label", content);
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }
            if (findEditText(nodeInfo, content)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }

    /**
     * 回到系统桌面
     */
    private void back2Home() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    /**
     * 系统是否在锁屏状态
     *
     * @return
     */
    private boolean isScreenLocked() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    private void release() {
        if (locked && kl != null) {
            Log.d("maptrix", "release the lock");
            //得到键盘锁管理器对象
            kl.reenableKeyguard();
            locked = false;
        }
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
                    String topic = "kiway_wx_" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsgs(topic, new MessageHandler() {
                        @Override
                        public void handle(Message message, MqClient mqClient) throws IOException {
                            String temp = message.getBodyString();
                            Log.d("test", "zbus receive = " + temp);
                        }
                    }, Constant.zbusHost + ":" + Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getReplayFromServer() {
        Log.d("test", "getReplayFromServer");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        String url = "http://202.104.136.9:8080/mdms/static/download/version/zip_student.json";
        client.get(this, url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                Log.d("test", "onSuccess = " + ret);
                handleResult("服务器回复：" + ret);
            }

            @Override
            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                Log.d("test", "onFailure = " + ret);
                handleResult("服务器请求失败");
            }

            private void handleResult(String s) {
                Log.d("test", "handleResult");
                retContent = s;
                try {
                    intents.remove(0).send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doSendMsg(String msg) throws Exception {
        //topic : 老师的deviceId#userId
        String userId = Utils.getIMEI(getApplicationContext());

        String topic = Utils.getIMEI(this) + "#" + userId;
        String url = Constant.zbusHost + ":" + Constant.zbusPost;
        PushMessageVo pushMessageVo = new PushMessageVo();
        pushMessageVo.setDescription("desc");
        pushMessageVo.setTitle("title");
        pushMessageVo.setMessage(msg);
        pushMessageVo.setAppId(Constant.APPID);
        pushMessageVo.setModule("student");
        Set<String> userIds = new HashSet<>();
//        for (Student s : students) {
//            userIds.add(s.token);
//        }
        pushMessageVo.setUserId(userIds);//学生token
        pushMessageVo.setSenderId(userId);//老师的userId
        pushMessageVo.setPushType("zbus");

        Log.d("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
        ZbusUtils.sendMsg(topic, pushMessageVo);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.d("maptrix", "service destroy");
    }
}
