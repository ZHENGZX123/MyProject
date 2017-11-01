package cn.kiway.mdm;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.activity.ScreenActivity;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.HttpDownload;
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
    public static final int MSG_SHUTDOWN = 8;//关机
    public static final int MSG_PUSH_FILE = 9;//下载文件
    public static final int MSG_OPEN_FILE = 10;//打开文件

    public static boolean shangke = false;

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
        flagCommands.add("allowWifi");
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
            if (msg.what == MSG_INSTALL) {
                String token = getSharedPreferences("kiway", 0).getString("token", "");
                String imei = Utils.getIMEI(getApplicationContext());
                Utils.installationPush(instance, token, imei);
            } else if (msg.what == MSG_LOCK) {
                //强制锁屏
                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
                MDMHelper.getAdapter().setTaskButtonDisabled(true);
                MDMHelper.getAdapter().setHomeButtonDisabled(true);
                MDMHelper.getAdapter().setBackButtonDisabled(true);
                startActivity(new Intent(getApplicationContext(), ScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else if (msg.what == MSG_UNLOCK) {
                //解除锁屏
                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
                MDMHelper.getAdapter().setTaskButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
                MDMHelper.getAdapter().setBackButtonDisabled(false);
                if (currentActivity != null) {
                    currentActivity.finish();
                }
            } else if (msg.what == MSG_LAUNCH_APP) {
                //打开APP，如果没安装怎么办
                shangke = true;
                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
                MDMHelper.getAdapter().setTaskButtonDisabled(true);
                MDMHelper.getAdapter().setHomeButtonDisabled(true);
                Utils.launchApp(getApplicationContext(), msg.obj.toString());
            } else if (msg.what == MSG_LAUNCH_MDM) {
                shangke = false;
                //返回MDM桌面
                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
                MDMHelper.getAdapter().setTaskButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
                Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                startActivity(intent);
            } else if (msg.what == MSG_FLAGCOMMAND) {
                //执行flag命令
                excuteFlagCommand();
            } else if (msg.what == MSG_REBOOT) {
                MDMHelper.getAdapter().rebootDevice();
            } else if (msg.what == MSG_SHUTDOWN) {
                MDMHelper.getAdapter().shutdownDevice();
            } else if (msg.what == MSG_PUSH_FILE) {
                handlePushFile(msg.obj.toString());
            } else if (msg.what == MSG_OPEN_FILE) {
                Utils.openFile(getApplicationContext(), msg.obj.toString());
            }
        }
    };

    private void handlePushFile(String c) {
        try {
            JSONObject content = new JSONObject(c);
            final String url = content.getString("url");
            String size = content.getString("size");
            String name = content.getString("name");
            Toast.makeText(getApplicationContext(), "老师给你发来文件：" + name, Toast.LENGTH_SHORT).show();
            new Thread() {
                @Override
                public void run() {
                    final String filename = url.substring(url.lastIndexOf("/") + 1);
                    final String folder = "/mnt/sdcard/kiway_mdm/file/";
                    final String filePath = folder + filename;
                    //文件已存在,直接打开
                    if (new File(filePath).exists()) {
                        Message m = new Message();
                        m.what = MSG_OPEN_FILE;
                        m.obj = filePath;
                        mHandler.sendMessage(m);
                        return;
                    }
                    //1.下载文件
                    //2.弹出提示
                    int ret = new HttpDownload().downFile(url, folder, filename);
                    Log.d("test", "download ret = " + ret);
                    if (ret == -1) {
                        return;
                    }
                    Message m = new Message();
                    m.what = MSG_OPEN_FILE;
                    m.obj = filePath;
                    mHandler.sendMessage(m);
                }
            }.start();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
        //MDMHelper.getAdapter().setWifiDisabled(flag_wifi == 0);

        int flag_systemupdate = getSharedPreferences("kiway", 0).getInt("flag_systemupdate", 1);
        MDMHelper.getAdapter().setSystemUpdateDisabled(flag_systemupdate == 0);

        int flag_bluetooth = getSharedPreferences("kiway", 0).getInt("flag_bluetooth", 1);
        MDMHelper.getAdapter().setBluetoothDisabled(flag_bluetooth == 0);
    }

}
