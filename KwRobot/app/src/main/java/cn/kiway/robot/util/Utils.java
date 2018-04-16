package cn.kiway.robot.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Enumeration;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.entity.ZbusRecv;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.wx.reply.utils.ZbusUtils;
import io.netty.util.internal.StringUtil;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.clientUrl;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Utils {

    public static String getIMEI(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (imei == null || imei.equals("null")) {
            imei = getLocalEthernetMacAddress();
        }
        Log.d("test", "IMEI = " + imei);
        return imei;
    }


    @SuppressLint("NewApi")
    public static String getLocalEthernetMacAddress() {
        String mac = null;
        try {
            @SuppressWarnings("rawtypes")
            Enumeration localEnumeration = NetworkInterface
                    .getNetworkInterfaces();
            while (localEnumeration.hasMoreElements()) {
                NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration
                        .nextElement();
                String interfaceName = localNetworkInterface.getDisplayName();
                if (interfaceName == null) {
                    continue;
                }
                if (interfaceName.equals("eth0")) {
                    mac = convertToMac(localNetworkInterface
                            .getHardwareAddress());
                    if (mac != null && mac.startsWith("0:")) {
                        mac = "0" + mac;
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return mac;
    }

    @SuppressLint("DefaultLocale")
    private static String convertToMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            byte b = mac[i];
            int value = 0;
            if (b >= 0 && b <= 16) {
                value = b;
                sb.append("0" + Integer.toHexString(value));
            } else if (b > 16) {
                value = b;
                sb.append(Integer.toHexString(value));
            } else {
                value = 256 + b;
                sb.append(Integer.toHexString(value));
            }
        }
        return sb.toString().toLowerCase();
    }


    public static boolean isInfilters(Context c, String sender) {
        String f = c.getSharedPreferences("filters", 0).getString("filters", "");
        String filters[] = f.split("===");
        if (filters.length == 0) {
            return false;
        }
        for (String temp : filters) {
            if (StringUtil.isNullOrEmpty(temp)) {
                continue;
            }
            if (temp.equals(sender)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGetPic(Context c, String content) {
        if (!content.equals("[图片]")) {
            return true;
        }
        return c.getSharedPreferences("getPic", 0).getBoolean("getPic", true);
    }

    public static String getCurrentVersion(Context c) {
        String versionName = "1.0.0";
        try {
            PackageInfo pkg = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            versionName = pkg.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getParentRemark(Context c) {
        String parentId = FileUtils.readSDCardFile(KWApplication.ROOT + "parent.txt", c);
        if (TextUtils.isEmpty(parentId)) {
            parentId = "1";//String.format("%04d", 1);
        } else {
            int id = Integer.parseInt(parentId);
            parentId = "" + (id + 1);//String.format("%04d", (id + 1));
        }
        FileUtils.saveFile(parentId, "parent.txt");
        return parentId;
    }

    public static String getForwardFrom(Context c) {
        return c.getSharedPreferences("forwardfrom", 0).getString("forwardfrom", "wxid_cokkmqud47e121的接口测试号");//转发使者
        // wxid_cokkmqud47e121的接口测试号
    }

    public static String getFCFrom(Context c) {
        return c.getSharedPreferences("FCFrom", 0).getString("FCFrom", "朋友圈使者");
    }

    public static String getToday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        String today = "" + year + month + date;
        return today;
    }

    public static void installationPush(final Context c) {
        try {
            String userId = Utils.getIMEI(c);
            String imei = Utils.getIMEI(c);

            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            Log.d("test", "userId = " + userId);
            RequestParams param = new RequestParams();
            param.put("appId", APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", imei);//userId
            param.put("module", "student");
            param.put("robotId", robotId);
            Log.d("test", "installationPush param = " + param.toString());

            String url = clientUrl + "/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    try {
                        String installationId = new JSONObject(ret).getJSONObject("data").getString("installationId");
                        c.getSharedPreferences("kiway", 0).edit().putString("installationId", installationId).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    initZbus(c);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    //初始化zbus
    public static void initZbus(Context c) {
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String userId = Utils.getIMEI(c);
                    if (TextUtils.isEmpty(userId)) {
                        return;
                    }
                    String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    String topic = "kiway_wx_reply_push_" + robotId + "#" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsg(topic, new MessageHandler() {

                        @Override
                        public void handle(Message message, MqClient mqClient) {
                            String msg = message.getBodyString();
                            if (AutoReplyService.instance != null) {
                                AutoReplyService.instance.zbusRecvs.add(new ZbusRecv(msg, true));
                            }
                        }
                    }, Constant.zbusHost + ":" + Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
