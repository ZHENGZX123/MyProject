package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.mdm.MDMHelper;

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
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
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
        //5.1失效
        //MDMHelper.getAdapter().setWIFIStandbyMode(2);
    }

    public void unlock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(false);
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


}
