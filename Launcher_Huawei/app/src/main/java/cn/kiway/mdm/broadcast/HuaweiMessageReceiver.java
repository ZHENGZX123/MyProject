package cn.kiway.mdm.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.huawei.android.pushagent.api.PushEventReceiver;

import org.json.JSONObject;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.MSG_FLAGCOMMAND;
import static cn.kiway.mdm.KWApp.MSG_INSTALL;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_APP;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_MDM;
import static cn.kiway.mdm.KWApp.MSG_LOCK;
import static cn.kiway.mdm.KWApp.MSG_REBOOT;
import static cn.kiway.mdm.KWApp.MSG_SHUTDOWN;
import static cn.kiway.mdm.KWApp.MSG_TOAST;
import static cn.kiway.mdm.KWApp.MSG_UNLOCK;

/*
 * 接收Push所有消息的广播接收器
 */
public class HuaweiMessageReceiver extends PushEventReceiver {

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Log.d("huawei", content);

        context.getSharedPreferences("kiway", 0).edit().putString("token", token).commit();

        //注册一下
        if (KWApp.instance != null) {
            Message m = new Message();
            m.what = MSG_INSTALL;
            KWApp.instance.mHandler.sendMessage(m);
        }
    }

    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String receive = new String(msg, "UTF-8");
            Log.d("huawei", "onPushMsg " + receive);

            JSONObject data = new JSONObject(receive).getJSONObject("data");
            String command = data.optString("command");
            int flag = data.optInt("flag");

            Message m = new Message();
            if (KWApp.flagCommands.contains(command)) {
                context.getSharedPreferences("kiway", 0).edit().putInt("flag_" + command, flag).commit();
                m.what = MSG_FLAGCOMMAND;
            } else if (command.equals("reboot")) {
                m.what = MSG_REBOOT;
            } else if (command.equals("shutdown")) {
                m.what = MSG_SHUTDOWN;
            } else if (command.equals("shangke")) {
                m.what = MSG_LAUNCH_APP;
                m.obj = "cn.kiway.homework.student";
            } else if (command.equals("xiake")) {
                m.what = MSG_LAUNCH_MDM;
            } else if (command.equals("lock")) {
                m.what = MSG_LOCK;
            } else if (command.equals("unlock")) {
                m.what = MSG_UNLOCK;
            } else if (command.equals("wifi")) {
                //保存进数据库，并马上执行一次
                Utils.checkWifis(MainActivity.instance);
            } else if (command.equals("app")) {
                //保存进数据库，并马上执行一次checkAppCharges
                Utils.checkAppCharges(MainActivity.instance);
            } else if (command.equals("network")) {
                //保存进数据库即可
            } else {
                m.what = MSG_TOAST;
                m.obj = receive;
            }
            if (KWApp.instance == null) {
                return false;
            }
            KWApp.instance.mHandler.sendMessage(m);
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


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
}
