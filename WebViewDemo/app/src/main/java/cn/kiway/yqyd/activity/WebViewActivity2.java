package cn.kiway.yqyd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.yqyd.R;
import cn.kiway.yqyd.dialog.LoginDialog;
import cn.kiway.yqyd.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebViewActivity2 extends Activity implements Callback {

    private WebView wv;
    private String uploadUserPic;
    private LoginDialog loginDialog;
    OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview2);
        wv = (WebView) findViewById(R.id.wv);
        loginDialog = new LoginDialog(this);
        initData();
        load();
        checkVersionUp();
    }

    private void checkVersionUp() {
        //静默更新
    }

    private void initData() {
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = wv.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);


        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidIntetface(), "js");
    }

    private void load() {
        String token = getIntent().getStringExtra("token");
        //wv.addJavascriptInterface(new JavaScriptObject(this), "native");
//        wv.loadUrl("file:///android_asset/yqydls/yqyd/index/index.html?token=" + token);
        wv.loadUrl("file:///android_asset/yqyd(teacher)/yqyd/yqyd/index/index.html?token=" + token);
    }


    long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            String page = url.substring(url.lastIndexOf("/") + 1);
            //Toast.makeText(this, page, Toast.LENGTH_SHORT).show();
            if (page.contains("file:///android_asset/yqyd(teacher)/yqyd/yqyd/index/index.html?token=") || page
                    .endsWith("sc") || page.endsWith("main") || page.endsWith("/class/myclass")) {
                long t = System.currentTimeMillis();
                if (t - time >= 2000) {
                    time = t;
                    Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                } else
                    finish();
                return true;
            } else {
                if (wv.canGoBack()) {
                    wv.goBack();//返回上一页面
                    return true;
                } else {
                    long t = System.currentTimeMillis();
                    if (t - time >= 2000) {
                        time = t;
                        Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                    } else
                        finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 888) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                try {
                    uploadUserPic(images.get(0).path);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void uploadUserPic(String fileName) throws UnsupportedEncodingException {
        if (loginDialog != null)
            loginDialog.show();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), fileName);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", fileName, fileBody)
                .build();
        Request request = new Request.Builder()
                .url(uploadUserPic)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        if (loginDialog != null)
            loginDialog.close();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (loginDialog != null)
            loginDialog.close();
        if (call.request().url().toString().equals(uploadUserPic)) {
            wv.loadUrl("javascript:updateUserInfo('" + "图像地址" + "')");
        }
    }

    public class JsAndroidIntetface {
        public JsAndroidIntetface() {
        }

        @JavascriptInterface
        public void logout() {
            //退出
            SharedPreferencesUtil.save(WebViewActivity2.this, "userName", "");
            SharedPreferencesUtil.save(WebViewActivity2.this, "passWord", "");
            startActivity(new Intent(WebViewActivity2.this, LoginActivity.class));
        }

        @JavascriptInterface
        public void scan() {
            //扫码的功能
        }

        @JavascriptInterface
        public String getSessionObj() {
            JSONObject da = new JSONObject();
            try {
                da.put("userName", SharedPreferencesUtil.getString(WebViewActivity2.this, "userName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return da.toString();
        }

        @JavascriptInterface
        public void updateUserInfo() {
            //imagepicker
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像
            Intent intent = new Intent(WebViewActivity2.this, ImageGridActivity.class);
            WebViewActivity2.this.startActivityForResult(intent, 888);
        }
    }
}