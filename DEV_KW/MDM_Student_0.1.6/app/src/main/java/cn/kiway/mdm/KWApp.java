package cn.kiway.mdm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.File;

import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.ScreenMDMActivity;
import cn.kiway.mdm.aidlservice.RemoteAidlService;
import cn.kiway.mdm.utils.HttpDownload;
import cn.kiway.mdm.utils.HttpUtil;
import cn.kiway.mdm.utils.Utils;
import cn.kiway.mdmsdk.MDMHelper;

import static cn.kiway.mdm.utils.AppListUtils.isAppInstalled;
import static cn.kiway.mdm.utils.Constant.ZHIHUIKETANGPG;
import static cn.kiway.mdm.utils.Utils.huaweiPush;

/**
 * Created by Administrator on 2017/6/9.
 */

public class KWApp extends Application {

//    public static final String serverUrl = "http://192.168.8.161:8083/";
//    public static final String clientUrl = "http://192.168.8.161:8084/";

//    public static final String serverUrl = "http://202.104.136.9:8083/";
//    public static final String clientUrl = "http://202.104.136.9:8084/";

    public static final String serverUrl = "http://www.yuertong.com:8083/";
    public static final String clientUrl = "http://www.yuertong.com:8084/";


    public static final int MSG_TOAST = 0;//Toast
    public static final int MSG_INSTALL = 1;//注册华为
    public static final int MSG_LOCK = 2;//锁屏
    public static final int MSG_LOCKONCLASS = -2;//上课锁屏
    public static final int MSG_UNLOCK = 3;//解锁
    public static final int MSG_LAUNCH_APP = 4;//打开某个APP
    public static final int MSG_LAUNCH_MDM = 5;//打开MDM
    public static final int MSG_FLAGCOMMAND = 6;//flag类型的命令
    public static final int MSG_PUSH_FILE = 7;//下载文件
    public static final int MSG_OPEN_FILE = 8;//打开文件
    public static final int MSG_REBOOT = 9;//重启
    public static final int MSG_SHUTDOWN = 10;//关机
    public static final int MSG_PORTRAIT = 11;//横屏
    public static final int MSG_LANDSCAPE = 12;//竖屏
    public static final int MSG_UNINSTALL = 13;//卸载
    public static final int MSG_PARENT_BIND = 14;//绑定家长
    public static final int MSG_ATTEND_CALSS = 15;//上课
    public static final int MSG_GET_OUT_OF_CALASS = 16;//下课
    public static final int MSG_SMS = 17;//短信
    public static final int MSG_MESSAGE = 18;//发送消息
    public static final int MSG_PUSH_FILE_I = 19;//局域网接收文件

    public static KWApp instance;
    public static boolean temporary_app = false;
    public Activity currentActivity;

    private boolean isAttendClass;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_TOAST) {
                if (msg.obj != null) {
                    Toast.makeText(KWApp.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == MSG_INSTALL) {
                String token = getSharedPreferences("huawei", 0).getString("token", "");
                String imei = Utils.getIMEI(getApplicationContext());
                HttpUtil.installationPush(instance, token, imei);
            } else if (msg.what == MSG_LOCK) {
                if (isAttendClass) {
                    if (msg.obj == null)
                        msg.obj = "";
                    RemoteAidlService.accpterMessage(currentActivity, msg.obj.toString());
                } else {
                    if (currentActivity != null && currentActivity instanceof ScreenMDMActivity) {
                        return;
                    }
                    MDMHelper.getAdapter().setBackButtonDisabled(true);
                    MDMHelper.getAdapter().setHomeButtonDisabled(true);
                    //强制锁屏
                    startActivity(new Intent(getApplicationContext(), ScreenMDMActivity.class).addFlags(Intent
                            .FLAG_ACTIVITY_NEW_TASK));
                }
                //1.熄灭屏幕
                //DevicePolicyManager mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context
                // .DEVICE_POLICY_SERVICE);
                //mDevicePolicyManager.lockNow();
                //2.静音
                mute();
            } else if (msg.what == MSG_LOCKONCLASS) {
                MDMHelper.getAdapter().setBackButtonDisabled(true);
                MDMHelper.getAdapter().setHomeButtonDisabled(true);
                mute();
            } else if (msg.what == MSG_UNLOCK) {
                //解除锁屏
                MDMHelper.getAdapter().setBackButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
                if (msg.obj == null)
                    msg.obj = "";
                RemoteAidlService.accpterMessage(currentActivity, msg.obj.toString());
                if (currentActivity != null && currentActivity instanceof ScreenMDMActivity) {
                    currentActivity.finish();
                }
                //1.点亮屏幕
                //PowerManager mPowerManager = ((PowerManager) getSystemService(POWER_SERVICE));
                //PowerManager.WakeLock mScreenLock = mPowerManager.newWakeLock(
                //        PowerManager.ACQUIRE_CAUSES_WAKEUP
                // 该flag使能屏幕关闭时，也能点亮屏幕（通常的wakelock只能维持屏幕处于一直开启状态，如果灭屏时，是不会自动点亮的）
                //                | PowerManager.SCREEN_DIM_WAKE_LOCK
                //                | PowerManager.ON_AFTER_RELEASE, "screenOnWakeLock");
                //mScreenLock.acquire();
                //mScreenLock.release();
                //2.恢复声音
                unMute();
            } else if (msg.what == MSG_LAUNCH_APP) {
                //打开APP
                temporary_app = true;
                Utils.launchApp(getApplicationContext(), (JSONObject) msg.obj);
            } else if (msg.what == MSG_LAUNCH_MDM) {
                temporary_app = false;
                if (isAttendClass) {

                    Intent in = getPackageManager().getLaunchIntentForPackage(ZHIHUIKETANGPG);
                    if (in != null) {
                        in.putExtra("shangke", "open");
                        in.putExtra("studentName", getSharedPreferences("kiway", 0).getString("name", ""));
                        in.putExtra("className", getSharedPreferences("kiway", 0).getString("className", ""));
                        in.putExtra("studentNumber", getSharedPreferences("kiway", 0).getString("studentNumber", ""));
                        in.putExtra("classId", getSharedPreferences("kiway", 0).getString("classId", ""));
                        in.putExtra("schoolId", getSharedPreferences("kiway", 0).getString("schoolId", ""));
                        in.putExtra("huaweiToken", getSharedPreferences("huawei", 0).getString("token", ""));
                        //RemoteAidlService.attendClass(msg.obj.toString());
                        Utils.startPackage(KWApp.instance, ZHIHUIKETANGPG, in);
                    }
                } else {
                    //返回MDM桌面
                    Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                    startActivity(intent);
                }
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
            } else if (msg.what == MSG_PORTRAIT) {
                getSharedPreferences("kiway", 0).edit().putInt("oriantation", 0).commit();
                if (currentActivity != null && currentActivity instanceof BaseActivity) {
                    ((BaseActivity) currentActivity).setScreenOrientation();
                }
            } else if (msg.what == MSG_LANDSCAPE) {
                getSharedPreferences("kiway", 0).edit().putInt("oriantation", 1).commit();
                if (currentActivity != null && currentActivity instanceof BaseActivity) {
                    ((BaseActivity) currentActivity).setScreenOrientation();
                }
            } else if (msg.what == MSG_UNINSTALL) {
                //卸载某个包
                MDMHelper.getAdapter().uninstallPackage(msg.obj.toString());
            } else if (msg.what == MSG_PARENT_BIND) {
                //绑定
                if (KWApp.instance != null) {
                    Utils.showBindDialog(KWApp.instance.currentActivity, (JSONObject) msg.obj);
                }
            } else if (msg.what == MSG_SMS) {
                if (KWApp.instance != null) {
                    Utils.showSMSDialog(KWApp.instance.currentActivity, (SmsMessage) msg.obj);
                }
            } else if (msg.what == MSG_ATTEND_CALSS) {
                if (KWApp.instance != null) {
                    if (!isAppInstalled(KWApp.instance, ZHIHUIKETANGPG)) {
                        Toast.makeText(KWApp.instance, "请安装课堂互动", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    isAttendClass = true;
                    Intent in = getPackageManager().getLaunchIntentForPackage(ZHIHUIKETANGPG);
                    if (in != null) {
                        in.putExtra("shangke", msg.obj.toString());
                        in.putExtra("studentName", getSharedPreferences("kiway", 0).getString("name", ""));
                        in.putExtra("className", getSharedPreferences("kiway", 0).getString("className", ""));
                        in.putExtra("studentNumber", getSharedPreferences("kiway", 0).getString("studentNumber", ""));
                        in.putExtra("classId", getSharedPreferences("kiway", 0).getString("classId", ""));
                        in.putExtra("schoolId", getSharedPreferences("kiway", 0).getString("schoolId", ""));
                        in.putExtra("huaweiToken", getSharedPreferences("huawei", 0).getString("token", ""));
                        RemoteAidlService.attendClass(msg.obj.toString());
                        Utils.startPackage(KWApp.instance, ZHIHUIKETANGPG, in);
                    }
                }
            } else if (msg.what == MSG_GET_OUT_OF_CALASS) {
                RemoteAidlService.goOutClass();
                isAttendClass = false;
            } else if (msg.what == MSG_MESSAGE) {
                RemoteAidlService.accpterMessage(currentActivity, msg.obj.toString());
            } else if (msg.what == MSG_PUSH_FILE_I) {
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //CrashHandler.getInstance().init(this);
        huaweiPush(this);
        //xutils
        x.Ext.init(this);
    }

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
                    final String folder = "/mnt/sdcard/kiway_mdm_student/file/";
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

    public void excuteFlagCommand() {
        int flag_camera = getSharedPreferences("kiway", 0).getInt("flag_camera", 1);
        //这个没有对应的MDM接口，需要代码控制

        int flag_snapshot = getSharedPreferences("kiway", 0).getInt("flag_snapshot", 1);
        MDMHelper.getAdapter().setScreenCaptureDisabled(flag_snapshot == 0);

        int flag_location = getSharedPreferences("kiway", 0).getInt("flag_location", 1);
        MDMHelper.getAdapter().setNetworkLocationDisabled(flag_location == 0);
        MDMHelper.getAdapter().setGPSDisabled(flag_location == 0);

        int flag_dataconnectivity = getSharedPreferences("kiway", 0).getInt("flag_dataconnectivity", 1);
        MDMHelper.getAdapter().setDataConnectivityDisabled(flag_dataconnectivity == 0);

        int flag_microphone = getSharedPreferences("kiway", 0).getInt("flag_microphone", 1);
        MDMHelper.getAdapter().setMicrophoneDisabled(flag_microphone == 0);

        int flag_restore = getSharedPreferences("kiway", 0).getInt("flag_restore", 1);
        MDMHelper.getAdapter().setRestoreFactoryDisabled(flag_restore == 0);

        int flag_ap = getSharedPreferences("kiway", 0).getInt("flag_ap", 1);
        MDMHelper.getAdapter().setWifiApDisabled(flag_ap == 0);

        int flag_app_open = getSharedPreferences("kiway", 0).getInt("flag_app_open", 1);
        //这个没有对应的MDM接口，需要代码控制

        int flag_usb = getSharedPreferences("kiway", 0).getInt("flag_usb", 1);
        //太危险了
        //MDMHelper.getAdapter().setUSBDataDisabled(flag_usb == 0);

        int flag_wifi = getSharedPreferences("kiway", 0).getInt("flag_allowWifi", 1);
        //太危险了
        //MDMHelper.getAdapter().setWifiDisabled(flag_wifi == 0);

        int flag_systemupdate = getSharedPreferences("kiway", 0).getInt("flag_systemupdate", 1);
        MDMHelper.getAdapter().setSystemUpdateDisabled(flag_systemupdate == 0);

        int flag_bluetooth = getSharedPreferences("kiway", 0).getInt("flag_bluetooth", 1);
        MDMHelper.getAdapter().setBluetoothDisabled(flag_bluetooth == 0);
    }

    private int lastVolume = 0;

    public void mute() {
        Log.d("test", "RINGING 已被静音");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lastVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
    }

    public void unMute() {
        Log.d("test", "RINGING 取消静音");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lastVolume, 0);
    }
}
