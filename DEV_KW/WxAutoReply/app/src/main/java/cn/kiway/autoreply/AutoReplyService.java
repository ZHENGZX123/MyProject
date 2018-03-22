package cn.kiway.autoreply;

import android.accessibilityservice.AccessibilityService;
import android.app.KeyguardManager;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import static cn.kiway.autoreply.Constant.APPID;
import static cn.kiway.autoreply.Constant.clientUrl;

public class AutoReplyService extends AccessibilityService {

    public static AutoReplyService instance;

    boolean actioning = false;
    boolean sending = false;
    boolean locked = false;
    boolean background = false;
    private Handler handler = new Handler();
    private boolean stop;

    //通知队列
    public ArrayList<KwNotification> kns = new ArrayList<>();
    public ArrayList<String> resultSends = new ArrayList<>();
    public HashMap<Long, KwNotification> actions = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");

        stop = false;
        instance = this;
        installationPush(this, Utils.getIMEI(this), Utils.getIMEI(this));

        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Log.d("test", "loop ...");
                    if (kns.size() == 0) {
                        //Log.d("test", "size == 0");
                        continue;
                    }
                    if (actioning || sending) {
                        //Log.d("test", "actioning continue");
                        continue;
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "start a action ...");
                            sending = true;
                            //1.测试回复
                            //retContent = "自动回复：" + System.currentTimeMillis();
                            //name = "客服一号";
                            //kns.remove(0).send();
                            //2.后台回复
                            getReplayFromServer();
                        }
                    });
                }
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    if (resultSends.size() == 0) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    if (actioning) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    String temp = resultSends.remove(0);
                    handleResult(temp);
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

                        //只打印用
                        String senderName = null;
                        String sendContent = null;
                        if (event.getParcelableData() != null
                                && event.getParcelableData() instanceof Notification) {
                            Notification notification = (Notification) event
                                    .getParcelableData();
                            String ticker = notification.tickerText.toString();
                            String[] cc = ticker.split(":");
                            senderName = cc[0].trim();
                            sendContent = cc[1].trim();
                            Log.d("test", "sender name = " + senderName);
                            Log.d("test", "sender content = " + sendContent);
                        }

//                        if (actioning) {
//                            Log.d("test", "当前事件还没有执行完。。。这样的话同时只能处理一个。。。");
//                            break;
//                        }

                        Log.d("test", "add ...");
                        PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                        KwNotification kn = new KwNotification(senderName, sendContent, intent);
                        kns.add(kn);
                    }
                }

                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("maptrix", "get type window down event");
                if (!actioning) {
                    Log.d("maptrix", "return1");
                    return;
                }

                String className = event.getClassName().toString();
                if (!className.equals("com.tencent.mm.ui.LauncherUI")) {
                    Log.d("maptrix", "return2");
                    return;
                }

                if (true) {
                    //1.发送文字回复
                    sendTxt();
                    back2Home();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            actioning = false;
                        }
                    }, 1000);
                } else {
                    //2.发送图片回复
                    //2.1下载图片
                    new Thread() {
                        @Override
                        public void run() {
                            Bitmap bmp = ImageLoader.getInstance().loadImageSync("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=862704645,1557247143&fm=27&gp=0.jpg", KWApplication.getLoaderOptions());
                            //2.2保存图片
                            saveImage(getApplication(), bmp);
                            //2.3发送图片
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    sendImage();
                                }
                            }, 1000);
                        }
                    }.start();
                }
                break;
        }
    }

    //---------------------------发送文字----------------

    private void sendTxt() {
        Log.d("test", "sendTxt is called");
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        boolean find = findEditText(rootNode);
        Log.d("test", "find = " + find);
        if (find) {
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
                }
            }
        }
    }

    private boolean findEditText(AccessibilityNodeInfo rootNode) {
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
                ClipData clip = ClipData.newPlainText("label", actions.get(currentID).senderContent + System.currentTimeMillis());
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(clip);
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                return true;
            }
            if (findEditText(nodeInfo)) {
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
                    String topic = "kiway_push_" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsgs(topic, new MessageHandler() {
                        @Override
                        public void handle(Message message, MqClient mqClient) throws IOException {
                            String temp = message.getBodyString();
                            Log.d("test", "zbus receive = " + temp);

                            resultSends.add(temp);

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
        try {
            long id = System.currentTimeMillis();
            KwNotification kn = kns.remove(0);
            actions.put(id, kn);
            String msg = new JSONObject().put("id", id).put("senderName", kn.senderName).put("senderContent", kn.senderContent).put("me", "客服一号").toString();

            sending = false;
            doSendMsg(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long currentID;

    private void handleResult(String res) {
        Log.d("test", "handleResult");
        try {
            actioning = true;
            JSONObject o = new JSONObject(res);
            long id = o.getLong("id");
            currentID = id;
            actions.get(id).pi.send();//拉起微信
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doSendMsg(String msg) throws Exception {
        //topic : 老师的deviceId#userId
        String userId = Utils.getIMEI(getApplicationContext());

        String topic = Utils.getIMEI(this) + "#" + userId;
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

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.d("maptrix", "service destroy");
        uninstallPush(this);
        ZbusUtils.close();
    }

    public void installationPush(final Context c, final String userId, final String imei) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "userId = " + userId);
            JSONObject param = new JSONObject();
            param.put("appId", APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", imei);//userId
            param.put("module", "student");
            Log.d("test", "installationPush param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = clientUrl + "/push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
    }

    int imageButtonCount = 0;

    //找到最后一个按钮，就是加号，点击一下
    private boolean findPlusButton(AccessibilityNodeInfo rootNode) {
        Log.d("test", "findPlusButton");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                imageButtonCount++;
                Log.d("test", "imageButtonCount = " + imageButtonCount);
            }
            if (imageButtonCount == 4) {
                imageButtonCount = 0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.d("test", "-------------------------------------------------");
                        findAlbumButton(getRootInActiveWindow());
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
        Log.d("test", "findAlbumButton");
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "-------------------------------------------------");
                        findFirstPicture(getRootInActiveWindow());
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
        Log.d("test", "findFirstPicture");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.RelativeLayout")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //这里checkbox点击不了，奇怪
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "----------------------------");
                        findSendImageButton(getRootInActiveWindow());
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
        Log.d("test", "findSendImageButton");
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Log.d("test", "text = " + nodeInfo.getText());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back2Home();
                        actioning = false;
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
