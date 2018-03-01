package cn.kiway.hybird.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.database.util.KwDBHelper;
import cn.kiway.hybird.teacher.R;
import cn.kiway.hybird.util.KwJsInterface;
import cn.kiway.hybird.util.Utils;
import cn.kiway.sharedpref.SPUtil;
import cn.kiway.utils.BadgeUtil;
import cn.kiway.utils.Configue;
import cn.kiway.utils.MLog;

import static cn.kiway.hybird.util.KwJsInterface.QRSCAN;
import static cn.kiway.hybird.util.KwJsInterface.SAOMAWANG;
import static cn.kiway.hybird.util.KwJsInterface.SELECT_PHOTO;
import static cn.kiway.hybird.util.KwJsInterface.SNAPSHOT;
import static cn.kiway.hybird.util.KwJsInterface.snapshotFile;


public class MainActivity extends BaseActivity {

    private WebView wv;
    private Button kill;
    public static MainActivity instance;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        initData();
        huaweiPush();
        checkNewAPK();
        Utils.checkNetWork(this, false);
    }


    private void initView() {
        wv = (WebView) findViewById(R.id.wv);
        kill = (Button) findViewById(R.id.kill);
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
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        //智慧课堂平板项目好像不维护了
        //if (Utils.isTabletDevice(this)) {
        //settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.LARGER);
        //} else {
        //settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.NORMAL);
        //}

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MLog.d("test", "onPageFinished host = " + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                MLog.d("test", "shouldInterceptRequest host = " + url);
                //if ((host.startsWith("http") || host.startsWith("https"))
                //&& (host.endsWith("jpg") || host.endsWith("JPG") || host.endsWith("jpeg") || host.endsWith("JPEG") || host.endsWith("png") || host.endsWith("PNG"))) {
                //InputStream is = getStreamByUrl(host);
                //if (is == null) {
                //return super.shouldInterceptRequest(view, host);
                //}
                //return new WebResourceResponse(getMimeType(host), "utf-8", is);
                //}
                //des解密用
                //else if (host.endsWith("js") || host.endsWith("css") || host.endsWith("html")) {
                //InputStream is = getStreamByUrl2(host.replace("file://", ""));
                //return new WebResourceResponse(getMimeType(host), "utf-8", is);
                //}
                return super.shouldInterceptRequest(view, url);
            }
        });

        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        wv.addJavascriptInterface(new KwJsInterface(this, wv), "wx");

        wv.loadUrl("file://" + Configue.ROOT + Configue.HTML);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BadgeUtil.sendBadgeNumber(getApplicationContext(), "0");
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkNotification();
            }
        }.start();
    }

    private synchronized void checkNotification() {
        final String event = SPUtil.instance().getValue("event", "");
        MLog.d("test", "取了一个event = " + event);
        if (TextUtils.isEmpty(event)) {
            return;
        }
        SPUtil.instance().setValue("event", "");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //告诉前端做动作。
                MLog.d("test", "notificationCallback");
                wv.loadUrl("javascript:notificationCallback('" + event + "')");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String url = wv.getUrl();
            MLog.d("test", "host = " + url);
            for (String p : Configue.pages) {
                if (url.endsWith(p)) {
                    doFinish();
                    return true;
                }
            }
            if (wv.canGoBack()) {
                wv.goBack();
                return true;
            }
            doFinish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doFinish() {
        long t = System.currentTimeMillis();
        if (t - time >= 2000) {
            time = t;
            toast("再按一下退出");
        } else {
            finish();
        }
    }

    public void clickSetToken(View view) {
        //SPUtil.instance().setValue("accessToken", "123456")
        new KwDBHelper(this).deleteAllHttpCache();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SNAPSHOT && resultCode == RESULT_OK) {
            MLog.d("test", "snapshotCallback");
            //wv.loadUrl("javascript:snapshotCallback('file://" + snapshotFile + "')");
            MLog.d("test", "压缩前大小" + new File(snapshotFile).length());
            File newFile = CompressHelper.getDefault(this).compressToFile(new File(snapshotFile));
            MLog.d("test", "压缩后大小" + newFile.length());
            wv.loadUrl("javascript:snapshotCallback('file://" + newFile.getAbsolutePath() + "')");
        } else if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                MLog.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                MLog.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;
            MLog.d("test", "path = " + path);
            wv.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        } else if (requestCode == SAOMAWANG) {
            if (data == null) {
                return;
            }
            int responseCode = data.getIntExtra("RESULT_OK", -1);
            MLog.d("test", "responseCode = " + responseCode);
        } else if (requestCode == QRSCAN) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            MLog.d("test", "result = " + result);
            wv.loadUrl("javascript:scanQRCallback('" + result + "')");
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                int arg1 = msg.arg1;
                int arg2 = msg.arg2;
                if (arg1 == 0) {
                    rl_nonet.setVisibility(View.VISIBLE);
                    MLog.d("test", "无网络");
                } else {
                    rl_nonet.setVisibility(View.GONE);
                    MLog.d("test", "有网络");
                    if (arg2 == 1) {
                        wv.loadUrl("javascript:reConnect()");
                    }
                }
            }
        }
    };

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}