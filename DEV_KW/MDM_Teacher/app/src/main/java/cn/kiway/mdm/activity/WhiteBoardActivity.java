package cn.kiway.mdm.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.X5WebView;
import cn.kiway.mdm.web.MyWebViewClient;


/**
 * Created by Administrator on 2017/12/29.
 */

public class WhiteBoardActivity extends BaseActivity {

    private X5WebView wv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);

        initView();
        initData();
        load();
    }

    private void load() {
        wv.loadUrl("file:///android_asset/dist/index.html");

        wv.loadUrl();
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
        com.tencent.smtt.sdk.WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "wx");
    }

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        @JavascriptInterface
        public void saveBase64(String param) {
            Log.d("test", "saveBase64 is called , param = " + param);
            Bitmap bitmap = null;
            try {
                byte[] bitmapArray = Base64.decode(param.split(",")[1], Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                Utils.saveBitmap(bitmap, System.currentTimeMillis() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initView() {
        super.initView();

        titleName.setText("画笔");
        wv = (X5WebView) findViewById(R.id.wv);
    }
}
