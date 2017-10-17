package cn.kiway.mdm.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.huawei.android.pushagent.api.PushEventReceiver;

import cn.kiway.mdm.utils.Utils;

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

    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String receive = new String(msg, "UTF-8");
            Log.d("huawei", "onPushMsg " + receive);
            //解析发送过来的命令，执行对应的操作
            //1.wifi电子围栏
            Utils.connectSSID(context, "KIWAY2", "KWF58888");
            //2.APP时间分段
            //3.网页打开黑名单
//            MDMHelper.getAdapter().addNetworkAccessBlackList(null);


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
            Log.d("huawei", "content = " + content);
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
