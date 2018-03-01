package cn.kiway.hybird.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.countly.CountlyUtil;
import cn.kiway.database.util.KwDBHelper;
import cn.kiway.hybird.teacher.R;
import cn.kiway.hybird.util.JsAndroidInterface;
import cn.kiway.hybird.util.Utils;
import cn.kiway.hybird.view.X5WebView;
import cn.kiway.utils.BadgeUtil;
import cn.kiway.utils.Configue;
import cn.kiway.utils.MLog;
import cn.kiway.utils.NetworkUtil;

import static cn.kiway.hybird.util.JsAndroidInterface.QRSCAN;
import static cn.kiway.hybird.util.JsAndroidInterface.SAOMAWANG;
import static cn.kiway.hybird.util.JsAndroidInterface.SELECT_PHOTO;
import static cn.kiway.hybird.util.JsAndroidInterface.SNAPSHOT;
import static cn.kiway.hybird.util.JsAndroidInterface.snapshotFile;
import static cn.kiway.utils.Configue.ZIP;


public class MainActivity extends BaseActivity {

    private Dialog dialog_download;
    protected ProgressDialog pd;
    private X5WebView wv;
    private Button kill;
    public static MainActivity instance;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        Utils.checkNetWork(this, false);
        initData();
        huaweiPush();
        load();
        checkNewAPK();
    }

    private void checkNewAPK() {
        //apkUrl , apkVersion
        String apkUrl = getIntent().getStringExtra("apkUrl");
        String apkVersion = getIntent().getStringExtra("apkVersion");
        if (TextUtils.isEmpty(apkUrl)) {
            return;
        }
        if (TextUtils.isEmpty(apkVersion)) {
            return;
        }
        if (Utils.getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
            downloadSilently(apkUrl, apkVersion);
        }
    }

    private void downloadSilently(final String apkUrl, String version) {
        final String savedFilePath = "/mnt/sdcard/cache/" + ZIP + "_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            MLog.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        int isWifi = NetworkUtil.isWifi(this);
        if (isWifi == 1) {
            startDownloadAPK(apkUrl, savedFilePath);
        } else if (isWifi == 0) {
            MLog.d("test", "不是wifi...");
            //提示4G
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            dialog_download = builder.setMessage("有新的版本需要更新，您当前的网络是4G，确定使用流量下载新的APK吗？").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    toast("后台下载APK文件");
                    dialog_download.dismiss();
                    startDownloadAPK(apkUrl, savedFilePath);
                }
            }).setPositiveButton(android.R.string.cancel, null).create();
            dialog_download.show();
        }
    }

    private void startDownloadAPK(String apkUrl, final String savedFilePath) {
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage("发现新的版本，是否更新？本次更新不消耗流量。").setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog_download.dismiss();
                Message msg = new Message();
                msg.what = 4;
                msg.obj = savedFilePath;
                mHandler.sendMessage(msg);
            }
        }).setPositiveButton(android.R.string.cancel, null).create();
        dialog_download.show();
    }


    private void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (X5WebView) findViewById(R.id.wv);
        kill = (Button) findViewById(R.id.kill);
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
        final String event = getSharedPreferences("kiway", 0).getString("event", "");
        MLog.d("test", "取了一个event = " + event);
        if (TextUtils.isEmpty(event)) {
            return;
        }
        getSharedPreferences("kiway", 0).edit().putString("event", "").commit();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //告诉前端做动作。
                MLog.d("test", "notificationCallback");
                wv.loadUrl("javascript:notificationCallback('" + event + "')");
            }
        });
    }

    private void load() {
        wv.loadUrl("file://" + Configue.ROOT + Configue.HTML);
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
        wv.addJavascriptInterface(new JsAndroidInterface(this, wv), "wx");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String url = wv.getUrl();
            MLog.d("test", "host = " + url);
            if (url.endsWith("login") || url.endsWith("exam") || url.endsWith("mine") || url.endsWith("message") || url.endsWith("index")) {
                doFinish();
                return true;
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
        //getSharedPreferences("kiway", 0).edit().putString("accessToken", "123456").commit();
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
                    //无网络
                    MLog.d("test", "无网络");
                } else {
                    rl_nonet.setVisibility(View.GONE);
                    //有网络
                    MLog.d("test", "有网络");
                    if (arg2 == 1) {
                        wv.loadUrl("javascript:reConnect()");
                    }
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
                CountlyUtil.getInstance().addEvent("升级APP");
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
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