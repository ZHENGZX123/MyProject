package cn.kiway.mdm.web;

import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyWebViewClient extends com.tencent.smtt.sdk.WebViewClient {
    public MyWebViewClient() {
    }


    @Override
    public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
        super.onPageFinished(webView, s);
        Log.d("test", "onPageFinished url = " + s);
    }

    @Override
    public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String s) {
        webView.loadUrl(s);
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(com.tencent.smtt.sdk.WebView webView, String url) {
        Log.d("test", "shouldInterceptRequest url = " + url);
        return super.shouldInterceptRequest(webView, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

}
