package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;

import com.huawei.android.app.admin.DeviceApplicationManager;
import com.huawei.android.app.admin.DeviceControlManager;
import com.huawei.android.app.admin.DeviceNetworkManager;
import com.huawei.android.app.admin.DevicePackageManager;
import com.huawei.android.app.admin.DeviceRestrictionManager;
import com.huawei.android.app.admin.DeviceSettingsManager;
import com.huawei.android.app.admin.DeviceVpnManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class HuaweiMDMAdapter implements IMDMAdapter {

    private Context c;
    private ComponentName mAdminName;

    @Override
    public void init(Context c) {
        this.c = c;
    }

    @Override
    public void init(Context c, ComponentName adminName) {
        this.c = c;
        this.mAdminName = adminName;
    }

    @Override
    public void setWifiDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setWifiDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setVoiceDisabled(boolean disabled) {

    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setDataConnectivityDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setUSBDataDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setExternalStorageDisabled(boolean disabled) {

    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setStatusBarExpandPanelDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setTaskButtonDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setHomeButtonDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void setBackButtonDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setBackButtonDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSettingsApplicationDisabled(boolean disable) {
        try {
            new DeviceRestrictionManager().setSettingsApplicationDisabled(mAdminName, disable);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
        try {
            new DeviceControlManager().setDefaultLauncher(mAdminName, packageName, className);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void turnOnGPS(boolean on) {
        try {
            new DeviceControlManager().turnOnGPS(mAdminName, on);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void shutdownDevice() {
        try {
            new DeviceControlManager().shutdownDevice(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {
        try {
            new DevicePackageManager().addDisallowedUninstallPackages(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearDefaultLauncher() {
        try {
            new DeviceControlManager().clearDefaultLauncher(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTimeAndDateSetDisabled(boolean b) {
        try {
            new DeviceSettingsManager().setTimeAndDateSetDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {
        try {
            new DeviceNetworkManager().addNetworkAccessBlackList(mAdminName, addDomainList);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setWIFIeditDisabled(boolean b) {
        try {
            new DeviceSettingsManager().setWIFIeditDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void installPackage(String path) {
        try {
            new DevicePackageManager().installPackage(mAdminName, path);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void uninstallPackage(String s) {
        try {
            new DevicePackageManager().uninstallPackage(mAdminName, s, false);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setBluetoothDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setBluetoothDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setGPSDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setGPSDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setWifiApDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setWifiApDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void rebootDevice() {
        try {
            new DeviceControlManager().rebootDevice(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setScreenCaptureDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setScreenCaptureDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setMicrophoneDisabled(boolean flag) {
        try {
            new DeviceRestrictionManager().setMicrophoneDisabled(mAdminName, flag);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setRestoreFactoryDisabled(boolean flag) {
        try {
            new DeviceSettingsManager().setRestoreFactoryDisabled(mAdminName, flag);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSystemUpdateDisabled(boolean flag) {
        try {
            new DeviceRestrictionManager().setSystemUpdateDisabled(mAdminName, flag);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setNetworkLocationDisabled(boolean flag) {
        try {
            new DeviceSettingsManager().setNetworkLocationDisabled(mAdminName, flag);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addInstallPackageBlackList(ArrayList<String> packageNames) {
        try {
            new DeviceApplicationManager().addInstallPackageBlackList(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addDisallowedRunningApp(ArrayList<String> packageNames) {
        try {
            new DeviceApplicationManager().addDisallowedRunningApp(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public List<String> getDisallowedRunningApp() {
        try {
            return new DeviceApplicationManager().getDisallowedRunningApp(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public void removeDisallowedRunningApp(ArrayList<String> packageNames) {
        try {
            new DeviceApplicationManager().removeDisallowedRunningApp(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSilentActiveAdmin() {
        try {
            new DeviceControlManager().setSilentActiveAdmin(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addInstallPackageWhiteList(ArrayList<String> whiteList) {
        try {
            new DevicePackageManager().addInstallPackageWhiteList(mAdminName, whiteList);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public List<String> getInstallPackageWhiteList() {
        List<String> whitelist = new ArrayList<>();
        try {
            whitelist = new DevicePackageManager().getInstallPackageWhiteList(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return whitelist;
    }

    @Override
    public void removeInstallPackageWhiteList(List<String> currentList) {
        try {
            new DevicePackageManager().removeInstallPackageWhiteList(mAdminName, currentList);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {
        try {
            new DeviceApplicationManager().addPersistentApp(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setVpnDisabled(boolean disabled) {
        try {
            new DeviceVpnManager().setVpnDisabled(mAdminName, true);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setWIFIStandbyMode(int status) {
        try {
            new DeviceSettingsManager().setWIFIStandbyMode(mAdminName, status);
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
