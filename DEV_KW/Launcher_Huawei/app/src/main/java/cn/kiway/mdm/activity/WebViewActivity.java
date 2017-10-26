package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.util.ArrayList;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.utils.MyDBHelper;


/**
 * Created by Administrator on 2017/10/26.
 */

public class WebViewActivity extends BaseActivity {

    private WebView wv;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv = (WebView) findViewById(R.id.wv);
        et = (EditText) findViewById(R.id.et);
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

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("test", "onPageFinished url = " + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("test", "url = " + url);
                view.loadUrl(url);
                return true;
            }

        });

        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
    }

    public void clickGo(View view) {
        String content = et.getText().toString();
        if (TextUtils.isEmpty(content)) {
            toast("不能为空");
            return;
        }
        if (checkNetwork(content) == 1) {
            wv.loadUrl(content);
        } else if (checkNetwork(content) == 2 || checkNetwork(content) == 0) {
            toast("该网站不能访问");
        }
    }

    //1.白名单
    //2.黑名单
    //TODO 需要优化
    private int checkNetwork(String content) {
        int type = 0;
        ArrayList<Network> networks = new MyDBHelper(this).getAllNetworks();
        for (Network n : networks) {
            if (content.contains(n.url)) {
                type = n.type;
                break;
            }
        }
        return type;
    }
}
