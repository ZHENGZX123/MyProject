package cn.kiway.mdm.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.widget.Toast;

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
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setDataConnectivityDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setDataConnectivityDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setUSBDataDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setUSBDataDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setStatusBarExpandPanelDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setStatusBarExpandPanelDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTaskButtonDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setTaskButtonDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setHomeButtonDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setHomeButtonDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSettingsApplicationDisabled(boolean disable) {
        try {
            new DeviceRestrictionManager().setSettingsApplicationDisabled(mAdminName, disable);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setDefaultLauncher(String packageName, String className) {
        try {
            new DeviceControlManager().setDefaultLauncher(mAdminName, packageName, className);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void turnOnGPS(boolean on) {
        try {
            new DeviceControlManager().turnOnGPS(mAdminName, on);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void shutdownDevice() {
        try {
            new DeviceControlManager().shutdownDevice(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addDisallowedUninstallPackages(List<String> packageNames) {
        try {
            new DevicePackageManager().addDisallowedUninstallPackages(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clearDefaultLauncher() {
        try {
            new DeviceControlManager().clearDefaultLauncher(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTimeAndDateSetDisabled(boolean b) {
        try {
            new DeviceSettingsManager().setTimeAndDateSetDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addNetworkAccessBlackList(ArrayList<String> addDomainList) {
        try {
            new DeviceNetworkManager().addNetworkAccessBlackList(mAdminName, addDomainList);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setWIFIeditDisabled(boolean b) {
        try {
            new DeviceSettingsManager().setWIFIeditDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void installPackage(String path) {
        try {
            new DevicePackageManager().installPackage(mAdminName, path);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void uninstallPackage(String s, boolean b) {
        try {
            new DevicePackageManager().uninstallPackage(mAdminName, s, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setBluetoothDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setBluetoothDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setGPSDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setGPSDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setWifiApDisabled(boolean b) {
        try {
            new DeviceRestrictionManager().setWifiApDisabled(mAdminName, b);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void rebootDevice() {
        try {
            new DeviceControlManager().rebootDevice(mAdminName);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setScreenCaptureDisabled(boolean disabled) {
        try {
            new DeviceRestrictionManager().setScreenCaptureDisabled(mAdminName, disabled);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addPersistentApp(List<String> packageNames) {
        try {
            new DeviceApplicationManager().addPersistentApp(mAdminName, packageNames);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setVpnDisabled(boolean disabled) {
        try {
            new DeviceVpnManager().setVpnDisabled(mAdminName, true);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
