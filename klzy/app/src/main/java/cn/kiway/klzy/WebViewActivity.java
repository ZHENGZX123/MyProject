package cn.kiway.klzy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.klzy.dialog.LoginDialog;
import cn.kiway.klzy.webuitl.HanderMessageWhat;
import cn.kiway.klzy.webuitl.JsAndroidIntercrpt;
import cn.kiway.klzy.webuitl.MyWebChromeClient;
import cn.kiway.klzy.webuitl.MyWebViewDownLoadListener;
import cn.kiway.klzy.webuitl.WebUrl;
import cn.kiway.klzy.webuitl.WebViewClientUtil;

public class WebViewActivity extends Activity {

    private WebView wv;
    public LoginDialog loginDialog;

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessagesAboveL;
    public ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_webview2);
        wv = (WebView) findViewById(R.id.wv);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        loginDialog = new LoginDialog(this);
        initData();
        loadHtml();
    }

    private void loadHtml() {
        wv.loadUrl(WebUrl.webUrl);
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
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        wv.setWebViewClient(new WebViewClientUtil(this));
        wv.setDownloadListener(new MyWebViewDownLoadListener(this, handler, (App) getApplicationContext()));
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new MyWebChromeClient(this));
        wv.addJavascriptInterface(new JsAndroidIntercrpt(this), "js");

    }

    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (mUploadMessagesAboveL != null) {
            MyWebChromeClient.onActivityResultAboveL(this, requestCode, resultCode, intent);
        }
        if (mUploadMessage == null) return;
        Uri uri = null;
        if (requestCode == HanderMessageWhat.ResultMessage888 && resultCode == RESULT_OK) {
            uri = Uri.parse("");
        }
        if (requestCode == HanderMessageWhat.ResultMessage999 && resultCode == RESULT_OK) {
            uri = MyWebChromeClient.afterChosePic(this, intent);
        }
        mUploadMessage.onReceiveValue(uri);
        mUploadMessage = null;
        super.onActivityResult(requestCode, resultCode, intent);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HanderMessageWhat.messageWhat1:
                    if (loginDialog != null)
                        loginDialog.close();
                    wv.loadUrl((String) msg.obj);
                    break;
                case HanderMessageWhat.messageWhat2:
                    if (loginDialog != null)
                        loginDialog.close();
                    Toast.makeText(WebViewActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case HanderMessageWhat.messageWhat3:
                    loginDialog.setTitle(msg.obj.toString());
                    break;
                case HanderMessageWhat.messageWhat4:
                    loginDialog.setTitle(msg.obj.toString());
                    loginDialog.show();
                    break;
            }
        }
    };

    long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            //Toast.makeText(this, page, Toast.LENGTH_SHORT).show();
            if (url.contains(WebUrl.webUrl)) {
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("axe.mg", "onConfigurationChanged");

    }
}