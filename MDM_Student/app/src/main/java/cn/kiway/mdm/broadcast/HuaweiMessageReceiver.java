package cn.kiway.mdm.broadcast;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.huawei.android.pushagent.api.PushEventReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.entity.AppCharge;
import cn.kiway.mdm.entity.Call;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.entity.Wifi;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.MSG_PARENT_BIND;
import static cn.kiway.mdm.KWApp.MSG_FLAGCOMMAND;
import static cn.kiway.mdm.KWApp.MSG_INSTALL;
import static cn.kiway.mdm.KWApp.MSG_LANDSCAPE;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_APP;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_MDM;
import static cn.kiway.mdm.KWApp.MSG_LOCK;
import static cn.kiway.mdm.KWApp.MSG_PORTRAIT;
import static cn.kiway.mdm.KWApp.MSG_PUSH_FILE;
import static cn.kiway.mdm.KWApp.MSG_REBOOT;
import static cn.kiway.mdm.KWApp.MSG_SHUTDOWN;
import static cn.kiway.mdm.KWApp.MSG_TOAST;
import static cn.kiway.mdm.KWApp.MSG_UNINSTALL;
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

        context.getSharedPreferences("huawei", 0).edit().putString("token", token).commit();

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

            //如果没有登录，不接收推送
            if (!context.getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                Log.d("huawei", "没有登录，不接受推送");
                return false;
            }

            String dataStr = new JSONObject(receive).getString("data");
            JSONObject data = new JSONObject(dataStr);
            String command = data.optString("command");

            Message m = new Message();
            if (command.equals("allowAppFunction")) {
                //重置所有的值为0
                Utils.resetFunctions(context);
                JSONArray content = data.getJSONArray("content");
                int count = content.length();
                for (int i = 0; i < count; i++) {
                    JSONObject o = content.getJSONObject(i);
                    String commandT = o.optString("command");
                    int flag = o.optInt("flag");
                    context.getSharedPreferences("kiway", 0).edit().putInt("flag_" + commandT, flag).commit();
                }
                m.what = MSG_FLAGCOMMAND;
            } else if (command.equals("reboot")) {
                m.what = MSG_REBOOT;
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
            } else if (command.equals("shutdown")) {
                m.what = MSG_SHUTDOWN;
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
                //TODO 判断有没有开始时间-结束时间
            } else if (command.equals("temporary_app")) {
                String currentTime = data.optString("currentTime");
                context.getSharedPreferences("kiway", 0).edit().putLong("app_time", Utils.dateToLong(currentTime)).commit();
                context.getSharedPreferences("kiway", 0).edit().putString("app_data", data.toString()).commit();
                m.what = MSG_LAUNCH_APP;
                m.obj = data;
            } else if (command.equals("temporary_app_uncharge")) {
                m.what = MSG_LAUNCH_MDM;
                context.getSharedPreferences("kiway", 0).edit().putLong("app_time", 0).commit();
                context.getSharedPreferences("kiway", 0).edit().putString("app_data", "").commit();
            } else if (command.equals("temporary_lockScreen")) {
                m.what = MSG_LOCK;
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
                context.getSharedPreferences("kiway", 0).edit().putLong("lock_time", Utils.dateToLong(currentTime)).commit();
            } else if (command.equals("temporary_unlockScreen")) {
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
                context.getSharedPreferences("kiway", 0).edit().putLong("lock_time", 0).commit();
                m.what = MSG_UNLOCK;
            } else if (command.equals("wifi")) {
                JSONArray content = data.optJSONArray("content");
                ArrayList<Wifi> wifis = new GsonBuilder().create().fromJson(content.toString(), new TypeToken<List<Wifi>>() {
                }.getType());
                Wifi wifi = wifis.get(0);
                if (wifi.operation.equals("save")) {
                    new MyDBHelper(context).addWifi(wifi);
                } else if (wifi.operation.equals("update")) {
                    new MyDBHelper(context).updateWifi(wifi);
                } else if (wifi.operation.equals("delete")) {
                    new MyDBHelper(context).deleteWifi(wifi.id);
                }
                Utils.checkWifis(MainActivity.instance);
            } else if (command.equals("app")) {
                //保存进数据库，并马上执行一次checkAppCharges
                JSONArray content = data.optJSONArray("content");
                ArrayList<AppCharge> apps = new GsonBuilder().create().fromJson(content.toString(), new TypeToken<List<AppCharge>>() {
                }.getType());
                AppCharge app = apps.get(0);
                if (app.operation.equals("save")) {
                    new MyDBHelper(context).addAppcharge(app);
                } else if (app.operation.equals("update")) {
                    new MyDBHelper(context).updateAppCharges(app);
                } else if (app.operation.equals("delete")) {
                    new MyDBHelper(context).deleteAppcharge(app.id);
                    //TODO 如果type=0||type=1要卸载应用
                }
                Utils.checkAppCharges(MainActivity.instance);
            } else if (command.equals("network")) {
                JSONArray content = data.optJSONArray("content");
                ArrayList<Network> networks = new GsonBuilder().create().fromJson(content.toString(), new TypeToken<List<Network>>() {
                }.getType());
                Network a = networks.get(0);
                if (a.operation.equals("save")) {
                    new MyDBHelper(context).addNetwork(a);
                } else if (a.operation.equals("update")) {
                    new MyDBHelper(context).updateNetwork(a);
                } else if (a.operation.equals("delete")) {
                    new MyDBHelper(context).deleteNetwork(a.id);
                }
            } else if (command.equals("network_black_white")) {
                int enable_network_type = data.getJSONObject("content").getInt("type");
                Log.d("test", "enable_network_type = " + enable_network_type);
                ArrayList<Network> networks1 = new MyDBHelper(context).getAllNetworks(1);
                ArrayList<Network> networks2 = new MyDBHelper(context).getAllNetworks(2);
                if (enable_network_type == 1) {
                    for (Network n : networks1) {
                        n.enable = 1;
                        new MyDBHelper(context).updateNetwork(n);
                    }
                    for (Network n : networks2) {
                        n.enable = 0;
                        new MyDBHelper(context).updateNetwork(n);
                    }
                } else if (enable_network_type == 2) {
                    for (Network n : networks1) {
                        n.enable = 0;
                        new MyDBHelper(context).updateNetwork(n);
                    }
                    for (Network n : networks2) {
                        n.enable = 1;
                        new MyDBHelper(context).updateNetwork(n);
                    }
                }
            } else if (command.equals("file_push")) {
                JSONObject content = data.getJSONObject("content");
                m.what = MSG_PUSH_FILE;
                m.obj = content.toString();
            } else if (command.equals("portrait")) {
                m.what = MSG_PORTRAIT;
            } else if (command.equals("landscape")) {
                m.what = MSG_LANDSCAPE;
            } else if (command.equals("uninstall")) {
                m.what = MSG_UNINSTALL;
                JSONObject content = data.getJSONObject("content");
                m.obj = content.getString("packages");
            } else if (command.equals("parent_bind")) {
                m.what = MSG_PARENT_BIND;
                m.obj = data;
            } else if (command.equals("call_gone") || command.equals("call_come")) {
                //去电的添加、编辑、删除
                //来电的添加、编辑、删除
                JSONArray content = data.optJSONArray("content");
                ArrayList<Call> calls = new GsonBuilder().create().fromJson(content.toString(), new TypeToken<List<Call>>() {
                }.getType());
                Call c = calls.get(0);
                if (c.operation.equals("save")) {
                    new MyDBHelper(context).addCall(c);
                } else if (c.operation.equals("update")) {
                    new MyDBHelper(context).updateCall(c);
                } else if (c.operation.equals("delete")) {
                    new MyDBHelper(context).deleteCall(c.id);
                }
                //TODO call_come 这里要调用一下华为的方法
            } else if (command.equals("call_come_gone")) {
                int enable_call_type = data.optInt("type");
                int froms = data.optInt("froms");
                if (froms == 2) {
                    //去电的黑名单，不用做。
                    return false;
                }
                Log.d("test", "enable_call_type = " + enable_call_type);
                ArrayList<Call> calls = new MyDBHelper(context).getAllCalls(1);
                if (enable_call_type == 1) {
                    for (Call n : calls) {
                        if (n.type == 1) {
                            n.enable = 1;
                            new MyDBHelper(context).updateCall(n);
                        }
                    }
                    for (Call n : calls) {
                        if (n.type == 2) {
                            n.enable = 0;
                            new MyDBHelper(context).updateCall(n);
                        }
                    }
                } else if (enable_call_type == 2) {
                    for (Call n : calls) {
                        if (n.type == 1) {
                            n.enable = 0;
                            new MyDBHelper(context).updateCall(n);
                        }
                    }
                    for (Call n : calls) {
                        if (n.type == 2) {
                            n.enable = 1;
                            new MyDBHelper(context).updateCall(n);
                        }
                    }
                }
            }

            if (KWApp.instance == null) {
                return false;
            }
            KWApp.instance.mHandler.sendMessage(m);

            //测试用
            Message testMsg = new Message();
            testMsg.what = MSG_TOAST;
            testMsg.obj = receive;
            KWApp.instance.mHandler.sendMessage(testMsg);
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
