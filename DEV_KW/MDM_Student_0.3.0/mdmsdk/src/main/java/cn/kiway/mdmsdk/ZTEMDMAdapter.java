package cn.kiway.mdmsdk;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdmsdk.cn.kiway.mdmsdk.util.RootCmd;

/**
 * Created by Administrator on 2018/6/11.
 */
public class ZTEMDMAdapter implements IMDMAdapter {

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
//暂时不做
    }

    @Override
    public void setVoiceDisabled(boolean disabled) {
//暂时不做
    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
//暂时不做
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
//暂时不做
    }

    @Override
    public void setExternalStorageDisabled(boolean disabled) {
//暂时不做
    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {

    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {

    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {

    }

    @Override
    public void setBackButtonDisabled(boolean disabled) {

    }

    @Override
    public void setSettingsApplicationDisabled(boolean disabled) {

    }

    @Override
    public void turnOnGPS(boolean on) {

    }

    @Override
    public void shutdownDevice() {
        RootCmd.execRootCmdSilent("shutdown");
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {

    }

    @Override
    public void setVpnDisabled(boolean disabled) {

    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
//暂时不做
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {

    }

    @Override
    public void removeDisallowedUninstallPackages() {

    }

    @Override
    public void clearDefaultLauncher() {

    }

    @Override
    public void setTimeAndDateSetDisabled(boolean b) {

    }

    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {

    }

    @Override
    public void setWIFIeditDisabled(boolean b) {

    }

    @Override
    public void installPackage(String path) {
        RootCmd.execRootCmdSilent("install " + path);
    }

    @Override
    public void installPackage(String path, boolean open) {

    }

    @Override
    public void uninstallPackage(String packages) {
        RootCmd.execRootCmdSilent("uninstall " + packages);
    }

    @Override
    public void setBluetoothDisabled(boolean b) {

    }

    @Override
    public void setGPSDisabled(boolean b) {

    }

    @Override
    public void setWifiApDisabled(boolean b) {

    }

    @Override
    public void rebootDevice() {
        RootCmd.execRootCmdSilent("reboot");
    }

    @Override
    public void setScreenCaptureDisabled(boolean flag) {

    }

    @Override
    public void setMicrophoneDisabled(boolean flag) {

    }

    @Override
    public void setRestoreFactoryDisabled(boolean flag) {

    }

    @Override
    public void setSystemUpdateDisabled(boolean flag) {

    }

    @Override
    public void setNetworkLocationDisabled(boolean flag) {

    }

    @Override
    public void addInstallPackageBlackList(ArrayList<String> packageNames) {

    }

    @Override
    public void addDisallowedRunningApp(ArrayList<String> packageNames) {

    }

    @Override
    public List<String> getDisallowedRunningApp() {
        return new ArrayList<>();
    }

    @Override
    public void removeDisallowedRunningApp(ArrayList<String> packageNames) {

    }

    @Override
    public void setSilentActiveAdmin() {

    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {

    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        return new ArrayList<>();
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {

    }

    @Override
    public void setWIFIStandbyMode(int status) {

    }

    @Override
    public void hangupCalling() {

    }

    @Override
    public void connectWifi(String ssid, String pwd) {

    }

    @Override
    public String getMdmSdkVersion() {
        return "1.0.0";
    }

    @Override
    public void setProximityDistance(int distance) {

    }

    @Override
    public void setProximityEnable(boolean enable) {

    }

    @Override
    public void setProximityDelay(int delay) {

    }

    @Override
    public String getRunningAPP() {
        return null;
    }

    @Override
    public Bitmap captureScreen() {
        return null;
    }

    @Override
    public void setSystemBrowserDisabled(boolean disabled) {

    }
}
