package cn.kiway.robot.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.wx.reply.utils.RabbitMQUtils;

import static cn.kiway.robot.KWApplication.channels;
import static cn.kiway.robot.KWApplication.rabbitMQUtils;
import static cn.kiway.robot.entity.AddFriend.STATUS_ADD_SUCCESS;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.HEART_BEAT_TESTER;
import static cn.kiway.robot.util.Constant.backdoors;
import static cn.kiway.robot.util.Constant.clientUrl;
import static com.mob.tools.utils.DeviceHelper.getApplication;

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
            if (TextUtils.isEmpty(temp)) {
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

    public static boolean isUselessContent(Context c, String remark, String content) {
        if (content.equals("我通过了你的朋友验证请求，现在我们可以开始聊天了")) {
            AddFriend af = new MyDBHelper(c).getAddFriendByRemark(remark);
            if (af != null) {
                af.status = STATUS_ADD_SUCCESS;
                new MyDBHelper(c).updateAddFriend(af);
                Utils.updateUserStatus(af.phone, remark, STATUS_ADD_SUCCESS);
            }
            return true;
        }
        return false;
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

    public static String getParentRemark(Context c, int plus) {
        String parentId = FileUtils.readSDCardFile(KWApplication.ROOT + "parent.txt", c);
        if (TextUtils.isEmpty(parentId)) {
            parentId = "1";//String.format("%04d", 1);
        } else {
            int id = Integer.parseInt(parentId);
            parentId = "" + (id + plus);//String.format("%04d", (id + 1));
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
            String imei = Utils.getIMEI(c);

            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
            String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);

            RequestParams param = new RequestParams();
            param.put("appId", APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", wxNo);//userId
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
        String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
        String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");
        if (TextUtils.isEmpty(robotId)) {
            return;
        }
        if (TextUtils.isEmpty(wxNo)) {
            return;
        }
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String topic = "kiway_wx_reply_push_" + robotId + "#" + wxNo;
                    Log.d("test", "topic = " + topic);
                    if (rabbitMQUtils == null) {
                        rabbitMQUtils = new RabbitMQUtils(Constant.host, Constant.port);
                    }
                    Channel channel = rabbitMQUtils.createChannel(topic, topic);
                    channels.add(channel);
                    rabbitMQUtils.consumeMsg(new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            //消费消费
                            String msg = new String(body, "utf-8");
                            System.out.println("consume msg: " + msg);
                            //处理逻辑
                            if (AutoReplyService.instance != null) {
                                //如果发送者是心跳，添加到队头
                                if (msg.contains(HEART_BEAT_TESTER)) {
                                    AutoReplyService.instance.sendReplyImmediately(msg, true);
                                } else {
                                    AutoReplyService.instance.sendReplyImmediately(msg, false);
                                }
                            }
                            //手动消息确认
                            channel.basicAck(envelope.getDeliveryTag(), false);
                        }
                    }, channel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static boolean isEffectiveDate() {
        try {
            String format = "HH:mm:ss";

            String dateStr = new SimpleDateFormat(format).format(new Date());
            Date nowTime = new SimpleDateFormat(format).parse(dateStr);


            Date startTime = new SimpleDateFormat(format).parse("08:30:00");
            Date endTime = new SimpleDateFormat(format).parse("22:00:00");

            if (nowTime.getTime() == startTime.getTime()
                    || nowTime.getTime() == endTime.getTime()) {
                return true;
            }

            Calendar date = Calendar.getInstance();
            date.setTime(nowTime);

            Calendar begin = Calendar.getInstance();
            begin.setTime(startTime);

            Calendar end = Calendar.getInstance();
            end.setTime(endTime);

            if (date.after(begin) && date.before(end)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String replace(String text) {
        while (true) {
            String expression = "\\[.{1,3}\\]|\\&+";
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                text = text.replace(matcher.group(), "");
            } else {
                return text;
            }
        }
    }

    public static void uploadFriend(Context c, ArrayList<Friend> friends) {
        try {
            String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);

            String url = clientUrl + "/freind/all";
            Log.d("test", "freind url = " + url);

            JSONArray param = new JSONArray();
            for (Friend f : friends) {
                JSONObject o = new JSONObject();
                o.put("nickname", f.nickname);//昵称
                o.put("remark", TextUtils.isEmpty(f.remark) ? f.nickname : f.remark);//备注
                o.put("wxId", f.wxId);//微信id
                o.put("wxNo", TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo);//微信号
                o.put("robotId", robotId);
                param.put(o);
            }

            Log.d("test", "freind param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "freind onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "freind onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }


    public static int checkInBackDoor(String content) {
        if (TextUtils.isEmpty(content)) {
            return 0;
        }
        //1.直接匹配的
        boolean fastCheck = content.equals(BACK_DOOR1) || content.equals(BACK_DOOR2);
        if (fastCheck) {
            return backdoors.get(content);
        }
        //2.json判断
        boolean isJsonString = true;
        try {
            JSONObject o = new JSONObject(content);
        } catch (JSONException e) {
            //e.printStackTrace();
            isJsonString = false;
        }
        if (!isJsonString) {
            return 0;
        }
        //3.json匹配的
        Iterator<Map.Entry<String, Integer>> it = backdoors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            String key = entry.getKey();
            int value = entry.getValue();


            if (content.contains(key)) {
                return value;
            }
        }

        return 0;
    }

    public static boolean aContainsB(String text, String targetText) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(targetText)) {
            return false;
        }
        if (!targetText.contains("|")) {
            return false;
        }
        String[] targets = targetText.split("\\|");
        for (String s : targets) {
            if (text.contains(s)) {
                return true;
            }
        }
        return false;
    }


    public static boolean aEqualsB(String text, String targetText) {
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(targetText)) {
            return false;
        }
        if (!targetText.contains("|")) {
            return false;
        }
        String[] targets = targetText.split("\\|");
        for (String s : targets) {
            if (text.equals(s)) {
                return true;
            }
        }
        return false;
    }


    public static long dateToLong(String currentTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = sdf.parse(currentTime);
        long lTime = dt.getTime();
        return lTime;
    }

    public static String longToDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(time);
        String str = sdf.format(date);
        return str;
    }

    public static void updateUserStatus(String phone, String remark, int status) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String url = clientUrl + "/users/status";
                    Log.d("test", "url = " + url);
                    Log.d("test", "phone = " + phone + " status = " + status);
                    HttpPut httpRequest = new HttpPut(url);
                    DefaultHttpClient client = new DefaultHttpClient();

                    List<NameValuePair> values = new ArrayList<>();
                    values.add(new BasicNameValuePair("phone", phone));
                    values.add(new BasicNameValuePair("status", "" + status));
                    if (status == STATUS_ADD_SUCCESS) {
                        values.add(new BasicNameValuePair("nickName", remark));
                    }

                    UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(values,
                            "UTF-8");
                    httpRequest.setEntity(urlEntity);

                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity(), "utf-8");
                    Log.d("test", "ret = " + ret);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        if (status == STATUS_ADD_SUCCESS) {
            String current = System.currentTimeMillis() + "";
            ArrayList<Friend> friends = new ArrayList<>();
            friends.add(new Friend(Utils.getNicknameFromRemark(remark), remark, current, current));
            Utils.uploadFriend(getApplication(), friends);
        }
    }

    public static String getPhoneNumber(Context c) {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String number = mTelephonyMgr.getLine1Number();
        Log.d("test", "number = " + number);
        return number;
    }

    public static void sendSMS(Context c, String phoneNumber, String message) {
        //获取短信管理器
        SmsManager manager = SmsManager.getDefault();
        //自定一两个intent,发送两个隐式意图,这两个隐式意图自己定义
        Intent intent1 = new Intent("com.example.SENT");
        ;
        Intent intent2 = new Intent("com.example.DELIVERY");
        PendingIntent pi1 = PendingIntent.getBroadcast(c, 0, intent1, 0);//延迟意图
        PendingIntent pi2 = PendingIntent.getBroadcast(c, 0, intent2, 0);
        //发送短信
        manager.sendTextMessage(phoneNumber, null, message, pi1, pi2);
    }


    private static int lastStatus = -1;

    //status： 1机器人正常 2机器人异常 3微信正常 4微信异常
    public static void updateOpenIdOrStatus(MainActivity act, Object o) {
        if (act == null) {
            return;
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String xtoken = act.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    String robotId = act.getSharedPreferences("kiway", 0).getString("robotId", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    Log.d("test", "xtoken = " + xtoken);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/robot/" + robotId;
                    Log.d("test", "updateOpenIdOrStatus url = " + url);

                    com.loopj.android.http.RequestParams param = new com.loopj.android.http.RequestParams();
                    if (o instanceof String) {
                        param.put("openId", o);
                    } else if (o instanceof Integer) {
                        if (lastStatus == (int) o) {
                            Log.d("test", "状态一致，本次不上报");
                            return;
                        }
                        param.put("status", o);
                        lastStatus = (int) o;
                    }
                    Log.d("test", "param = " + param.toString());

                    client.put(act, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "updateOpenIdOrStatus onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "updateOpenIdOrStatus onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static String getNicknameFromRemark(String remark) {
        try {
            String nickname = remark.split(" ")[1];
            if (TextUtils.isEmpty(nickname)) {
                return remark;
            }
            return nickname;
        } catch (Exception e) {
            e.printStackTrace();
            return remark;
        }
    }
}
