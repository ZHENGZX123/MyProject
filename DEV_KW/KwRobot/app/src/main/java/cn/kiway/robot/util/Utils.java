package cn.kiway.robot.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.xiaoleilu.hutool.crypto.symmetric.DES;

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
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.robot.KWApplication;
import cn.kiway.robot.activity.MainActivity;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Action;
import cn.kiway.robot.entity.AddFriend;
import cn.kiway.robot.entity.FileDownload;
import cn.kiway.robot.entity.Filter;
import cn.kiway.robot.entity.Friend;
import cn.kiway.robot.entity.Group;
import cn.kiway.robot.entity.GroupPeople;
import cn.kiway.robot.entity.ImageUpload;
import cn.kiway.robot.entity.Message;
import cn.kiway.robot.entity.ServerMsg;
import cn.kiway.robot.moment.SnsInfo;
import cn.kiway.robot.service.AutoReplyService;
import cn.kiway.wx.reply.utils.RabbitMQUtils;

import static cn.kiway.robot.KWApplication.DOWNLOAD;
import static cn.kiway.robot.KWApplication.ROOT;
import static cn.kiway.robot.entity.AddFriend.STATUS_ADD_SUCCESS;
import static cn.kiway.robot.entity.FileDownload.DOWNLOAD_STATUS_0;
import static cn.kiway.robot.entity.FileDownload.DOWNLOAD_STATUS_1;
import static cn.kiway.robot.entity.FileDownload.DOWNLOAD_STATUS_2;
import static cn.kiway.robot.entity.FileDownload.DOWNLOAD_STATUS_3;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_0;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_1;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_2;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_3;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_4;
import static cn.kiway.robot.entity.ImageUpload.UPLOAD_STATUS_5;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_0;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_1;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_2;
import static cn.kiway.robot.entity.ServerMsg.ACTION_STATUS_3;
import static cn.kiway.robot.entity.ServerMsg.TYPE_HTTP;
import static cn.kiway.robot.entity.ServerMsg.TYPE_MQ;
import static cn.kiway.robot.util.Constant.APPID;
import static cn.kiway.robot.util.Constant.BACK_DOOR1;
import static cn.kiway.robot.util.Constant.BACK_DOOR2;
import static cn.kiway.robot.util.Constant.BROWSE_MESSAGE_CMD;
import static cn.kiway.robot.util.Constant.DEFAULT_TRANSFER;
import static cn.kiway.robot.util.Constant.SEND_FRIEND_CIRCLE_CMD;
import static cn.kiway.robot.util.Constant.TICK_PERSON_GROUP_CMD;
import static cn.kiway.robot.util.Constant.UPDATE_GROUP_NOTICE_CMD;
import static cn.kiway.robot.util.Constant.backdoors;
import static cn.kiway.robot.util.Constant.clientUrl;
import static cn.kiway.robot.util.Constant.host;
import static cn.kiway.robot.util.RootCmd.execRootCmdSilent;
import static com.mob.tools.utils.ResHelper.copyFile;
import static net.sqlcipher.database.SQLiteDatabase.OPEN_READONLY;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Utils {

    public static RabbitMQUtils rabbitMQUtils;
    public static List<Channel> channels = new ArrayList<>();
    private static Channel channel;

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
        ArrayList<Filter> filters = new MyDBHelper(c).getAllFilters(0);
        for (Filter f : filters) {
            if (f.name.equals(sender)) {
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
                Utils.updateUserStatus(c, af.phone, remark, STATUS_ADD_SUCCESS);
            }
            //0808主动添加好友，请求通过后，上报一次好友列表。
            MainActivity.instance.getAllFriends(false, true);
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
            parentId = "1";
        } else {
            int id = Integer.parseInt(parentId);
            parentId = "" + (id + plus);
        }
        FileUtils.saveFile(parentId, "parent.txt");
        return parentId;
    }

    public synchronized static void installationPush(final Context c) {
        try {
            String imei = Utils.getIMEI(c);

            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
            String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(3, 10000);
            Log.d("test", "x-auth-token = " + xtoken);
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
                    doInstallSuccess(c);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                    check301(c, s, "installationPush");
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    //初始化zbus
    private static void doInstallSuccess(Context c) {
        Log.d("test", "doInstallSuccess");
        startMQThread(c);
        startCheckFileDownloadThread(c);
        startGetServerMsgThread(c);
        startImgUploadThread(c);
    }

    private static void startMQThread(final Context c) {
        final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
        final String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");
        if (TextUtils.isEmpty(robotId)) {
            Log.d("test", "robotId is null");
            return;
        }
        if (TextUtils.isEmpty(wxNo)) {
            Log.d("test", "wxNo is null");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (rabbitMQUtils == null) {
                        rabbitMQUtils = new RabbitMQUtils(Constant.host, Constant.port);
                        String topic = "kiway_wx_reply_push_" + robotId + "#" + wxNo;
                        Log.d("test", "topic = " + topic);
                        if (channel == null) {
                            channel = rabbitMQUtils.createChannel(topic, topic);
                        }
                        while (!channel.isOpen()) {
                            channel = rabbitMQUtils.createChannel(topic, topic);
                            Thread.sleep(5000);
                        }
                        if (channels == null) {
                            channels = new ArrayList<>();
                        }
                        channels.add(channel);
                        rabbitMQUtils.consumeMsg(new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                                String msg;
                                if (host.equals("robot.kiway.cn")) {
                                    msg = new String(body, "utf-8");
                                } else {
                                    //zsrobot
                                    msg = new String(body);
                                }
                                Log.d("test", "handleDelivery msg = " + msg);
                                //手动消息确认
                                channel.basicAck(envelope.getDeliveryTag(), false);

                                getMsgIndexAndSaveDB(c, msg, TYPE_MQ);

                                if (!isHeartBeatReply(msg)) {
                                    handleMsgInDB(c);
                                }
                            }
                        }, channel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static boolean getMsgIndexAndSaveDB(Context c, String msg, int type) {
        try {
            JSONObject o = new JSONObject(msg);
            int index = o.optInt("indexs");
            if (index == 0) {
                Log.d("test", "index = 0 , heartbeat");
                return false;
            }
            boolean existed = new MyDBHelper(c).isIndexExisted(index);
            if (existed) {
                Log.d("test", "DB已存在该记录");
                return true;
            }
            //插入数据库，初始
            new MyDBHelper(c).addServerMsg(new ServerMsg(index, msg, "", ACTION_STATUS_0, System.currentTimeMillis(), type));
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Thread getServerMsgThread;

    private static void startGetServerMsgThread(final Context c) {
        if (getServerMsgThread != null) {
            return;
        }
        getServerMsgThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(2 * 60 * 1000);
                        getLastMsgIndex(c, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        getServerMsgThread.start();
    }


    private static Thread imgUploadThread;
    public static ArrayList<ImageUpload> imgUploads = new ArrayList<>();

    private static void startImgUploadThread(final Context c) {
        if (imgUploadThread != null) {
            return;
        }
        imgUploadThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(60 * 1000);
                        int count = imgUploads.size();
                        for (int i = 0; i < count; i++) {
                            ImageUpload iu = imgUploads.get(i);
                            if (iu.status == UPLOAD_STATUS_0) {
                                //初始态，检查是不是要做补救
                                boolean needSave = false;//checkIsNeedSaveImg(iu);
                                if (needSave) {
                                    //发起补救事件
                                    iu.status = UPLOAD_STATUS_1;
                                    sendSaveImgEvent(TextUtils.isEmpty(iu.clientGroupId) ? iu.sender : iu.clientGroupId, iu.imgPath);
                                } else {
                                    //直接上传
                                    iu.status = UPLOAD_STATUS_2;
                                    doUploadImg(iu, c);
                                }
                            } else if (iu.status == UPLOAD_STATUS_1) {
                                //补救中。。。不做动作
                            } else if (iu.status == UPLOAD_STATUS_2) {
                                //上传中。。。不做动作
                            } else if (iu.status == UPLOAD_STATUS_3) {
                                //上传失败。。。准备重新上传
                                iu.status = UPLOAD_STATUS_2;
                                doUploadImg(iu, c);
                            } else if (iu.status == UPLOAD_STATUS_4) {
                                //上报中。。。不做动作
                            } else if (iu.status == UPLOAD_STATUS_5) {
                                //上传多次失败。。。不做动作
                            }
                            sleep(3000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        imgUploadThread.start();
    }

    private static void sendSaveImgEvent(String target, String imgPath) {
        try {
            JSONObject o = new JSONObject();
            o.put("cmd", BROWSE_MESSAGE_CMD);
            o.put("fromFront", true);
            o.put("target", target);//群：12176038998@chatroom 家长：33 执着
            o.put("imgPath", imgPath);
            String temp = o.toString();
            Log.d("test", "temp = " + temp);
            AutoReplyService.instance.sendReplyImmediately(temp, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkIsNeedSaveImg(ImageUpload iu) {
        //判断模糊图片、清晰图片是否存在
        String clearJpg = getClearJpgByImgPath(iu.imgPath);
        Log.d("test", "clearJpg existed = " + new File(clearJpg).exists());

        if (new File(clearJpg).exists()) {
            return false;
        }

        String fuzzyJpg = getFuzzyJpgByImgPath(iu.imgPath);
        Log.d("test", "fuzzyJpg existed = " + new File(fuzzyJpg).exists());

        //TODO 模糊图片不存在，没有必要进行补救了
        return true;
    }

    private static void doUploadImg(final ImageUpload iu, final Context c) {
        new Thread() {
            @Override
            public void run() {
                try {
                    File targetUploadFile = null;
                    //优先使用清晰图片
                    String clearJpg = getClearJpgByImgPath(iu.imgPath);
                    if (new File(clearJpg).exists()) {
                        targetUploadFile = new File(clearJpg);
                    } else {
                        String fuzzyJpg = getFuzzyJpgByImgPath(iu.imgPath);
                        if (new File(fuzzyJpg).exists()) {
                            targetUploadFile = new File(fuzzyJpg);
                        }
                    }
                    if (targetUploadFile == null) {
                        Log.d("test", "targetUploadFile is null , error");
                        throw new Exception();
                    }
                    final String ret = UploadUtil.uploadFile(targetUploadFile, clientUrl + "/common/file?origin=true", targetUploadFile.getName());
                    JSONObject o = new JSONObject(ret);
                    iu.imgUrl = o.optJSONObject("data").optString("url");

                    //上传成功，直接上报
                    iu.status = UPLOAD_STATUS_4;
                    if (TextUtils.isEmpty(iu.clientGroupId)) {
                        AutoReplyService.instance.sendMsgToServer(iu.sender, iu.imgUrl, iu.saved, Action.TYPE_IMAGE);
                    } else {
                        String name = c.getSharedPreferences("kiway", 0).getString("name", "");
                        String areaCode = c.getSharedPreferences("kiway", 0).getString("areaCode", "");
                        String msg = new JSONObject()
                                .put("id", System.currentTimeMillis() + "")
                                .put("sender", iu.sender)
                                .put("content", iu.imgUrl)
                                .put("me", name)
                                .put("areaCode", areaCode)
                                .put("clientGroupId", iu.clientGroupId).toString();
                        AutoReplyService.instance.sendMsgToServer3(iu.clientGroupId, iu.sender, msg, Action.TYPE_IMAGE, iu.saved);
                    }
                    //TODO 上报不成功，没有做
                } catch (Exception e) {
                    e.printStackTrace();
                    if (iu.uploadCount < 3) {
                        iu.uploadCount++;
                        iu.status = UPLOAD_STATUS_3;
                    } else {
                        iu.status = UPLOAD_STATUS_5;
                    }
                }
            }
        }.start();
    }

    private static String getClearJpgByImgPath(String imgPath) {
        String temp = imgPath.replace("THUMBNAIL_DIRPATH://th_", "");
        return "/sdcard/tencent/MicroMsg/" + wechatCurrentUserMD5 + "/image2/" + temp.substring(0, 2) + "/" + temp.substring(2, 4) + "/" + temp + ".jpg";
    }

    private static String getFuzzyJpgByImgPath(String imgPath) {
        String temp2 = imgPath.replace("THUMBNAIL_DIRPATH://", "");
        return "/sdcard/tencent/MicroMsg/" + wechatCurrentUserMD5 + "/image2/" + temp2.substring(3, 5) + "/" + temp2.substring(5, 7) + "/" + temp2;
    }

    private static Thread fileDownloadThread;
    private static ArrayList<FileDownload> downloads = new ArrayList<>();
    private static ArrayList<String> downloadingUrls = new ArrayList<>();

    private static void startCheckFileDownloadThread(Context c) {
        if (fileDownloadThread != null) {
            return;
        }
        fileDownloadThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        sleep(60 * 1000);
                        int count = downloads.size();
                        for (int i = 0; i < count; i++) {
                            FileDownload fd = downloads.get(i);
                            if (fd.status == DOWNLOAD_STATUS_0) {
                                if (!downloadingUrls.contains(fd.urls.get(0))) {
                                    fd.status = DOWNLOAD_STATUS_1;
                                    doDownloadFile(fd);
                                }
                            } else if (fd.status == DOWNLOAD_STATUS_2) {
                                fd.status = DOWNLOAD_STATUS_3;
                                //重发
                                AutoReplyService.instance.sendReplyImmediately(fd.original, isNeedImme(fd.original));
                            }
                            sleep(3000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        fileDownloadThread.start();
    }

    private static void doDownloadFile(final FileDownload fd) {
        ArrayList<String> urls = fd.urls;
        ArrayList<String> filepaths = fd.filepaths;
        int count = urls.size();
        for (int i = 0; i < count; i++) {
            String url = urls.get(i);
            Log.d("test", "doDownloadFile url = " + url);
            downloadingUrls.add(url);
            final String savedFilePath = filepaths.get(i);
            org.xutils.http.RequestParams params = new org.xutils.http.RequestParams(url);
            params.setSaveFilePath(savedFilePath);
            params.setAutoRename(false);
            params.setReadTimeout(60000);
            params.setConnectTimeout(60000);
            params.setMaxRetryCount(5);
            params.setAutoResume(true);
            x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    fd.successUrl++;
                    Log.d("test", "onSuccess");
                    if (fd.successUrl == fd.urls.size()) {
                        fd.status = DOWNLOAD_STATUS_2;
                    }
                    downloadingUrls.remove(fd.urls.get(0));
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.d("test", "onError");
                    fd.status = DOWNLOAD_STATUS_0;
                    new File(savedFilePath).delete();
                    downloadingUrls.remove(fd.urls.get(0));
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    Log.d("test", "onCancelled");
                    fd.status = DOWNLOAD_STATUS_0;
                    new File(savedFilePath).delete();
                    downloadingUrls.remove(fd.urls.get(0));
                }

                @Override
                public void onFinished() {
                    Log.d("test", "onFinished");
                }
            });
        }
    }

    private static boolean needPreDownload(String msg) {
        try {
            //需要预先下载文件type 50
            if (msg.contains("\"returnType\":50") || msg.contains("\"type\":50")) {
                JSONObject msgO = new JSONObject(msg);
                String url = null;
                String filepath = null;
                if (msg.contains("\"returnType\":50")) {
                    JSONObject o = msgO.optJSONArray("returnMessage").optJSONObject(0);
                    url = o.optString("content");
                    filepath = DOWNLOAD + o.optString("fileName");
                } else if (msg.contains("\"type\":50")) {
                    if (msg.contains("groupSendBatchMessageCmd")) {
                        JSONObject messageO = msgO.optJSONArray("messages").optJSONObject(0);
                        JSONObject contentO = messageO.optJSONObject("content");
                        url = contentO.optString("url");
                        filepath = DOWNLOAD + contentO.optString("fileName");
                    } else {
                        String message = msgO.optString("message");
                        JSONObject messageO = new JSONObject(message);
                        url = messageO.optString("content");
                        filepath = DOWNLOAD + messageO.optString("fileName");
                    }
                }
                FileDownload fd = new FileDownload();
                fd.urls.add(url);
                fd.filepaths.add(filepath);
                fd.original = msg;
                Log.d("test", "add a fd = " + fd.toString());
                downloads.add(fd);
                return true;
            }
            //预先下载朋友圈的图文、网文
            if (msg.contains(SEND_FRIEND_CIRCLE_CMD)) {
                JSONObject msgO = new JSONObject(msg);
                JSONObject contentO = msgO.optJSONObject("content");
                String imageUrl = contentO.optString("imgUrl");

                String[] imageArray = imageUrl.replace("[", "").replace("]", "").split(",");
                if (imageArray.length == 0) {
                    return false;
                }
                FileDownload fd = new FileDownload();
                for (String anImageArray : imageArray) {
                    String image = anImageArray.replace("\"", "");
                    fd.urls.add(image);
                    fd.filepaths.add(KWApplication.DOWNLOAD + Utils.getMD5(image) + ".jpg");//url做md5
                    fd.original = msg;
                }
                downloads.add(fd);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //需要插队的命令
    private static boolean isNeedImme(String msg) {
        boolean imme = false;
        try {
            //踢人
            if (msg.contains(TICK_PERSON_GROUP_CMD)) {
                imme = true;
            }
            //所有朋友圈
            if (msg.contains(SEND_FRIEND_CIRCLE_CMD)) {
                imme = true;
            }
            //定时发朋友圈
            JSONObject o = new JSONObject(msg);
            if (o.optBoolean("schedule")) {
                imme = true;
            }
            //定时修改群公告
            if (msg.contains(UPDATE_GROUP_NOTICE_CMD) && !TextUtils.isEmpty(o.optString("sendTime"))) {
                imme = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("test", "isNeedImme = " + imme);
        return imme;
    }

    public static boolean hbReply = false;

    private static boolean isHeartBeatReply(String msg) {
        try {
            JSONArray returnMessage = new JSONObject(msg).optJSONArray("returnMessage");
            if (returnMessage.length() > 0) {
                JSONObject o = returnMessage.getJSONObject(0);
                String content = o.optString("content");
                int returnType = o.optInt("returnType");
                if (content.equals("OK") && returnType == 1) {
                    Log.d("test", "心跳回复");
                    hbReply = true;
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean isEffectiveDate(String startStr, String endStr) {
        try {
            String format = "HH:mm:ss";

            String dateStr = new SimpleDateFormat(format).format(new Date());
            Date nowTime = new SimpleDateFormat(format).parse(dateStr);

            Date startTime = new SimpleDateFormat(format).parse(startStr);
            Date endTime = new SimpleDateFormat(format).parse(endStr);

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

    private static ArrayList<Friend> friends;

    public static void uploadFriend(final Activity c, final ArrayList<Friend> friends) {
        Utils.friends = friends;
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(60000);
                    client.addHeader("x-auth-token", xtoken);

                    String url = clientUrl + "/freind/all";
                    Log.d("test", "freind url = " + url);

                    String transfer = c.getSharedPreferences("transfer", 0).getString("transfer", DEFAULT_TRANSFER);
                    JSONArray param = new JSONArray();
                    for (Friend f : friends) {
                        //过滤转发使者1127不再过滤
                        /*if (f.wxNo.equals(transfer)) {
                            continue;
                        }*/
                        String remark = TextUtils.isEmpty(f.remark) ? f.nickname : f.remark;
                        //remark = Utils.filterEmoji(remark);
                        JSONObject o = new JSONObject();
                        o.put("nickname", f.nickname);//昵称
                        o.put("remark", remark);//备注
                        o.put("wxId", f.wxId);//微信id
                        o.put("wxNo", TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo);//微信号
                        o.put("robotId", robotId);
                        param.put(o);
                    }
                    Log.d("test", "freind param count = " + param.length());
                    Log.d("test", "freind param = " + param.toString());
                    FileUtils.saveFile(friends.toString(), "friends.txt");
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "freind onSuccess = " + ret);
                            Utils.friends.clear();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "freind onFailure = " + s);
                            check301(c, s, "uploadFriend");
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void updateFriendRemark(final Activity c, final ArrayList<Friend> friends) {
        Utils.friends = friends;
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(60000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/freind/remark";
                    Log.d("test", "updateFriendRemark url = " + url);
                    JSONArray param = new JSONArray();
                    for (Friend f : friends) {
                        String remark = TextUtils.isEmpty(f.remark) ? f.nickname : f.remark;
                        //remark = Utils.filterEmoji(remark);
                        JSONObject o = new JSONObject();
                        o.put("nickname", f.nickname);//昵称
                        o.put("remark", remark);//备注
                        o.put("wxId", f.wxId);//微信id
                        o.put("wxNo", TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo);//微信号
                        o.put("robotId", robotId);
                        param.put(o);
                    }
                    Log.d("test", "updateFriendRemark param = " + param.toString());
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    client.put(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "updateFriendRemark onSuccess = " + ret);
                            Utils.friends.clear();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "updateFriendRemark onFailure = " + s);
                            check301(c, s, "updateFriendRemark");
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static String filterEmoji(String str) {
        String pattern = "[\ud800\udc00-\udbff\udfff\ud800-\udfff]";
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date dt = sdf.parse(currentTime);
        long lTime = dt.getTime();
        return lTime;
    }

    public static String longToDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        java.util.Date date = new Date(time);
        String str = sdf.format(date);
        return str;
    }

    public static void updateUserStatus(final Context c, final String phone, final String remark, final int status) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String url = clientUrl + "/users/status";
                    Log.d("test", "url = " + url);
                    Log.d("test", "phone = " + phone + " status = " + status);
                    HttpPut httpRequest = new HttpPut(url);
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    httpRequest.addHeader("x-auth-token", xtoken);
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
    }

    private static int status;

    //status： 1机器人正常 2机器人异常 /*3微信正常 4微信异常*/
    public synchronized static void updateRobotStatus(final MainActivity act, final int status) {
        Utils.status = status;
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
                    client.setMaxRetriesAndTimeout(3, 10000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/robot/" + robotId;
                    Log.d("test", "updateRobotStatus url = " + url);
                    com.loopj.android.http.RequestParams param = new com.loopj.android.http.RequestParams();
                    param.put("status", status);
                    Log.d("test", "param = " + param.toString());
                    client.put(act, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "updateRobotStatus onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "updateRobotStatus onFailure = " + s);
                            check301(act, s, "updateRobotStatus");
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    private static void check301(final Context c, String result, final String type) {
        if (TextUtils.isEmpty(result)) {
            return;
        }
        try {
            int statusCode = new JSONObject(result).optInt("statusCode");
            if (statusCode != 301) {
                return;
            }
            Log.d("test", "301 happen");
            String username = c.getSharedPreferences("kiway", 0).getString("username", "");
            String password = c.getSharedPreferences("kiway", 0).getString("password", "");
            String name = c.getSharedPreferences("kiway", 0).getString("name", "");
            String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");
            String tenantId = c.getSharedPreferences("kiway", 0).getString("tenantId", "");
            boolean newLogin = c.getSharedPreferences("kiway", 0).getBoolean("newLogin", false);
            if (newLogin) {
                doNewLogin(c, name, wxNo, tenantId, new MyListener() {
                    @Override
                    public void onResult(boolean success) {
                        if (success) {
                            dealWithloginSuccess(c, type);
                        }
                    }
                });
            } else {
                doLogin(c, username, password, name, wxNo, new MyListener() {
                    @Override
                    public void onResult(boolean success) {
                        if (success) {
                            dealWithloginSuccess(c, type);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "check301 exception = " + e.toString());
        }
    }

    private static void dealWithloginSuccess(Context c, String type) {
        if (TextUtils.isEmpty(type)) {
            return;
        }
        if (type.equals("installationPush")) {
            installationPush(c);
            return;
        }
        if (type.equals("uploadFriend")) {
            uploadFriend(MainActivity.instance, Utils.friends);
            return;
        }
        if (type.equals("updateFriendRemark")) {
            updateFriendRemark(MainActivity.instance, Utils.friends);
            return;
        }
        if (type.equals("updateRobotStatus")) {
            updateRobotStatus(MainActivity.instance, Utils.status);
            return;
        }
        if (type.equals("uploadGroup")) {
            uploadGroup(MainActivity.instance, Utils.groups, Utils.listener);
            return;
        }
        if (type.equals("uploadGroupPeoples")) {
            uploadGroupPeoples(MainActivity.instance, Utils.groups);
            return;
        }
        if (type.equals("blackfile")) {
            blackfile(MainActivity.instance);
            return;
        }
        if (type.equals("getConsumers")) {
            getConsumers(c, Utils.listener);
            return;
        }
        if (type.equals("resultReact")) {
            resultReact(c, msg, Utils.listener);
            return;
        }
        if (type.equals("getOmissionMsg")) {
            getOmissionMsg(c, Utils.missingInt, Utils.listener);
            return;
        }
        if (type.equals("getLastMsgIndex")) {
            getLastMsgIndex(c, Utils.listener);
            return;
        }
        if (type.equals("getLastMsgIndex2")) {
            getLastMsgIndex2(c, Utils.listener2);
            return;
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

    public static String password = null;

    public static String initDbPassword(Context c) {
        if (!TextUtils.isEmpty(password)) {
            System.out.println("password = " + password);
            return password;
        }
        execRootCmdSilent("chmod -R 777 " + WX_ROOT_PATH);
        execRootCmdSilent("chmod  777 /data/data/com.tencent.mm/shared_prefs/auth_info_key_prefs.xml");
        String uin = initCurrWxUin();
        TelephonyManager phone = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = phone.getDeviceId();
        if (TextUtils.isEmpty(imei) || TextUtils.isEmpty(uin)) {
            LogUtil.e("初始化数据库密码失败：imei或uid为空");
            return null;
        }
        String md5 = getMD5(imei + uin);
        password = md5.substring(0, 7).toLowerCase();
        System.out.println("password = " + password);
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

    private static String wechatCurrentUserMD5 = null;

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

        wechatCurrentUserMD5 = latestFile.getAbsolutePath().replace("/data/data/com.tencent.mm/MicroMsg/", "").replace("/" + dbName, "");

        String copyFilePath = ROOT + saveDbName;
        if (new File(copyFilePath).exists()) {
            new File(copyFilePath).delete();
        }
        copyFile(latestFile.getAbsolutePath(), copyFilePath);
        return new File(copyFilePath);
    }

    private static ArrayList<File> findDBFile(String dbName) {
        ArrayList<File> dbs = new ArrayList<>();
        File wxDataDir = new File(WX_DB_DIR_PATH);
        doFindDBFile(wxDataDir, dbName, dbs);
        return dbs;
    }

    private static void doFindDBFile(File file, String fileName, ArrayList<File> dbs) {
        if (TextUtils.isEmpty(wechatCurrentUserMD5)) {
            if (file.isDirectory()) {
                if (file.getName().equals("MicroMsg") || file.getName().length() == 32) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File childFile : files) {
                            doFindDBFile(childFile, fileName, dbs);
                        }
                    }
                }
            } else {
                if (fileName.equals(file.getName())) {
                    dbs.add(file);
                }
            }
        } else {
            String targetPath = file.getAbsolutePath() + "/" + wechatCurrentUserMD5 + "/" + fileName;
            dbs.add(new File(targetPath));
        }
    }

    public static ArrayList<Friend> doGetFriends(Context c, File dbFile, String password) {
        ArrayList<Friend> friends = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");//me
            cursor = db.rawQuery("select username ,alias,nickname, conRemark,type from rcontact where username not like 'gh_%' and username not like '%@chatroom' and  verifyFlag<>24 and verifyFlag<>29 and verifyFlag<>56 and type<>33 and type<>70 and verifyFlag=0 and type<>4 and type<>0 and type<>8 and showHead<>43 and type<>65536 and type<> 11 and type <> 2 and type<> 512 and type<> 9 and type<> 520", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //wxID
                String alias = cursor.getString(cursor.getColumnIndex("alias"));        //wxNo
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //nickname
                String conRemark = cursor.getString(cursor.getColumnIndex("conRemark"));//remark
                if (wxNo.equals(alias)) {
                    //排除掉自己
                    c.getSharedPreferences("kiway", 0).edit().putString("wxId", username).commit();
                } else if (username.equals("filehelper")) {
                    Log.d("test", "过滤的username = " + username + " alias = " + alias);
                } else {
                    friends.add(new Friend(nickname, conRemark, username, alias));
                }
            }
            Log.d("test", "friends = " + friends);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return friends;
    }

    public static Friend doGetOneFriendByWxId(Context c, File dbFile, String password, String wxId) {
        Log.d("test", "doGetOneFriendByWxId");
        Friend f = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            String sql = "select username ,alias,nickname, conRemark from rcontact where (username not like '%@chatroom') and  (username = '" + wxId + "')";
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //wxID
                String alias = cursor.getString(cursor.getColumnIndex("alias"));        //wxNo
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //nickname
                String conRemark = cursor.getString(cursor.getColumnIndex("conRemark"));//remark
                f = new Friend(nickname, conRemark, username, alias);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static Friend doGetOneFriendByWxNo(Context c, File dbFile, String password, String wxNo) {
        Log.d("test", "doGetOneFriendByWxNo");
        Friend f = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            cursor = db.rawQuery("select username ,alias,nickname, conRemark from rcontact where (username not like '%@chatroom') and  (alias = '" + wxNo + "')", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //wxID
                String alias = cursor.getString(cursor.getColumnIndex("alias"));        //wxNo
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //nickname
                String conRemark = cursor.getString(cursor.getColumnIndex("conRemark"));//remark
                f = new Friend(nickname, conRemark, username, alias);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static Friend doGetOneFriendByNickNameOrRemark(Context c, File dbFile, String password, String name) {
        Log.d("test", "doGetOneFriendByNickNameOrRemark");
        Friend f = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            cursor = db.rawQuery("select username ,alias,nickname, conRemark from rcontact where  (username not like '%@chatroom') and  (nickname = '" + name + "' or  conRemark = '" + name + "')", null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //wxID
                String alias = cursor.getString(cursor.getColumnIndex("alias"));        //wxNo
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //nickname
                String conRemark = cursor.getString(cursor.getColumnIndex("conRemark"));//remark
                f = new Friend(nickname, conRemark, username, alias);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static ArrayList<Message> doGetMessagesIn2Hour(Context c, File dbFile, String password, String sql) {
        Log.d("test", "doGetMessages");

        ArrayList<Message> messages = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            long current = System.currentTimeMillis();
            long before1hour = current - 2 * 60 * 60 * 1000;
            if (sql == null) {
                sql = "select message.msgId , message.type , message.createTime  , message.talker ,  message.imgPath ,  rcontact.nickname ,  rcontact.conRemark, message.content from message left JOIN rcontact on message.talker = rcontact.username where message.isSend = 0 and (message.type = 1 or message.type = 49 or message.type = 3) and message.createTime > " + before1hour;
            }
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                long createTime = cursor.getLong(cursor.getColumnIndex("createTime"));
                String talker = cursor.getString(cursor.getColumnIndex("talker"));
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                String conRemark = cursor.getString(cursor.getColumnIndex("conRemark"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String imgPath = cursor.getString(cursor.getColumnIndex("imgPath"));

                Message m = new Message();
                m.type = type;
                m.createTime = createTime;
                m.talker = talker;
                if (talker.endsWith("@chatroom")) {
                    //群聊remark=talker
                    m.remark = talker;
                } else {
                    String remark = TextUtils.isEmpty(conRemark) ? nickname : conRemark;
                    remark = Utils.filterEmoji(remark);
                    m.remark = remark;
                }
                m.content = content;
                m.imgPath = imgPath;
                messages.add(m);
            }
            Log.d("test", "messages = " + messages);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messages;
    }

    public static ArrayList<Group> doGetGroups(Context c, File dbFile, String password, boolean needowner) {
        Log.d("test", "doGetGroups");
        ArrayList<Group> groups = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            //type=2未保存的群,3是已经保存的 1129过滤type2050且人数只有一个的群
            String sql = "";
            if (needowner) {
                sql = "select username,nickname,type  , roomowner , (select alias from rContact where username = roomowner) as  masterWxNo from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and type != 2050  and memberlist not like '%\\;%'";//and (type = 2 or type = 3 or type = 0)
            } else {
                sql = "select username,nickname,type  from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and type != 2050  and memberlist not like '%\\;% "; //and (type = 2 or type = 3 or type = 0)
            }
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //clientGroupId
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //groupName
                int type = cursor.getInt(cursor.getColumnIndex("type"));  //type
                if (needowner) {
                    String master = cursor.getString(cursor.getColumnIndex("roomowner"));//群主ID
                    String masterWxNo = cursor.getString(cursor.getColumnIndex("masterWxNo"));//群主wxNo
                    groups.add(new Group(username, nickname, type, master, masterWxNo));
                } else {
                    groups.add(new Group(username, nickname, type));
                }
            }
            Log.d("test", "groups = " + groups);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return groups;
    }

    public static Group doGetOneGroupByGroupId(Context c, File dbFile, String password, String clientGroupId, boolean needOwner) {
        Log.d("test", "doGetOneGroupByGroupId");
        Group group = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            //type=2未保存的群,3是已经保存的
            String sql = "";
            if (needOwner) {
                sql = "select username,nickname,type  , roomowner  , (select alias from rContact where username = roomowner) as  masterWxNo from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and username = '" + clientGroupId + "'"; //and (type = 2 or type = 3 or type = 0)
            } else {
                sql = "select username,nickname,type   from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and username = '" + clientGroupId + "'"; //and (type = 2 or type = 3 or type = 0)
            }
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //clientGroupId
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //groupName
                int type = cursor.getInt(cursor.getColumnIndex("type"));  //type
                if (needOwner) {
                    String master = cursor.getString(cursor.getColumnIndex("roomowner"));//群主ID
                    String masterWxNo = cursor.getString(cursor.getColumnIndex("masterWxNo"));//群主wxNo
                    group = new Group(username, nickname, type, master, masterWxNo);
                } else {
                    group = new Group(username, nickname);
                }
            }
            Log.d("test", "group = " + group);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return group;
    }

    public static Group doGetOneGroupByGroupName(Context c, File dbFile, String password, String name, boolean needOwner) {
        Log.d("test", "doGetOneGroupByGroupName");
        Group group = null;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            //type=2未保存的群,3是已经保存的
            String sql = "";
            if (needOwner) {
                sql = "select username,nickname,type  , roomowner , (select alias from rContact where username = roomowner) as  masterWxNo  from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and nickname = '" + name + "'"; //and (type = 2 or type = 3 or type = 0)
            } else {
                sql = "select username,nickname,type from rcontact  left JOIN chatroom on  rcontact.username = chatroom.chatroomname where username like '%@chatroom' and nickname = '" + name + "'"; //and (type = 2 or type = 3 or type = 0)
            }
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));  //clientGroupId
                String nickname = cursor.getString(cursor.getColumnIndex("nickname"));  //groupName
                int type = cursor.getInt(cursor.getColumnIndex("type"));  //type
                if (needOwner) {
                    String master = cursor.getString(cursor.getColumnIndex("roomowner"));//群主ID
                    String masterWxNo = cursor.getString(cursor.getColumnIndex("masterWxNo"));//群主wxNo
                    group = new Group(username, nickname, type, master, masterWxNo);
                } else {
                    group = new Group(username, nickname);
                }
            }
            Log.d("test", "group = " + group);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return group;
    }

    private static String getWxNoByWxId(Context c, String master) {
        Log.d("test", "getWxNoByWxId");
        String wxNo = "";
        String password = initDbPassword(c);
        File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
        Friend f = doGetOneFriendByWxId(c, dbFile, password, master);
        if (f != null) {
            wxNo = TextUtils.isEmpty(f.wxNo) ? f.wxId : f.wxNo;
        }
        return wxNo;
    }

    public static void doGetPeopleInGroups(Context c, File dbFile, String password, ArrayList<Group> groups) {
        Log.d("test", "doGetPeopleInGroups");
        String clientGroupIds = "";
        int count = groups.size();
        for (int i = 0; i < count; i++) {
            Group g = groups.get(i);
            if (i == count - 1) {
                String temp = "'" + g.clientGroupId + "'";
                clientGroupIds += temp;
            } else {
                String temp = "'" + g.clientGroupId + "' ,";
                clientGroupIds += temp;
            }
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            String sql = "select chatroomname , memberlist ,  displayname  from chatroom where chatroomname in ( " + clientGroupIds + " ) ";
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String chatroomname = cursor.getString(cursor.getColumnIndex("chatroomname"));
                String memberlist = cursor.getString(cursor.getColumnIndex("memberlist"));
                String displayname = cursor.getString(cursor.getColumnIndex("displayname"));
                String[] temp1 = memberlist.split(";");
                String[] temp2 = displayname.split("、");
                ArrayList<GroupPeople> peoples = new ArrayList<>();
                for (int i = 0; i < temp1.length; i++) {
                    peoples.add(new GroupPeople(temp1[i], filterEmoji(temp2[i])));
                }
                Log.d("test", "peples = " + peoples);
                for (Group g : groups) {
                    if (g.clientGroupId.equals(chatroomname)) {
                        g.peoples = peoples;
                        break;
                    }
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<GroupPeople> doGetPeopleInOneGroup(Context c, File dbFile, String password, String clientGroupId) {
        Log.d("test", "doGetGroups clientGroupId = " + clientGroupId);
        ArrayList<GroupPeople> peoples = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, password);
            String sql = "select memberlist ,  displayname   from chatroom where chatroomname = '" + clientGroupId + "'";
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String memberlist = cursor.getString(cursor.getColumnIndex("memberlist"));
                String displayname = cursor.getString(cursor.getColumnIndex("displayname"));
                String[] temp1 = memberlist.split(";");
                String[] temp2 = displayname.split("、");
                for (int i = 0; i < temp1.length; i++) {
                    peoples.add(new GroupPeople(temp1[i], filterEmoji(temp2[i])));
                }
                Log.d("test", "peples = " + peoples);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        return SQLiteDatabase.openDatabase(dbFile.getAbsolutePath(), password, null, OPEN_READONLY, hook);
    }

    public static ArrayList<String> doGetMoments(Context c, File dbFile) {
        Log.d("test", "doGetMoments");
        ArrayList<String> moments = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = openWechatDB(c, dbFile, null);
            String sql = "select  *  from SnsInfo";
            Log.d("test", "sql = " + sql);
            cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                byte[] content = cursor.getBlob(cursor.getColumnIndex("content"));
                Log.d("test", "username = " + userName);
                Log.d("test", "content = " + new String(content));
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (cursor != null) {
                    cursor.close();
                }
                if (db != null) {
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                    if (!new File(DOWNLOAD).exists()) {
                        new File(DOWNLOAD).mkdirs();
                    }
                    File file = new File(DOWNLOAD + fileName);
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
                if (!new File(DOWNLOAD).exists()) {
                    new File(DOWNLOAD).mkdirs();
                }
                final String savedFilePath = DOWNLOAD + fileName;
                File file = new File(savedFilePath);
                if (file.exists()) {
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

    public static void addFilter(Context c, Filter filter) {
        if (isInfilters(c, filter.name)) {
            return;
        }
        new MyDBHelper(c).addFilter(filter);
    }

    public static String decrypt(String hex) {
        DES des = new DES("kiwayWxReply".getBytes());
        //String hex = des.encryptHex("kiway");
        //Log.d("test", "hex = " + hex);
        String ret = des.decryptStr(hex);
        Log.d("test", "ret = " + ret);
        return ret;
    }

    private static ArrayList<Group> groups;
    private static MyListener listener;
    private static MyListener2 listener2;

    public static void uploadGroup(final MainActivity act, final ArrayList<Group> groups, final MyListener myListener) {
        Utils.groups = groups;
        Utils.listener = myListener;
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String robotId = act.getSharedPreferences("kiway", 0).getString("robotId", "");
                String wxNo = act.getSharedPreferences("kiway", 0).getString("wxNo", "");
                String xtoken = act.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setMaxRetriesAndTimeout(3, 10000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/groups/name/change";
                    Log.d("test", "groups/name/change url = " + url);
                    JSONArray param = new JSONArray();
                    for (Group g : groups) {
                        JSONObject o = new JSONObject();
                        o.put("clientGroupId", g.clientGroupId);
                        o.put("name", TextUtils.isEmpty(g.groupName) ? "未命名群聊" : g.groupName);
                        o.put("robotId", robotId);
                        o.put("userId", wxNo);
                        o.put("master", g.master);
                        o.put("masterWxNo", g.masterWxNo);
                        int saved = 0;
                        if (g.type == 3 || g.type == 2051) {
                            saved = 1;
                        }
                        o.put("saved", saved);
                        param.put(o);
                    }
                    Log.d("test", "groups/name/change param = " + param.toString());
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    client.put(act, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "groups/name/change onSuccess = " + ret);
                            myListener.onResult(true);
                            Utils.groups.clear();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "groups/name/change onFailure = " + s);
                            myListener.onResult(false);
                            check301(act, s, "uploadGroup");
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void uploadGroupPeoples(final MainActivity act, final ArrayList<Group> groups) {
        Utils.groups = groups;
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String robotId = act.getSharedPreferences("kiway", 0).getString("robotId", "");
                String xtoken = act.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(60000);
                    client.addHeader("x-auth-token", xtoken);
                    String url = clientUrl + "/groupMembersRel";
                    Log.d("test", "groupMembersRel url = " + url);

                    JSONArray param = new JSONArray();
                    for (Group g : groups) {
                        JSONObject o = new JSONObject();
                        JSONArray members = new JSONArray();
                        for (GroupPeople gp : g.peoples) {
                            JSONObject temp = new JSONObject();
                            temp.put("friendId", System.currentTimeMillis() + "" + new Random().nextInt(1000));
                            temp.put("nickName", gp.displayname);
                            temp.put("robotId", robotId);
                            temp.put("wxId", gp.wxId);
                            members.put(temp);
                        }
                        if (members.length() == 0) {
                            continue;
                        }
                        o.put("members", members);
                        o.put("clientGroupId", g.clientGroupId);
                        param.put(o);
                    }
                    if (param.length() == 0) {
                        return;
                    }
                    FileUtils.saveFile(param.toString(), "groupMembersRel.txt");
                    Log.d("test", "groupMembersRel param = " + param.toString());
                    StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
                    client.post(act, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "groupMembersRel onSuccess = " + ret);
                            Utils.groups.clear();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "groupMembersRel onFailure = " + s);
                            check301(act, s, "uploadGroupPeoples");
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void doLogin(final Context c, final String username, final String password, final String name, final String wxNo, final MyListener myListener) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(3, 10000);
            String url = clientUrl + "/robot/login";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("imei", wxNo);
            param.put("userName", username);
            param.put("password", password);
            param.put("name", name);
            Log.d("test", "param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "login onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        int statusCode = o.optInt("statusCode");
                        JSONObject data = o.getJSONObject("data");
                        String token = data.optString("token");
                        String robotId = data.optString("robotId");
                        String areaCode = data.optString("areaCode");
                        Log.d("test", "login get token = " + token);
                        if (statusCode == 200) {
                            c.getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("username", username).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("name", name).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("wxNo", wxNo).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("robotId", robotId).commit();
                            c.getSharedPreferences("kiway", 0).edit().putString("areaCode", areaCode).commit();
                            myListener.onResult(true);
                        } else {
                            myListener.onResult(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        myListener.onResult(false);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + s);
                    myListener.onResult(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            myListener.onResult(false);
        }
    }

    public static void doNewLogin(final Context c, final String name, final String wxNo, String tenantId, final MyListener myListener) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(3, 10000);
            String url = clientUrl + "/robot/newLogin";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("wxNo", wxNo);
            param.put("name", name);
            param.put("tenantId", tenantId);
            Log.d("test", "param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "login onSuccess = " + ret);
                    //登录成功
                    try {
                        JSONObject o = new JSONObject(ret);
                        int statusCode = o.optInt("statusCode");
                        if (statusCode != 200) {
                            myListener.onResult(false);
                            return;
                        }
                        JSONObject data = o.optJSONObject("data");
                        String token = data.optString("token");
                        String areaCode = data.optString("areaCode");
                        String robotId = data.optString("robotId");
                        String userName = data.optString("userName");
                        String password = Utils.decrypt(data.optString("password"));
                        Log.d("test", "password解密 = " + password);
                        c.getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("username", userName).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("robotId", robotId).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("areaCode", areaCode).commit();
                        myListener.onResult(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + s);
                    //登录失败原因
                    myListener.onResult(false);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            myListener.onResult(false);
        }
    }

    public static boolean isStartWithNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str.charAt(0) + "");
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String leftChinese(String str) {
        String reg = "[^\u4e00-\u9fa5A-Za-z0-9\\s]";
        str = str.replaceAll(reg, "");
        return str;
    }

    public static String getRandomChineseName() {
        String name = "";
        for (int i = 0; i < 5; i++) {
            char temp = (char) (0x4e00 + (int) (Math.random() * (0x9fa5 - 0x4e00 + 1)));
            name += temp;
        }
        return name;
    }

    public static String getNewRemark(Context c, String current) {
        if (TextUtils.isEmpty(current)) {
            current = Utils.getRandomChineseName();
        }
        String leftChinese = Utils.leftChinese(current);

        if (!Utils.isStartWithNumber(leftChinese)) {
            leftChinese = Utils.getParentRemark(c, 1) + " " + leftChinese;
        }
        //zhengkang add 0925
        if (leftChinese.length() > 10) {
            leftChinese = leftChinese.substring(0, 10);
        }
        //zhengkang add 1120
        leftChinese = leftChinese.trim();

        return leftChinese;
    }

    public static String getDisplayNameFromGroup(Context c, String clientGroupId, String wxId) {
        final String password = initDbPassword(c);
        final File dbFile = getWxDBFile("EnMicroMsg.db", "getAllGroups" + new Random().nextInt(9999) + ".db");
        ArrayList<GroupPeople> peoples = doGetPeopleInOneGroup(c, dbFile, password, clientGroupId);
        for (GroupPeople gp : peoples) {
            if (gp.wxId.equals(wxId)) {
                return gp.displayname;
            }
        }
        return "";
    }

    public static void getAuthorRemarkByAuthorId(Context c, ArrayList<SnsInfo.Comment> comments) {
        String password = initDbPassword(c);
        File dbFile = getWxDBFile("EnMicroMsg.db", "getAllFriends" + new Random().nextInt(9999) + ".db");
        for (SnsInfo.Comment comment : comments) {
            Friend f = doGetOneFriendByWxId(c, dbFile, password, comment.authorId);
            if (f == null) {
                continue;
            }
            comment.authorName = TextUtils.isEmpty(f.remark) ? f.nickname : f.remark;
        }
    }

    public static void closeMQ() {
        Log.d("test", "closeMQ");
        new Thread() {
            @Override
            public void run() {
                try {
                    if (channels != null) {
                        for (Channel channel : channels) {
                            channel.abort();
                        }
                        channels = null;
                    }
                    if (rabbitMQUtils != null) {
                        rabbitMQUtils.close();
                        rabbitMQUtils = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void blackfile(final Context c) {
        try {
            String name = c.getSharedPreferences("kiway", 0).getString("name", "");
            if (TextUtils.isEmpty(name)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            client.setMaxRetriesAndTimeout(3, 10000);
            RequestParams param = new RequestParams();
            param.put("name", name);
            String url = "http://robot.kiway.cn:8181/wechat/black/file";
            Log.d("test", "blackfile url = " + url);
            Log.d("test", "blackfile param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "black file onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "black file onFailure = " + s);
                    check301(c, s, "blackfile");
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }


    public static void getLastMsgIndex2(final Context c, final MyListener2 myListener) {
        if (MainActivity.instance == null) {
            return;
        }
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.listener2 = myListener;
                try {
                    final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    if (TextUtils.isEmpty(robotId)) {
                        Log.d("test", "robotId is null");
                        return;
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    Log.d("test", "x-auth-token = " + xtoken);
                    client.addHeader("x-auth-token", xtoken);

                    client.setMaxRetriesAndTimeout(3, 10000);
                    String url = clientUrl + "/sendContent/omission/last?robotId=" + robotId;
                    Log.d("test", "url = " + url);
                    client.get(c, url, new TextHttpResponseHandler() {

                        @Override
                        public void onSuccess(int arg0, Header[] arg1, String ret) {
                            Log.d("test", "getLastMsgIndex2 onSuccess ret = " + ret);
                            int serverIndex = 0;
                            try {
                                serverIndex = new JSONObject(ret).optJSONObject("data").optInt("indexs");
                                Log.d("test", "serverIndex = " + serverIndex);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            myListener.onResult(serverIndex);
                        }

                        @Override
                        public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                            Log.d("test", "getLastMsgIndex2 onFailure ret = " + s);
                            check301(c, s, "getLastMsgIndex2");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "getLastMsgIndex2 exception = " + e.toString());
                }
            }
        });
    }

    public static void getLastMsgIndex(final Context c, final MyListener myListener) {
        if (MainActivity.instance == null) {
            return;
        }
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.listener = myListener;
                try {
                    final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    if (TextUtils.isEmpty(robotId)) {
                        Log.d("test", "robotId is null");
                        return;
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    Log.d("test", "x-auth-token = " + xtoken);
                    client.addHeader("x-auth-token", xtoken);

                    client.setMaxRetriesAndTimeout(3, 10000);
                    String url = clientUrl + "/sendContent/omission/last?robotId=" + robotId;
                    Log.d("test", "url = " + url);
                    client.get(c, url, new TextHttpResponseHandler() {

                        @Override
                        public void onSuccess(int arg0, Header[] arg1, String ret) {
                            Log.d("test", "getLastMsgIndex onSuccess ret = " + ret);
                            try {
                                int serverIndex = new JSONObject(ret).optJSONObject("data").optInt("indexs");
                                Log.d("test", "serverIndex = " + serverIndex);
                                compareWithLocalDB(c, serverIndex);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                            Log.d("test", "getLastMsgIndex onFailure ret = " + s);
                            check301(c, s, "getLastMsgIndex");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "getLastMsgIndex exception = " + e.toString());
                }
            }
        });
    }

    private static void compareWithLocalDB(Context c, int serverIndex) {
        if (serverIndex == 0) {
            return;
        }
        ArrayList<ServerMsg> localSms = new MyDBHelper(c).getAllServerMsg(0);
        int count = localSms.size();
        int[] temp = new int[localSms.size()];
        for (int i = 0; i < count; i++) {
            ServerMsg sm = localSms.get(i);
            temp[i] = sm.index;
        }

        int start = c.getSharedPreferences("start", 0).getInt("start", 0);
        Log.d("test", "get start = " + start);

        ArrayList<Integer> missing = getRemoveNums(temp, serverIndex, start);
        Log.d("test", "missing = " + missing);
        int[] missingInt = new int[missing.size()];
        for (int i = 0; i < missing.size(); i++) {
            missingInt[i] = missing.get(i);
        }
        getOmissionMsg(c, missingInt, null);
    }

    private static ArrayList<Integer> getRemoveNums(int[] src, int fullLength, int start) {
        String result = "";
        int[] toolArray = new int[fullLength + 1];
        toolArray[0] = 1;
        for (int i = 0; i < src.length; i++) {
            int num = src[i];
            toolArray[num] = 1;
        }
        for (int i = 0; i < toolArray.length; i++) {
            int num = toolArray[i];
            if (num != 1) {
                result += i + ",";
            }
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (result.length() > 0) {
            String[] splits = result.substring(0, result.length() - 1).split(",");
            for (String s : splits) {
                int a = Integer.parseInt(s);
                if (a > start) {
                    list.add(a);
                }
            }
        }
        return list;
    }

    private static int[] missingInt;

    public static void getOmissionMsg(final Context c, final int[] missingInt, final MyListener myListener) {
        if (MainActivity.instance == null) {
            return;
        }
        if (missingInt.length == 0) {
            handleMsgInDB(c);
            return;
        }
        MainActivity.instance.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Utils.listener = myListener;
                Utils.missingInt = missingInt;
                try {
                    final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
                    if (TextUtils.isEmpty(robotId)) {
                        Log.d("test", "robotId is null");
                        return;
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    Log.d("test", "x-auth-token = " + xtoken);
                    client.addHeader("x-auth-token", xtoken);
                    client.setMaxRetriesAndTimeout(3, 10000);
                    String indexString = "";
                    for (int i = 0; i < missingInt.length; i++) {
                        indexString += ("&indexs[]=" + missingInt[i]);
                    }
                    String url = clientUrl + "/sendContent/omission?robotId=" + robotId + indexString;
                    Log.d("test", "getOmissionMsg url = " + url);
                    client.get(c, url, new TextHttpResponseHandler() {

                        @Override
                        public void onSuccess(int arg0, Header[] arg1, String ret) {
                            Log.d("test", "getOmissionMsg onSuccess ret = " + ret);
                            try {
                                JSONObject obj = new JSONObject(ret);
                                JSONArray data = obj.optJSONArray("data");
                                int count = data.length();
                                for (int i = 0; i < count; i++) {
                                    JSONObject o = data.optJSONObject(i);
                                    int indexs = o.optInt("indexs");
                                    String content = o.optString("content");
                                    JSONObject contentObj = new JSONObject(content);
                                    contentObj.put("indexs", indexs);
                                    //1.保存漏掉的消息到DB
                                    getMsgIndexAndSaveDB(c, contentObj.toString(), TYPE_HTTP);
                                    handleMsgInDB(c);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                            Log.d("test", "getOmissionMsg onFailure ret = " + s);
                            check301(c, s, "getOmissionMsg");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "getOmissionMsg exception = " + e.toString());
                }
            }
        });
    }

    public synchronized static void handleMsgInDB(final Context c) {
        if (AutoReplyService.instance == null) {
            return;
        }
        if (MainActivity.instance == null) {
            return;
        }
        ArrayList<ServerMsg> sms = new MyDBHelper(c).getAllServerMsg(3);
        Log.d("test", "handleMsgInDB 需要补漏做的count = " + sms.size());
        for (final ServerMsg sm : sms) {
            Log.d("test", "sm = " + sm.toString());
            //zzx提议
            boolean out = checkIsOutofDate(sm);
            if (out) {
                Log.d("test", "cmd is outofdate");
                new MyDBHelper(c).updateServerMsgStatusByIndex(sm.index, ACTION_STATUS_3);
                continue;
            }
            int status = sm.status;
            if (status == ACTION_STATUS_0) {
                //未执行的命令
                if (!needPreDownload(sm.content)) {
                    AutoReplyService.instance.sendReplyImmediately(sm.content, isNeedImme(sm.content));
                }
                //不管是否需要预下载，状态都设置为1
                new MyDBHelper(c).updateServerMsgStatusByIndex(sm.index, ACTION_STATUS_1);
            } else if (status == ACTION_STATUS_2) {
                //补报状态3
                MainActivity.instance.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Utils.resultReact(MainActivity.instance, sm.replyContent, new MyListener() {
                            @Override
                            public void onResult(boolean success) {
                                if (success) {
                                    new MyDBHelper(c).updateServerMsgStatusByIndex(sm.index, ACTION_STATUS_3);
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    //注意：手机时间必须和服务器一致
    private static boolean checkIsOutofDate(ServerMsg sm) {
        try {
            if (sm.content.contains(SEND_FRIEND_CIRCLE_CMD)) {
                JSONObject o = new JSONObject(sm.content);
                long cmdTime = 0;
                if (o.has("scheduleTime")) {
                    cmdTime = o.optLong("scheduleTime");
                } else {
                    cmdTime = o.optLong("token");
                }
                Log.d("test", "cmdTime = " + cmdTime);
                long currentTime = System.currentTimeMillis();
                long between = 2 * 60 * 60 * 1000;
                if ((currentTime - cmdTime) > between) {
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void getConsumers(final Context c, final MyListener myListener) {
        Utils.listener = myListener;
        try {
            final String robotId = c.getSharedPreferences("kiway", 0).getString("robotId", "");
            final String wxNo = c.getSharedPreferences("kiway", 0).getString("wxNo", "");
            if (TextUtils.isEmpty(robotId)) {
                Log.d("test", "robotId is null");
                return;
            }
            if (TextUtils.isEmpty(wxNo)) {
                Log.d("test", "wxNo is null");
                return;
            }
            String topic = "kiway_wx_reply_push_" + robotId + "#" + wxNo;
            topic = URLEncoder.encode(topic);
            AsyncHttpClient client = new AsyncHttpClient();
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            Log.d("test", "x-auth-token = " + xtoken);
            client.addHeader("x-auth-token", xtoken);

            client.setMaxRetriesAndTimeout(3, 10000);
            String url = clientUrl + "/system/rabbitMq/consumers?topic=" + topic;
            Log.d("test", "url = " + url);
            client.get(c, url, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "consumer onSuccess ret = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        int count = o.optInt("data");
                        Log.d("test", "消费者个数为" + count);
                        if (count != 1) {
                            myListener.onResult(false);
                        } else {
                            myListener.onResult(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                    Log.d("test", "consumer onFailure ret = " + s);
                    check301(c, s, "getConsumers");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "consumer exception = " + e.toString());
        }
    }

    private static String msg;

    public static void resultReact(final Context c, String msg, final MyListener myListener) {
        Utils.msg = msg;
        Utils.listener = myListener;
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            client.addHeader("x-auth-token", xtoken);
            client.setMaxRetriesAndTimeout(3, 10000);
            String url = clientUrl + "/resultReact";
            Log.d("test", "resultReact url = " + url);
            RequestParams param = new RequestParams();
            param.put("msg", msg);
            Log.d("test", "resultReact param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "resultReact onSuccess ret = " + ret);
                    try {
                        JSONObject data = new JSONObject(ret);
                        if (data.optInt("statusCode") == 200) {
                            myListener.onResult(true);
                        } else {
                            myListener.onResult(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        myListener.onResult(false);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                    Log.d("test", "resultReact onFailure ret = " + s);
                    myListener.onResult(false);
                    check301(c, s, "resultReact");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "resultReact exception = " + e.toString());
            myListener.onResult(false);
        }
    }

    public static String replaceReply(String content) {
        String s = "";
        s = content.replace("<br/>", "\n").replace("<br />", "\n").replace("<br>", "\n").replace("&nbsp;", " ").replace("&nbsp", " ");//.replace("<div>", "").replace("</div>", "")
        return delHTMLTag(s);
    }

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    private static final String BR_STYLE = "<br[^>]*?>[\\s\\S]*?<\\/br>";

    private static String delHTMLTag(String htmlStr) {

        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("\n");
        return htmlStr.trim(); // 返回文本字符串
    }

    public static void keepLog(String content, String actionName, int index) {
        String fileName = "Log_" + Utils.longToDate(System.currentTimeMillis()) + "_" + actionName + "_" + index + ".txt";
        FileUtils.saveFile2(content, fileName);
    }

    //用imgPath做去重
    public static boolean checkImgUploadsExisted(String imgPath) {
        int count = imgUploads.size();
        for (int i = 0; i < count; i++) {
            ImageUpload iu = imgUploads.get(i);
            if (iu.imgPath.equals(imgPath)) {
                return true;
            }
        }
        return false;
    }

    public static void setScreenBrightness(MainActivity c) {
        //先关闭系统的亮度自动调节
        try {
            if (android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE) == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                android.provider.Settings.System.putInt(c.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE, android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        //不让屏幕全暗
        int brightness = 1;
        //设置当前activity的屏幕亮度
        WindowManager.LayoutParams lp = c.getWindow().getAttributes();
        //0到1,调整亮度暗到全亮
        lp.screenBrightness = brightness / 255.0f;
        c.getWindow().setAttributes(lp);
        android.provider.Settings.System.putInt(c.getContentResolver(), android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
    }

    public static void saveImgJobDone(String imgPath, Context c) {
        int count = imgUploads.size();
        for (int i = 0; i < count; i++) {
            ImageUpload iu = imgUploads.get(i);
            if (iu.imgPath.equals(imgPath)) {
                iu.status = UPLOAD_STATUS_2;
                doUploadImg(iu, c);
                break;
            }
        }
    }


    public static boolean isWeekend() {
        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1 || weekday == 7) {
            return true;
        }
        return false;
    }
}
