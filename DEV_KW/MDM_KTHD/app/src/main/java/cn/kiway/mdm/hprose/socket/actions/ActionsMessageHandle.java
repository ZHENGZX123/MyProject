package cn.kiway.mdm.hprose.socket.actions;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.RemoteException;

import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.mdm.App;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.hprose.socket.KwHproseClient;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.App.MSG_LOCKONCLASS;
import static cn.kiway.mdm.App.MSG_UNLOCK;
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
        Logger.log("Tcp::::::::"+s);
        try {
            if (System.currentTimeMillis() - time < 2000)
                return;
            time = System.currentTimeMillis();
            JSONObject data = new JSONObject(s);
            int msgType = data.optInt("msgType");
            if (msgType == SIGN) {//签到
                if (App.instance.currentActivity != null)
                    ((BaseActivity) App.instance.currentActivity).Session(SIGN);
            } else if (msgType == ANSWER) {//抢答
                if (App.instance.currentActivity != null)
                    ((BaseActivity) App.instance.currentActivity).Session(SIGN);
            } else if (msgType == UNANSWER) {//抢答结束
                if (App.instance.currentActivity != null)
                    ((BaseActivity) App.instance.currentActivity).Session(SIGN);
            } else if (msgType == LOCKSCREEN) {//锁屏
                try {
                    if (App.instance.mRemoteInterface != null)
                        App.instance.mRemoteInterface.lockScreen(true);
                    App.instance.mHandler.sendEmptyMessage(MSG_LOCKONCLASS);
                    JSONObject da = new JSONObject();
                    da.put("userId", Utils.getIMEI(context));
                    da.put("msg", "1");
                    if (App.instance.isIos) {
                        if (App.instance.client == null)
                            return;
                        App.instance.client.sendTCP(da.toString());
                    } else {
                        if (KwHproseClient.helloClient != null)
                            KwHproseClient.helloClient.sign(da.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msgType == SOLUTIONSCREE) {//解屏
                try {
                    if (App.instance.mRemoteInterface != null)
                        App.instance.mRemoteInterface.lockScreen(false);
                    App.instance.mHandler.sendEmptyMessage(MSG_UNLOCK);
                    JSONObject da = new JSONObject();
                    da.put("userId", Utils.getIMEI(context));
                    da.put("msg", "0");
                    if (App.instance.isIos) {
                        if (App.instance.client == null)
                            return;
                        App.instance.client.sendTCP(da.toString());
                    } else {
                        if (KwHproseClient.helloClient != null)
                            KwHproseClient.helloClient.sign(da.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msgType == SCREENSHARE) {//打开屏幕共享
                try {
                    if (App.instance.mRemoteInterface != null)
                        App.instance.mRemoteInterface.shareScreen(true, data.optString("msg"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msgType == OFFSCREENSHARE) {//关闭屏幕共享
                try {
                    if (App.instance.mRemoteInterface != null)
                        App.instance.mRemoteInterface.shareScreen(false, "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msgType == SUREREPONSE) {//确实是否听懂，是与否在 msg中体现，1听懂，0听不懂
                if (App.instance.currentActivity != null)
                    ((BaseActivity) App.instance.currentActivity).Session(SUREREPONSE);
            } else if (msgType == OFF_MOBLIE) {//关机
            } else if (msgType == REON_MOBLIE) {//重启
            } else if (msgType == USE_CAMARE) {//使用相机
            } else if (msgType == USE_WIFI) {//使用wifi
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_wifi", data.optInt("msg"));
                // TODO MDMHelper.getAdapter().setWifiDisabled(flag_wifi == 0);
            } else if (msgType == USE_4G) {//使用移动网咯
                //TODO 暂时没有接口
            } else if (msgType == USE_BULEBOOH) {//使用蓝牙
            } else if (msgType == USE_MICROPHONE) {//使用麦克风
            } else if (msgType == USE_SOUND) {//使用声音
                //TODO 暂时没有接口
            } else if (msgType == USE_SYSTEM_UPDATE) {//允许系统更新
            } else if (msgType == USE_WIFI_HOTSPOT) {//使用便携式热点
            } else if (msgType == USE_ADDITIONAL_FUNCTION) {//使用辅助功能
                if (data.optInt("msg") == 0) {
                } else {
                }
            } else if (msgType == SCREEN) {//横屏
                if (data.optInt("msg") == 0) {
                    ((BaseActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    ((BaseActivity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            } else if (msgType == SHARE_FILE) {//下载文件
//                Message msg1 = new Message();
//                msg1.what = MSG_PUSH_FILE_I;
//                msg1.obj = s;
//                KWApp.instance.mHandler.sendMessage(msg1);
                if (App.instance.currentActivity != null)
                    ((BaseActivity) App.instance.currentActivity).downloadFile(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
