package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.MyWebChromeClient;


/**
 * Created by Administrator on 2017/10/26.
 */

public class WebViewActivity extends BaseActivity {

    private WebView wv;
    private EditText et;
    private ProgressBar pg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        wv = (WebView) findViewById(R.id.wv);
        et = (EditText) findViewById(R.id.et);
        pg1=(ProgressBar) findViewById(R.id.progressBar1);
        initData();
    }

    private void initData() {
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                int type = checkNetwork(url);
                Log.d("test", "shouldOverrideUrlLoading url = " + url);
                Log.d("test", "type = " + type);
                if (type == 1) {
                    view.loadUrl(url);
                    return true;
                } else {
                    toast("该网站不能访问");
                    return true;
                }
            }
        });
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new MyWebChromeClient(pg1));
    }

    public void clickGo(View view) {
        String content = et.getText().toString();
        if (TextUtils.isEmpty(content)) {
            toast("不能为空");
            return;
        }
        if (!content.startsWith("http")) {
            content = "http://" + content;
        }
        if (checkNetwork(content) == 1) {
            wv.loadUrl(content);
        } else if (checkNetwork(content) == 2 || checkNetwork(content) == 0) {
            toast("该网站不能访问");
        }
    }

    //1.白名单
    //2.黑名单
    //判断域名，还是全匹配
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
