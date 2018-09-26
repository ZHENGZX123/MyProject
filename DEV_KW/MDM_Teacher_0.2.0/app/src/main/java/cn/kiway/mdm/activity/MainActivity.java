package cn.kiway.mdm.activity;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.FileUtils;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.NetworkUtil;
import cn.kiway.mdm.util.UploadUtil;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.X5WebView;
import cn.kiway.mdm.web.JsAndroidInterface;
import cn.kiway.mdm.web.MyWebViewClient;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.RabbitMQUtils;
import cn.kiway.mdm.zbus.ZbusHost;
import cn.kiway.mdm.zbus.ZbusMessageHandler;

import static cn.kiway.mdm.WXApplication.url;
import static cn.kiway.mdm.WXApplication.zbusHost;
import static cn.kiway.mdm.WXApplication.zbusPort;
import static cn.kiway.mdm.util.Utils.getCurrentVersion;
import static cn.kiway.mdm.zbus.ZbusHost.channels;
import static cn.kiway.mdm.zbus.ZbusHost.consumeUtil;


public class MainActivity extends BaseActivity {
    private static final String currentPackageVersion = "0.2.0";

    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    public ProgressDialog pd;
    public X5WebView wv;
    private LinearLayout layout_welcome;
    public static MainActivity instance;
    private long time;
    private ZbusMessageHandler zbusMessageHandler;

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
        initZbus();
    }

    private void initView() {
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        wv = (X5WebView) findViewById(R.id.wv);
        layout_welcome = (LinearLayout) findViewById(R.id.layout_welcome);
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
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient());
        wv.addJavascriptInterface(new JsAndroidInterface(this, wv), "scoket");
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
            toast("再按一下返回退出");
        } else {
            finish();
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
                    HttpGet httpRequest = new HttpGet(url + "/static/download/version/zip_teacher.json");
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
            if (isJump) {
                return;
            }
            isSuccess = true;
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
            } else if (msg.what == 2) {
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
                        if (new File(WXApplication.ROOT + "mdm_teacher").exists()) {
                            FileUtils.delFolder(WXApplication.ROOT + "mdm_teacher");
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
            FileUtils.copyRawToSdcard(this, R.raw.mdm_teacher, WXApplication.ZIP);
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
            }
        });
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    public static final int SELECT_PHOTO = 8888;
    public final static String accpterFilePath = "javascript:accpterFilePath('fileName','filePath','fileSize')";//发送文件给web的回调
    public final static String sendCommandCallback = "javascript:onlineCallback('studentIMEI','command')";//发送文件给web的回调

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                    .EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            final String path = images.get(0).path;
            Log.d("test", "path = " + path);
            final File f = new File(path);
            new Thread() {
                @Override
                public void run() {
                    try {
                        String accessToken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                        final String ret = UploadUtil.uploadFile(f, WXApplication.url + "/common/file?x-auth-token=" + accessToken, f.getName());
                        Log.d("test", "upload ret = " + ret);
                        JSONObject obj = new JSONObject(ret);
                        final String url = obj.optJSONObject("data").optString("url");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                wv.loadUrl(accpterFilePath.replace("fileName", f.getName()).replace("filePath", url).replace("fileSize", f.length() + ""));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    //初始化zbus
    public void initZbus() {
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String userId = Utils.getIMEI(instance);
                    if (TextUtils.isEmpty(userId)) {
                        return;
                    }
                    String topic = "kiway_push_" + userId;
                    if (consumeUtil == null)
                        consumeUtil = new RabbitMQUtils(zbusHost, zbusPort);
                    Channel channel = consumeUtil.createChannel(topic, topic);
                    channels.add(channel);
                    consumeUtil.consumeMsg(new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties
                                properties, byte[] body) throws IOException {
                            String msg = new String(body, "utf-8");
                            System.out.println("consume msg: " + msg);
                            if (zbusMessageHandler == null)
                                zbusMessageHandler = new ZbusMessageHandler();
                            zbusMessageHandler.handle(msg);
                            super.getChannel().basicAck(envelope.getDeliveryTag(), false);
                        }
                    }, channel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZbusHost.closeMQ();
    }

    public void clickTest(View view) {
        ArrayList<String> students = new ArrayList<>();
        students.add("_9bb5b19c61be4ba300000804300CN01");//IMT1
        ZbusHost.sendMsg(this, "online", students, new OnListener() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }
}