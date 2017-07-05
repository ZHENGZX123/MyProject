package cn.kiway.homework.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.homework.HTTPCache;
import cn.kiway.homework.WXDBHelper;
import cn.kiway.homework.teacher.R;
import cn.kiway.homework.util.NetworkUtil;


public class WebViewActivity extends BaseActivity {

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
        wv.loadUrl("file:///android_asset/test2.html");
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

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("test", "shouldInterceptRequest url = " + url);
//                if (url.endsWith("jpg")) {
//                    InputStream is = getAssets().open(localUrl);
//                    return new WebResourceResponse(getMimeType(url), "utf-8", is);
//                }
                return super.shouldInterceptRequest(view, url);
            }
        });
        
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "native");
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
        public void httpRequest(final String url, final String param, final String method, String reload) {
            Log.d("test", "httpRequest url = " + url + " , param = " + param + " , method = " + method + " , reload = " + reload);
            if (reload.equals("1")) {
                //重新获取
                doHttpRequest(url, param, method);
            } else {
                //取缓存
                String request = url + param + method;
                HTTPCache cache = new WXDBHelper(WebViewActivity.this).getHttpCacheByRequest(request);
                if (cache == null) {
                    doHttpRequest(url, param, method);
                } else {
                    callback(url, cache.response);
                }
            }
        }
    }

    private void doHttpRequest(final String url, final String param, final String method) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!NetworkUtil.isNetworkAvailable(WebViewActivity.this)) {
                        toast("没有网络");
                        return;
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    if (method.equalsIgnoreCase("POST")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.post(WebViewActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                Log.d("test", "post onSuccess = " + ret);
                                saveDB(url, param, method, ret);
                                callback(url, ret);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                                Log.d("test", "post onFailure = " + arg2);
                                callback(url, "failure");
                            }
                        });
                    } else if (method.equalsIgnoreCase("GET")) {
                        client.get(WebViewActivity.this, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                Log.d("test", "get onSuccess = " + ret);
                                saveDB(url, param, method, ret);
                                callback(url, ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                Log.d("test", "get onFailure = " + s);
                                callback(url, "failure");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "exception = " + e.toString());
                }
            }
        });
    }

    private void saveDB(String url, String param, String method, String ret) {
        Log.d("test", "saveDB");
        HTTPCache cache = new HTTPCache();
        cache.request = url + param + method;
        cache.response = ret;
        cache.requesttime = "" + System.currentTimeMillis();
        new WXDBHelper(this).addHTTPCache(cache);
    }

    private void callback(String url, String result) {
        Log.d("test", "callback , url = " + url + " , result = " + result);
        wv.loadUrl("javascript:httpRequestResult('" + result +
                "')");
    }
}