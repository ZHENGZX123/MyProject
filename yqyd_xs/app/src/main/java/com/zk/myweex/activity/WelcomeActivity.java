package com.zk.myweex.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zk.myweex.WXApplication;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.entity.ZipPackage;
import com.zk.myweex.utils.FileUtils;
import com.zk.myweex.utils.MyDBHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.yiqiyuedu.R;

public class WelcomeActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    private void jump() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity2.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity2.class));
                }
                finish();
            }
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestRunTimePermission(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                mListener);
    }

    private final int REQUEST_CODE = 1;
    private IPermission mListener = new IPermission() {
        @Override
        public void onGranted() {
            toast("所有权限被接受");
            checkIsFirst();
            jump();
        }

        @Override
        public void onDenied(List<String> deniedPermissions) {
            toast("有权限被拒绝");
            finish();
        }
    };

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
            //2.拷贝。。。
            FileUtils.copyRawToSdcard(this, R.raw.yqyd, "yqydTab0.zip");
            FileUtils.copyRawToSdcard(this, R.raw.yqyd, "yqydTab1.zip");
            FileUtils.copyRawToSdcard(this, R.raw.yqyd, "yqydTab2.zip");

            //3.插入数据库
            TabEntity tab0 = new TabEntity();
            tab0.name = "首页";
            tab0.idStr = "yqydTab0";
            TabEntity tab1 = new TabEntity();
            tab1.name = "书库";
            tab1.idStr = "yqydTab1";
            TabEntity tab2 = new TabEntity();
            tab2.name = "我的";
            tab2.idStr = "yqydTab2";
            new MyDBHelper(getApplicationContext()).addTabEntity(tab0);
            new MyDBHelper(getApplicationContext()).addTabEntity(tab1);
            new MyDBHelper(getApplicationContext()).addTabEntity(tab2);

            ZipPackage zip0 = new ZipPackage();
            zip0.name = "yqydTab0.zip";
            zip0.indexPath = "yqyd/dist/tab0.js";
            zip0.version = "2.0.0";
            zip0.patchs = "";
            ZipPackage zip1 = new ZipPackage();
            zip1.name = "yqydTab1.zip";
            zip1.indexPath = "yqyd/dist/tab1.js";
            zip1.version = "2.0.0";
            zip1.patchs = "";
            ZipPackage zip2 = new ZipPackage();
            zip2.name = "yqydTab2.zip";
            zip2.indexPath = "yqyd/dist/tab2.js";
            zip2.version = "2.0.0";
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
            jump();
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
