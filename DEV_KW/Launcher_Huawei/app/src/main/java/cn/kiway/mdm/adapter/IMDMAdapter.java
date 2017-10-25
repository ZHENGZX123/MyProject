package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public interface IMDMAdapter {

    void init(Context c);

    void init(Context c, ComponentName name);

    void setWifiDisabled(boolean disabled);

    void setDataConnectivityDisabled(boolean disabled);

    void setUSBDataDisabled(boolean disabled);//慎用

    void setStatusBarExpandPanelDisabled(boolean disabled);

    void setTaskButtonDisabled(boolean disabled);

    void setHomeButtonDisabled(boolean disabled);

    void setSettingsApplicationDisabled(boolean disabled);

    void turnOnGPS(boolean on);

    void shutdownDevice();

    void addPersistentApp(List<String> packageNames);

    void setVpnDisabled(boolean disabled);

    void setDefaultLauncher(String packageName, String className);//慎用

    void addDisallowedUninstallPackages(List<String> packageNames);//慎用

    void clearDefaultLauncher();

    void setTimeAndDateSetDisabled(boolean b);

    void addNetworkAccessBlackList(ArrayList<String> addDomainList);

    void setWIFIeditDisabled(boolean b);

    void installPackage(String path);

    void uninstallPackage(String s, boolean b);

    void setBluetoothDisabled(boolean b);

    void setGPSDisabled(boolean b);

    void setWifiApDisabled(boolean b);

    void rebootDevice();

    void setScreenCaptureDisabled(boolean flag);

    void setMicrophoneDisabled(boolean flag);

    void setRestoreFactoryDisabled(boolean flag);

    void setSystemUpdateDisabled(boolean flag);

    void setNetworkLocationDisabled(boolean flag);
}
