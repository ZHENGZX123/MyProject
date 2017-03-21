package com.zk.myweex.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.taobao.weex.utils.WXFileUtils;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        renderPage(WXFileUtils.loadAsset("yjpt/weex_jzd/login.js", this), "file://assets/yjpt/weex_jzd/", null);

        if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
            startActivity(new Intent(this, MainActivity2.class));
            finish();
        }
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
        }

        @Override
        public void onDenied(List<String> deniedPermissions) {
            toast("有权限被拒绝");
            finish();
        }
    };

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
