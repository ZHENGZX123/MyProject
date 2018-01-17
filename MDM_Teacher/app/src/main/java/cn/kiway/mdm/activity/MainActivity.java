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
import android.widget.Toast;

import com.leon.lfilepickerlibrary.utils.Constant;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.FileUtils;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.NetworkUtil;
import cn.kiway.mdm.util.UploadUtil;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.web.JsAndroidInterface;
import cn.kiway.mdm.web.MyWebViewClient;
import cn.kiway.mdm.zbus.ZbusMessageHandler;
import cn.kiway.web.kthd.zbus.ZbusConfiguration;
import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static cn.kiway.mdm.KWApplication.clientUrl;
import static cn.kiway.mdm.util.ResultMessage.QRSCAN;
import static cn.kiway.mdm.util.Utils.getCurrentVersion;
import static cn.kiway.mdm.web.JsAndroidInterface.REQUEST_ORIGINAL;
import static cn.kiway.mdm.web.JsAndroidInterface.accessToken;
import static cn.kiway.mdm.web.JsAndroidInterface.picPath;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile2;
import static cn.kiway.mdm.web.JsAndroidInterface.setFilePath;
import static cn.kiway.mdm.web.JsAndroidInterface.userAccount;
import static cn.kiway.mdm.web.WebJsCallBack.accpterFilePath;
import static cn.kiway.mdm.zbus.ZbusHost.zbusHost;
import static cn.kiway.mdm.zbus.ZbusHost.zbusPost;


public class MainActivity extends BaseActivity {

    private static final String currentPackageVersion = "0.3.0";
    private static final String zburPath = "file:///android_asset";
    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    public ProgressDialog pd;
    private WebView wv;
    private LinearLayout layout_welcome;
    public static MainActivity instance;
    private long time;
    private JsAndroidInterface jsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppData();
        instance = this;
        initView();
        Utils.checkNetWork(this, false);
        checkIsFirst();
        initData();
        load();
        checkNewVersion();
        initzbus();
        initRecorder();
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
    private void initzbus() {
        ZbusConfiguration.instance.setHost(zbusHost);
        ZbusConfiguration.instance.setPort(zbusPost);
        try {
            //// TODO: 2018/1/2
            ZbusUtils.consumeMsg("userId", new ZbusMessageHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getAppData() {
        Intent intent = getIntent();
        userAccount = "";
        if (intent != null && intent.getFlags() == Intent.FLAG_ACTIVITY_NEW_TASK) {
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            if (password == null || username == null) {
                userAccount = "";
            } else {
                userAccount = username + ":::" + password;
            }
        }
        Logger.log("----------------------" + userAccount);
    }

    public void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (WebView) findViewById(R.id.wv);
        layout_welcome = (LinearLayout) findViewById(R.id.layout_welcome);
    }

    private void load() {
        wv.clearCache(true);
        String url = "file://" + KWApplication.ROOT + KWApplication.HTML;
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
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());
        jsInterface = new JsAndroidInterface(this, wv);
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

    private void doFinish() {
        long t = System.currentTimeMillis();
        if (t - time >= 2000) {
            time = t;
            toast("再按一次退出");
        } else {
            //1.closeServer
            jsInterface.closeServer();
            //2.关闭录制
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
            setFilePath = filePath;
            uploadFile(filePath, false);
        } else if (requestCode == requsetFile2) {
            if (data == null)
                return;
            List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            String filePath = list.get(0);
            setFilePath = filePath;
            //1.上传
            uploadFile(filePath, true);

        } else if (requestCode == REQUEST_ORIGINAL) {
            if (resultCode != RESULT_OK)
                return;
            Luban.with(this).load(picPath).ignoreBy(100).setTargetDir(getPath()).setCompressListener(new OnCompressListener() {
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
                    uploadFile(file.getAbsolutePath(), false);
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

    public void uploadFile(String filePath, boolean copy) {
        File file = new File(filePath);
        pd.show();
        pd.setMessage(getString(R.string.upload));
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

    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    checkTimeout();
                    HttpGet httpRequest = new HttpGet(KWApplication.clientUrl + "/static/download/version/zip_teacher.json");
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
//                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
//                int arg1 = msg.arg1;
//                if (arg1 == 0) {
//                    rl_nonet.setVisibility(View.VISIBLE);
//                    //无网络
//                    Log.d("test", "无网络");
//                } else {
//                    rl_nonet.setVisibility(View.GONE);
//                    //有网络
//                }
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
        final String savedFilePath = "/mnt/sdcard/cache/mdm_teacher_" + version + ".apk";
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
        dialog_download = builder.setMessage(getString(R.string.new_version)).setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
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
                final int ret = new HttpDownload().downFile(downloadUrl, KWApplication.ROOT, KWApplication.ZIP);
                Log.d("test", "下载新包 ret = " + ret);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ret == -1) {
                            jump(false);
                            return;
                        }
                        Log.d("test", "删除旧包");
                        if (new File(KWApplication.ROOT + "mdm_teacher").exists()) {
                            FileUtils.delFolder(KWApplication.ROOT + "mdm_teacher");
                        }
                        try {
                            Log.d("test", "解压新包");
                            new ZipFile(KWApplication.ROOT + KWApplication.ZIP).extractAll(KWApplication.ROOT);
                        } catch (ZipException e) {
                            e.printStackTrace();
                        }
                        //解压完毕，删掉zip文件
                        new File(KWApplication.ROOT + KWApplication.ZIP).delete();
                        Log.d("test", "解压完毕");
                        getSharedPreferences("kiway", 0).edit().putString("version_package", outer_package).commit();
                        jump(true);
                    }
                });
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
            if (new File(KWApplication.ROOT).exists()) {
                FileUtils.delFolder(KWApplication.ROOT);
            }
            if (!new File(KWApplication.ROOT).exists()) {
                new File(KWApplication.ROOT).mkdirs();
            }
            //拷贝
            FileUtils.copyRawToSdcard(this, R.raw.mdm_teacher, KWApplication.ZIP);
            //解压
            try {
                new ZipFile(KWApplication.ROOT + KWApplication.ZIP).extractAll(KWApplication.ROOT);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            //解压完毕，删掉zip文件
            new File(KWApplication.ROOT + KWApplication.ZIP).delete();
            getSharedPreferences("kiway", 0).edit().putString("version_package", currentPackageVersion).commit();
        }
    }

    private boolean checkFileComplete() {
        if (!new File(KWApplication.ROOT + KWApplication.HTML).exists()) {
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
        unbindService(connection);
    }
}