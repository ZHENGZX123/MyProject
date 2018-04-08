package com.android.kiway.activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.kiway.KWApp;
import com.android.kiway.broadcast.SampleDeviceReceiver;
import com.android.kiway.dialog.NotifyShowDailog;
import com.android.kiway.utils.NetworkUtil;
import com.android.kiway.utils.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdmsdk.MDMHelper;

import static com.android.kiway.utils.Constant.clientUrl;

/**
 * Created by Administrator on 2017/6/9.
 */

public class BaseActivity extends com.android.launcher3.BaseActivity {

    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //初始化MDM
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
        KWApp.instance.currentActivity = this;
        //2.GPS要开启
        MDMHelper.getAdapter().turnOnGPS(true);
    }

    public void setScreenOrientation() {
        int oriantation = getSharedPreferences("kiway", 0).getInt("oriantation", 0);//0竖屏1横屏
        if (oriantation == 0) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        KWApp.instance.currentActivity = this;
        setScreenOrientation();
    }

    public void showPD() {
        pd.show();
    }

    public void dismissPD() {
        pd.dismiss();
    }

    public void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void lock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.EulaActivity");
        //2.关闭settings.失效
        MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        packages.add("cn.kiway.session");
        packages.add("cn.kiway.browser");
        packages.add("cn.kiway.marketplace");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        //保持APP持续运行
        //MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(true);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        MDMHelper.getAdapter().setVpnDisabled(true);
        //7.禁止修改时间
        MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);
        //5.1华为失效
        MDMHelper.getAdapter().setWIFIStandbyMode(2);
    }

    public void unlock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //TODO出厂的时候去掉这个
        MDMHelper.getAdapter().removeDisallowedUninstallPackages();
        //2.关闭settings.慎用！！！
        MDMHelper.getAdapter().setSettingsApplicationDisabled(false);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
        //5.禁止USB，调试期间可以关闭
        MDMHelper.getAdapter().setUSBDataDisabled(false);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(false);
        MDMHelper.getAdapter().setHomeButtonDisabled(false);
        MDMHelper.getAdapter().setBackButtonDisabled(false);
        MDMHelper.getAdapter().setVpnDisabled(true);
        MDMHelper.getAdapter().setTimeAndDateSetDisabled(false);

        //7.移除白名单
        List<String> currentList = MDMHelper.getAdapter().getInstallPackageWhiteList();
        Log.d("test", "白名单size = " + currentList.size());
        if (currentList.size() > 0) {
            MDMHelper.getAdapter().removeInstallPackageWhiteList(currentList);
        }
        //8.命令重置`
        Utils.resetFunctions(this, 1);
        KWApp.instance.excuteFlagCommand();
    }

    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {

                    HttpGet httpRequest = new HttpGet(clientUrl + "static/download/version/zip_student.json");
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");
                    if (Utils.getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
                        downloadSilently(apkUrl, apkVersion);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_student_" + version + ".apk";
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
                Log.d("test", "下载成功");
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("test", "下载失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("test", "下载取消");
            }

            @Override
            public void onFinished() {
                Log.d("test", "下载结束");
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
//        AlertDialog dialog_download = builder.setMessage("发现新的版本，是否更新？本次更新不消耗流量。").setNegativeButton(android.R
// .string.ok, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface d, int arg1) {
//                d.dismiss();
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android
// .package-archive");
//                startActivity(intent);
//                finish();
//            }
//        }).setPositiveButton(android.R.string.cancel, null).create();
//        dialog_download.show();
        MDMHelper.getAdapter().installPackage(savedFilePath, true);
    }

    public void NotifyShow(final String title, final String message, final String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotifyShowDailog notifyShowDailog = new NotifyShowDailog(BaseActivity.this, title, message, name);
                notifyShowDailog.show();
            }
        });
    }




}
