package com.clovsoft.core.demo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.clovsoft.core.CDevice;

public class MainActivity extends AppCompatActivity implements CDevice.OnInitListener {
    static final String EXTRA_ONLY_INIT_SDK = "only_init_sdk";
    private boolean showMainPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showMainPage = !getIntent().getBooleanExtra(EXTRA_ONLY_INIT_SDK, false);
        if (android.support.v4.content.ContextCompat.checkSelfPermission(this, Manifest.permission
                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSION);
        } else {
            // 初始化sdk
            initSdk();
        }
    }

    private static final int REQUEST_CODE_PERMISSION = 999;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        // 初始化sdk
                        initSdk();
                    } else {
                        Toast.makeText(this, "写数据权限被禁用", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        }
    }

    @Override
    public boolean onInstallCoreLibrary(String apkPath) {
        Log.e("onInstallCoreLibrary", apkPath);
        ((App) getApplication()).onRequestInstallPackage(apkPath);
        finish();
        return true;
    }

    @Override
    public void onInitSuccessful(CDevice device) {
        if (showMainPage) {
            String copyright = "";
            int resId = getIdentifier(this, BuildConfig.COPYRIGHT, "string");
            if (resId > 0) {
                copyright = getResources().getString(resId);
            }
            CDevice.getInstance().showMainPage(
                    this,
                    BuildConfig.CACHE_DIRECTORY,
                    copyright
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }

    @Override
    public void onInitFailure(CDevice device) {

    }

    private void initSdk() {
        CDevice.getInstance().init(getApplicationContext(), this);
        ;
    }

    private static int getIdentifier(Context context, String name, String defType) {
        context = context.getApplicationContext();
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }
}
