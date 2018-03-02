package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.parent.R;
import cn.kiway.mdm.util.FileUtils;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.NetworkUtil;
import cn.kiway.mdm.util.Utils;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.mdm.WXApplication.url;
import static cn.kiway.mdm.util.Utils.getCurrentVersion;


public class MainActivity extends BaseActivity {
    private static final String currentPackageVersion = "0.0.1";

    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    protected ProgressDialog pd;
    private int lastProgress;
    private WebView wv;
    private LinearLayout layout_welcome;
    public static MainActivity instance;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        Utils.checkNetWork(this, false);
        checkIsFirst();
        initData();
        load();
        checkNewVersion();
    }


    private void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (WebView) findViewById(R.id.wv);
        layout_welcome = (LinearLayout) findViewById(R.id.layout_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void load() {
        wv.clearCache(true);
        String url = "file://" + WXApplication.ROOT + WXApplication.HTML;
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
        com.tencent.smtt.sdk.WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        if (Utils.isTabletDevice(this)) {
            settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.LARGER);
        } else {
            settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.NORMAL);
        }

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("test", "onPageFinished url = " + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("test", "shouldInterceptRequest url = " + url);
                if ((url.startsWith("http") || url.startsWith("https"))
                        && (url.endsWith("jpg") || url.endsWith("JPG") || url.endsWith("jpeg") || url.endsWith("JPEG") || url.endsWith("png") || url.endsWith("PNG"))) {
                    InputStream is = getStreamByUrl(url);
                    if (is == null) {
                        return super.shouldInterceptRequest(view, url);
                    }
                    return new WebResourceResponse(getMimeType(url), "utf-8", is);
                }
                //des解密用
//                else if (url.endsWith("js") || url.endsWith("css") || url.endsWith("html")) {
//                    InputStream is = getStreamByUrl2(url.replace("file://", ""));
//                    return new WebResourceResponse(getMimeType(url), "utf-8", is);
//                }
                return super.shouldInterceptRequest(view, url);
            }
        });

        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "wx");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String url = wv.getUrl();
            Log.d("test", "url = " + url);
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

    private static final int SNAPSHOT = 9999;
    private static final int SELECT_PHOTO = 8888;
    private static final int SAOMAWANG = 7777;
    private static final int QRSCAN = 6666;
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;
    private String snapshotFile;

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        @JavascriptInterface
        public String getOS() {
            Log.d("test", "getOS is called");
            return "Android";
        }

        @JavascriptInterface
        public String getIMEI() {
            return Utils.getIMEI(MainActivity.this);
        }

        @JavascriptInterface
        public String getHost() {
            return WXApplication.url;
        }

        @JavascriptInterface
        public void scanQR() {
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), QRSCAN);
        }

        @JavascriptInterface
        public String getCacheSize() {
            return "1.5M";
        }

        @JavascriptInterface
        public void clearCache() {
            toast("清理完毕");
        }

        @JavascriptInterface
        public void showPhoto(String param1, String param2) {
            try {
                try {
                    Log.d("test", "showPhoto param1 = " + param1);
                    Log.d("test", "showPhoto param2 = " + param2);
                    ViewPagerActivity.sDrawables = param1.replace("[", "").replace("]", "").replace("\"", "").split(",");
                    Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                    intent.putExtra("position", Integer.parseInt(param2));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public String getVersionCode() {
            Log.d("test", "getVersionCode");
            return getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
        }

        @JavascriptInterface
        public void record() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startRecord();
                    final Dialog dialog = new Dialog(MainActivity.this, R.style.popupDialog);
                    dialog.setContentView(R.layout.dialog_voice);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    final Button voice = (Button) dialog.findViewById(R.id.voice);
                    final TextView time = (TextView) dialog.findViewById(R.id.time);
                    voice.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            stopRecord();
                            dialog.dismiss();
                        }
                    });
                    new Thread() {
                        @Override
                        public void run() {
                            int duration = 0;
                            while (dialog.isShowing()) {
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                duration++;
                                final int finalDuration = duration;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        time.setText(Utils.getTimeFormLong(finalDuration * 1000));
                                    }
                                });
                            }
                        }
                    }.start();
                }
            });
        }

        @JavascriptInterface
        public void selectPhoto() {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setImageLoader(new GlideImageLoader());// 图片加载器
            imagePicker.setSelectLimit(1);// 设置可以选择几张
            imagePicker.setMultiMode(false);// 是否为多选
            imagePicker.setCrop(true);// 是否剪裁
            imagePicker.setFocusWidth(1000);// 需要剪裁的宽
            imagePicker.setFocusHeight(1000);// 需要剪裁的高
            imagePicker.setStyle(CropImageView.Style.RECTANGLE);// 方形
            imagePicker.setShowCamera(true);// 是否显示摄像

            Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, SELECT_PHOTO);
        }

        @JavascriptInterface
        public void snapshot() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            snapshotFile = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
            Uri uri = Uri.fromFile(new File(snapshotFile));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, SNAPSHOT);
        }

        @JavascriptInterface
        public void setRequestedOrientation(String screen) {
            Log.d("test", "setRequestedOrientation = " + screen);
            if (Integer.parseInt(screen) == 1) {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
    }

    //录音
    private void startRecord() {
        try {
            // 判断，若当前文件已存在，则删除
            String path = "/mnt/sdcard/voice/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            recordFile = new File(path + System.currentTimeMillis() + ".mp3");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setAudioChannels(2);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//输出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//编码格式
            mediaRecorder.setAudioEncodingBitRate(16);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            toast("录制声音失败，请检查SDcard");
        }
        start = System.currentTimeMillis();
    }

    private void stopRecord() {
        if (recordFile == null) {
            return;
        }
        if (mediaRecorder == null) {
            return;
        }

        mediaRecorder.stop();

        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("太短了");
            return;
        }

        wv.loadUrl("javascript:recordCallback('file://" + recordFile.getAbsolutePath() + "')");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SNAPSHOT && resultCode == RESULT_OK) {
            Log.d("test", "snapshotCallback");
//            wv.loadUrl("javascript:snapshotCallback('file://" + snapshotFile + "')");
            Log.d("test", "压缩前大小" + new File(snapshotFile).length());
            File newFile = CompressHelper.getDefault(this).compressToFile(new File(snapshotFile));
            Log.d("test", "压缩后大小" + newFile.length());
            wv.loadUrl("javascript:snapshotCallback('file://" + newFile.getAbsolutePath() + "')");
        } else if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;
            Log.d("test", "path = " + path);
            wv.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        } else if (requestCode == SAOMAWANG) {
            if (data == null) {
                return;
            }
            int responseCode = data.getIntExtra("RESULT_OK", -1);
            Log.d("test", "responseCode = " + responseCode);
        } else if (requestCode == QRSCAN) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            Log.d("test", "result = " + result);
            wv.loadUrl("javascript:scanQRCallback('" + result + "')");
        }
    }


    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    checkTimeout();
                    HttpGet httpRequest = new HttpGet(url + "/static/download/version/zip_parent.json");
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
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
                    Log.d("test", "无网络");
                } else {
                    rl_nonet.setVisibility(View.GONE);
                    //有网络
                    Log.d("test", "有网络");
                    if (arg2 == 1) {
                        wv.loadUrl("javascript:reConnect()");
                    }
                }
                return;
            }
            if (isJump) {
                return;
            }
            isSuccess = true;
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");

                    String zipCode = new JSONObject(ret).getString("zipCode");
                    String zipUrl = new JSONObject(ret).getString("zipUrl");

                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
//                        showUpdateConfirmDialog(apkUrl);
                        downloadSilently(apkUrl, apkVersion);
                        jump(false);
                    } else {
                        //如果APK没有最新版本，比较包的版本。如果内置包的版本号比较高，直接替换
                        boolean flag = false;
                        String currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        if (currentPackage.compareTo(currentPackageVersion) < 0) {
                            Log.d("test", "内置包的版本大，替换新包");
                            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
                            checkIsFirst();
                            flag = true;
                        }
                        //替换完内置包之后，比较内置包和外包，如果版本号还是小了，更新外包
                        currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        String outer_package = zipCode;
                        if (currentPackage.compareTo(outer_package) < 0) {
                            //提示有新的包，下载新的包
                            Log.d("test", "下载新的H5包");
                            updatePackage(outer_package, zipUrl);
                        } else {
                            Log.d("test", "H5包是最新的");
                            jump(flag);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jump(false);
                }
            } else if (msg.what == 3) {
                jump(false);
            } else if (msg.what == 4) {
                pd.dismiss();
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            } else if (msg.what == 5) {
                pd.dismiss();
                toast(R.string.downloadfailure);
                jump(false);
            } else if (msg.what == 6) {
                pd.setProgress(msg.arg1);
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_parent_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
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
        }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jump(false);
            }
        }).create();
        dialog_download.show();
    }

    private void updatePackage(final String outer_package, final String downloadUrl) {
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "updatePackage downloadUrl = " + downloadUrl);
                final int ret = new HttpDownload().downFile(downloadUrl, WXApplication.ROOT, WXApplication.ZIP);
                Log.d("test", "下载新包 ret = " + ret);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ret == -1) {
                            jump(false);
                            return;
                        }
                        Log.d("test", "删除旧包");
                        if (new File(WXApplication.ROOT + "mdm_parent").exists()) {
                            FileUtils.delFolder(WXApplication.ROOT + "mdm_parent");
                        }
                        try {
                            Log.d("test", "解压新包");
                            new ZipFile(WXApplication.ROOT + WXApplication.ZIP).extractAll(WXApplication.ROOT);
                        } catch (ZipException e) {
                            e.printStackTrace();
                        }
                        //解压完毕，删掉zip文件
                        new File(WXApplication.ROOT + WXApplication.ZIP).delete();
                        Log.d("test", "解压完毕");
                        getSharedPreferences("kiway", 0).edit().putString("version_package", outer_package).commit();
                        jump(true);
                    }
                });
            }
        }.start();


    }

    protected void showUpdateConfirmDialog(final String url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(R.string.getnewversion).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dialog_download.dismiss();
                downloadNewVersion(url);
            }
        }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                jump(false);
            }
        }).create();
        dialog_download.setCancelable(false);
        dialog_download.show();
    }

    protected void downloadNewVersion(final String urlString) {
        pd.setMessage(getString(R.string.downloading));
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();
        pd.setCancelable(false);
        pd.setProgress(0);

        new Thread() {
            @SuppressWarnings("resource")
            public void run() {
                try {
                    InputStream input = null;
                    HttpURLConnection urlConn = null;
                    URL url = new URL(urlString);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("Accept-Encoding", "identity");
                    urlConn.setReadTimeout(10000);
                    urlConn.setConnectTimeout(10000);
                    input = urlConn.getInputStream();
                    int total = urlConn.getContentLength();
                    File file = null;
                    OutputStream output = null;
                    if (!new File("/mnt/sdcard/cache/").exists()) {
                        new File("/mnt/sdcard/cache/").mkdirs();
                    }
                    String savedFilePath = "/mnt/sdcard/cache/mdm_parent.apk";
                    file = new File(savedFilePath);
                    output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int temp = 0;
                    int read = 0;
                    while ((temp = input.read(buffer)) != -1) {
                        output.write(buffer, 0, temp);
                        read += temp;
                        float progress = ((float) read) / total;
                        int progress_int = (int) (progress * 100);
                        if (lastProgress != progress_int) {
                            lastProgress = progress_int;
                            Message msg = new Message();
                            msg.what = 6;// downloading
                            msg.arg1 = progress_int;
                            mHandler.sendMessage(msg);
                        } else {
                            // do not send msg
                        }
                    }
                    output.flush();
                    output.close();
                    input.close();
                    Message msg = new Message();
                    msg.what = 4;
                    msg.obj = savedFilePath;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(5);
                }
            }
        }.start();
    }

    protected void checkTimeout() {
        new Thread() {
            public void run() {
                try {
                    sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess) {
                    return;
                }
                jump(false);
            }
        }.start();
    }

    protected void checkIsFirst() {
        if (!checkFileComplete()) {
            Log.d("test", "文件不完整");
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
        }
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.ROOT).exists()) {
                new File(WXApplication.ROOT).mkdirs();
            }
            //拷贝
            FileUtils.copyRawToSdcard(this, R.raw.mdm_parent, WXApplication.ZIP);
            //解压
            try {
                new ZipFile(WXApplication.ROOT + WXApplication.ZIP).extractAll(WXApplication.ROOT);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            //解压完毕，删掉zip文件
            new File(WXApplication.ROOT + WXApplication.ZIP).delete();
            getSharedPreferences("kiway", 0).edit().putString("version_package", currentPackageVersion).commit();
        }
    }

    private boolean checkFileComplete() {
        if (!new File(WXApplication.ROOT + WXApplication.HTML).exists()) {
            return false;
        }
        return true;
    }

    public void jump(final boolean refresh) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layout_welcome.setVisibility(View.GONE);
                if (refresh) {
                    load();
                }
                //更新完成完成
            }
        });
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}