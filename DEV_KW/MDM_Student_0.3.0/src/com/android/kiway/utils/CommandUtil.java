package com.android.kiway.utils;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.android.kiway.KWApp;
import com.android.kiway.activity.CallActivity;
import com.android.kiway.activity.MainActivity;
import com.android.kiway.entity.AppCharge;
import com.android.kiway.entity.Call;
import com.android.kiway.entity.Network;
import com.android.kiway.entity.TimeSet;
import com.android.kiway.entity.Wifi;
import com.android.kiway.zbus.ZbusHost;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.kiway.KWApp.MSG_ATTEND_CALSS;
import static com.android.kiway.KWApp.MSG_FLAGCOMMAND;
import static com.android.kiway.KWApp.MSG_GET_OUT_OF_CALASS;
import static com.android.kiway.KWApp.MSG_LANDSCAPE;
import static com.android.kiway.KWApp.MSG_LAUNCH_APP;
import static com.android.kiway.KWApp.MSG_LAUNCH_MDM;
import static com.android.kiway.KWApp.MSG_LOCK;
import static com.android.kiway.KWApp.MSG_MESSAGE;
import static com.android.kiway.KWApp.MSG_PARENT_BIND;
import static com.android.kiway.KWApp.MSG_PORTRAIT;
import static com.android.kiway.KWApp.MSG_PUSH_FILE;
import static com.android.kiway.KWApp.MSG_REBOOT;
import static com.android.kiway.KWApp.MSG_SHUTDOWN;
import static com.android.kiway.KWApp.MSG_TOAST;
import static com.android.kiway.KWApp.MSG_UNINSTALL;
import static com.android.kiway.KWApp.MSG_UNLOCK;

/**
 * Created by Administrator on 2018/1/23.
 */

public class CommandUtil {

    public static boolean handleCommand(Context context, String receive) {
        try {
            Log.d("huawei", "onPushMsg = " + receive);
            if (TextUtils.isEmpty(receive)) {
                return false;
            }

            //如果没有登录，不接收推送
            if (!context.getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                Log.d("huawei", "没有登录，不接受推送");
                return false;
            }

            //FIXME 测试用
            Message testMsg = new Message();
            testMsg.what = MSG_TOAST;
            testMsg.obj = receive;
            KWApp.instance.mHandler.sendMessage(testMsg);

            String dataStr = new JSONObject(receive).getString("data");
            JSONObject data = new JSONObject(dataStr);
            String command = data.optString("command");
            Constant.teacherUserId = data.optString("teacherUserId");

            Message m = new Message();

            if (command.equals("allowAppFunction")) {
                //重置所有的值为0
                Utils.resetFunctions(context, 0);
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
                String operation = data.optString("operation");
                String startTime = data.optString("startTime");
                String endTime = data.optString("endTime");
                if (operation.equals("save")) {
                    context.getSharedPreferences("kiway", 0).edit().putString("shutdown_startTime", startTime).commit();
                    context.getSharedPreferences("kiway", 0).edit().putString("shutdown_endTime", endTime).commit();
                    Utils.checkShutDown(MainActivity.instance);
                    return false;
                }
                if (operation.equals("delete")) {
                    context.getSharedPreferences("kiway", 0).edit().putString("shutdown_startTime", "").commit();
                    context.getSharedPreferences("kiway", 0).edit().putString("shutdown_endTime", "").commit();
                    return false;
                }
            } else if (command.equals("temporary_app")) {
                String currentTime = data.optString("currentTime");
                context.getSharedPreferences("kiway", 0).edit().putLong("app_time", Utils.dateToLong(currentTime))
                        .commit();
                context.getSharedPreferences("kiway", 0).edit().putString("app_data", data.toString()).commit();
                m.what = MSG_LAUNCH_APP;
                m.obj = data;
            } else if (command.equals("temporary_app_uncharge")) {
                m.what = MSG_LAUNCH_MDM;
                context.getSharedPreferences("kiway", 0).edit().putLong("app_time", 0).commit();
                context.getSharedPreferences("kiway", 0).edit().putString("app_data", "").commit();
            } else if (command.equals("temporary_lockScreen")) {
                m.what = MSG_LOCK;
                m.obj = data;
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
                context.getSharedPreferences("kiway", 0).edit().putLong("lock_time", Utils.dateToLong(currentTime))
                        .commit();
            } else if (command.equals("temporary_unlockScreen")) {
                String currentTime = data.optString("currentTime");
                if (!Utils.checkCommandAvailable(currentTime)) {
                    return false;
                }
                context.getSharedPreferences("kiway", 0).edit().putLong("lock_time", 0).commit();
                m.obj = data;
                m.what = MSG_UNLOCK;
            } else if (command.equals("wifi")) {
                JSONArray content = data.optJSONArray("content");
                ArrayList<Wifi> wifis = new GsonBuilder().create().fromJson(content.toString(), new
                        TypeToken<List<Wifi>>() {
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
                ArrayList<AppCharge> apps = new GsonBuilder().create().fromJson(content.toString(), new
                        TypeToken<List<AppCharge>>() {
                        }.getType());
                AppCharge app = apps.get(0);
                app.priority = data.optInt("priority");
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
                ArrayList<Network> networks = new GsonBuilder().create().fromJson(content.toString(), new
                        TypeToken<List<Network>>() {
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
                //network黑白名单启用
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
                ArrayList<Call> calls = new GsonBuilder().create().fromJson(content.toString(), new
                        TypeToken<List<Call>>() {
                        }.getType());
                Call c = calls.get(0);
                if (c.operation.equals("save")) {
                    new MyDBHelper(context).addCall(c);
                } else if (c.operation.equals("update")) {
                    new MyDBHelper(context).updateCall(c);
                } else if (c.operation.equals("delete")) {
                    new MyDBHelper(context).deleteCall(c.id);
                }
                if (KWApp.instance.currentActivity != null && KWApp.instance.currentActivity instanceof CallActivity) {
                    ((CallActivity) KWApp.instance.currentActivity).refreshUI();
                }
                //TODO call_come 这里要调用一下华为的方法
            } else if (command.equals("call_come_gone")) {
                //来电黑白名单启用
                int enable_call_type = data.optInt("type");
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
            } else if (command.equals("parent_charge_app")) {
                String packageName = data.optJSONObject("content").optString("package");
                String ids = data.optJSONObject("content").optString("id");
                TimeSet timeSet = new TimeSet();
                if (data.optJSONObject("content").optString("operation").equals("detele")) {
                    new MyDBHelper(context).deleteTime(ids, packageName);
                } else {
                    timeSet.packageName = packageName;
                    JSONArray array = new JSONArray();
                    array.put(data.optJSONObject("content").optJSONObject("tims"));
                    timeSet.times = array.toString();
                    timeSet.ids = ids;
                    new MyDBHelper(context).deleteTime(timeSet.ids, packageName);
                    new MyDBHelper(context).addTime(timeSet);
                }
            }
            //下面是和课堂互动相关的命令。。。。。
            else if (command.equals("shangke")) {
                m.what = MSG_ATTEND_CALSS;
                m.obj = data;
                //返回shagnke给教师端，当作online
                ZbusHost.doSendMsg(context, "shangke");
            } else if (command.equals("xiake")) {
                m.what = MSG_GET_OUT_OF_CALASS;
                m.obj = data;
            } else if (command.equals("send_msg")
                    || command.equals("question")
                    || command.equals("questionTimeup")
                    || command.equals("questionEnd")
                    || command.equals("sign")
                    || command.equals("tongji")
                    || command.equals("qiangda")
                    || command.equals("qiangdaResult")
                    || command.equals("wenjian")
                    || command.equals("collection")
                    || command.equals("tuiping")
                    || command.equals("chaping")
                    ) {
                m.what = MSG_MESSAGE;
                m.obj = data;
            } else if (command.equals("test")) {
                int id = data.optInt("id");
                long sendTime = data.optLong("time");
                long currentTime = System.currentTimeMillis();
                Log.d("test", "id = " + id + " , delay = " + (currentTime - sendTime));
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
}
