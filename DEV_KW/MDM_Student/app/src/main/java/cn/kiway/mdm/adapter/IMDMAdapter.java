package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public interface IMDMAdapter {

    void init(Context c);

    void init(Context c, ComponentName name);

    //禁用wifi
    void setWifiDisabled(boolean disabled);

    //暂时不需要
    void setVoiceDisabled(boolean disabled);

    //禁用移动数据连接
    void setDataConnectivityDisabled(boolean disabled);

    //禁用usb口
    void setUSBDataDisabled(boolean disabled);//慎用

    //禁用sd卡
    void setExternalStorageDisabled(boolean disabled);//慎用

    //禁用状态栏下拉
    void setStatusBarExpandPanelDisabled(boolean disabled);

    //禁用任务栏
    void setTaskButtonDisabled(boolean disabled);

    //禁用Home键
    void setHomeButtonDisabled(boolean disabled);

    //禁用返回键
    void setBackButtonDisabled(boolean disabled);//慎用

    //禁用设置APP
    void setSettingsApplicationDisabled(boolean disabled);//5.1

    //打开GPS定位
    //Android7.0扫描SSID需要
    void turnOnGPS(boolean on);

    //关机
    void shutdownDevice();

    //设置
    void addPersistentApp(List<String> packageNames);

    //禁用vpn
    void setVpnDisabled(boolean disabled);

    //设置默认Launcher
    void setDefaultLauncher(String packageName, String className);//慎用

    //不允许卸载APP
    void addDisallowedUninstallPackages(List<String> packageNames);//慎用

    void removeDisallowedUninstallPackages();

    //清除默认桌面
    void clearDefaultLauncher();

    //设置系统时间不可编辑
    void setTimeAndDateSetDisabled(boolean b);

    //暂时用不上
    void addNetworkAccessBlackList(ArrayList<String> addDomainList);

    //设置wifi不可编辑
    void setWIFIeditDisabled(boolean b);

    //安装app
    void installPackage(String path);

    void installPackage(String path, boolean open);

    //卸载app
    void uninstallPackage(String packages);

    //设置蓝牙不可用
    void setBluetoothDisabled(boolean b);

    //设置GPS不可用
    void setGPSDisabled(boolean b);

    //设置AP不可用
    void setWifiApDisabled(boolean b);

    //重启设备
    void rebootDevice();

    //设置屏幕截图不可用
    void setScreenCaptureDisabled(boolean flag);

    //设置麦克风不可用
    void setMicrophoneDisabled(boolean flag);

    //设置出厂设置不可用
    void setRestoreFactoryDisabled(boolean flag);

    //设置系统更新不可用
    void setSystemUpdateDisabled(boolean flag);

    //设置网络定位不可用
    void setNetworkLocationDisabled(boolean flag);

    //添加安装设备的白名单
    void addInstallPackageBlackList(ArrayList<String> packageNames);

    //添加禁止运行的APP
    void addDisallowedRunningApp(ArrayList<String> packageNames);

    //获取禁止运行的APP列表
    List<String> getDisallowedRunningApp();

    void removeDisallowedRunningApp(ArrayList<String> packageNames);

    void setSilentActiveAdmin();

    void addInstallPackageWhiteList(ArrayList<String> whiteList);

    List<String> getInstallPackageWhiteList();

    void removeInstallPackageWhiteList(List<String> currentList);

    //设置wifi的standby模式
    void setWIFIStandbyMode(int status);

    //挂断当前电话
    void hangupCalling();

    //一键连接wifi
    void connectWifi(String ssid, String pwd);

    String getMdmSdkVersion();

    void setProximityDistance(int distance);//距离感应器距离

    void setProximityEnable(boolean enable);//启动距离感应器

    void setProximityDelay(int delay);//距离感应器反应时间

    String getRunningAPP();

    Bitmap captureScreen(ComponentName admin);

}
