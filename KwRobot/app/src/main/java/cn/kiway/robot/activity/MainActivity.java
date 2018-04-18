package cn.kiway.robot.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import cn.kiway.robot.R;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.robot.util.Constant;
import cn.kiway.robot.util.RootCmd;
import cn.kiway.robot.util.Utils;
import cn.kiway.wx.reply.utils.ZbusUtils;

import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Constant.qas;
import static cn.kiway.robot.util.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {


    public static MainActivity instance;
    private Button star;

    public static final int MSG_NETWORK_OK = 11;
    public static final int MSG_NETWORK_ERR = 22;
    private static final int MSG_INSTALL = 33;
    private static final int MSG_UPGRADE = 44;
    private static final int MSG_WELCOME = 55;
    private static final int MSG_GET_QA = 66;

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
        mHandler.sendEmptyMessage(MSG_INSTALL);
        mHandler.sendEmptyMessage(MSG_UPGRADE);
        mHandler.sendEmptyMessage(MSG_WELCOME);
        mHandler.sendEmptyMessage(MSG_GET_QA);
    }

    private void initView() {
        nameTV = (TextView) findViewById(R.id.name);
        star = (Button) findViewById(R.id.star);
        getPic = (CheckBox) findViewById(R.id.getPic);

        versionTV = (TextView) findViewById(R.id.version);
        versionTV.setText("当前版本号：" + getCurrentVersion(this));
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

    public void checkFCFrom(View v) {
        String forwardfrom = getSharedPreferences("FCFrom", 0).getString("FCFrom", "朋友圈使者");
        EditText et = new EditText(this);
        et.setSingleLine();
        et.setText(forwardfrom);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("当前朋友圈使者：" + forwardfrom)
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }
                        getSharedPreferences("FCFrom", 0).edit().putString("FCFrom", content).commit();
                    }
                }).setPositiveButton("取消", null).create();
        dialog.show();
    }

    public void checkFCRemark(View v) {
        String remark = getSharedPreferences("FCremark", 0).getString("FCremark", "");
        if (TextUtils.isEmpty(remark)) {
            toast("您还没有设置朋友圈备注");
        } else {
            toast("当前朋友圈备注：" + remark);
        }
    }

    public void setForwardFrom(View v) {
        startActivity(new Intent(this, SetPublicAccountActivity.class));
        if (true) {
            return;
        }
        String forwardfrom = getSharedPreferences("forwardfrom", 0).getString("forwardfrom",
                "wxid_cokkmqud47e121的接口测试号");
        EditText et = new EditText(this);
        et.setSingleLine();
        et.setText(forwardfrom);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("当前转发人：" + forwardfrom)
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }
                        getSharedPreferences("forwardfrom", 0).edit().putString("forwardfrom", content).commit();
                    }
                }).setPositiveButton("取消", null).create();
        dialog.show();
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

    public void setOpenId(View view) {
        String openId = getSharedPreferences("openId", 0).getString("openId", "osP5zwJ-lEdJVGD-_5_WyvQL9Evo");
        EditText et = new EditText(this);
        et.setSingleLine();
        et.setText(openId);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("当前openId：" + openId)
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }
                        getSharedPreferences("openId", 0).edit().putString("openId", content).commit();

                        updateOpenIdOrStatus(content);
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

    private void updateOpenIdOrStatus(Object o) {
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
                param.put("status", o);
            }
            Log.d("test", "param = " + param.toString());

            client.put(this, url, param, new TextHttpResponseHandler() {
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

    public void updateServiceCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String name = getSharedPreferences("kiway", 0).getString("name", "");
                int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0);
                int replyCount = getSharedPreferences("kiway", 0).getInt("replyCount", 0);
                nameTV.setText("昵称：" + name + "  IMEI：" + Utils.getIMEI(getApplication()) + "\n" + "接收次数：" +
                        recvCount + "，回复次数：" + replyCount);
            }
        });
    }

    private boolean isServiceEnabled() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context
                .ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager
                .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.service.AutoReplyService")) {
                return true;
            }
        }
        return false;
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    public void start(View view) {
        try {
            toast("选择“开维客服机器人“-选择“打开”");
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        } catch (Exception e) {
            e.printStackTrace();
            toast("该手机不支持微信自动回复功能");
        }
    }

    public void filter(View view) {
        startActivity(new Intent(this, FilterActivity.class));
    }

    public void logout(View view) {
        ZbusUtils.close();
        getSharedPreferences("kiway", 0).edit().clear().commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void checkNewVersion(View view) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String url = clientUrl + "/static/download/version/zip_robot.json";
                    Log.d("test", "url = " + url);
                    HttpGet httpRequest = new HttpGet(url);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
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
                        areaCode = "440303";
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
                    String welcomeTitle = obj.getString(areaCode);
                    if (TextUtils.isEmpty(welcomeTitle)) {
                        welcomeTitle = "感谢您添加招生客服，您可以发送您的问题进行人工咨询。为了减少您的等待，您可以按以下关键字发送咨询招生相关问题。谢谢！";
                    }

                    url = clientUrl + "/replyContent/keyWords?title=" + URLEncoder.encode(welcomeTitle, "utf-8") + "&origin=mp&areaCode=" + areaCode;
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

    public void getAllFriends(View view) {
        if (AutoReplyService.instance == null) {
            return;
        }
        if (AutoReplyService.instance.actions.size() < 1) {
            return;
        }
        Long firstKey = AutoReplyService.instance.actions.keySet().iterator().next();
        Action firstA = AutoReplyService.instance.actions.get(firstKey);

        int friendCount = getSharedPreferences("friendCount", 0).getInt("friendCount", 100);
        EditText et = new EditText(this);
        et.setHint("好友数量1~5000");
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setSingleLine();
        et.setText("" + friendCount);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请输入好友数量")
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }
                        int temp = 0;
                        try {
                            temp = Integer.parseInt(content);
                        } catch (Exception e) {
                            toast("必须是数字");
                            return;
                        }
                        if (temp < 1) {
                            toast("不能小于1");
                            return;
                        }
                        getSharedPreferences("friendCount", 0).edit().putInt("friendCount", temp).commit();

                        AutoReplyService.instance.getAllFriends(firstKey, firstA);
                    }
                }).setPositiveButton("取消", null).create();
        dialog.show();
    }

    public void getFriendCircle(View view) {
        if (AutoReplyService.instance == null) {
            return;
        }
        if (AutoReplyService.instance.actions.size() < 1) {
            return;
        }
        EditText et = new EditText(this);
        et.setHint("请输入好友昵称");
        et.setSingleLine();
        et.setText("5之");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("请输入好友昵称")
                .setView(et)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = et.getText().toString().trim();
                        if (TextUtils.isEmpty(content)) {
                            toast("不能为空");
                            return;
                        }

                        getSharedPreferences("FCName", 0).edit().putString("FCName", content).commit();

                        Long firstKey = AutoReplyService.instance.actions.keySet().iterator().next();
                        Action firstA = AutoReplyService.instance.actions.get(firstKey);
                        AutoReplyService.instance.getFriendCircle(firstKey, firstA);
                    }
                }).setPositiveButton("取消", null).create();
        dialog.show();
    }

    public void test(View v) {
//        String msg = "{\"sender\":\"20 小辉小号\",\"me\":\"客服888\"," +
//                "\"returnMessage\":[{\"content\":\"学位房学位房学位房学位房学位房学位房学位房学位房学位房学位房\",\"returnType\":1}," +
//                "{\"content\":\"学位房2学位房2学位房2学位房2学位房2学位房2学位房2\",\"returnType\":1}],\"id\":9999,\"time\":1523342900085," +
//                "\"content\":\"学位房\"}";
//        AutoReplyService.instance.zbusRecvs.add(new ZbusRecv(msg, false));

        int a = 100 / 0;
        System.out.println("a = " + a);
    }

    public void share(View view) {
        ShareEntity testBean = new ShareEntity("开维教育", "我是内容，描述内容。");
        testBean.setUrl("http://www.kiway.cn");//分享的地址
        testBean.setImgUrl(Environment.getExternalStorageDirectory() + "/Yjpty/photos/1.png");//只能本地地址，正方形
        ShareUtil.startShare(this, ShareConstant.SHARE_CHANNEL_WEIXIN_FRIEND, testBean, ShareConstant
                .REQUEST_CODE);
    }

    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            star.setText("服务已经开启");
            star.setEnabled(false);
            updateOpenIdOrStatus(1);
        } else {
            star.setText("点击开启服务");
            star.setEnabled(true);
            updateOpenIdOrStatus(2);
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");
                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
                        toast("有新的版本，正在后台下载，请稍等");
                        downloadSilently(apkUrl, apkVersion);
                    } else {
                        toast("已经是最新版本");
                    }
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(3);
                    e.printStackTrace();
                }
            } else if (msg.what == 3) {
                toast("检查更新失败");
            } else if (msg.what == 4) {
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            } else if (msg.what == 5) {
                String savedFilePath = (String) msg.obj;
                String cmd = "pm install -r " + savedFilePath;
                int ret = RootCmd.execRootCmdSilent(cmd);
                Log.d("test", "execRootCmdSilent ret = " + ret);
            } else if (msg.what == MSG_NETWORK_OK) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.GONE);
                Log.d("test", "有网络");
            } else if (msg.what == MSG_NETWORK_ERR) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.VISIBLE);
                Log.d("test", "无网络");
                ZbusUtils.close();
            } else if (msg.what == MSG_INSTALL) {
                Utils.installationPush(getApplication());
                mHandler.removeMessages(MSG_INSTALL);
                mHandler.sendEmptyMessageDelayed(MSG_INSTALL, 2 * 60 * 60 * 1000);
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
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        final String savedFilePath = "/mnt/sdcard/cache/kw_robot_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Log.d("test", "onSuccess");
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.d("test", "onFinished");
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        startInstall(savedFilePath);
    }

    private void startInstall(String savedFilePath) {
        Message msg = new Message();
        if (RootCmd.haveRoot()) {
            msg.what = 5;
        } else {
            msg.what = 4;
        }
        msg.obj = savedFilePath;
        mHandler.sendMessage(msg);
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