package cn.kiway.mdm.hprose.socket.actions;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.hprose.screen.FxService;
import cn.kiway.mdm.hprose.socket.KwHproseClient;
import cn.kiway.mdmsdk.MDMHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.MSG_LOCK;
import static cn.kiway.mdm.KWApp.MSG_PUSH_FILE_I;
import static cn.kiway.mdm.KWApp.MSG_REBOOT;
import static cn.kiway.mdm.KWApp.MSG_SHUTDOWN;
import static cn.kiway.mdm.KWApp.MSG_UNLOCK;
import static cn.kiway.mdm.hprose.socket.MessageType.ANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.LOCKSCREEN;
import static cn.kiway.mdm.hprose.socket.MessageType.OFFSCREENSHARE;
import static cn.kiway.mdm.hprose.socket.MessageType.OFF_MOBLIE;
import static cn.kiway.mdm.hprose.socket.MessageType.REON_MOBLIE;
import static cn.kiway.mdm.hprose.socket.MessageType.SCREEN;
import static cn.kiway.mdm.hprose.socket.MessageType.SCREENSHARE;
import static cn.kiway.mdm.hprose.socket.MessageType.SHARE_FILE;
import static cn.kiway.mdm.hprose.socket.MessageType.SIGN;
import static cn.kiway.mdm.hprose.socket.MessageType.SOLUTIONSCREE;
import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;
import static cn.kiway.mdm.hprose.socket.MessageType.UNANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_4G;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_ADDITIONAL_FUNCTION;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_BULEBOOH;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_CAMARE;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_MICROPHONE;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_SOUND;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_SYSTEM_UPDATE;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_WIFI;
import static cn.kiway.mdm.hprose.socket.MessageType.USE_WIFI_HOTSPOT;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ActionsMessageHandle {
    static long time;

    public static void MessageHandle(Context context, String s) {
        try {
            if (System.currentTimeMillis() - time < 2000)
                return;
            time = System.currentTimeMillis();
            JSONObject data = new JSONObject(s);
            int msgType = data.optInt("msgType");
            if (msgType == SIGN) {//签到
                if (KWApp.instance.currentActivity != null)
                    ((BaseActivity) KWApp.instance.currentActivity).Session(SIGN);
            } else if (msgType == ANSWER) {//抢答
                if (KWApp.instance.currentActivity != null)
                    ((BaseActivity) KWApp.instance.currentActivity).Session(ANSWER);
            } else if (msgType == UNANSWER) {//抢答结束
                if (KWApp.instance.currentActivity != null)
                    ((BaseActivity) KWApp.instance.currentActivity).Session(UNANSWER);
            } else if (msgType == LOCKSCREEN) {//锁屏
                KWApp.instance.mHandler.sendEmptyMessage(MSG_LOCK);
                JSONObject da = new JSONObject();
                da.put("userId", Utils.getIMEI(context));
                da.put("msg", "1");
                if (KWApp.instance.isIos) {
                    if (KWApp.instance.client==null)
                        return;
                    KWApp.instance.client.sendTCP(da.toString());
                } else {
                    if (KwHproseClient.helloClient != null)
                        KwHproseClient.helloClient.sign(da.toString());
                }
            } else if (msgType == SOLUTIONSCREE) {//解屏
                context.getSharedPreferences("kiway", 0).edit().putLong("lock_time", 0).commit();
                KWApp.instance.mHandler.sendEmptyMessage(MSG_UNLOCK);
                JSONObject da = new JSONObject();
                da.put("userId", Utils.getIMEI(context));
                da.put("msg", "0");
                if (KWApp.instance.isIos) {
                    if (KWApp.instance.client==null)
                        return;
                    KWApp.instance.client.sendTCP(da.toString());
                } else {
                    if (KwHproseClient.helloClient != null)
                        KwHproseClient.helloClient.sign(da.toString());
                }
            } else if (msgType == SCREENSHARE) {//打开屏幕共享
                FxService.setIp(data.optString("msg"));
                ((MainActivity) context).startScreen();
            } else if (msgType == OFFSCREENSHARE) {//关闭屏幕共享
                ((MainActivity) context).stopScreen();
            } else if (msgType == SUREREPONSE) {//确实是否听懂，是与否在 msg中体现，1听懂，0听不懂
                if (KWApp.instance.currentActivity != null)
                    ((BaseActivity) KWApp.instance.currentActivity).Session(SUREREPONSE);
            } else if (msgType == OFF_MOBLIE) {//关机
                KWApp.instance.mHandler.sendEmptyMessage(MSG_SHUTDOWN);
            } else if (msgType == REON_MOBLIE) {//重启
                KWApp.instance.mHandler.sendEmptyMessage(MSG_REBOOT);
            } else if (msgType == USE_CAMARE) {//使用相机
                context.getSharedPreferences("kiway", 0).edit().putString("flag_camera", data.optString("msg"));
                if (data.optInt("msg") == 0) {
                    ((MainActivity) context).findViewById(R.id.button7).setVisibility(View.GONE);
                } else {
                    ((MainActivity) context).findViewById(R.id.button7).setVisibility(View.VISIBLE);
                }
            } else if (msgType == USE_WIFI) {//使用wifi
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_wifi", data.optInt("msg"));
                // TODO MDMHelper.getAdapter().setWifiDisabled(flag_wifi == 0);
            } else if (msgType == USE_4G) {//使用移动网咯
                //TODO 暂时没有接口
            } else if (msgType == USE_BULEBOOH) {//使用蓝牙
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_bluetooth", data.optInt("msg"));
                MDMHelper.getAdapter().setBluetoothDisabled(data.optInt("msg") == 0);
            } else if (msgType == USE_MICROPHONE) {//使用麦克风
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_microphone", data.optInt("msg"));
                MDMHelper.getAdapter().setMicrophoneDisabled(data.optInt("msg") == 0);
            } else if (msgType == USE_SOUND) {//使用声音
                //TODO 暂时没有接口
            } else if (msgType == USE_SYSTEM_UPDATE) {//允许系统更新
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_systemupdate", data.optInt("msg"));
                MDMHelper.getAdapter().setMicrophoneDisabled(data.optInt("msg") == 0);
            } else if (msgType == USE_WIFI_HOTSPOT) {//使用便携式热点
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_systemupdate", data.optInt("msg"));
                MDMHelper.getAdapter().setWifiApDisabled(data.optInt("msg") == 0);
            } else if (msgType == USE_ADDITIONAL_FUNCTION) {//使用辅助功能
                if (data.optInt("msg") == 0) {
                } else {
                }
            } else if (msgType == SCREEN) {//横屏
                if (data.optInt("msg") == 0) {
                    ((MainActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    ((MainActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            } else if (msgType == SHARE_FILE) {//下载文件
                Message msg1 = new Message();
                msg1.what = MSG_PUSH_FILE_I;
                msg1.obj = s;
                KWApp.instance.mHandler.sendMessage(msg1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
