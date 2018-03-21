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
import java.util.List;

public class AutoReplyService extends AccessibilityService {
    private final static String MM_PNAME = "com.tencent.mm";
    boolean hasAction = false;
    boolean locked = false;
    boolean background = false;
    private String name;
    private String scontent;
    private String retContent = "未知错误";
    private KeyguardManager.KeyguardLock kl;
    private Handler handler = new Handler();
    public ArrayList<PendingIntent> intents = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.d("maptrix", "service oncreate");

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(10000);
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
                            try {
                                Log.d("test", "send ...");
                                hasAction = true;
                                retContent = "回复：" + System.currentTimeMillis();
                                name = "浪翻云";
                                intents.remove(0).send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
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
        android.util.Log.d("maptrix", "get event = " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                android.util.Log.d("maptrix", "get notification event");
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
                        android.util.Log.d("maptrix", "the screen is unlocked");
                        background = true;
                        android.util.Log.d("maptrix", "is mm in background");

                        Log.d("test", "add ...");

                        PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                        intents.add(intent);
                    }
                }

                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                android.util.Log.d("maptrix", "get type window down event");
                if (!hasAction) {
                    android.util.Log.d("maptrix", "return1");
                    return;
                }
                String className = event.getClassName().toString();
                if (!className.equals("com.tencent.mm.ui.LauncherUI")) {
                    android.util.Log.d("maptrix", "return2");
                    return;
                }
                if (fill()) {
                    Log.d("test", "send is called");
                    send();
                } else {
                    Log.d("test", "fill failure");

                    //1.如果在首页，找到联系人，点一下。
//        rootNode = getRootInActiveWindow();
//        findContact3(rootNode, name);
                    //findContact1();

                    //2.run script
                    //MainActivity.instance.runMyUiautomator(null);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (fill()) {
                                send();
                            }
                        }
                    }, 5000);
                }

                back2Home();
                release();
                hasAction = false;
                break;
        }
    }

    /**
     * 寻找窗体中的“发送”按钮，并且点击。
     */
    @SuppressLint("NewApi")
    private void send() {
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

    /**
     * 拉起微信界面
     *
     * @param event
     */
    private void launchWechat(AccessibilityEvent event) {
        hasAction = true;
        android.util.Log.i("maptrix", "event.getParcelableData() = " + event.getParcelableData());
        if (event.getParcelableData() != null
                && event.getParcelableData() instanceof Notification) {
            Notification notification = (Notification) event
                    .getParcelableData();


            String content = notification.tickerText.toString();
            String[] cc = content.split(":");
            name = cc[0].trim();
            scontent = cc[1].trim();

            android.util.Log.i("maptrix", "sender name =" + name);
            android.util.Log.i("maptrix", "sender content =" + scontent);

            //MainActivity.instance.runMyUiautomator(null);
            //getReplayFromServer(notification, name, scontent);
        }
    }

    @SuppressLint("NewApi")
    private boolean fill() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        boolean find = findEditText(rootNode, retContent);
        return find;
    }

    private boolean findContact3(AccessibilityNodeInfo rootNode, String name) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "findContact3 classname = " + nodeInfo.getClassName());
            if ("android.widget.ListView".equals(nodeInfo.getClassName())) {
                Log.d("test", "listview child count = " + nodeInfo.getChildCount());
                Log.d("test", "0 classname = " + nodeInfo.getChild(0).getClassName());
                Log.d("test", "1 classname = " + nodeInfo.getChild(1).getClassName());

                nodeInfo.getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
            if (findContact3(nodeInfo, name)) {
                return true;
            }
        }
        return false;
    }

    private boolean findContact2(AccessibilityNodeInfo rootNode, String name) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "findContact2 classname = " + nodeInfo.getClassName());

            if ("android.widget.TextView".equals(nodeInfo.getClassName())) {
                CharSequence text = nodeInfo.getText();
                if (text == null) {
                    continue;
                }
                Log.d("test", "textview text = " + text.toString());
                if (name.equals(text.toString())) {
                    Log.d("test", "click...");
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    return true;
                }
            } else if ("android.widget.LinearLayout".equals(nodeInfo.getClassName())) {

            }
            if (findContact2(nodeInfo, name)) {
                return true;
            }
        }
        return false;
    }

    private void findContact1() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("浪翻云");
            Log.d("test", "浪翻云个数 = " + list.size());
            if (list != null && list.size() > 0) {
                for (AccessibilityNodeInfo n : list) {
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx n = " + n.getClassName());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 1" + n.isClickable());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 2" + n.isLongClickable());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 3" + n.isSelected());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 4" + n.isAccessibilityFocused());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 5" + n.isCheckable());
                    Log.d("test", "xxxxxxxxxxxxxxxxxxxx 6" + n.isEnabled());


                    List<AccessibilityNodeInfo.AccessibilityAction> actions = n.getActionList();
                    Log.d("test", "xxxxxxxxxxxxxxxxxx actions size = " + actions.size());
                    for (AccessibilityNodeInfo.AccessibilityAction aa : actions) {
                        Log.d("test", "xxxxxxxxxx aa = " + aa.getId() + " , " + aa.getLabel());
                    }


                    n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    private boolean findEditText(AccessibilityNodeInfo rootNode, String content) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if ("com.tencent.mm.ui.mogic.WxViewPager".equals(nodeInfo.getClassName())) {
                break;
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
            android.util.Log.d("maptrix", "release the lock");
            //得到键盘锁管理器对象
            kl.reenableKeyguard();
            locked = false;
        }
    }

    private void getReplayFromServer(final Notification notification, final String name, final String content) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        String url = "http://202.104.136.9:8080/mdms/static/download/version/zip_student.json";
        client.get(this, url, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, String ret) {
                Log.d("test", "onSuccess = " + ret);
                retContent = "回复：" + content;
                handleResult();
            }

            private void handleResult() {
                PendingIntent pendingIntent = notification.contentIntent;
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.d("test", "onFailure = " + s);
                retContent = s;
                handleResult();
            }
        });
    }
}
