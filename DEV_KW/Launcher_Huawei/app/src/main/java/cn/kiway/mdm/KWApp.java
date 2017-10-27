package cn.kiway.mdm;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.huawei.android.pushagent.api.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.activity.TestActivity;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/6/9.
 */

public class KWApp extends Application {

    public static KWApp instance;
    public static final String server = "http://192.168.8.161:8082/mdms/";
    public Activity currentActivity;

    public static final int MSG_INSTALL = 1;
    public static final int MSG_LOCK = 2;
    public static final int MSG_UNLOCK = 3;
    public static final int MSG_LAUNCH_APP = 4;
    public static final int MSG_LAUNCH_MDM = 5;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //CrashHandler.getInstance().init(this);
        huaweiPush();
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.d("huawei", "try to get Token ,current packageName is " + this.getPackageName());
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_INSTALL) {
                String token = getSharedPreferences("kiway", 0).getString("token", "");
                String imei = Utils.getIMEI(getApplicationContext());
                installationPush(token, imei);
            } else if (msg.what == MSG_LOCK) {
                //强制锁屏
                lock();
                startActivity(new Intent(getApplicationContext(), TestActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (msg.what == MSG_UNLOCK) {
                //解除锁屏
                unlock();
                if (currentActivity != null) {
                    currentActivity.finish();
                }
            } else if (msg.what == MSG_LAUNCH_APP) {
                //打开APP
                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.homework.student");
                startActivity(intent);
            } else if (msg.what == MSG_LAUNCH_MDM) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                startActivity(intent);
            }
        }
    };

    private void unlock() {
        //初始化adapter
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        //MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(false);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(false);
        MDMHelper.getAdapter().setHomeButtonDisabled(false);
    }

    private void lock() {
        //设置锁屏模式
        //初始化adapter
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
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
        MDMHelper.getAdapter().setHomeButtonDisabled(true);
        MDMHelper.getAdapter().setBackButtonDisabled(true);
    }

    public void installationPush(final String token, final String imei) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "huaweitoken = " + token);
            JSONObject param = new JSONObject();
            param.put("appId", "c77b6c47dbcee47d7ffbc9461da0c82a");
            param.put("type", Build.TYPE);
            param.put("deviceId", token);
            param.put("userId", imei);
            param.put("module", "student");
            Log.d("test", "param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = server + "push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }
}
