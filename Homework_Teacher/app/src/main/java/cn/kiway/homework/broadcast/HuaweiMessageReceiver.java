package cn.kiway.homework.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.huawei.android.pushagent.api.PushEventReceiver;

import cn.kiway.homework.activity.MainActivity;
import cn.kiway.homework.util.MyDBHelper;

/*
 * 接收Push所有消息的广播接收器
 */
public class HuaweiMessageReceiver extends PushEventReceiver {


    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Log.d("huawei", content);

        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        Log.d("huawei", "imei = " + tm.getDeviceId());

        context.getSharedPreferences("kiway", 0).edit().putString("huaweitoken", token).commit();
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = "收到一条Push消息： " + new String(msg, "UTF-8");
            Log.d("huawei", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onEvent(Context context, Event event, Bundle extras) {
        Log.d("huawei", "event = " + event);
        if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = "收到通知附加消息： " + extras.getString(BOUND_KEY.pushMsgKey);
            Log.d("huawei", content);
            Log.d("test", "存了一个event");
            context.getSharedPreferences("kiway", 0).edit().putString("event", content.replace("[", "").replace("]", "")).commit();

            //没有接收消息的event，只好暂时放这里了 TODO
            new MyDBHelper(context).deleteHttpCache("getTeacherInMsg");

            //华为必须重新打开页面，因为event竟然比onresume还慢
            Intent i = new Intent(context, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } else if (Event.PLUGINRSP.equals(event)) {
            final int TYPE_LBS = 1;
            final int TYPE_TAG = 2;
            int reportType = extras.getInt(BOUND_KEY.PLUGINREPORTTYPE, -1);
            boolean isSuccess = extras.getBoolean(BOUND_KEY.PLUGINREPORTRESULT, false);
            String message = "";
            if (TYPE_LBS == reportType) {
                message = "LBS report result :";
            } else if (TYPE_TAG == reportType) {
                message = "TAG report result :";
            }
            Log.d("huawei", message + isSuccess);
        }
        super.onEvent(context, event, extras);
    }
}
