package cn.kiway.klzy.webuitl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.InputStream;

import cn.kiway.klzy.WebViewActivity;

import static android.content.ContentValues.TAG;
import static cn.kiway.klzy.webuitl.WebUrl.indexUrl;

/**
 * Created by Administrator on 2017/5/26.
 */

public class WebViewClientUtil extends WebViewClient {
    Context context;

    public WebViewClientUtil(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        ((WebViewActivity) context).mProgressBar.setVisibility(View.VISIBLE);
        ((WebViewActivity) context).mProgressBar.setAlpha(1.0f);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        Log.e("onPageFinished", url);
        if (url.equals(indexUrl))
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onLoadResource(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.e("shouldInterceptRequest", url);
        if (url.startsWith(WebUrl.replaceUrl)) {
            String mimeType = InterceptRequest.getMimeType(url);
            Log.i(TAG, "尝试本地加载文件：" + url + "||mime:" + mimeType);
            InputStream is = null;
            try {
                String loginrl = url.replace(WebUrl.replaceUrl, "klzy");
                Log.e("shouldInterceptRequest", loginrl);
                if (loginrl.contains("servlet") || loginrl.contains("openFile")) {
                    loginrl = "http://202.104.136.13:66/" + loginrl;
                    Log.d("test", "localUrl = " + loginrl);
                    return super.shouldInterceptRequest(view, loginrl);
                }
                is = InterceptRequest.getIS(context, loginrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new WebResourceResponse(mimeType, "utf-8", is);
        }
        return super.shouldInterceptRequest(view, url);
    }

}
