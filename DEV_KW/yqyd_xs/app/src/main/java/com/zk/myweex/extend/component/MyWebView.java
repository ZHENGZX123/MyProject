package com.zk.myweex.extend.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.zk.myweex.utils.ScreenUtil;

public class MyWebView extends WXComponent<WebView> {
    private WebView wv;

    public MyWebView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected WebView initComponentHostView(@NonNull Context context) {
        wv = new WebView(context);

        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setTextSize(WebSettings.TextSize.LARGEST);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                wv.loadUrl("javascript:window.kiway.getContentHeight(document.documentElement.scrollHeight+'');");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        wv.addJavascriptInterface(new JavaScriptObject(), "kiway");
        return wv;
    }

    @WXComponentProp(name = "content")
    public void setContent(String content) {
        Log.d("test", "content = " + content);
        String txt = content;
        wv.loadDataWithBaseURL(null, txt, "text/html", "utf-8", null);
    }

    private class JavaScriptObject {

        @JavascriptInterface
        public void getContentHeight(String value) {
            Toast.makeText(getContext(), "ContentWidth of webpage is: " + value + "px", Toast.LENGTH_SHORT).show();
            int width = ScreenUtil.getDisplayWidth((AppCompatActivity) getContext());
            int height = Integer.parseInt(value);
            MyWebView.this.setHostLayoutParams(getHostView(), width, height, 0, 0, 0, 0);
        }
    }
}
