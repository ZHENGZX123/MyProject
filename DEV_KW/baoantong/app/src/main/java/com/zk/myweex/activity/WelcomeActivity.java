package com.zk.myweex.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.WXApplication;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.entity.ZipPackage;
import com.zk.myweex.utils.FileUtils;
import com.zk.myweex.utils.MyDBHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.baoantong.R;

import static com.zk.myweex.utils.Utils.getCurrentVersion;

public class WelcomeActivity extends WXBaseActivity {

    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    private ProgressDialog pd;
    private int lastProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);


    }

    private void jump() {
        new Thread() {
            @Override
            public void run() {
                if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }
                finish();
            }
        }.start();
    }

    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    checkTimeout();
                    HttpGet httpRequest = new HttpGet("http://yqyd.qgjydd.com/yqyd/static/version/zip_xs.json");
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

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (isJump) {
                return;
            }
            isSuccess = true;
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    Log.d("test", "新版本返回值" + ret);
                    String version = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");
                    Log.d("test", "current = " + getCurrentVersion(getApplicationContext()));
                    if (getCurrentVersion(getApplicationContext()).compareTo(version) < 0) {
                        showUpdateConfirmDialog(apkUrl);
                    } else {
                        jump();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jump();
                }
            } else if (msg.what == 3) {
                jump();
            } else if (msg.what == 4) {
                pd.dismiss();
                toast(R.string.downloadsuccess);
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
                jump();
            } else if (msg.what == 6) {
                pd.setProgress(msg.arg1);
            }
        }
    };

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
                jump();
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
                    if (!new File(Environment.getExternalStorageDirectory().getPath() + "/cache").exists()) {
                        new File(Environment.getExternalStorageDirectory().getPath() + "/cache").mkdirs();
                    }
                    String savedFilePath = Environment.getExternalStorageDirectory().getPath() + "/cache/bat.apk";
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
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isSuccess) {
                    return;
                }
                jump();
            }
        }.start();
    }

    private void showNewVersionDialog(final String filename) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                AlertDialog dialog_download = builder.setTitle("提示").setMessage("有新的版本，是否安装新版本，过程不消耗流量").
                        setNegativeButton("安装", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                getSharedPreferences("kiway", 0).edit().putLong("refuse", 0).apply();
                                String savedFilePath = WXApplication.PATH_APK + filename;
                                Intent intent = new Intent();
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getSharedPreferences("kiway", 0).edit().putLong("refuse", System.currentTimeMillis()).apply();
                            }
                        })
                        .setCancelable(false)
                        .create();
                dialog_download.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRunTimePermission(
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                },
                mListener);
    }

    private final int REQUEST_CODE = 1;
    private IPermission mListener = new IPermission() {
        @Override
        public void onGranted() {
            toast("所有权限被接受");
            checkIsFirst();
            checkHttpConf();
            checkNewVersion();
        }

        @Override
        public void onDenied(List<String> deniedPermissions) {
            toast("有权限被拒绝");
            finish();
        }
    };

    private void checkHttpConf() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String httpcacheFile = WXFileUtils.findLoginJS(WXApplication.PATH, "conf.txt");
                    Log.d("test", "httpcacheFile = " + httpcacheFile);
                    if (httpcacheFile == null) {
                        return;
                    }
                    String content = WXFileUtils.readFileInZip(httpcacheFile);
                    Log.d("test", "cache = " + content);
                    JSONObject o = new JSONObject(content);
                    JSONArray hc = o.getJSONArray("http_caches");
                    JSONArray ot = o.getJSONArray("offline_task");
                    ArrayList hcs = new ArrayList();
                    ArrayList ots = new ArrayList();
                    for (int i = 0; i < hc.length(); i++) {
                        String url = hc.getJSONObject(i).getString("url");
                        hcs.add(url);
                    }
                    for (int i = 0; i < ot.length(); i++) {
                        String url = ot.getJSONObject(i).getString("url");
                        ots.add(url);
                    }
                    WXSDKEngine.setHttpConfig(hcs, ots);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkIsFirst() {
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();

            //1.mkdirs
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.PATH).exists()) {
                new File(WXApplication.PATH).mkdirs();
            }
            if (!new File(WXApplication.PATH_BACKUP).exists()) {
                new File(WXApplication.PATH_BACKUP).mkdirs();
            }
            if (!new File(WXApplication.PATH_TMP).exists()) {
                new File(WXApplication.PATH_TMP).mkdirs();
            }
            if (!new File(WXApplication.PATH_APK).exists()) {
                new File(WXApplication.PATH_APK).mkdirs();
            }
            //2.拷贝。。。
            FileUtils.copyRawToSdcard(this, R.raw.bat, "batTab0.zip");
            FileUtils.copyRawToSdcard(this, R.raw.bat, "batTab1.zip");
            FileUtils.copyRawToSdcard(this, R.raw.bat, "batTab2.zip");

            //3.插入数据库
            TabEntity tab0 = new TabEntity();
            tab0.name = "首页";
            tab0.idStr = "batTab0";
            TabEntity tab1 = new TabEntity();
            tab1.name = "书库";
            tab1.idStr = "batTab1";
            TabEntity tab2 = new TabEntity();
            tab2.name = "我的";
            tab2.idStr = "batTab2";
            new MyDBHelper(getApplicationContext()).addTabEntity(tab0);
            new MyDBHelper(getApplicationContext()).addTabEntity(tab1);
            new MyDBHelper(getApplicationContext()).addTabEntity(tab2);

            ZipPackage zip0 = new ZipPackage();
            zip0.name = "batTab0.zip";
            zip0.indexPath = "bat/dist/tab0.js";
            zip0.version = "1.0.0";
            zip0.patchs = "";
            ZipPackage zip1 = new ZipPackage();
            zip1.name = "batTab1.zip";
            zip1.indexPath = "bat/dist/tab1.js";
            zip1.version = "1.0.0";
            zip1.patchs = "";
            ZipPackage zip2 = new ZipPackage();
            zip2.name = "batTab2.zip";
            zip2.indexPath = "bat/dist/tab2.js";
            zip2.version = "1.0.0";
            zip2.patchs = "";
            new MyDBHelper(getApplicationContext()).addZipPackage(zip0);
            new MyDBHelper(getApplicationContext()).addZipPackage(zip1);
            new MyDBHelper(getApplicationContext()).addZipPackage(zip2);
        }
    }

    interface IPermission {
        //权限被允许时的回调
        void onGranted();

        //权限被拒绝时的回调， 参数：被拒绝权限的集合
        void onDenied(List<String> deniedPermissions);
    }

    //申请权限的方法
    public void requestRunTimePermission(String[] permissions, IPermission listener) {
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), REQUEST_CODE);
        } else {
            checkIsFirst();
            checkHttpConf();
            checkNewVersion();
        }
    }

    //当用户拒绝或者同意权限时的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }
}
