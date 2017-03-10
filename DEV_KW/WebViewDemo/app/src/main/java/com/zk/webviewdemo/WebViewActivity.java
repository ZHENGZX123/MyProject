package com.zk.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    private EditText et;
    private Button go;
    private WebView wv;
    private RelativeLayout rl;
    private TextView title;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et = (EditText) findViewById(R.id.et);
        go = (Button) findViewById(R.id.go);
        wv = (WebView) findViewById(R.id.wv);
        rl = (RelativeLayout) findViewById(R.id.rl);
        title = (TextView) findViewById(R.id.title);
        pb = (ProgressBar) findViewById(R.id.pb);


        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        String url = getSharedPreferences("webview", 0).getString("url", "http://www.kiway.cn");
        et.setText(url);
        wv.loadUrl(url);


        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    String url = et.getText().toString();
                    getSharedPreferences("webview", 0).edit().putString("url", url).commit();
                    WebViewActivity.this.title.setText(view.getTitle());
                }
                pb.setProgress(newProgress);
            }

//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                WebViewActivity.this.title.setText(title);
//            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
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

    public void clickInput(View v) {
        rl.setVisibility(View.VISIBLE);
    }

    public void clickGo(View view) {
        String url = et.getText().toString();
        if (url.equals("")) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!url.startsWith("http")) {
            Toast.makeText(this, "请以http或https开头", Toast.LENGTH_SHORT).show();
            return;
        }
        wv.loadUrl(url);
        rl.setVisibility(View.GONE);
    }
}
