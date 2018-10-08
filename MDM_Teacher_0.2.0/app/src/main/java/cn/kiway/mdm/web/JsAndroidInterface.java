package cn.kiway.mdm.web;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.X5WebView;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.mdm.WXApplication.url;
import static cn.kiway.mdm.activity.MainActivity.SELECT_PHOTO;
import static cn.kiway.mdm.activity.MainActivity.sendCommandCallback;
import static cn.kiway.mdm.zbus.ZbusMessageHandler.callbackArray;

/**
 * Created by Administrator on 2017/11/9.
 */

public class JsAndroidInterface {
    MainActivity activity;

    public JsAndroidInterface(MainActivity activity, X5WebView webView) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void setScreenOrientation(String orientation) {//设置横竖
        if (orientation.equals("0"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else if (orientation.equals("1"))
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @JavascriptInterface
    public String getPlatform() {
        return "Android";
    }

    @JavascriptInterface
    public String getHost() {
        Log.d("test", "getHost is called");
        return url;
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

    //{"tokens":[] , "cmd":"snapshot"}
    @JavascriptInterface
    public void sendCommand(String command) {
        Log.d("test", "sendCommand command = " + command);
        try {
            JSONObject o = new JSONObject(command);
            JSONArray tokens = o.optJSONArray("tokens");
            ArrayList<String> students = new ArrayList<>();
            int count = tokens.length();
            for (int i = 0; i < count; i++) {
                students.add(tokens.optString(i));
            }
            final String cmd = o.optString("cmd");
            ZbusHost.sendMsg(MainActivity.instance, cmd, students, new OnListener() {

                @Override
                public void onSuccess() {
                    try {
                        callbackArray = new JSONArray();
                        if (cmd.startsWith("online")) {
                            Thread.sleep(5000);
                            final JSONObject o = new JSONObject();
                            o.put("online", callbackArray);
                            MainActivity.instance.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String js = sendCommandCallback.replace("callbackString", o.toString());
                                    Log.d("test", "js = " + js);
                                    MainActivity.instance.wv.loadUrl(js);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            param.put("appId", "f2ec1fb69b27c7ab5260d2eb7cd95dea");
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", imei);//userId
            param.put("module", "student");
            Log.d("test", "installationPush param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = WXApplication.url + "/push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    MainActivity.instance.initZbus();
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
            String url = WXApplication.url + "/device/uninstall";
            Log.d("test", "uninstallPush = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "uninstallPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "uninstallPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    @JavascriptInterface
    public void showPhoto(String param1, String param2) {
        try {
            try {
                Log.d("test", "showPhoto param1 = " + param1);
                Log.d("test", "showPhoto param2 = " + param2);
                ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
                Intent intent = new Intent(MainActivity.instance, ViewPagerActivity.class);
                intent.putExtra("position", Integer.parseInt(param2));
                MainActivity.instance.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
