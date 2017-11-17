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
        try {
            Log.d("test", "setWifiDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVoiceDisabled(boolean disabled) {
        try {
            Log.d("test", "setVoiceDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
        try {
            Log.d("test", "setDataConnectivityDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        try {
            mManager.setUsbDebuggingEnabled(!disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //暂时没有用到
    @Override
    public void setExternalStorageDisabled(boolean disabled) {
        try {
            mManager.setSdCardState(!disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {
        try {
            mManager.setStatusBarDisabled(!disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {
        try {
            mManager.captureRecentKey(disabled ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {
        try {
            mManager.captureHomeKey(disabled ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBackButtonDisabled(boolean disabled) {
        try {
            mManager.captureBackKey(disabled ? 0 : 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSettingsApplicationDisabled(boolean disabled) {
        try {
            mManager.setSettingsApplicationDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnOnGPS(boolean on) {
        try {
            Log.d("test", "turnOnGPS没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdownDevice() {
        try {
            mManager.shutDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {
        try {
            Log.d("test", "addPersistentApp没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVpnDisabled(boolean disabled) {
        try {
            Log.d("test", "setVpnDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
        try {
            mManager.setDefaultLauncher(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {
        try {
            for (String s : packageNames) {
                mManager.appNoUnInstallListWrite(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearDefaultLauncher() {
        try {
            mManager.clearDefaultLauncher();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTimeAndDateSetDisabled(boolean disabled) {
        try {
            mManager.setSysTimeDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //暂时没有用到这个
    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {
        try {
            for (String s : addDomainList) {
                mManager.addNetworkAccessWhitelistRule(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setWIFIeditDisabled(boolean b) {
        try {
            Log.d("test", "setWIFIeditDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void installPackage(String path) {
        try {
            mManager.installApp(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uninstallPackage(String packages) {
        try {
            mManager.uninstallApp(packages);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBluetoothDisabled(boolean disabled) {
        try {
            mManager.setBluetoothDataDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGPSDisabled(boolean b) {
        try {
            Log.d("test", "setGPSDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setWifiApDisabled(boolean b) {
        try {
            Log.d("test", "setWifiApDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rebootDevice() {
        try {
            mManager.reboot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenCaptureDisabled(boolean flag) {
        try {
            Log.d("test", "setScreenCaptureDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMicrophoneDisabled(boolean flag) {
        try {
            Log.d("test", "setMicrophoneDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRestoreFactoryDisabled(boolean disabled) {
        try {
            mManager.allowFactoryReset(!disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSystemUpdateDisabled(boolean flag) {
        try {
            Log.d("test", "setSystemUpdateDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNetworkLocationDisabled(boolean flag) {
        try {
            Log.d("test", "setNetworkLocationDisabled没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInstallPackageBlackList(ArrayList<String> packageNames) {
        try {
            Log.d("test", "addInstallPackageBlackList没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addDisallowedRunningApp(ArrayList<String> packageNames) {
        try {
            mManager.setDisableApplication(packageNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getDisallowedRunningApp() {
        try {
            Log.d("test", "getDisallowedRunningApp没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeDisallowedRunningApp(ArrayList<String> packageNames) {
        try {
            Log.d("test", "removeDisallowedRunningApp没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSilentActiveAdmin() {
        try {
            Log.d("test", "setSilentActiveAdmin没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {
        try {
            Log.d("test", "addInstallPackageWhiteList没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        try {
            Log.d("test", "getInstallPackageWhiteList没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {
        try {
            Log.d("test", "removeInstallPackageWhiteList没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setWIFIStandbyMode(int status) {
        try {
            Log.d("test", "setWIFIStandbyMode没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hangupCalling() {
        try {
            Log.d("test", "hangupCalling没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectWifi(String ssid, String pwd) {
        try {
            Log.d("test", "hangupCalling没有对应实现");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMdmSdkVersion() {
        try {
            return mManager.getMdmSdkVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setProximityDistance(int distance) {
        try {
            mManager.setProximityDistance(distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
