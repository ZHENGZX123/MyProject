package com.clovsoft.core.demo;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.clovsoft.core.CDevice;

import java.util.ArrayList;
import java.util.List;


public class App extends Application implements CDevice.OnSystemActionListener, CDevice.OnConnectionStateListener {

    public static App instance;
    private String shutdown = "shutdown";
    private String lockapp = "lockapp";
    private String unlockapp = "unlockapp";
    private String forceStopApp = "forceStopApp";
    private String installApp = "installApp";
    private String uninstallApp = "uninstallApp";
    private String networkAccessWhiteList = "networkAccessWhiteList";
    private String addNetworkAccessWhiteList = "addNetworkAccessWhiteList";
    private String cleanNetworkAccessWhiteList = "cleanNetworkAccessWhiteList";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 监听学生端管控功能
        CDevice.getInstance().setSystemActionListener(this);
        // 监听学生端连接状态
        CDevice.getInstance().setConnectionStateLisener(this);
        // 设置学生端平台默认地址，如 http://sz.xtclass.com/app
        CDevice.getInstance().setPlatformApi(BuildConfig.DEFAULT_API);
    }

    @Override
    public void onRequestInitDevice() {
        Log.i(getClass().getSimpleName(), "初始化设备");
    }

    @Override
    public void onRequestUninitDevice() {

    }

    @Override
    public void onRequestShutdown() {
        Log.i(getClass().getSimpleName(), "关机");
        Intent intent = new Intent(shutdown);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestLockApp() {
        Log.i(getClass().getSimpleName(), "锁定当前app");
        Intent intent = new Intent(lockapp);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestUnlockApp() {
        Log.i(getClass().getSimpleName(), "解除锁定当前app");
        Intent intent = new Intent(unlockapp);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestForceStopApp(String packageName) {
        Log.i(getClass().getSimpleName(), "强行停止app");
        Intent intent = new Intent(forceStopApp);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageName);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestInstallPackage(String apkPath) {
        Log.i(getClass().getSimpleName(), "安装：" + apkPath);
        Intent intent = new Intent(installApp);
        Bundle bundle = new Bundle();
        bundle.putString("apkPath", apkPath);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestUninstallPackage(String packageName) {
        Log.i(getClass().getSimpleName(), "卸载：" + packageName);
        Intent intent = new Intent(uninstallApp);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", packageName);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestSetNetworkAccessWhiteList(List<String> domains) {
        Log.i(getClass().getSimpleName(), "设置上网白名单");
        Intent intent = new Intent(networkAccessWhiteList);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("networkAccessWhiteList", (ArrayList<String>) domains);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestAddNetworkAccessWhiteList(String domain) {
        Log.i(getClass().getSimpleName(), "添加一条上网白名单");
        Intent intent = new Intent(addNetworkAccessWhiteList);
        Bundle bundle = new Bundle();
        bundle.putString("domain", domain);
        intent.putExtras(bundle);
        sendBroadcast(intent);
    }

    @Override
    public void onRequestCleanNetworkAccessWhiteList() {
        Log.i(getClass().getSimpleName(), "清除上网白名单");
        Intent intent = new Intent(cleanNetworkAccessWhiteList);
        sendBroadcast(intent);
    }


    @Override
    public void onConnected(CDevice device) {
        // 学生端连接到服务端
        // 打开主页
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onDisconnected(CDevice device) {
        // 学生端断开连接
    }
}
