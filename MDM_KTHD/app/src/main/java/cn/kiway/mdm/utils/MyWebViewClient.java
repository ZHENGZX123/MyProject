package cn.kiway.mdm.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyWebViewClient extends WebViewClient {
    public MyWebViewClient() {
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        Log.d("test", "onPageFinished url = " + s);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            webView.loadUrl(url);
            return true;
        }
        return false;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
        Log.d("test", "shouldInterceptRequest url = " + url);
        return super.shouldInterceptRequest(webView, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        Logger.log("webUrl::::" + webResourceRequest.getUrl());
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

}
