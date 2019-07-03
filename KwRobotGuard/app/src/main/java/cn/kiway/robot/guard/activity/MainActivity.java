package cn.kiway.robot.guard.activity;

import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import cn.kiway.robot.guard.R;
import cn.kiway.robot.guard.service.GuideService_4;
import cn.kiway.robot.guard.service.GuideService_5;
import cn.kiway.robot.guard.util.ShowMessageDailog;

import static cn.kiway.robot.guard.util.Constant.clientUrl;
import static cn.kiway.robot.guard.util.ShowMessageDailog.MessageId.YUXUNFANWENJLU;
import static cn.kiway.robot.guard.util.Utils.getCurrentVersion;
import static cn.kiway.robot.guard.util.Utils.upgradeRootPermission;

public class MainActivity extends BaseActivity {

    private TextView versionTV;
    public static final int USAGE_STATS = 1101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        versionTV = (TextView) findViewById(R.id.version);
        versionTV.setText("当前版本号：" + getCurrentVersion(this));
        upgradeRootPermission(getPackageCodePath());
        startService();
    }

    private void startService() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
            if (hasPermission()) {
                Intent i = new Intent(this, GuideService_5.class);
                startService(i);
            } else {
                ShowMessageDailog showMessageDailog = new ShowMessageDailog(this);
                showMessageDailog.setShowMessage("请您到设置页面打开权限：选择开维教育桌面--允许访问使用记录--打开", YUXUNFANWENJLU);
                showMessageDailog.setCancelable(false);
                showMessageDailog.show();
            }
        } else {
            Intent i = new Intent(this, GuideService_4.class);
            startService(i);
        }
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USAGE_STATS) {
            if (hasPermission()) {
                Intent i = new Intent(this, GuideService_5.class);
                startService(i);
            } else {
                toast("请务必设置权限");
                startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), USAGE_STATS);
            }
        }
    }

    public void clickWechat(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        startActivity(intent);
    }

    public void clickRobot(View v) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    public void checkNewVersion(View view) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String url = clientUrl + "/static/download/version/zip_robot_launcher.json";
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
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        final String savedFilePath = "/mnt/sdcard/cache/kw_robot_launcher_" + version + ".apk";
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
        AlertDialog dialog_download = builder.setMessage("有新的版本，请点击更新").setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int arg1) {
                        di.dismiss();
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
}
