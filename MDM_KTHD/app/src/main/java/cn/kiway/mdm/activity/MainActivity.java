package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.NetworkUtil;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.rtc.Constants;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.IContants.CHECK_VERSION_URL;
import static cn.kiway.mdm.utils.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {

    public static MainActivity instantce;
    private Dialog dialog_download;
    TextView className, studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantce = this;
        setContentView(R.layout.activity_main);
        getAppData();
        initView1();
    }

    void initView1() {
        className = (TextView) findViewById(R.id.className);
        studentName = (TextView) findViewById(R.id.studentName);
        studentName.setText(getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
        className.setText(getSharedPreferences("kiwaykthd", 0).getString("className", ""));
    }


    public void onInfo(View view) {//个人信息
        startActivity(new Intent(this, UserInfoActivity.class));
        // startPlayer();
        //  startActivity(new Intent(this, AnswerQuestionsAcitivity.class));
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void logout(View view) {

    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void onKT(View view) {
        //课堂分析
    }


    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra("studentName") != null)//学生名字
                getSharedPreferences("kiwaykthd", 0).edit().putString("studentName", intent.getStringExtra
                        ("studentName")).commit();

            if (intent.getStringExtra("className") != null)//班级名字
                getSharedPreferences("kiwaykthd", 0).edit().putString("className", intent.getStringExtra
                        ("className")).commit();
            if (intent.getStringExtra("studentNumber") != null)//学号
                getSharedPreferences("kiwaykthd", 0).edit().putString("studentNumber", intent.getStringExtra
                        ("studentNumber")).commit();

            if (intent.getStringExtra("classId") != null)//班级Id
                getSharedPreferences("kiwaykthd", 0).edit().putString("classId", intent.getStringExtra
                        ("classId")).commit();

            if (intent.getStringExtra("schoolId") != null)//学校Id
                getSharedPreferences("kiwaykthd", 0).edit().putString("schoolId", intent.getStringExtra
                        ("schoolId")).commit();

            if (intent.getStringExtra("huaweiToken") != null)//华为token
                getSharedPreferences("kiwaykthd", 0).edit().putString("huaweiToken", intent.getStringExtra
                        ("huaweiToken")).commit();
            String shangke = intent.getStringExtra("shangke");
            Logger.log("shangke::::::" + shangke);
            if (shangke != null && !shangke.equals("")) {
            } else {
                checkNewVersion();
            }
        } else {
            checkNewVersion();
        }
    }


    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpGet httpRequest = new HttpGet(CHECK_VERSION_URL);
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

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");

                    String zipCode = new JSONObject(ret).getString("zipCode");
                    String zipUrl = new JSONObject(ret).getString("zipUrl");
                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
//                        showUpdateConfirmDialog(apkUrl);
                        downloadSilently(apkUrl, apkVersion);
                    }
//                   else {****************************暂时没有zip包更新 所以不用
//                        //如果APK没有最新版本，比较包的版本。如果内置包的版本号比较高，直接替换
//                        boolean flag = false;
//                        String currentPackage = getSharedPreferences("kiway", 0).getString("version_package",
// "0.0.1");
//                        if (currentPackage.compareTo(currentPackageVersion) < 0) {
//                            Log.d("test", "内置包的版本大，替换新包");
//                            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
//                            checkIsFirst();
//                            flag = true;
//                        }
//                        //替换完内置包之后，比较内置包和外包，如果版本号还是小了，更新外包
//                        currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
//                        String outer_package = zipCode;
//                        if (currentPackage.compareTo(outer_package) < 0) {//提示有新的包，下载新的包
//                            Log.d("test", "下载新的H5包");
//                            updatePackage(outer_package, zipUrl);
//                        } else {
//                            Log.d("test", "H5包是最新的");
//                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_zhkt_" + version + ".apk";
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
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(getString(R.string.new_version)).setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog_download.dismiss();
                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = savedFilePath;
                        mHandler.sendMessage(msg);
                    }
                }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog_download.show();
    }

    ////////////////以上是版本更新
    long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (app.isAttenClass) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //--------------------------------------------------2.0新增--------------------------

    public void startPlayer() {
        //1.开始播放教师推屏
        Intent i = new Intent(MainActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, "kiway");
        startActivity(i);
    }

    public void clickSnapshot(View view) {

    }

    public void clickAsk(View view) {

    }


}