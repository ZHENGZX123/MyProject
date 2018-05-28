package cn.kiway.robot.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

import static cn.kiway.robot.activity.WeChatActivity.WX_ROOT_PATH;
import static cn.kiway.robot.util.Constant.ADD_FRIEND_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_VALIDATION;
import static cn.kiway.robot.util.Constant.DEFAULT_WELCOME_TITLE;
import static cn.kiway.robot.util.Constant.FORGET_FISH_CMD;
import static cn.kiway.robot.util.Constant.PERSION_NEARBY_CMD;
import static cn.kiway.robot.util.Constant.WX_DB_DIR_PATH;
import static cn.kiway.robot.util.Constant.WX_SP_UIN_PATH;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static cn.kiway.robot.util.Utils.getCurrentVersion;
import static com.mob.tools.utils.ResHelper.copyFile;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private Button start;

    public static final int MSG_NETWORK_OK = 101;
    public static final int MSG_NETWORK_ERR = 102;
    private static final int MSG_UPGRADE = 103;
    private static final int MSG_WELCOME = 104;
    private static final int MSG_GET_QA = 105;
    private static final int MSG_GET_CELLPHONES = 106;//主动根据号码加好友
    private static final int MSG_GET_VALIDATION = 107;
    private static final int MSG_ADD_NEARBY = 108;    //主动加附近的人
    private static final int MSG_MISSING_FISH = 109;    //主动加附近的人
    private static final int MSG_GET_ALL_FRIENDS = 110;//上报所有好友

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
        mHandler.sendEmptyMessage(MSG_GET_VALIDATION);
        mHandler.sendEmptyMessageDelayed(MSG_GET_CELLPHONES, 60 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_ADD_NEARBY, 80 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 100 * 60 * 1000);
        //mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 120 * 60 * 1000);
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

    public void getValidation() {
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

                    String url = clientUrl + "/static/download/version/label.json";
                    Log.d("test", "url1 = " + url);
                    HttpGet httpRequest = new HttpGet(url);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                    Log.d("test", "getValidation  = " + ret);
                    JSONObject obj = new JSONObject(ret);
                    String validation = obj.optString(areaCode);
                    if (TextUtils.isEmpty(validation)) {
                        validation = DEFAULT_VALIDATION;
                    }
                    getSharedPreferences("validation", 0).edit().putString("validation", validation).commit();
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

//        Utils.updateUserStatus("18626318013", 2);

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
//        Utils.uploadFriend(this, "5之", "4 5之", "test2", "test2");
//        getAllFriends();
//        addNearBy();
//        missingFish();

        new MyDBHelper(getApplicationContext()).deleteAddFriends();
        getCellPhones();
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
            } else if (msg.what == MSG_WELCOME) {
                mHandler.removeMessages(MSG_WELCOME);
                getWelcome();
                mHandler.sendEmptyMessageDelayed(MSG_WELCOME, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_QA) {
                mHandler.removeMessages(MSG_GET_QA);
                getQA();
                mHandler.sendEmptyMessageDelayed(MSG_GET_QA, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_VALIDATION) {
                mHandler.removeMessages(MSG_GET_VALIDATION);
                getValidation();
                mHandler.sendEmptyMessageDelayed(MSG_GET_VALIDATION, 8 * 60 * 60 * 1000);
            } else if (msg.what == MSG_GET_ALL_FRIENDS) {
                mHandler.removeMessages(MSG_GET_ALL_FRIENDS);
                getAllFriends();
                mHandler.sendEmptyMessageDelayed(MSG_GET_ALL_FRIENDS, 24 * 60 * 60 * 1000);
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
                mHandler.sendEmptyMessageDelayed(MSG_MISSING_FISH, 24 * 60 * 60 * 1000);
            }

        }
    };

    private void missingFish() {
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

    private List<File> mWxDbPathList = new ArrayList<>();
    private long latestModified;
    private String mCurrApkPath = "/data/data/cn.kiway.robot/";
    private static final String COPY_WX_DATA_DB = "wx_data.db";

    private void getAllFriends() {
        new Thread() {
            @Override
            public void run() {
                // 获取root权限
                execRootCmdSilent("chmod -R 777 " + WX_ROOT_PATH);
                execRootCmdSilent("chmod  777 /data/data/com.tencent.mm/shared_prefs/auth_info_key_prefs.xml");
                // 获取微信的Uid
                String Uin = initCurrWxUin();

                // 获取 IMEI 唯一识别码
                TelephonyManager phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                String IMEI = phone.getDeviceId();
                System.out.println("IMEI" + IMEI);

                // 根据imei和uin生成的md5码，获取数据库的密码（去前七位的小写字母）
                String password = initDbPassword(IMEI, Uin);
                System.out.println(password + "数据库的密码");

                //  递归查询微信本地数据库文件
                mWxDbPathList.clear();
                File wxDataDir = new File(WX_DB_DIR_PATH);
                searchFile(wxDataDir, "EnMicroMsg.db");

                File latestFile = null;
                for (File f : mWxDbPathList) {
                    System.out.println("f = " + f.lastModified());
                    if (f.lastModified() > latestModified) {
                        latestModified = f.lastModified();
                        latestFile = f;
                    }
                }

                System.out.println("查询数据库文件");

                int count = mWxDbPathList.size();
                System.out.println("count = " + count);

                File file = latestFile;
                System.out.println("file = " + file.getAbsolutePath());
                String copyFilePath = mCurrApkPath + COPY_WX_DATA_DB;

                System.out.println("copyFilePath = " + copyFilePath);
                //将微信数据库拷贝出来，因为直接连接微信的db，会导致微信崩溃
                copyFile(file.getAbsolutePath(), copyFilePath);
                File copyWxDataDb = new File(copyFilePath);
                openWxDb(copyWxDataDb, password);
            }
        }.start();
    }

    private void openWxDb(File dbFile, String password) {

        SQLiteDatabase.loadLibs(this);
        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
            public void preKey(SQLiteDatabase database) {
            }

            public void postKey(SQLiteDatabase database) {
                database.rawExecSQL("PRAGMA cipher_migrate;"); //兼容2.0的数据库
            }
        };

        try {
            //打开数据库连接
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbFile, password, null, hook);
            Cursor c1 = db.rawQuery("select * from rcontact where username not like 'gh_%' and verifyFlag<>24 and verifyFlag<>29 and verifyFlag<>56 and type<>33 and type<>70 and verifyFlag=0 and type<>4 and type<>0 and showHead<>43 and type<>65536 and type<>1", null);
            c1.moveToFirst();
            int columnCount = c1.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                System.out.println("column = " + c1.getColumnName(i));
            }
            c1.moveToFirst();
            ArrayList<Friend> friends = new ArrayList<>();
            while (c1.moveToNext()) {
                String username = c1.getString(c1.getColumnIndex("username"));  //wxID
                String alias = c1.getString(c1.getColumnIndex("alias"));        //wxNo
                String nickname = c1.getString(c1.getColumnIndex("nickname"));  //nickname
                String conRemark = c1.getString(c1.getColumnIndex("conRemark"));//remark

                System.out.println("usename = " + username);
                System.out.println("alias = " + alias);
                System.out.println("nickname = " + nickname);
                System.out.println("conRemark = " + conRemark);

                friends.add(new Friend(nickname, conRemark, username, alias));
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Utils.uploadFriend(getApplication(), friends);
                }
            });
            c1.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("读取数据库信息失败");
        }
    }

    private void searchFile(File file, String fileName) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    searchFile(childFile, fileName);
                }
            }
        } else {
            if (fileName.equals(file.getName())) {
                mWxDbPathList.add(file);
            }
        }
    }

    private String initDbPassword(String imei, String uin) {
        if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(uin)) {
            LogUtil.e("初始化数据库密码失败：imei或uid为空");
            return null;
        }
        String md5 = getMD5(imei + uin);
        System.out.println(imei + uin + "初始数值");
        System.out.println(md5 + "MD5");
        String password = md5.substring(0, 7).toLowerCase();
        System.out.println("加密后" + password);
        return password;
    }

    public String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private String initCurrWxUin() {
        String Uin = null;
        File file = new File(WX_SP_UIN_PATH);
        try {
            FileInputStream in = new FileInputStream(file);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(in);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                if ("_auth_uin".equals(element.attributeValue("name"))) {
                    Uin = element.attributeValue("value");
                }
            }

            return Uin;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("获取微信uid失败，请检查auth_info_key_prefs文件权限");
        }

        return null;
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
        //1.获取所有的好友
        //2.上报给易敏
        toast("当前仅供测试");
        startActivity(new Intent(this, WeChatActivity.class));
    }

}