package cn.kiway.mdmsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class HuaruianMDMAdapter implements IMDMAdapter {

    private Context c;

    @Override
    public void init(Context c) {
        this.c = c;
    }

    @Override
    public void init(Context c, ComponentName name) {
        this.c = c;
    }

    @Override
    public void setWifiDisabled(boolean disabled) {
        sendBroadcast("com.hra.wifi", "flag", !disabled);
    }

    @Override
    public void setVoiceDisabled(boolean disabled) {
        Log.d("test", "setVoiceDisabled缺少");
    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
        Log.d("test", "setDataConnectivityDisabled不支持");
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        if (disabled) {
            sendBroadcast("com.hra.closeMTP", "", false);
        } else {
            sendBroadcast("com.hra.openMTP", "", false);
        }
    }

    @Override
    public void setExternalStorageDisabled(boolean disabled) {
        sendBroadcast("com.hra.disableSDandOTG", "disabled", !disabled ? "1" : "0");
    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {
        sendBroadcast("com.hra.IsDown_state", "Isdown", !disabled);
    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {
        sendBroadcast("com.hra.recent", "recent", !disabled);
    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {
        sendBroadcast("com.hra.home", "home", !disabled);
    }

    @Override
    public void setBackButtonDisabled(boolean disabled) {
        sendBroadcast("com.hra.back", "back", !disabled);
    }

    @Override
    public void setSettingsApplicationDisabled(boolean disabled) {
        sendBroadcast("com.hra.settings", "setting", !disabled);
    }

    @Override
    public void turnOnGPS(boolean on) {
        Log.d("test", "turnOnGPS不支持");
    }

    @Override
    public void shutdownDevice() {
        sendBroadcast("com.hra.shutdown", "", false);
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {
        Log.d("test", "addPersistentApp暂时不需要");
    }

    @Override
    public void setVpnDisabled(boolean disabled) {
        sendBroadcast("com.hra.WirelessSettings", "vpn", !disabled);
    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
        sendBroadcast("com.hra.Launcher", "PkgName", packageName, "ClassName", className);
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {
        Log.d("test", "addDisallowedUninstallPackages不支持");
    }

    @Override
    public void removeDisallowedUninstallPackages() {
        Log.d("test", "removeDisallowedUninstallPackages不支持");
    }

    @Override
    public void clearDefaultLauncher() {
        sendBroadcast("com.hra.ClearLauncher", "", false);
    }

    @Override
    public void setTimeAndDateSetDisabled(boolean b) {
        sendBroadcast("com.hra.datetime", "DateTime", !b);
    }

    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {
        Log.d("test", "addNetworkAccessBlackList不支持");
    }

    @Override
    public void setWIFIeditDisabled(boolean b) {
        Log.d("test", "setWIFIeditDisabled暂时不需要");
    }

    @Override
    public void installPackage(String path) {
        sendBroadcast("com.hra.Silence.install", "filePath", path);
    }

    @Override
    public void installPackage(String path, boolean open) {
        installPackage(path);
    }

    @Override
    public void uninstallPackage(String packageName) {
        sendBroadcast("com.hra.Silence.uninstall", "packageName", packageName);
    }

    @Override
    public void setBluetoothDisabled(boolean disabled) {
        sendBroadcast("com.hra.openBluetooth", "Btflag", !disabled);
    }

    @Override
    public void setGPSDisabled(boolean b) {
        Log.d("test", "setGPSDisabled不支持");
    }

    @Override
    public void setWifiApDisabled(boolean b) {
        sendBroadcast("com.hra.androidAp", "androidAp", !b);
    }

    @Override
    public void rebootDevice() {
        sendBroadcast("com.hra.reboot", "", false);
    }

    @Override
    public void setScreenCaptureDisabled(boolean flag) {
        sendBroadcast("com.hra.Screenshot", "screenshot", !flag);
    }

    @Override
    public void setMicrophoneDisabled(boolean flag) {
        Log.d("test", "setMicrophoneDisabled缺少");
    }

    @Override
    public void setRestoreFactoryDisabled(boolean flag) {
        sendBroadcast("com.hra.MasterClear", "MasterClear", !flag);
    }

    @Override
    public void setSystemUpdateDisabled(boolean flag) {

    }

    @Override
    public void setNetworkLocationDisabled(boolean flag) {
        Log.d("test", "setNetworkLocationDisabled不支持");
    }


    @Override
    public void addDisallowedRunningApp(ArrayList<String> packageNames) {

    }

    @Override
    public List<String> getDisallowedRunningApp() {
        return null;
    }

    @Override
    public void removeDisallowedRunningApp(ArrayList<String> packageNames) {

    }

    @Override
    public void setSilentActiveAdmin() {
        Log.d("test", "setSilentActiveAdmin不需要");
    }

    @Override
    public void addInstallPackageBlackList(ArrayList<String> packageNames) {

    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {
        Log.d("test", "addInstallPackageWhiteList暂时做不了");
    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        Log.d("test", "getInstallPackageWhiteList缺少");
        return new ArrayList<>();
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {
        Log.d("test", "removeInstallPackageWhiteList暂时做不了");
    }

    @Override
    public void setWIFIStandbyMode(int status) {

    }

    @Override
    public void hangupCalling() {
        Log.d("test", "hangupCalling不支持");
    }

    @Override
    public void connectWifi(String ssid, String pwd) {
        Log.d("test", "connectWifi不支持");
    }

    @Override
    public String getMdmSdkVersion() {
        return null;
    }

    @Override
    public void setProximityDistance(int distance) {
        Log.d("test", "setProximityDistance不支持");
    }

    @Override
    public void setProximityEnable(boolean enable) {
        Log.d("test", "setProximityEnable不支持");
    }

    @Override
    public void setProximityDelay(int delay) {
        Log.d("test", "setProximityDelay不支持");
    }

    @Override
    public String getRunningAPP() {
        Log.d("test", "getRunningAPP不支持");
        return null;
    }

    @Override
    public Bitmap captureScreen() {
        return null;
    }

    @Override
    public void setSystemBrowserDisabled(boolean disabled) {
        sendBroadcast("com.hra.browser", "browser", !disabled);
    }


    private void sendBroadcast(String action, String key, boolean value) {
        Log.d("test", "sendBroadcast " + action + " , " + key + " , " + value);
        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        if (c != null) {
            c.sendBroadcast(intent);
        }
    }

    private void sendBroadcast(String action, String key, String value) {
        Log.d("test", "sendBroadcast " + action + " , " + key + " , " + value);
        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(key)) {
            intent.putExtra(key, value);
        }
        if (c != null) {
            c.sendBroadcast(intent);
        }
    }

    private void sendBroadcast(String action, String key1, String value1, String key2, String value2) {
        Log.d("test", "sendBroadcast " + action + " , " + key1 + " , " + value1);
        Intent intent = new Intent(action);
        if (!TextUtils.isEmpty(key1)) {
            intent.putExtra(key1, value1);
        }
        if (!TextUtils.isEmpty(key2)) {
            intent.putExtra(key2, value2);
        }
        if (c != null) {
            c.sendBroadcast(intent);
        }
    }
}
