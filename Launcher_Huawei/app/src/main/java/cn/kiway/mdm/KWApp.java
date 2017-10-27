package cn.kiway.mdm;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.activity.TestActivity;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.Utils.huaweiPush;

/**
 * Created by Administrator on 2017/6/9.
 */

public class KWApp extends Application {

    public static KWApp instance;
    public static final String server = "http://192.168.8.161:8082/mdms/";
    public Activity currentActivity;

    public static final int MSG_TOAST = 0;//测试
    public static final int MSG_INSTALL = 1;//注册华为
    public static final int MSG_LOCK = 2;//锁屏
    public static final int MSG_UNLOCK = 3;//解锁
    public static final int MSG_LAUNCH_APP = 4;//打开某个APP
    public static final int MSG_LAUNCH_MDM = 5;//打开MDM
    public static final int MSG_FLAGCOMMAND = 6;//flag类型的命令
    public static final int MSG_REBOOT = 7;//重启
    public static final int MSG_SHUTDOWN = 8;//重启


    public static final ArrayList<String> flagCommands = new ArrayList<>();

    static {
        flagCommands.add("camera");
        flagCommands.add("snapshot");
        flagCommands.add("location");
        flagCommands.add("dataconnectivity");
        flagCommands.add("microphone");
        flagCommands.add("restore");
        flagCommands.add("ap");
        flagCommands.add("usb");
        flagCommands.add("wifi");
        flagCommands.add("systemupdate");
        flagCommands.add("landscape");
        flagCommands.add("portrait");
        flagCommands.add("bluetooth");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //CrashHandler.getInstance().init(this);
        huaweiPush(this);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_TOAST) {
                Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
            } else if (msg.what == MSG_INSTALL) {
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
                //返回MDM桌面
                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                startActivity(intent);
            } else if (msg.what == MSG_FLAGCOMMAND) {
                //执行flag命令
                excuteFlagCommand();
            } else if (msg.what == MSG_REBOOT) {
                MDMHelper.getAdapter().rebootDevice();
            } else if (msg.what == MSG_SHUTDOWN) {
                MDMHelper.getAdapter().shutdownDevice();
            }
        }
    };

    private void excuteFlagCommand() {
        int flag_camera = getSharedPreferences("kiway", 0).getInt("flag_camera", 1);
        //这个没有对应的MDM接口，需要代码控制

        int flag_snapshot = getSharedPreferences("kiway", 0).getInt("flag_snapshot", 1);
        //MDMHelper.getAdapter().setScreenCaptureDisabled(flag_snapshot == 0);

        int flag_location = getSharedPreferences("kiway", 0).getInt("flag_location", 1);
        //MDMHelper.getAdapter().setNetworkLocationDisabled(flag_location == 0);
        MDMHelper.getAdapter().setGPSDisabled(flag_location == 0);

        int flag_dataconnectivity = getSharedPreferences("kiway", 0).getInt("flag_dataconnectivity", 1);
        MDMHelper.getAdapter().setDataConnectivityDisabled(flag_dataconnectivity == 0);

        int flag_microphone = getSharedPreferences("kiway", 0).getInt("flag_microphone", 1);
        //MDMHelper.getAdapter().setMicrophoneDisabled(flag_microphone == 0);

        int flag_restore = getSharedPreferences("kiway", 0).getInt("flag_restore", 1);
        //MDMHelper.getAdapter().setRestoreFactoryDisabled(flag_restore == 0);

        int flag_ap = getSharedPreferences("kiway", 0).getInt("flag_ap", 1);
        MDMHelper.getAdapter().setWifiApDisabled(flag_ap == 0);

        int flag_app_open = getSharedPreferences("kiway", 0).getInt("flag_app_open", 1);
        //这个没有对应的MDM接口，需要代码控制

        int flag_usb = getSharedPreferences("kiway", 0).getInt("flag_usb", 1);
        MDMHelper.getAdapter().setUSBDataDisabled(flag_usb == 0);

        int flag_wifi = getSharedPreferences("kiway", 0).getInt("flag_wifi", 1);
        //太危险了
        MDMHelper.getAdapter().setWifiDisabled(flag_wifi == 0);

        int flag_systemupdate = getSharedPreferences("kiway", 0).getInt("flag_systemupdate", 1);
        MDMHelper.getAdapter().setSystemUpdateDisabled(flag_systemupdate == 0);

        int flag_bluetooth = getSharedPreferences("kiway", 0).getInt("flag_bluetooth", 1);
        MDMHelper.getAdapter().setBluetoothDisabled(flag_bluetooth == 0);
    }

    private void unlock() {
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
        MDMHelper.getAdapter().setTaskButtonDisabled(false);
        MDMHelper.getAdapter().setHomeButtonDisabled(false);
        MDMHelper.getAdapter().setBackButtonDisabled(false);
    }

    private void lock() {
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
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
