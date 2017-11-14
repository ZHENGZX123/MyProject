package cn.kiway.mdm.web;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.LFilePicker;

import java.io.File;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.scoket.ScreenActivity;
import cn.kiway.mdm.scoket.db.DbUtils;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.netty.HproseChannelMapStatic;
import cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.scoket.utils.WifiUtils;
import cn.kiway.mdm.view.X5WebView;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.NettyServerBootstrap.staute;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer.FilePath;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {

    MainActivity activity;
    AccpectMessageHander accpectMessageHander;
    //BroadCastUdp broadCastUdp;

    public JsAndroidInterface(MainActivity activity, X5WebView webView) {
        this.activity = activity;
        accpectMessageHander = new AccpectMessageHander(activity, webView);
        // this.broadCastUdp = broadCastUdp;
        startServer();
    }

    @JavascriptInterface
    public void scoketOperate(String state) { //1启动，0关闭
        Logger.log("--------------Start----------" + state);
        if (state.equals("1")) {
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
    }

    @JavascriptInterface
    public String getscoketStaute() {//主动获取服务器状态 1 开启，2关闭，3异常
        return staute;
    }

    @JavascriptInterface
    public void setScreenOrientation(String orientation) {
        if (orientation.equals("0"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation.equals("1"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @JavascriptInterface
    public String getWifiIp() {
        return WifiUtils.getIPAddress(activity);
    }

    @JavascriptInterface
    public void pushTheScreen() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName("com.kiway.ikv3", "com.clovsoft.ik.MainActivity");
        intent.setComponent(cn);
        activity.startActivity(intent);
    }

    @JavascriptInterface
    public void multiControl(String userId) {
        if (HproseChannelMapStatic.getChannel(userId) == null) {
            Toast.makeText(activity, "学生还没上线", Toast.LENGTH_SHORT).show();
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

    @JavascriptInterface
    public void takePhoto() {
        picPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kiwaymdm/pic/" + System
                .currentTimeMillis() + ".png";
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));//为拍摄的图片指定一个存储的路径
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent2, REQUEST_ORIGINAL);
    }

    public static String FILEPER = "";
    public static final int requsetFile = 45612;

    @JavascriptInterface
    public void sendFile(String userId) {//发送文件
        FILEPER = userId;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.parse(FilePath));
//        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        activity.startActivityForResult(intent, requsetFile);
        new LFilePicker()
                .withActivity(activity)
                .withTitle("请将文件移动到此目录下（kiwaymdm）")
                .withRequestCode(requsetFile)
                .withMutilyMode(true)
                .withfilePath(FilePath)
                .start();
    }

    @JavascriptInterface
    public boolean getStudentStatus(String userId) { //学生是否连上了 true false
        if (HproseChannelMapStatic.getChannel(userId) == null)
            return false;
        return true;
    }


    public void startServer() {
        PushServer.startHprose(accpectMessageHander);
    }

    public void closeServer() {
        PushServer.stop();
    }
}
