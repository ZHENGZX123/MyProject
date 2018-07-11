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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.kiway.robot.util.Constant.ADD_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_TRANSFER;
import static cn.kiway.robot.util.Constant.DEFAULT_VALIDATION;
import static cn.kiway.robot.util.Constant.FORGET_FISH_CMD;
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
    private static final int MSG_GET_ALL_GROUPS = 112;//上报所有群组
    private static final int MSG_GET_ALL_MESSAGES = 113;//上报所有群组
    private static final int MSG_CHECK_APPKEY = 114;//检测key是否有效

    private TextView nameTV;
    private CheckBox getPic;
    private TextView versionTV;
    private int friendCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        initListener();
        checkRoot(null);
        Utils.installationPush(getApplication());

        mHandler.sendEmptyMessage(MSG_UPGRADE);
        mHandler.sendEmptyMessage(MSG_GET_BASEDATA);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 60 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 80 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 10 * 60 * 1000);
        mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 120 * 60 * 1000);
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

    public void setCollector(View view) {
        String oldCollector = getSharedPreferences("collector", 0).getString("collector", "转发使者");
        EditText et = new EditText(this);
        et.setSingleLine();
        et.setText(oldCollector);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("当前消息收集微信（群）：" + oldCollector)
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }
                        getSharedPreferences("collector", 0).edit().putString("collector", content).commit();
                        //这个消息收集器是自动要过滤的，先减后加
                        String filters = getSharedPreferences("filters", 0).getString("filters", "");
                        filters = filters.replace("===" + oldCollector, "");
                        getSharedPreferences("filters", 0).edit().putString("filters", filters).commit();

                        filters = getSharedPreferences("filters", 0).getString("filters", "");
                        getSharedPreferences("filters", 0).edit().putString("filters", filters + "===" + content)
                                .commit();
                    }
                }).setPositiveButton("取消", null).create();
        dialog.show();
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
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    String password = initDbPassword(getApplicationContext());
//                    File dbFile = getWxDBFile("SnsMicroMsg.db");
////                    doGetGroups(getApplicationContext(), dbFile, password, null);
//                    //ArrayList<String> peoples = doGetPeopleInGroup(getApplicationContext(), dbFile, password, "9189004002@chatroom");
//                    doGetMoments(getApplicationContext(), dbFile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
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
        if (friendCount > 4900) {
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
                    ArrayList<Friend> friends = doGetFriends(getApplicationContext(), dbFile, password);
                    friendCount = friends.size();
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