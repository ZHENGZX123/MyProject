package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.Log;

import com.android.mdm.MdmPolicyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/16.
 */

public class JiChengShiXunAdapter implements IMDMAdapter {

    private Context c;
    public MdmPolicyManager mManager;

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
            mManager.setWifiDisabled(disabled);
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
            Log.d("test", "M102 M110不支持移动网络");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        //???是这个接口吗。
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
            mManager.turnOnGPS(on);
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
            mManager.setVpnDisabled(disabled);
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
    public void removeDisallowedUninstallPackages() {
        try {
            mManager.appNoUnInstallListDeleteAll();
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
    public void installPackage(String path, boolean open) {
        try {
            mManager.installApp(path, true);
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
    public void setGPSDisabled(boolean disabled) {
        try {
            mManager.setGpsDisabled(disabled);
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
    public void setScreenCaptureDisabled(boolean disabled) {
        try {
            mManager.setScreenCaptureDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMicrophoneDisabled(boolean disabled) {
        try {
            mManager.setMicrophoneDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRestoreFactoryDisabled(boolean disabled) {
        try {
            mManager.setRestoreFactoryDisabled(disabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSystemUpdateDisabled(boolean disabled) {
        try {
            mManager.setSystemUpdateDisabled(disabled);
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
        return new ArrayList<>();
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
            Log.d("test", "setSilentActiveAdmin没有必要");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {
        try {
            for (String s : whiteList) {
                mManager.appWhiteListWrite(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        try {
            Set<String> set = mManager.appWhiteListRead();
            return new ArrayList<>(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {
        try {
            mManager.appWhiteListDeleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setWIFIStandbyMode(int status) {
        //mode - 默认为2，始终不断开 2 始终 1 仅充电 0 永不 3 智能休眠
        try {
            mManager.setWIFIStandbyMode(Settings.Global.WIFI_SLEEP_POLICY_NEVER);
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
        return "unknown";
    }

    @Override
    public void setProximityDistance(int distance) {
        try {
            mManager.setProximityDistance(distance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProximityEnable(boolean enable) {
        try {
            mManager.setProximityEnable(enable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProximityDelay(int delay) {
        try {
            mManager.setProximityDelay(delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRunningAPP() {
        try {
            return mManager.GetTopAppPackageName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Bitmap captureScreen(ComponentName admin) {
        try {
            return mManager.captureBitmap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
