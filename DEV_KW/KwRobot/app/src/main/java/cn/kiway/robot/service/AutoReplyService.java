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
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
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
import cn.kiway.robot.moment.model.Zombie;
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
import static cn.kiway.robot.entity.Action.TYPE_ADD_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_ADD_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_AT_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_AUTO_MATCH;
import static cn.kiway.robot.entity.Action.TYPE_BACK_DOOR;
import static cn.kiway.robot.entity.Action.TYPE_CARD;
import static cn.kiway.robot.entity.Action.TYPE_CLEAR_ZOMBIE_FAN;
import static cn.kiway.robot.entity.Action.TYPE_COLLECTOR_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_CREATE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_MOMENT;
import static cn.kiway.robot.entity.Action.TYPE_FILE;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NAME;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NOTICE;
import static cn.kiway.robot.entity.Action.TYPE_FRIEND_CIRCLER;
import static cn.kiway.robot.entity.Action.TYPE_GET_ALL_FRIENDS;
import static cn.kiway.robot.entity.Action.TYPE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_IMAGE;
import static cn.kiway.robot.entity.Action.TYPE_LINK;
import static cn.kiway.robot.entity.Action.TYPE_MISSING_FISH;
import static cn.kiway.robot.entity.Action.TYPE_PUBLIC_ACCONT_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_REQUEST_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_SET_FORWARDTO;
import static cn.kiway.robot.entity.Action.TYPE_SET_FRIEND_CIRCLER_REMARK;
import static cn.kiway.robot.entity.Action.TYPE_TEXT;
import static cn.kiway.robot.entity.Action.TYPE_VIDEO;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.BACK_DOOR3;
import static cn.kiway.robot.util.Constant.DEFAULT_BUSY;
import static cn.kiway.robot.util.Constant.DEFAULT_OFFLINE;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.HEART_BEAT_TESTER;
import static cn.kiway.robot.util.Constant.backdoors;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static cn.kiway.robot.util.Utils.getParentRemark;
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

    private String forwardto;//当前要转发的对象

    //心跳
    private long lastHearBeatID;
    private long zbusFailureCount;

    //清理僵尸粉
    private ArrayList<Zombie> zombies = new ArrayList<>();
    private Set<String> checkedFriends = new HashSet<>();//临时变量，已经勾选的好友
    private int start;
    private int end;

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

                //zbus发送
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


    private void backToRobot() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
        startActivity(intent);
    }

    public void handleZbusMsg(ZbusRecv recv) {
        Log.d("test", "handleZbusMsg msg = " + recv.msg);
        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject o = new JSONObject(recv.msg);
                    //0425新增朋友圈
                    if (o.has("type")) {
                        int type = o.getInt("type");
                        if (type == 100) {
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
        //心跳测试使者 100007
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
                    String url = Constant.zbusHost + ":" + Constant.zbusPort;
                    PushMessageVo pushMessageVo = new PushMessageVo();
                    pushMessageVo.setDescription("desc");
                    pushMessageVo.setTitle("title");
                    pushMessageVo.setMessage(msg);
                    pushMessageVo.setAppId(APPID);
                    pushMessageVo.setModule("wx_reply");
                    Set<String> userIds = new HashSet<>();
                    userIds.add(topic);

                    pushMessageVo.setUserId(userIds);
                    pushMessageVo.setSenderId(wxNo);// userid imei
                    pushMessageVo.setPushType("zbus");
                    pushMessageVo.setInstallationId(installationId);

                    Log.d("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);

                    sendUtil = new RabbitMQUtils(Constant.zbusHost, topic, topic);
                    sendUtil.sendMsg(pushMessageVo);
                    //ZbusUtils.sendMsg(topic, pushMessageVo);
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
        backToRobot();
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
                        if (Utils.isInfilters(getApplicationContext(), sender)) {
                            Log.d("test", "该昵称被过滤");
                            continue;
                        }
                        if (!Utils.isGetPic(getApplicationContext(), content)) {
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
                    //需要转发到朋友圈，目前只支持链接
                    else if (sender.equals(Utils.getFCFrom(this)) && content.startsWith("[链接]")) {
                        action.actionType = TYPE_FRIEND_CIRCLER;
                    } else if (sender.equals(Utils.getFCFrom(this)) && content.startsWith("设置朋友圈备注：")) {
                        action.actionType = TYPE_SET_FRIEND_CIRCLER_REMARK;
                    }
                    //来自公众号的消息每一条都要转发：图片还没有做，需要测试
                    else if (content.startsWith("设置转发对象：")) {
                        //设置转发对象有可能丢掉！因 为微信可能不弹出通知
                        action.actionType = TYPE_SET_FORWARDTO;
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
                    } else if (action.actionType == TYPE_FRIEND_CIRCLER) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (action.actionType == TYPE_PUBLIC_ACCONT_FORWARDING || action.actionType == TYPE_COLLECTOR_FORWARDING) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);//false
                    } else if (action.actionType == TYPE_SET_FORWARDTO) {
                        String forwardto = action.content.replace("设置转发对象：", "").trim();
                        if (TextUtils.isEmpty(forwardto)) {
                            continue;
                        }
                        getSharedPreferences("forwardto", 0).edit().putString("forwardto", forwardto).commit();
                        getSharedPreferences("collector", 0).edit().putString("collector", forwardto).commit();
                        toast("设置转发对象成功");
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (action.actionType == TYPE_SET_FRIEND_CIRCLER_REMARK) {
                        String remark = action.content.replace("设置朋友圈备注：", "").trim();
                        if (TextUtils.isEmpty(remark)) {
                            continue;
                        }
                        getSharedPreferences("FCremark", 0).edit().putString("FCremark", remark).commit();
                        toast("设置朋友圈备注成功");
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (action.actionType == TYPE_REQUEST_FRIEND) {
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, false);
                    } else if (
                            action.actionType == TYPE_GET_ALL_FRIENDS
                                    || action.actionType == TYPE_CLEAR_ZOMBIE_FAN
                                    || action.actionType == TYPE_CREATE_GROUP_CHAT
                                    || action.actionType == TYPE_ADD_GROUP_PEOPLE
                                    || action.actionType == TYPE_DELETE_GROUP_PEOPLE
                                    || action.actionType == TYPE_FIX_GROUP_NAME
                                    || action.actionType == TYPE_FIX_GROUP_NOTICE
                                    || action.actionType == TYPE_GROUP_CHAT
                                    || action.actionType == TYPE_AT_GROUP_PEOPLE
                                    || action.actionType == TYPE_DELETE_MOMENT
                                    || action.actionType == TYPE_ADD_FRIEND
                                    || action.actionType == TYPE_MISSING_FISH
                            ) {
                        //{"cmd": "漏网之鱼","backdoor":true}
                        //{"cmd": "添加朋友","members":["18626318013","13267069058"], "content":"你好，可以加个好友吗？","backdoor":true}
                        //{"cmd": "查询好友数量","backdoor":true}
                        //{"cmd": "清理僵尸粉","start": "1","end":"20","backdoor":true}
                        //{"cmd": "发起群聊","members": ["5行","5之","执着"],"groupName": "111","backdoor":true}
                        //{"cmd": "拉人入群","members": ["5行","5之"],"groupName": "111","backdoor":true}
                        //{"cmd": "踢人出群","members": ["5行","5之"],"groupName": "111","backdoor":true}
                        //{"cmd": "修改群公告","content": "群公告啊啊啊","groupName": "222","backdoor":true}
                        //{"cmd": "修改群名称","content":"1","groupName": "111","backdoor":true}
                        //{"cmd": "群发消息","content":"1","groupName": "111","backdoor":true}
                        //{"cmd": "艾特某人","members": ["执着","朋友圈使者擦"],"groupName": "222","backdoor":true}
                        //{"cmd": "删除朋友圈","content":"密密麻麻","backdoor":true}
                        action.content = Base64.encodeToString(content.getBytes(), NO_WRAP);
                        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
                        sendReplyImmediately(fakeRecv, true);
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
                int actionType = actions.get(currentActionID).actionType;

                if (actionType == TYPE_REQUEST_FRIEND) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "=============findAcceptButton===============");
                            boolean find = findAcceptButton(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        }
                    }, 3000);
                } else if (actionType == TYPE_CLEAR_ZOMBIE_FAN
                        || actionType == TYPE_GET_ALL_FRIENDS
                        || actionType == TYPE_CREATE_GROUP_CHAT
                        || actionType == TYPE_ADD_GROUP_PEOPLE
                        || actionType == TYPE_DELETE_GROUP_PEOPLE
                        || actionType == TYPE_FIX_GROUP_NOTICE
                        || actionType == TYPE_FIX_GROUP_NAME
                        || actionType == TYPE_GROUP_CHAT
                        || actionType == TYPE_AT_GROUP_PEOPLE
                        || actionType == TYPE_DELETE_MOMENT
                        || actionType == TYPE_ADD_FRIEND
                        || actionType == TYPE_MISSING_FISH
                        ) {
                    if (!checkIsWxHomePage()) {
                        boolean backdoor = false;
                        try {
                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                            Log.d("test", "content = " + content);
                            JSONObject o = new JSONObject(content);
                            JSONArray members = o.optJSONArray("members");
                            backdoor = o.optBoolean("backdoor");
                            start = o.optInt("start");
                            end = o.optInt("end");

                            if (backdoor) {
                                if (actionType == TYPE_ADD_GROUP_PEOPLE
                                        || actionType == TYPE_DELETE_GROUP_PEOPLE
                                        || actionType == TYPE_AT_GROUP_PEOPLE
                                        ) {
                                    int count = members.length();
                                    if (count < 0 || count > 100) {
                                        sendTextOnly2("members数量必须是1-100个", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_ADD_FRIEND) {
                                    int count = members.length();
                                    if (count < 0 || count > 100) {
                                        sendTextOnly2("members数量必须是1-100个", true);
                                        return;
                                    }
                                    String notice = o.optString("content");
                                    if (notice.length() > 15) {
                                        sendTextOnly2("请求content太长", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_CREATE_GROUP_CHAT) {
                                    String notice = o.optString("content");
                                    if (TextUtils.isEmpty(notice)) {
                                        sendTextOnly2("群名称不能为空", true);
                                        return;
                                    }
                                    if (notice.length() > 15) {
                                        sendTextOnly2("群名称字数太长", true);
                                        return;
                                    }
                                    int count = members.length();
                                    if (count < 2 || count > 500) {
                                        sendTextOnly2("好友数量必须是2-500个", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_FIX_GROUP_NAME) {
                                    String notice = o.optString("content");
                                    if (TextUtils.isEmpty(notice)) {
                                        sendTextOnly2("群名称不能为空", true);
                                        return;
                                    }
                                    if (notice.length() > 15) {
                                        sendTextOnly2("群名称字数太长", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_FIX_GROUP_NOTICE) {
                                    String notice = o.optString("content");
                                    if (TextUtils.isEmpty(notice)) {
                                        sendTextOnly2("群公告不能为空", true);
                                        return;
                                    }
                                    if (notice.length() > 2000) {
                                        sendTextOnly2("群公告字数太长", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_GROUP_CHAT) {
                                    String notice = o.optString("content");
                                    if (TextUtils.isEmpty(notice)) {
                                        sendTextOnly2("消息内容不能为空", true);
                                        return;
                                    }
                                    if (notice.length() > 5000) {
                                        sendTextOnly2("消息内容字数太长", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_DELETE_MOMENT) {
                                    String notice = o.optString("content");
                                    if (TextUtils.isEmpty(notice)) {
                                        sendTextOnly2("朋友圈关键字不能为空", true);
                                        return;
                                    }
                                } else if (actionType == TYPE_CLEAR_ZOMBIE_FAN) {
                                    int friendCount = Integer.parseInt(Utils.getParentRemark(getApplication()));
                                    if (friendCount < start) {
                                        sendTextOnly2("你输入的命令不正确，当前好友数量是：" + friendCount, true);
                                        return;
                                    }
                                }

                                String text = "";
                                if (actionType == TYPE_ADD_GROUP_PEOPLE) {
                                    text = "正在拉人入群，请稍等";
                                } else if (actionType == TYPE_DELETE_GROUP_PEOPLE) {
                                    text = "正在踢人出群，请稍等";
                                } else if (actionType == TYPE_FIX_GROUP_NAME) {
                                    text = "正在修改群名称，请稍等";
                                } else if (actionType == TYPE_FIX_GROUP_NOTICE) {
                                    text = "正在修改群公告，请稍等";
                                } else if (actionType == TYPE_GROUP_CHAT) {
                                    text = "正在群发消息，请稍等";
                                } else if (actionType == TYPE_AT_GROUP_PEOPLE) {
                                    text = "正在艾特某人，请稍等";
                                } else if (actionType == TYPE_DELETE_MOMENT) {
                                    text = "正在删除朋友圈，请稍等";
                                } else if (actionType == TYPE_ADD_FRIEND) {
                                    text = "正在添加朋友，请稍等";
                                } else if (actionType == TYPE_CREATE_GROUP_CHAT) {
                                    text = "正在创建群组，请稍等";
                                } else if (actionType == TYPE_GET_ALL_FRIENDS) {
                                    text = "正在查询数量，请稍等";
                                } else if (actionType == TYPE_CLEAR_ZOMBIE_FAN) {
                                    text = "正在清理僵尸粉，请稍等";
                                } else if (actionType == TYPE_MISSING_FISH) {
                                    text = "正在添加漏网之鱼，请稍等";
                                }
                                sendTextOnly2(text, false);
                            }
                            boolean finalBackdoor = backdoor;
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            checkIsWxHomePage();
                                            if (actionType == TYPE_GET_ALL_FRIENDS) {
                                                tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        checkIsWxHomePage();
                                                        doFindFriendInListView(finalBackdoor);
                                                    }
                                                }, 3000);
                                            } else if (actionType == TYPE_CREATE_GROUP_CHAT || actionType == TYPE_CLEAR_ZOMBIE_FAN || actionType == TYPE_ADD_FRIEND) {
                                                //首页右上角工具栏
                                                findTopRightToolBarInHomePage();
                                            } else if (actionType == TYPE_DELETE_MOMENT) {
                                                //我-相册
                                                deleteMoment();
                                            } else if (actionType == TYPE_MISSING_FISH) {
                                                //通讯录-新的朋友
                                                addMissingFish();
                                            } else {
                                                //搜索群名
                                                searchSenderInWxHomePage(actionType);
                                            }
                                        }
                                    }, 2000);
                                }
                            }, 3000);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (backdoor) {
                                sendTextOnly2("输入命令有误", true);
                            } else {
                                release();
                            }
                        }
                    }
                } else {
                    //需要容错
                    //1.判断当前是不是首页
                    Log.d("test", "========================checkIsWxHomePage============");
                    if (checkIsWxHomePage()) {
                        //1.如果已经使用过的action，进来会去到首页
                        searchSenderInWxHomePage(1);
                    } else {
                        String targetSender = actions.get(currentActionID).sender;
                        Log.d("test", "checkIsCorrectPage targetSender = " + targetSender);
                        //2.容错判断
                        boolean isCorrect = checkIsCorrectSender(getRootInActiveWindow(), targetSender);
                        Log.d("test", "isCorrect = " + isCorrect);
                        if (isCorrect) {
                            doChatByActionType();
                        } else {
                            backToWxHomePage();
                        }
                    }
                }
                break;
        }
    }

    private boolean adding_missing_fish = false;

    private void addMissingFish() {
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsWxHomePage();
                clickSomeWhere(newFriendTextView);
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
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adding_missing_fish = findAcceptButton_AddedButton(getRootInActiveWindow());
                                    }
                                });
                                sleep(30000);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        }, 3000);
    }

    private boolean findAcceptButton_AddedButton(AccessibilityNodeInfo rootNode) {
        boolean find1 = findAcceptButton(rootNode);
        if (find1) {
            return true;
        }
        boolean find2 = findAddedTextView(rootNode);
        return find2;
    }

    private boolean findAddedTextView(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("已添加")) {
                //长按
                doLongClickNickname(nodeInfo);
                return true;
            }
            if (findAddedTextView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void doLongClickNickname(AccessibilityNodeInfo nodeInfo) {
        nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findDeleteButton3(getRootInActiveWindow());
            }
        }, 3000);
    }

    private void deleteMoment() {
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findAlbum(getRootInActiveWindow());
                if (!find) {
                    release();
                }
            }
        }, 1000);
    }

    private boolean findAlbum(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("相册")) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listViewNode = null;
                        findMomentListView(getRootInActiveWindow());
                        if (listViewNode == null) {
                            release();
                            return;
                        }
                        startFindMoment();
                    }
                }, 2000);
                return true;
            }
            if (findAlbum(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findMoment = false;

    private void startFindMoment() {
        new Thread() {
            @Override
            public void run() {
                try {
                    findMoment = false;
                    int tryCount = 20;//尝试下拉次数
                    resetMaxReleaseTime(tryCount * 10000);

                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    String text = o.optString("content");

                    for (int i = 0; i < tryCount; i++) {
                        if (findMoment) {
                            break;
                        }
                        sleep(5000);
                        //find
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("test", "============findMomentInListView===========");
                                findMoment = findMomentInListView(getRootInActiveWindow(), text);
                                Log.d("test", "findMoment = " + findMoment);
                            }
                        });
                        sleep(5000);
                        if (findMoment) {
                            break;
                        }
                        //scroll
                        String cmd = "input swipe 360 900 360 300";
                        execRootCmdSilent(cmd);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    release();
                }
            }
        }.start();
    }

    private AccessibilityNodeInfo targetNode;

    private boolean findMomentInListView(AccessibilityNodeInfo rootNode, String text) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().contains(text)) {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                //跳页去删除。。。
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //如果是网文，点进去直接删除；
                        //如果是图片，右上角删除；
                        targetNode = null;
                        hasTargetButton(getRootInActiveWindow(), "删除");
                        if (targetNode == null) {
                            lastImageButton = null;
                            findUserInfoButton(getRootInActiveWindow());
                            if (lastImageButton == null) {
                                release();
                                return;
                            }
                            lastImageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findDeleteButton3(getRootInActiveWindow());
                                    if (!find) {
                                        release();
                                    }
                                }
                            }, 2000);
                        } else {
                            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findSureButtonInDialog(getRootInActiveWindow());
                                }
                            }, 2000);
                        }
                    }
                }, 2000);
                return true;
            }
            if (findMomentInListView(nodeInfo, text)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasTargetButton(AccessibilityNodeInfo rootNode, String target) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(target)) {
                targetNode = nodeInfo;
                return true;
            } else if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(target)) {
                targetNode = nodeInfo;
                return true;
            }
            if (hasTargetButton(nodeInfo, target)) {
                return true;
            }
        }
        return false;
    }

    private boolean findMomentListView(AccessibilityNodeInfo rootNode) {
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
            if (findMomentListView(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private Set<String> friends = new HashSet<>();

    private void doFindFriendInListView(boolean backdoor) {
        friends.clear();
        resetMaxReleaseTime(30 * 60 * 1000);
        new Thread() {
            @Override
            public void run() {
                //开始滚动读取
                while (true) {
                    Log.d("test", "==============findFriendView=============");
                    findFriendView(getRootInActiveWindow());
                    //查询是否滚动到底部
                    Log.d("test", "==============checkIsListBottom=============");
                    boolean isBottom = checkIsListBottom(getRootInActiveWindow());
                    Log.d("test", "isBottom = " + isBottom);
                    if (isBottom) {
                        break;
                    }
                    String cmd = "input swipe 360 900 360 300";
                    execRootCmdSilent(cmd);
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                int count = friends.size();
                Log.d("test", "friends.size = " + count);
                FileUtils.saveFile(count + "", "parent.txt");

                if (backdoor) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            searchSenderInWxHomePage(TYPE_GET_ALL_FRIENDS);
                        }
                    }, 2000);
                } else {
                    release();
                }
            }
        }.start();
    }

    private boolean checkIsListBottom(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().endsWith("位联系人")) {
                return true;
            }
            if (checkIsListBottom(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

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
                    continue;
                }
                friends.add(nickname);
            }
            findFriendView(nodeInfo);
        }
    }

    private void findTopRightToolBarInHomePage() {
        checkIsWxHomePage();
        if (lastRelativeLayout == null) {
            release();
            return;
        }
        lastRelativeLayout.performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
                boolean find = findTargetButtonInToolBar(getRootInActiveWindow(), target);
                if (!find) {
                    release();
                }
            }
        }, 2000);
    }

    private boolean findTargetButtonInToolBar(AccessibilityNodeInfo rootNode, String target) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(target)) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int actionType = actions.get(currentActionID).actionType;
                        boolean find = false;
                        if (actionType == TYPE_CREATE_GROUP_CHAT || actionType == TYPE_CLEAR_ZOMBIE_FAN) {
                            listViewNode = null;
                            sureButton = null;
                            find = findFriendListView(getRootInActiveWindow());

                        } else if (actionType == TYPE_ADD_FRIEND) {
                            //在点一下文本框
                            find = findTargetPeople2(getRootInActiveWindow(), "微信号/QQ号/手机号", actionType);
                        }
                        if (!find) {
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findTargetButtonInToolBar(nodeInfo, target)) {
                return true;
            }
        }
        return false;
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

                    resetMaxReleaseTime(20000 * count);

                    for (int i = 0; i < count; i++) {
                        String m = members.getString(i);
                        addFriend(m);
                        sleep(20000);
                    }
                    release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void addFriend(String m) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findInputEditText(getRootInActiveWindow(), m);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetPeople3(getRootInActiveWindow(), m);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                targetNode = null;
                                hasTargetButton(getRootInActiveWindow(), "添加到通讯录");
                                if (targetNode == null) {
                                    //已经是好友,返回即可
                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                } else {
                                    //还不是好友
                                    targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                                JSONObject o = new JSONObject(content);
                                                content = o.optString("content");

                                                //1.申请语句，这里要先点一下删除。
                                                if (!TextUtils.isEmpty(content)) {
                                                    int length = getTextLengthInEditText(1, content);
                                                    for (int i = 0; i < length; i++) {
                                                        String cmd = "input keyevent  " + KeyEvent.KEYCODE_DEL;
                                                        RootCmd.execRootCmdSilent(cmd);
                                                    }
                                                    findInputEditText(getRootInActiveWindow(), content);
                                                }
                                                //2.备注
                                                edittextCount = 0;
                                                findSecondInputEditText(getRootInActiveWindow(), Utils.getParentRemark(getApplicationContext()));
                                                //3.点击发送按钮
                                                findSendImageButton(getRootInActiveWindow(), false);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                    }
                                                }, 3000);
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

    private AccessibilityNodeInfo sureButton;

    private boolean findFriendListView(AccessibilityNodeInfo rootNode) {
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
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int type = actions.get(currentActionID).actionType;
                        if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                            checkFriendInListView1();
                        } else {
                            checkFriendInListView2(type);
                        }
                    }
                }, 2000);
                return true;
            } else if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null) {
                String text = nodeInfo.getText().toString();
                if (text.equals("确定") || text.equals("删除")) {
                    sureButton = nodeInfo;
                }
            }
            if (findFriendListView(nodeInfo)) {
                return true;
            }
        }
        return false;
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
                        String cmd = "input swipe 360 900 360 300";
                        execRootCmdSilent(cmd);
                        sleep(3000);
                    }

                    int scrollCount = (end - start) / 8 + 1;
                    Log.d("test", "scrollCount = " + scrollCount);
                    checkedFriends.clear();
                    for (int i = 0; i < scrollCount; i++) {
                        doCheckFriend1(getRootInActiveWindow());
                        sleep(3000);
                        String cmd = "input swipe 360 900 360 300";
                        execRootCmdSilent(cmd);
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

    private void checkFriendInListView2(int type) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    JSONArray members = o.getJSONArray("members");
                    int count = members.length();

                    resetMaxReleaseTime(10000 * count);

                    checkedFriends.clear();

                    for (int i = 0; i < count; i++) {
                        String temp = members.getString(i);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                findInputEditText(getRootInActiveWindow(), temp);
                            }
                        });
                        Thread.sleep(2000);
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
                        Thread.sleep(2000);
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
                sureButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int type = actions.get(currentActionID).actionType;

                        if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                            boolean find = findZombieFans(getRootInActiveWindow());
                            if (!find) {
                                Log.d("test", "创建失败或者没有僵尸粉");
                                release();
                            }
                        } else if (type == TYPE_CREATE_GROUP_CHAT) {
                            lastImageButton = null;
                            findUserInfoButton(getRootInActiveWindow());
                            if (lastImageButton == null) {
                                release();
                                return;
                            }
                            lastImageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            //找到群聊名称
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findGroupName(getRootInActiveWindow());
                                    if (!find) {
                                        release();
                                    }
                                }
                            }, 2000);
                        } else if (type == TYPE_ADD_GROUP_PEOPLE) {
                            release();
                        } else if (type == TYPE_DELETE_GROUP_PEOPLE) {
                            //这里会弹出一个对话框
                            boolean find = findSureButtonInDialog(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        }
                    }
                }, 5000);
            }
        });
    }

    private boolean findGroupName(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("群聊名称")) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                            Log.d("test", "content = " + content);
                            JSONObject o = new JSONObject(content);
                            String groupName = o.optString("groupName");
                            findInputEditText(getRootInActiveWindow(), groupName);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findSaveButton(getRootInActiveWindow());
                                    if (!find) {
                                        release();
                                    }
                                }
                            }, 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findGroupName(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findSaveButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("保存")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 5000);
                return true;
            }
            if (findSaveButton(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findZombieFans(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().startsWith("你无法邀请未添加你为好友的用户进去群聊，请先向")) {
                String text = nodeInfo.getText().toString().replace("你无法邀请未添加你为好友的用户进去群聊，请先向", "").replace("发送朋友验证申请。对方通过验证后，才能加入群聊。", "");
                String[] temp = text.split("、");
                Log.d("test", "僵尸粉的数量：" + temp.length);
                //返回，通过搜索好友名字，去逐个删除。
                if (temp.length == 0) {
                    toast("僵尸粉数量为0");
                    release();
                }

                resetMaxReleaseTime(60000 * temp.length);

                zombies.clear();
                for (String t : temp) {
                    zombies.add(new Zombie(t, false));
                }

                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doSequeClearZombies();
                    }
                }, 2000);
                return true;
            }
            if (findZombieFans(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private Zombie currentZombie;

    private void doSequeClearZombies() {
        //检查allCleared线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!actioningFlag) {
                        break;
                    }
                    boolean allCleared = true;
                    for (Zombie rm : zombies) {
                        allCleared = allCleared & rm.cleared;
                    }
                    Log.d("test", "allCleared = " + allCleared);
                    if (allCleared) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                release();
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

        new Thread() {
            @Override
            public void run() {
                int count = zombies.size();
                for (int i = 0; i < count; i++) {
                    while (i != 0 && !zombies.get(i - 1).cleared) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    currentZombie = zombies.get(i);
                    doClearZombieFan();
                }
            }
        }.start();
    }

    private void doClearZombieFan() {
        Log.d("test", "doClearZombieFan currentZombie = " + currentZombie.nickname);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                checkIsWxHomePage();
                searchZombieInWxHomePage();
            }
        });
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
            sb.append("你可以发送以下后台命令：\n");
            Iterator<Map.Entry<String, Integer>> it = backdoors.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Integer> entry = it.next();
                sb.append(entry.getKey() + "\n");
            }
        } else if (key.equals(BACK_DOOR2)) {
            int totalActionCount = actions.size();
            int undoCount = zbusRecvs.size() + 1;
            int doneCount = totalActionCount - undoCount;
            sb.append("家长问题总个数：" + totalActionCount + "，\n");
            sb.append("其中已回复个数：" + doneCount + "，\n");
            sb.append("未回复个数：" + undoCount + "。");
        } else if (key.equals(BACK_DOOR3)) {
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
        } else if (actionType == TYPE_FRIEND_CIRCLER) {
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
                    boolean find = findTopRightButton(getRootInActiveWindow(), false);
                    if (!find) {
                        Log.d("test", "找不到右上角按钮");
                        release();
                    }
                }
            }, 10000);//防止页面加载不完整
        } else if (actionType == TYPE_SET_FORWARDTO || actionType == TYPE_SET_FRIEND_CIRCLER_REMARK) {
            sendTextOnly2("设置成功！", true);
        } else if (actionType == TYPE_PUBLIC_ACCONT_FORWARDING || actionType == TYPE_COLLECTOR_FORWARDING) {
            // 找到最后一张链接，点击转发给某人
            if (actionType == TYPE_PUBLIC_ACCONT_FORWARDING) {
                forwardto = getSharedPreferences("forwardto", 0).getString("forwardto", "");
            } else if (actionType == TYPE_COLLECTOR_FORWARDING) {
                forwardto = getSharedPreferences("collector", 0).getString("collector", "我的KW");
            }
            if (TextUtils.isEmpty(forwardto)) {
                toast("您还没有设置转发对象");
                //回复给微信
                sendTextOnly2("您还没有设置转发对象，设置方法：请输入“设置转发对象：昵称”", true);
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
                            sendTextOnly2("个人名片暂时不支持转发", true);
                        }
                    } else if (content.startsWith("[视频]")) {
                        lastMsgView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
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

    private void backToWxHomePage() {
        //先返回首页，再按搜索去做
        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIsWxHomePage();
                searchSenderInWxHomePage(1);
            }
        }, 2000);
    }

    private boolean checkIsCorrectSender(AccessibilityNodeInfo rootNode, String targetSender) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            //equals
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().contains(targetSender)) {
                return true;
            }
            if (checkIsCorrectSender(nodeInfo, targetSender)) {
                return true;
            }
        }
        return false;
    }

    private void searchSenderInWxHomePage(int type) {
        if (lastTextView == null) {
            release();
            return;
        }
        lastTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String sender = "";
                    if (type == TYPE_ADD_GROUP_PEOPLE
                            || type == TYPE_DELETE_GROUP_PEOPLE
                            || type == TYPE_FIX_GROUP_NAME
                            || type == TYPE_FIX_GROUP_NOTICE
                            || type == TYPE_GROUP_CHAT
                            || type == TYPE_AT_GROUP_PEOPLE) {
                        String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                        JSONObject o = new JSONObject(content);
                        sender = o.optString("groupName");
                    } else {
                        sender = actions.get(currentActionID).sender;
                    }

                    boolean find = findInputEditText(getRootInActiveWindow(), sender);
                    Log.d("test", "findInputEditText = " + find);
                    if (!find) {
                        release();
                        return;
                    }
                    String finalSender = sender;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("test", "=============findTargetPeople2==============");
                            boolean find = findTargetPeople2(getRootInActiveWindow(), finalSender, type);
                            if (!find) {
                                Log.d("test", "findTargetPeople2 failure");
                                release();
                            }
                        }
                    }, 3000);
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
        lastTextView = null;
        lastRelativeLayout = null;
        tongxunluTextView = null;
        woTextView = null;
        newFriendTextView = null;
        checkIsWxHomePage(getRootInActiveWindow());
        boolean isWxHomePage = weixin && tongxunlu && faxian && wo;
        Log.d("test", "isWxHomePage = " + isWxHomePage);
        return isWxHomePage;
    }

    private void doSequeSendText() {
        ArrayList<ReturnMessage> returnMessages = actions.get(currentActionID).returnMessages;

        //检查allDone线程
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

        //2.挨个发送线程
        new Thread() {
            @Override
            public void run() {
                int count = returnMessages.size();
                for (int i = 0; i < count; i++) {
                    ReturnMessage rm = returnMessages.get(i);
                    while (i != 0 && !returnMessages.get(i - 1).returnFinished) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    sendTextOnly(rm);
                }
            }
        }.start();
    }

    private boolean weixin;
    private boolean tongxunlu;
    private boolean faxian;
    private boolean wo;
    private AccessibilityNodeInfo lastRelativeLayout;
    private AccessibilityNodeInfo newFriendTextView;
    private AccessibilityNodeInfo tongxunluTextView;
    private AccessibilityNodeInfo woTextView;

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
                    } else if (text.equals("我")) {
                        wo = true;
                        woTextView = nodeInfo.getParent();
                    } else if (text.equals("新的朋友")) {
                        newFriendTextView = nodeInfo;
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
                lastTextView = nodeInfo;
            } else if (nodeInfo.getClassName().equals("android.widget.RelativeLayout")) {
                lastRelativeLayout = nodeInfo;
            }
            checkIsWxHomePage(nodeInfo);
        }
    }

    //1聊天 其他：actionType
    private boolean findTargetPeople2(AccessibilityNodeInfo rootNode, String forwardto, int type) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            //equals
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().contains(forwardto)) {
                Log.d("test", "click targetPeople = " + forwardto);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (type == 1) {
                            //开始聊天
                            doChatByActionType();
                        } else if (type == TYPE_CLEAR_ZOMBIE_FAN) {
                            //清粉
                            lastImageButton = null;
                            findUserInfoButton(getRootInActiveWindow());
                            if (lastImageButton == null) {
                                currentZombie.cleared = true;
                                return;
                            }
                            lastImageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findTargetPeople4(getRootInActiveWindow(), currentZombie.nickname);
                                    if (!find) {
                                        currentZombie.cleared = true;
                                    }
                                }
                            }, 2000);
                        } else if (type == TYPE_ADD_GROUP_PEOPLE || type == TYPE_DELETE_GROUP_PEOPLE || type == TYPE_FIX_GROUP_NAME || type == TYPE_FIX_GROUP_NOTICE) {
                            lastImageButton = null;
                            findUserInfoButton(getRootInActiveWindow());
                            if (lastImageButton == null) {
                                release();
                                return;
                            }
                            lastImageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (type == TYPE_ADD_GROUP_PEOPLE || type == TYPE_DELETE_GROUP_PEOPLE) {
                                        addOrDeleteGroupPeople(type);
                                    } else if (type == TYPE_FIX_GROUP_NOTICE) {
                                        fixGroupNameOrNotice(getRootInActiveWindow(), "群公告");
                                    } else if (type == TYPE_FIX_GROUP_NAME) {
                                        fixGroupNameOrNotice(getRootInActiveWindow(), "群聊名称");
                                    }
                                }
                            }, 2000);
                        } else if (type == TYPE_GROUP_CHAT) {
                            try {
                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                JSONObject o = new JSONObject(content);
                                String text = o.optString("content");
                                sendTextOnly2(text, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (type == TYPE_AT_GROUP_PEOPLE) {
                            //循环开始艾特人
                            startAtPeople();
                        } else if (type == TYPE_GET_ALL_FRIENDS) {
                            sendTextOnly2("好友数量：" + Utils.getParentRemark(getApplication()), true);
                        } else if (type == TYPE_ADD_FRIEND) {
                            clickSomeWhere(nodeInfo);
                            startAddFriend();
                        }
                    }
                }, 3000);
                return true;
            }
            if (findTargetPeople2(nodeInfo, forwardto, type)) {
                return true;
            }
        }
        return false;
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

                    resetMaxReleaseTime(count * 15000);

                    for (int i = 0; i < count; i++) {
                        String m = members.getString(i);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                findInputEditText(getRootInActiveWindow(), "@");
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findSearchButton(getRootInActiveWindow());
                                    }

                                    private boolean findSearchButton(AccessibilityNodeInfo rootNode) {
                                        int count = rootNode.getChildCount();
                                        for (int i = 0; i < count; i++) {
                                            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
                                            if (nodeInfo == null) {
                                                continue;
                                            }
                                            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
                                            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
                                            if (nodeInfo.getClassName().equals("android.widget.TextView")
                                                    && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("选择提醒的人")) {
                                                AccessibilityNodeInfo lastNode = rootNode.getChild(i + 1);
                                                lastNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        findInputEditText(getRootInActiveWindow(), m);
                                                        boolean find = findTargetPeople3(getRootInActiveWindow(), m);
                                                        if (!find) {
                                                            //FXIME 这里最好做检测
                                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                        }
                                                    }
                                                }, 1000);
                                                return true;
                                            }
                                            if (findSearchButton(nodeInfo)) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    }
                                }, 3000);
                            }
                        });
                        sleep(10000);
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendTextOnly2("", false);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String contentStr = o.optString("content");
                                    sendTextOnly2(contentStr, true);
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

    private boolean findTargetPeople3(AccessibilityNodeInfo rootNode, String forwardto) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            //equails
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().contains(forwardto)) {
                Log.d("test", "click targetPeople = " + forwardto);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
            if (findTargetPeople3(nodeInfo, forwardto)) {
                return true;
            }
        }
        return false;
    }

    private boolean fixGroupNameOrNotice(AccessibilityNodeInfo rootNode, String target) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(target)) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //判断是不是已经有了公告
                        try {
                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                            JSONObject o = new JSONObject(content);
                            String text = o.optString("content");
                            boolean has = hasNoticeAlready(getRootInActiveWindow());
                            if (has) {
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //1.先执行删除键
                                        int length = getTextLengthInEditText(1, text);
                                        for (int i = 0; i < length; i++) {
                                            String cmd = "input keyevent  " + KeyEvent.KEYCODE_DEL;
                                            execRootCmdSilent(cmd);
                                        }
                                        //2.再粘贴上content
                                        doFixGroupNameOrNotice(text);
                                    }
                                }, 2000);
                            } else {
                                doFixGroupNameOrNotice(text);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 2000);
                return true;
            }
            if (fixGroupNameOrNotice(nodeInfo, target)) {
                return true;
            }
        }
        return false;
    }

    private void doFixGroupNameOrNotice(String text) {
        findInputEditText(getRootInActiveWindow(), text);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findFinishButton2(getRootInActiveWindow());
                if (!find) {
                    release();
                }
            }
        }, 1000);
    }

    private boolean hasNoticeAlready(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("编辑")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                return true;
            }
            if (hasNoticeAlready(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo editTextNode;

    public int getTextLengthInEditText(int index, String compare) {
        edittextCount = 0;
        editTextNode = null;
        findInputEditText(getRootInActiveWindow(), index);
        if (editTextNode == null) {
            return 0;
        }
        //useless
        //editTextNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
        String text = editTextNode.getText().toString();
        if (text.equals(compare)) {
            return 0;
        }
        int length = text.length();
        Log.d("test", "text = " + text + " length = " + length);
        return length;
    }

    private boolean findInputEditText(AccessibilityNodeInfo rootNode, int index) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.EditText")) {
                edittextCount++;
                if (edittextCount == index) {
                    editTextNode = nodeInfo;
                    return true;
                }
            }
            if (findInputEditText(nodeInfo, index)) {
                return true;
            }
        }
        return false;
    }

    private boolean findFinishButton2(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && (nodeInfo.getText().toString().equals("完成") || nodeInfo.getText().toString().equals("保存"))) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findPublishButtonInDialog(getRootInActiveWindow());
                        if (!find) {
                            release();
                        }
                    }
                }, 5000);
                return true;
            }
            if (findFinishButton2(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findPublishButtonInDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发布")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 2000);
                return true;
            }
            if (findPublishButtonInDialog(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void addOrDeleteGroupPeople(int type) {
        lastImageView = null;
        imageViewIndex = 0;
        int targetIndex = 0;
        if (type == TYPE_ADD_GROUP_PEOPLE) {
            targetIndex = 2;
        } else if (type == TYPE_DELETE_GROUP_PEOPLE) {
            targetIndex = 3;
        }
        findImageView(getRootInActiveWindow(), targetIndex);
        if (lastImageView == null) {
            release();
            return;
        }
        lastImageView.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "================findFriendListView===================");
                listViewNode = null;
                sureButton = null;
                boolean find = findFriendListView(getRootInActiveWindow());
                if (!find) {
                    release();
                }
            }
        }, 2000);
    }

    //android.widget.ImageView
    private int imageViewIndex;

    private boolean findImageView(AccessibilityNodeInfo rootNode, int index) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.ImageView")) {
                imageViewIndex++;
                if (imageViewIndex == index) {
                    lastImageView = nodeInfo;
                }
            }
            if (findImageView(nodeInfo, index)) {
                return true;
            }
        }
        return false;
    }

    private boolean findTargetPeople4(AccessibilityNodeInfo rootNode, String forwardto) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals(forwardto)) {
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTopRightButton(getRootInActiveWindow(), true);
                        if (!find) {
                            currentZombie.cleared = true;
                        }
                    }
                }, 3000);
                return true;
            }
            if (findTargetPeople4(nodeInfo, forwardto)) {
                return true;
            }
        }
        return false;
    }

    private AccessibilityNodeInfo lastImageButton;

    private boolean findUserInfoButton(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                lastImageButton = nodeInfo;
            }
            if (findUserInfoButton(nodeInfo)) {
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
                            //lastMsgView.performAction(AccessibilityNodeInfo.ACTION_CLICK);//无效

                            clickSomeWhere(lastMsgView);

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //3.点一下返回
                                    String content = actions.get(currentActionID).content;
                                    Log.d("test", "content = " + content);
                                    if (content.startsWith("[图片]") || content.startsWith("[链接]") || content.startsWith("[视频]") || content.startsWith("[文件]") || content.startsWith("[位置]") || content.contains("向你推荐了") || isCallingDialog(getRootInActiveWindow())) {
                                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                    }
                                    //4.再次执行长按
                                    doLongClickLastMsgAgain();
                                }
                            }, 2000);//有待查看
                        }
                    }
                }, 2000);
            }
        }, 2000);
    }

    private void clickSomeWhere(AccessibilityNodeInfo node) {
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        // 1.生成点击坐标
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        String cmd = "input tap " + x + " " + y;
        Log.d("test", "cmd = " + cmd);
        // 2.执行su命令
        int ret = execRootCmdSilent(cmd);
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
        }, 2000);
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
    private String lastNickname = "";

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

                if (lastNickname.equals(nickname)) {
                    //重复nickname，执行删除操作
                    doLongClickNickname(nicknameNode);
                    return true;
                }

                lastNickname = nickname;
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        remark = getParentRemark(getApplicationContext());
                        boolean find = findInputEditText(getRootInActiveWindow(), remark);
                        if (find) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("test", "===============findFinishButton==================");
                                    boolean find = findFinishButton(getRootInActiveWindow());
                                    if (!find) {
                                        if (adding_missing_fish) {
                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                        } else {
                                            release();
                                        }
                                    }
                                }
                            }, 2000);
                        } else {
                            if (adding_missing_fish) {
                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                            } else {
                                release();
                            }
                        }
                    }
                }, 3000);
                return true;
            }
            if (findAcceptButton(nodeInfo)) {
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
                            if (adding_missing_fish) {
                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                            } else {
                                release();
                            }
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
                        String welcome = getSharedPreferences("welcome", 0).getString("welcome", DEFAULT_WELCOME);
                        boolean find = findInputEditText(getRootInActiveWindow(), welcome);
                        if (find) {
                            sendTextOnly2(welcome, !adding_missing_fish);
                            String current = System.currentTimeMillis() + "";
                            Utils.uploadFriend(getApplication(), nickname, remark + " " + nickname, current, current);
                        } else {
                            if (adding_missing_fish) {
                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                            } else {
                                release();
                            }
                        }
                    }
                }, 3000);
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
                        boolean find = findSearchEditText(getRootInActiveWindow(), forwardto, false);
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

    private boolean findSearchEditText(AccessibilityNodeInfo rootNode, String forwardto, boolean share) {
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
                        boolean find = findTargetPeople(getRootInActiveWindow(), forwardto, share);
                        if (!find) {
                            Log.d("test", "findTargetPeople failure");
                            release();
                        }
                    }
                }, 2000);
                return true;
            }
            if (findSearchEditText(nodeInfo, forwardto, share)) {
                return true;
            }
        }
        return false;
    }

    private boolean findTargetPeople(AccessibilityNodeInfo rootNode, String forwardto, boolean share) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            //equails
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().contains(forwardto)) {
                Log.d("test", "click targetPeople = " + forwardto);
                AccessibilityNodeInfo parent = nodeInfo.getParent();
                parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                //弹出发送框框
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (share) {
                            //分享
                            Log.d("test", "=========findShareButtonInDialog============");
                            boolean find = findShareButtonInDialog(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        } else {
                            //转发
                            int actionType = actions.get(currentActionID).actionType;
                            if (actionType == TYPE_COLLECTOR_FORWARDING) {
                                //这里要额外做一步，找到文本框并粘贴内容
                                String content = getCollectorForwardingContent();
                                boolean find = findInputEditText(getRootInActiveWindow(), content);
                                if (!find) {
                                    Log.d("test", "粘贴不上，不发过去");
                                    release();
                                    return;
                                }
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("test", "=========findSendButtonInDialog============");
                                    boolean find = findSendButtonInDialog(getRootInActiveWindow());
                                    if (!find) {
                                        release();
                                    }
                                }
                            }, 2000);
                        }
                    }
                }, 3000);
                return true;
            }
            if (findTargetPeople(nodeInfo, forwardto, share)) {
                return true;
            }
        }
        return false;
    }


    private boolean findSureButtonInDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("确定")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 2000);
                return true;
            }
            if (findSureButtonInDialog(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findShareButtonInDialog(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("分享")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 2000);
                return true;
            }
            if (findShareButtonInDialog(nodeInfo)) {
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
                        int actionType = actions.get(currentActionID).actionType;
                        if (actionType == TYPE_COLLECTOR_FORWARDING) {
                            release();
                        } else if (actionType == TYPE_PUBLIC_ACCONT_FORWARDING) {
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
                                    sendTextOnly2(sendContent, true);
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
            //公众号微信发来的文字类：android.view.TextView
            //公众号微信发来的链接：android.widget.LinearLayout
            if (/*nodeInfo.getClassName().equals("android.view.View") ||*/ nodeInfo.getClassName().equals("android.widget.FrameLayout")) {
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

    private boolean findTopRightButton(AccessibilityNodeInfo rootNode, boolean clearZombie) {
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
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clearZombie) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("test", "===============findDeleteButton1================");
                                    boolean find = findDeleteButton1(getRootInActiveWindow());
                                    if (!find) {
                                        currentZombie.cleared = true;
                                    }
                                }
                            }, 2000);
                        } else {
                            secondLinearLayout = 0;
                            boolean find = findShareButton(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        }
                    }
                }, 2000);
                return true;
            }
            if (findTopRightButton(nodeInfo, clearZombie)) {
                return true;
            }
        }
        return false;
    }

    private boolean findDeleteButton1(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("删除")) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "===============findDeleteButton2================");
                        boolean find = findDeleteButton2(getRootInActiveWindow());
                        if (!find) {
                            currentZombie.cleared = true;
                        }
                    }
                }, 2000);
                return true;
            }
            if (findDeleteButton1(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findDeleteButton2(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("删除")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentZombie.cleared = true;
                    }
                }, 3000);
                return true;
            }
            if (findDeleteButton2(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findDeleteButton3(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("删除")) {
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                if (!adding_missing_fish) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("test", "===============findDeleteButton4================");
                            boolean find = findDeleteButton4(getRootInActiveWindow());
                            if (!find) {
                                release();
                            }
                        }
                    }, 2000);
                }
                return true;
            }
            if (findDeleteButton3(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private boolean findDeleteButton4(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
            if (nodeInfo.getClassName().equals("android.widget.Button") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("确定")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        release();
                    }
                }, 3000);
                return true;
            }
            if (findDeleteButton4(nodeInfo)) {
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
                        boolean find = findSendImageButton(getRootInActiveWindow(), true);
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

    private void sendTextOnly(ReturnMessage rm) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "sendTextOnly is called");
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                boolean find = findInputEditText(rootNode, rm.content);
                Log.d("test", "findInputEditText = " + find);
                if (!find) {
                    rm.returnFinished = true;
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
                        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("发送");
                        if (list != null && list.size() > 0) {
                            for (AccessibilityNodeInfo n : list) {
                                if (n.getClassName().equals("android.widget.Button") && n.isEnabled()) {
                                    n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rm.returnFinished = true;
                            }
                        }, 1500);
                    }
                }, 1500);
            }
        }, 1500);
    }

    private void sendTextOnly2(String reply, boolean release) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "sendTextOnly is called");
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                boolean find = findInputEditText(rootNode, reply);
                Log.d("test", "findInputEditText = " + find);
                if (!find) {
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
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

    private boolean findInputEditText(AccessibilityNodeInfo rootNode, String reply) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if ("android.widget.EditText".equals(nodeInfo.getClassName())) {
                if (!TextUtils.isEmpty(reply)) {
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
                }
                return true;
            }
            if (findInputEditText(nodeInfo, reply)) {
                return true;
            }
        }
        return false;
    }

    private int edittextCount;

    private boolean findSecondInputEditText(AccessibilityNodeInfo rootNode, String reply) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if ("android.widget.EditText".equals(nodeInfo.getClassName())) {
                edittextCount++;
                if (edittextCount != 2) {
                    continue;
                }
                if (!TextUtils.isEmpty(reply)) {
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
                }
                return true;
            }
            if (findSecondInputEditText(nodeInfo, reply)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onInterrupt() {

    }

    //---------------发送图片---------------------------------------

    public String saveImage2(Context context, Bitmap bmp) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String localPath = file.getAbsolutePath();
        return localPath;
    }

    public void saveImage(Context context, Bitmap bmp) {
        if (bmp == null) {
            return;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertDB(context, file);
    }

    private void insertDB(Context context, File file) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    private void sendImageOnly2(String url) {
        //1.下载图片
        Bitmap bmp = ImageLoader.getInstance().loadImageSync(url, KWApplication.getLoaderOptions());
        if (bmp == null) {
            release();
            return;
        }
        //2.保存图片
        String localPath = saveImage2(getApplication(), bmp);
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

        doShareToWechat();
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

        doShareToWechat();
    }

    private void sendFileOnly2(String url, String fileName) {
        //int index = url.lastIndexOf("/");
        //String fileName = url.substring(index + 1);
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

                doShareToWechat();
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
                    localPath = saveImage2(getApplication(), bmp);
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

            doShareToWechat();
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
            if (title.equals("title") && url.equals("url")) {
                String[] imageArray = imageUrl.replace("[", "").replace("]", "").split(",");
                //图文
                ArrayList<Uri> imageUris = new ArrayList<>();
                for (int i = 0; i < imageArray.length; i++) {
                    String image = imageArray[i].replace("\"", "");
                    Log.d("test", "image = " + image);
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(image, KWApplication.getLoaderOptions());
                    if (bmp != null) {
                        String localPath = saveImage2(getApplication(), bmp);
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
                        localPath = saveImage2(getApplication(), bmp);
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
                    findInputEditText(getRootInActiveWindow(), remark);
                    delay = 2000;
                }
                //2.查找发送按钮并点击
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findSendImageButton(getRootInActiveWindow(), true);
                        if (!find) {
                            release();
                        }
                    }
                }, delay);
            }
        }, 4000);
    }

    private void doShareToWechat() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String sender = actions.get(currentActionID).sender;
                //找文本框
                Log.d("test", " =================findSearchEditText===============");
                boolean find = findSearchEditText(getRootInActiveWindow(), sender, true);
                if (!find) {
                    Log.d("test", "findSearchEditText失败");
                    release();
                }
            }
        }, 4000);
    }

    private boolean findSendImageButton(AccessibilityNodeInfo rootNode, boolean release) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo = " + nodeInfo.getClassName());
            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("发送")) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                if (release) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            release();
                        }
                    }, 3000);
                }
                return true;
            }
            if (findSendImageButton(nodeInfo, release)) {
                return true;
            }
        }
        return false;
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

    private void searchZombieInWxHomePage() {
        if (lastTextView == null) {
            currentZombie.cleared = true;
            return;
        }
        lastTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String nickname = currentZombie.nickname;
                boolean find = findInputEditText(getRootInActiveWindow(), nickname);
                Log.d("test", "findInputEditText = " + find);
                if (!find) {
                    currentZombie.cleared = true;
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("test", "=============findTargetPeople2==============");
                        boolean find = findTargetPeople2(getRootInActiveWindow(), nickname, TYPE_CLEAR_ZOMBIE_FAN);
                        if (!find) {
                            Log.d("test", "findTargetPeople2 failure");
                            currentZombie.cleared = true;
                        }
                    }
                }, 3000);
            }
        }, 3000);
    }

    public boolean test(AccessibilityNodeInfo rootNode) {
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
//            if (nodeInfo.getClassName().equals("android.widget.TextView") && nodeInfo.getText() != null && nodeInfo.getText().toString().equals("新的朋友")) {
//                clickSomeWhere(nodeInfo);
//                return true;
//            }
            if (test(nodeInfo)) {
                return true;
            }
        }
        return false;
    }
}
