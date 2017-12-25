package cn.kiway.mdm.web;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.ViewPhotosActivity;
import cn.kiway.mdm.scoket.ScreenActivity;
import cn.kiway.mdm.scoket.db.DbUtils;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.netty.HproseChannelMapStatic;
import cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer;
import cn.kiway.mdm.scoket.scoket.udp.BroadCastUdp;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.scoket.utils.WifiUtils;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.X5WebView;

import static cn.kiway.mdm.WXApplication.url;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SHARE_FILE;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.NettyServerBootstrap.staute;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer.FilePath;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.FileUtils.EnFILEPATH;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {
    public static String userAccount = "";

    MainActivity activity;
    AccpectMessageHander accpectMessageHander;
    String className;

    public JsAndroidInterface(MainActivity activity, X5WebView webView) {
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
        Logger.log(msg + "---------------" + userId);
        try {
            if (userId.equals("all")) {
                PushServer.hproseSrv.push("ground", msg);
            } else {
                if (HproseChannelMapStatic.getChannel(userId) == null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "student no inline", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                PushServer.hproseSrv.push(userId + "owner", msg);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
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
            Toast.makeText(activity, activity.getString(R.string.please_install_kiway_srceen), Toast.LENGTH_SHORT)
                    .show();
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
            Toast.makeText(activity, activity.getString(R.string.student_no_inline), Toast.LENGTH_SHORT).show();
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

    public static final int requsetFile = 45612;
    public static final int requsetFile2 = 45613;
    public static String accessToken;

    @JavascriptInterface
    public void chooseFile(String token) {//选择文件
        accessToken = token;
        activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token);
        new LFilePicker()
                .withActivity(activity)
                .withTitle(activity.getString(R.string.filepath))
                .withRequestCode(requsetFile)
                .withMutilyMode(false)
                .withfilePath(FilePath)
                .start();
    }

    public static String setFilePath = "";

    @JavascriptInterface
    public void sendFile(String userId, String filePath) {//发送文件
        if (HproseChannelMapStatic.getChannel(userId) != null) {
            JSONObject da = new JSONObject();
            try {
                Logger.log(filePath);
                Logger.log(setFilePath.split("kiwaymdm/")[1]);
                String path = setFilePath.split("kiwaymdm/")[setFilePath.split("kiwaymdm/").length - 1];
                PushServer.hproseSrv.shareFile("/" + path);
                da.put("msgType", SHARE_FILE);
                da.put("msg", "/" + path);
                da.put("path", filePath);
                da.put("fileType", path.split("\\.")[path.split("\\.").length - 1]);
                if (userId.equals("all"))
                    PushServer.hproseSrv.push("ground", da.toString());
                else
                    PushServer.hproseSrv.push(userId + "owner", da.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        return url;
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
                            Toast.makeText(activity, activity.getString(R.string.downloadfie), Toast.LENGTH_SHORT)
                                    .show();
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
        JSONObject data = new JSONObject();
        try {
            data.put("className", className);
            data.put("platform", "Android");
            if (broadCastUdp == null) {
                broadCastUdp = new BroadCastUdp(data.toString());
                broadCastUdp.start();
            }
            broadCastUdp.isRun = true;
            PushServer.startHprose(accpectMessageHander);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        if (broadCastUdp != null) {
            broadCastUdp.Close();
            broadCastUdp = null;
        }
        PushServer.stop();
    }

    //---------------------------------2.0版本新增的接口--------------------------------------

    @JavascriptInterface
    public void shangke() {
        Log.d("test", "shangke");
        setScreenOrientation("0");
        showTools();
    }

    @JavascriptInterface
    public void xiake() {
        Log.d("test", "xiake");
        setScreenOrientation("1");
        hideTools();
    }

    @JavascriptInterface
    public void showTools() {//显示上课悬浮按钮
        Log.d("test", "showTools");
        this.activity.showTools();
    }

    @JavascriptInterface
    public void hideTools() {//隐藏上课悬浮按钮
        Log.d("test", "hideTools");
        this.activity.hideTools();
    }

    @JavascriptInterface
    public void openFileByX5(String path) {
        Log.d("test", "openFileByX5 is called , path = " + path);
        //1.检查本地有没有对应文件
        //2.如果有，直接打开
        //3.如果没有，下载再打开
        this.activity.openFileByX5(path);
    }

    @JavascriptInterface
    public void prepareFile(String token) {//选择文件-备课专用，同chooseFile，上传完成后拷贝文件到FilePath
        accessToken = token;
        activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token);
        new LFilePicker()
                .withActivity(activity)
                .withTitle(activity.getString(R.string.filepath2))
                .withRequestCode(requsetFile2)
                .withMutilyMode(false)
                .withfilePath(FilePath)
                .start();
    }
}
