package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.MyWebChromeClient;
import cn.kiway.mdm.utils.Utils;


/**
 * Created by Administrator on 2017/10/26.
 */

public class WebViewActivity extends BaseActivity {

    private WebView wv;
    private EditText et;
    private ProgressBar pg1;
    private HashMap<String, String> extHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv = (WebView) findViewById(R.id.wv);
        et = (EditText) findViewById(R.id.et);
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
        initData();
    }

    private void initData() {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        if (Utils.isTabletDevice(this)) {
            settings.setTextSize(WebSettings.TextSize.NORMAL);
        } else {
            settings.setTextSize(WebSettings.TextSize.NORMAL);
        }
        wv.addJavascriptInterface(new JsAndroidInterface(), "wx");

        extHeader = new HashMap<String, String>();//创建额外的请求头参数表
        extHeader.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0");

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("test", "shouldOverrideUrlLoading = " + url);
                if (checkUrlEnable(url)) {
                    wv.loadUrl(url, extHeader);
                    return false;
                } else {
                    toast("该网站不能访问");
                    return true;
                }
            }

//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                Log.d("test", "onPageStarted url = " + url);
//                if (checkUrlEnable(url)) {
//                    super.onPageStarted(view, url, favicon);
//                } else {
//                    toast("该网站不能访问");
//                }
//            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.wx.showSource('<head>'+"
                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            }

        });
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new MyWebChromeClient(pg1));
    }

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        //显示html的内容
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("html = " + html);
            //判断是不是非法网站
        }

    }

    public void clickGo(View view) {
        Utils.hideSoftInput(this, view.getWindowToken());
        String content = et.getText().toString();
        if (TextUtils.isEmpty(content)) {
            toast("不能为空");
            return;
        }
        if (!content.startsWith("http")) {
            content = "http://" + content;
        }
        if (checkUrlEnable(content)) {
            wv.loadUrl(content, extHeader);
            Utils.childOperation(this, "surfInternet", "上网" + content);
        } else {
            toast("该网站不能访问");
        }
    }

    private boolean checkUrlEnable(String url) {
        int enable_type = Utils.getEnable_Network(this);
        Log.d("test", "enable_type = " + enable_type);
        if (enable_type == 0) {
            Log.d("test", "后台没有设置过url，或者设置过都清除了");
            return true;
        }
        //1.白名单启用
        if (enable_type == 1) {
            boolean in = false;
            ArrayList<Network> networks = new MyDBHelper(this).getAllNetworks(1);
            for (Network n : networks) {
                if (url.contains(n.url)) {
                    in = true;
                    break;
                }
            }
            return in;
        }
        if (enable_type == 2) {
            boolean in = false;
            ArrayList<Network> networks = new MyDBHelper(this).getAllNetworks(2);
            for (Network n : networks) {
                if (url.contains(n.url)) {
                    in = true;
                    break;
                }
            }
            return !in;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                Log.d("test", "goBack");
                wv.goBack();
                return true;
            } else {
                Log.d("test", "finish");
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
