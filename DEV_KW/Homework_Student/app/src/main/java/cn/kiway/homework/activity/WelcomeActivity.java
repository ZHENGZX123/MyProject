package cn.kiway.homework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

import cn.kiway.homework.WXApplication;
import cn.kiway.homework.student.R;
import cn.kiway.homework.util.CountlyUtil;
import cn.kiway.homework.util.FileUtils;
import cn.kiway.homework.util.HttpDownload;
import cn.kiway.homework.util.MLog;

import static cn.kiway.homework.WXApplication.url;
import static cn.kiway.homework.util.Utils.getCurrentVersion;

/**
 * Created by Administrator on 2018/1/29.
 */

public class WelcomeActivity extends BaseActivity {

    private static final String currentPackageVersion = "1.1.3";

    private boolean isSuccess = false;
    private boolean isJump = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
        checkIsFirst();
        checkNewVersion();
    }

    private void initView() {

    }

    //下面是版本更新相关
    public void checkNewVersion() {
        final String checkUrl = url + "/download/version/zip_xs.json";
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                    checkTimeout();
                    HttpGet httpRequest = new HttpGet(checkUrl);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    MLog.d("test", "new version = " + ret);
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
                jump();
            }
        }.start();
    }

    private String apkVersion;
    private String apkUrl;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (isJump) {
                return;
            }
            isSuccess = true;
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    MLog.d("test", "新版本返回值" + ret);
                    apkVersion = new JSONObject(ret).getString("apkCode");
                    apkUrl = new JSONObject(ret).getString("apkUrl");

                    String zipCode = new JSONObject(ret).getString("zipCode");
                    String zipUrl = new JSONObject(ret).getString("zipUrl");

                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {

                        jump();
                    } else {
                        //如果APK没有最新版本，比较包的版本。如果内置包的版本号比较高，直接替换
                        String currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        if (currentPackage.compareTo(currentPackageVersion) < 0) {
                            MLog.d("test", "内置包的版本大，替换新包");
                            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
                            checkIsFirst();
                        }
                        //替换完内置包之后，比较内置包和外包，如果版本号还是小了，更新外包
                        currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
                        String outer_package = zipCode;
                        if (currentPackage.compareTo(outer_package) < 0) {
                            //提示有新的包，下载新的包
                            MLog.d("test", "下载新的H5包");
                            updatePackage(outer_package, zipUrl);
                        } else {
                            MLog.d("test", "H5包是最新的");
                            jump();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    jump();
                }
            } else if (msg.what == 3) {
                jump();
            }
        }
    };

    private void updatePackage(final String outer_package, final String downloadUrl) {
        new Thread() {
            @Override
            public void run() {
                MLog.d("test", "updatePackage downloadUrl = " + downloadUrl);
                final int ret = new HttpDownload().downFile(downloadUrl, WXApplication.ROOT, WXApplication.ZIP);
                MLog.d("test", "下载新包 ret = " + ret);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ret == -1) {
                            jump();
                            return;
                        }
                        MLog.d("test", "删除旧包");
                        if (new File(WXApplication.ROOT + "xtzy_student").exists()) {
                            FileUtils.delFolder(WXApplication.ROOT + "xtzy_student");
                        }
                        try {
                            MLog.d("test", "解压新包");
                            new ZipFile(WXApplication.ROOT + WXApplication.ZIP).extractAll(WXApplication.ROOT);
                        } catch (ZipException e) {
                            e.printStackTrace();
                        }
                        //解压完毕，删掉zip文件
                        new File(WXApplication.ROOT + WXApplication.ZIP).delete();
                        MLog.d("test", "解压完毕");
                        getSharedPreferences("kiway", 0).edit().putString("version_package", outer_package).commit();
                        jump();
                    }
                });
            }
        }.start();


    }

    protected void checkIsFirst() {
        if (!checkFileComplete()) {
            MLog.d("test", "文件不完整");
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
        }
        if (getSharedPreferences("kiway", 0).getBoolean("isFirst", true)) {
            CountlyUtil.getInstance().addEvent("安装APP");
            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", false).commit();
            if (new File(WXApplication.ROOT).exists()) {
                FileUtils.delFolder(WXApplication.ROOT);
            }
            if (!new File(WXApplication.ROOT).exists()) {
                new File(WXApplication.ROOT).mkdirs();
            }
            //拷贝
            FileUtils.copyRawToSdcard(this, R.raw.xtzy_student, WXApplication.ZIP);
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

    public void jump() {
        isJump = true;
        startActivity(new Intent(this, MainActivity.class).putExtra("apkUrl", apkUrl).putExtra("apkVersion", apkVersion));
        finish();
    }
}
