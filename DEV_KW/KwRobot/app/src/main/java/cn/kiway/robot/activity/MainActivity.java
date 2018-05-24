package cn.kiway.robot.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private Button start;

    public static final int MSG_NETWORK_OK = 11;
    public static final int MSG_NETWORK_ERR = 22;
    private static final int MSG_UPGRADE = 33;
    private static final int MSG_WELCOME = 44;
    private static final int MSG_GET_QA = 55;
    private static final int MSG_GET_CELLPHONES = 66;

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

        mHandler.sendEmptyMessage(MSG_UPGRADE);
        mHandler.sendEmptyMessage(MSG_WELCOME);
        mHandler.sendEmptyMessage(MSG_GET_QA);
        mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 60 * 60 * 1000);
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
        String oldCollector = getSharedPreferences("collector", 0).getString("collector", "我的KW");
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

    private int lastStatus = -1;

    public void updateOpenIdOrStatus(Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String xtoken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    String robotId = getSharedPreferences("kiway", 0).getString("robotId", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    Log.d("test", "xtoken = " + xtoken);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/robot/" + robotId;
                    Log.d("test", "updateOpenIdOrStatus url = " + url);

                    com.loopj.android.http.RequestParams param = new com.loopj.android.http.RequestParams();
                    if (o instanceof String) {
                        param.put("openId", o);
                    } else if (o instanceof Integer) {
                        if (lastStatus == (int) o) {
                            Log.d("test", "状态一致，本次不上报");
                            return;
                        }
                        param.put("status", o);
                        lastStatus = (int) o;
                    }
                    Log.d("test", "param = " + param.toString());

                    client.put(MainActivity.this, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "updateOpenIdOrStatus onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "updateOpenIdOrStatus onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
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
                nameTV.setText("帐号：" + username + " 昵称：" + name + " 微信号：" + wxNo + "\n接收次数：" + recvCount + "回复次数：" +
                        replyCount + "\nareaCode：" + areaCode);
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

    private void getQA() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String url = clientUrl + "/static/download/version/lmhf.json";
                    Log.d("test", "url = " + url);
                    HttpGet httpRequest = new HttpGet(url);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                    Log.d("test", "getQA  = " + ret);
                    JSONObject obj = new JSONObject(ret);
                    Iterator<String> sIterator = obj.keys();
                    while (sIterator.hasNext()) {
                        String key = sIterator.next();
                        String value = obj.getString(key);
                        Constant.qas.put(key, value);
                    }
                    Log.d("test", "qas size = " + qas.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void getWelcome() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    if (TextUtils.isEmpty(areaCode)) {
                        toast("areaCode为空");
                        return;
                    }
                    Log.d("test", "areaCode = " + areaCode);

                    String url = clientUrl + "/static/download/version/subscribe.json";
                    Log.d("test", "url1 = " + url);
                    HttpGet httpRequest = new HttpGet(url);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                    Log.d("test", "getWelcomeTitle  = " + ret);
                    JSONObject obj = new JSONObject(ret);
                    String welcomeTitle = obj.optString(areaCode);
                    if (TextUtils.isEmpty(welcomeTitle)) {
                        welcomeTitle = DEFAULT_WELCOME_TITLE;
                    }
                    getSharedPreferences("welcomeTitle", 0).edit().putString("welcomeTitle", welcomeTitle).commit();

                    url = clientUrl + "/replyContent/keyWords?title=" + URLEncoder.encode(welcomeTitle, "utf-8") +
                            "&origin=mp&areaCode=" + areaCode;
                    Log.d("test", "url2 = " + url);
                    httpRequest = new HttpGet(url);
                    client = new DefaultHttpClient();
                    response = client.execute(httpRequest);
                    ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "getWelcome  = " + ret);
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
//        String fakeRecv = "{\"sender\":\"小辉小号\",\"me\":\"客服888\"," +
//                "\"returnMessage\":[{\"content\":\"http://upload.jnwb.net/2014/0311/1394514005639.jpg\"," +
//                "\"returnType\":2}]," +
//                "\"id\":9999,\"time\":1523342900085," +
//                "\"content\":\"学位房\"}";
//        AutoReplyService.instance.sendReplyImmediately(fakeRecv, false);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AutoReplyService.instance.test(AutoReplyService.instance.getRootInActiveWindow());
            }
        }, 10000);

//        Utils.updateUserStatus("18626318013", 2);
//        getSharedPreferences("currentUser", 0).edit().putInt("currentUser", 1).commit();
//        getCellPhones();

//        ArrayList<AddFriend> requests = new ArrayList();
//        AddFriend af = new AddFriend();
//        af.phone = "18565808596";
//        requests.add(af);
//        doRequestFriends(requests);

    }

    public void test2(View v) {
//        AddFriend a = new MyDBHelper(getApplicationContext()).getAddFriendByRemark("11 10 执着");
//        Log.d("test", "a  = " + a);
//        ArrayList<AddFriend> afs = new MyDBHelper(getApplicationContext()).getAddFriends();
//        for (AddFriend af : afs) {
//            Log.d("test", "af = " + af);
//        }
        int a = 100;
        System.out.println(a / 0);
    }

    public void sharePic(View view) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setImagePath("/mnt/sdcard/1520306782983.jpg");//1523179210665
        //sp.setText("sdfsadfasfdfdfadfs");
        sp.setShareType(Platform.SHARE_IMAGE);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);
    }

    public void shareFile(View view) {
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle("我给你分享了文件2");
        sp.setFilePath(KWApplication.ROOT + "downloads/1394514005639.jpg");
        sp.setImagePath(KWApplication.ROOT + "downloads/1394514005639.jpg");
        sp.setShareType(Platform.SHARE_FILE);
        wx.share(sp);
    }

    public void shareVideo(View view) {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("我给你分享了视频");
        sp.setTitle("我给你分享了视频");
        sp.setUrl("http://www.baidu.com/test.mp4");//网络地址
        sp.setImagePath(KWApplication.ROOT + "downloads/1394514005639.jpg");//缩略图  本地
        sp.setShareType(Platform.SHARE_VIDEO);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);
    }

    public void shareWeb(View view) {
        /*Platform.ShareParams sp = new Platform.ShareParams();
        sp.setText("测试分享的文本");
        sp.setTitle("ccccccccc");
        sp.setUrl("http://www.kiway.cn");
        //sp.setImagePath("/mnt/sdcard/1520306782983.jpg");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform wx = ShareSDK.getPlatform(Wechat.NAME);
        wx.share(sp);*/

        //朋友圈
//        Platform.ShareParams sp = new Platform.ShareParams();
//        sp.setText("fdsafdsafsdaf");
//        sp.setImageArray(new String[]{KWApplication.defaultVideo, KWApplication.defaultFile});
//        sp.setShareType(Platform.SHARE_IMAGE);
//        Platform wx = ShareSDK.getPlatform(WechatMoments.NAME);
//        wx.share(sp);

        ArrayList<File> files = new ArrayList<>();
        files.add(new File(KWApplication.defaultFile));
        files.add(new File(KWApplication.defaultVideo));

        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putExtra("Kdescription", "dis");
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        for (File f : files) {
            imageUris.add(Uri.fromFile(f));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivity(intent);

        //不能绕过审核
//        Platform.ShareParams sp = new Platform.ShareParams();
//        sp.setText("fdsafdsafsdaf");
//        sp.setShareType(Platform.SHARE_TEXT);
//        Platform wx = ShareSDK.getPlatform(WechatMoments.NAME);
//        wx.share(sp);
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
            updateOpenIdOrStatus(1);
        } else {
            start.setText("点击开启服务");
            start.setEnabled(true);
            updateOpenIdOrStatus(2);
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
            } else if (msg.what == MSG_WELCOME) {
                mHandler.removeMessages(MSG_WELCOME);
                getWelcome();
                mHandler.sendEmptyMessageDelayed(MSG_WELCOME, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_QA) {
                mHandler.removeMessages(MSG_GET_QA);
                getQA();
                mHandler.sendEmptyMessageDelayed(MSG_GET_QA, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_CELLPHONES) {
                mHandler.removeMessages(MSG_GET_CELLPHONES);
                getCellPhones();
                mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 24 * 60 * 60 * 1000);
            }
        }
    };

    private void getCellPhones() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int current = getSharedPreferences("currentUser", 0).getInt("currentUser", 1);
                    String areaCode = getSharedPreferences("kiway", 0).getString("areaCode", "");
                    if (TextUtils.isEmpty(areaCode)) {
                        toast("areaCode为空");
                        return;
                    }
                    Log.d("test", "areaCode = " + areaCode);
                    //hardcode
                    //areaCode = "440302";
                    String url = clientUrl + "/users?status=0&areaCode=" + areaCode + "&current=" + current + "&size=10";
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
                    getSharedPreferences("currentUser", 0).edit().putInt("currentUser", current + 1).commit();

                    long currentTime = System.currentTimeMillis();
                    ArrayList<AddFriend> requests = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        JSONObject temp = list.getJSONObject(i);
                        AddFriend af = new AddFriend();
                        af.requesttime = Utils.longToDate(currentTime);
                        af.phone = temp.getString("phone").trim();
                        boolean has = Utils.checkHasRequested(getApplicationContext(), af.phone);
                        if (has) {
                            Log.d("test", af.phone + "之前已经申请过了");
                        } else {
                            Log.d("test", af.phone + "还没有申请过");
                            requests.add(af);
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

    private void doRequestFriends(ArrayList<AddFriend> requests) {
        JSONObject o = null;
        try {
            o = new JSONObject();
            o.put("cmd", "addFriendCmd");
            o.put("id", "84f119408d6441358d24b668323f0a23");
            JSONObject content = new JSONObject();
            JSONArray members = new JSONArray();
            for (AddFriend af : requests) {
                members.put(af.phone);
                //添加进数据库
                new MyDBHelper(getApplicationContext()).addAddFriend(af);
            }
            content.put("content", "你好，可以加个好友吗？");
            content.put("members", members);
            o.put("content", content);
            o.put("token", "1526895528997");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String temp = o.toString();
        Log.d("test", "temp = " + temp);
        AutoReplyService.instance.sendReplyImmediately(temp, true);
    }

    public void clickInstruction(View view) {
        startActivity(new Intent(this, InstructionActivity.class));
    }

    //-----------------------------新增功能------------------------

    public void Xposed(View view) {
        //1.获取所有的好友
        //2.上报给易敏
        toast("当前仅供测试");
        startActivity(new Intent(this, WeChatActivity.class));
    }

}