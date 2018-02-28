package cn.kiway.mdm.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.leon.lfilepickerlibrary.LFilePicker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
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
import static cn.kiway.mdm.util.Constant.APPID;
import static cn.kiway.mdm.util.Constant.clientUrl;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.FileUtils.EnFILEPATH;
import static cn.kiway.mdm.util.ResultMessage.SELECT_PHOTO;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {

    private MainActivity activity;
    public static final int requsetFile = 45612;
    public static final int requsetFile2 = 45613;
    public static String accessToken;

    public JsAndroidInterface(MainActivity activity) {
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
        activity.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
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
        Log.d("test", "getHost is called return " + clientUrl);
        return clientUrl;
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
                final int ret = new HttpDownload().downFile(downUrl, folder, fileName);//开始下载
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
        KWApplication.students.clear();

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
            activity.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
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
        activity.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
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
    public void selectPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
        imagePicker.setSelectLimit(1);// 设置可以选择几张
        imagePicker.setMultiMode(false);// 是否为多选
        imagePicker.setCrop(true);// 是否剪裁
        imagePicker.setFocusWidth(1000);// 需要剪裁的宽
        imagePicker.setFocusHeight(1000);// 需要剪裁的高
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
        imagePicker.setShowCamera(true);// 是否显示摄像

        Intent intent = new Intent(this.activity, ImageGridActivity.class);
        this.activity.startActivityForResult(intent, SELECT_PHOTO);
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

    @JavascriptInterface
    public void login(String info) {
        Log.d("test", "login is called , info = " + info);
        try {
            JSONObject o = new JSONObject(info);
            String token = o.optString("token");
            String userId = o.optString("userId");
            activity.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
            activity.getSharedPreferences("kiway", 0).edit().putString("userId", userId).commit();
            String imei = Utils.getIMEI(this.activity);
            installationPush(this.activity, userId, imei);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public void logout() {
        Log.d("test", "logout is called");
        uninstallPush(this.activity);
    }

    public void installationPush(final Context c, final String userId, final String imei) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            if (TextUtils.isEmpty(xtoken)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            client.setTimeout(10000);
            Log.d("test", "userId = " + userId);
            JSONObject param = new JSONObject();
            param.put("appId", APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", userId);
            param.put("module", "student");
            Log.d("test", "installationPush param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = clientUrl + "/push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    JsAndroidInterface.this.activity.initZbus();
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

    public void uninstallPush(final Context c) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            if (TextUtils.isEmpty(xtoken)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("type", "huawei");
            param.put("imei", Utils.getIMEI(c));
            param.put("token", userId);
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "/device/uninstall";
            Log.d("test", "uninstallPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "uninstallPush onSuccess = " + ret);
                    //callback loadurl(xxxcalback)
                    c.getSharedPreferences("kiway", 0).edit().clear().commit();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "uninstallPush onFailure = " + s);
                    //callback

                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }
}
