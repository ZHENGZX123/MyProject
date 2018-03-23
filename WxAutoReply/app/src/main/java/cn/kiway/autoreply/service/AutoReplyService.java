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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.util.Constant;
import cn.kiway.autoreply.util.Utils;
import cn.kiway.zbus.utils.ZbusUtils;
import cn.kiway.zbus.vo.PushMessageVo;
import io.zbus.mq.Broker;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;
import io.zbus.mq.Producer;

import static cn.kiway.autoreply.entity.Action.TYPE_IMAGE;
import static cn.kiway.autoreply.entity.Action.TYPE_TEST;
import static cn.kiway.autoreply.entity.Action.TYPE_TXT;
import static cn.kiway.autoreply.util.Constant.APPID;
import static cn.kiway.autoreply.util.Constant.clientUrl;

public class AutoReplyService extends AccessibilityService {

    public static AutoReplyService instance;
    private Handler handler = new Handler();
    private final Object o = new Object();

    //递增的id
    private long id;
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
        installationPush(this, Utils.getIMEI(this), Utils.getIMEI(this));
    }

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d("maptrix", "get event = " + eventType);
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                Log.d("maptrix", "接收到通知");
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

                    //1.预先加入map
                    id = System.currentTimeMillis();
                    PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                    Action action = new Action();
                    action.sender = sender;
                    action.content = content;
                    action.intent = intent;
                    if (content.equals("[图片]")) {
                        action.receiveType = TYPE_IMAGE;
                    } else {
                        action.receiveType = TYPE_TXT;
                    }
                    actions.put(id, action);

                    //2.获取答案
                    if (action.receiveType == TYPE_TXT) {
                        //文字的话直接走zbus
                        sendMsgToServer(id, action);
                    } else {
                        //图片要先拉起微信,截图上传
                        currentActionID = id;
                        launchWechat();
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
                } else if (receiveType == TYPE_IMAGE && !uploaded) {
                    // 找到最后一张图片，放大，截屏，上传，得到url后返回
                    lastFrameLayout = null;
                    Log.d("test", "----------------findLastImageMsg------------------");
                    findLastImageMsg(getRootInActiveWindow());
                    if (lastFrameLayout == null) {
                        Log.d("test", "没有找到最后一张图片？郁闷。。。");
                        release();
                        return;
                    }
                    lastFrameLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //1.截图
//                            MainActivity.instance.getWindow().getDecorView().setDrawingCacheEnabled(true);
//                            Bitmap bitmap = MainActivity.instance.getWindow().getDecorView().getDrawingCache();
//                            String time = System.currentTimeMillis() + "";
//                            String fileName = time + ".png";
//                            Utils.saveBitmap(bitmap, fileName, KWApplication.ROOT);
                            //2.找下载按钮
                            Log.d("test", "-------------------findDownloadButton------------------");
                            boolean find = findDownloadButton(getRootInActiveWindow());
                            if (!find) {
                                Log.d("test", "找不到下载按钮");
                                release();
                            }
                        }
                    }, 1500);
                }

                //2.发送图片回复
                //2.1下载图片
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            Bitmap bmp = ImageLoader.getInstance().loadImageSync("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=862704645,1557247143&fm=27&gp=0.jpg", KWApplication.getLoaderOptions());
//                            //2.2保存图片
//                            saveImage(getApplication(), bmp);
//                            //2.3发送图片
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    sendImage();
//                                }
//                            }, 1000);
//                        }
//                    }.start();

                break;
        }
    }

    private void launchWechat() {
        try {
            actions.get(currentActionID).intent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
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
                        Log.d("test", "uploading...");
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long tempID = currentActionID;
                        actions.get(currentActionID).uploaded = true;
                        release();
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sendMsgToServer(tempID, actions.get(tempID));
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

    private boolean findLastImageMsg(AccessibilityNodeInfo rootNode) {
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
            if (findLastImageMsg(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void release() {
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
                ClipData clip = ClipData.newPlainText("label", actions.get(currentActionID).reply);
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

    private long time = 5000;

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
                                        JSONObject o = new JSONObject(temp);
                                        long id = o.getLong("id");
                                        int receiveType = o.getInt("receiveType");
                                        Action action = actions.get(id);
                                        if (action == null) {
                                            Log.d("test", "action null , error!!!");
                                            return;
                                        }
                                        Log.d("test", "开始处理action = " + id);
                                        currentActionID = id;
                                        action.reply = temp;//FIXME

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (receiveType == TYPE_TXT) {
                                                    launchWechat();
                                                } else if (receiveType == TYPE_IMAGE || receiveType == TYPE_TEST) {
                                                    //do nothing
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
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer");
                try {
                    String msg = new JSONObject()
                            .put("id", id)
                            .put("sender", action.sender)
                            .put("content", action.content)
                            .put("me", "客服一号")
                            .put("time", System.currentTimeMillis())
                            .put("receiveType", action.receiveType).toString();


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
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        back2Home();
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