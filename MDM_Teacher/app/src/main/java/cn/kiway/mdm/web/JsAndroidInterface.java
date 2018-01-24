package cn.kiway.mdm.web;

import android.app.ProgressDialog;
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

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.util.WifiUtils;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_DIANMING;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.FileUtils.EnFILEPATH;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {
    public static String userAccount = "";

    private MainActivity activity;
    private String className;
    public static final int requsetFile = 45612;
    public static final int requsetFile2 = 45613;
    public static String accessToken;

    public JsAndroidInterface(MainActivity activity, WebView webView) {
        this.activity = activity;
    }

    @JavascriptInterface//获取wifi IP
    public String getWifiIp() {
        return WifiUtils.getIPAddress(activity);
    }

    @JavascriptInterface
    public String getVersionCode() {
        //return getCurrentVersion(MainActivity.this);
        Log.d("test", "getVersionCode");
        return this.activity.getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
    }

    @JavascriptInterface
    public void setScreenOrientation(String orientation) {//设置横竖
        if (orientation.equals("0"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation.equals("1"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    @JavascriptInterface
    public String getPlatform() {
        return "Android";
    }

    @JavascriptInterface
    public String getHost() {
        Log.d("test", "getHost is called return " + KWApplication.clientUrl);
        return KWApplication.clientUrl;
    }

    @JavascriptInterface
    public void openFile(final String downUrl, final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String folder = DOWNFILEPATH;//下载存放的文件夹
                if (!new File(folder).exists()) {
                    new File(folder).mkdirs();
                }
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
            String userId = o.optString("userId");
            activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("userId", userId).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("classId", classId).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("className", className).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("username", username).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("teacherName", teacherName).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("teacherAvatar", teacherAvatar).commit();

            this.activity.startActivity(new Intent(this.activity, StudentGridActivity.class).putExtra("type", TYPE_DIANMING));
            //Utils.shangke(this.activity, wifiIp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void prepareFile(String token, String type) {//选择文件-备课专用，同chooseFile，上传完成后拷贝文件到FilePath
        Log.d("test", "prepareFile is called");
        accessToken = token;
        activity.getSharedPreferences("kiway", 0).edit().putString("accessToken", token).commit();
        String[] filters;
        if (type.equals("1")) {
            filters = new String[]{".jpg", ".jpeg", ".png", ".JPG", ".JPEG", ".PNG"};
        } else {
            filters = new String[]{".doc", ".docx", ".txt", ".xls", ".xlsx", ".ppt", ".pptx", "pdf", ".DOC", ".DOCX", ".TXT", ".XLS", ".XLSX", ".PPT", ".PPTX", "PDF"};
        }
        new LFilePicker()
                .withActivity(activity)
                .withTitle(activity.getString(R.string.filepath2))
                .withRequestCode(requsetFile2)
                .withMutilyMode(false)
                .withFileFilter(filters)
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