package cn.kiway.homework.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.storage.OnObbStateChangeListener;
import android.os.storage.StorageManager;
import android.util.Log;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.kiway.homework.WXApplication;
import cn.kiway.homework.teacher.R;
import cn.kiway.homework.util.FileUtils;

import static cn.kiway.homework.util.Utils.getCurrentVersion;


public class WelcomeActivity extends BaseActivity {

    private boolean isSuccess = false;
    private boolean isJump = false;
    private Dialog dialog_download;
    private ProgressDialog pd;
    private int lastProgress;
    private StorageManager storageManager;
    private String filename;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);

        checkIsFirst();
    }


    private void jump() {
        new Thread() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }

    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
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

                    //1.apk更新
                    //2.包更新
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
                    String savedFilePath = Environment.getExternalStorageDirectory().getPath() + "/cache/xtzy.apk";
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

    private void checkIsFirst() {
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.ROOT).exists()) {
                new File(WXApplication.ROOT).mkdirs();
            }
            //拷贝
            FileUtils.copyRawToSdcard(this, R.raw.xtzy_teacher, "xtzy_teacher.zip");
            //解压
            try {
                new ZipFile(WXApplication.ROOT + "xtzy_teacher.zip").extractAll(WXApplication.ROOT);
            } catch (ZipException e) {
                e.printStackTrace();
            }
        }
        checkNewVersion();
    }

}