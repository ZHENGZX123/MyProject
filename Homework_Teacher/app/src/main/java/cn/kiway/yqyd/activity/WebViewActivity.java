package cn.kiway.yqyd.activity;

import android.app.Activity;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.homework.teacher.R;


public class WebViewActivity extends Activity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        wv = (WebView) findViewById(R.id.wv);
        initData();
        load();
    }

    private void load() {
        wv.loadUrl("");
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
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "js");
    }

    private long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            String page = url.substring(url.lastIndexOf("/") + 1);
            if (page.contains("yqyd/yqyd/index/index.html?token=") || page
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

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        @JavascriptInterface
        public void httpRequest(String url, String param, String method) {
            Log.d("test", "httpRequest url = " + url + " , param = " + param + " , method = " + method);

        }
    }
}