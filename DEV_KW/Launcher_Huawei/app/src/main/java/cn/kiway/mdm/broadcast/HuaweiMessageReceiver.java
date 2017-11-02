package cn.kiway.mdm.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.huawei.android.pushagent.api.PushEventReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.utils.MyDBHelper;

import static cn.kiway.mdm.KWApp.MSG_FLAGCOMMAND;
import static cn.kiway.mdm.KWApp.MSG_INSTALL;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_APP;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_MDM;
import static cn.kiway.mdm.KWApp.MSG_LOCK;
import static cn.kiway.mdm.KWApp.MSG_PUSH_FILE;
import static cn.kiway.mdm.KWApp.MSG_REBOOT;
import static cn.kiway.mdm.KWApp.MSG_SHUTDOWN;
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
            Log.d("huawei", "onPushMsg = " + receive);

            String dataStr = new JSONObject(receive).getString("data");
            JSONObject data = new JSONObject(dataStr);
            String command = data.optString("command");

            Message m = new Message();
            if (command.equals("allowAppFunction")) {
                JSONArray content = data.getJSONArray("content");
                int count = content.length();
                for (int i = 0; i < count; i++) {
                    JSONObject o = content.getJSONObject(i);
                    String commandT = o.optString("command");
                    int flag = data.optInt("flag");
                    context.getSharedPreferences("kiway", 0).edit().putInt("flag_" + commandT, flag).commit();
                }
                m.what = MSG_FLAGCOMMAND;
            } else if (command.equals("reboot")) {
                m.what = MSG_REBOOT;
            } else if (command.equals("shutdown")) {
                m.what = MSG_SHUTDOWN;
            } else if (command.equals("temporary_app")) {
                m.what = MSG_LAUNCH_APP;
                JSONArray content = data.optJSONArray("content");
                String packageName = content.getJSONObject(0).getString("packages");
                m.obj = packageName;
            } else if (command.equals("temporary_app_uncharge")) {
                m.what = MSG_LAUNCH_MDM;
            } else if (command.equals("temporary_lockScreen")) {
                m.what = MSG_LOCK;
            } else if (command.equals("temporary_unlockScreen")) {
                m.what = MSG_UNLOCK;
            } else if (command.equals("wifi")) {
                //TODO 保存进数据库，并马上执行一次
                //注意这里是单条的操作

            } else if (command.equals("app")) {
                //TODO 注意这里是单条的操作
                //保存进数据库，并马上执行一次checkAppCharges

            } else if (command.equals("network")) {
                //TODO 保存进数据库即可
                //注意这里是单条的操作
                //{"data":"{\"command\":\"network\",\"content\":[{\"type\":1,\"operation\":\"save\",\"url\":\"www.123.com\"}]}"}
                JSONArray content = data.optJSONArray("content");
                String operation = content.getJSONObject(0).optString("operation");
                String url = content.getJSONObject(0).optString("url");
                int type = content.getJSONObject(0).optInt("type");
                String id = content.getJSONObject(0).optString("id");
                Network a = new Network();
                a.url = url;
                a.type = type;
                if (operation.equals("save")) {
                    new MyDBHelper(context).addNetwork(a);
                } else if (operation.equals("update")) {
                    new MyDBHelper(context).updateNetwork(a);
                } else if (operation.equals("delete")) {
                    new MyDBHelper(context).deleteNetwork(id);
                }
            } else if (command.equals("file_push")) {
                JSONObject content = data.getJSONObject("content");
                m.what = MSG_PUSH_FILE;
                m.obj = content.toString();
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


}
