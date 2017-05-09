package com.zk.myweex.extend.component;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXFileUtils;

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

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                wv.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.imagelistner.openImage(this.src);  "
                        + "    }  " + "}" + "})()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        wv.addJavascriptInterface(new JavaScriptObject(context), "imagelistner");
        return wv;
    }

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        String txt = WXFileUtils.loadAsset("test2.txt", getContext());
        txt = txt.replaceAll("font-size: 16px", "font-size: 34px").replaceAll("font-size: 12px", "font-size: 34px");
        txt = txt.replaceAll("<img", "<img style='width:90%;height:auto'");
        wv.loadDataWithBaseURL(null, txt, "text/html", "utf-8", null);
    }
}
