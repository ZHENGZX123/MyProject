package cn.kiway.hybird.activity;

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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
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

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;

import cn.kiway.countly.CountlyUtil;
import cn.kiway.database.entity.HTTPCache;
import cn.kiway.database.util.KwDBHelper;
import cn.kiway.database.util.ResourceUtil;
import cn.kiway.hybird.teacher.R;
import cn.kiway.hybird.util.Utils;
import cn.kiway.hybird.view.X5WebView;
import cn.kiway.utils.BadgeUtil;
import cn.kiway.utils.Configue;
import cn.kiway.utils.MLog;
import cn.kiway.utils.NetworkUtil;
import cn.kiway.utils.UploadUtil;
import uk.co.senab.photoview.sample.ViewPagerActivity;


public class MainActivity extends BaseActivity {

    private Dialog dialog_download;
    protected ProgressDialog pd;
    private X5WebView wv;
    private RelativeLayout root;
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
        final String savedFilePath = "/mnt/sdcard/cache/xtzy_teacher_" + version + ".apk";
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
        root = (RelativeLayout) findViewById(R.id.root);
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
//        if (Utils.isTabletDevice(this)) {
//            settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.LARGER);
//        } else {
//            settings.setTextSize(com.tencent.smtt.sdk.WebSettings.TextSize.NORMAL);
//        }

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
//                if ((host.startsWith("http") || host.startsWith("https"))
//                        && (host.endsWith("jpg") || host.endsWith("JPG") || host.endsWith("jpeg") || host.endsWith("JPEG") || host.endsWith("png") || host.endsWith("PNG"))) {
//                    InputStream is = getStreamByUrl(host);
//                    if (is == null) {
//                        return super.shouldInterceptRequest(view, host);
//                    }
//                    return new WebResourceResponse(getMimeType(host), "utf-8", is);
//                }
                //des解密用
//                else if (host.endsWith("js") || host.endsWith("css") || host.endsWith("html")) {
//                    InputStream is = getStreamByUrl2(host.replace("file://", ""));
//                    return new WebResourceResponse(getMimeType(host), "utf-8", is);
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
        public void setKeyAndValue(String key, String value) {
            MLog.d("test", "setKeyAndValue is called , key = " + key + " , value = " + value);
            if (TextUtils.isEmpty(key)) {
                return;
            }
            //加密
            getSharedPreferences("kiway", 0).edit().putString(key, value).commit();
        }

        @JavascriptInterface
        public String getValueByKey(String key) {
            MLog.d("test", "getValueByKey is called , key = " + key);
            if (TextUtils.isEmpty(key)) {
                return "";
            }
            //解密
            String ret = getSharedPreferences("kiway", 0).getString(key, "");
            MLog.d("test", "ret = " + ret);
            return ret;
        }

        @JavascriptInterface
        public String getOS() {
            MLog.d("test", "getOS is called");
            return "Android";
        }

        @JavascriptInterface
        public void scanQR() {
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), QRSCAN);
        }


        @JavascriptInterface
        public void login(String param) {
            MLog.d("test", "login param = " + param);
            try {
                String accessToken = new JSONObject(param).optString("accessToken");
                String userId = new JSONObject(param).optString("userId");
                String schoolName = new JSONObject(param).optString("schoolName");
                getSharedPreferences("kiway", 0).edit().putString("accessToken", accessToken).commit();
                getSharedPreferences("kiway", 0).edit().putString("userId", userId).commit();
                getSharedPreferences("kiway", 0).edit().putString("schoolName", schoolName).commit();
                installationPush();
                //getBooks();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @JavascriptInterface
        public void logout() {
            MLog.d("test", "logout");
            uninstallationPush();
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
                    MLog.d("test", "showPhoto param1 = " + param1);
                    MLog.d("test", "showPhoto param2 = " + param2);
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
        public void fileUpload(String filepath) {
            filepath = filepath.replace("file://", "");
            MLog.d("test", "fileUpload , filepath = " + filepath);
            //选择图片
            final String finalFilepath = filepath;
            new Thread() {
                @Override
                public void run() {
                    String token = getSharedPreferences("kiway", 0).getString("accessToken", "");
                    MLog.d("test", "取出token=" + token);
                    File file = new File(finalFilepath);
                    final String ret = UploadUtil.uploadFile(file, Configue.host + String.format(Configue.url_upload, token), file.getName());
                    MLog.d("test", "upload ret = " + ret);
                    if (TextUtils.isEmpty(ret)) {
                        toast("上传图片失败，请稍后再试");
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject(ret);
                                String url = Configue.host + obj.getJSONObject("data").getString("host");
                                obj.getJSONObject("data").put("host", url);
                                MLog.d("test", "obj = " + obj.toString());
                                wv.loadUrl("javascript:fileUploadCallback(" + obj.toString() + ")");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }.start();
        }

        @JavascriptInterface
        public String getVersionCode() {
            MLog.d("test", "getVersionCode");
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
            MLog.d("test", "setRequestedOrientation = " + screen);
            if (Integer.parseInt(screen) == 1) {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }

        @JavascriptInterface
        public String isTest() {
            MLog.d("test", "isTest is called");
            return Configue.isTest ? "1" : "0";
        }

        @JavascriptInterface
        public void httpRequest(String url, String param, final String method, String time, String tagname, String related, String event) {
            if (!Configue.isTest) {
                url = url.replace(Configue.ceshiUrl, Configue.zhengshiUrl);
            }
            try {
                Integer.parseInt(time);
                param = param.replace("\\\"", "\"");
                if (param.startsWith("\"")) {
                    param = param.substring(1);
                }
                if (param.endsWith("\"")) {
                    param = param.substring(0, param.length() - 1);
                }
            } catch (Exception e) {
                MLog.d("test", "参数错误");
                return;
            }
            MLog.d("test", "httpRequest host = " + url + " , param = " + param + " , method = " + method + " , time = " + time + " , tagname = " + tagname + " , related = " + related + ", event = " + event);
            CountlyUtil.getInstance().addEvent(event);

            if (time.equals("0")) {
                //1.重新获取
                doHttpRequest(url, param, method, tagname, time, related);
            } else {
                //2.取缓存
                String request = url + param + method;
                HTTPCache cache1 = new KwDBHelper(MainActivity.this).getHttpCacheByRequest(request, Integer.parseInt(time));
                if (cache1 == null) {
                    MLog.d("test", "没有缓存");
                    //3.如果是查询题目的话，还要再查一下资源包
                    HTTPCache cache2 = new ResourceUtil(MainActivity.this).searchResourceByUrl(url, tagname);
                    if (cache2 == null) {
                        doHttpRequest(url, param, method, tagname, time, related);
                    } else {
                        httpRequestCallback(cache2.tagname, cache2.response);
                    }
                } else {
                    MLog.d("test", "有缓存");
                    httpRequestCallback(cache1.tagname, cache1.response);
                }
            }
        }
    }

    private void doHttpRequest(final String url, final String param, final String method, final String tagname, final String time, final String related) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    String token = getSharedPreferences("kiway", 0).getString("accessToken", "");
                    client.addHeader("X-Auth-Token", token);
                    if (method.equalsIgnoreCase("POST")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.post(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                MLog.d("test", "post onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                MLog.d("test", "post onFailure = " + ret);
                                MLog.d("test", "post onFailure = arg0" + arg0);
                                MLog.d("test", "post onFailure = arg3" + arg3.toString());
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("PUT")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.put(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                MLog.d("test", "put onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                MLog.d("test", "put onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    } else if (method.equalsIgnoreCase("GET")) {
                        String checkUrl = doCheckUrl(url);
                        MLog.d("test", "checkUrl = " + checkUrl);
                        client.get(MainActivity.this, checkUrl, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                MLog.d("test", "get onSuccess = " + ret);
                                saveDB(url, param, method, ret, tagname);
                                httpRequestCallback(tagname, ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                MLog.d("test", "get onFailure = " + ret);
                                //如果是get，把缓存回它
                                String request = url + param + method;
                                HTTPCache cache = new KwDBHelper(getApplicationContext()).getHttpCacheByRequest(request, Integer.parseInt(time));
                                if (cache != null) {
                                    httpRequestCallback(cache.tagname, cache.response);
                                } else {
                                    httpRequestCallback(tagname, ret);
                                }
                            }
                        });
                    } else if (method.equalsIgnoreCase("DELETE")) {
                        client.delete(MainActivity.this, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                MLog.d("test", "get onSuccess = " + ret);
                                httpRequestCallback(tagname, ret);
                                //如果是post，related不为空，查找一下相关的缓存，并清除掉
                                new KwDBHelper(getApplicationContext()).deleteHttpCache(related);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                MLog.d("test", "get onFailure = " + ret);
                                httpRequestCallback(tagname, ret);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    MLog.d("test", "exception = " + e.toString());
                }
            }
        });
    }

    private String doCheckUrl(String url) {
        //1.使用正则
        String split[] = url.split("\\?");
        if (split.length > 1) {
            String queryString = split[1];
            StringBuilder sb = new StringBuilder();
            for (String param : queryString.split("&")) {
                String[] paramSplit = param.split("=");
                String key = paramSplit[0];
                String value = "";
                if (paramSplit.length > 1) {
                    value = paramSplit[1];
                }
                sb.append(key).append("=");
                if (paramSplit.length > 1) {
                    sb.append(URLEncoder.encode(value));
                }
                sb.append("&");
            }
            //sb.deleteCharAt(sb.length() -1);
            queryString = sb.toString();
            url = split[0] + "?" + queryString;
        }
        return url;
    }

    public void clickSetToken(View view) {
        //getSharedPreferences("kiway", 0).edit().putString("accessToken", "123456").commit();
        new KwDBHelper(this).deleteAllHttpCache();
    }

    private void saveDB(String url, String param, String method, String ret, String tagname) {
        if (kill.getVisibility() == View.VISIBLE) {
            return;
        }
        if (TextUtils.isEmpty(ret)) {
            return;
        }
        if (ret.startsWith("<html>")) {
            return;
        }
        MLog.d("test", "saveDB");
        String request = url + param + method;
        HTTPCache cache = new KwDBHelper(this).getHttpCacheByRequest(request);
        if (cache == null) {
            cache = new HTTPCache();
            cache.request = request;
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            cache.tagname = tagname;
            new KwDBHelper(this).addHTTPCache(cache);
        } else {
            cache.response = ret;
            cache.requesttime = "" + System.currentTimeMillis();
            new KwDBHelper(this).updateHTTPCache(cache);
        }
    }

    private void httpRequestCallback(final String tagname, final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    String finalResult = "";
                    if (TextUtils.isEmpty(result)) {
                        finalResult = get503String();
                    } else if (result.startsWith("<html>")) {
                        finalResult = get503String();
                    } else {
                        finalResult = result;
                    }
                    String r = finalResult.replace("null", "\"\"").replace("\"\"\"\"", "\"\"");
                    MLog.d("test", "httpRequestCallback , tagname = " + tagname + " , result = " + r);
                    wv.loadUrl("javascript:" + tagname + "(" + r + ")");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String get503String() throws JSONException {
        JSONObject o = new JSONObject();
        o.put("data", "");
        o.put("errorMsg", "请求失败，请检查网络稍后再试");
        o.put("statusCode", 503);
        return o.toString();
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
            MLog.d("test", "snapshotCallback");
//            wv.loadUrl("javascript:snapshotCallback('file://" + snapshotFile + "')");
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