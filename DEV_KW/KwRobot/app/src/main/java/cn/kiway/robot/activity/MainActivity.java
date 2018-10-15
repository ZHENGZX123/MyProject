package cn.kiway.robot.activity;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Filter;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.entity.Moment;
import cn.kiway.robot.entity.Second;
import cn.kiway.robot.moment.SnsInfo;
import cn.kiway.robot.moment.Task;
import cn.kiway.robot.receiver.AdminReceiver;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.MyListener;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.kiway.robot.KWApplication.DCIM;
import static cn.kiway.robot.KWApplication.DOWNLOAD;
import static cn.kiway.robot.KWApplication.ROOT;
import static cn.kiway.robot.util.Constant.ADD_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.CHECK_MOMENT_CMD;
import static cn.kiway.robot.util.Constant.CLEAR_CHAT_HISTORY_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_TRANSFER;
import static cn.kiway.robot.util.Constant.DEFAULT_VALIDATION;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.FORGET_FISH_CMD;
import static cn.kiway.robot.util.Constant.MAX_FRIENDS;
import static cn.kiway.robot.util.Constant.PERSION_NEARBY_CMD;
import static cn.kiway.robot.util.Constant.ROLE_KEFU;
import static cn.kiway.robot.util.Constant.ROLE_WODI;
import static cn.kiway.robot.util.Constant.UPDATE_FRIEND_NICKNAME_CMD;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Utils.doGetFriends;
import static cn.kiway.robot.util.Utils.doGetGroups;
import static cn.kiway.robot.util.Utils.doGetMessages;
import static cn.kiway.robot.util.Utils.doGetPeopleInGroup;
import static cn.kiway.robot.util.Utils.getCurrentVersion;
import static cn.kiway.robot.util.Utils.getWxDBFile;
import static cn.kiway.robot.util.Utils.initDbPassword;

public class MainActivity extends BaseActivity {


    public static final int MSG_NETWORK_OK = 101;
    public static final int MSG_NETWORK_ERR = 102;
    private static final int MSG_UPGRADE = 103;
    private static final int MSG_GET_BASEDATA = 104;
    private static final int MSG_GET_CELLPHONES = 106;
    private static final int MSG_ADD_NEARBY = 109;    //主动加附近的人
    private static final int MSG_MISSING_FISH = 110;    //漏网之鱼
    private static final int MSG_GET_ALL_FRIENDS = 111;//上报所有好友
    private static final int MSG_GET_ALL_MOMENTS = 112;//上报所有朋友圈
    private static final int MSG_GET_ALL_GROUPS = 113;//上报所有群组
    private static final int MSG_GET_ALL_MESSAGES = 114;//上报所有聊天消息
    private static final int MSG_CHECK_APPKEY = 115;//检测key是否有效
    private static final int MSG_GET_ALL_WODIS = 116;//获取所有卧底
    private static final int MSG_CLEAR_CHAT_HISTORY = 117;//删除聊天历史记录
    private static final int MSG_CLEAR_CACHE_FILE = 118;    //漏网之鱼

    public static MainActivity instance;
    private Button start;
    private TextView nameTV;
    private CheckBox getPic;
    private TextView versionTV;
    private TextView roleTV;
    private CheckBox lockscreen;
    private Button admin;

    private boolean newLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        newLogin = getIntent().getBooleanExtra("newLogin", false);
        getSharedPreferences("kiway", 0).edit().putBoolean("newLogin", newLogin).commit();

        initView();
        initListener();
        checkRoot(null);
        Utils.blackfile(getApplication());
        Utils.installationPush(getApplication());
        Utils.updateRobotStatus(this, AutoReplyService.instance == null ? 2 : 1);
        Utils.addFilter(this, new Filter("转发使者", "", Filter.TYPE_TRANSFER));
        setBrightness();

        mHandler.sendEmptyMessageDelayed(MSG_UPGRADE, 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_BASEDATA, 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 60 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 80 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 10 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 120 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MOMENTS, 140 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_GROUPS, 100 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_CHECK_APPKEY, 10 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_CLEAR_CHAT_HISTORY, 8 * 60 * 60 * 1000);
        clearCachedFiles(true);
        mHandler.sendEmptyMessageDelayed(MSG_CLEAR_CACHE_FILE, 10 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MESSAGES, intervalGrades[currentGrade] * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_WODIS, 10 * 1000);
    }

    private void setBrightness() {
        //先关闭系统的亮度自动调节
        try {
            if (android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE)
                    == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                android.provider.Settings.System.putInt(getContentResolver(),
                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //不让屏幕全暗
        int brightness = 1;
        //设置当前activity的屏幕亮度
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        //0到1,调整亮度暗到全亮
        lp.screenBrightness = brightness / 255.0f;
        this.getWindow().setAttributes(lp);
        android.provider.Settings.System.putInt(getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    private void initView() {
        nameTV = (TextView) findViewById(R.id.name);
        start = (Button) findViewById(R.id.start);
        admin = (Button) findViewById(R.id.admin);

        getPic = (CheckBox) findViewById(R.id.getPic);
        lockscreen = (CheckBox) findViewById(R.id.lockscreen);

        versionTV = (TextView) findViewById(R.id.version);
        versionTV.setText((clientUrl.contains("robot") ? "正式版：" : "测试版：") + getCurrentVersion(this));

        roleTV = (TextView) findViewById(R.id.role);

    }

    private void initListener() {
        getPic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("test", "onCheckedChanged isCheck = " + isChecked);
                getSharedPreferences("getPic", 0).edit().putBoolean("getPic", isChecked).commit();
            }
        });

        lockscreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("test", "onCheckedChanged isCheck = " + isChecked);
                getSharedPreferences("lockscreen", 0).edit().putBoolean("lockscreen", isChecked).commit();
            }
        });
    }

    public void checkRoot(View v) {
        new Thread() {
            @Override
            public void run() {
                boolean have = RootCmd.haveRoot();
                if (have) {
                    toast("已经拥有Root权限");
                } else {
                    toast("尚未拥有Root权限");
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
        updateAdminStatus();
        updateServiceCount();
    }

    private void updateAdminStatus() {
        DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context
                .DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(this, AdminReceiver.class);
        if (mDevicePolicyManager.isAdminActive(componentName)) {
            admin.setEnabled(false);
        } else {
            admin.setEnabled(true);
        }
    }

    public void updateServiceCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String name = getSharedPreferences("kiway", 0).getString("name", "");
                String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                nameTV.setText("微信号：" + wxNo + "\n昵称：" + name);
            }
        });
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    public void start(View view) {
        doStartService();
    }

    public void filter(View view) {
        startActivity(new Intent(this, FilterActivity.class));
    }

    public void reLogin(View view) {
        if (!newLogin) {
            getSharedPreferences("kiway", 0).edit().putBoolean("login", false).commit();
            Utils.closeMQ();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("微信是否切换帐号");
        builder.setPositiveButton("不切换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doRelogin(false);
            }
        });
        builder.setNegativeButton("切换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doRelogin(true);
            }
        });
        builder.show();
    }

    private void doRelogin(boolean change) {
        Utils.closeMQ();
        getSharedPreferences("kiway", 0).edit().putBoolean("login", false).commit();
        if (change) {
            startActivity(new Intent(this, Guide3Activity.class));
        } else {
            startActivity(new Intent(this, Guide4Activity.class));
        }
        finish();
    }

    public void getBaseData() {
        Log.d("test", "getBaseData");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/baseData/getDataByType?type=" + wxNo;
                    Log.d("test", "url = " + url);
                    client.get(MainActivity.this, url, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", " onSuccess = " + ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                List<Second> seconds = JSON.parseArray(data.toString(), Second.class);
                                Log.d("test", "seconds = " + seconds);

                                for (Second s : seconds) {
                                    if (TextUtils.isEmpty(s.description)) {
                                        continue;
                                    }
                                    if (s.description.equals("null")) {
                                        s.description = "";
                                    }
                                    switch (s.name) {
                                        case "被动加好友，欢迎语句":
                                            getSharedPreferences("welcomeTitle", 0).edit().putString("welcomeTitle", s.description).commit();
                                            break;
                                        case "微信好友满之后备用微信号":
                                            getSharedPreferences("backup", 0).edit().putString("backup", s.description).commit();
                                            break;
                                        case "客服正忙提示语":
                                            getSharedPreferences("busy", 0).edit().putString("busy", s.description).commit();
                                            break;
                                        case "客服已下线提示语":
                                            getSharedPreferences("offline", 0).edit().putString("offline", s.description).commit();
                                            break;
                                        case "主动加好友，请求语句":
                                            getSharedPreferences("validation", 0).edit().putString("validation", s.description).commit();
                                            break;
                                        case "转发使者的微信":
                                            getSharedPreferences("transfer", 0).edit().putString("transfer", s.description).commit();
                                            break;
                                        case "卧底好友的微信":
                                            getSharedPreferences("wodis", 0).edit().putString("wodis", s.description).commit();
                                            break;
                                    }
                                }
                                getWelcome();
                                getAllFriends(true, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", " onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getWelcome() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    if (TextUtils.isEmpty(areaCode)) {
                        toast("areaCode为空");
                        return;
                    }
                    String welcomeTitle = getSharedPreferences("welcomeTitle", 0).getString("welcomeTitle", DEFAULT_WELCOME_TITLE);
                    String url = clientUrl + "/replyContent/keyWords?title=" + URLEncoder.encode(welcomeTitle, "utf-8") +
                            "&origin=mp&areaCode=" + areaCode;
                    HttpGet httpRequest = new HttpGet(url);
                    DefaultHttpClient client = new DefaultHttpClient();
                    String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    httpRequest.addHeader("x-auth-token", xtoken);
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    String welcome = new JSONObject(ret).getJSONObject("data").getString("message");
                    Log.d("test", "welcome = " + welcome);
                    if (!TextUtils.isEmpty(welcome)) {
                        getSharedPreferences("welcome", 0).edit().putString("welcome", welcome).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private void getAllMomentComments(final boolean scan) {
        new Thread() {
            @Override
            public void run() {
                try {
                    //1.先去朋友圈浏览一下
                    if (scan) {
                        checkMomemt();
                        sleep(100 * 1000);
                    }
                    //2.破解数据库
                    getCommentFromDB();
                    sleep(10 * 1000);
                    //3.上报commemt
                    uploadComment();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void uploadComment() throws IOException, JSONException {
        ArrayList<SnsInfo.Comment> comments = new MyDBHelper(getApplicationContext()).getCommentsByMomentID(null);
        for (SnsInfo.Comment c : comments) {
            //只上传toUser是null的
            if (c.uploaded == 0 && c.toUser == null) {
                Log.d("test", "do upload c = " + c);
                String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                String url = Constant.clientUrl + "/robot/" + robotId + "/friendCircleComment/report";
                Log.d("test", "url = " + url);
                HttpPost httpRequest = new HttpPost(url);
                httpRequest.addHeader("x-auth-token", xtoken);
                DefaultHttpClient client = new DefaultHttpClient();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("createDate", c.time * 1000 + ""));
                params.add(new BasicNameValuePair("robotId", robotId));
                params.add(new BasicNameValuePair("circleId", c.momentID));
                params.add(new BasicNameValuePair("author", c.authorName));
                params.add(new BasicNameValuePair("content", c.content));
                params.add(new BasicNameValuePair("toUser", "null"));//c.toUser
                Log.d("test", "params = " + params.toString());
                UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(params, "UTF-8");
                httpRequest.setEntity(urlEntity);
                HttpResponse response = client.execute(httpRequest);
                String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                Log.d("test", "ret = " + ret);
                int statusCode = new JSONObject(ret).optInt("statusCode");
                if (statusCode == 200) {
                    new MyDBHelper(getApplicationContext()).setCommentUploaded(c.momentID, c.authorName, c.content, c.time, 1);
                }
            }
        }
    }

    private void getCommentFromDB() throws Throwable {
        File dbFile = getWxDBFile("SnsMicroMsg.db", "getAllComments" + new Random().nextInt(9999) + ".db");
        Task task = new Task(getApplicationContext());
        boolean init = task.initSnsReader();
        if (init && dbFile != null) {
            task.snsReader.run(getApplicationContext(), dbFile.getAbsolutePath());
            ArrayList<SnsInfo> infos = task.snsReader.getSnsList();
            for (SnsInfo info : infos) {
                info.print();
                int count = info.comments.size();
                Log.d("test", "该SnsInfo有" + count + "条评论");
                if (count > 0) {
                    //1.判断数据库有没有对应的moment
                    Moment m = new MyDBHelper(getApplicationContext()).getMomentByDescription(info.content);
                    if (m == null) {
                        Log.d("test", "该SnsInfo没有对应的moment，是老数据，不再处理");
                        continue;
                    }
                    //2.修改authorName
                    Utils.getAuthorRemarkByAuthorId(getApplicationContext(), info.comments);

                    //3.判断是否存在
                    for (SnsInfo.Comment c : info.comments) {
                        c.momentID = m.momentID;
                        boolean existed = new MyDBHelper(getApplicationContext()).checkCommentExisted(m.momentID, c.authorName, c.content, c.time);
                        Log.d("test", "checkCommentExisted existed = " + existed);
                        if (!existed) {
                            new MyDBHelper(getApplicationContext()).addComment(c);
                        }
                    }
                }
            }
        }
    }

    private void checkMomemt() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("cmd", CHECK_MOMENT_CMD);
        String temp = o.toString();
        Log.d("test", "temp = " + temp);
        AutoReplyService.instance.sendReplyImmediately(temp, false);
    }

    private void getAllMessages() {
        if (AutoReplyService.instance == null) {
            Log.d("test", "服务未开");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    String password = initDbPassword(getApplicationContext());
                    File dbFile = getWxDBFile("EnMicroMsg.db", "getAllMessages" + new Random().nextInt(9999) + ".db");
                    ArrayList<Message> wxMessages = doGetMessages(getApplicationContext(), dbFile, password, null);
                    ArrayList<Message> sendMessages = new MyDBHelper(getApplication()).getMessagesIn1Hour();
                    //过滤掉已经发送的消息
                    for (Message m1 : wxMessages) {
                        boolean sended = false;
                        for (Message m2 : sendMessages) {
                            if (!m1.talker.endsWith("@chatroom") && m1.remark.equals(m2.remark) && m1.content.equals(m2.content)) {
                                sended = true;
                            } else if (m1.talker.endsWith("@chatroom") && m1.content.endsWith(m2.content)) {//displayName sender暂时不比较
                                sended = true;
                            }
                        }
                        Log.d("test", "check msg = " + m1.toString());
                        if (sended) {
                            Log.d("test", "不需要补发该消息");
                        } else {
                            Log.d("test", "需要补发该消息");
                            if (m1.talker.endsWith("@chatroom")) {
                                String wxId = m1.content.substring(0, m1.content.indexOf(":"));
                                String sender = Utils.getDisplayNameFromGroup(getApplicationContext(), m1.talker, wxId);
                                if (!TextUtils.isEmpty(sender)) {
                                    String message = m1.content.substring(m1.content.indexOf(":") + 2);
                                    AutoReplyService.instance.sendMsgToServer3(m1.talker, sender, message);
                                } else {
                                    Log.d("test", "sender is null");
                                }
                            } else {
                                Action action = new Action();
                                action.sender = m1.remark;
                                action.content = m1.content;
                                AutoReplyService.instance.sendMsgToServer(action);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void shareProgram(View view) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("title");
        sp.setWxUserName("gh_afb25ac019c9");
        sp.setWxPath("/page/API");
        sp.setShareType(Platform.SHARE_WXMINIPROGRAM);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);
    }

    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            start.setText("服务已经开启");
            start.setEnabled(false);
            //Utils.updateRobotStatus(MainActivity.instance, 1);
        } else {
            start.setText("点击开启服务");
            start.setEnabled(true);
            Utils.updateRobotStatus(MainActivity.instance, 2);
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_NETWORK_OK) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.GONE);
                Log.d("test", "有网络");
            } else if (msg.what == MSG_NETWORK_ERR) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.VISIBLE);
                Log.d("test", "无网络");
            } else if (msg.what == MSG_UPGRADE) {
                mHandler.removeMessages(MSG_UPGRADE);
                checkNewVersion(null);
                mHandler.sendEmptyMessageDelayed(MSG_UPGRADE, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_BASEDATA) {
                mHandler.removeMessages(MSG_GET_BASEDATA);
                getBaseData();
                mHandler.sendEmptyMessageDelayed(MSG_GET_BASEDATA, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_FRIENDS) {
                mHandler.removeMessages(MSG_GET_ALL_FRIENDS);
                getAllFriends(true, true);
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_MOMENTS) {
                mHandler.removeMessages(MSG_GET_ALL_MOMENTS);
                getAllMomentComments(true);
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MOMENTS, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_GROUPS) {
                mHandler.removeMessages(MSG_GET_ALL_GROUPS);
                getAllGroups(true);
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_GROUPS, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_CELLPHONES) {
                mHandler.removeMessages(MSG_GET_CELLPHONES);
                getCellPhones();
                mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_ADD_NEARBY) {
                mHandler.removeMessages(MSG_ADD_NEARBY);
                addNearByPeople();
                mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_MISSING_FISH) {
                mHandler.removeMessages(MSG_MISSING_FISH);
                doMissingFish();
                mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 30 * 60 * 1000);
            } else if (msg.what == MSG_CHECK_APPKEY) {
                mHandler.removeMessages(MSG_CHECK_APPKEY);
                checkAPPKey();
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_APPKEY, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_MESSAGES) {
                mHandler.removeMessages(MSG_GET_ALL_MESSAGES);
                getAllMessages();
                if (currentGrade < (intervalGrades.length - 1)) {
                    currentGrade++;
                }
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MESSAGES, intervalGrades[currentGrade] * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_WODIS) {
                mHandler.removeMessages(MSG_GET_ALL_WODIS);
                getAllWodis();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_WODIS, 60 * 60 * 1000);
            } else if (msg.what == MSG_CLEAR_CHAT_HISTORY) {
                mHandler.removeMessages(MSG_CLEAR_CHAT_HISTORY);
                clearChatHistory();
                mHandler.sendEmptyMessageDelayed(MSG_CLEAR_CHAT_HISTORY, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_CLEAR_CACHE_FILE) {
                mHandler.removeMessages(MSG_CLEAR_CACHE_FILE);
                clearCachedFiles(false);
                mHandler.sendEmptyMessageDelayed(MSG_CLEAR_CACHE_FILE, 10 * 60 * 1000);
            }
        }
    };

    public long lastUpdateTime;
    public int currentGrade = 5;
    public static final int[] intervalGrades = new int[]{2, 3, 5, 10, 20, 30, 60};

    public void updateCheckMsgInterval() {
        long current = System.currentTimeMillis();
        if ((current - lastUpdateTime) < 2 * 60 * 1000) {
            return;
        }
        lastUpdateTime = current;
        currentGrade = 0;
        mHandler.removeMessages(MSG_GET_ALL_MESSAGES);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MESSAGES, intervalGrades[currentGrade] * 60 * 1000);
    }

    private void clearChatHistory() {
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", CLEAR_CHAT_HISTORY_CMD);
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //剧本改版之后，这个接口可以暂时不要了。
    public void getAllWodis() {
        //1.获取所有卧底的微信号
        //2.相互加入过滤名单
        //3.相互加为好友（这个没做？！）
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/robot?isUndercover=1&size=1024";
                    Log.d("test", "url = " + url);
                    client.get(MainActivity.this, url, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", " onSuccess = " + ret);
                            try {
                                JSONArray list = new JSONObject(ret).getJSONObject("data").getJSONArray("list");
                                int count = list.length();
                                boolean isWodi = false;
                                for (int i = 0; i < count; i++) {
                                    JSONObject o = list.getJSONObject(i);
                                    String name = o.getString("name");
                                    String imei = o.getString("imei");
                                    if (imei.equals(wxNo)) {
                                        isWodi = true;
                                        continue;
                                    }
                                    //加入过滤名单
                                    Utils.addFilter(MainActivity.this, new Filter(name, imei, Filter.TYPE_WODI));
                                }
                                Log.d("test", "isWodi = " + isWodi);
                                if (isWodi) {
                                    getSharedPreferences("role", 0).edit().putInt("role", ROLE_WODI).commit();
                                } else {
                                    getSharedPreferences("role", 0).edit().putInt("role", ROLE_KEFU).commit();
                                }
                                refreshRoleTV();
                                getAllFriends(true, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", " onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshRoleTV() {
        int role = getSharedPreferences("role", 0).getInt("role", ROLE_KEFU);
        if (role == ROLE_KEFU) {
            roleTV.setText("当前角色：客服");
        } else if (role == ROLE_WODI) {
            roleTV.setText("当前角色：卧底");
        }
    }

    private void checkAPPKey() {
        Log.d("test", "checkAPPKey");
        if (true) {
            Log.d("test", "APPKEY有效");
        } else {
            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
            ab.setTitle("提示").setMessage("您的APPKEY已失效，请续费或咨询工作人员")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //跳到appkey页面
                            startActivity(new Intent(MainActivity.this, APPkeyActivity.class));
                            finish();
                        }
                    }).setCancelable(false);
            ab.create().show();
        }
    }

    private void doMissingFish() {
        int friendCount = getSharedPreferences("friendCount", 0).getInt("friendCount", 0);
        if (friendCount > MAX_FRIENDS) {
            toast("好友已经到上限了");
            return;
        }
        if (AutoReplyService.instance == null) {
            return;
        }
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", FORGET_FISH_CMD);
            o.put("id", "84f119408d6441358d24b668323f0a23");
            o.put("token", "1526895528997");
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNearByPeople() {
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", PERSION_NEARBY_CMD);
            o.put("content", DEFAULT_VALIDATION);
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllFriends(final boolean doCheck, final boolean upload) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String password = initDbPassword(getApplicationContext());
                    File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
                    final ArrayList<Friend> friends = doGetFriends(getApplicationContext(), dbFile, password);
                    int friendCount = friends.size();
                    Log.d("test", "friendCount = " + friendCount);
                    getSharedPreferences("friendCount", 0).edit().putInt("friendCount", friendCount).commit();
                    //0.校验
                    boolean validate = true;
                    for (Friend f : friends) {
                        if (TextUtils.isEmpty(f.nickname.trim()) && TextUtils.isEmpty(f.remark.trim())) {
                            Log.d("test", "校验不通过的f = " + f.toString());
                            validate = false;
                            break;
                        }
                    }
                    if (!validate) {
                        toast("校验不通过，有空的昵称或备注名");
                        return;
                    }

                    //1.上传
                    if (upload) {
                        Utils.uploadFriend(MainActivity.instance, friends);
                    }
                    //2.检查有没有转发使者
                    if (doCheck) {
                        checkTransfer(friends);
                        checkWodis(friends);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkWodis(ArrayList<Friend> friends) {
        Log.d("test", "checkWodis");
        String wodis = getSharedPreferences("wodis", 0).getString("wodis", "");
        if (TextUtils.isEmpty(wodis)) {
            return;
        }
        String wodi[] = wodis.split(",");
        if (wodi.length == 0) {
            return;
        }
        for (String str : wodi) {
            if (TextUtils.isEmpty(str)) {
                continue;
            }
            boolean has = false;
            for (Friend f : friends) {
                String wxNo = TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo;
                if (wxNo.equals(str)) {
                    has = true;
                }
            }
            if (!has) {
                ArrayList<String> requests = new ArrayList<>();
                requests.add(str);
                doRequestFriends(requests);
            }
        }
    }

    private void checkTransfer(ArrayList<Friend> friends) {
        Log.d("test", "checkTransfer");
        String transfer = getSharedPreferences("transfer", 0).getString("transfer", DEFAULT_TRANSFER);

        boolean hasTransfer = false;
        for (Friend f : friends) {
            String wxNo = TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo;
            if (wxNo.equals(transfer)) {
                hasTransfer = true;
            }
        }
        Log.d("test", "hasTransfer = " + hasTransfer);
        if (hasTransfer) {
            return;
        }
        ArrayList<String> requests = new ArrayList<>();
        requests.add(transfer);
        doRequestFriends(requests);
    }

    public void getAllGroups(final boolean updatePeoples) {
        new Thread() {
            @Override
            public void run() {
                try {
                    final String password = initDbPassword(getApplicationContext());
                    final File dbFile = getWxDBFile("EnMicroMsg.db", "getAllGroups" + new Random().nextInt(9999) + ".db");
                    final ArrayList<Group> groups = doGetGroups(getApplicationContext(), dbFile, password);
                    if (groups != null && groups.size() > 0) {
                        Utils.uploadGroup(MainActivity.this, groups, new MyListener() {
                            @Override
                            public void onResult(boolean success) {
                                if (updatePeoples) {
                                    for (Group group : groups) {
                                        group.peoples = doGetPeopleInGroup(MainActivity.this, dbFile, password, group.clientGroupId);
                                    }
                                    Utils.uploadGroupPeoples(MainActivity.this, groups);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void getCellPhones() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!isServiceEnabled()) {
                        toast("服务未开启，不能主动加好友");
                        return;
                    }

                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    if (TextUtils.isEmpty(areaCode)) {
                        toast("areaCode为空");
                        return;
                    }
                    Log.d("test", "areaCode = " + areaCode);
                    String url = clientUrl + "/users?status=0&areaCode=" + areaCode + "&current=1&size=10";
                    Log.d("test", "url = " + url);
                    HttpGet httpRequest = new HttpGet(url);
                    String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    httpRequest.addHeader("x-auth-token", xtoken);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                    Log.d("test", "getCellPhones  = " + ret);
                    JSONObject o = new JSONObject(ret);
                    JSONObject data = o.getJSONObject("data");
                    JSONArray list = data.getJSONArray("list");
                    int count = list.length();
                    if (count == 0) {
                        return;
                    }

                    ArrayList<String> requests = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        JSONObject temp = list.getJSONObject(i);
                        String phone = temp.getString("phone").trim();
                        AddFriend af = new MyDBHelper(getApplicationContext()).getAddFriendByPhone(phone);
                        if (af == null) {
                            Log.d("test", phone + "还没有申请过");
                            requests.add(phone);
                        } else {
                            Log.d("test", phone + "已经申请过了");
                        }
                    }
                    if (requests.size() == 0) {
                        return;
                    }
                    doRequestFriends(requests);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void doRequestFriends(ArrayList<String> requests) {
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", ADD_FRIEND_CMD);
            o.put("id", "84f119408d6441358d24b668323f0a23");
            JSONArray members = new JSONArray();
            for (String phone : requests) {
                members.put(phone);
            }
            String validation = getSharedPreferences("validation", 0).getString("validation", DEFAULT_VALIDATION);
            o.put("members", members);
            o.put("message", validation);
            o.put("token", "1526895528997");

            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, false);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "服务没开");
        }
    }

    public void clickInstruction(View view) {
        startActivity(new Intent(this, InstructionActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void admin(View view) {
        ComponentName componentName = new ComponentName(this, AdminReceiver.class);
        //启动设备管理 - 在AndroidManifest.xml中设定相应过滤器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        //权限列表
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        //描述
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活后才能使用锁屏功能哦");
        startActivity(intent);
    }

    public void test(View v) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoReplyService.instance.test(AutoReplyService.instance.getRootInActiveWindow());
            }
        }, 10000);
    }

    public void test2(View v) {
        getAllFriends(false, true);
    }

    public void test3(View view) {
        resetNickName();
//        resetNickName2();
    }

    private void resetNickName() {
        try {
            String password = initDbPassword(getApplicationContext());
            File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
            final ArrayList<Friend> friends = doGetFriends(getApplicationContext(), dbFile, password);
            ArrayList<Friend> needUpdateFriends = new ArrayList<>();

            int count = friends.size();
            for (int i = 0; i < count; i++) {
                Friend f = friends.get(i);
                Log.d("test", "f = " + f.toString());
                String leftChinese = Utils.leftChinese(f.remark);
                Log.d("test", "leftChinese = |" + leftChinese + "|");
                if (TextUtils.isEmpty(leftChinese)) {
                    leftChinese = Utils.leftChinese(f.nickname);
                }
                if (TextUtils.isEmpty(leftChinese)) {
                    leftChinese = Utils.getRandomChineseName();
                }
                if (!Utils.isStartWithNumber(leftChinese)) {
                    //zhengkang fix0920: Utils.getParentRemark(this, 1)
                    leftChinese = i + " " + leftChinese;
                }
                //zhengkang add 0925
                if (leftChinese.length() > 10) {
                    leftChinese = leftChinese.substring(0, 10);
                }
                f.newRemark = leftChinese;
                Log.d("test", "newRemark = " + f.newRemark);
                if (!f.newRemark.equals(f.remark) && !f.newRemark.equals(f.nickname)) {
                    //Log.d("test", "该好友要重新备注");
                    needUpdateFriends.add(f);
                }
            }
            Log.d("test", "needUpdateFriends size = " + needUpdateFriends.size());
            //1.发送改备注的命令：
            for (Friend f : needUpdateFriends) {
                JSONObject o = new JSONObject();
                o.put("cmd", UPDATE_FRIEND_NICKNAME_CMD);
                o.put("id", "84f119408d6441358d24b668323f0a23");
                o.put("token", "1526895528997");
                o.put("fromFront", true);
                o.put("nickname", f.nickname);
                o.put("wxId", f.wxId);
                o.put("wxNo", TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo);
                o.put("oldName", TextUtils.isEmpty(f.remark) ? f.nickname : f.remark);
                o.put("newName", f.newRemark);
                String temp = o.toString();
                Log.d("test", "temp = " + temp);
                AutoReplyService.instance.sendRenameTaskQueue(temp);
            }
            //2.完成之后再上报一下好友
            getAllFriends(false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //替补方法
    private void resetNickName2() {
        try {
            String password = initDbPassword(getApplicationContext());
            File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
            final ArrayList<Friend> friends = doGetFriends(getApplicationContext(), dbFile, password);
            ArrayList<Friend> needUpdateFriends = new ArrayList<>();

            int count = friends.size();
            for (int i = 0; i < count; i++) {
                Friend f = friends.get(i);
                Log.d("test", "f = " + f.toString());
                String leftChinese = Utils.leftChinese(f.remark);
                Log.d("test", "leftChinese = |" + leftChinese + "|");
                if (TextUtils.isEmpty(leftChinese)) {
                    leftChinese = Utils.leftChinese(f.nickname);
                }
                if (TextUtils.isEmpty(leftChinese)) {
                    leftChinese = Utils.getRandomChineseName();
                }
                if (!Utils.isStartWithNumber(leftChinese)) {
                    //zhengkang fix0920: Utils.getParentRemark(this, 1)
                    leftChinese = i + " " + leftChinese;
                }
                //zhengkang add 0925
                if (leftChinese.length() > 10) {
                    leftChinese = leftChinese.substring(0, 10);
                }
                f.newRemark = leftChinese;
                Log.d("test", "newRemark = " + f.newRemark);
                if (!f.newRemark.equals(f.remark) && !f.newRemark.equals(f.nickname)) {
                    //Log.d("test", "该好友要重新备注");
                    needUpdateFriends.add(f);
                }
            }
            Log.d("test", "needUpdateFriends size = " + needUpdateFriends.size());
            //1.发送改备注的命令：
            for (Friend f : needUpdateFriends) {
                if (TextUtils.isEmpty(f.wxNo)) {
                    continue;
                }
                JSONObject o = new JSONObject();
                o.put("cmd", UPDATE_FRIEND_NICKNAME_CMD);
                o.put("id", "84f119408d6441358d24b668323f0a23");
                o.put("token", "1526895528997");
                o.put("fromFront", true);
                o.put("nickname", f.nickname);
                o.put("wxId", f.wxId);
                o.put("wxNo", TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo);
                o.put("oldName", f.wxNo);
                o.put("newName", f.newRemark);
                String temp = o.toString();
                Log.d("test", "temp = " + temp);
                AutoReplyService.instance.sendRenameTaskQueue(temp);
            }
            //2.完成之后再上报一下好友
            getAllFriends(false, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void debugUse() {
        new Thread() {
            @Override
            public void run() {
                String password = initDbPassword(getApplicationContext());
                File dbFile = getWxDBFile("EnMicroMsg.db", "getAllMessages" + new Random().nextInt(9999) + ".db");
                String sql = "select message.msgId , message.type , message.createTime  , message.talker , rcontact.nickname ,  rcontact.conRemark, message.content from message left JOIN rcontact on message.talker = rcontact.username where message.isSend = 0 and message.type = 1 and talker = '7188448662@chatroom'";
                ArrayList<Message> wxMessages = doGetMessages(getApplicationContext(), dbFile, password, sql);
                for (Message m1 : wxMessages) {
                    String wxId = m1.content.substring(0, m1.content.indexOf(":"));
                    String sender = Utils.getDisplayNameFromGroup(getApplicationContext(), m1.talker, wxId);
                    Log.d("test", "sender = " + sender);
                }
            }
        }.start();
    }

    private void clearCachedFiles(boolean force) {
        if (force) {
            deleteAllCachedFiles(ROOT);
            deleteAllCachedFiles(DOWNLOAD);
            deleteAllCachedFiles(DCIM);
            ImageLoader.getInstance().clearDiskCache();
        } else {
            //删除部分db
            deleteUselessDBFiles(ROOT);
        }
    }

    private void deleteAllCachedFiles(String folder) {
        File[] files = new File(folder).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File f : files) {
            Log.d("test", "f = " + f.getAbsolutePath());
            if (!f.isDirectory() && (f.getName().contains("db") || f.getName().endsWith("jpg"))) {
                Log.d("test", "delete file = " + f.getName());
                f.delete();
            }
        }
    }

    private void deleteUselessDBFiles(String folder) {
        File[] files = new File(folder).listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        int max = 100;
        int count = files.length;
        Log.d("test", "count = " + count);
        if (count < max) {
            return;
        }
        for (File f : files) {
            Log.d("test", "f = " + f.getAbsolutePath());
        }
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o1.lastModified() - o2.lastModified());
            }
        });
        Log.d("test", "=============================");
        for (File f : files) {
            Log.d("test", "f = " + f.getAbsolutePath());
        }
        int delete = max / 2;
        for (int i = 0; i < delete; i++) {
            File f = files[i];
            Log.d("test", "f = " + f.getAbsolutePath());
            if (!f.isDirectory() && f.getName().contains("db")) {
                Log.d("test", "delete file = " + f.getName());
                f.delete();
            }
        }
    }

    public void upload(View view) {
        getAllGroups(true);
        getAllFriends(false, true);
    }
}