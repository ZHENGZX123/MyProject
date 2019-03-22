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
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rabbitmq.client.Channel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.DensityUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Command;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.Moment;
import cn.kiway.robot.entity.ReturnMessage;
import cn.kiway.robot.entity.Script;
import cn.kiway.robot.entity.ZbusRecv;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.FileUtils;
import cn.kiway.robot.util.MyListener;
import cn.kiway.robot.util.SuUtil;
import cn.kiway.robot.util.UploadUtil;
import cn.kiway.robot.util.Utils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.util.Base64.NO_WRAP;
import static cn.kiway.robot.KWApplication.DCIM;
import static cn.kiway.robot.KWApplication.WEIXIN;
import static cn.kiway.robot.entity.Action.TYPE_ADD_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_ADD_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_ADD_PUBLIC_ACCOUNT;
import static cn.kiway.robot.entity.Action.TYPE_AT_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_AUTO_MATCH;
import static cn.kiway.robot.entity.Action.TYPE_BACK_DOOR;
import static cn.kiway.robot.entity.Action.TYPE_BROWSER_MESSAGE;
import static cn.kiway.robot.entity.Action.TYPE_CARD;
import static cn.kiway.robot.entity.Action.TYPE_CHAT_IN_GROUP;
import static cn.kiway.robot.entity.Action.TYPE_CHECK_MOMENT;
import static cn.kiway.robot.entity.Action.TYPE_CHECK_NEW_VERSION;
import static cn.kiway.robot.entity.Action.TYPE_CLEAR_CHAT_HISTORY;
import static cn.kiway.robot.entity.Action.TYPE_COLLECTOR_FORWARDING;
import static cn.kiway.robot.entity.Action.TYPE_CREATE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_GROUP_CHAT;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_GROUP_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_DELETE_MOMENT;
import static cn.kiway.robot.entity.Action.TYPE_FILE;
import static cn.kiway.robot.entity.Action.TYPE_FIX_FRIEND_NICKNAME;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NAME;
import static cn.kiway.robot.entity.Action.TYPE_FIX_GROUP_NOTICE;
import static cn.kiway.robot.entity.Action.TYPE_FIX_ICON;
import static cn.kiway.robot.entity.Action.TYPE_FIX_NICKNAME;
import static cn.kiway.robot.entity.Action.TYPE_GROUP_QRCODE;
import static cn.kiway.robot.entity.Action.TYPE_IMAGE;
import static cn.kiway.robot.entity.Action.TYPE_INTERACT_MOMENT;
import static cn.kiway.robot.entity.Action.TYPE_LINK;
import static cn.kiway.robot.entity.Action.TYPE_MISSING_FISH;
import static cn.kiway.robot.entity.Action.TYPE_NEARBY_PEOPLE;
import static cn.kiway.robot.entity.Action.TYPE_NOTIFY_RESULT;
import static cn.kiway.robot.entity.Action.TYPE_REQUEST_FRIEND;
import static cn.kiway.robot.entity.Action.TYPE_SAVE_GROUP;
import static cn.kiway.robot.entity.Action.TYPE_SCRIPT;
import static cn.kiway.robot.entity.Action.TYPE_SEARCH_PUBLIC_ACCOUNT;
import static cn.kiway.robot.entity.Action.TYPE_SEND_BATCH;
import static cn.kiway.robot.entity.Action.TYPE_SET_COLLECTOR;
import static cn.kiway.robot.entity.Action.TYPE_TEXT;
import static cn.kiway.robot.entity.Action.TYPE_TRANSFER_MASTER;
import static cn.kiway.robot.entity.Action.TYPE_UPDATE_BASEDATA;
import static cn.kiway.robot.entity.Action.TYPE_VIDEO;
import static cn.kiway.robot.entity.AddFriend.STATUS_ADDING;
import static cn.kiway.robot.entity.AddFriend.STATUS_ADD_SUCCESS;
import static cn.kiway.robot.entity.AddFriend.STATUS_NOT_EXISTED;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_0;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_2;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_3;
import static cn.kiway.robot.util.Constant.ADD_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.AT_PERSONS_CMD;
import static cn.kiway.robot.util.Constant.AUTO_REPLY_CONTENT_CMD;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.BROWSE_MESSAGE_CMD;
import static cn.kiway.robot.util.Constant.CHAT_IN_GROUP_CMD;
import static cn.kiway.robot.util.Constant.CHECK_MOMENT_CMD;
import static cn.kiway.robot.util.Constant.CLEAR_CHAT_HISTORY_CMD;
import static cn.kiway.robot.util.Constant.CLICK_NONE;
import static cn.kiway.robot.util.Constant.CLICK_PARENT;
import static cn.kiway.robot.util.Constant.CLICK_SELF;
import static cn.kiway.robot.util.Constant.CREATE_GROUP_CHAT_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_BACKUP;
import static cn.kiway.robot.util.Constant.DEFAULT_BUSY;
import static cn.kiway.robot.util.Constant.DEFAULT_OFFLINE;
import static cn.kiway.robot.util.Constant.DEFAULT_RELEASE_TIME;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME;
import static cn.kiway.robot.util.Constant.DELETE_BASEDATA_CMD;
import static cn.kiway.robot.util.Constant.DELETE_FRIEND_CIRCLE_CMD;
import static cn.kiway.robot.util.Constant.DELETE_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.FLAG_ACTION;
import static cn.kiway.robot.util.Constant.FLAG_PREACTION;
import static cn.kiway.robot.util.Constant.GROUP_QRCODE_CMD;
import static cn.kiway.robot.util.Constant.INSERT_BASEDATA_CMD;
import static cn.kiway.robot.util.Constant.INTERACT_MOMENT_CMD;
import static cn.kiway.robot.util.Constant.INVITE_GROUP_CMD;
import static cn.kiway.robot.util.Constant.MAX_FRIENDS;
import static cn.kiway.robot.util.Constant.NODE_BUTTON;
import static cn.kiway.robot.util.Constant.NODE_CHECKBOX;
import static cn.kiway.robot.util.Constant.NODE_EDITTEXT;
import static cn.kiway.robot.util.Constant.NODE_FRAMELAYOUT;
import static cn.kiway.robot.util.Constant.NODE_IMAGEBUTTON;
import static cn.kiway.robot.util.Constant.NODE_IMAGEVIEW;
import static cn.kiway.robot.util.Constant.NODE_LINEARLAYOUT;
import static cn.kiway.robot.util.Constant.NODE_RADIOBUTTON;
import static cn.kiway.robot.util.Constant.NODE_RELATIVELAYOUT;
import static cn.kiway.robot.util.Constant.NODE_TEXTVIEW;
import static cn.kiway.robot.util.Constant.NOTIFY_RESULT_CMD;
import static cn.kiway.robot.util.Constant.REMOVE_WODI_CMD;
import static cn.kiway.robot.util.Constant.ROLE_KEFU;
import static cn.kiway.robot.util.Constant.ROLE_WODI;
import static cn.kiway.robot.util.Constant.SAVE_GROUP_CMD;
import static cn.kiway.robot.util.Constant.SEND_FRIEND_CIRCLE_CMD;
import static cn.kiway.robot.util.Constant.SET_WODI_CMD;
import static cn.kiway.robot.util.Constant.TICK_PERSON_GROUP_CMD;
import static cn.kiway.robot.util.Constant.TRANSFER_MASTER_CMD;
import static cn.kiway.robot.util.Constant.UPDATE_BASEDATA_CMD;
import static cn.kiway.robot.util.Constant.UPDATE_FRIEND_NICKNAME_CMD;
import static cn.kiway.robot.util.Constant.UPDATE_GROUP_NAME_CMD;
import static cn.kiway.robot.util.Constant.UPDATE_GROUP_NOTICE_CMD;
import static cn.kiway.robot.util.Constant.UPGRADE_CMD;
import static cn.kiway.robot.util.Constant.backdoors;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.Constant.replies;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static cn.kiway.robot.util.Utils.channels;
import static cn.kiway.robot.util.Utils.getParentRemark;
import static cn.kiway.robot.util.Utils.getWxDBFile;
import static cn.kiway.robot.util.Utils.initDbPassword;
import static cn.kiway.robot.util.Utils.rabbitMQUtils;
import static java.lang.System.currentTimeMillis;

public class AutoReplyService extends AccessibilityService {

    public static int MSG_ACTION_TIMEOUT = 1;
    public static int MSG_INSERT_QUEUE = 2;
    public static int MSG_INSERT_RENAME_QUEUE = 3;
    public static int MSG_TRAVERSAL_QUEUE = 4;
    public static int MSG_SEND_HEARTBEAT = 5;
    public static int MSG_CHECK_HEARTBEAT = 6;

    public static AutoReplyService instance;


    //事件map
    public LinkedHashMap<Long, Action> actions = new LinkedHashMap<>();
    //当前执行的事件id
    private long currentActionID = -1;
    private boolean actioningFlag;

    //没有拉起-分配的intents
    private ArrayList<Action> preActionsQueue = new ArrayList<>();
    //zbus接收队列
    public ArrayList<ZbusRecv> zbusRecvsQueue = new ArrayList<>();
    //重命名用队列
    public ArrayList<ZbusRecv> renameTasksQueue = new ArrayList<>();

    //当前微信页面
    private String currentWechatPage;

    //清理僵尸粉
    private Set<String> checkedFriends = new HashSet<>();//已经勾选的好友
    private int start;
    private int end;

    //附近的人
    private ArrayList<AccessibilityNodeInfo> peoples = new ArrayList<>();

    //群二维码
    private String qrCodeUrl;

    private final Object object = new Object();

    //trycount
    private HashMap<Integer, Integer> tryCounts = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("maptrix", "service oncreate");
        instance = this;
        if (rabbitMQUtils == null) {
            Utils.installationPush(this);
        }
        mHandler.sendEmptyMessage(MSG_TRAVERSAL_QUEUE);
        mHandler.sendEmptyMessageDelayed(MSG_SEND_HEARTBEAT, 10 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_CHECK_HEARTBEAT, 5 * 60 * 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_ACTION_TIMEOUT) {
                release(false);
                return;
            }
            if (msg.what == MSG_INSERT_QUEUE) {
                ZbusRecv recv = (ZbusRecv) msg.obj;
                boolean insertToHead = (msg.arg1 == 1);
                if (insertToHead) {
                    zbusRecvsQueue.add(0, recv);
                } else {
                    zbusRecvsQueue.add(recv);
                }
                return;
            }
            if (msg.what == MSG_INSERT_RENAME_QUEUE) {
                ZbusRecv recv = (ZbusRecv) msg.obj;
                renameTasksQueue.add(recv);
                return;
            }
            if (msg.what == MSG_TRAVERSAL_QUEUE) {
                mHandler.removeMessages(MSG_TRAVERSAL_QUEUE);
                mHandler.sendEmptyMessageDelayed(MSG_TRAVERSAL_QUEUE, 3000);
                if (!getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    return;
                }
                if (currentActionID != -1) {
                    return;
                }

                //1.消息分发预处理队列
                if (preActionsQueue.size() > 0) {
                    Log.d("test", "preActionsQueue size = " + preActionsQueue.size());
                    previewAction(preActionsQueue.remove(0));
                    return;
                }
                //2.事件队列
                if (zbusRecvsQueue.size() > 0) {
                    Log.d("test", "zbusRecvsQueue size = " + zbusRecvsQueue.size());
                    ZbusRecv ze = zbusRecvsQueue.remove(0);
                    Utils.keepLog(ze.toString(), "LAST_EVENT", 0);
                    handleZbusMsg(ze);

                    return;
                }
                //3.重命名队列
                if (renameTasksQueue.size() > 0) {
                    Log.d("test", "renameTasksQueue size = " + renameTasksQueue.size());
                    handleZbusMsg(renameTasksQueue.remove(0));
                }
                return;
            }
            if (msg.what == MSG_SEND_HEARTBEAT) {
                sendMsgToServer("心跳测试使者", "heartbeat", null, TYPE_TEXT);
                mHandler.removeMessages(MSG_SEND_HEARTBEAT);
                mHandler.sendEmptyMessageDelayed(MSG_SEND_HEARTBEAT, 10 * 60 * 1000);
                return;
            }
            if (msg.what == MSG_CHECK_HEARTBEAT) {
                mHandler.removeMessages(MSG_CHECK_HEARTBEAT);
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_HEARTBEAT, 25 * 60 * 1000);
                if (Utils.hbReply) {
                    //3次都收到了心跳回复
                    Utils.hbReply = false;
                } else {
                    //3次都没有收到心跳回复
                    if (MainActivity.instance != null) {
                        MainActivity.instance.doReConnect();
                    }
                }
                return;
            }
        }
    };

    //由于微信的问题，打开的可能是首页，所以该方法可能会有遗漏消息
    private void previewAction(final Action action) {
        currentActionID = FLAG_PREACTION;
        try {
            action.intent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        //如果这个时候刚好打开聊天页面，send打开的就是微信主页，就无法判断出是来自个人还是群。
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是来自个人还是群组
                String clientGroupId = "";
                findTargetNode(NODE_TEXTVIEW, 1);
                if (mFindTargetNode != null) {
                    CharSequence cs = mFindTargetNode.getText();
                    if (cs != null) {
                        String title = cs.toString();
                        clientGroupId = isFromPrivateOrGroup(title);
                    }
                }

                Log.d("test", "isFromPrivateOrGroup clientGroupId = " + clientGroupId);
                action.clientGroupId = clientGroupId;

                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
                startActivity(intent);

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dispatchAction(action);
                        currentActionID = -1;
                    }
                }, 3000);
            }
        }, 5000);
    }

    private void launchWechat(long id, long maxReleaseTime) {
        //0823FIX不再使用pendingIntent
        //0827只有图片使用pendingIntent
        currentActionID = id;
        if (actions.get(currentActionID).actionType == TYPE_COLLECTOR_FORWARDING) {
            try {
                actions.get(currentActionID).intent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        } else {
            Intent i = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            startActivity(i);
        }
        if (maxReleaseTime < DEFAULT_RELEASE_TIME) {
            maxReleaseTime = DEFAULT_RELEASE_TIME;
        }
        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, maxReleaseTime);
    }

    //处理后台过来的命令：
    public void handleZbusMsg(final ZbusRecv recv) {
        Log.d("test", "handleZbusMsg msg = " + recv.msg);
        currentActionID = FLAG_ACTION;

        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject o = new JSONObject(recv.msg);
                    if (o.has("cmd")) {
                        Command command = new Command();
                        command.cmd = o.optString("cmd");
                        command.id = o.optString("id");
                        if (o.has("content") && !command.cmd.equals(AT_PERSONS_CMD) && !command.cmd.equals(INTERACT_MOMENT_CMD)) {
                            command.content = o.optString("content");
                            if (TextUtils.isEmpty(command.content)) {
                                command.content = o.optJSONObject("content").toString();
                            }
                        } else {
                            command.content = Base64.encodeToString(recv.msg.getBytes(), NO_WRAP);
                        }
                        //0718新增
                        command.original = Base64.encodeToString(recv.msg.getBytes(), NO_WRAP);
                        doActionCommand(recv.msg, command);
                        return;
                    }
                    if (o.has("clientGroupId")) {
                        //在群里发消息，这个没有cmd，无语。。。
                        //这里校验，导致了时间超过2000
                        Command command = new Command();
                        command.cmd = CHAT_IN_GROUP_CMD;
                        command.content = Base64.encodeToString(o.toString().getBytes(), NO_WRAP);
                        doActionCommand(o.toString(), command);
                        return;
                    }
                    JSONArray returnMessage = o.getJSONArray("returnMessage");

                    //0823FIX 如果没有对应action，重新new一个
                    Long key = o.optLong("id");
                    Action action = actions.get(key);
                    if (action == null) {
                        Log.d("test", "action is null , create one action");
                        action = new Action();
                        key = System.currentTimeMillis();
                        action.sender = o.optString("sender");
                        action.content = o.optString("content");
                        action.actionType = TYPE_TEXT;
                        action.command = null;
                        action.indexs = o.optInt("indexs");
                        actions.put(key, action);
                    }

                    String busyStr = getSharedPreferences("busy", 0).getString("busy", DEFAULT_BUSY);
                    String offlineStr = getSharedPreferences("offline", 0).getString("offline", DEFAULT_OFFLINE);
                    if (action.replied && recv.msg.contains("\"returnType\":1") && (recv.msg.contains(busyStr) || recv.msg.contains(offlineStr))) {
                        Log.d("test", "action.replied");
                        currentActionID = -1;
                        return;
                    }
                    doHandleZbusMsg(key, action, returnMessage, recv.realReply);
                } catch (Exception e) {
                    e.printStackTrace();
                    currentActionID = -1;
                }
            }
        }.start();
    }

    //处理后台各种操作命令
    private void doActionCommand(String msg, Command command) {
        if (command.cmd.equals(UPGRADE_CMD)) {
            MainActivity.instance.checkNewVersion(null);
            updateStatus3ByMsg(msg);
            currentActionID = -1;
            return;
        }
        if (command.cmd.equals(UPDATE_BASEDATA_CMD) || command.cmd.equals(INSERT_BASEDATA_CMD) || command.cmd.equals(DELETE_BASEDATA_CMD)) {
            MainActivity.instance.getBaseData();
            updateStatus3ByMsg(msg);
            currentActionID = -1;
            return;
        }
        if (command.cmd.equals(SET_WODI_CMD) || command.cmd.equals(REMOVE_WODI_CMD)) {
            updateStatus3ByMsg(msg);
            currentActionID = -1;
            sendMsgToServer2(200, command);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MainActivity.instance.getAllWodis();
                }
            }.start();
            return;
        }
        if (command.cmd.equals(AUTO_REPLY_CONTENT_CMD)) {
            updateStatus3ByMsg(msg);
            currentActionID = -1;
            try {
                String busyContent = new JSONObject(msg).optString("busyContent");
                String offlineContent = new JSONObject(msg).optString("offlineContent");
                getSharedPreferences("busy", 0).edit().putString("busy", busyContent).commit();
                getSharedPreferences("offline", 0).edit().putString("offline", offlineContent).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendMsgToServer2(200, command);
            return;
        }

        Long key = System.currentTimeMillis();
        Action action = new Action();
        actions.put(key, action);
        action.sender = "";
        action.content = Base64.encodeToString(msg.getBytes(), NO_WRAP);
        action.actionType = backdoors.get(command.cmd);
        action.command = command;

        if (command.cmd.equals(SEND_FRIEND_CIRCLE_CMD)) {
            mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, DEFAULT_RELEASE_TIME);
            currentActionID = key;
            actioningFlag = true;
            String original = new String(Base64.decode(command.original.getBytes(), NO_WRAP));
            shareToWechatMoments(command.id, command.content, original);
            return;
        }
        doHandleZbusMsg(key, action, new JSONArray(), false);
    }

    private void updateStatus3ByMsg(String msg) {
        try {
            int indexs = new JSONObject(msg).optInt("indexs");
            new MyDBHelper(getApplicationContext()).updateServerMsgStatusByIndex(indexs, ACTION_STATUS_3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doHandleZbusMsg(final long id, final Action action, final JSONArray returnMessage, boolean realReply) {
        if (realReply) {
            action.replied = true;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "开始执行action = " + id);
                try {
                    int size = returnMessage.length();
                    action.returnMessages.clear();
                    int imageCount = 0;
                    int linkCount = 0;
                    int videoCount = 0;
                    int fileCount = 0;
                    for (int i = 0; i < size; i++) {
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
                    }
                    long maxReleaseTime = action.returnMessages.size() * 8000;
                    Log.d("test", "imageCount = " + imageCount + " fileCount = " + fileCount + " videoCount = " + videoCount + " linkCount = " + linkCount);

                    if (imageCount > 0) {
                        //图片
                        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, DEFAULT_RELEASE_TIME);
                        currentActionID = id;
                        actioningFlag = true;
                        new Thread() {
                            @Override
                            public void run() {
                                sendImageOnly(action.returnMessages.get(0).content, false);
                            }
                        }.start();
                    }
                    if (videoCount > 0) {
                        //  视频
                        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, DEFAULT_RELEASE_TIME);
                        currentActionID = id;
                        actioningFlag = true;
                        sendVideoOnly();
                    } else if (fileCount > 0) {
                        //文件
                        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, DEFAULT_RELEASE_TIME);
                        currentActionID = id;
                        actioningFlag = true;
                        sendFileOnly(action.returnMessages.get(0).content, action.returnMessages.get(0).fileName, false);
                    } else if (linkCount > 0) {
                        //链接
                        mHandler.sendEmptyMessageDelayed(MSG_ACTION_TIMEOUT, DEFAULT_RELEASE_TIME);
                        currentActionID = id;
                        actioningFlag = true;
                        new Thread() {
                            @Override
                            public void run() {
                                sendLinkOnly(action.returnMessages.get(0).content, false);
                            }
                        }.start();
                    } else {
                        //文字，直接拉起微信即可；
                        launchWechat(id, maxReleaseTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    currentActionID = -1;
                }
            }
        });
    }

    //家长发来的消息，发给服务器
    public void sendMsgToServer(final String sender, final String content, final String saved, final int type) {
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer");
                try {
                    String name = getSharedPreferences("kiway", 0).getString("name", "");
                    String installationId = getSharedPreferences("kiway", 0).getString("installationId", "");
                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");

                    String msg = new JSONObject()
                            .put("id", System.currentTimeMillis())
                            .put("sender", sender)
                            .put("content", content)
                            .put("type", type)
                            .put("me", name)
                            .put("areaCode", areaCode)
                            .toString();

                    //topic : robotId#wxNo
                    String topic = robotId + "#" + wxNo;
                    String url = Constant.host + ":" + Constant.port;
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

                    Log.d("test", "sendMsgToServer topic = " + topic + " , msg = " + msg + ", url = " + url);

                    boolean sendSuccess = doSendMessageToServer(topic, pushMessageVo);
                    if (sendSuccess) {
                        if (sender.equals("心跳测试使者")) {
                            Utils.updateRobotStatus(MainActivity.instance, 1);
                        } else {
                            new MyDBHelper(getApplication()).addMessage(sender, saved, System.currentTimeMillis() + "");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (sender.equals("心跳测试使者")) {
                        Utils.updateRobotStatus(MainActivity.instance, 2);
                    }
                }
            }
        }.start();
    }

    //后台操作命令，执行完后的回复
    public synchronized void sendMsgToServer2(final int statusCode, final Command command) {
        if (command.cmd.equals(CHAT_IN_GROUP_CMD) || command.cmd.equals(CHECK_MOMENT_CMD)
                || command.cmd.equals(CLEAR_CHAT_HISTORY_CMD) || command.cmd.equals(NOTIFY_RESULT_CMD) || command.cmd.equals(BROWSE_MESSAGE_CMD)) {
            try {
                String content = new String(Base64.decode(command.content.getBytes(), NO_WRAP));
                JSONObject o = new JSONObject(content);
                int indexs = o.optInt("indexs");
                new MyDBHelper(this).updateServerMsgStatusByIndex(indexs, ACTION_STATUS_3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer2");
                try {
                    String topic = "kiway_wx_reply_result_react";
                    String content;
                    if (command.cmd.equals(SEND_FRIEND_CIRCLE_CMD) || command.cmd.equals(DELETE_FRIEND_CIRCLE_CMD)) {
                        content = new String(Base64.decode(command.original.getBytes(), NO_WRAP));
                    } else {
                        content = new String(Base64.decode(command.content.getBytes(), NO_WRAP));
                    }
                    JSONObject o = new JSONObject(content);
                    int index = o.optInt("indexs");
                    o.put("cmd", replies.get(command.cmd));
                    o.put("type", replies.get(command.cmd));
                    o.put("statusCode", statusCode);
                    o.put("robotId", getSharedPreferences("kiway", 0).getString("robotId", ""));

                    if (command.cmd.equals(ADD_FRIEND_CMD)) {
                        sendFriendInfoDelay();
                    } else if (command.cmd.equals(UPDATE_FRIEND_NICKNAME_CMD)) {
                        if (o.optBoolean("fromFront") && !o.optBoolean("isTransfer")) {
                            //修改昵称后上报
                            Friend f = new Friend(o.optString("nickname"), o.optString("newName"), o.optString("wxId"), o.optString("wxNo"));
                            ArrayList<Friend> updateFriends = new ArrayList<>();
                            updateFriends.add(f);
                            Utils.updateFriendRemark(MainActivity.instance, updateFriends);
                        } else {
                            //后台发来的参数缺少wxId、wxNo
                            sendFriendInfoDelay();
                        }
                    } else if (command.cmd.equals(DELETE_FRIEND_CMD)) {
                        JSONArray members = new JSONArray();
                        JSONArray recvMembers = new JSONObject(content).optJSONArray("members");
                        int count = recvMembers.length();
                        for (int i = 0; i < count; i++) {
                            String remark = recvMembers.getString(i);
                            JSONObject m = new JSONObject();
                            m.put("phone", Utils.getPhoneFromRemark(getApplication(), remark));
                            m.put("remark", remark);
                            members.put(m);
                        }
                        o.put("members", members);
                        sendFriendInfoDelay();
                    } else if (command.cmd.equals(INTERACT_MOMENT_CMD)) {
                        o.put("createDate", System.currentTimeMillis() / 1000);
                        o.put("content", new JSONObject(content).optString("replyContent"));
                        String name = getSharedPreferences("kiway", 0).getString("name", "");
                        o.put("author", name);
                        o.put("toUser", new JSONObject(content).optString("author"));
                    } else if (command.cmd.equals(CREATE_GROUP_CHAT_CMD)) {
                        // 创建群之后，微信数据库延时问题。。。
                        String groupName = o.optString("name");
                        Group g = getOneGroupFromWechatDB_ClientGroupName(groupName);
                        if (g != null) {
                            o.put("clientGroupId", g.clientGroupId);
                        }
                        String wxId = getSharedPreferences("kiway", 0).getString("wxId", "");
                        String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                        o.put("master", wxId);
                        o.put("masterWxNo", wxNo);
                        sendGroupInfoDelay(true);
                    } else if (command.cmd.equals(UPDATE_GROUP_NAME_CMD) || command.cmd.equals(INVITE_GROUP_CMD) || command.cmd.equals(TICK_PERSON_GROUP_CMD) || command.cmd.equals(SAVE_GROUP_CMD)) {
                        sendGroupInfoDelay(true);
                    } else if (command.cmd.equals(UPDATE_GROUP_NOTICE_CMD)) {
                        o.put("schedule", !TextUtils.isEmpty(o.optString("sendTime")));
                    } else if (command.cmd.equals(GROUP_QRCODE_CMD)) {
                        if (!TextUtils.isEmpty(qrCodeUrl)) {
                            o.put("icon", qrCodeUrl);
                        }
                    } else if (command.cmd.equals(TRANSFER_MASTER_CMD) && statusCode == 200) {
                        //读取数据库，获取新群主
                        String clientGroupId = o.optString("clientGroupId");
                        Group g = getOneGroupFromWechatDB_ClientGroupId(clientGroupId, true);
                        if (g != null) {
                            o.put("masterWxId", g.master);
                            o.put("masterWxNo", g.masterWxNo);
                        }
                        sendGroupInfoDelay(false);
                    }
                    String msg = o.toString();
                    Log.d("test", "sendMsgToServer2 topic = " + topic + " , msg = " + msg);

                    //修改DB状态
                    new MyDBHelper(getApplicationContext()).updateServerMsgStatusByIndex(index, ACTION_STATUS_2);
                    new MyDBHelper(getApplicationContext()).updateServerMsgReplyContentByIndex(index, msg);

                    //fromFront不用走rabbitMQ
                    if (!o.optBoolean("fromFront")) {
                        doSendMessageToServer(topic, msg, true, index);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //群里发来的消息   zzx add  type类型 增加链接和小程序  wxMessage  因为链接跟小程序上报的内容和本地数据库不一致，所以需要传过来然后保存本地的
    public synchronized void sendMsgToServer3(final String clientGroupId, final String sender, final String message, final int type, final String saved) {
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "sendMsgToServer3");
                try {
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                    String topic = "kiway-group-message-" + robotId + "#" + wxNo;

                    JSONObject o = new JSONObject();
                    o.put("clientGroupId", clientGroupId);
                    o.put("name", sender);
                    // o.put("type", TYPE_TEXT);
                    o.put("type", type);
                    o.put("message", message);
                    o.put("robotId", robotId);
                    o.put("userId", wxNo);

                    String msg = o.toString();
                    Log.d("test", "sendMsgToServer3 topic = " + topic + " , msg = " + msg);

                    boolean successed = doSendMessageToServer(topic, msg, false, 0);
                    if (successed) {
                        new MyDBHelper(getApplication()).addMessage(sender, saved, System.currentTimeMillis() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private synchronized void sendFriendInfoDelay() {
        new Thread() {
            @Override
            public void run() {
                if (MainActivity.instance != null) {
                    MainActivity.instance.getAllFriends(false, true);
                }
                try {
                    Thread.sleep(120 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (MainActivity.instance != null) {
                    MainActivity.instance.getAllFriends(false, true);
                }
            }
        }.start();
    }

    private synchronized void sendGroupInfoDelay(final boolean updatePeoples) {
        new Thread() {
            @Override
            public void run() {
                if (MainActivity.instance != null) {
                    MainActivity.instance.getAllGroups(updatePeoples);
                }
                try {
                    Thread.sleep(120 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (MainActivity.instance != null) {
                    MainActivity.instance.getAllGroups(updatePeoples);
                }
            }
        }.start();
    }

    private Friend getOneFriendFromWechatDB_Name(String name) {
        String password = initDbPassword(getApplicationContext());
        File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
        Friend f = Utils.doGetOneFriendByNickNameOrRemark(getApplicationContext(), dbFile, password, name);
        return f;
    }

    private Friend getOneFriendFromWechatDB_WxNo(String wxNo) {
        String password = initDbPassword(getApplicationContext());
        File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
        Friend f = Utils.doGetOneFriendByWxNo(getApplicationContext(), dbFile, password, wxNo);
        return f;
    }

    private Group getOneGroupFromWechatDB_ClientGroupName(String groupName) {
        final String password = initDbPassword(getApplicationContext());
        final File dbFile = getWxDBFile("EnMicroMsg.db", "getAllGroups" + new Random().nextInt(9999) + ".db");
        Group g = Utils.doGetOneGroupByGroupName(getApplicationContext(), dbFile, password, groupName, false);
        return g;
    }

    public Group getOneGroupFromWechatDB_ClientGroupId(String clientGroupId, boolean needOwer) {
        String password = initDbPassword(getApplicationContext());
        File dbFile = getWxDBFile("EnMicroMsg.db", "getAllGroups" + new Random().nextInt(9999) + ".db");
        Group g = Utils.doGetOneGroupByGroupId(getApplicationContext(), dbFile, password, clientGroupId, needOwer);
        return g;
    }

    private void sendReply20sLater(Action action) {
        //卧底不需要发这个
        int role = getSharedPreferences("role", 0).getInt("role", ROLE_KEFU);
        if (role == ROLE_WODI) {
            return;
        }
        String busyStr = getSharedPreferences("busy", 0).getString("busy", DEFAULT_BUSY);
        String offlineStr = getSharedPreferences("offline", 0).getString("offline", DEFAULT_OFFLINE);
        if (TextUtils.isEmpty(busyStr) || TextUtils.isEmpty(offlineStr)) {
            return;
        }

        Message msg = new Message();
        msg.what = MSG_INSERT_QUEUE;

        boolean in = Utils.isEffectiveDate("08:30:00", "20:00:00");
        String hint = in ? busyStr : offlineStr;

        Log.d("test", "hint = " + hint);
        long id = System.currentTimeMillis();
        String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + hint + "\",\"returnType\":1}],\"id\":" + id + ",\"time\":" + id + ",\"content\":\"" + action.content + "\"}";
        msg.obj = new ZbusRecv(fakeRecv, false);
        mHandler.sendMessageDelayed(msg, 20000);
    }

    public void sendRenameTaskQueue(String fakeRecv) {
        Message msg = new Message();
        msg.what = MSG_INSERT_RENAME_QUEUE;
        msg.obj = new ZbusRecv(fakeRecv, true);
        mHandler.sendMessage(msg);
    }

    public void sendReplyImmediately(String fakeRecv, boolean insertToHead) {
        Message msg = new Message();
        msg.what = MSG_INSERT_QUEUE;
        msg.arg1 = insertToHead ? 1 : 0;

        msg.obj = new ZbusRecv(fakeRecv, true);
        mHandler.sendMessage(msg);
    }

    private void release(boolean success, boolean jump, boolean upload) {
        doRelease(success, jump, upload);
    }

    private void release(boolean success) {
        doRelease(success, true, true);
    }

    private void doRelease(boolean success, boolean jump, boolean upload) {
        Log.d("test", "release is called ");
        //1.跳转
        if (jump) {
            Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
            startActivity(intent);
        }

        //2.上报状态
        if (upload) {
            Action action = actions.get(currentActionID);
            if (action != null) {
                Command command = action.command;
                if (command != null) {
                    sendMsgToServer2(success ? 200 : 500, command);
                } else {
                    //command为空，直接修改状态即可
                    new MyDBHelper(this).updateServerMsgStatusByIndex(action.indexs, ACTION_STATUS_3);
                }
            }
        }

        //3.清除状态
        mHandler.removeMessages(MSG_ACTION_TIMEOUT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentActionID = -1;
                actioningFlag = false;
                FileUtils.saveFile("false", "actioningFlag.txt");
            }
        }, 3000);
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
                    Log.d("test", "texts isEmpty");
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
                        if (Utils.isUselessContent(getApplicationContext(), sender, content)) {
                            Log.d("test", "isUselessContent");
                            continue;
                        }
                    } else if (ticker.endsWith("请求添加你为朋友")) {
                        sender = "系统";
                        content = ticker;
                    } else {
                        continue;
                    }

                    //1.预先加入map
                    long id = System.currentTimeMillis();
                    Log.d("test", "id = " + id);
                    PendingIntent intent = ((Notification) event.getParcelableData()).contentIntent;
                    Action action = new Action();
                    action.id = id;
                    action.sender = sender;
                    action.content = content;
                    action.intent = intent;

                    //自动加好友
                    if (content.endsWith("请求添加你为朋友")) {
                        action.actionType = TYPE_REQUEST_FRIEND;
                    }
                    //设置转发对象
                    else if (content.startsWith("设置转发对象：")) {
                        //设置转发对象有可能丢掉！因为微信可能不弹出通知
                        action.actionType = TYPE_SET_COLLECTOR;
                    }
                    //需要转发到“消息收集群”
                    else if (content.startsWith("[图片]") /*|| content.startsWith("[链接]") || content.startsWith("[视频]") || content.startsWith("[文件]") || content.contains("向你推荐了")*/) {
                        //1122 图片也做preAction
                        action.actionType = TYPE_TEXT; //TYPE_COLLECTOR_FORWARDING
                    } else if (!TextUtils.isEmpty(Constant.qas.get(content.trim()))) {
                        action.actionType = TYPE_AUTO_MATCH;
                        action.returnMessages.add(new ReturnMessage(TYPE_TEXT, Constant.qas.get(content.trim())));
                    } else if (Utils.checkInBackDoor(content.trim()) > 0) {
                        action.actionType = Utils.checkInBackDoor(content.trim());
                    } else {
                        action.actionType = TYPE_TEXT;
                    }

                    actions.put(id, action);
                    if (action.actionType == TYPE_TEXT) {
                        //需要预处理
                        preActionsQueue.add(action);
                    } else {
                        //不需要预处理
                        dispatchAction(action);
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d("maptrix", "窗口状态变化");
                currentWechatPage = event.getClassName().toString();
                Log.d("test", "currentWechatPage = " + currentWechatPage);

                if (currentWechatPage.equals("com.tencent.mm.plugin.voip.ui.VideoActivity")) {
                    //视频通话，挂断并关闭页面即可。
                    findTargetNode(NODE_TEXTVIEW, "挂断", -1, true);
                    release(false);
                    return;
                }
                if (currentActionID == FLAG_PREACTION) {
                    Log.d("maptrix", "预处理事件，return0");
                    return;
                }
                if (currentActionID == -1) {
                    Log.d("maptrix", "没有事件，return1");
                    return;
                }
                if (actioningFlag) {
                    Log.d("maptrix", "事件进行中，return2");
                    return;
                }
                actioningFlag = true;
                FileUtils.saveFile("true", "actioningFlag.txt");
                final int actionType = actions.get(currentActionID).actionType;

                boolean exception = checkWechatExceptionStatus();
                if (exception) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dispatchAction(actionType);
                        }
                    }, 8000);
                    return;
                } else {
                    dispatchAction(actionType);
                }
                break;
        }
    }

    private void dispatchAction(final int actionType) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (actionType == TYPE_CREATE_GROUP_CHAT || actionType == TYPE_DELETE_GROUP_CHAT || actionType == TYPE_ADD_GROUP_PEOPLE || actionType == TYPE_DELETE_GROUP_PEOPLE || actionType == TYPE_FIX_GROUP_NOTICE
                        || actionType == TYPE_FIX_GROUP_NAME || actionType == TYPE_CHAT_IN_GROUP || actionType == TYPE_AT_GROUP_PEOPLE || actionType == TYPE_DELETE_MOMENT || actionType == TYPE_ADD_FRIEND || actionType == TYPE_DELETE_FRIEND
                        || actionType == TYPE_MISSING_FISH || actionType == TYPE_BROWSER_MESSAGE || actionType == TYPE_FIX_NICKNAME || actionType == TYPE_FIX_FRIEND_NICKNAME
                        || actionType == TYPE_FIX_ICON || actionType == TYPE_NEARBY_PEOPLE || actionType == TYPE_SEND_BATCH || actionType == TYPE_CHECK_MOMENT
                        || actionType == TYPE_CLEAR_CHAT_HISTORY || actionType == TYPE_INTERACT_MOMENT || actionType == TYPE_NOTIFY_RESULT || actionType == TYPE_SCRIPT
                        || actionType == TYPE_ADD_PUBLIC_ACCOUNT || actionType == TYPE_SEARCH_PUBLIC_ACCOUNT || actionType == TYPE_SAVE_GROUP || actionType == TYPE_GROUP_QRCODE || actionType == TYPE_TRANSFER_MASTER
                        ) {
                    new Thread() {
                        @Override
                        public void run() {
                            goBacktoWechatHomepage(true);
                            doActionCommandByType(actionType);
                        }
                    }.start();
                } else if (actionType == TYPE_REQUEST_FRIEND) {
                    new Thread() {
                        @Override
                        public void run() {
                            goBacktoWechatHomepage(true);
                            doRequestFriend();
                        }
                    }.start();
                } else if (actionType == TYPE_COLLECTOR_FORWARDING) {
                    doChatByActionType();
                } else {
                    //聊天有关，0810修改，一律退到首页再做处理
                    new Thread() {
                        @Override
                        public void run() {
                            goBacktoWechatHomepage(true);
                            final String targetSender = actions.get(currentActionID).sender;
                            searchTargetInWxHomePage(1, targetSender, true);
                        }
                    }.start();
                }
            }
        }, 3000);
    }

    public boolean goBacktoWechatHomepage(boolean checkActionFlag) {
        while (!checkIsWxHomePage()) {
            if (checkActionFlag && !actioningFlag) {
                break;
            }
            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    findTargetNode(NODE_BUTTON, "不保留|不保存|退出", CLICK_SELF, true);
                }
            });

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //分发事件
    private void dispatchAction(Action action) {
        if (action.actionType == TYPE_TEXT) {
            //文字的话直接走zbus
            if (action.clientGroupId.equals("-1")) {
                Log.d("test", "好友消息");
                if (action.content.equals("[图片]") || action.content.startsWith("[链接]") || action.content.startsWith("[文件]") || action.content.startsWith("[小程序]")) {
                } else {
                    sendMsgToServer(action.sender, action.content, action.content, TYPE_TEXT);
                }
                sendReply20sLater(action);
            } else if (!TextUtils.isEmpty(action.clientGroupId)) {
                Log.d("test", "群组消息");
                if (action.content.equals("[图片]") || action.content.startsWith("[链接]") || action.content.startsWith("[文件]") || action.content.startsWith("[小程序]")) {
                } else {
                    sendMsgToServer3(action.clientGroupId, action.sender, action.content, TYPE_TEXT, action.content);
                }
            } else {
                Log.d("test", "既不是好友也不是群组，不处理这个消息");
            }
        } else if (action.actionType == TYPE_AUTO_MATCH) {
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + action.returnMessages.get(0).content + "\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, false);
        } else if (action.actionType == TYPE_BACK_DOOR) {
            String ret = getBackDoorByKey(action.content.trim());
            action.returnMessages.add(new ReturnMessage(TYPE_TEXT, ret));
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"" + action.returnMessages.get(0).content + "\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, true);
        } else if (action.actionType == TYPE_COLLECTOR_FORWARDING) {
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, true);
        } else if (action.actionType == TYPE_SET_COLLECTOR) {
            String forwardto = action.content.replace("设置转发对象：", "").trim();
            if (TextUtils.isEmpty(forwardto)) {
                return;
            }
            getSharedPreferences("forwardto", 0).edit().putString("forwardto", forwardto).commit();
            getSharedPreferences("collector", 0).edit().putString("collector", forwardto).commit();
            toast("设置转发对象成功");
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, false);
        } else if (action.actionType == TYPE_REQUEST_FRIEND) {
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, true);
        } else if (action.actionType == TYPE_CREATE_GROUP_CHAT
                || action.actionType == TYPE_DELETE_GROUP_CHAT
                || action.actionType == TYPE_ADD_GROUP_PEOPLE
                || action.actionType == TYPE_DELETE_GROUP_PEOPLE
                || action.actionType == TYPE_FIX_GROUP_NAME
                || action.actionType == TYPE_FIX_GROUP_NOTICE
                || action.actionType == TYPE_CHAT_IN_GROUP
                || action.actionType == TYPE_AT_GROUP_PEOPLE
                || action.actionType == TYPE_SAVE_GROUP
                || action.actionType == TYPE_GROUP_QRCODE
                || action.actionType == TYPE_TRANSFER_MASTER
                || action.actionType == TYPE_DELETE_MOMENT
                || action.actionType == TYPE_ADD_FRIEND
                || action.actionType == TYPE_DELETE_FRIEND
                || action.actionType == TYPE_MISSING_FISH
                || action.actionType == TYPE_BROWSER_MESSAGE
                || action.actionType == TYPE_FIX_NICKNAME
                || action.actionType == TYPE_FIX_FRIEND_NICKNAME
                || action.actionType == TYPE_FIX_ICON
                || action.actionType == TYPE_NEARBY_PEOPLE
                || action.actionType == TYPE_CHECK_MOMENT
                || action.actionType == TYPE_CLEAR_CHAT_HISTORY
                || action.actionType == TYPE_INTERACT_MOMENT
                || action.actionType == TYPE_NOTIFY_RESULT
                || action.actionType == TYPE_SCRIPT
                || action.actionType == TYPE_ADD_PUBLIC_ACCOUNT
                || action.actionType == TYPE_SEARCH_PUBLIC_ACCOUNT
                ) {
            action.content = Base64.encodeToString(action.content.getBytes(), NO_WRAP);
            String fakeRecv = "{\"areaCode\":\"440305\",\"sender\":\"" + action.sender + "\",\"me\":\"客服888\",\"returnMessage\":[{\"content\":\"content\",\"returnType\":1}],\"id\":" + action.id + ",\"time\":" + action.id + ",\"content\":\"" + action.content + "\"}";
            sendReplyImmediately(fakeRecv, true);
        } else if (action.actionType == TYPE_CHECK_NEW_VERSION) {
            MainActivity.instance.checkNewVersion(null);
        } else if (action.actionType == TYPE_UPDATE_BASEDATA) {
            MainActivity.instance.getBaseData();
        }
    }

    private boolean checkWechatExceptionStatus() {
        Log.d("test", "checkWechatExceptionStatus");
        //1、微信更新弹出框
        boolean find1 = findTargetNode(NODE_BUTTON, "取消", CLICK_NONE, false);
        boolean find2 = findTargetNode(NODE_BUTTON, "立即安装|下载安装", CLICK_NONE, true);
        if (find1 && find2) {
            findTargetNode(NODE_BUTTON, "取消", CLICK_SELF, false);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    findTargetNode(NODE_BUTTON, "是", CLICK_SELF, true);
                }
            }, 3000);
            return true;
        } else {
            boolean find3 = findTargetNode(NODE_TEXTVIEW, "是否取消安装？", CLICK_NONE, false);
            if (find3) {
                findTargetNode(NODE_BUTTON, "是", CLICK_SELF, true);
                return true;
            }
        }
        return false;
    }

    private void doActionCommandByType(final int actionType) {
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            Log.d("test", "doActionCommandByType content = " + content + " , actionType = " + actionType);
            final JSONObject o = new JSONObject(content);
            content = o.optString("content");
            start = o.optInt("start");
            end = o.optInt("end");
            String clientGroupId = null;
            final int type = o.optInt("type");
            if (o.has("clientGroupId")) {
                clientGroupId = o.optString("clientGroupId");
            } else if (o.has("clientGroupIds")) {
                JSONArray array = o.optJSONArray("clientGroupIds");
                if (array != null) {
                    clientGroupId = array.optString(0);
                }
            }
            final String url = o.optString("url");
            final JSONArray members = o.optJSONArray("members");
            final String finalContent = content;
            final String finalClientGroupId = clientGroupId;
            final String target = o.optString("target");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (actionType == TYPE_CREATE_GROUP_CHAT
                            || actionType == TYPE_ADD_FRIEND) {
                        //首页-右上角工具栏
                        findTargetNode(NODE_RELATIVELAYOUT, Integer.MAX_VALUE);
                        if (mFindTargetNode == null) {
                            release(false);
                            return;
                        }
                        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        selectToolbar();
                    } else if (actionType == TYPE_DELETE_MOMENT) {
                        //我-相册
                        goBacktoWechatHomepage(true);
                        deleteMoment(finalContent);
                    } else if (actionType == TYPE_MISSING_FISH) {
                        //通讯录-新的朋友
                        goBacktoWechatHomepage(true);
                        addMissingFish();
                    } else if (actionType == TYPE_BROWSER_MESSAGE) {
                        //浏览，补救图片
                        if (target.endsWith("@chatroom")) {
                            Group g = getOneGroupFromWechatDB_ClientGroupId(target, false);
                            if (g == null) {
                                release(false);
                                toast("该群不存在或已经被删除");
                                return;
                            }
                            goBacktoWechatHomepage(true);
                            searchTargetInWxGroupPage(actionType, g.groupName, true);
                        } else {
                            searchTargetInWxHomePage(actionType, target, true);
                        }
                    } else if (actionType == TYPE_FIX_NICKNAME
                            || actionType == TYPE_FIX_ICON) {
                        //我-个人信息
                        goBacktoWechatHomepage(true);
                        fixMyNicknameOrIcon(actionType, url);
                    } else if (actionType == TYPE_NEARBY_PEOPLE) {
                        //发现-附近的人
                        goBacktoWechatHomepage(true);
                        addNearbyPeople(finalContent);
                    } else if (actionType == TYPE_CHAT_IN_GROUP
                            || actionType == TYPE_ADD_GROUP_PEOPLE
                            || actionType == TYPE_DELETE_GROUP_CHAT
                            || actionType == TYPE_FIX_GROUP_NAME
                            || actionType == TYPE_AT_GROUP_PEOPLE
                            || actionType == TYPE_SAVE_GROUP
                            || actionType == TYPE_GROUP_QRCODE
                            || actionType == TYPE_TRANSFER_MASTER
                            || actionType == TYPE_DELETE_GROUP_PEOPLE
                            || actionType == TYPE_SCRIPT
                            ) {
                        //通讯录-群聊-关于群的操作
                        Group g = getOneGroupFromWechatDB_ClientGroupId(finalClientGroupId, false);
                        if (g == null) {
                            release(actionType == TYPE_DELETE_GROUP_CHAT);
                            toast("该群不存在或已经被删除");
                            return;
                        }
                        int role = getSharedPreferences("role", 0).getInt("role", ROLE_KEFU);
                        if (actionType == TYPE_CHAT_IN_GROUP) {
                            if (type == 1) {//文本用搜索做
                                goBacktoWechatHomepage(true);
                                searchTargetInWxGroupPage(actionType, g.groupName, true);
                            } else {//其他用分享做
                                chatInGroup(g.groupName);
                            }
                        } else if ((actionType == TYPE_SCRIPT && role == ROLE_WODI) || (actionType == TYPE_SAVE_GROUP) || actionType == TYPE_DELETE_GROUP_CHAT) {
                            //从首页找到群
                            searchTargetInWxHomePage(actionType, g.groupName, true);
                        } else {
                            //从通讯录-群
                            goBacktoWechatHomepage(true);
                            searchTargetInWxGroupPage(actionType, g.groupName, true);
                        }
                    } else if (actionType == TYPE_FIX_GROUP_NOTICE) {
                        String clientGroupId = finalClientGroupId.replace("[", "").replace("]", "");
                        Group g = getOneGroupFromWechatDB_ClientGroupId(clientGroupId, false);
                        if (g == null) {
                            release(false);
                            return;
                        }
                        goBacktoWechatHomepage(true);
                        searchTargetInWxGroupPage(actionType, g.groupName, true);
                    } else if (actionType == TYPE_DELETE_FRIEND) {
                        startDeleteFriend(members);
                    } else if (actionType == TYPE_SEND_BATCH) {
                        int flag = o.optInt("flag");
                        if (flag == 1) {
                            sendBatchMessage1();
                        } else {
                            sendBatchMessage2();
                        }
                    } else if (actionType == TYPE_FIX_FRIEND_NICKNAME) {
                        String target = o.optString("oldName");
                        searchTargetInWxHomePage(actionType, target, true);
                    } else if (actionType == TYPE_CHECK_MOMENT) {
                        //发现-朋友圈，改为：我-相册
                        goBacktoWechatHomepage(true);
                        checkMoment();
                    } else if (actionType == TYPE_INTERACT_MOMENT) {
                        goBacktoWechatHomepage(true);
                        //我-相册
                        String description = o.optString("description");
                        interactMoment(description);
                    } else if (actionType == TYPE_ADD_PUBLIC_ACCOUNT) {
                        goBacktoWechatHomepage(true);
                        //通讯录-公众号
                        addPublicAccount();
                    } else if (actionType == TYPE_SEARCH_PUBLIC_ACCOUNT) {
                        goBacktoWechatHomepage(true);
                        //通讯录-公众号
                        searchPublicAccount();
                    } else if (actionType == TYPE_NOTIFY_RESULT) {
                        String target = o.optString("wxNo");
                        checkNotifier(target);
                    } else if (actionType == TYPE_CLEAR_CHAT_HISTORY) {
                        goBacktoWechatHomepage(true);
                        //发现-朋友圈
                        clearChatHistory();
                    } else {
                        String target = actions.get(currentActionID).sender;
                        searchTargetInWxHomePage(actionType, target, true);
                    }
                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void sendBatchMessage2() {
        if (woTextView == null) {
            release(false);
            return;
        }
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "设置", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "通用", CLICK_PARENT, true);
                        if (!find) {
                            release(false);
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean find = findTargetNode(NODE_TEXTVIEW, "辅助功能", CLICK_PARENT, true);
                                if (!find) {
                                    release(false);
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean find = findTargetNode(NODE_TEXTVIEW, "群发助手", CLICK_PARENT, true);
                                        if (!find) {
                                            release(false);
                                            return;
                                        }
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                boolean find = findTargetNode(NODE_TEXTVIEW, "开始群发", CLICK_PARENT, true);
                                                if (!find) {
                                                    release(false);
                                                    return;
                                                }
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_BUTTON, "新建群发", CLICK_SELF, true);
                                                        if (!find) {
                                                            release(false);
                                                            return;
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                checkFriendInListView2();
                                                            }
                                                        }, 3000);
                                                    }
                                                }, 3000);
                                            }
                                        }, 3000);
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void doRequestFriend() {
        if (tongxunluTextView == null) {
            release(false);
            return;
        }
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 120 / 762);
                            sleep(3000);
                            boolean has = hasAcceptButton(false);
                            if (!has) {
                                release(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            release(false);
                        }
                    }
                }.start();

            }
        }, 3000);
    }

    private void clearChatHistory() {
        if (woTextView == null) {
            release(false);
            return;
        }
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "设置", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "聊天", CLICK_PARENT, true);
                        if (!find) {
                            release(false);
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean find = findTargetNode(NODE_TEXTVIEW, "清空聊天记录", CLICK_PARENT, true);
                                if (!find) {
                                    release(false);
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean find = findTargetNode(NODE_BUTTON, "清空", CLICK_SELF, true);
                                        if (!find) {
                                            release(false);
                                            return;
                                        }
                                        //FIXME 这里60秒时间不一定够，要智能判断
                                        resetMaxReleaseTime(DEFAULT_RELEASE_TIME);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                release(true);

                                                restartWechat();
                                            }
                                        }, 60000);
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void searchPublicAccount() {
        if (tongxunluTextView == null) {
            release(false);
            return;
        }
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        //公众号
                        clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 300 / 762);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findTargetNode(NODE_IMAGEBUTTON, 1);
                                if (mFindTargetNode == null) {
                                    release(false);
                                    return;
                                }
                                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                            final String name = new JSONObject(content).optString("name");
                                            findTargetNode(NODE_EDITTEXT, name);
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    final boolean find = findTargetNode(NODE_TEXTVIEW, name, CLICK_PARENT, true);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (!find) {
                                                                release(false);
                                                                return;
                                                            }
                                                            findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                                                            if (mFindTargetNode == null) {
                                                                release(false);
                                                                return;
                                                            }
                                                            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                            mHandler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    findTargetNode(NODE_TEXTVIEW, "查看历史消息", CLICK_PARENT, true);
                                                                    //10秒自动退出
                                                                    mHandler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            release(true);
                                                                        }
                                                                    }, 10000);
                                                                }
                                                            }, 3000);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 2000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            release(false);
                                        }
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }.start();
            }
        }, 3000);
    }

    private void addPublicAccount() {
        if (tongxunluTextView == null) {
            release(false);
            return;
        }
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread() {
                    @Override
                    public void run() {
                        //公众号
                        clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 300 / 762);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                                if (mFindTargetNode == null) {
                                    release(false);
                                    return;
                                }
                                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                            String name = new JSONObject(content).optString("name");
                                            findTargetNode(NODE_EDITTEXT, name);
                                            execRootCmdSilent("input keyevent 160");
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 220 / 762);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            boolean find1 = findTargetNode(NODE_TEXTVIEW, "关注", CLICK_PARENT, true);
                                                            if (!find1) {
                                                                boolean find2 = findTargetNode(NODE_TEXTVIEW, "进入公众号", CLICK_NONE, true);
                                                                if (find2) {
                                                                    release(true);
                                                                    return;
                                                                }
                                                                release(false);
                                                                return;
                                                            }
                                                            mHandler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    release(true);
                                                                }
                                                            }, 5000);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 5000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            release(false);
                                        }
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }.start();
            }
        }, 3000);
    }

    private void checkNotifier(final String target) {
        new Thread() {
            @Override
            public void run() {
                Friend f = getOneFriendFromWechatDB_WxNo(target);
                Log.d("test", "hasNotifyer = " + f.toString());
                if (f != null) {
                    searchTargetInWxHomePage(TYPE_NOTIFY_RESULT, target, true);
                } else {
                    //先加好友，再去做
                    Log.d("test", "还不是好友，发起好友申请");
                    ArrayList<String> requests = new ArrayList<>();
                    requests.add(target);
                    MainActivity.instance.doRequestFriends(requests);
                    release(false);
                }
            }
        }.start();
    }

    private void doNotifyResult() {
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            String message = new JSONObject(content).optString("message");
            sendTextOnly(message, true);
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void interactMoment(final String description) {
        if (woTextView == null) {
            release(false);
            return;
        }
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startCheckMomentList(10, description);
                    }
                }, 3000);
            }
        }, 2000);
    }

    private void checkMoment() {
        if (woTextView == null) {
            release(false);
            return;
        }
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findTargetNode(NODE_IMAGEBUTTON, 1);
                        if (mFindTargetNode == null) {
                            release(false);
                            return;
                        }
                        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(5000);
                                    int count = 5;
                                    for (int i = 0; i < count; i++) {
                                        execRootCmdSilent("input swipe 360 900 360 300");
                                        sleep(3000);
                                    }
                                    release(false);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    release(false);
                                }
                            }
                        }.start();

                    }
                }, 3000);
            }
        }, 2000);
    }

    //用分享去做
    private void chatInGroup(String groupName) {
        Log.d("test", "chatInGroup");
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            Log.d("test", "content = " + content);
            actions.get(currentActionID).sender = groupName;
            JSONObject o = new JSONObject(content);
            final String message = o.optString("message");
            int type = o.optInt("type");

            if (type == 1) {//文本，0.5.9已不走这里
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setText(Utils.replaceReply(message));
                sp.setShareType(Platform.SHARE_TEXT);
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                wx.share(sp);
                doShareToWechatFriend();
            } else if (type == 3) {//图片
                new Thread() {
                    @Override
                    public void run() {
                        sendImageOnly(message, false);
                    }
                }.start();
            } else if (type == 52) {//链接
                final JSONObject contentO = new JSONObject(message);
                new Thread() {
                    @Override
                    public void run() {
                        sendLinkOnly(contentO.toString(), false);
                    }
                }.start();
            } else if (type == 50) { //文件
                JSONObject contentO = new JSONObject(message);
                sendFileOnly(contentO.getString("content"), contentO.getString("fileName"), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    //用分享去做
    private void sendBatchMessage1() {
        Log.d("test", "sendBatchMessage1");
        try {
            final String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            Log.d("test", "content = " + content);
            JSONArray messages = new JSONObject(content).optJSONArray("messages");
            int type = messages.getJSONObject(0).getInt("type");
            final String text = messages.getJSONObject(0).getString("content");
            if (type == 1) {//文本
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setText(text);
                sp.setShareType(Platform.SHARE_TEXT);
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                wx.share(sp);

                doShareToWechatFriend2();
            } else if (type == 2) {//图片url=text
                new Thread() {
                    @Override
                    public void run() {
                        sendImageOnly(text, true);
                    }
                }.start();
            } else if (type == 3) {//链接
                final JSONObject contentO = messages.getJSONObject(0).getJSONObject("content");
                new Thread() {
                    @Override
                    public void run() {
                        sendLinkOnly(contentO.toString(), true);
                    }
                }.start();
            } else if (type == 50) {
                final JSONObject contentO = messages.getJSONObject(0).getJSONObject("content");
                new Thread() {
                    @Override
                    public void run() {
                        sendFileOnly(contentO.optString("url"), contentO.optString("fileName"), true);
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void startDeleteFriend(final JSONArray members) {
        new Thread() {
            @Override
            public void run() {
                try {
                    int count = members.length();
                    resetMaxReleaseTime(DEFAULT_RELEASE_TIME * count);

                    for (int i = 0; i < count; i++) {
                        final String currentZombie = members.optString(i);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                searchTargetInWxHomePage(TYPE_DELETE_FRIEND, currentZombie, false);
                            }
                        });
                        sleep(30000);
                    }
                    release(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }.start();
    }

    private void addNearbyPeople(final String content) {
        if (faxianView == null) {
            release(false);
            return;
        }
        faxianView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "附近的人", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (currentWechatPage.equals("com.tencent.mm.plugin.nearby.ui.NearbyPersonalInfoUI")) {
                            SupplementInfo(content);
                        } else {
                            boolean find = findTargetNode(NODE_BUTTON, "开始查看", CLICK_SELF, true);
                            if (find) {
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findTargetNode(NODE_CHECKBOX, "下次不提示", CLICK_SELF, true);
                                        findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //补充个人信息
                                                if (currentWechatPage.equals("com.tencent.mm.plugin.nearby.ui.NearbyPersonalInfoUI")) {
                                                    SupplementInfo(content);
                                                } else {
                                                    doAddNearByPeople(content);
                                                }
                                            }
                                        }, 3000);
                                    }
                                }, 2000);
                            } else {
                                doAddNearByPeople(content);
                            }
                        }
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void SupplementInfo(final String content) {
        findTargetNode(NODE_RADIOBUTTON, "男", CLICK_SELF, true);
        findTargetNode(NODE_TEXTVIEW, "地区*", CLICK_PARENT, true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_LINEARLAYOUT, 3);
                if (mFindTargetNode != null) {
                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findTargetNode(NODE_TEXTVIEW, "下一步", CLICK_SELF, true);
                            doAddNearByPeople(content);
                        }
                    }, 3000);
                }
            }
        }, 5000);
    }

    private void doAddNearByPeople(final String content) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                peoples.clear();
                findNearbyPeopleInListView(getRootInActiveWindow());
                Log.d("test", "friends count = " + peoples.size());
                final int count = peoples.size();

                resetMaxReleaseTime(21000 * count);

                new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < count; i++) {
                            final int finalI = i;
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
                                                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                return;
                                            }
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findTargetNode(NODE_EDITTEXT, content);
                                                    findTargetNode(NODE_TEXTVIEW, "发送", CLICK_SELF, true);
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
                        release(true);
                    }
                }.start();
            }
        }, 10000);
    }

    private boolean findNearbyPeopleInListView(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) {
            return false;
        }
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
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

    private void fixMyNicknameOrIcon(final int actionType, final String url) {
        if (woTextView == null) {
            release(false);
            return;
        }
        Log.d("test", "fixMyNicknameOrIcon , url = " + url);
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        new Thread() {
            @Override
            public void run() {
                if (actionType == TYPE_FIX_ICON) {
                    //1.下载图片
                    Bitmap bmp = ImageLoader.getInstance().loadImageSync(url);
                    if (bmp == null) {
                        release(false);
                        return;
                    }
                    //2.保存图片
                    String localPath = saveImage(getApplication(), bmp, true);
                    if (localPath == null) {
                        release(false);
                        return;
                    }
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean find = findTargetNode(NODE_TEXTVIEW, "微信号：", CLICK_PARENT, false);
                        if (!find) {
                            release(false);
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String target = "";
                                int sleepTime = 3000;
                                boolean equals = false;
                                if (actionType == TYPE_FIX_NICKNAME) {
                                    target = "昵称";
                                    sleepTime = 3000;
                                    equals = false;
                                } else if (actionType == TYPE_FIX_ICON) {
                                    target = "头像";
                                    sleepTime = 5000;
                                    equals = true;
                                }
                                boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, equals);
                                if (!find) {
                                    release(false);
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
                                                final String newName = o.optString("newName");
                                                clearAndPasteEditText(1, newName);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        final boolean find = findTargetNode(NODE_TEXTVIEW, "保存", CLICK_SELF, true);
                                                        if (find) {
                                                            getSharedPreferences("kiway", 0).edit().putString("name", newName).commit();
                                                            //Utils.blackfile(getApplication());
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release(find);
                                                            }
                                                        }, 3000);
                                                    }
                                                }, 2000);
                                            } else if (actionType == TYPE_FIX_ICON) {
                                                //选图片
                                                findTargetNode(NODE_RELATIVELAYOUT, 2);
                                                if (mFindTargetNode == null) {
                                                    release(false);
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
                                                                release(true);
                                                            }
                                                        }, 5000);
                                                    }
                                                }, 3000);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            release(false);
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

    private boolean isMe(String oldName) {
        String me = getSharedPreferences("kiway", 0).getString("name", "");
        return oldName.equals(me);
    }

    private boolean adding_missing_fish = false;

    private void addMissingFish() {
        if (tongxunluTextView == null) {
            release(false);
            return;
        }
        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //判断是否在通讯录的顶部
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (!checkIsTongxunluTop()) {
                                sleep(1000);
                                //双击到顶部
                                tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                sleep(5000);
                                if (!actioningFlag) {
                                    break;
                                }
                            }
                            //新的朋友
                            clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 120 / 762);
                            sleep(3000);
                            adding_missing_fish = true;
                            resetMaxReleaseTime(30000 * 10 + 5000);
                            int tryCount = 0;
                            while (adding_missing_fish) {
                                if (!actioningFlag) {
                                    break;
                                }
                                if (tryCount == 10) {
                                    break;
                                }
                                int ret = hasAcceptButtonOrAddedTextView();
                                tryCount++;
                                if (ret == 1) {
                                    sleep(30000);
                                } else if (ret == 2) {
                                    sleep(10000);
                                } else if (ret == 3) {
                                    break;
                                }
                            }
                            adding_missing_fish = false;
                            release(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                            release(false);
                        }
                    }
                }.start();

            }
        }, 3000);
    }

    //1:30秒 2：10秒 3：3秒
    //这里30秒不一定够！~
    private int hasAcceptButtonOrAddedTextView() {
        boolean find1 = hasAcceptButton(true);
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

    private void deleteMoment(final String content) {
        if (woTextView == null) {
            release(false);
            return;
        }
        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startCheckMomentList(10, content);
                    }
                }, 3000);
            }
        }, 2000);
    }

    private boolean getMoment = false;

    private void startCheckMomentList(final int tryCount, final String targetText) {
        new Thread() {
            @Override
            public void run() {
                try {
                    getMoment = false;
                    resetMaxReleaseTime(tryCount * 15000);
                    for (int i = 0; i < tryCount; i++) {
                        Log.d("test", "current try = " + i);
                        if (getMoment) {
                            break;
                        }
                        sleep(5000);
                        //find
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                getMoment = getMomentInListView(targetText);
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
                    if (!getMoment) {
                        release(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }.start();
    }

    private boolean getComment = false;

    private void startCheckCommentList(final int tryCount) {
        new Thread() {
            @Override
            public void run() {
                try {
                    getComment = false;
                    resetMaxReleaseTime(tryCount * 15000);
                    for (int i = 0; i < tryCount; i++) {
                        Log.d("test", "current try = " + i);
                        if (getComment) {
                            break;
                        }
                        sleep(5000);
                        //find
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                getComment = getCommentInList();
                                Log.d("test", "getComment = " + getComment);
                            }
                        });
                        sleep(5000);
                        if (getComment) {
                            break;
                        }
                        //scroll
                        execRootCmdSilent("input swipe 360 900 360 300");
                    }
                    if (!getComment) {
                        release(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }.start();
    }

    private boolean getCommentInList() {
        //{"cmd":"friendCircleCommentCmd","author": "浪翻云","content" : "郑康评论测试","replyContent" : "这个是我回复给你的" ,  "robotId" :"111","senderId" : "111","token":"111"}
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            JSONObject o = new JSONObject(content);
            String author = o.optString("author");
            content = o.optString("content") + " ";
            final String replyContent = o.optString("replyContent");
            //FIXME 这里有问题，找到的node都是第一个
            findTargetNode(NODE_TEXTVIEW, content, CLICK_NONE, true);
            if (mFindTargetNode == null) {
                return false;
            }
            mFindTargetNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendTextOnly(replyContent, true);
                }
            }, 3000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
        return false;
    }

    private boolean getMomentInListView(String text) {
        //zhengkang 0831
        if (text.length() > 10) {
            text = text.substring(0, 10);
        }
        boolean find = findTargetNode(NODE_TEXTVIEW, text, CLICK_PARENT, false);
        if (!find) {
            return false;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int actionType = actions.get(currentActionID).actionType;
                if (actionType == TYPE_DELETE_MOMENT) {
                    doDeleteMoment();
                } else if (actionType == TYPE_INTERACT_MOMENT) {
                    //如果是图文，需要多点一下
                    int type = 1;
                    try {
                        String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                        JSONObject o = new JSONObject(content);
                        type = o.optInt("type");
                    } catch (Exception e) {
                        e.printStackTrace();
                        release(false);
                    }
                    if (type == 2) {
                        findTargetNode(NODE_LINEARLAYOUT, 3);
                        if (mFindTargetNode == null) {
                            release(false);
                        }
                        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startCheckCommentList(5);
                            }
                        }, 3000);
                    } else {
                        startCheckCommentList(5);
                    }
                }
            }
        }, 3000);
        return true;
    }

    private void doDeleteMoment() {
        findTargetNode(NODE_TEXTVIEW, "删除", CLICK_NONE, true);
        if (mFindTargetNode == null) {
            //图片
            findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
            if (mFindTargetNode == null) {
                release(false);
                return;
            }
            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean find = findTargetNode(NODE_TEXTVIEW, "删除", CLICK_PARENT, true);
                    if (!find) {
                        release(false);
                        return;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //TODO 删除DB对应的记录。
                                    //if (true){
                                    //new MyDBHelper(getApplicationContext()).deleteMoment();
                                    //}
                                    release(true);
                                }
                            }, 3000);
                        }
                    }, 3000);
                }
            }, 3000);
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
                            release(true);
                        }
                    }, 3000);
                }
            }, 3000);
        }
    }

    //首页右上角工具栏
    private void selectToolbar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int actionType = actions.get(currentActionID).actionType;
                String target = "";
                if (actionType == TYPE_CREATE_GROUP_CHAT) {
                    target = "发起群聊";
                } else if (actionType == TYPE_ADD_FRIEND) {
                    target = "添加朋友";
                }
                final boolean find = findTargetNode(NODE_TEXTVIEW, target, CLICK_PARENT, true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!find) {
                            release(false);
                            return;
                        }
                        int actionType = actions.get(currentActionID).actionType;
                        if (actionType == TYPE_CREATE_GROUP_CHAT) {
                            checkFriendInListView2();
                        } else if (actionType == TYPE_ADD_FRIEND) {
                            boolean find = findTargetNode(NODE_TEXTVIEW, "微信号/QQ号/手机号", CLICK_NONE, true);
                            if (!find) {
                                release(false);
                                return;
                            }
                            clickSomeWhere(mFindTargetNode);
                            startAddFriend();
                        }
                    }
                }, 3000);
            }
        }, 3000);
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
                    JSONArray members = o.optJSONArray("members");
                    String message = o.optString("message");
                    int count = members.length();
                    resetMaxReleaseTime(DEFAULT_RELEASE_TIME * count);
                    for (int i = 0; i < count; i++) {
                        String member = members.getString(i);
                        addFriend(member, message);
                        synchronized (object) {
                            object.wait(DEFAULT_RELEASE_TIME);
                        }
                    }
                    release(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }

            }
        }.start();
    }

    private void addFriend(final String member, final String message) {
        Log.d("test", "addFriend member = " + member);
        AddFriend af = new MyDBHelper(getApplicationContext()).getAddFriendByPhone(member);
        if (af == null) {
            af = new AddFriend();
            af.requesttime = Utils.longToDate(System.currentTimeMillis());
            af.phone = member;
            new MyDBHelper(getApplicationContext()).addAddFriend(af);
        }

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
                                if (currentWechatPage.equals("com.tencent.mm.plugin.fts.ui.FTSAddFriendUI")) {
                                    Log.d("test", "没有跳页");
                                    updateUserStatus(member, "该用户不存在", STATUS_NOT_EXISTED);
                                    notifyObj(2000);
                                } else if (currentWechatPage.equals("com.tencent.mm.plugin.profile.ui.ContactInfoUI")) {
                                    findTargetNode(NODE_BUTTON, "添加到通讯录", CLICK_NONE, true);
                                    if (mFindTargetNode == null) {
                                        //已经是好友,返回即可
                                        performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                        findTargetNode(NODE_TEXTVIEW, 3);
                                        String remark = mFindTargetNode.getText().toString();
                                        updateUserStatus(member, remark, STATUS_ADD_SUCCESS);
                                        notifyObj(3000);
                                    } else {
                                        //还不是好友
                                        //0.寻找昵称
                                        final String remarkIndex = getParentRemark(getApplication(), 1);
                                        findTargetNode(NODE_TEXTVIEW, 3);
                                        String remark = remarkIndex + " " + mFindTargetNode.getText().toString();
                                        updateUserStatus(member, remark, STATUS_ADDING);
                                        findTargetNode(NODE_BUTTON, "添加到通讯录", CLICK_SELF, true);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    //1.申请语句
                                                    if (!TextUtils.isEmpty(message)) {
                                                        clearAndPasteEditText(1, message);
                                                    }
                                                    //2.备注 TODO 判断备注前面是数字，可以不用加了
                                                    findTargetNode(NODE_EDITTEXT, 2, remarkIndex);
                                                    //3.点击发送按钮
                                                    findTargetNode(NODE_TEXTVIEW, "发送", CLICK_SELF, true);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                            notifyObj(8000);
                                                        }
                                                    }, 5000);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    release(false);
                                                }
                                            }
                                        }, 3000);
                                    }
                                }
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        });
    }

    private void updateUserStatus(String phone, String remark, int status) {
        AddFriend af = new MyDBHelper(getApplicationContext()).getAddFriendByPhone(phone);
        Log.d("test", "af = " + af);
        if (af != null) {
            af.remark = remark;
            af.status = status;
            new MyDBHelper(getApplicationContext()).updateAddFriend(af);
            Utils.updateUserStatus(getApplicationContext(), phone, remark, status);
        }
    }

    private void notifyObj(final long sleepTime) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("test", "notifyAll");
                synchronized (object) {
                    object.notifyAll();
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
                    JSONArray members = o.optJSONArray("members");
                    if (members == null || members.length() == 0) {
                        members = o.optJSONArray("receivers");
                    }
                    int count = members.length();

                    resetMaxReleaseTime(20000 * count + 15000);

                    checkedFriends.clear();

                    for (int i = 0; i < count; i++) {
                        JSONObject temp = members.getJSONObject(i);
                        String member = temp.optString("remark");
                        if (TextUtils.isEmpty(member)) {
                            member = temp.optString("name");
                        }
                        int length = getTextLengthInEditText(1, member);
                        long sleepTime = 3000 + length * 2000;
                        final String finalMember = member;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                clearAndPasteEditText(1, finalMember);
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
                                    //加人、群发选人
                                    doCheckFriend1(getRootInActiveWindow());
                                }
                            }
                        });
                        Thread.sleep(3000);
                    }
                    clickSureButton();
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
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
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "发送|确定|删除|下一步", CLICK_SELF, false);//确定(16) 发送(2)
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int type = actions.get(currentActionID).actionType;
                        if (type == TYPE_CREATE_GROUP_CHAT) {
                            findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                            if (mFindTargetNode == null) {
                                release(false);
                                return;
                            }
                            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //1. 修改群名称
                                    boolean find = findTargetNode(NODE_TEXTVIEW, "群聊名称", CLICK_PARENT, true);
                                    if (!find) {
                                        release(false);
                                        return;
                                    }
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                                JSONObject o = new JSONObject(content);
                                                String name = o.optString("name");
                                                findTargetNode(NODE_EDITTEXT, name);
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_TEXTVIEW, "保存", CLICK_SELF, true);
                                                        if (!find) {
                                                            release(false);
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
                                                                            release(false);
                                                                            return;
                                                                        }
                                                                        clickSomeWhere(mFindTargetNode.getParent().getChild(1));
                                                                        mHandler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                release(true);
                                                                            }
                                                                        }, 3000);
                                                                    }
                                                                }, 3000);
                                                            }
                                                        }, 5000);
                                                    }
                                                }, 2000);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                release(false);
                                            }
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        } else if (type == TYPE_ADD_GROUP_PEOPLE) {
                            boolean find = findTargetNode(NODE_BUTTON, "邀请", CLICK_SELF, true);
                            if (find) {
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                release(true);
                                            }
                                        }, 3000);
                                    }
                                }, 3000);
                            } else {
                                release(true);
                            }
                        } else if (type == TYPE_DELETE_GROUP_PEOPLE) {
                            findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release(true);
                                }
                            }, 3000);
                        } else if (type == TYPE_SEND_BATCH) {
                            doSendBatchMessage();
                        }
                    }
                }, 10000);
            }
        }, 3000);
    }

    private void doSendBatchMessage() {
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            JSONObject o = new JSONObject(content);
            int flag = o.optInt("flag");
            if (flag == 1) {
                final boolean find = findTargetNode(NODE_BUTTON, "分享", CLICK_SELF, true);
                if (!find) {
                    release(false);
                    return;
                }
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final boolean find = findTargetNode(NODE_BUTTON, "留在微信", CLICK_SELF, true);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                release(find);
                            }
                        }, 3000);
                    }
                }, 3000);
            } else if (flag == 2) {
                JSONObject msg = o.optJSONArray("messages").optJSONObject(0);
                int type = msg.optInt("type");
                final String text = msg.optString("content");
                if (type == 1) {
                    // 判断文本框是不是有文字
                    clearAndPasteEditText(1, text);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final boolean find = findTargetNode(NODE_BUTTON, "发送", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release(find);
                                }
                            }, 3000);
                        }
                    }, 3000);
                } else if (type == 2) {
                    new Thread() {
                        @Override
                        public void run() {
                            Bitmap bmp = ImageLoader.getInstance().loadImageSync(text);
                            if (bmp == null) {
                                release(false);
                                return;
                            }
                            saveImage(getApplication(), bmp, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
                                    if (mFindTargetNode == null) {
                                        release(false);
                                        return;
                                    }
                                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            final boolean find = findTargetNode(NODE_TEXTVIEW, "你可能要发送的照片：", CLICK_NONE, true);
                                            if (!find) {
                                                release(false);
                                                return;
                                            }
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mHandler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                                                                    if (!find) {
                                                                        release(false);
                                                                        return;
                                                                    }
                                                                    mHandler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            findTargetNode(NODE_RELATIVELAYOUT, 1);
                                                                            if (mFindTargetNode == null) {
                                                                                release(false);
                                                                                return;
                                                                            }
                                                                            mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                                                            mHandler.postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    final boolean find = findTargetNode(NODE_BUTTON, "完成", CLICK_SELF, true);
                                                                                    mHandler.postDelayed(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {
                                                                                            release(find);
                                                                                        }
                                                                                    }, 4000);
                                                                                }
                                                                            }, 3000);
                                                                        }
                                                                    }, 3000);
                                                                }
                                                            }, 3000);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 3000);
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        }
                    }.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void doCheckFriend1(final AccessibilityNodeInfo rootNode) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (rootNode == null) {
                    return;
                }
                int count = rootNode.getChildCount();
                for (int i = 0; i < count; i++) {
                    AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
                    if (nodeInfo == null) {
                        continue;
                    }
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
            if (nodeInfo.getClassName().equals("android.widget.ImageButton")) {
                if (i - 2 < 0) {
                    continue;
                }
                int index = i - 2;
                if (index < 0) {
                    continue;
                }
                AccessibilityNodeInfo prevNode = rootNode.getChild(index);
                if (prevNode == null) {
                    continue;
                }
                if (prevNode.getText() == null) {
                    continue;
                }
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
            int undoCount = zbusRecvsQueue.size() + 1;
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
                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String targetSender = actions.get(currentActionID).sender;
                            searchTargetInWxHomePage(1, targetSender, true);
                        }
                    }, 3000);
                }
            }
        } else if (actionType == TYPE_SET_COLLECTOR) {
            sendTextOnly("设置成功！", true);
        } else if (actionType == TYPE_COLLECTOR_FORWARDING) {
            // 找到最后一张链接，点击转发给某人
            String collector = getSharedPreferences("collector", 0).getString("collector", "转发使者");
            if (TextUtils.isEmpty(collector)) {
                toast("您还没有设置转发对象");
                //回复给微信
                sendTextOnly("您还没有设置转发对象，设置方法：请输入“设置转发对象：昵称”", true);
                return;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("test", "=================findLastMsgViewInListView====================");

                    //判断是群还是个人
                    findTargetNode(NODE_TEXTVIEW, 1);
                    String title = mFindTargetNode.getText().toString();
                    final String clientGroupId = isFromPrivateOrGroup(title);
                    Log.d("test", "isFromPrivateOrGroup clientGroupId = " + clientGroupId);

                    findTargetNode(NODE_FRAMELAYOUT, Integer.MAX_VALUE);
                    if (mFindTargetNode == null) {
                        Log.d("test", "没有找到最后一条消息。。。");
                        release(false);
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
                                        doLongClickLastMsg(clientGroupId);
                                    }
                                }, 3000);
                            }
                        }, 5000);
                    } else {
                        doLongClickLastMsg(clientGroupId);
                    }
                }
            }, 3000);
        } else {
            Log.d("test", "没有匹配的消息，直接release");
            release(false);
        }
    }

    //"":既不是个人也不是群组
    //-1：个人
    //string:group
    private String isFromPrivateOrGroup(String name) {
        Log.d("test", "isFromPrivateOrGroup title = " + name);
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        int lastLeft = name.lastIndexOf("(");
        int lastRight = name.lastIndexOf(")");
        if (lastRight != -1 && lastLeft != -1 && (lastRight - lastLeft > 1)) {
            name = name.substring(0, lastLeft);
        }
        Friend f = getOneFriendFromWechatDB_Name(name);
        if (f != null) {
            return "-1";
        }
        Group g = getOneGroupFromWechatDB_ClientGroupName(name);
        if (g == null) {
            return "";
        }
        return g.clientGroupId;
    }

    private void searchTargetInWxHomePage(final int actionType, final String target, final boolean canRelease) {
        Log.d("test", "searchTargetInWxHomePage target = " + target);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_TEXTVIEW, Integer.MAX_VALUE);//放大镜
                if (mFindTargetNode == null) {
                    release(false);
                    return;
                }
                mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String temp = target;
                        boolean equal = true;
                        if (target.length() > 13) {
                            temp = target.substring(0, 13);
                            equal = false;
                        }
                        //zhengkang add 1122
                        if (temp.contains("转发使者")) {
                            equal = false;
                        }

                        findTargetNode(NODE_EDITTEXT, temp);
                        final String finalTemp = temp;
                        final boolean finalEqual = equal;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findTargetNode(NODE_TEXTVIEW, finalTemp, CLICK_PARENT, finalEqual);
                                if (mFindTargetNode == null) {
                                    if (canRelease) {
                                        release(false);
                                    }
                                    return;
                                }
                                enterChatView(actionType, target);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        });
    }

    private void searchTargetInWxGroupPage(final int actionType, String groupName, final boolean canRelease) {
        if (groupName.length() > 13) {
            groupName = groupName.substring(0, 13);
        }
        if (tongxunluTextView == null) {
            release(false);
            return;
        }
        final String finalGroupName = groupName;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    while (!checkIsTongxunluTop()) {
                                        sleep(1000);
                                        //双击到顶部
                                        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                        sleep(5000);
                                        if (!actioningFlag) {
                                            break;
                                        }
                                    }
                                    //群聊
                                    clickSomeWhere(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() * 180 / 762);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean find = findTargetNode(NODE_TEXTVIEW, "群聊", 1, true);
                                            if (!find) {
                                                release(false);
                                                return;
                                            }
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findTargetNode(NODE_EDITTEXT, finalGroupName);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            findTargetNode(NODE_TEXTVIEW, finalGroupName, CLICK_PARENT, false);
                                                            if (mFindTargetNode == null) {
                                                                if (canRelease) {
                                                                    release(false);
                                                                }
                                                                return;
                                                            }
                                                            enterChatView(actionType, finalGroupName);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 3000);
                                        }
                                    }, 3000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    release(false);
                                }
                            }
                        }.start();
                    }
                }, 3000);
            }
        });
    }

    private void doSequeSendText() {
        final ArrayList<ReturnMessage> returnMessages = actions.get(currentActionID).returnMessages;
        new Thread() {
            @Override
            public void run() {
                try {
                    int count = returnMessages.size();
                    for (int i = 0; i < count; i++) {
                        ReturnMessage rm = returnMessages.get(i);
                        sendTextOnly(rm.content, false);
                        sleep(5000);
                    }
                    release(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }.start();
    }

    private boolean qunliao;
    private boolean biaoqian;
    private boolean gongzhonghao;

    private boolean checkIsTongxunluTop() {
        qunliao = false;
        biaoqian = false;
        gongzhonghao = false;
        doCheckPage(getRootInActiveWindow());
        boolean isTongxunluTop = qunliao && biaoqian && gongzhonghao;
        Log.d("test", "isTongxunluTop = " + isTongxunluTop);
        return isTongxunluTop;
    }

    private boolean weixin;
    private boolean tongxunlu;
    private boolean faxian;
    private boolean wo;

    private boolean checkIsWxHomePage() {
        weixin = false;
        tongxunlu = false;
        faxian = false;
        wo = false;
        tongxunluTextView = null;
        woTextView = null;
        faxianView = null;
        doCheckPage(getRootInActiveWindow());
        boolean isWxHomePage = weixin && tongxunlu && faxian && wo;
        Log.d("test", "isWxHomePage = " + isWxHomePage);
        return isWxHomePage;
    }


    private AccessibilityNodeInfo tongxunluTextView;
    private AccessibilityNodeInfo woTextView;
    private AccessibilityNodeInfo faxianView;

    private void doCheckPage(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) {
            return;
        }
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            //Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            //Log.d("test", "nodeInfo.getText() = " + nodeInfo.getText());
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
                        String realWxNo = text.replace("微信号：", "");
                        String loginWxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                        if (realWxNo.equals(loginWxNo)) {
                            Log.d("test", "微信号一致");
                        } else {
                            toast("机器人的微信号和实际微信号不一致！！！");
                        }
                    } else if (text.equals("群聊")) {
                        qunliao = true;
                    } else if (text.equals("标签")) {
                        biaoqian = true;
                    } else if (text.equals("公众号")) {
                        gongzhonghao = true;
                    }
                }
            }
            doCheckPage(nodeInfo);
        }
    }

    // 好友、群的聊天窗口：1聊天 其他：actionType
    // 注意可能有循环的操作，比如删除好友、群发公告，不能release
    private void enterChatView(final int actionType, final String target) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (actionType == 1) {
                    //开始聊天
                    doChatByActionType();
                } else if (actionType == TYPE_DELETE_FRIEND) {
                    deleteFriend(target);
                } else if (actionType == TYPE_ADD_GROUP_PEOPLE
                        || actionType == TYPE_DELETE_GROUP_PEOPLE
                        || actionType == TYPE_FIX_GROUP_NAME
                        || actionType == TYPE_FIX_GROUP_NOTICE
                        || actionType == TYPE_DELETE_GROUP_CHAT
                        || actionType == TYPE_SAVE_GROUP
                        || actionType == TYPE_GROUP_QRCODE
                        || actionType == TYPE_TRANSFER_MASTER) {
                    groupRelative(actionType);
                } else if (actionType == TYPE_AT_GROUP_PEOPLE) {
                    //循环开始艾特人
                    startAtPeople();
                } else if (actionType == TYPE_FIX_FRIEND_NICKNAME) {
                    //好友信息
                    fixFriendNickname(target);
                } else if (actionType == TYPE_NOTIFY_RESULT) {
                    doNotifyResult();
                } else if (actionType == TYPE_SCRIPT) {
                    //根据脚本，完成要发送的命令
                    doScript();
                } else if (actionType == TYPE_CHAT_IN_GROUP) {
                    try {
                        String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                        String message = new JSONObject(content).optString("message");
                        sendTextOnly(message, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        release(false);
                    }
                } else if (actionType == TYPE_BROWSER_MESSAGE) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                sleep(10000);
                                String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                JSONObject o = new JSONObject(content);
                                String target = o.optString("target");
                                final String imgPath = o.optString("imgPath");
                                int count = target.endsWith("@chatroom") ? 5 : 3;
                                for (int i = 0; i < count; i++) {
                                    execRootCmdSilent("input swipe 360 300 360 900");
                                    sleep(3000);
                                }
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //补救完成
                                        Utils.saveImgJobDone(imgPath, getApplicationContext());
                                        release(true);
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                                release(false);
                            }
                        }
                    }.start();
                }
            }
        }, 5000);
    }

    private void groupRelative(final int actionType) {
        //群信息
        findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
        if (mFindTargetNode == null) {
            release(false);
            return;
        }
        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //聊天信息
                if (actionType == TYPE_ADD_GROUP_PEOPLE || actionType == TYPE_DELETE_GROUP_PEOPLE) {
                    addOrDeleteGroupPeople(actionType);
                } else if (actionType == TYPE_FIX_GROUP_NAME) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("群聊名称")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            fixGroupName();
                        }
                    }.start();
                } else if (actionType == TYPE_FIX_GROUP_NOTICE) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("群公告")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            fixGroupNotice();
                        }
                    }.start();
                } else if (actionType == TYPE_DELETE_GROUP_CHAT) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("删除并退出")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final boolean find1 = findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                    final boolean find2 = findTargetNode(NODE_TEXTVIEW, "离开群聊", CLICK_PARENT, true);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            release(find1 || find2);
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        }
                    }.start();
                } else if (actionType == TYPE_SAVE_GROUP) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("保存到通讯录")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            clickSomeWhere(mFindTargetNode.getParent().getChild(1));
                            if (MainActivity.instance != null) {
                                MainActivity.instance.getAllGroups(true);
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    release(true);
                                }
                            }, 3000);
                        }
                    }.start();
                } else if (actionType == TYPE_GROUP_QRCODE) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("群二维码")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findTargetNode(NODE_IMAGEBUTTON, 1);
                                    if (mFindTargetNode == null) {
                                        release(false);
                                        return;
                                    }
                                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean find = findTargetNode(NODE_TEXTVIEW, "保存到手机", CLICK_PARENT, true);
                                            if (!find) {
                                                release(false);
                                                return;
                                            }
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    boolean ret = getQRCodeFromSdcard();
                                                    release(ret);
                                                }
                                            }.start();
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        }
                    }.start();
                } else if (actionType == TYPE_TRANSFER_MASTER) {
                    new Thread() {
                        @Override
                        public void run() {
                            while (!scrollAndFindTarget("群管理")) {
                                if (!actioningFlag) {
                                    release(false);
                                    break;
                                }
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findTargetNode(NODE_TEXTVIEW, "群主管理权转让", CLICK_PARENT, true);
                                    if (!find) {
                                        release(false);
                                        return;
                                    }
                                    doTransferMaster();
                                }
                            }, 3000);
                        }
                    }.start();
                }
            }
        }, 3000);
    }

    private boolean scrollAndFindTarget(String text) {
        boolean find = findTargetNode(NODE_TEXTVIEW, text, CLICK_PARENT, true);
        if (!find) {
            execRootCmdSilent("input swipe 360 900 360 300");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            find = findTargetNode(NODE_TEXTVIEW, text, CLICK_PARENT, true);
        }
        Log.d("test", "scrollAndFindTarget find = " + find);
        return find;
    }

    private void doTransferMaster() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    final String text = o.optString("name");
                    findTargetNode(NODE_EDITTEXT, text);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean find = findTargetNode(NODE_TEXTVIEW, text, CLICK_PARENT, true);
                            if (!find) {
                                release(false);
                                return;
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final boolean find = findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            release(find);
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        }
                    }, 2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }, 3000);
    }


    private boolean getQRCodeFromSdcard() {
        qrCodeUrl = "";
        String path = WEIXIN;
        File[] files = new File(path).listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        File latestFile = null;
        long latestModified = 0L;
        for (File f : files) {
            if (f.lastModified() > latestModified) {
                latestModified = f.lastModified();
                latestFile = f;
            }
        }
        try {
            final String ret = UploadUtil.uploadFile(latestFile, clientUrl + "/common/file?origin=true", latestFile.getName());
            JSONObject o = new JSONObject(ret);
            qrCodeUrl = o.optJSONObject("data").optString("url");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private void doScript() {
        //{"cmd":"scriptCmd","scripts":[{"member":"kangkangbaba" , "time":"5" , "content":"我给小孩买了一个玩具"},{"member":"zskf_18" , "time":"15" , "content":"什么玩具呀"}],"clientGroupId":"4352489286@chatroom"}
        try {
            String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            JSONObject o = new JSONObject(content);
            JSONArray scripts = o.getJSONArray("scripContent");
            final ArrayList<Script> myScripts = new ArrayList<>();
            int count = scripts.length();
            //找出自己负责的脚本，可能有N条，完成之后才release
            for (int i = 0; i < count; i++) {
                JSONObject temp = scripts.getJSONObject(i);
                String member = temp.getString("wxNo");
                if (member.equals(wxNo)) {
                    myScripts.add(new Script(temp.getString("content"), temp.getInt("intervals"), temp.getInt("type")));
                }
            }
            Log.d("test", "我负责的脚本有" + myScripts.size() + "条");
            resetMaxReleaseTime((myScripts.get(myScripts.size() - 1).time + 15) * 1000);

            new Thread() {
                @Override
                public void run() {
                    int i = 0;
                    while (i < myScripts.size()) {
                        Script script = myScripts.get(i);
                        try {
                            if (i == 0) {
                                sleep(script.time * 1000 - 2000);
                            } else {
                                sleep(script.time * 1000 - 4000);
                            }
                            if (script.type == 1) {
                                sendTextOnly(script.content, false);
                            } else {
                                sendImageOnly2(script.content);
                            }
                            if (script.type == 1) {
                                sleep(4000);
                            } else {
                                sleep(10000);
                            }
                            i++;
                        } catch (Exception e) {
                            e.printStackTrace();
                            release(false);
                        }
                    }
                    release(true);
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void deleteFriend(final String target) {
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
                        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                boolean find = findTargetNode(NODE_TEXTVIEW, "删除", CLICK_PARENT, true);
                                if (!find) {
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findTargetNode(NODE_BUTTON, "删除", CLICK_SELF, true);
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 3000);
    }

    private void fixGroupName() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    String text = o.optString("name");
                    boolean has = findTargetNode(NODE_TEXTVIEW, "编辑", CLICK_SELF, true);
                    long sleepTime = 0;
                    if (has) {
                        sleepTime = 3000;
                    }
                    final String finalText = text;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //1.先执行删除键
                            clearAndPasteEditText(1, finalText);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final boolean find = findTargetNode(NODE_TEXTVIEW, "保存|发布|完成", CLICK_SELF, true);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!find) {
                                                release(false);
                                                return;
                                            }
                                            findTargetNode(NODE_BUTTON, "发布", CLICK_SELF, true);
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    release(true);
                                                }
                                            }, 3000);
                                        }
                                    }, 5000);
                                }
                            }, 3000);
                        }
                    }, sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }, 3000);
    }

    private void fixGroupNotice() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                    JSONObject o = new JSONObject(content);
                    String text = o.optString("notice");
                    boolean has = findTargetNode(NODE_TEXTVIEW, "编辑", CLICK_SELF, true);
                    long sleepTime = 0;
                    if (has) {
                        sleepTime = 3000;
                    }
                    final String finalText = text;
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int length = getTextLengthInEditText(1, finalText);
                            resetMaxReleaseTime(length * 2 * 1000 + 30000);
                            //1.先执行删除键
                            clearAndPasteEditText(1, finalText);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final boolean find = findTargetNode(NODE_TEXTVIEW, "保存|发布|完成", CLICK_SELF, true);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!find) {
                                                return;
                                            }
                                            final boolean find = findTargetNode(NODE_BUTTON, "发布", CLICK_SELF, true);
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    release(find);
                                                }
                                            }, 3000);
                                        }
                                    }, 5000);
                                }
                            }, 3000);
                        }
                    }, sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                    release(false);
                }
            }
        }, 3000);
    }

    private void fixFriendNickname(final String friend) {
        findTargetNode(NODE_IMAGEBUTTON, Integer.MAX_VALUE);
        if (mFindTargetNode == null) {
            release(false);
            return;
        }
        mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final boolean find = findTargetNode(NODE_TEXTVIEW, friend, CLICK_PARENT, false);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!find) {
                            release(false);
                            return;
                        }
                        boolean find = findTargetNode(NODE_TEXTVIEW, "设置备注和标签", CLICK_SELF, true);
                        if (!find) {
                            find = findTargetNode(NODE_TEXTVIEW, "标签", CLICK_PARENT, true);
                        }
                        final boolean finalFind = find;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!finalFind) {
                                    release(false);
                                    return;
                                }
                                final boolean find = findTargetNode(NODE_TEXTVIEW, friend, CLICK_SELF, false);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!find) {
                                            release(false);
                                            return;
                                        }
                                        try {
                                            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                                            Log.d("test", "content = " + content);
                                            JSONObject o = new JSONObject(content);
                                            String newName = o.optString("newName");

                                            clearAndPasteEditText(1, newName);

                                            boolean find = findTargetNode(NODE_TEXTVIEW, "完成", CLICK_SELF, true);
                                            if (!find) {
                                                release(false);
                                                return;
                                            }
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    findTargetNode(NODE_BUTTON, "发布", CLICK_SELF, true);
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            release(true);
                                                        }
                                                    }, 3000);
                                                }
                                            }, 5000);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            release(false);
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
                    final JSONObject o = new JSONObject(content);
                    JSONArray members = o.getJSONArray("name");
                    int count = members.length();

                    resetMaxReleaseTime(count * 30000);

                    for (int i = 0; i < count; i++) {
                        final String member = members.getString(i);
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
                                                    }, 3000);
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
                                                                    execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_DEL);
                                                                }
                                                            }, 2000);
                                                        }
                                                    }, 2000);
                                                }
                                            }
                                        }, 2000);
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
                    release(false);
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
        if (rootNode == null) {
            return false;
        }
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if (nodeInfo.getClassName().equals(className)) {
                nodeIndex++;

                if (className.equals(NODE_TEXTVIEW) || className.equals(NODE_BUTTON) || className.equals(NODE_CHECKBOX) || className.equals(NODE_RADIOBUTTON)) {
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
                        || className.equals(NODE_LINEARLAYOUT)
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

    private void clearAndPasteEditText(int index, String newContent) {
        Log.d("test", "clearAndPasteEditText");
        int length = getTextLengthInEditText(index, newContent);
        for (int i = 0; i < length; i++) {
            execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_DEL);
        }
        if (length != -1) {
            findTargetNode(NODE_EDITTEXT, newContent);
        }
    }

    public String getTextInEditText(int index) {
        findTargetNode(NODE_EDITTEXT, index);
        if (mFindTargetNode == null) {
            return "";
        }
        String text = mFindTargetNode.getText().toString();
        return text;
    }

    public int getTextLengthInEditText(int index, String compare) {
        findTargetNode(NODE_EDITTEXT, index);
        if (mFindTargetNode == null) {
            return 0;
        }
        CharSequence cs = mFindTargetNode.getText();
        if (cs == null) {
            return 0;
        }
        String text = cs.toString();
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

    private ArrayList<AccessibilityNodeInfo> imageViewNodes = new ArrayList<>();

    private void addOrDeleteGroupPeople(final int type) {
        int index = 0;
        try {
            String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
            JSONObject o = new JSONObject(content);
            index = o.optInt("indexs");
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
            return;
        }

        final int finalIndex = index;
        new Thread() {
            @Override
            public void run() {
                //查找imageview，直到发现“群聊名称”
                imageViewNodes.clear();
                while (true) {
                    findImageViewNode(getRootInActiveWindow());
                    boolean findGroupName = findTargetNode(NODE_TEXTVIEW, "查看全部群成员|群聊名称", CLICK_NONE, true);
                    if (findGroupName) {
                        break;
                    }
                    if (!actioningFlag) {
                        break;
                    }
                    execRootCmdSilent("input swipe 360 900 360 300");
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int count = imageViewNodes.size();
                Log.d("test", "imageviews个数：" + count);
                if (count == 0) {
                    retryAction(finalIndex, true);
                    return;
                }
                //加人：是群主倒数第2个，不是群主，倒数第一个；踢人肯定是倒数第一个。
                AccessibilityNodeInfo targetNode = null;
                if (type == TYPE_DELETE_GROUP_PEOPLE) {
                    targetNode = imageViewNodes.get(count - 1);
                } else if (type == TYPE_ADD_GROUP_PEOPLE) {
                    try {
                        String content = new String(Base64.decode(actions.get(currentActionID).content.getBytes(), NO_WRAP));
                        JSONObject o = new JSONObject(content);
                        String clientGroupId = o.optString("clientGroupId");
                        Group g = getOneGroupFromWechatDB_ClientGroupId(clientGroupId, true);
                        if (g == null) {
                            release(false);
                            return;
                        }
                        String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                        if (g.masterWxNo.equals(wxNo)) {
                            targetNode = imageViewNodes.get(count - 2);
                        } else {
                            targetNode = imageViewNodes.get(count - 1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        release(false);
                        return;
                    }
                }
                if (targetNode == null) {
                    release(false);
                    return;
                }
                targetNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        checkFriendInListView2();
                    }
                }, 3000);
            }
        }.start();
    }

    private boolean findImageViewNode(AccessibilityNodeInfo rootNode) {
        if (rootNode == null) {
            return false;
        }
        int count = rootNode.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            if (nodeInfo.getClassName().equals("android.widget.ImageView") && nodeInfo.getParent().getClassName().equals("android.widget.RelativeLayout")) {
                imageViewNodes.add(nodeInfo);
            }
            if (findImageViewNode(nodeInfo)) {
                return true;
            }
        }
        return false;
    }

    private void doLongClickLastMsg(final String clientGroupId) {
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
                            release(false);
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                final String collector = getSharedPreferences("collector", 0).getString("collector", "转发使者");
                                findTargetNode(NODE_EDITTEXT, collector);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean find = findTargetNode(NODE_TEXTVIEW, collector, CLICK_PARENT, false);
                                        if (!find) {
                                            release(false);
                                            return;
                                        }
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                String content = getCollectorForwardingContent(clientGroupId);
                                                boolean find = findTargetNode(NODE_EDITTEXT, content);
                                                if (!find) {
                                                    Log.d("test", "粘贴不上，不发过去");
                                                    release(false);
                                                    return;
                                                }
                                                mHandler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        boolean find = findTargetNode(NODE_BUTTON, "发送", CLICK_SELF, true);
                                                        if (!find) {
                                                            release(false);
                                                            return;
                                                        }
                                                        mHandler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                release(true);
                                                            }
                                                        }, 4000);
                                                    }
                                                }, 3000);
                                            }
                                        }, 3000);
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 3000);
            }
        }, 0);
    }

    private void clickSomeWhere(AccessibilityNodeInfo node) {
        Log.d("test", "clickSomeWhere node = " + node.getClassName());
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        execRootCmdSilent("input tap " + x + " " + y);
    }

    private void clickSomeWhere(int x, int y) {
        execRootCmdSilent("input tap " + x + " " + y);
    }

    private void longClickSomeWhere(AccessibilityNodeInfo node) {
        Log.d("test", "clickSomeWhere node = " + node.getClassName());
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        int x = r.width() / 2 + r.left;
        int y = r.height() / 2 + r.top;
        execRootCmdSilent("input touchscreen swipe " + x + " " + y + " " + x + " " + y + " 2000");
    }

    private void longClickSomeWhere(int x, int y) {
        execRootCmdSilent("input touchscreen swipe " + x + " " + y + " " + x + " " + y + " 2000");
    }

    private void toast(String txt) {
        if (MainActivity.instance != null) {
            MainActivity.instance.toast(txt);
        }
    }

    //自动加好友
    private String nickname;
    private String remark;
    private String lastNickname = "";

    private boolean hasAcceptButton(final boolean checkRepeat) {
        findTargetNode(NODE_BUTTON, "接受", CLICK_NONE, true);
        if (mFindTargetNode == null) {
            return false;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (checkRepeat) {
                    AccessibilityNodeInfo nicknameNode = mFindTargetNode.getParent().getChild(0);
                    nickname = nicknameNode.getText().toString();
                    Log.d("test", "nickname = " + nickname);
                    if (lastNickname.equals(nickname)) {
                        //重复nickname，执行删除操作
                        deleteUselessNickname(nicknameNode);
                        return;
                    }
                    lastNickname = nickname;
                }
                int friendCount = getSharedPreferences("friendCount", 0).getInt("friendCount", 0);
                Log.d("test", "friendCount = " + friendCount);
                if (friendCount > MAX_FRIENDS) {
                    final String backup = getSharedPreferences("backup", 0).getString("backup", DEFAULT_BACKUP);
                    if (backup.equals("") || backup.equals("null")) {
                        release(true);
                        return;
                    }
                    mFindTargetNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            findTargetNode(NODE_BUTTON, "回复", CLICK_SELF, true);
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    String response = DEFAULT_BACKUP + "，请加备用微信号：" + backup;
                                    findTargetNode(NODE_EDITTEXT, response);
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            findTargetNode(NODE_BUTTON, "确定", CLICK_SELF, true);
                                            mHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    release(true);
                                                }
                                            }, 3000);
                                        }
                                    }, 3000);
                                }
                            }, 3000);
                        }
                    }, 3000);
                } else {
                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //1.粘贴后，系统会马上带上原名
                            remark = getParentRemark(getApplication(), 1);
                            findTargetNode(NODE_EDITTEXT, remark);

                            //2.过滤特殊字符，使用newRemark
                            String current = getTextInEditText(1);
                            final String newRemark = Utils.getNewRemark(getApplicationContext(), current);
                            if (!current.equals(newRemark)) {
                                int length = getTextLengthInEditText(1, "");
                                execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_MOVE_END);
                                for (int i = 0; i < length; i++) {
                                    execRootCmdSilent("input keyevent  " + KeyEvent.KEYCODE_DEL);
                                }
                                findTargetNode(NODE_EDITTEXT, newRemark);
                            }

                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    boolean find = findTargetNode(NODE_TEXTVIEW, "完成", CLICK_SELF, true);
                                    sendFriendInfoDelay();

                                    if (find) {
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (adding_missing_fish) {
                                                    //漏网之鱼不再发消息
                                                    performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                                } else {
                                                    //zhengkang 20180921
                                                    new Thread() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                sleep(5000);
                                                                String links = getSharedPreferences("links", 0).getString("links", "");
                                                                if (TextUtils.isEmpty(links)) {
                                                                    release(true);
                                                                    return;
                                                                }
                                                                final JSONArray array = new JSONArray(links);
                                                                int count = array.length();
                                                                for (int i = 0; i < count; i++) {
                                                                    JSONObject o = array.getJSONObject(i);
                                                                    sendLink20190322(o.toString(), newRemark);
                                                                    sleep(30000);
                                                                }
                                                                release(true);
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                                release(false);
                                                            }
                                                        }
                                                    }.start();
                                                    if (true) {
                                                        return;
                                                    }
                                                    int role = getSharedPreferences("role", 0).getInt("role", ROLE_KEFU);
                                                    if (role == ROLE_WODI) {
                                                        release(true);
                                                        return;
                                                    }
                                                    //找到发消息，发一段话
                                                    boolean find = findTargetNode(NODE_BUTTON, "发消息", CLICK_SELF, true);
                                                    if (!find) {
                                                        release(false);
                                                        return;
                                                    }
                                                    mHandler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            //1.找到文本框输入文字发送
                                                            String welcome = getSharedPreferences("welcome", 0).getString
                                                                    ("welcome", DEFAULT_WELCOME);
                                                            sendTextOnly(welcome, true);
                                                            //2.发送小程序码
                                                            //sendMiniProgramCode();
                                                            //3.上报好友，0613不再调用
                                                            //String current = System.currentTimeMillis() + "";
                                                            //ArrayList<Friend> friends = new ArrayList<>();
                                                            //friends.add(new Friend(nickname, remark + " " + nickname, current, current));
                                                            //Utils.uploadFriend(getApplication(), friends);
                                                        }
                                                    }, 3000);
                                                }
                                            }
                                        }, 5000);
                                    } else {
                                        if (adding_missing_fish) {
                                            performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                                        } else {
                                            release(true);
                                        }
                                    }
                                }
                            }, 3000);
                        }
                    }, 3000);
                }
            }
        });
        return true;
    }

    private void sendTextOnly(String reply, boolean release) {
        reply = Utils.replaceReply(reply);
        final String finalReply = reply;
        Log.d("test", "sendTextOnly finalReply = " + finalReply);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_EDITTEXT, finalReply);
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
        }, 3000);

        if (release) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    release(true);
                }
            }, 5000);
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
        File appDir = new File(DCIM);
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
            Log.d("test", "path = " + file.getAbsolutePath());
            Log.d("test", "name = " + file.getName());
            Log.d("test", "size = " + file.length());

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

    private void sendImageOnly(String url, boolean multiple) {
        //1.下载图片
        Bitmap bmp = ImageLoader.getInstance().loadImageSync(url);
        if (bmp == null) {
            release(false);
            return;
        }
        //2.保存图片
        String localPath = saveImage(getApplication(), bmp, false);
        if (localPath == null) {
            release(false);
            return;
        }

        Log.d("test", "sendImageOnly");
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath(localPath);
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);

        if (multiple) {
            doShareToWechatFriend2();
        } else {
            doShareToWechatFriend();
        }
    }

    public void sendImageOnly2(String url) {
        //1.下载图片
        Bitmap bmp = ImageLoader.getInstance().loadImageSync(url);
        if (bmp == null) {
            release(false);
            return;
        }
        //2.保存图片
        String localPath = saveImage(getApplication(), bmp, true);
        if (localPath == null) {
            release(false);
            return;
        }
        Log.d("test", "sendImageOnly2");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                findTargetNode(NODE_IMAGEBUTTON, 3);
                if (mFindTargetNode != null) {
                    mFindTargetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            final boolean find = findTargetNode(NODE_TEXTVIEW, "相册", CLICK_PARENT, true);
                            if (find) {
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findTargetNode(NODE_CHECKBOX, 1);
                                        if (mFindTargetNode != null) {
                                            clickSomeWhere(mFindTargetNode);
                                            findTargetNode(NODE_TEXTVIEW, "发送", CLICK_SELF, false);
                                        }
                                    }
                                }, 3000);
                            } else {
                                boolean find2 = findTargetNode(NODE_TEXTVIEW, "你可能要发送的照片：", CLICK_PARENT, true);
                                if (find2) {
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            findTargetNode(NODE_TEXTVIEW, "发送", CLICK_SELF, true);
                                        }
                                    }, 3000);
                                }
                            }
                        }
                    }, 3000);
                }
            }
        });
    }

    private void sendVideoOnly() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("视频");
        sp.setTitle("视频");
        sp.setUrl(actions.get(currentActionID).returnMessages.get(0).content);
        sp.setImagePath(KWApplication.defaultVideoIcon);
        sp.setShareType(Platform.SHARE_VIDEO);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);

        doShareToWechatFriend();
    }

    private void sendFileOnly(String url, final String fileName, final boolean multiple) {
        //1.下载
        final String savedFilePath = KWApplication.ROOT + "downloads/" + fileName;
        if (!new File(savedFilePath).exists()) {
            release(false);
            return;
        }
        Log.d("test", "sendFileOnly");
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(fileName);
        sp.setFilePath(savedFilePath);
        String imagePath = KWApplication.defaultFileIcon;
        if (fileName.toLowerCase().endsWith("ppt") || fileName.toLowerCase().endsWith("pptx")) {
            imagePath = KWApplication.defaultPPTIcon;
        } else if (fileName.toLowerCase().endsWith("pdf")) {
            imagePath = KWApplication.defaultPDFIcon;
        } else if (fileName.toLowerCase().endsWith("doc") || fileName.toLowerCase().endsWith("docx")) {
            imagePath = KWApplication.defaultWordIcon;
        } else if (fileName.toLowerCase().endsWith("xls") || fileName.toLowerCase().endsWith("xlsx")) {
            imagePath = KWApplication.defaultXlsIcon;
        } else if (fileName.toLowerCase().endsWith("zip") || fileName.toLowerCase().endsWith("rar")) {
            imagePath = KWApplication.defaultZIPIcon;
        }
        sp.setImagePath(imagePath);
        sp.setShareType(Platform.SHARE_FILE);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);
        if (multiple) {
            doShareToWechatFriend2();
        } else {
            doShareToWechatFriend();
        }
    }

    private void sendLinkOnly(String content, boolean multiple) {
        try {
            JSONObject contentO = new JSONObject(content);
            String title = contentO.optString("title");
            String describe = contentO.optString("description");
            if (TextUtils.isEmpty(describe)) {
                describe = contentO.optString("content");
            }
            String imageUrl = contentO.optString("imgUrl");
            String url = contentO.optString("url");

            //1.下载图片
            String localPath = null;
            if (!TextUtils.isEmpty(imageUrl)) {
                Bitmap bmp = ImageLoader.getInstance().loadImageSync(imageUrl);
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

            if (multiple) {
                doShareToWechatFriend2();
            } else {
                doShareToWechatFriend();
            }
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }
    }

    private void shareToWechatMoments(final String id, final String content, final String original) {
        try {
            JSONObject contentO = new JSONObject(content);
            String title = contentO.optString("title");
            String description = contentO.optString("description");
            String imageUrl = contentO.optString("imgUrl");
            String url = contentO.optString("url");
            int type = contentO.optInt("type");
            int index = new JSONObject(original).optInt("indexs");

            if (type == 2) {
                String[] imageArray = imageUrl.replace("[", "").replace("]", "").split(",");
                //图文
                ArrayList<Uri> imageUris = new ArrayList<>();
                for (String anImageArray : imageArray) {
                    String image = anImageArray.replace("\"", "");
                    File f = new File(KWApplication.DOWNLOAD + Utils.getMD5(image) + ".jpg");
                    if (f.exists()) {
                        imageUris.add(Uri.fromFile(f));
                    }
                }
                Log.d("test", "imageUris count = " + imageUris.size());
                Intent intent = new Intent();
                ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putExtra("Kdescription", description);
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                doShareToWechatMoments(id, description, type, original, index);
            } else if (type == 1) {
                //网文
                String localPath = null;
                if (!TextUtils.isEmpty(imageUrl)) {
                    localPath = KWApplication.DOWNLOAD + Utils.getMD5(imageUrl) + ".jpg";
                }
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setTitle(title);
                sp.setText(description);
                sp.setUrl(url);
                if (!TextUtils.isEmpty(localPath)) {
                    sp.setImagePath(localPath);
                }
                sp.setShareType(Platform.SHARE_WEBPAGE);
                Platform wx = ShareSDK.getPlatform(WechatMoments.NAME);
                wx.share(sp);
                doShareToWechatMoments(id, description, type, original, index);
            }
        } catch (Exception e) {
            e.printStackTrace();
            release(false);
        }

    }

    public boolean isPasteContent(String remark) {
        if (TextUtils.isEmpty(remark)) {
            return true;
        }
        findTargetNode(NODE_EDITTEXT, 1);
        if (mFindTargetNode == null) {
            Log.d("test", "===>找不到文本框");
            return false;
        } else {
            CharSequence cs = mFindTargetNode.getText();
            if (cs == null) {
                Log.d("test", "===>文本框Text为null");
                return false;
            }
            String content = mFindTargetNode.getText().toString();
            if (TextUtils.isEmpty(content) || content.equals("这一刻的想法...")) {
                Log.d("test", "===>文本框string未粘贴");
                return false;
            }
        }
        return true;
    }

    private void doShareToWechatMoments(final String id, final String remark, final int type, final String original, final int index) {
        Utils.keepLog(original, "SEND_FRIEND_CIRCLE_CMD", index);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //1.查找备注文本框并粘贴remark
                new Thread() {
                    @Override
                    public void run() {
                        int pasteCount = 0;
                        while (pasteCount < 5) {
                            Log.d("test", "pasteCount = " + pasteCount);
                            boolean pasted = isPasteContent(remark);
                            if (pasted) {
                                break;
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    findTargetNode(NODE_EDITTEXT, remark);
                                }
                            });
                            pasteCount++;
                            try {
                                sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //2.查找发送按钮并点击
                                logs.clear();
                                test(getRootInActiveWindow(), true);
                                final boolean find = findTargetNode(NODE_TEXTVIEW, "发布|发表|发送", CLICK_SELF, true);
                                Log.d("test", "发布|发表|发送 find = " + find);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (find) {
                                            new MyDBHelper(getApplicationContext()).addMoment(new Moment(id, remark));
                                            //成功，直接上报
                                            Utils.keepLog(logs.toString(), "RESEND_FRIEND_CIRCLE_CMD_SUCCESS", index);
                                            release(true, type == 2, true);
                                        } else {
                                            //失败
                                            Utils.keepLog(logs.toString(), "RESEND_FRIEND_CIRCLE_CMD_FAILURE", index);
                                            retryAction(index, type == 2);
                                        }
                                    }
                                }, 3000);
                            }

                        });
                    }
                }.start();
            }
        }, 10000);
    }

    private ArrayList<String> logs = new ArrayList<>();

    private void doShareToWechatFriend() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final String sender = actions.get(currentActionID).sender;
                //找文本框
                findTargetNode(NODE_EDITTEXT, sender);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String temp = sender;
                        boolean equal = true;
                        if (sender.length() > 13) {
                            temp = sender.substring(0, 13);
                            equal = false;
                        }
                        boolean find = findTargetNode(NODE_TEXTVIEW, temp, CLICK_PARENT, equal);
                        //Log.d("test", "share find = " + find);
                        //test(getRootInActiveWindow() , false);
                        if (!find) {
                            handleShareFriendFailure();
                            return;
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                final boolean find = findTargetNode(NODE_BUTTON, "分享", CLICK_SELF, true);
                                if (!find) {
                                    handleShareFriendFailure();
                                    return;
                                }
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        findTargetNode(NODE_BUTTON, "留在微信", CLICK_SELF, true);
                                        mHandler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                release(true);
                                            }
                                        }, 3000);
                                    }
                                }, 3000);
                            }
                        }, 3000);
                    }
                }, 2000);
            }
        }, 10000);
    }

    private void handleShareFriendFailure() {
        int index = 0;
        try {
            //获取群聊的indexs
            JSONObject o = new JSONObject(new String(Base64.decode(actions.get(currentActionID).content, NO_WRAP)));
            index = o.optInt("indexs");
        } catch (Exception e) {
            //获取单聊的indexs
            index = actions.get(currentActionID).indexs;
        }
        if (index == 0) {
            Log.d("test", "error!!!");
            return;
        }
        retryAction(index, false);
    }

    private void retryAction(final int index, boolean jump) {
        Log.d("test", "retryAction, index = " + index);
        if (tryCounts.containsKey(index)) {
            int temp = tryCounts.get(index) + 1;
            tryCounts.put(index, temp);
        } else {
            tryCounts.put(index, 1);
        }
        int tryCount = tryCounts.get(index);
        if (tryCount < 9) {
            release(false, jump, false);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //N分钟后重试
                    new MyDBHelper(getApplicationContext()).updateServerMsgStatusByIndex(index, ACTION_STATUS_0);
                    Utils.handleMsgInDB(getApplicationContext());
                }
            }, tryCount * 60 * 1000);//1+2+3+4+5+6+7+8=36
        } else {
            release(false, jump, true);
        }
    }

    //分享给多人(群)
    private void doShareToWechatFriend2() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean find = findTargetNode(NODE_TEXTVIEW, "多选", CLICK_SELF, true);
                if (!find) {
                    release(false);
                    return;
                }
                checkFriendInListView2();
            }
        }, 10000);
    }

    private String getCollectorForwardingContent(String clientGroupId) {
        JSONObject o = new JSONObject();
        try {
            String name = getSharedPreferences("kiway", 0).getString("name", "");
            String installationId = getSharedPreferences("kiway", 0).getString("installationId", "");
            String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
            String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
            String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
            String topic = robotId + "#" + wxNo;
            Action currentAction = actions.get(currentActionID);
            String content = currentAction.content;
            String msg = "";

            JSONObject msgObj = new JSONObject()
                    .put("id", System.currentTimeMillis() + "")
                    .put("sender", currentAction.sender)
                    .put("content", "content")
                    .put("me", name)
                    .put("areaCode", areaCode);
            if (!TextUtils.isEmpty(clientGroupId)) {
                msgObj.put("clientGroupId", clientGroupId);
            }
            msg = msgObj.toString();

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
            if (clientGroupId.endsWith("chatroom")) {
                o.put("froms", "groups");
            }
            Log.d("test", "o = " + o.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    public boolean test(AccessibilityNodeInfo rootNode, boolean keepLog) {
        if (rootNode == null) {
            return false;
        }
        int count = rootNode.getChildCount();
        Log.d("test", "node child count = " + count);
        if (keepLog) {
            logs.add("node child count = " + count);
        }
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo nodeInfo = rootNode.getChild(i);
            if (nodeInfo == null) {
                continue;
            }
            Log.d("test", "nodeInfo.getClassName() = " + nodeInfo.getClassName());
            if (keepLog) {
                logs.add("nodeInfo.getClassName() = " + nodeInfo.getClassName());
            }
            Log.d("test", "nodeInfo.getText() = |" + nodeInfo.getText() + "|");
            if (keepLog) {
                logs.add("nodeInfo.getText() = |" + nodeInfo.getText() + "|");
            }
            if (test(nodeInfo, keepLog)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("maptrix", "service destroy");
        mHandler.removeCallbacksAndMessages(null);
    }

    public void checkWechatLogin(final MyListener myListener) {
        new Thread() {
            @Override
            public void run() {
                goBacktoWechatHomepage(false);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boolean isHomePage = checkIsWxHomePage();
                        if (!isHomePage) {
                            myListener.onResult(false);
                            backToRobot();
                            return;
                        }
                        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //test(getRootInActiveWindow());
                                boolean find = findTargetNode(NODE_TEXTVIEW, "微信号：", CLICK_NONE, false);
                                if (!find) {
                                    myListener.onResult(false);
                                    backToRobot();
                                    return;
                                }
                                String wxNo = mFindTargetNode.getText().toString().substring(4);
                                Log.d("test", "wxNo = " + wxNo);
                                if (TextUtils.isEmpty(wxNo)) {
                                    myListener.onResult(false);
                                    backToRobot();
                                    return;
                                }
                                Friend f = getOneFriendFromWechatDB_WxNo(wxNo);
                                if (f == null) {
                                    myListener.onResult(false);
                                    backToRobot();
                                    return;
                                }
                                Log.d("test", "f = " + f.toString());
                                getSharedPreferences("kiway", 0).edit().putString("wxNo", wxNo).commit();
                                getSharedPreferences("kiway", 0).edit().putString("name", f.nickname).commit();
                                myListener.onResult(true);
                                backToRobot();
                            }
                        }, 3000);
                    }

                    private void backToRobot() {
                        Intent intent = new Intent();
                        ComponentName cn = new ComponentName("cn.kiway.robot", "cn.kiway.robot.activity.Guide3Activity");
                        intent.setComponent(cn);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }, 3000);
            }
        }.start();
    }

    public boolean doSendMessageToServer(String topic, PushMessageVo vo) {
        Channel channel = null;
        try {
            channel = rabbitMQUtils.createChannel(topic, topic);
            if (channels == null) {
                channels = new ArrayList<>();
            }
            channels.add(channel);
            rabbitMQUtils.sendMsg(vo, channel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (channel != null) {
                    channel.abort();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean doSendMessageToServer(String topic, final String replyMsg, boolean useHTTP, final int index) {
        if (useHTTP) {
            if (MainActivity.instance != null) {
                //上报并修改状态
                MainActivity.instance.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.resultReact(MainActivity.instance, replyMsg, new MyListener() {
                            @Override
                            public void onResult(boolean success) {
                                if (success) {
                                    new MyDBHelper(getApplicationContext()).updateServerMsgStatusByIndex(index, ACTION_STATUS_3);
                                }
                            }
                        });
                    }
                });
            }
        } else {
            Channel channel = null;
            try {
                channel = rabbitMQUtils.createChannel(topic, topic);
                if (channels == null) {
                    channels = new ArrayList<>();
                }
                channels.add(channel);
                rabbitMQUtils.sendMsgs(replyMsg, channel);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (channel != null) {
                        channel.abort();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void restartWechat() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10000);

                    Log.d("test", "restartWechat start...");
                    SuUtil.kill("com.tencent.mm");
                    sleep(5000);
                    Intent i = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(i);
                    sleep(30000);

                    checkIsWxHomePage();

                    if (tongxunluTextView != null) {
                        tongxunluTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    sleep(5000);
                    if (faxianView != null) {
                        faxianView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    sleep(5000);
                    if (woTextView != null) {
                        woTextView.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    sleep(5000);
                    Log.d("test", "restartWechat finish...");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
                    startActivity(intent);
                }
            }
        }.start();
    }

    private void sendLink20190322(String content, final String target) {
        try {
            JSONObject contentO = new JSONObject(content);
            String title = contentO.optString("title");
            String describe = contentO.optString("description");
            if (TextUtils.isEmpty(describe)) {
                describe = contentO.optString("content");
            }
            String imageUrl = contentO.optString("imgUrl");
            String url = contentO.optString("url");

            //1.下载图片
            String localPath = null;
            if (!TextUtils.isEmpty(imageUrl)) {
                Bitmap bmp = ImageLoader.getInstance().loadImageSync(imageUrl);
                if (bmp != null) {
                    //2.保存图片
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
            Platform wx = ShareSDK.getPlatform(Wechat.NAME);
            wx.share(sp);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final String sender = target;
                    //找文本框
                    findTargetNode(NODE_EDITTEXT, sender);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String temp = sender;
                            boolean equal = true;
                            if (sender.length() > 13) {
                                temp = sender.substring(0, 13);
                                equal = false;
                            }
                            boolean find = findTargetNode(NODE_TEXTVIEW, temp, CLICK_PARENT, equal);
                            if (!find) {
                                return;
                            }
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    final boolean find = findTargetNode(NODE_BUTTON, "分享", CLICK_SELF, true);

                                }
                            }, 3000);
                        }
                    }, 2000);
                }
            }, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
