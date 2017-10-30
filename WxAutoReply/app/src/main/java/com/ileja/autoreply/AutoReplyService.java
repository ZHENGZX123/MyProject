package com.ileja.autoreply;

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
    AccessibilityNodeInfo itemNodeinfo;
    private KeyguardManager.KeyguardLock kl;
    private Handler handler = new Handler();
    public ArrayList<AccessibilityEvent> events = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.d("maptrix", "service oncreate");

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (events.size() == 0) {
                        continue;
                    }
                    if (hasAction) {
                        continue;
                    }
                    android.util.Log.d("maptrix", "get firstEvent");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            AccessibilityEvent firstEvent = events.remove(0);
                            launchWechat(firstEvent);
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

//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
                        launchWechat(event);
//                            }
//                        }, 100);

                        //加入队列
//                        events.add(event);
                    }
                }

                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                android.util.Log.d("maptrix", "get type window down event");
                if (!hasAction) {
                    android.util.Log.d("maptrix", "break");
                    break;
                }
                itemNodeinfo = null;
                String className = event.getClassName().toString();
                if (!className.equals("com.tencent.mm.ui.LauncherUI")) {
                    return;
                }
                if (fill()) {
                    send();
                }
                //bring2Front();
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
            pressBackButton();
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

            getReplayFromServer(notification, name, scontent);
        }
    }

    @SuppressLint("NewApi")
    private boolean fill() {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            return findEditText(rootNode, retContent);
        }
        return false;
    }

    private boolean findEditText(AccessibilityNodeInfo rootNode, String content) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if (nodeInfo.getContentDescription() != null) {
                int nindex = nodeInfo.getContentDescription().toString().indexOf(name);
                if (nindex != -1) {
                    itemNodeinfo = nodeInfo;
                }
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
        String url = "http://202.104.136.9:8389/download/version/zip_xs.json";
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
