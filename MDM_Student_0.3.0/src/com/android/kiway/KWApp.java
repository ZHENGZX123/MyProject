package com.android.kiway;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.kiway.activity.BaseActivity;
import com.android.kiway.aidlservice.RemoteAidlService;
import com.android.kiway.utils.HttpUtil;
import com.android.kiway.utils.Utils;
import com.android.kiway.windows.LockSreenService;
import com.android.kiway.zbus.ZbusHost;

import org.json.JSONObject;
import org.xutils.x;

import cn.kiway.mdmsdk.MDMHelper;
import cn.kiway.wx.reply.utils.RabbitMQUtils;

import static com.android.kiway.utils.AppListUtils.isAppInstalled;
import static com.android.kiway.utils.Constant.ZHIHUIKETANGPG;
import static com.android.kiway.utils.Utils.huaweiPush;

/**
 * Created by Administrator on 2017/6/9.
 */

public class KWApp extends Application {

    public static final int MSG_TOAST = 0;//Toast
    public static final int MSG_INSTALL = 1;//注册华为
    public static final int MSG_LOCK = 2;//锁屏
    public static final int MSG_LOCKONCLASS = -2;//上课锁屏
    public static final int MSG_UNLOCK = 3;//解锁
    public static final int MSG_LAUNCH_APP = 4;//打开某个APP
    public static final int MSG_LAUNCH_MDM = 5;//打开MDM
    public static final int MSG_FLAGCOMMAND = 6;//flag类型的命令
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

    public static final int MSG_MUTE = 20;//静音
    public static final int MSG_UNMUTE = 21;//解除禁音


    public static KWApp instance;
    public Activity currentActivity;
    public static boolean temporary_app = false;
    public static boolean lockscreen = false;
    public static boolean muted = false;

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
                //zzx add
                lockscreen = true;
                Intent intent = new Intent(KWApp.this, LockSreenService.class);
                startService(intent);
                MDMHelper.getAdapter().setBackButtonDisabled(true);
                MDMHelper.getAdapter().setHomeButtonDisabled(true);
            } else if (msg.what == MSG_LOCKONCLASS) {
                //0.锁屏
                MDMHelper.getAdapter().setBackButtonDisabled(true);
                MDMHelper.getAdapter().setHomeButtonDisabled(true);
            } else if (msg.what == MSG_UNLOCK) {
                //zzx add
                lockscreen = false;
                Intent intent = new Intent(KWApp.this, LockSreenService.class);
                stopService(intent);
                MDMHelper.getAdapter().setBackButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
            } else if (msg.what == MSG_MUTE) {
                muted = true;
                mute();
            } else if (msg.what == MSG_UNMUTE) {
                muted = false;
                unMute();
            } else if (msg.what == MSG_LAUNCH_APP) {
                if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
                    Log.d("test", "bug#1598解锁状态下，不接收管控命令");
                    return;
                }
                //打开APP
                temporary_app = true;
                Utils.launchApp(getApplicationContext(), (JSONObject) msg.obj);
            } else if (msg.what == MSG_LAUNCH_MDM) {
                if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
                    Log.d("test", "bug#1598解锁状态下，不接收管控命令");
                    return;
                }
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
            } else if (msg.what == MSG_PORTRAIT) {
                if (Build.MODEL.equals("BZK-W00") && currentActivity != null && currentActivity instanceof
                        BaseActivity) {
                    ((BaseActivity) currentActivity).toast("该型号手机暂不支持横竖屏操作");
                    return;
                }
                if (lockscreen && currentActivity != null && currentActivity instanceof BaseActivity) {
                    ((BaseActivity) currentActivity).toast("无法进行竖屏操作，请先解除锁屏状态");
                    return;
                }
                if (getSharedPreferences("kiway", 0).getInt("oriantation", 0) == 0) {
                    return;
                }
                getSharedPreferences("kiway", 0).edit().putInt("oriantation", 0).commit();
                if (currentActivity != null && currentActivity instanceof BaseActivity) {
                    ((BaseActivity) currentActivity).setScreenOrientation();
                }
            } else if (msg.what == MSG_LANDSCAPE) {
                if (Build.MODEL.equals("BZK-W00") && currentActivity != null && currentActivity instanceof
                        BaseActivity) {
                    ((BaseActivity) currentActivity).toast("该型号手机暂不支持横竖屏操作");
                    return;
                }
                if (lockscreen && currentActivity != null && currentActivity instanceof BaseActivity) {
                    ((BaseActivity) currentActivity).toast("无法进行竖屏操作，请先解除锁屏状态");
                    return;
                }
                if (getSharedPreferences("kiway", 0).getInt("oriantation", 0) == 1) {
                    return;
                }
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
                    //3.唤醒屏幕
                    // 获取电源管理器对象
                    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                    boolean screenOn = pm.isScreenOn();
                    if (!screenOn) {
                        // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
                        PowerManager.WakeLock wl = pm.newWakeLock(
                                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                        PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
                        wl.acquire(10000); // 点亮屏幕
                        wl.release(); // 释放
                    }
                    // 屏幕解锁
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                    KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
                    // 屏幕锁定
                    keyguardLock.reenableKeyguard();
                    keyguardLock.disableKeyguard(); // 解锁
                    MDMHelper.getAdapter().setTaskButtonDisabled(true);
                    MDMHelper.getAdapter().setHomeButtonDisabled(true);
                    Intent in = getBaseContext().getPackageManager().getLaunchIntentForPackage(ZHIHUIKETANGPG);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //1.打开APP
                    in.putExtra("shangke", msg.obj.toString());
                    in.putExtra("studentName", getSharedPreferences("kiway", 0).getString("name", ""));
                    in.putExtra("className", getSharedPreferences("kiway", 0).getString("className", ""));
                    in.putExtra("studentNumber", getSharedPreferences("kiway", 0).getString("studentNumber", ""));
                    in.putExtra("classId", getSharedPreferences("kiway", 0).getString("classId", ""));
                    in.putExtra("schoolId", getSharedPreferences("kiway", 0).getString("schoolId", ""));
                    in.putExtra("huaweiToken", getSharedPreferences("huawei", 0).getString("token", ""));
                    RemoteAidlService.attendClass(msg.obj.toString());
                    startActivity(in);
                    //2.发送zbus命令
                    //返回shagnke给教师端，当作online
                    ZbusHost.doSendMsg(KWApp.instance, "shangke");
                }
            } else if (msg.what == MSG_GET_OUT_OF_CALASS) {
                RemoteAidlService.goOutClass();
                isAttendClass = false;
                //MDMHelper.getAdapter().setTaskButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
            } else if (msg.what == MSG_MESSAGE) {
                RemoteAidlService.accpterMessage(currentActivity, msg.obj.toString());
            } else if (msg.what == MSG_PUSH_FILE_I) {
            }
        }
    };

    public static RabbitMQUtils consumeUtil;
    public static RabbitMQUtils sendUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //crash
        //CrashHandler.getInstance().init(this);
        //huawei
        huaweiPush(this);
        //xutils
        x.Ext.init(this);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Log.d("test", "addShutdownHook");
                closeMQ();
            }
        });
    }

    public static void closeMQ() {
        if (consumeUtil != null) {
            consumeUtil.close();
        }
        if (sendUtil != null) {
            sendUtil.close();
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
        Utils.muteCheck(getApplicationContext());
    }

    public void unMute() {
        Log.d("test", "RINGING 取消静音");
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lastVolume, 0);
    }
}
