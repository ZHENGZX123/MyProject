package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.dialog.MyProgressDialog;
import cn.kiway.mdm.dialog.NotifyShowDailog;
import cn.kiway.mdm.dialog.ShowMessageDailog;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.NetworkUtil;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.server;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.ANSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.REPONSEDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.SIGNDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.UNSWERDIALOG;
import static cn.kiway.mdm.hprose.socket.MessageType.ANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.SIGN;
import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;
import static cn.kiway.mdm.hprose.socket.MessageType.UNANSWER;

/**
 * Created by Administrator on 2017/6/9.
 */

public class BaseActivity extends Activity {

    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");

        //初始化MDM
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
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

    protected void toast(final String id) {
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
        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.MainActivity");
        //2.关闭settings.失效
        MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        //保持APP持续运行
        //MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(true);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        //MDMHelper.getAdapter().setHomeButtonDisabled(true);
        //MDMHelper.getAdapter().setVpnDisabled(true); //这个失效。
        //7.禁止修改时间
        //MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);//这个失效
        //5.1华为失效
        MDMHelper.getAdapter().setWIFIStandbyMode(2);
    }

    public void unlock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //出厂的时候去掉这个
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
        //MDMHelper.getAdapter().setVpnDisabled(true); //这个失效。
        //MDMHelper.getAdapter().setTimeAndDateSetDisabled(false);//这个失效

//        MDMHelper.getAdapter().setWifiDisabled(false);
//        MDMHelper.getAdapter().setDataConnectivityDisabled(false);
//        MDMHelper.getAdapter().setBluetoothDisabled(false);
//        MDMHelper.getAdapter().setGPSDisabled(false);
//        MDMHelper.getAdapter().setWifiApDisabled(false);
//        MDMHelper.getAdapter().setScreenCaptureDisabled(false);
//        MDMHelper.getAdapter().setNetworkLocationDisabled(false);
//        MDMHelper.getAdapter().setMicrophoneDisabled(false);
//        MDMHelper.getAdapter().setRestoreFactoryDisabled(false);
//        MDMHelper.getAdapter().setSystemUpdateDisabled(false);

        //TODO 各种黑白名单

        //移除白名单
        List<String> currentList = MDMHelper.getAdapter().getInstallPackageWhiteList();
        if (currentList.size() > 0) {
            MDMHelper.getAdapter().removeInstallPackageWhiteList(currentList);
        }

        getSharedPreferences("kiway", 0).edit().putInt("flag_camera", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_snapshot", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_location", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_dataconnectivity", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_microphone", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_restore", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_ap", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_app_open", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_usb", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_wifi", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_systemupdate", 1).commit();
        getSharedPreferences("kiway", 0).edit().putInt("flag_bluetooth", 1).commit();
    }

    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {

                    HttpGet httpRequest = new HttpGet(server + "/static/download/version/zip_student.json");
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
//        AlertDialog dialog_download = builder.setMessage("发现新的版本，是否更新？本次更新不消耗流量。").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface d, int arg1) {
//                d.dismiss();
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setAction(android.content.Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
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

    MyProgressDialog progressDialog;
    String proData = "";

    public void downloadFile(String data) {
        if (proData.equals(data) && progressDialog != null && progressDialog.isShowing())
            return;
        proData = data;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new MyProgressDialog(BaseActivity.this, proData);
                progressDialog.show();
            }
        });
    }

    public ShowMessageDailog showMessageDailog;
    int showI;

    public void Session(int i) {
        if (showI == i && showMessageDailog != null && showMessageDailog.isShowing())
            return;
        showI = i;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessageDailog = new ShowMessageDailog(BaseActivity.this);
                showMessageDailog.setCancelable(false);
                if (showI == SIGN) {
                    showMessageDailog.setShowMessage("老师上课签到，请你点击确定确认签到上课", SIGNDIALOG);
                } else if (showI == ANSWER) {
                    showMessageDailog.setShowMessage("老师有道题正在抢答，是否进去抢答", ANSWERDIALOG);
                } else if (showI == UNANSWER) {
                    showMessageDailog.setShowMessage("抢答结束了", UNSWERDIALOG);
                } else if (showI == SUREREPONSE) {
                    showMessageDailog.setShowMessage("同学们，老师这套题清楚了吗？", REPONSEDIALOG);
                }
                if (!showMessageDailog.isShowing())
                    showMessageDailog.show();
            }
        });
    }


}
