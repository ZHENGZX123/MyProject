package com.sonix.oidbluetooth;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.sonix.oid.Dots;
import com.sonix.util.MyDBHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class WebViewActivity extends Activity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv = (WebView) findViewById(R.id.wv);
        initData();
        wv.loadUrl("file:///mnt/sdcard/dist/draw.html");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        final int pageid = getIntent().getIntExtra("pageid", 0);
        ArrayList<Dots> dots = new MyDBHelper(getApplicationContext()).getDotsByPageID("" + pageid);

        for (int i = 0; i < dots.size(); i++) {
            Dots d = dots.get(i);
            //规则1：如果第一个点不是0，强制为0
            if (i == 0) {
                d.ntype = 0;
                continue;
            }
            //规则2：如果0前面不是2，强制为2
            if (d.ntype == 0) {
                Dots prevDot = dots.get(i - 1);
                if (prevDot.ntype != 2) {
                    prevDot.ntype = 2;
                }
            }
            //规则3:如果最后一个不是2，强制2为2
            if (i == dots.size() - 1) {
                d.ntype = 2;
            }
        }

        String param = "";
        for (Dots d : dots) {
//            Log.d("test", "d = " + d.toString());
            Log.d("test", "d = " + d.ntype);
            if (d.ntype == 0) {
                param += "[" + d.pointX + "," + d.pointY + ",";
            } else if (d.ntype == 1) {
                param += d.pointX + "," + d.pointY + ",";
            } else {
                param += d.pointX + "," + d.pointY + "],";
            }
        }
        param = param.substring(0, param.length() - 1);
        param = "[" + param + "]";
        Log.d("test", "param = " + param);
        final String finalParam = param;
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadUrl("javascript:drawOffline('" + finalParam + "')");
                    }
                });
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebViewActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
