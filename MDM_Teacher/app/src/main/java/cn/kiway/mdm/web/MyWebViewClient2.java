package cn.kiway.mdm.web;

import android.graphics.Bitmap;
import android.util.Log;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyWebViewClient2 extends com.tencent.smtt.sdk.WebViewClient {
    public MyWebViewClient2() {
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
        // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不另跳浏览器
        // 在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
        if (url.startsWith("http://") || url.startsWith("https://")) {
            webView.loadUrl(url);
            webView.stopLoading();
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
        //Logger.log("webUrl::::" + webResourceRequest.getUrl());
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

}
