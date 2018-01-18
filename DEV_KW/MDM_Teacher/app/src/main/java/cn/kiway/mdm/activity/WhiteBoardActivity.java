package cn.kiway.mdm.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.web.JsAndroidInterface2;
import cn.kiway.mdm.web.MyWebViewClient;


/**
 * Created by Administrator on 2017/12/29.
 */

public class WhiteBoardActivity extends BaseActivity {

    private WebView wv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);

        initView();
        initData();
        load();
    }

    private void load() {
        wv.clearCache(true);
        String url = "file://" + KWApplication.ROOT + KWApplication.HTML2;
        Log.d("test", "url = " + url);
        wv.loadUrl(url);
    }

    private void initData() {
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = wv.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        //settings.setDomStorageEnabled(true); 画板不能用这个属性
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface2(), "wx");
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("画笔");
        wv = (WebView) findViewById(R.id.wv);
    }
}
