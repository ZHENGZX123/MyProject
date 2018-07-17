package cn.kiway.robot.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

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
import java.util.List;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.entity.Moment;
import cn.kiway.robot.entity.Second;
import cn.kiway.robot.moment.SnsInfo;
import cn.kiway.robot.moment.Task;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.kiway.robot.util.Constant.ADD_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.CHECK_MOMENT_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_TRANSFER;
import static cn.kiway.robot.util.Constant.DEFAULT_VALIDATION;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.FORGET_FISH_CMD;
import static cn.kiway.robot.util.Constant.MAX_FRIENDS;
import static cn.kiway.robot.util.Constant.PERSION_NEARBY_CMD;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Utils.doGetFriends;
import static cn.kiway.robot.util.Utils.doGetGroups;
import static cn.kiway.robot.util.Utils.doGetMessages;
import static cn.kiway.robot.util.Utils.getCurrentVersion;
import static cn.kiway.robot.util.Utils.getWxDBFile;
import static cn.kiway.robot.util.Utils.initDbPassword;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private Button start;

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
    private static final int MSG_GET_ALL_MESSAGES = 114;//上报所有群组
    private static final int MSG_CHECK_APPKEY = 115;//检测key是否有效

    private TextView nameTV;
    private CheckBox getPic;
    private TextView versionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        initListener();
        checkRoot(null);
        Utils.installationPush(getApplication());

        mHandler.sendEmptyMessageDelayed(MSG_UPGRADE, 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_BASEDATA, 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 60 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 80 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 10 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 120 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MOMENTS, 140 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_GROUPS, 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_CHECK_APPKEY, 10 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MESSAGES, 60 * 60 * 1000);
    }

    private void initView() {
        nameTV = (TextView) findViewById(R.id.name);
        start = (Button) findViewById(R.id.start);
        getPic = (CheckBox) findViewById(R.id.getPic);

        versionTV = (TextView) findViewById(R.id.version);
        versionTV.setText((clientUrl.contains("robot") ? "正式版：" : "测试版：") + getCurrentVersion(this));
    }

    private void initListener() {
        getPic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("test", "onCheckedChanged isCheck = " + isChecked);
                getSharedPreferences("getPic", 0).edit().putBoolean("getPic", isChecked).commit();
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
        updateServiceCount();
    }

    public void updateServiceCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String username = getSharedPreferences("kiway", 0).getString("username", "");
                String name = getSharedPreferences("kiway", 0).getString("name", "");
                String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
                int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0);
                int replyCount = getSharedPreferences("kiway", 0).getInt("replyCount", 0);
                String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                nameTV.setText(
                        "帐号：" + username + " 昵称：" + name + " 微信号：" + wxNo + "\n"
                                + "接收次数：" + recvCount + " 回复次数：" + replyCount + "\n"
                                + "areaCode：" + areaCode);
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
        KWApplication.closeMQ();

        getSharedPreferences("kiway", 0).edit().putBoolean("login", false).commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void getBaseData() {
        String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = "http://robot.kiway.cn/baseData/getDataByType?type=" + wxNo;
            Log.d("test", "url = " + url);
            client.get(this, url, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    try {
                        JSONArray data = new JSONObject(ret).getJSONArray("data");
                        List<Second> seconds = JSON.parseArray(data.toString(), Second.class);
                        Log.d("test", "seconds = " + seconds);

                        for (Second s : seconds) {
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
                            }
                        }
                        getWelcome();
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
            Log.d("test", "e = " + e.toString());
        }
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

    public void test(View v) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoReplyService.instance.test(AutoReplyService.instance.getRootInActiveWindow());
            }
        }, 10000);
    }


    public void test2(View v) {
        //getAllFriends();
        //ArrayList<String> peoples = doGetPeopleInGroup(getApplicationContext(), dbFile, password, "9189004002@chatroom");
        //Log.d("test", "moments = " + new MyDBHelper(this).getAllMoments());
        //new MyDBHelper(this).addMoment(new Moment("1111", "我是一条测试数据"));
        //new MyDBHelper(this).addComment(new SnsInfo.Comment("1111", "评论人名字", "评论内容", "toUser", 1530867548, 0));

        getAllMomentComments();

        //Log.d("test", "getAllMoments " + new MyDBHelper(this).getAllMoments());
        //Log.d("test", "getCommentsByMomentID " + new MyDBHelper(this).getCommentsByMomentID("81f2aa68708147f3b9e794fdba0ae96d"));
        //SnsInfo.Comment c = new SnsInfo.Comment("81f2aa68708147f3b9e794fdba0ae96d", "评论作者", "评论内容", "toUser", 1530867548, 0);
        //new MyDBHelper(this).addComment(c);
    }

    private void getAllMomentComments() {
        new Thread() {
            @Override
            public void run() {
                try {
                    //1.先去朋友圈浏览一下
                    checkMomemt();
                    sleep(100 * 1000);
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
        ArrayList<SnsInfo.Comment> comments = new MyDBHelper(getApplicationContext()).getCommentsByMomentID("81f2aa68708147f3b9e794fdba0ae96d");
        for (SnsInfo.Comment c : comments) {
            if (c.uploaded == 0) {
                String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                String url = Constant.clientUrl + "/robot/" + robotId + "/friendCircleComment/report";
                Log.d("test", "url = " + url);
                HttpPost httpRequest = new HttpPost(url);
                DefaultHttpClient client = new DefaultHttpClient();
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("createDate", "" + c.time));
                params.add(new BasicNameValuePair("robotId", robotId));
                params.add(new BasicNameValuePair("circleId", c.momentID));
                params.add(new BasicNameValuePair("author", c.authorName));
                params.add(new BasicNameValuePair("content", c.content));
                params.add(new BasicNameValuePair("toUser", c.toUser));
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
        File dbFile = getWxDBFile("SnsMicroMsg.db", null);
        Task task = new Task(getApplicationContext());
        boolean init = task.initSnsReader();
        if (init) {
            task.snsReader.run(getApplicationContext(), dbFile.getAbsolutePath());
            ArrayList<SnsInfo> infos = task.snsReader.getSnsList();
            for (SnsInfo info : infos) {
                info.print();
                int count = info.comments.size();
                Log.d("test", "该SnsInfo有" + count + "条评论");
                if (count > 0) {
                    //判断数据库有没有对应的moment
                    Moment m = new MyDBHelper(getApplicationContext()).getMomentByDescription(info.content);
                    if (m == null) {
                        Log.d("test", "该SnsInfo没有对应的moment，是老数据，不再处理");
                        continue;
                    }
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
        o.put("id", "84f119408d6441358d24b668323f0a23");
        o.put("token", "1526895528997");
        String temp = o.toString();
        Log.d("test", "temp = " + temp);
        AutoReplyService.instance.sendReplyImmediately(temp, true);
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
                    File dbFile = getWxDBFile("EnMicroMsg.db", "getAllMessages.db");
                    ArrayList<Message> wxMessages = doGetMessages(getApplicationContext(), dbFile, password);
                    ArrayList<Message> sendMessages = new MyDBHelper(getApplication()).getMessagesIn1Hour();
                    //过滤掉已经发送的消息
                    for (Message m1 : wxMessages) {
                        boolean send = false;
                        for (Message m2 : sendMessages) {
                            if (m1.remark.equals(m2.remark) && m1.content.equals(m2.content)) {
                                send = true;
                            }
                        }
                        Log.d("test", m1.remark + ":" + m1.content + "===>");
                        if (send) {
                            Log.d("test", "不需要补发该消息");
                        } else {
                            Log.d("test", "需要补发该消息");
                            long id = System.currentTimeMillis();
                            Action action = new Action();
                            action.sender = m1.remark;
                            action.content = m1.content;
                            AutoReplyService.instance.sendMsgToServer(id, action);
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
            Utils.updateOpenIdOrStatus(this, 1);
        } else {
            start.setText("点击开启服务");
            start.setEnabled(true);
            Utils.updateOpenIdOrStatus(this, 2);
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
                getAllFriends();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_MOMENTS) {
                mHandler.removeMessages(MSG_GET_ALL_MOMENTS);
                getAllMomentComments();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_GROUPS) {
                mHandler.removeMessages(MSG_GET_ALL_GROUPS);
                getAllGroups();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_GROUPS, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_CELLPHONES) {
                mHandler.removeMessages(MSG_GET_CELLPHONES);
                getCellPhones();
                mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_ADD_NEARBY) {
                mHandler.removeMessages(MSG_ADD_NEARBY);
                addNearBy();
                mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 24 * 60 * 60 * 1000);
            } else if (msg.what == MSG_MISSING_FISH) {
                mHandler.removeMessages(MSG_MISSING_FISH);
                missingFish();
                mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 30 * 60 * 1000);
            } else if (msg.what == MSG_CHECK_APPKEY) {
                mHandler.removeMessages(MSG_CHECK_APPKEY);
                checkAPPKey();
                mHandler.sendEmptyMessageDelayed(MSG_CHECK_APPKEY, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_MESSAGES) {
                mHandler.removeMessages(MSG_GET_ALL_MESSAGES);
                getAllMessages();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_MESSAGES, 60 * 60 * 1000);
            }
        }
    };

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

    private void missingFish() {
        int friendCount = getSharedPreferences("friendCount", 0).getInt("friendCount", 0);
        if (friendCount > MAX_FRIENDS) {
            toast("好友已经到上限了");
            return;
        }
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", FORGET_FISH_CMD);
            o.put("id", "84f119408d6441358d24b668323f0a23");
            o.put("token", "1526895528997");
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNearBy() {
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", PERSION_NEARBY_CMD);
            o.put("id", "84f119408d6441358d24b668323f0a23");
            o.put("content", DEFAULT_VALIDATION);
            o.put("token", "1526895528997");
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAllFriends() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String password = initDbPassword(getApplicationContext());
                    File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends.db");
                    final ArrayList<Friend> friends = doGetFriends(getApplicationContext(), dbFile, password);
                    int friendCount = friends.size();
                    getSharedPreferences("friendCount", 0).edit().putInt("friendCount", friendCount).commit();
                    //1.上传
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Utils.uploadFriend(getApplication(), friends);
                        }
                    });
                    //2.检查有没有转发使者
                    checkTransfer(friends);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkTransfer(ArrayList<Friend> friends) {
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

    private void getAllGroups() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String password = initDbPassword(getApplicationContext());
                    File dbFile = getWxDBFile("EnMicroMsg.db", "getAllGroups.db");
                    ArrayList<Group> groups = doGetGroups(getApplicationContext(), dbFile, password, null);
                    new MyDBHelper(getApplicationContext()).deleteWXGroups();
                    if (groups != null && groups.size() > 0) {
                        for (Group group : groups) {
                            new MyDBHelper(getApplicationContext()).addWXGroup(group);
                        }
                        Utils.uploadGroup(MainActivity.this, groups);
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

    private void doRequestFriends(ArrayList<String> requests) {
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
            AutoReplyService.instance.sendReplyImmediately(temp, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickInstruction(View view) {
        startActivity(new Intent(this, InstructionActivity.class));
    }

    public void Xposed(View view) {
        //startActivity(new Intent(this, WeChatActivity.class));
    }

}