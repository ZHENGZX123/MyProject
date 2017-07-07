package cn.kiway.homework.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.loader.GlideImageLoader;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nanchen.compresshelper.CompressHelper;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.homework.entity.HTTPCache;
import cn.kiway.homework.teacher.R;
import cn.kiway.homework.util.NetworkUtil;
import cn.kiway.homework.util.ResourceUtil;
import cn.kiway.homework.util.Utils;
import cn.kiway.homework.util.WXDBHelper;
import uk.co.senab.photoview.sample.ViewPagerActivity;


public class MainActivity extends BaseActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv = (WebView) findViewById(R.id.wv);
        initData();
        load();
    }

    private void load() {
        wv.loadUrl("file:///android_asset/dist/index.html");
//        wv.loadUrl("file:///android_asset/test2.html");
//        wv.loadUrl("file://" + WXApplication.ROOT + WXApplication.HTML);
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
        settings.setDomStorageEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("test", "shouldInterceptRequest url = " + url);
                if (url.endsWith("jpg") || url.endsWith("JPG") || url.endsWith("jpeg") || url.endsWith("JPEG")
                        || url.endsWith("png") || url.endsWith("PNG")) {
                    InputStream is = getStreamByUrl(url);
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
        wv.setWebChromeClient(new WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(), "wx");
    }

    private long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("test", "click back = " + wv.getUrl());
            String url = wv.getUrl();
            String page = url.substring(url.lastIndexOf("/") + 1);
            if (page.endsWith("login") || page.endsWith("main")) {
                long t = System.currentTimeMillis();
                if (t - time >= 2000) {
                    time = t;
                    Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                } else
                    finish();
                return true;
            } else {
                if (wv.canGoBack()) {
                    wv.goBack();//返回上一页面
                    return true;
                } else {
                    long t = System.currentTimeMillis();
                    if (t - time >= 2000) {
                        time = t;
                        Toast.makeText(this, "再按一下退出", Toast.LENGTH_SHORT).show();
                    } else
                        finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private static final int SNAPSHOT = 9999;
    private static final int SELECT_PHOTO = 8888;
    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;
    private String snapshotFile;

    public class JsAndroidInterface {
        public JsAndroidInterface() {
        }

        @JavascriptInterface
        public void showPhoto(String param1, String param2) {
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
        }

        @JavascriptInterface
        public void share() {
            Log.d("test", "前端来做");
        }

        @JavascriptInterface
        public String getVersionCode() {
            return Utils.getCurrentVersion(MainActivity.this);
        }

        @JavascriptInterface
        public void record() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Dialog dialog = new Dialog(MainActivity.this, R.style.popupDialog);
                    dialog.setContentView(R.layout.dialog_voice);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    final Button voice = (Button) dialog.findViewById(R.id.voice);
                    voice.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            if (voice.getText().toString().equals("开始")) {
                                startRecord();
                                voice.setText("结束");
                            } else if (voice.getText().toString().equals("结束")) {
                                stopRecord();
                                dialog.dismiss();
                            }
                        }
                    });
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
            //为拍摄的图片指定一个存储的路径
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

        @JavascriptInterface
        public void httpRequest(final String url, final String param,
                                final String method, String reload) {
            Log.d("test", "httpRequest url = " + url + " , param = " + param + " , method = " + method + " , reload = " + reload);
            if (reload.equals("1")) {
                //1.重新获取
                doHttpRequest(url, param, method);
            } else {
                //2.取缓存
                String request = url + param + method;
                HTTPCache cache1 = new WXDBHelper(MainActivity.this).getHttpCacheByRequest(request);
                if (cache1 == null) {
                    //3.如果是查询题目的话，还要再查一下资源包
                    HTTPCache cache2 = new ResourceUtil(MainActivity.this).searchResourceByRequest(request);
                    if (cache2 == null) {
                        doHttpRequest(url, param, method);
                    } else {
                        httpCallback(url, cache2.response);
                    }
                } else {
                    httpCallback(url, cache1.response);
                }
            }
        }
    }

    private void doHttpRequest(final String url, final String param, final String method) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!NetworkUtil.isNetworkAvailable(MainActivity.this)) {
                        toast("没有网络");
                        return;
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    if (method.equalsIgnoreCase("POST")) {
                        StringEntity stringEntity = new StringEntity(param, "utf-8");
                        client.post(MainActivity.this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1, String ret) {
                                Log.d("test", "post onSuccess = " + ret);
                                saveDB(url, param, method, ret);
                                httpCallback(url, ret);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                                Log.d("test", "post onFailure = " + arg2);
                                httpCallback(url, "failure");
                            }
                        });
                    } else if (method.equalsIgnoreCase("GET")) {
                        client.get(MainActivity.this, url, new TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, String ret) {
                                Log.d("test", "get onSuccess = " + ret);
                                saveDB(url, param, method, ret);
                                httpCallback(url, ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                Log.d("test", "get onFailure = " + s);
                                httpCallback(url, "failure");
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "exception = " + e.toString());
                }
            }
        });
    }

    private void saveDB(String url, String param, String method, String ret) {
        Log.d("test", "saveDB");
        HTTPCache cache = new HTTPCache();
        cache.request = url + param + method;
        cache.response = ret;
        cache.requesttime = "" + System.currentTimeMillis();
        new WXDBHelper(this).addHTTPCache(cache);
    }

    private void httpCallback(final String url, final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "httpCallback , url = " + url + " , result = " + result);
                wv.loadUrl("javascript:httpRequestCallback(\"" + url + "\" , \"" + result + "\")");
            }
        });
    }

    private void startRecord() {
        try {
            //录音并上传
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
            wv.loadUrl("javascript:snapshotCallback('file://" + snapshotFile + "')");
        } else if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            Log.d("test", "images count = " + images.size());
            if (!isOrig) {
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
            }
            String path = images.get(0).path;
            wv.loadUrl("javascript:selectPhotoCallback('" + path + "')");
        }
    }

}