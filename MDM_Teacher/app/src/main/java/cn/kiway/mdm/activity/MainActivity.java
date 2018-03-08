package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.utils.Constant;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Logger;
import cn.kiway.mdm.util.NetworkUtil;
import cn.kiway.mdm.util.UploadUtil;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.web.JsAndroidInterface;
import cn.kiway.mdm.web.MyWebViewClient;
import cn.kiway.mdm.zbus.ZbusMessageHandler;
import cn.kiway.zbus.utils.ZbusUtils;
import io.zbus.mq.Broker;
import io.zbus.mq.Producer;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.kiway.mdm.util.Constant.clientUrl;
import static cn.kiway.mdm.util.ResultMessage.QRSCAN;
import static cn.kiway.mdm.util.ResultMessage.SELECT_PHOTO;
import static cn.kiway.mdm.util.Utils.getCurrentVersion;
import static cn.kiway.mdm.web.JsAndroidInterface.REQUEST_ORIGINAL;
import static cn.kiway.mdm.web.JsAndroidInterface.accessToken;
import static cn.kiway.mdm.web.JsAndroidInterface.picPath;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile2;
import static cn.kiway.mdm.web.WebJsCallBack.accpterFilePath;


public class MainActivity extends BaseActivity {

    private static final String currentPackageVersion = "0.0.3";
    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    public ProgressDialog pd;
    private WebView wv;
    private LinearLayout layout_welcome;
    public static MainActivity instance;
    private long time;
    private JsAndroidInterface jsInterface;
    public static final int MSG_NETWORK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        initView();
        Utils.checkNetWork(this, false);
        initData();
        load();
        initZbus();
        initRecorder();
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
        if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
            downloadSilently(apkUrl, apkVersion);
        }
    }

    private void initRecorder() {
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            KWApplication.recordService = binder.getRecordService();
            KWApplication.recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    //初始化zbus
    public void initZbus() {
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String userId = Utils.getIMEI(getApplicationContext());
                    if (TextUtils.isEmpty(userId)) {
                        return;
                    }
                    Broker broker = new Broker(cn.kiway.mdm.util.Constant.zbusHost + ":" + cn.kiway.mdm.util.Constant.zbusPost);
                    Producer p = new Producer(broker);
                    ZbusUtils.init(broker, p);
                    String topic = "kiway_push_" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsgs(topic, new ZbusMessageHandler(), cn.kiway.mdm.util.Constant.zbusHost + ":" + cn.kiway.mdm.util.Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (WebView) findViewById(R.id.wv);
        layout_welcome = (LinearLayout) findViewById(R.id.layout_welcome);
    }

    private void load() {
        wv.loadUrl("file://" + KWApplication.ROOT + KWApplication.HTML);
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
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        jsInterface = new JsAndroidInterface(this);
        wv.addJavascriptInterface(jsInterface, "scoket");
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

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void doFinish() {
        long t = System.currentTimeMillis();
        if (t - time >= 2000) {
            time = t;
            toast("再按一次退出");
        } else {
            //该关闭的关闭
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRSCAN) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            Log.d("test", "result = " + result);
            wv.loadUrl("javascript:scanQRCallback('" + result + "')");
        } else if (requestCode == requsetFile) {
            if (data == null)
                return;
            List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            String filePath = list.get(0);
            if (!filePath.contains("/kiwaymdm")) {
                toast(R.string.choose_file);
                return;
            }
            uploadFile(filePath, false);
        } else if (requestCode == requsetFile2) {
            if (data == null)
                return;
            List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            String filePath = list.get(0);
            if (Utils.isImage(filePath)) {
                //需要裁剪的图片路径
                Uri sourceUri = Uri.fromFile(new File(filePath));
                //裁剪完毕的图片存放路径
                Uri destinationUri = Uri.fromFile(new File(filePath.split("\\.")[0] + "1." + filePath.split("\\.")[1]));
                Crop.of(sourceUri, destinationUri).start(this);
            } else {
                uploadFile(filePath, true);
            }
        } else if (requestCode == REQUEST_ORIGINAL) {
            //需要裁剪的图片路径
            Uri sourceUri = Uri.fromFile(new File(picPath));
            //裁剪完毕的图片存放路径
            Uri destinationUri = Uri.fromFile(new File(picPath.split("\\.")[0] + "1.png"));
            //UCrop.of(sourceUri, destinationUri).start(this);
            Crop.of(sourceUri, destinationUri).start(this);
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            final Uri resultUri = Crop.getOutput(data);
            //压缩图片
            Luban.with(this).load(resultUri.getPath()).ignoreBy(100).setTargetDir(getPath()).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pd == null)
                                pd = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
                            pd.show();
                            pd.setMessage(getString(R.string.yasuo));
                        }
                    });
                }

                @Override
                public void onSuccess(File file) {
                    uploadFile(file.getAbsolutePath(), true);
                }

                @Override
                public void onError(Throwable e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (pd != null)
                                pd.dismiss();
                            Toast.makeText(MainActivity.this, getString(R.string.yasuoshibai), Toast
                                    .LENGTH_SHORT).show();
                        }
                    });
                }
            }).launch();    //启动压缩
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == Crop.RESULT_ERROR) {   //出错时进入该分支
            final Throwable cropError = Crop.getError(data);
            Logger.log("cropError:::::" + cropError.toString());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, getString(R.string.caijianshibai), Toast.LENGTH_SHORT).show();
                }
            });
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

            uploadFile(path, false);
            //wv.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        }
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/kiway_mdm_teacher/pic/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public void uploadFile(final String filePath, final boolean copy) {
        boolean isImage = false;
        if (Utils.isImage(filePath))
            isImage = true;
        final File file = new File(filePath);
        pd.show();
        pd.setMessage(getString(R.string.upload));
        final boolean finalIsImage = isImage;
        new Thread() {
            @Override
            public void run() {
                final String ret = UploadUtil.uploadFile(file, clientUrl + "/common/file?x-auth-token=" +
                        accessToken, file
                        .getName());
                Log.d("test", "upload ret = " + ret);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            pd.dismiss();
                            if (finalIsImage && file.getName().endsWith("1.png"))//因为剪切会多一张图，所以不管上传成功失败都要删除
                                file.delete();
                            if (TextUtils.isEmpty(ret)) {
                                toast(getString(R.string.upload_fialt));
                                return;
                            }
                            JSONObject obj = new JSONObject(ret);
                            if (obj.optInt("statusCode") != 200) {
                                toast(getString(R.string.upload_fialt));
                                return;
                            }
                            String url = obj.optJSONObject("data").optString("url");
                            Log.d("test", "obj = " + obj.toString());
                            wv.loadUrl(accpterFilePath.replace("fileName", filePath.split("/")[filePath.split("/")
                                    .length - 1]).replace("filePath", url).replace("fileSize", file.length() + ""));
                            //2.拷贝到缓存
                            if (copy) {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                    Log.d("test", "无网络");
                } else {
                    rl_nonet.setVisibility(View.GONE);
                    Log.d("test", "有网络");
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
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

    private void downloadSilently(String apkUrl, String version) {
        final String savedFilePath = "/mnt/sdcard/cache/mdm_teacher_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        int isWifi = NetworkUtil.isWifi(this);
        if (isWifi == 1) {
            startDownloadAPK(apkUrl, savedFilePath);
        } else if (isWifi == 0) {
            Log.d("test", "不是wifi...");
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

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        //ZbusUtils.close();
    }
}