package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.android.mdm.MdmPolicyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/16.
 */

public class JiChengShiXunAdapter implements IMDMAdapter {

    private Context c;
    private MdmPolicyManager mManager;

    @Override
    public void init(Context c) {
        this.c = c;
        mManager = new MdmPolicyManager(c);
    }

    @Override
    public void init(Context c, ComponentName name) {
        this.c = c;
        mManager = new MdmPolicyManager(c);
    }

    @Override
    public void setWifiDisabled(boolean disabled) {
        Log.d("test", "setWifiDisabled没有对应实现");
    }

    @Override
    public void setVoiceDisabled(boolean disabled) {
        Log.d("test", "setVoiceDisabled没有对应实现");
    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
        Log.d("test", "setDataConnectivityDisabled没有对应实现");
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        mManager.setUsbDebuggingEnabled(!disabled);
    }

    @Override
    public void setExternalStorageDisabled(boolean disabled) {
        mManager.setSdCardState(!disabled);
    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {
        mManager.setStatusBarDisabled(!disabled);
    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {
        mManager.captureRecentKey(disabled ? 0 : 1);
    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {
        mManager.captureHomeKey(disabled ? 0 : 1);
    }

    @Override
    public void setBackButtonDisabled(boolean disabled) {
        mManager.captureBackKey(disabled ? 0 : 1);
    }

    @Override
    public void setSettingsApplicationDisabled(boolean disabled) {
        mManager.setSettingsApplicationDisabled(disabled);
    }

    @Override
    public void turnOnGPS(boolean on) {
        Log.d("test", "turnOnGPS没有对应实现");
    }

    @Override
    public void shutdownDevice() {
        mManager.shutDown();
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {
        Log.d("test", "addPersistentApp没有对应实现");
    }

    @Override
    public void setVpnDisabled(boolean disabled) {
        Log.d("test", "setVpnDisabled没有对应实现");
    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
        mManager.setDefaultLauncher(packageName);
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {
        for (String s : packageNames) {
            mManager.appNoUnInstallListWrite(s);
        }
    }

    @Override
    public void clearDefaultLauncher() {
        mManager.clearDefaultLauncher();
    }

    @Override
    public void setTimeAndDateSetDisabled(boolean disabled) {
        mManager.setSysTimeDisabled(disabled);
    }

    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {
        for (String s : addDomainList) {
            mManager.addNetworkAccessWhitelistRule(s);
        }
    }

    @Override
    public void setWIFIeditDisabled(boolean b) {
        Log.d("test", "setWIFIeditDisabled没有对应实现");
    }

    @Override
    public void installPackage(String path) {
        mManager.installApp(path);
    }

    @Override
    public void uninstallPackage(String packages) {
        mManager.uninstallApp(packages);
    }

    @Override
    public void setBluetoothDisabled(boolean disabled) {
        mManager.setBluetoothDataDisabled(disabled);
    }

    @Override
    public void setGPSDisabled(boolean b) {
        Log.d("test", "setGPSDisabled没有对应实现");
    }

    @Override
    public void setWifiApDisabled(boolean b) {
        Log.d("test", "setWifiApDisabled没有对应实现");
    }

    @Override
    public void rebootDevice() {
        mManager.reboot();
    }

    @Override
    public void setScreenCaptureDisabled(boolean flag) {
        Log.d("test", "setScreenCaptureDisabled没有对应实现");
    }

    @Override
    public void setMicrophoneDisabled(boolean flag) {
        Log.d("test", "setMicrophoneDisabled没有对应实现");
    }

    @Override
    public void setRestoreFactoryDisabled(boolean disabled) {
        mManager.allowFactoryReset(!disabled);
    }

    @Override
    public void setSystemUpdateDisabled(boolean flag) {
        Log.d("test", "setSystemUpdateDisabled没有对应实现");
    }

    @Override
    public void setNetworkLocationDisabled(boolean flag) {
        Log.d("test", "setNetworkLocationDisabled没有对应实现");
    }

    @Override
    public void addInstallPackageBlackList(ArrayList<String> packageNames) {
        Log.d("test", "addInstallPackageBlackList没有对应实现");
    }

    @Override
    public void addDisallowedRunningApp(ArrayList<String> packageNames) {
        mManager.setDisableApplication(packageNames);
    }

    @Override
    public List<String> getDisallowedRunningApp() {
        Log.d("test", "getDisallowedRunningApp没有对应实现");
        return null;
    }

    @Override
    public void removeDisallowedRunningApp(ArrayList<String> packageNames) {
        Log.d("test", "removeDisallowedRunningApp没有对应实现");
    }

    @Override
    public void setSilentActiveAdmin() {
        Log.d("test", "setSilentActiveAdmin没有对应实现");
    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {

    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        return null;
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {

    }

    @Override
    public void setWIFIStandbyMode(int status) {
        Log.d("test", "setWIFIStandbyMode没有对应实现");
    }

    @Override
    public void hangupCalling() {
        Log.d("test", "hangupCalling没有对应实现");
    }

    @Override
    public void connectWifi(String ssid, String pwd) {
        Log.d("test", "hangupCalling没有对应实现");
    }
}
