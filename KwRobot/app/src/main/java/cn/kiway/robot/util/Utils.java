package cn.kiway.robot.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
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

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.wx.reply.utils.RabbitMQUtils;

import static cn.kiway.robot.KWApplication.ROOT;
import static cn.kiway.robot.KWApplication.channels;
import static cn.kiway.robot.KWApplication.rabbitMQUtils;
import static cn.kiway.robot.entity.AddFriend.STATUS_ADD_SUCCESS;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.backdoors;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static com.mob.tools.utils.ResHelper.copyFile;

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
        String parentId = FileUtils.readSDCardFile(ROOT + "parent.txt", c);
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
            param.put("userId", wxNo);
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
        final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
        final String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");
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
                    final Channel channel = rabbitMQUtils.createChannel(topic, topic);
                    channels.add(channel);
                    rabbitMQUtils.consumeMsg(new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            //消费消费
                            String msg = new String(body, "utf-8");
                            Log.d("test", "handleDelivery msg = " + msg);
                            //处理逻辑
                            if (AutoReplyService.instance != null) {
                                AutoReplyService.instance.sendReplyImmediately(msg, false);
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
                String remark = TextUtils.isEmpty(f.remark) ? f.nickname : f.remark;
                remark = Utils.filterEmoji(remark);

                JSONObject o = new JSONObject();
                o.put("nickname", f.nickname);//昵称
                o.put("remark", remark);//备注
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

    public static String filterEmoji(String str) {
        String pattern =    "[\ud800\udc00-\udbff\udfff\ud800-\udfff]";
        //String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr = "";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        str = emojiMatcher.replaceAll(reStr);
        return str;
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

    public static void updateUserStatus(final String phone, final String remark, final int status) {
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

        //0613不再调用
//        if (status == STATUS_ADD_SUCCESS) {
//            String current = System.currentTimeMillis() + "";
//            ArrayList<Friend> friends = new ArrayList<>();
//            friends.add(new Friend(Utils.getNicknameFromRemark(remark), remark, current, current));
//            Utils.uploadFriend(getApplication(), friends);
//        }
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
    public static void updateOpenIdOrStatus(final MainActivity act, final Object o) {
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

    public static String getPhoneFromRemark(Context c, String remark) {
        AddFriend af = new MyDBHelper(c).getAddFriendByRemark(remark);
        if (af == null) {
            return "";
        }
        if (TextUtils.isEmpty(af.phone)) {
            return "";
        }
        return af.phone;
    }

    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static final String WX_ROOT_PATH = "/data/data/com.tencent.mm/";

    public static final String WX_SP_UIN_PATH = WX_ROOT_PATH + "shared_prefs/auth_info_key_prefs.xml";

    public static final String WX_DB_DIR_PATH = WX_ROOT_PATH + "MicroMsg";

    public static String initDbPassword(Context c) {
        execRootCmdSilent("chmod -R 777 " + WX_ROOT_PATH);
        execRootCmdSilent("chmod  777 /data/data/com.tencent.mm/shared_prefs/auth_info_key_prefs.xml");
        String uin = initCurrWxUin();
        TelephonyManager phone = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = phone.getDeviceId();
        Log.d("test", "imei = " + imei);
        if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(uin)) {
            LogUtil.e("初始化数据库密码失败：imei或uid为空");
            return null;
        }
        String md5 = getMD5(imei + uin);
        System.out.println(imei + uin + "初始数值");
        System.out.println(md5 + "MD5");
        String password = md5.substring(0, 7).toLowerCase();
        Log.d("test", "password = " + password);
        return password;
    }

    private static String initCurrWxUin() {
        String Uin = null;
        File file = new File(WX_SP_UIN_PATH);
        try {
            FileInputStream in = new FileInputStream(file);
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(in);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            for (Element element : elements) {
                if ("_auth_uin".equals(element.attributeValue("name"))) {
                    Uin = element.attributeValue("value");
                }
            }
            return Uin;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("获取微信uid失败，请检查auth_info_key_prefs文件权限");
        }
        return null;
    }

    public static File getWxDBFile(String dbName, String saveDbName) {
        long latestModified = 0;
        ArrayList<File> dbs = findDBFile(dbName);
        File latestFile = null;
        for (File f : dbs) {
            if (f.lastModified() > latestModified) {
                latestModified = f.lastModified();
                latestFile = f;
            }
        }
        if (latestFile == null) {
            return null;
        }
        String copyFilePath = ROOT + saveDbName;
        if (new File(copyFilePath).exists()) {
            new File(copyFilePath).delete();
        }
        copyFile(latestFile.getAbsolutePath(), copyFilePath);

        return new File(copyFilePath);
    }

    public static ArrayList<File> findDBFile(String dbName) {
        ArrayList<File> dbs = new ArrayList<>();
        File wxDataDir = new File(WX_DB_DIR_PATH);
        doFindDBFile(wxDataDir, dbName, dbs);
        return dbs;
    }

    private static void doFindDBFile(File file, String fileName, ArrayList<File> dbs) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    doFindDBFile(childFile, fileName, dbs);
                }
            }
        } else {
            if (fileName.equals(file.getName())) {
                dbs.add(file);
            }
        }
    }

    public static ArrayList<Friend> doGetFriends(Context c, File dbFile, String password) {
        ArrayList<Friend> friends = new ArrayList<>();
        try {
            SQLiteDatabase db = openWechatDB(c, dbFile, password);
            String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");//me
            Cursor c1 = db.rawQuery("select * from rcontact where username not like 'gh_%' and username not like '%@chatroom' and  verifyFlag<>24 and verifyFlag<>29 and verifyFlag<>56 and type<>33 and type<>70 and verifyFlag=0 and type<>4 and type<>0 and showHead<>43 and type<>65536", null);
            while (c1.moveToNext()) {
                String username = c1.getString(c1.getColumnIndex("username"));  //wxID
                String alias = c1.getString(c1.getColumnIndex("alias"));        //wxNo
                String nickname = c1.getString(c1.getColumnIndex("nickname"));  //nickname
                String conRemark = c1.getString(c1.getColumnIndex("conRemark"));//remark
                if (wxNo.equals(alias)) {
                    Log.d("test", "wxNo = " + wxNo);
                    Log.d("test", "wxId = " + username);
                    c.getSharedPreferences("kiway", 0).edit().putString("wxId", username).commit();
                } else {
                    friends.add(new Friend(nickname, conRemark, username, alias));
                }
            }
            Log.d("test", "friends = " + friends);
            c1.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static ArrayList<Group> doGetGroups(Context c, File dbFile, String password, String groupName) {
        Log.d("test", "doGetGroups");
        ArrayList<Group> groups = new ArrayList<>();
        try {
            SQLiteDatabase db = openWechatDB(c, dbFile, password);
            Cursor c1 = null;
            String sql = "";
            if (TextUtils.isEmpty(groupName)) {
                sql = "select username , nickname from rContact where type = 3";
            } else {
                sql = "select username , nickname from rContact where type = 3 and nickname = '" + groupName + "'";
            }
            Log.d("test", "sql = " + sql);
            c1 = db.rawQuery(sql, null);
            while (c1.moveToNext()) {
                String username = c1.getString(c1.getColumnIndex("username"));  //clientGroupId
                String nickname = c1.getString(c1.getColumnIndex("nickname"));  //groupName
                if (username.endsWith("chatroom")) {
                    groups.add(new Group(username, nickname));
                }
            }
            Log.d("test", "groups = " + groups);
            c1.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    public static ArrayList<Message> doGetMessages(Context c, File dbFile, String password) {
        Log.d("test", "doGetMessages");
        ArrayList<Message> messages = new ArrayList<>();
        try {
            SQLiteDatabase db = openWechatDB(c, dbFile, password);
            long current = System.currentTimeMillis();
            long before1hour = current - 60 * 60 * 1000;
            String sql = " select message.msgId , message.type , message.createTime  , message.talker , rcontact.nickname ,  rcontact.conRemark, message.content from message left JOIN rcontact on message.talker = rcontact.username where message.isSend = 0 and message.type = 1 and message.createTime > " + before1hour;
            Log.d("test", "sql = " + sql);
            Cursor c1 = db.rawQuery(sql, null);
            while (c1.moveToNext()) {
                int type = c1.getInt(c1.getColumnIndex("type"));
                long createTime = c1.getLong(c1.getColumnIndex("createTime"));
                String talker = c1.getString(c1.getColumnIndex("talker"));
                String nickname = c1.getString(c1.getColumnIndex("nickname"));
                String conRemark = c1.getString(c1.getColumnIndex("conRemark"));
                String content = c1.getString(c1.getColumnIndex("content"));
                //0613暂时只做个人发的消息
                if (talker.endsWith("@chatroom")) {
                    continue;
                }
                Message m = new Message();
                m.type = type;
                m.createTime = createTime;
                m.talker = talker;
                m.remark = TextUtils.isEmpty(conRemark) ? nickname : conRemark;
                m.content = content;
                messages.add(m);
            }
            Log.d("test", "messages = " + messages);
            c1.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static ArrayList<String> doGetPeopleInGroup(Context c, File dbFile, String password, String clientGroupId) {
        Log.d("test", "doGetGroups");
        ArrayList<String> peoples = new ArrayList<>();
        try {
            SQLiteDatabase db = openWechatDB(c, dbFile, password);
            String sql = "select  displayname  from chatroom where chatroomname = '" + clientGroupId + "'";

            Log.d("test", "sql = " + sql);
            Cursor c1 = db.rawQuery(sql, null);
            while (c1.moveToNext()) {
                String username = c1.getString(c1.getColumnIndex("displayname"));
                String[] temp = username.split("、");
                Collections.addAll(peoples, temp);
                Log.d("test", "username = " + username);
            }
            c1.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return peoples;
    }

    private static SQLiteDatabase openWechatDB(Context c, File dbFile, String password) {
        SQLiteDatabase.loadLibs(c);
        SQLiteDatabaseHook hook = new SQLiteDatabaseHook() {
            public void preKey(SQLiteDatabase database) {
            }

            public void postKey(SQLiteDatabase database) {
                database.rawExecSQL("PRAGMA cipher_migrate;");
            }
        };
        return SQLiteDatabase.openOrCreateDatabase(dbFile, password, null, hook);
    }


    //null:private
    //string:group
    public static String isFromGroup(Context c, String name) {
        int lastLeft = name.lastIndexOf("(");
        int lastRight = name.lastIndexOf(")");
        if (lastRight != -1 && lastLeft != -1 && (lastRight - lastLeft > 1)) {
            name = name.substring(0, lastLeft);
        }
        ArrayList<Group> groups = new MyDBHelper(c).getWXGroups();
        for (Group group : groups) {
            if (name.equals(group.groupName)) {
                return group.clientGroupId;
            }
        }
        return null;
    }

    public static void uploadGroup(final MainActivity act, final ArrayList<Group> groups) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String robotId = act.getSharedPreferences("kiway", 0).getString("robotId", "");
                String wxNo = act.getSharedPreferences("kiway", 0).getString("wxNo", "");

                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    String url = clientUrl + "/groups/name/change";
                    Log.d("test", "groups/name/change url = " + url);
                    JSONArray param = new JSONArray();
                    for (Group g : groups) {
                        JSONObject o = new JSONObject();
                        o.put("clientGroupId", g.clientGroupId);
                        o.put("name", g.groupName);
                        o.put("robotId", robotId);
                        o.put("userId", wxNo);
                        param.put(o);
                    }
                    Log.d("test", "groups/name/change param = " + param.toString());
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    client.put(act, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "groups/name/change onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "groups/name/change onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }


    public static ArrayList<String> doGetMoments(Context c, File dbFile) {
        Log.d("test", "doGetMoments");
        ArrayList<String> moments = new ArrayList<>();
        try {
            SQLiteDatabase db = openWechatDB(c, dbFile, null);
            String sql = "select  *  from SnsInfo";
            Log.d("test", "sql = " + sql);
            Cursor c1 = db.rawQuery(sql, null);
            while (c1.moveToNext()) {
                String userName = c.getString(c1.getColumnIndex("userName"));
                byte[] content = c1.getBlob(c1.getColumnIndex("content"));
                Log.d("test", "username = " + userName);
                Log.d("test", "content = " + new String(content));
            }
            c1.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return moments;
    }

    public static boolean isWifiProxy(Context c) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            proxyAddress = android.net.Proxy.getHost(c);
            proxyPort = android.net.Proxy.getPort(c);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }

    public static void saveDefaultFile(final Context c, final String fileName, final int id) {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!new File(ROOT + "downloads/").exists()) {
                        new File(ROOT + "downloads/").mkdirs();
                    }
                    File file = new File(ROOT + "downloads/" + fileName);
                    if (file.exists()) {
                        return;
                    }
                    InputStream inStream = c.getResources().openRawResource(id);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[10];
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    int len = 0;
                    while ((len = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    byte[] bs = outStream.toByteArray();
                    fileOutputStream.write(bs);
                    outStream.close();
                    inStream.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void saveDefaultFile(final Context c, final String fileName, final String url) {
        new Thread() {
            @Override
            public void run() {
                if (!new File(ROOT + "downloads/").exists()) {
                    new File(ROOT + "downloads/").mkdirs();
                }
                final String savedFilePath = ROOT + "downloads/" + fileName;
                File file = new File(savedFilePath);
                if (file.exists()) {
                    Log.d("test", "exist size = " + file.length());
                    return;
                }
                org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
                params.setSaveFilePath(savedFilePath);
                params.setAutoRename(false);
                params.setAutoResume(true);
                x.http().get(params, new Callback.CommonCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        Log.d("test", "onSuccess");
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.d("test", "onError");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.d("test", "onCancelled");
                    }

                    @Override
                    public void onFinished() {
                        Log.d("test", "onFinished");
                    }
                });
            }
        }.start();
    }
}
