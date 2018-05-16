package cn.kiway.robot.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.ReturnMessage;
import cn.kiway.robot.entity.ZbusRecv;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.FileUtils;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.kiway.wx.reply.utils.RabbitMQUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static android.util.Base64.NO_WRAP;
import static cn.kiway.robot.KWApplication.sendUtil;
import static cn.kiway.robot.KWApplication.sendUtil2;
import static cn.kiway.robot.entity.Action.TYPE_ADD_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_ADD_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_AT_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_AUTO_MATCH;
import static cn.kiway.robot.entity.Action.TYPE_BACK_DOOR;
import static cn.kiway.robot.entity.Action.TYPE_CARD;
import static cn.kiway.robot.entity.Action.TYPE_CHECK_NEW_VERSION;
import static cn.kiway.robot.entity.Action.TYPE_CLEAR_ZOMBIE_FAN;
import static cn.kiway.robot.entity.Action.TYPE_COLLECTOR_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_CREATE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_MOMENT;
import static cn.kiway.robot.entity.Action.TYPE_FILE;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NAME;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NOTICE;
import static cn.kiway.robot.entity.Action.TYPE_FIX_ICON;
import static cn.kiway.robot.entity.Action.TYPE_FIX_NICKNAME;
import static cn.kiway.robot.entity.Action.TYPE_GET_ALL_FRIENDS;
import static cn.kiway.robot.entity.Action.TYPE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_IMAGE;
import static cn.kiway.robot.entity.Action.TYPE_LINK;
import static cn.kiway.robot.entity.Action.TYPE_MISSING_FISH;
import static cn.kiway.robot.entity.Action.TYPE_NEARBY_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_REQUEST_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_SET_COLLECTOR;
import static cn.kiway.robot.entity.Action.TYPE_TEXT;
import static cn.kiway.robot.entity.Action.TYPE_VIDEO;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.CLICK_NONE;
import static cn.kiway.robot.util.Constant.CLICK_PARENT;
import static cn.kiway.robot.util.Constant.CLICK_SELF;
import static cn.kiway.robot.util.Constant.DEFAULT_BUSY;
import static cn.kiway.robot.util.Constant.DEFAULT_OFFLINE;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.HEART_BEAT_TESTER;
import static cn.kiway.robot.util.Constant.NODE_BUTTON;
import static cn.kiway.robot.util.Constant.NODE_EDITTEXT;
import static cn.kiway.robot.util.Constant.NODE_FRAMELAYOUT;
import static cn.kiway.robot.util.Constant.NODE_IMAGEBUTTON;
import static cn.kiway.robot.util.Constant.NODE_IMAGEVIEW;
import static cn.kiway.robot.util.Constant.NODE_RELATIVELAYOUT;
import static cn.kiway.robot.util.Constant.NODE_TEXTVIEW;
import static cn.kiway.robot.util.Constant.port;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static java.lang.System.currentTimeMillis;

public class AutoReplyService extends AccessibilityService {

    public static int MSG_ACTION_TIMEOUT = 1;
    public static int MSG_INSERT_QUEUE = 2;
    public static int MSG_TRAVERSAL_QUEUE = 3;
    public static int MSG_HEART_BEAT = 4;

    public static AutoReplyService instance;

    //事件map
    public LinkedHashMap<Long, Action> actions = new LinkedHashMap<>();
    //当前执行的事件id
    private long currentActionID = -1;
    private boolean actioningFlag;
    //zbus接收队列
    public ArrayList<ZbusRecv> zbusRecvs = new ArrayList<>();
    //家长busy次数
    public HashMap<String, Integer> busyCountMap = new HashMap<>();

    //心跳
    private long lastHearBeatID;
    private long zbusFailureCount;

    //清理僵尸粉
    private Set<String> checkedFriends = new HashSet<>();//临时变量，已经勾选的好友
    private int start;
    private int end;
    private String currentZombie;

    //附近的人
    private ArrayList<AccessibilityNodeInfo> peoples = new ArrayList<>();

    //好友
    private Set<String> friends = new HashSet<>();


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");
        instance = this;
        mHandler.sendEmptyMessage(MSG_TRAVERSAL_QUEUE);
        //mHandler.sendEmptyMessage(MSG_HEART_BEAT);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_ACTION_TIMEOUT) {
                release();
                return;
            }
            if (msg.what == MSG_INSERT_QUEUE) {
                ZbusRecv recv = (ZbusRecv) msg.obj;
                boolean insertToHead = (msg.arg1 == 1);
                if (insertToHead) {
                    zbusRecvs.add(0, recv);
                } else {
                    zbusRecvs.add(recv);
                }
                return;
            }
            if (msg.what == MSG_TRAVERSAL_QUEUE) {
                mHandler.removeMessages(MSG_TRAVERSAL_QUEUE);
                mHandler.sendEmptyMessageDelayed(MSG_TRAVERSAL_QUEUE, 2000);
                if (!getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    return;
                }
                if (zbusRecvs.size() == 0) {
                    return;
                }
                if (currentActionID != -1) {
                    return;
                }
                handleZbusMsg(zbusRecvs.remove(0));
                return;
            }
            if (msg.what == MSG_HEART_BEAT) {
                mHandler.removeMessages(MSG_HEART_BEAT);
                mHandler.sendEmptyMessageDelayed(MSG_HEART_BEAT, 10 * 60 * 1000);
                Log.d("test", "发送了一个心跳");
                //创建一个假事件作为心跳
                Action action = new Action();
                action.sender = HEART_BEAT_TESTER;
                action.content = "100007";
                action.actionType = TYPE_TEXT;
                long id = System.currentTimeMillis();

                sendMsgToServer(id, action);
                sendReply20sLater(id, action);
            }
        }
    };

    private void launchWechat(long id, long maxReleaseTime) {
        try {
            currentActionID = id;
            actions.get(currentActionID).intent.send();

            if (maxReleaseTime < 60000) {
                maxReleaseTime = 60000;
            }
            mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, maxReleaseTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleZbusMsg(ZbusRecv recv) {
        Log.d("test", "handleZbusMsg msg = " + recv.msg);
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject o = new JSONObject(recv.msg);
                    //新增后台发的命令
                    if (o.has("type")) {
                        int type = o.getInt("type");
                        if (type == 1 || type == 2) {
                            mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, 60000);
                            currentActionID = 9999;
                            actioningFlag = true;

                            shareToWechatMoments(recv.msg);
                        }
                        return;
                    }
                    long id = o.optLong("id");
                    if (id == 0) {
                        Log.d("test", "没有id！！！");
                        return;
                    }
                    //doCheckZbusStatus(id, recv.msg);
                    //心跳测试不用拉起微信
                    if (recv.msg.contains(HEART_BEAT_TESTER)) {
                        return;
                    }

                    Action action = actions.get(id);
                    if (action == null) {
                        Log.d("test", "action null , doFindUsableAction");
                        doFindUsableAction(o);
                    } else {
                        if (action.replied && recv.msg.contains("\"returnType\":1") && recv.msg.contains("客服正忙")) {
                            Log.d("test", "action.replied");
                            return;
                        }
                        JSONArray returnMessage = o.getJSONArray("returnMessage");
                        doHandleZbusMsg(id, action, returnMessage, recv.realReply);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void doFindUsableAction(JSONObject o) {
        try {
            String sender = o.getString("sender");
            String content = o.getString("content");
            JSONArray returnMessage = o.getJSONArray("returnMessage");
            if (actions.size() < 1) {
                toast("需要至少有1个家长消息");
                return;
            }
            Long firstKey = actions.keySet().iterator().next();
            Action firstA = actions.get(firstKey);
            firstA.sender = sender;
            firstA.content = content;
            firstA.actionType = TYPE_TEXT;
            //因为使用的是别人的action，所以realReply是false
            doHandleZbusMsg(firstKey, firstA, returnMessage, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHandleZbusMsg(long id, Action action, JSONArray returnMessage, boolean realReply) {
        if (realReply) {
            action.replied = true;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "开始执行action = " + id);
                int size = returnMessage.length();
                if (size == 0) {
                    return;
                }
                action.returnMessages.clear();
                int imageCount = 0;
                int linkCount = 0;
                int videoCount = 0;
                int fileCount = 0;
                for (int i = 0; i < size; i++) {
                    try {
                        ReturnMessage rm = new ReturnMessage();
                        rm.returnType = returnMessage.getJSONObject(i).getInt("returnType");
                        if (rm.returnType == TYPE_IMAGE) {
                            imageCount++;
                        } else if (rm.returnType == TYPE_LINK) {
                            linkCount++;
                        } else if (rm.returnType == TYPE_VIDEO) {
                            videoCount++;
                        } else if (rm.returnType == TYPE_FILE) {
                            fileCount++;
                        }
                        rm.content = returnMessage.getJSONObject(i).getString("content");
                        rm.fileName = returnMessage.getJSONObject(i).optString("fileName");
                        action.returnMessages.add(rm);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                long maxReleaseTime = action.returnMessages.size() * 8000;
                Log.d("test", "imageCount = " + imageCount + " fileCount = " + fileCount + " videoCount = " + videoCount + " linkCount = " + linkCount);

                if (imageCount > 0) {
                    //图片
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, 60000);
                    currentActionID = id;
                    actioningFlag = true;
                    new Thread() {
                        @Override
                        public void run() {
                            sendImageOnly2(action.returnMessages.get(0).content);
                        }
                    }.start();
                }
                if (videoCount > 0) {
                    //  视频
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, 60000);
                    currentActionID = id;
                    actioningFlag = true;
                    sendVideoOnly2();
                } else if (fileCount > 0) {
                    //文件
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, 60000);
                    currentActionID = id;
                    actioningFlag = true;
                    sendFileOnly2(action.returnMessages.get(0).content, action.returnMessages.get(0).fileName);
                } else if (linkCount > 0) {
                    //链接
                    mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, 60000);
                    currentActionID = id;
                    actioningFlag = true;
                    new Thread() {
                        @Override
                        public void run() {
                            sendLinkOnly(action.returnMessages.get(0).content);
                        }
                    }.start();
                } else {
                    //文字，直接拉起微信即可；
                    launchWechat(id, maxReleaseTime);
                }
            }
        });
    }

    private void doCheckZbusStatus(long id, String msg) {
        //检测心跳：心跳测试使者 100007
        if (msg.contains(HEART_BEAT_TESTER) && msg.contains("100007")) {
            if (msg.contains("客服已下线") || msg.contains("客服正忙")) {
                if (id != lastHearBeatID) {
                    zbusFailureCount++;
                    Log.d("test", "心跳测试：zbus断开 FailureCount = " + zbusFailureCount);
                    if (zbusFailureCount % 3 == 0) {
                        if (MainActivity.instance != null) {
                            MainActivity.instance.updateOpenIdOrStatus(2);
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                //断开后3秒重连
                                //ZbusUtils.close();
                                try {
                                    sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Utils.initZbus(getApplication());
                            }
                        }.start();
                    }
                }
            } else {
                Log.d("test", "心跳测试：zbus正常");
                lastHearBeatID = id;
                zbusFailureCount = 0;
                if (MainActivity.instance != null) {
                    MainActivity.instance.updateOpenIdOrStatus(1);
                }
            }
        }
    }

    public synchronized void sendMsgToServer(long id, Action action) {
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer");
                try {
                    String name = getSharedPreferences("kiway", 0).getString("name", "");
                    String installationId = getSharedPreferences("kiway", 0).getString("installationId", "");
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");

                    String msg = new JSONObject()
                            .put("id", id)
                            .put("sender", action.sender)
                            .put("content", action.content)
                            .put("me", name)
                            .put("areaCode", areaCode)
                            .toString();

                    //topic : robotId#userId
                    String topic = robotId + "#" + wxNo;
                    String url = Constant.host + ":" + port;
                    PushMessageVo pushMessageVo = new PushMessageVo();
                    pushMessageVo.setDescription("desc");
                    pushMessageVo.setTitle("title");
                    pushMessageVo.setMessage(msg);
                    pushMessageVo.setAppId(APPID);
                    pushMessageVo.setModule("wx_reply");
                    Set<String> userIds = new HashSet<>();
                    userIds.add(topic);

                    pushMessageVo.setUserId(userIds);
                    pushMessageVo.setSenderId(wxNo);
                    pushMessageVo.setPushType("zbus");
                    pushMessageVo.setInstallationId(installationId);

                    Log.d("test", "sendMsgToServer = " + topic + " , msg = " + msg + ", url = " + url);

                    if (sendUtil == null) {
                        sendUtil = new RabbitMQUtils(Constant.host, topic, topic, port);
                    }
                    sendUtil.sendMsg(pushMessageVo);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public synchronized void sendMsgToServer2() {
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer2");
                try {
                    String topic = "kiway_wx_reply_result_react";
                    String msg = new JSONObject()
                            .put("cmd", "sendFriendCircleReplyCmd")
                            .put("id", "4d65cd6es6w5")
                            .put("type", "sendFriendCircleReplyCmd")
                            .put("token", "12345")
                            .toString();

                    Log.d("test", "sendMsgToServer2 = " + topic + " , msg = " + msg);

                    if (sendUtil2 == null) {
                        sendUtil2 = new RabbitMQUtils(Constant.host, topic, topic, port);
                    }
                    sendUtil2.sendMsgs(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void sendReply20sLater(long id, Action action) {
        Message msg = new Message();
        msg.what = MSG_INSERT_QUEUE;

        String hint = "";
        boolean in = Utils.isEffectiveDate();
        String welcome = getSharedPreferences("welcome", 0).getString("welcome", DEFAULT_WELCOME);
        String welcomeTitle = getSharedPreferences("welcomeTitle", 0).getString("welcomeTitle", DEFAULT_WELCOME_TITLE);
        if (in) {
            Integer i = busyCountMap.get(action.sender);
            int busyCount = (i == null) ? 0 : i;
            Log.d("test", "action.sender = " + action.sender + " ，busyCount = " + busyCount);
            if (busyCount < 3) {
                hint = DEFAULT_BUSY;
                busyCountMap.put(action.sender, busyCount + 1);
            } else {
                hint = welcome.replace(welcomeTitle, "客服正忙，您可以发送以下序号或关键字咨询：");
                busyCountMap.put(action.sender, 0);
            }
        } else {
            hint = DEFAULT_OFFLINE + welcome.replace(welcomeTitle, "");
        }
        Log.d("test", "hint = " + hint);
        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + hint + "\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
        msg.obj = new ZbusRecv(fakeRecv, false);
        mHandler.sendMessageDelayed(msg, 20000);
    }

    public void sendReplyImmediately(String fakeRecv, boolean insertToHead) {
        Message msg = new Message();
        msg.what = MSG_INSERT_QUEUE;
        msg.arg1 = insertToHead ? 1 : 0;

        msg.obj = new ZbusRecv(fakeRecv, true);
        mHandler.sendMessage(msg);
    }

    private void release() {
        Log.d("test", "release is called");
        mHandler.removeMessages(MSG_ACTION_TIMEOUT);

        //backToRobot
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
        startActivity(intent);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentActionID = -1;
                actioningFlag = false;
                FileUtils.saveFile("" + actioningFlag, "actioningFlag.txt");
            }
        }, 2000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("maptrix", "service destroy");
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
                        sender = Utils.replace(cc[0].trim());
                        content = ticker.substring(ticker.indexOf(":") + 1).trim();
                        Log.d("test", "sender name = " + sender);
                        Log.d("test", "sender content = " + content);
                        if (Utils.isInfilters(getApplication(), sender)) {
                            Log.d("test", "该昵称被过滤");
                            continue;
                        }
                        if (!Utils.isGetPic(getApplication(), content)) {
                            Log.d("test", "图片接收被过滤");
                            continue;
                        }
                        if (Utils.isUselessContent(content)) {
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

                    //自动加好友
                    if (content.endsWith("请求添加你为朋友")) {
                        action.actionType = TYPE_REQUEST_FRIEND;
                    }
                    //来自公众号的消息每一条都要转发：图片还没有做，需要测试
                    else if (content.startsWith("设置转发对象：")) {
                        //设置转发对象有可能丢掉！因为微信可能不弹出通知
                        action.actionType = TYPE_SET_COLLECTOR;
                    }
                    //需要转发到“消息收集群”
                    else if (content.startsWith("[图片]") /*|| content.startsWith("[链接]") || content.startsWith("[视频]") || content.startsWith("[文件]") || content.contains("向你推荐了")*/) {
                        action.actionType = TYPE_COLLECTOR_FORWARDING;
                    } else if (!TextUtils.isEmpty(Constant.qas.get(content.trim()))) {
                        action.actionType = TYPE_AUTO_MATCH;
                        action.returnMessages.add(new ReturnMessage(TYPE_TEXT, Constant.qas.get(content.trim())));
                    } else if (Utils.checkInBackDoor(content.trim()) > 0) {
                        action.actionType = Utils.checkInBackDoor(content.trim());
                    }
                    //其他文字直接走zbus即可
                    else {
                        action.actionType = TYPE_TEXT;
                    }

                    actions.put(id, action);

                    if (action.actionType == TYPE_TEXT) {
                        //刷新界面
                        refreshUI1();
                        //文字的话直接走zbus
                        sendMsgToServer(id, action);
                        sendReply20sLater(id, action);
                    } else if (action.actionType == TYPE_AUTO_MATCH) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + action.returnMessages.get(0).content + "\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (action.actionType == TYPE_BACK_DOOR) {
                        String ret = getBackDoorByKey(content.trim());
                        action.returnMessages.add(new ReturnMessage(TYPE_TEXT, ret));
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + action.returnMessages.get(0).content + "\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);
                    } else if (action.actionType == TYPE_COLLECTOR_FORWARDING) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);
                    } else if (action.actionType == TYPE_SET_COLLECTOR) {
                        String forwardto = action.content.replace("设置转发对象：", "").trim();
                        if (TextUtils.isEmpty(forwardto)) {
                            continue;
                        }
                        getSharedPreferences("forwardto", 0).edit().putString("forwardto", forwardto).commit();
                        getSharedPreferences("collector", 0).edit().putString("collector", forwardto).commit();
                        toast("设置转发对象成功");
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (action.actionType == TYPE_REQUEST_FRIEND) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);
                    } else if (action.actionType == TYPE_GET_ALL_FRIENDS
                            || action.actionType == TYPE_CLEAR_ZOMBIE_FAN
                            || action.actionType == TYPE_CREATE_GROUP_CHAT
                            || action.actionType == TYPE_DELETE_GROUP_CHAT
                            || action.actionType == TYPE_ADD_GROUP_PEOPLE
                            || action.actionType == TYPE_DELETE_GROUP_PEOPLE
                            || action.actionType == TYPE_FIX_GROUP_NAME
                            || action.actionType == TYPE_FIX_GROUP_NOTICE
                            || action.actionType == TYPE_GROUP_CHAT
                            || action.actionType == TYPE_AT_GROUP_PEOPLE
                            || action.actionType == TYPE_DELETE_MOMENT
                            || action.actionType == TYPE_ADD_FRIEND
                            || action.actionType == TYPE_MISSING_FISH
                            || action.actionType == TYPE_FIX_NICKNAME
                            || action.actionType == TYPE_FIX_ICON
                            || action.actionType == TYPE_NEARBY_PEOPLE) {
                        action.content = Base64.encodeToString(content.getBytes(), NO_WRAP);
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);
                    } else if (action.actionType == TYPE_CHECK_NEW_VERSION) {
                        if (MainActivity.instance != null) {
                            MainActivity.instance.checkNewVersion(null);
                        }
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
                        //视频通话，挂断并关闭页面即可。
                        findTargetNode(NODE_TEXTVIEW, "挂断", -1, true);
                        release();
                    }
                    return;
                }
                if (actioningFlag) {
                    Log.d("maptrix", "事件进行中，return2");
                    return;
                }
                actioningFlag = true;
                FileUtils.saveFile("" + actioningFlag, "actioningFlag.txt");
                int actionType = actions.get(currentActionID).actionType;

                if (actionType == TYPE_REQUEST_FRIEND) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean find = hasAcceptButton();
                            if (!find) {
                                release();
                            }
                        }
                    }, 3000);
                } else if (actionType == TYPE_CLEAR_ZOMBIE_FAN
                        || actionType == TYPE_GET_ALL_FRIENDS
                        || actionType == TYPE_CREATE_GROUP_CHAT
                        || actionType == TYPE_DELETE_GROUP_CHAT
                        || actionType == TYPE_ADD_GROUP_PEOPLE
                        || actionType == TYPE_DELETE_GROUP_PEOPLE
                        || actionType == TYPE_FIX_GROUP_NOTICE
                        || actionType == TYPE_FIX_GROUP_NAME
                        || actionType == TYPE_GROUP_CHAT
                        || actionType == TYPE_AT_GROUP_PEOPLE
                        || actionType == TYPE_DELETE_MOMENT
                        || actionType == TYPE_ADD_FRIEND
                        || actionType == TYPE_MISSING_FISH
                        || actionType == TYPE_FIX_NICKNAME
                        || actionType == TYPE_FIX_ICON
                        || actionType == TYPE_NEARBY_PEOPLE
                        ) {
                    //来自后台的命令
                    if (!checkIsWxHomePage()) {
                        try {
                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                            Log.d("test", "content = " + content);
                            JSONObject o = new JSONObject(content);
                            content = o.optString("content");
                            start = o.optInt("start");
                            end = o.optInt("end");
                            String url = o.optString("url");
                            String groupName = o.optString("groupName");

                            String finalContent = content;
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            checkIsWxHomePage();
                                            if (actionType == TYPE_GET_ALL_FRIENDS) {
                                                //通讯录
                                                getAllFriends();
                                            } else if (actionType == TYPE_CREATE_GROUP_CHAT
                                                    || actionType == TYPE_CLEAR_ZOMBIE_FAN
                                                    || actionType == TYPE_ADD_FRIEND) {
                                                //首页-右上角工具栏
                                                findTargetNode(NODE_RELATIVELAYOUT, Integer.MAX_VALUE);
                                                if (mFindTargetNode == null) {
                                                    release();
                                                    return;
                                                }
                                                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                selectToolbar();
                                            } else if (actionType == TYPE_DELETE_MOMENT) {
                                                //我-相册
                                                deleteMoment();
                                            } else if (actionType == TYPE_MISSING_FISH) {
                                                //通讯录-新的朋友
                                                addMissingFish();
                                            } else if ((actionType == TYPE_FIX_NICKNAME && isMe()) || actionType == TYPE_FIX_ICON) {
                                                //我-个人信息
                                                fixMyNicknameOrIcon(actionType, url);
                                            } else if (actionType == TYPE_NEARBY_PEOPLE) {
                                                //发现-附近的人
                                                addNearbyPeople(finalContent);
                                            } else if (actionType == TYPE_DELETE_GROUP_CHAT
                                                    || actionType == TYPE_ADD_GROUP_PEOPLE
                                                    || actionType == TYPE_DELETE_GROUP_PEOPLE
                                                    || actionType == TYPE_FIX_GROUP_NAME
                                                    || actionType == TYPE_FIX_GROUP_NOTICE
                                                    || actionType == TYPE_GROUP_CHAT
                                                    || actionType == TYPE_AT_GROUP_PEOPLE) {
                                                //通讯录-群聊-关于群的操作
                                                groupRelativeAction(actionType, groupName);
                                            } else {
                                                searchTargetInWxHomePage(actionType);
                                            }
                                        }
                                    }, 2000);
                                }
                            }, 3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            release();
                        }
                    }
                } else {
                    //聊天有关，需要容错
                    Log.d("test", "========================checkIsWxHomePage============");
                    if (checkIsWxHomePage()) {
                        //1.如果已经使用过的action，进来会去到首页
                        searchTargetInWxHomePage(1);
                    } else {
                        String targetSender = actions.get(currentActionID).sender;
                        //2.容错判断
                        boolean isCorrectPage = findTargetNode(NODE_TEXTVIEW, targetSender, CLICK_NONE, false);
                        Log.d("test", "isCorrectPage = " + isCorrectPage);
                        if (isCorrectPage) {
                            doChatByActionType();
                        } else {
                            backToWxHomePage();
                        }
                    }
                }
                break;
        }
    }

    private void groupRelativeAction(int actionType, String groupName) {
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickSomeWhere(DensityUtil.getScreenWidth() / 2, 452);//FIXME
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "群聊", 1, true);
                        if (!find) {
                            release();
                            return;
                        }
                        searchAndEnterChatView(groupName, actionType);
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void addNearbyPeople(String content) {
        faxianView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "附近的人", CLICK_PARENT, true);
                if (!find) {
                    release();
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        peoples.clear();
                        findNearbyPeopleInListView(getRootInActiveWindow());
                        Log.d("test", "friends count = " + peoples.size());
                        int count = peoples.size();

                        resetMaxReleaseTime(20000 * count);

                        new Thread() {
                            @Override
                            public void run() {
                                for (int i = 0; i < count; i++) {
                                    int finalI = i;
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            AccessibilityNodeInfo p = peoples.get(finalI);
                                            p.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                            //打招呼
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    boolean find = findTargetNode(NODE_BUTTON, "打招呼", CLICK_SELF, true);
                                                    if (!find) {
                                                        return;
                                                    }
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            findTargetNode(NODE_EDITTEXT, content);
                                                            findTargetNode(NODE_TEXTVIEW, "发布|发表", CLICK_SELF, true);
                                                            mHandler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                                }
                                                            }, 3000);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 3000);
                                        }
                                    });
                                    try {
                                        sleep(20000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                release();
                            }
                        }.start();
                    }
                }, 8000);
            }
        }, 3000);
    }

    private boolean findNearbyPeopleInListView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.LinearLayout") && nodeInfo.getChildCount() >= 3) {
                String nickname = nodeInfo.getChild(0).getText().toString();
                Log.d("test", "find nickname = " + nickname);
                peoples.add(nodeInfo);
            }
            if (findNearbyPeopleInListView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void fixMyNicknameOrIcon(int actionType, String url) {
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        new Thread() {
            @Override
            public void run() {
                if (actionType == TYPE_FIX_ICON) {
                    //1.下载图片
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(url, KWApplication.getLoaderOptions());
                    if (bmp == null) {
                        release();
                        return;
                    }
                    //2.保存图片
                    String localPath = saveImage(getApplication(), bmp, true);
                    if (localPath == null) {
                        release();
                        return;
                    }
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "微信号：", CLICK_PARENT, true);
                        if (!find) {
                            release();
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String target = "";
                                int sleepTime = 3000;
                                boolean equals = false;
                                if (actionType == TYPE_FIX_NICKNAME) {
                                    target = getSharedPreferences("kiway", 0).getString("name", "");
                                    sleepTime = 3000;
                                    equals = false;
                                } else if (actionType == TYPE_FIX_ICON) {
                                    target = "头像";
                                    sleepTime = 5000;
                                    equals = true;
                                }
                                boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, equals);
                                if (!find) {
                                    release();
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (actionType == TYPE_FIX_NICKNAME) {
                                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                                Log.d("test", "content = " + content);
                                                JSONObject o = new JSONObject(content);
                                                String newName = o.optString("newName");

                                                clearAndPasteEditText(1, newName);

                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_TEXTVIEW, "保存", CLICK_SELF, true);
                                                        if (find) {
                                                            getSharedPreferences("kiway", 0).edit().putString("name", newName).commit();
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release();
                                                            }
                                                        }, 3000);
                                                    }
                                                }, 1000);
                                            } else if (actionType == TYPE_FIX_ICON) {
                                                //选图片
                                                findTargetNode(NODE_RELATIVELAYOUT, 2);
                                                if (mFindTargetNode == null) {
                                                    release();
                                                    return;
                                                }
                                                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        findTargetNode(NODE_TEXTVIEW, "使用", CLICK_SELF, true);
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release();
                                                            }
                                                        }, 5000);
                                                    }
                                                }, 2000);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, sleepTime);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }.start();
    }

    private boolean isMe() {
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            JSONObject o = new JSONObject(content);
            String old = o.optString("oldName");
            String me = getSharedPreferences("kiway", 0).getString("name", "");
            return old.equals(me);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getAllFriends() {
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCheckFriendList();
            }
        }, 3000);
    }

    private boolean adding_missing_fish = false;

    private void addMissingFish() {
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickSomeWhere(DensityUtil.getScreenWidth() / 2, 296);//FIXME

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(3000);
                            adding_missing_fish = true;
                            resetMaxReleaseTime(30 * 60 * 1000);

                            while (adding_missing_fish) {
                                if (!actioningFlag) {
                                    break;
                                }
                                int ret = hasAcceptButtonOrAddedTextView();
                                if (ret == 1) {
                                    sleep(30000);
                                } else if (ret == 2) {
                                    sleep(10000);
                                } else if (ret == 3) {
                                    sleep(3000);
                                    break;
                                }
                            }
                            release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }, 3000);
    }

    //1:30秒 2：10秒 3：3秒
    private int hasAcceptButtonOrAddedTextView() {
        boolean find1 = hasAcceptButton();
        if (find1) {
            return 1;
        }
        boolean find2 = hasAddedTextView();
        return find2 ? 2 : 3;
    }

    private boolean hasAddedTextView() {
        findTargetNode(NODE_TEXTVIEW, "已添加", CLICK_NONE, true);
        if (mFindTargetNode == null) {
            return false;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                deleteUselessNickname(mFindTargetNode);
            }
        });
        return true;
    }

    private void deleteUselessNickname(AccessibilityNodeInfo nodeInfo) {
        nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_TEXTVIEW, "删除", CLICK_PARENT, true);
            }
        }, 3000);
    }

    private void deleteMoment() {
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                if (!find) {
                    release();
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startCheckMomentList();
                    }
                }, 2000);
            }
        }, 1000);
    }

    private boolean getMoment = false;

    private void startCheckMomentList() {
        new Thread() {
            @Override
            public void run() {
                try {
                    getMoment = false;
                    int tryCount = 20;//尝试下拉次数
                    resetMaxReleaseTime(tryCount * 10000);

                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    String text = o.optString("content");

                    for (int i = 0; i < tryCount; i++) {
                        if (getMoment) {
                            break;
                        }
                        sleep(5000);
                        //find
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                getMoment = checkMomentInListView(text);
                                Log.d("test", "getMoment = " + getMoment);
                            }
                        });
                        sleep(5000);
                        if (getMoment) {
                            break;
                        }
                        //scroll
                        execRootCmdSilent("input swipe 360 900 360 300");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    release();
                }
            }
        }.start();
    }

    private boolean checkMomentInListView(String text) {
        boolean find = findTargetNode(NODE_TEXTVIEW, text, CLICK_PARENT, false);
        if (!find) {
            return false;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_TEXTVIEW, "删除", CLICK_NONE, true);
                if (mFindTargetNode == null) {
                    //图片
                    findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                    if (mFindTargetNode == null) {
                        release();
                        return;
                    }
                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean find = findTargetNode(NODE_TEXTVIEW, "删除", CLICK_PARENT, true);
                            if (!find) {
                                release();
                                return;
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            release();
                                        }
                                    }, 3000);
                                }
                            }, 2000);
                        }
                    }, 2000);
                } else {
                    //网文
                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release();
                                }
                            }, 2000);
                        }
                    }, 2000);
                }
            }
        }, 2000);
        return true;
    }

    private void startCheckFriendList() {
        friends.clear();
        resetMaxReleaseTime(30 * 60 * 1000);
        new Thread() {
            @Override
            public void run() {
                //FIXME 有漏掉的现象？
                while (true) {
                    boolean isBottom = findTargetNode(NODE_TEXTVIEW, "位联系人", CLICK_NONE, false);
                    findFriendView(getRootInActiveWindow());
                    if (isBottom) {
                        break;
                    }
                    execRootCmdSilent("input swipe 360 900 360 300");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int count = friends.size();
                Log.d("test", "friends.size = " + count);
                for (String f : friends) {
                    Log.d("test", "friend =====>" + f);
                }
                FileUtils.saveFile(count + "", "parent.txt");

                release();
            }
        }.start();
    }

    //可以和findNearbyPeopleInListView重构成一个
    private void findFriendView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.view.View") && nodeInfo.getText() != null) {
                String nickname = nodeInfo.getText().toString();
                String me = getSharedPreferences("kiway", 0).getString("name", "");
                if (nickname.equals("微信团队") || nickname.equals("文件传输助手") || nickname.equals(me)) {
                    Log.d("test", "not add nickname = " + nickname);
                } else {
                    Log.d("test", "add nickname = " + nickname);
                    friends.add(nickname);
                }
            }
            findFriendView(nodeInfo);
        }
    }

    //首页右上角工具栏
    private void selectToolbar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int actionType = actions.get(currentActionID).actionType;
                String target = "";
                if (actionType == TYPE_CREATE_GROUP_CHAT || actionType == TYPE_CLEAR_ZOMBIE_FAN) {
                    target = "发起群聊";
                } else if (actionType == TYPE_ADD_FRIEND) {
                    target = "添加朋友";
                }
                boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!find) {
                            release();
                            return;
                        }
                        int actionType = actions.get(currentActionID).actionType;
                        if (actionType == TYPE_CLEAR_ZOMBIE_FAN) {
                            checkFriendInListView1();
                        } else if (actionType == TYPE_CREATE_GROUP_CHAT) {
                            checkFriendInListView2();
                        } else if (actionType == TYPE_ADD_FRIEND) {
                            boolean find = findTargetNode(NODE_TEXTVIEW, "微信号/QQ号/手机号", CLICK_NONE, true);
                            if (!find) {
                                release();
                                return;
                            }
                            clickSomeWhere(mFindTargetNode);
                            startAddFriend();
                        }
                    }
                }, 2000);
            }
        }, 2000);
    }

    private void startAddFriend() {
        Log.d("test", "==============startAddFriend============");
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);

                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    JSONArray members = o.getJSONArray("members");
                    int count = members.length();

                    resetMaxReleaseTime(30000 * count);

                    for (int i = 0; i < count; i++) {
                        String member = members.getString(i);
                        addFriend(member);
                        sleep(30000);
                    }
                    release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void addFriend(String member) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_EDITTEXT, member);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetNode(NODE_TEXTVIEW, member, CLICK_PARENT, false);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findTargetNode(NODE_BUTTON, "添加到通讯录", CLICK_NONE, true);
                                if (mFindTargetNode == null) {
                                    //已经是好友,返回即可
                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                } else {
                                    //还不是好友
                                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                                JSONObject o = new JSONObject(content);
                                                content = o.optString("content");

                                                //1.申请语句，这里要先点一下删除。
                                                if (!TextUtils.isEmpty(content)) {
                                                    clearAndPasteEditText(1, content);
                                                }
                                                //2.备注
                                                findTargetNode(NODE_EDITTEXT, 2, Utils.getParentRemark(getApplication(), 1));

                                                //3.点击发送按钮
                                                findTargetNode(NODE_TEXTVIEW, "发送", CLICK_SELF, true);

                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                    }
                                                }, 5000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 3000);
                                }
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        });
    }

    private void checkFriendInListView1() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //这里要滚动到预置好的位子。
                    int preScollCount = start / 8;
                    Log.d("test", "preScollCount = " + preScollCount);
                    for (int i = 0; i < preScollCount; i++) {
                        Log.d("test", "预滚第" + (i + 1) + "次");
                        execRootCmdSilent("input swipe 360 900 360 300");
                        sleep(3000);
                    }

                    int scrollCount = (end - start) / 8 + 1;
                    Log.d("test", "scrollCount = " + scrollCount);
                    checkedFriends.clear();
                    for (int i = 0; i < scrollCount; i++) {
                        doCheckFriend1(getRootInActiveWindow());
                        sleep(3000);
                        execRootCmdSilent("input swipe 360 900 360 300");
                        sleep(3000);
                        doCheckFriend1(getRootInActiveWindow());
                    }
                    //点一下确定。建群时间比较长
                    clickSureButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkFriendInListView2() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    JSONArray members = o.getJSONArray("members");
                    int count = members.length();

                    resetMaxReleaseTime(20000 * count + 15000);

                    checkedFriends.clear();

                    for (int i = 0; i < count; i++) {
                        String temp = members.getString(i);
                        int length = getTextLengthInEditText(1, temp);
                        long sleepTime = 3000 + length * 2000;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                clearAndPasteEditText(1, temp);
                            }
                        });
                        Thread.sleep(sleepTime);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                int type = actions.get(currentActionID).actionType;
                                if (type == TYPE_DELETE_GROUP_PEOPLE) {
                                    doCheckFriend2(getRootInActiveWindow());
                                } else {
                                    doCheckFriend1(getRootInActiveWindow());
                                }
                            }
                        });
                        Thread.sleep(3000);
                    }
                    //点一下确定。建群时间比较长
                    clickSureButton();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void resetMaxReleaseTime(int maxReleaseTime) {
        Log.d("test", "重设maxReleaseTime");
        mHandler.removeMessages(MSG_ACTION_TIMEOUT);
        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, maxReleaseTime);
    }

    private void clickSureButton() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "确定|删除", CLICK_SELF, false);//确定(16)
                if (!find) {
                    release();
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int type = actions.get(currentActionID).actionType;

                        if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                            boolean find = hasZombieFans();
                            if (!find) {
                                Log.d("test", "创建失败或者没有僵尸粉");
                                release();
                            }
                        } else if (type == TYPE_CREATE_GROUP_CHAT) {
                            findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                            if (mFindTargetNode == null) {
                                release();
                                return;
                            }
                            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //1. 修改群名称
                                    boolean find = findTargetNode(NODE_TEXTVIEW, "群聊名称", CLICK_PARENT, true);
                                    if (!find) {
                                        release();
                                        return;
                                    }
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                                Log.d("test", "content = " + content);
                                                JSONObject o = new JSONObject(content);
                                                String groupName = o.optString("groupName");

                                                findTargetNode(NODE_EDITTEXT, groupName);

                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_TEXTVIEW, "保存", CLICK_SELF, true);
                                                        if (!find) {
                                                            release();
                                                            return;
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                execRootCmdSilent("input swipe 360 900 360 300");

                                                                mHandler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        findTargetNode(NODE_TEXTVIEW, "保存到通讯录", CLICK_NONE, true);
                                                                        if (mFindTargetNode == null) {
                                                                            release();
                                                                            return;
                                                                        }
                                                                        clickSomeWhere(mFindTargetNode.getParent().getChild(1));
                                                                        mHandler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                release();
                                                                            }
                                                                        }, 2000);
                                                                    }
                                                                }, 3000);
                                                            }
                                                        }, 5000);
                                                    }
                                                }, 1000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, 2000);
                                }
                            }, 2000);
                        } else if (type == TYPE_ADD_GROUP_PEOPLE) {
                            release();
                        } else if (type == TYPE_DELETE_GROUP_PEOPLE) {
                            findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release();
                                }
                            }, 2000);
                        }
                    }
                }, 5000);
            }
        });
    }

    private boolean hasZombieFans() {
        boolean has = findTargetNode(NODE_TEXTVIEW, "你无法邀请未添加你为好友的用户进去群聊，请先向", CLICK_NONE, false);
        if (!has) {
            return false;
        }
        String text = mFindTargetNode.getText().toString().replace("你无法邀请未添加你为好友的用户进去群聊，请先向", "").replace("发送朋友验证申请。对方通过验证后，才能加入群聊。", "");
        String[] zombies = text.split("、");
        Log.d("test", "僵尸粉的数量：" + zombies.length);
        //返回，通过搜索好友名字，去逐个删除。
        if (zombies.length == 0) {
            toast("僵尸粉数量为0");
            release();
        }

        resetMaxReleaseTime(60000 * zombies.length);

        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doSequeClearZombies(zombies);
            }
        }, 2000);
        return true;
    }

    private void doSequeClearZombies(String[] zombies) {
        new Thread() {
            @Override
            public void run() {
                try {
                    int count = zombies.length;
                    for (int i = 0; i < count; i++) {
                        currentZombie = zombies[i];
                        Log.d("test", "currentZombie = " + currentZombie);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchTargetInWxHomePage(TYPE_CLEAR_ZOMBIE_FAN);
                            }
                        });
                        sleep(20000);
                    }
                    release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void doCheckFriend1(AccessibilityNodeInfo rootNode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int count = rootNode.getChildCount();
                for (int i = 0; i < count; i++) {
                    AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
                    if (nodeInfo == null) {
                        continue;
                    }
                    Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
                    Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
                    if (nodeInfo.getClassName().equals("android.widget.CheckBox")) {
                        AccessibilityNodeInfo prevNode = rootNode.getChild(i - 1);
                        String nickname = prevNode.getText().toString();
                        Log.d("test", "nickname = " + nickname);
                        if (checkedFriends.contains(nickname)) {
                            continue;
                        }
                        Log.d("test", "nickname add ...");
                        nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        checkedFriends.add(nickname);
                    }
                    doCheckFriend1(nodeInfo);
                }
            }
        });
    }

    private boolean doCheckFriend2(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                if (i - 2 < 0) {
                    continue;
                }
                AccessibilityNodeInfo prevNode = rootNode.getChild(i - 2);
                String nickname = prevNode.getText().toString();
                Log.d("test", "nickname = " + nickname);
                if (checkedFriends.contains(nickname)) {
                    continue;
                }
                Log.d("test", "nickname add ...");
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                checkedFriends.add(nickname);
            }
            if (doCheckFriend2(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private String getBackDoorByKey(String key) {
        StringBuilder sb = new StringBuilder();
        if (key.equals(BACK_DOOR1)) {
            int totalActionCount = actions.size();
            int undoCount = zbusRecvs.size() + 1;
            int doneCount = totalActionCount - undoCount;
            sb.append("家长问题总个数：" + totalActionCount + "，\n");
            sb.append("其中已回复个数：" + doneCount + "，\n");
            sb.append("未回复个数：" + undoCount + "。");
        } else if (key.equals(BACK_DOOR2)) {
            int totalActionCount = actions.size();
            if (totalActionCount == 0) {
                return "目前还没有家长提问";
            }
            int autoCount = 0;

            String welcome = getSharedPreferences("welcome", 0).getString("welcome", DEFAULT_WELCOME);
            Iterator<Map.Entry<Long, Action>> it = actions.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Long, Action> entry = it.next();
                Action a = entry.getValue();
                if (!TextUtils.isEmpty(qas.get(a.content))) {
                    autoCount++;
                }
                if (welcome.contains(a.content)) {
                    autoCount++;
                }
            }
            int percent = (int) ((float) autoCount / totalActionCount * 100);


            sb.append("家长问题总个数：" + totalActionCount + "，\n");
            sb.append("其中自动回复个数：" + autoCount + "，占比" + percent + "%");
        }
        return sb.toString();
    }

    private boolean realSendText;

    private void doChatByActionType() {
        int actionType = actions.get(currentActionID).actionType;
        if (actionType == TYPE_TEXT || actionType == TYPE_AUTO_MATCH || actionType == TYPE_BACK_DOOR) {
            if (realSendText) {
                doSequeSendText();
                realSendText = false;
            } else {
                int size = actions.get(currentActionID).returnMessages.size();
                Log.d("test", "要回复的条数:" + size);
                if (size < 4) {
                    doSequeSendText();
                } else {
                    realSendText = true;
                    backToWxHomePage();
                }
            }
        } else if (actionType == TYPE_SET_COLLECTOR) {
            sendTextOnly("设置成功！", true);
        } else if (actionType == TYPE_COLLECTOR_FORWARDING) {
            // 找到最后一张链接，点击转发给某人
            String collector = getSharedPreferences("collector", 0).getString("collector", "我的KW");
            if (TextUtils.isEmpty(collector)) {
                toast("您还没有设置转发对象");
                //回复给微信
                sendTextOnly("您还没有设置转发对象，设置方法：请输入“设置转发对象：昵称”", true);
                return;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //暂时去掉公众号、名片功能
                    Log.d("test", "=================findLastMsgViewInListView====================");
                    findTargetNode(NODE_FRAMELAYOUT, Integer.MAX_VALUE);
                    if (mFindTargetNode == null) {
                        Log.d("test", "没有找到最后一条消息。。。");
                        release();
                        return;
                    }
                    String content = actions.get(currentActionID).content;
                    if (content.startsWith("[视频]")) {
                        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
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
        } else {
            Log.d("test", "没有匹配的消息，直接release");
            release();
        }
    }

    //FIXME 可以干掉
    private void backToWxHomePage() {
        //先返回首页，再按搜索去做
        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                searchTargetInWxHomePage(1);
            }
        }, 2000);
    }

    private void searchTargetInWxHomePage(int type) {
        findTargetNode(NODE_TEXTVIEW, Integer.MAX_VALUE);
        if (mFindTargetNode == null) {
            release();
            return;
        }
        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String target = "";
                    if (type == TYPE_FIX_NICKNAME) {
                        String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                        JSONObject o = new JSONObject(content);
                        target = o.optString("oldName");
                    } else if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                        target = currentZombie;
                    } else {
                        //不需要base64
                        target = actions.get(currentActionID).sender;
                    }
                    searchAndEnterChatView(target, type);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
    }

    private boolean checkIsWxHomePage() {
        weixin = false;
        tongxunlu = false;
        faxian = false;
        wo = false;
        tongxunluTextView = null;
        woTextView = null;
        faxianView = null;
        checkIsWxHomePage(getRootInActiveWindow());
        boolean isWxHomePage = weixin && tongxunlu && faxian && wo;
        Log.d("test", "isWxHomePage = " + isWxHomePage);
        return isWxHomePage;
    }

    private void doSequeSendText() {
        ArrayList<ReturnMessage> returnMessages = actions.get(currentActionID).returnMessages;
        new Thread() {
            @Override
            public void run() {
                try {
                    int count = returnMessages.size();
                    for (int i = 0; i < count; i++) {
                        ReturnMessage rm = returnMessages.get(i);
                        sendTextOnly(rm.content, false);
                        sleep(3000);
                    }
                    release();
                    refreshUI2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private boolean weixin;
    private boolean tongxunlu;
    private boolean faxian;
    private boolean wo;

    private AccessibilityNodeInfo tongxunluTextView;
    private AccessibilityNodeInfo woTextView;
    private AccessibilityNodeInfo faxianView;

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
                    String text = nodeInfo.getText().toString();
                    if (text.equals("微信")) {
                        weixin = true;
                    } else if (text.equals("通讯录")) {
                        tongxunlu = true;
                        tongxunluTextView = nodeInfo.getParent();
                    } else if (text.equals("发现")) {
                        faxian = true;
                        faxianView = nodeInfo.getParent();
                    } else if (text.equals("我")) {
                        wo = true;
                        woTextView = nodeInfo.getParent();
                    } else if (text.startsWith("微信号：")) {
                        Log.d("test", "===============wxNo compare==============");
                        String realWxNo = text.replace("微信号：", "");
                        String loginWxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                        if (realWxNo.equals(loginWxNo)) {
                            Log.d("test", "微信号一致");
                        } else {
                            toast("机器人的微信号和实际微信号不一致！！！");
                        }
                    }
                }
            }
            checkIsWxHomePage(nodeInfo);
        }
    }

    // 好友、群的聊天窗口：1聊天 其他：actionType
    private void searchAndEnterChatView(String target, int type) {
        findTargetNode(NODE_EDITTEXT, target);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, false);
                if (mFindTargetNode == null) {
                    release();
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 1) {
                            //开始聊天
                            doChatByActionType();
                        } else if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                            deleteFriend(target);
                        } else if (type == TYPE_ADD_GROUP_PEOPLE
                                || type == TYPE_DELETE_GROUP_PEOPLE
                                || type == TYPE_FIX_GROUP_NAME
                                || type == TYPE_FIX_GROUP_NOTICE) {
                            //群信息
                            findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                            if (mFindTargetNode == null) {
                                release();
                                return;
                            }
                            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (type == TYPE_ADD_GROUP_PEOPLE || type == TYPE_DELETE_GROUP_PEOPLE) {
                                        addOrDeleteGroupPeople(type);
                                    } else if (type == TYPE_FIX_GROUP_NOTICE || type == TYPE_FIX_GROUP_NAME) {
                                        String target = type == TYPE_FIX_GROUP_NAME ? "群聊名称" : "群公告";
                                        boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, true);
                                        if (!find) {
                                            release();
                                            return;
                                        }
                                        fixGroupNameOrNotice();
                                    }
                                }
                            }, 2000);
                        } else if (type == TYPE_GROUP_CHAT) {
                            //这里应该调用 doChatByActionType();
                            try {
                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                JSONObject o = new JSONObject(content);
                                String text = o.optString("content");
                                sendTextOnly(text, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (type == TYPE_AT_GROUP_PEOPLE) {
                            //循环开始艾特人
                            startAtPeople();
                        } else if (type == TYPE_FIX_NICKNAME) {
                            //好友信息
                            fixFriendNickname(target);
                        }
                    }
                }, 3000);
            }
        }, 2000);
    }

    private void deleteFriend(String target) {
        findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
        if (mFindTargetNode == null) {
            return;
        }
        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, false);
                if (!find) {
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetNode(NODE_IMAGEBUTTON, 1);
                        if (mFindTargetNode == null) {
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean find = findTargetNode(NODE_TEXTVIEW, "删除", CLICK_PARENT, true);
                                if (find) {
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            findTargetNode(NODE_BUTTON, "删除", CLICK_SELF, true);
                                        }
                                    }, 2000);
                                }
                            }
                        }, 2000);
                    }
                }, 3000);
            }
        }, 2000);
    }

    private void fixGroupNameOrNotice() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是不是已经有了公告
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    String text = o.optString("content");
                    boolean has = findTargetNode(NODE_TEXTVIEW, "编辑", CLICK_SELF, true);
                    if (has) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //1.先执行删除键
                                clearAndPasteEditText(1, text);

                                doFixGroupNameOrNotice(text);
                            }
                        }, 2000);
                    } else {
                        findTargetNode(NODE_EDITTEXT, text);
                        doFixGroupNameOrNotice(text);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    private void fixFriendNickname(String friend) {
        findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
        if (mFindTargetNode == null) {
            release();
            return;
        }
        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, friend, CLICK_PARENT, false);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!find) {
                            release();
                            return;
                        }
                        boolean find = findTargetNode(NODE_TEXTVIEW, "设置备注和标签", CLICK_SELF, true);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!find) {
                                    release();
                                    return;
                                }
                                boolean find = findTargetNode(NODE_TEXTVIEW, friend, CLICK_SELF, false);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!find) {
                                            release();
                                            return;
                                        }
                                        try {
                                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                            Log.d("test", "content = " + content);
                                            JSONObject o = new JSONObject(content);
                                            String newName = o.optString("newName");

                                            clearAndPasteEditText(1, newName);

                                            boolean find = findTargetNode(NODE_TEXTVIEW, "完成", CLICK_SELF, true);
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (find) {
                                                        findTargetNode(NODE_BUTTON, "发布", CLICK_SELF, true);
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release();
                                                            }
                                                        }, 2000);
                                                    } else {
                                                        release();
                                                    }
                                                }
                                            }, 5000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void startAtPeople() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    Log.d("test", "content = " + content);
                    JSONObject o = new JSONObject(content);
                    JSONArray members = o.getJSONArray("members");
                    int count = members.length();

                    resetMaxReleaseTime(count * 15000 + 5000);

                    for (int i = 0; i < count; i++) {
                        String member = members.getString(i);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                findTargetNode(NODE_EDITTEXT, "@");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean find = findTargetNode(NODE_TEXTVIEW, "选择提醒的人", 1, true);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                findTargetNode(NODE_EDITTEXT, member);
                                                boolean find = findTargetNode(NODE_TEXTVIEW, member, CLICK_PARENT, false);
                                                Log.d("test", "@find = " + find);
                                                if (find) {
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            findTargetNode(NODE_BUTTON, "发送", CLICK_SELF, true);
                                                        }
                                                    }, 2000);
                                                } else {
                                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                            mHandler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                                    //删掉@
                                                                    RootCmd.execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_DEL);
                                                                }
                                                            }, 1000);
                                                        }
                                                    }, 1000);
                                                }
                                            }
                                        }, 1000);
                                    }
                                }, 3000);
                            }
                        });
                        sleep(15000);
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String contentStr = o.optString("content");
                                    sendTextOnly(contentStr, true);
                                }
                            }, 3000);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //TextView Button的nodeText是用来对比的；EditText的nodeText是用来做粘贴的
    //targetIndex: 1第一个  9999最后一个
    //clickType修改成int：0不点击 1点击自己 2点击parent
    //equals：true全文比较 false包含

    private int nodeIndex = 0;
    private AccessibilityNodeInfo mFindTargetNode;

    private boolean findTargetNode(String className, int targetIndex) {
        nodeIndex = 0;
        mFindTargetNode = null;
        return doFindTargetNode(getRootInActiveWindow(), className, targetIndex, null, CLICK_NONE, false);
    }

    private boolean findTargetNode(String className, int targetIndex, String targetText) {
        nodeIndex = 0;
        mFindTargetNode = null;
        return doFindTargetNode(getRootInActiveWindow(), className, targetIndex, targetText, CLICK_NONE, false);
    }

    private boolean findTargetNode(String className, String targetText) {
        nodeIndex = 0;
        mFindTargetNode = null;
        return doFindTargetNode(getRootInActiveWindow(), className, 1, targetText, CLICK_NONE, false);
    }

    private boolean findTargetNode(String className, String targetText, int clickType, boolean equals) {
        nodeIndex = 0;
        mFindTargetNode = null;
        return doFindTargetNode(getRootInActiveWindow(), className, 1, targetText, clickType, equals);
    }

    private boolean doFindTargetNode(AccessibilityNodeInfo rootNode, String className, int targetIndex, String targetText, int clickType, boolean equals) {

        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if (nodeInfo.getClassName().equals(className)) {
                nodeIndex++;

                if (className.equals(NODE_TEXTVIEW) || className.equals(NODE_BUTTON)) {
                    if (TextUtils.isEmpty(targetText)) {
                        //根据nodeIndex匹配
                        mFindTargetNode = nodeInfo;
                        if (targetIndex != Integer.MAX_VALUE && nodeIndex == targetIndex) {
                            return true;
                        }
                    } else {
                        //根据nodeText匹配
                        CharSequence text = nodeInfo.getText();
                        if (text != null) {
                            String textStr = text.toString();
                            if ((textStr.contains(targetText) && !equals) || (Utils.aContainsB(textStr, targetText) && !equals)
                                    || (textStr.equals(targetText) && equals) || (Utils.aEqualsB(textStr, targetText) && equals)) {
                                if (clickType == CLICK_SELF) {
                                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                } else if (clickType == CLICK_PARENT) {
                                    nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                } else if (clickType == CLICK_NONE) {
                                    //do nothing
                                } else {
                                    rootNode.getChild(i + clickType).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                                mFindTargetNode = nodeInfo;
                                return true;
                            }
                        }
                    }
                } else if (className.equals(NODE_EDITTEXT)) {
                    //根据nodeIndex匹配
                    mFindTargetNode = nodeInfo;
                    if (targetIndex != Integer.MAX_VALUE && nodeIndex == targetIndex) {
                        if (!TextUtils.isEmpty(targetText)) {
                            Bundle arguments = new Bundle();
                            arguments.putInt(AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT,
                                    AccessibilityNodeInfo.MOVEMENT_GRANULARITY_WORD);
                            arguments.putBoolean(AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN,
                                    true);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY,
                                    arguments);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
                            ClipData clip = ClipData.newPlainText("label", targetText);
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboardManager.setPrimaryClip(clip);
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_PASTE);
                        }
                        return true;
                    }
                } else if (className.equals(NODE_IMAGEVIEW)
                        || className.equals(NODE_FRAMELAYOUT)
                        || className.equals(NODE_RELATIVELAYOUT)
                        || className.equals(NODE_IMAGEBUTTON)
                        ) {
                    //根据nodeIndex匹配
                    mFindTargetNode = nodeInfo;
                    if (targetIndex != Integer.MAX_VALUE && nodeIndex == targetIndex) {
                        return true;
                    }
                }
            }
            if (doFindTargetNode(nodeInfo, className, targetIndex, targetText, clickType, equals)) {
                return true;
            }
        }
        return false;
    }

    private void doFixGroupNameOrNotice(String text) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "保存|发布|完成", CLICK_SELF, true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (find) {
                            findTargetNode(NODE_BUTTON, "发布", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release();
                                }
                            }, 2000);
                        } else {
                            release();
                        }
                    }
                }, 5000);
            }
        }, 1000);
    }

    private void clearAndPasteEditText(int index, String newContent) {
        int length = getTextLengthInEditText(index, newContent);
        for (int i = 0; i < length; i++) {
            RootCmd.execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_DEL);
        }
        if (length != -1) {
            findTargetNode(NODE_EDITTEXT, newContent);
        }
    }

    public int getTextLengthInEditText(int index, String compare) {
        findTargetNode(NODE_EDITTEXT, index);
        if (mFindTargetNode == null) {
            return 0;
        }
        String text = mFindTargetNode.getText().toString();
        if (text.equals("搜索")) {
            return 0;
        }
        if (text.equals(compare)) {
            return -1;
        }
        int length = text.length();
        Log.d("test", "text = " + text + " length = " + length);
        return length;
    }

    private void addOrDeleteGroupPeople(int type) {
        findTargetNode(NODE_IMAGEVIEW, (type == TYPE_ADD_GROUP_PEOPLE) ? 2 : 3);
        if (mFindTargetNode == null) {
            release();
            return;
        }
        mFindTargetNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkFriendInListView2();
            }
        }, 2000);
    }

    private void doLongClickLastMsg() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                longClickSomeWhere(mFindTargetNode);
                Log.d("test", "执行长按事件");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "发送给朋友", CLICK_SELF, true);
                        if (!find) {
                            release();
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String collector = getSharedPreferences("collector", 0).getString("collector", "我的KW");
                                findTargetNode(NODE_EDITTEXT, collector);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean find = findTargetNode(NODE_TEXTVIEW, collector, CLICK_PARENT, false);
                                        if (!find) {
                                            release();
                                            return;
                                        }
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                String content = getCollectorForwardingContent();
                                                boolean find = findTargetNode(NODE_EDITTEXT, content);
                                                if (!find) {
                                                    Log.d("test", "粘贴不上，不发过去");
                                                    release();
                                                    return;
                                                }
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_BUTTON, "发送", CLICK_SELF, true);
                                                        if (!find) {
                                                            release();
                                                            return;
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release();
                                                            }
                                                        }, 4000);
                                                    }
                                                }, 2000);
                                            }
                                        }, 2000);
                                    }
                                }, 2000);
                            }
                        }, 3000);
                    }
                }, 2000);
            }
        }, 2000);
    }

    private void clickSomeWhere(AccessibilityNodeInfo node) {
        Log.d("test", "clickSomeWhere node = " + node.getClassName());
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        // 1.生成点击坐标
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        // 2.执行su命令
        int ret = execRootCmdSilent("input tap " + x + " " + y);
        Log.d("test", "execRootCmdSilent ret = " + ret);
    }

    private void longClickSomeWhere(AccessibilityNodeInfo node) {
        Log.d("test", "longClickSomeWhere node = " + node.getClassName());
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        // 1.生成点击坐标
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        // 2.执行su命令
        int ret = execRootCmdSilent("input touchscreen swipe " + x + " " + y + " " + x + " " + y + " 2000");
        Log.d("test", "execRootCmdSilent ret = " + ret);
    }

    private void clickSomeWhere(int x, int y) {
        // 2.执行su命令
        int ret = execRootCmdSilent("input tap " + x + " " + y);
        Log.d("test", "execRootCmdSilent ret = " + ret);
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
            Toast.makeText(AutoReplyService.instance.getApplication(), txt, Toast.LENGTH_LONG).show();
        }
    }

    //自动加好友
    private String nickname;
    private String remark;
    private String lastNickname = "";

    private boolean hasAcceptButton() {
        findTargetNode(NODE_BUTTON, "接受", CLICK_NONE, true);
        if (mFindTargetNode == null) {
            return false;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AccessibilityNodeInfo nicknameNode = mFindTargetNode.getParent().getChild(0);
                nickname = nicknameNode.getText().toString();
                Log.d("test", "nickname = " + nickname);
                if (lastNickname.equals(nickname)) {
                    //重复nickname，执行删除操作
                    deleteUselessNickname(nicknameNode);
                    return;
                }
                lastNickname = nickname;
                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        remark = Utils.getParentRemark(getApplication(), 1);
                        findTargetNode(NODE_EDITTEXT, remark);

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean find = findTargetNode(NODE_TEXTVIEW, "完成", CLICK_SELF, true);
                                if (find) {
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (adding_missing_fish) {
                                                //漏网之鱼不再发消息
                                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                            } else {
                                                //找到发消息，发一段话
                                                boolean find = findTargetNode(NODE_BUTTON, "发消息", CLICK_SELF, true);
                                                if (find) {
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //找到文本框输入文字发送
                                                            String welcome = getSharedPreferences("welcome", 0).getString
                                                                    ("welcome", DEFAULT_WELCOME);
                                                            boolean find = findTargetNode(NODE_EDITTEXT, welcome);
                                                            release();
                                                            if (find) {
                                                                String current = System.currentTimeMillis() + "";
                                                                Utils.uploadFriend(getApplication(), nickname, remark + " " +
                                                                        nickname, current, current);
                                                            }
                                                        }
                                                    }, 3000);
                                                } else {
                                                    release();
                                                }
                                            }
                                        }
                                    }, 5000);
                                } else {
                                    if (adding_missing_fish) {
                                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                    } else {
                                        release();
                                    }
                                }
                            }
                        }, 2000);
                    }
                }, 3000);
            }
        });
        return true;
    }

    private void sendTextOnly(String reply, boolean release) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_EDITTEXT, reply);
                if (!find) {
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetNode(NODE_BUTTON, "发送", CLICK_SELF, true);
                    }
                }, 1000);
            }
        });

        if (release) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    release();
                }
            }, 3000);
        }
    }

    @Override
    public void onInterrupt() {

    }

    public String saveImage(Context context, Bitmap bmp, boolean insertDB) {
        if (bmp == null) {
            return null;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "DCIM");
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();


            if (insertDB) {
                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        file.getAbsolutePath(), file.getName(), null);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            }

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendImageOnly2(String url) {
        //1.下载图片
        Bitmap bmp = ImageLoader.getInstance().loadImageSync(url, KWApplication.getLoaderOptions());
        if (bmp == null) {
            release();
            return;
        }
        //2.保存图片
        String localPath = saveImage(getApplication(), bmp, false);
        if (localPath == null) {
            release();
            return;
        }

        Log.d("test", "sendImageOnly2");
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath(localPath);
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);

        doShareToWechatFriend();
    }

    private void sendVideoOnly2() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("视频");
        sp.setTitle("视频");
        sp.setUrl(actions.get(currentActionID).returnMessages.get(0).content);
        sp.setImagePath(KWApplication.defaultVideo);
        sp.setShareType(Platform.SHARE_VIDEO);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);

        doShareToWechatFriend();
    }

    private void sendFileOnly2(String url, String fileName) {
        //1.下载
        String savedFilePath = KWApplication.ROOT + "downloads/" + fileName;

        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Log.d("test", "onSuccess");

                Log.d("test", "sendFileOnly2");
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setTitle("文件");
                sp.setFilePath(savedFilePath);
                sp.setImagePath(KWApplication.defaultFile);
                sp.setShareType(Platform.SHARE_FILE);
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                wx.share(sp);

                doShareToWechatFriend();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError");
                release();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "onCancelled");
                release();
            }

            @Override
            public void onFinished() {
                Log.d("test", "onFinished");
            }
        });
    }

    private void sendLinkOnly(String content) {
        try {
            JSONObject contentO = new JSONObject(content);
            String title = contentO.getString("title");
            String describe = contentO.getString("description");
            String imageUrl = contentO.getString("imgUrl");
            String url = contentO.getString("url");

            //1.下载图片
            String localPath = null;
            if (!TextUtils.isEmpty(imageUrl)) {
                Bitmap bmp = ImageLoader.getInstance().loadImageSync(imageUrl, KWApplication.getLoaderOptions());
                if (bmp != null) {
                    //2.保存图片
                    localPath = saveImage(getApplication(), bmp, false);
                }
            }

            Log.d("test", "sendLinkOnly");
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(title);
            sp.setText(describe);
            sp.setUrl(url);
            if (!TextUtils.isEmpty(localPath)) {
                sp.setImagePath(localPath);
            }
            sp.setShareType(Platform.SHARE_WEBPAGE);
            Platform wx = ShareSDK.getPlatform(Wechat.NAME);
            wx.share(sp);

            doShareToWechatFriend();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareToWechatMoments(String content) {
        try {
            JSONObject contentO = new JSONObject(content);
            String title = contentO.getString("title");
            String describe = contentO.getString("description");
            String imageUrl = contentO.getString("imgUrl");
            String url = contentO.getString("url");
            int type = contentO.getInt("type");
            if (type == 2) {
                String[] imageArray = imageUrl.replace("[", "").replace("]", "").split(",");
                //图文
                ArrayList<Uri> imageUris = new ArrayList<>();
                for (int i = 0; i < imageArray.length; i++) {
                    String image = imageArray[i].replace("\"", "");
                    Log.d("test", "image = " + image);
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(image, KWApplication.getLoaderOptions());
                    if (bmp != null) {
                        String localPath = saveImage(getApplication(), bmp, false);
                        Log.d("test", "localPath = " + localPath);
                        imageUris.add(Uri.fromFile(new File(localPath)));
                    }
                }
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putExtra("Kdescription", describe);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                doShareToWechatMoments("");
            } else {
                //网文
                String localPath = null;
                if (!TextUtils.isEmpty(imageUrl)) {
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(imageUrl, KWApplication.getLoaderOptions());
                    if (bmp != null) {
                        localPath = saveImage(getApplication(), bmp, false);
                    }
                }

                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setTitle(title);
                sp.setText(describe);
                sp.setUrl(url);
                if (!TextUtils.isEmpty(localPath)) {
                    sp.setImagePath(localPath);
                }
                sp.setShareType(Platform.SHARE_WEBPAGE);
                Platform wx = ShareSDK.getPlatform(WechatMoments.NAME);
                wx.share(sp);

                doShareToWechatMoments(describe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doShareToWechatMoments(String remark) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int delay = 0;
                if (!TextUtils.isEmpty(remark)) {
                    //1.查找备注文本框并粘贴remark
                    findTargetNode(NODE_EDITTEXT, remark);
                    delay = 2000;
                }
                //2.查找发送按钮并点击
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetNode(NODE_TEXTVIEW, "发布|发表", CLICK_SELF, true);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                release();
                            }
                        }, 3000);
                    }
                }, delay);
            }
        }, 4000);
    }

    private void doShareToWechatFriend() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String sender = actions.get(currentActionID).sender;
                //找文本框
                boolean find = findTargetNode(NODE_EDITTEXT, sender);
                if (!find) {
                    release();
                    return;
                }
                findTargetNode(NODE_BUTTON, "分享", CLICK_SELF, true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 2000);
            }
        }, 4000);
    }

    private String getCollectorForwardingContent() {
        JSONObject o = new JSONObject();
        try {
            String name = getSharedPreferences("kiway", 0).getString("name", "");
            String installationId = getSharedPreferences("kiway", 0).getString("installationId", "");
            String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
            String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
            String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
            String topic = robotId + "#" + wxNo;
            Action currentAction = actions.get(currentActionID);
            String content = currentAction.content;

            String msg = new JSONObject()
                    .put("id", System.currentTimeMillis() + "")
                    .put("sender", currentAction.sender)
                    .put("content", "content")
                    .put("me", name)
                    .put("areaCode", areaCode)
                    .toString();

            int msgType = TYPE_TEXT;
            if (content.startsWith("[图片]")) {
                msgType = TYPE_IMAGE;
            } else if (content.startsWith("[链接]")) {
                msgType = TYPE_LINK;
            } else if (content.startsWith("[视频]")) {
                msgType = TYPE_VIDEO;
            } else if (content.startsWith("[文件]")) {
                msgType = TYPE_FILE;
            } else if (content.contains("向你推荐了")) {
                msgType = TYPE_CARD;
            }

            o.put("appId", APPID);
            o.put("description", "description");
            o.put("installationId", installationId);
            o.put("message", msg);
            o.put("module", "wx_reply");
            o.put("pushType", "zbus");
            o.put("senderId", wxNo);
            o.put("type", msgType);
            o.put("title", "title");
            o.put("userId", new JSONArray().put(topic));

            Log.d("test", "o = " + o.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    public boolean test(AccessibilityNodeInfo rootNode) {
        getTextLengthInEditText(1, "5之");
        return false;
//        int count = rootNode.getChildCount();
//        Log.d("test", "count = " + count);
//        for (int i = 0; i < count; i++) {
//            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
//            if (nodeInfo == null) {
//                continue;
//            }
//            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
//            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
//            //Log.d("test", "nodeInfo.xy = " + getNodePosition(nodeInfo));
//            if (test(nodeInfo)) {
//                return true;
//            }
//        }
//        return false;
    }

    private String getNodePosition(AccessibilityNodeInfo node) {
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        // 1.生成点击坐标
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        return x + " " + y;
    }

    public void test2() {
        Log.d("test", "=================findLastMsgViewInListView====================");
        findTargetNode(NODE_FRAMELAYOUT, Integer.MAX_VALUE);
        longClickSomeWhere(mFindTargetNode);
    }
}
