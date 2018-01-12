package cn.kiway.mdm.web;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.leon.lfilepickerlibrary.LFilePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.ViewPhotosActivity;
import cn.kiway.mdm.scoket.ScreenActivity;
import cn.kiway.mdm.scoket.db.DbUtils;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.netty.HproseChannelMapStatic;
import cn.kiway.mdm.scoket.scoket.udp.BroadCastUdp;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.scoket.utils.WifiUtils;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.Utils;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.NettyServerBootstrap.staute;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.FileUtils.EnFILEPATH;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {
    public static String userAccount = "";

    private MainActivity activity;
    private AccpectMessageHander accpectMessageHander;
    private String className;
    public static final int requsetFile = 45612;
    public static final int requsetFile2 = 45613;
    public static String accessToken;

    public JsAndroidInterface(MainActivity activity, WebView webView) {
        this.activity = activity;
        accpectMessageHander = new AccpectMessageHander(activity, webView);
    }

    @JavascriptInterface
    public void scoketOperate(String state, String className) { //1启动，0关闭
        this.className = className;
        if (state.equals("1")) {
            closeServer();
            startServer();
            Logger.log("--------------Start----------");
        } else if (state.equals("0")) {
            closeServer();
            Logger.log("--------------Stop-----------");
        }
    }

    @JavascriptInterface
    public void sendMessage(String msg, String userId) {//发送消息

    }

    @JavascriptInterface//获取服务器状态
    public String getscoketStaute() {//主动获取服务器状态 1 开启，2关闭，3异常
        return staute;
    }

    @JavascriptInterface
    public String getUserAccount() {//获取用户名密码
        Logger.log("----------------------" + userAccount);
        return userAccount;
    }

    @JavascriptInterface
    public void setScreenOrientation(String orientation) {//设置横竖
        if (orientation.equals("0"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation.equals("1"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @JavascriptInterface//获取wifi IP
    public String getWifiIp() {
        return WifiUtils.getIPAddress(activity);
    }

    @JavascriptInterface
    public void pushTheScreen() {//推屏  ios不用
        if (!Utils.isAppInstalled(activity, "com.kiway.ikv3")) {
            this.activity.toast(R.string.please_install_kiway_srceen);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.kiway.ikv3", "com.clovsoft.ik.MainActivity");
        intent.setComponent(cn);
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void multiControl(String userId) {//多屏互动
        Logger.log("multiControl::::" + userId);
        if (HproseChannelMapStatic.getChannel(userId) == null) {
            this.activity.toast(R.string.student_no_inline);
            return;
        }
        activity.startActivity(new Intent(activity, ScreenActivity.class).putExtra("clientId", userId));
    }

    @JavascriptInterface
    public String sureReponse() {
        return DbUtils.getResponse(activity);
    }

    public static int REQUEST_ORIGINAL = 2000;// 请求原图信号标识
    public static String picPath = "";

    @JavascriptInterface//拍照上传
    public void takePhoto(String token) {
        accessToken = token;
        if (!new File(EnFILEPATH).exists())
            new File(EnFILEPATH).mkdirs();
        picPath = EnFILEPATH + "/" + System.currentTimeMillis() + ".png";
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent2, REQUEST_ORIGINAL);
    }

    @JavascriptInterface
    public void chooseFile(String token) {//选择文件
        accessToken = token;
        activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
        new LFilePicker()
                .withActivity(activity)
                .withTitle(activity.getString(R.string.filepath))
                .withRequestCode(requsetFile)
                .withMutilyMode(false)
                .start();
    }

    public static String setFilePath = "";

    @JavascriptInterface
    public void sendFile(String userId, String filePath) {//发送文件

    }

    @JavascriptInterface
    public boolean getStudentStatus(String userId) { //学生是否连上了 true false
        if (HproseChannelMapStatic.getChannel(userId) == null)
            return false;
        return true;
    }

    @JavascriptInterface
    public void ViewPicture(String picUrl, String position) {
        String[] picSplit = picUrl.split(",");
        ArrayList<String> picList = new ArrayList<String>();
        for (String s : picSplit) {
            picList.add(s);
        }
        activity.startActivity(new Intent(activity, ViewPhotosActivity.class).putStringArrayListExtra("urls",
                picList).putExtra("position", Integer.parseInt(position)));
    }

    @JavascriptInterface
    public String getPlatform() {
        return "Android";
    }

    @JavascriptInterface
    public String getHost() {
        Log.d("test", "getHost is called return " + WXApplication.clientUrl);
        return WXApplication.clientUrl;
    }

    @JavascriptInterface
    public void openFile(final String downUrl, final String fileName) {
        Logger.log("::::::" + downUrl);
        Logger.log("::::::" + fileName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String folder = DOWNFILEPATH;//下载存放的文件夹
                if (new File(folder + fileName).exists()) {
                    Utils.openFile(activity, folder + fileName);
                    return;
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (activity.pd == null)
                            activity.pd = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
                        activity.pd.setMessage(activity.getString(R.string.openFile));
                        activity.pd.show();
                    }
                });
                int ret = new HttpDownload().downFile(downUrl, folder, fileName);//开始下载
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.pd.dismiss();
                        if (ret == -1) {//下载失败
                            JsAndroidInterface.this.activity.toast(R.string.downloadfie);
                        } else {//下载成功
                            Utils.openFile(activity, folder + fileName);
                        }
                    }
                });
            }
        }).start();
    }

    BroadCastUdp broadCastUdp;

    public void startServer() {

    }

    public void closeServer() {
    }

    //---------------------------------2.0版本新增的接口--------------------------------------

    @JavascriptInterface
    public void shangke(String info) {
        Log.d("test", "shangke info = " + info);
        try {
            JSONObject o = new JSONObject(info);
            String token = o.optString("token");
            String wifiIp = o.optString("wifiIp");
            String classId = o.optString("classId");
            String className = o.optString("className");
            String username = o.optString("user");
            String password = o.optString("password");
            String teacherName = o.optString("realName");
            String teacherAvatar = o.optString("avatar");
            activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("classId", classId).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("className", className).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("username", username).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("teacherName", teacherName).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("teacherAvatar", teacherAvatar).commit();
            Utils.shangke(this.activity, wifiIp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void prepareFile(String token , int type) {//选择文件-备课专用，同chooseFile，上传完成后拷贝文件到FilePath
        Log.d("test", "prepareFile is called");
        accessToken = token;
        activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
        new LFilePicker()
                .withActivity(activity)
                .withTitle(activity.getString(R.string.filepath2))
                .withRequestCode(requsetFile2)
                .withMutilyMode(false)
                .withFileFilter(new String[]{".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG"})
                .start();
    }

    @JavascriptInterface
    public void showPhoto(String param1, String param2) {
        try {
            Log.d("test", "showPhoto param1 = " + param1);
            Log.d("test", "showPhoto param2 = " + param2);
            ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
            //ViewPagerActivity.sDrawables = new String[]{"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=1897461257,834967576&fm=80&w=179&h=119&img.JPEG"};
            //ViewPagerActivity.sDrawables = new String[]{"file:///mnt/sdcard/aaa2.jpg", "file:///mnt/sdcard/aaa.jpg"};本地要有file://
            Intent intent = new Intent(this.activity, ViewPagerActivity.class);
            intent.putExtra("position", Integer.parseInt(param2));
            this.activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
