/*
 * IMPORTANT:  This Huawei software is supplied to you by Huawei Technologies Co., Ltd. 
 * ("Huawei") in consideration of your agreement to the following
 * terms, and your use, copy, installation, modification or redistribution of
 * this Huawei software constitutes acceptance of these terms.  If you do
 * not agree with these terms, please do not use, copy, install, modify or
 * redistribute this Huawei software.

 * In consideration of your agreement to abide by the following terms, and
 * subject to these terms, Huawei grants you a personal, non-exclusive
 * license, under Huawei's copyrights in this original Huawei software(hereinafter referred as “Huawei Software”), to use, reproduce, modify and redistribute the Huawei Software, with or without modifications, in source and/or binary forms;
 * provided that if you redistribute the Huawei Software in its entirety and
 * without modifications, you must retain this notice and the following
 * text and disclaimers in all such redistributions of the Huawei Software.
 * Neither the name, trademarks, service marks or logos of Huawei Technologies Co.. Ltd. may
 * be used to endorse or promote products derived from the Huawei Software
 * without specific prior written permission from Huawei.  Except as
 * expressly stated in this notice, no other rights or licenses, express or
 * implied, are granted by Huawei herein, including but not limited to any
 * patent rights that may be infringed by your derivative works or by other
 * works in which the Huawei Software may be incorporated.

 * The Huawei Software is provided by Huawei on an "AS IS" basis.  Huawei
 * MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE, REGARDING THE HUAWEI SOFTWARE OR ITS USE AND
 * OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.

 * IN NO EVENT SHALL HUAWEI BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 * MODIFICATION AND/OR DISTRIBUTION OF THE HUAWEI SOFTWARE, HOWEVER CAUSED
 * AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 * STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.

 * Copyright (C) 2015 Huawei Technologies Co., Ltd. All Rights Reserved.

 * Trademarks and Permissions
 * Huawei and other Huawei trademarks are trademarks of Huawei Technologies Co., Ltd.
 * All other trademarks and trade names mentioned in this document are the property of their respective holders.
 */
package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.android.app.admin.DeviceControlManager;
import com.huawei.android.app.admin.DeviceRestrictionManager;
import com.huawei.android.pushagent.api.PushManager;

import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.util.SampleEula;
import cn.kiway.mdm.util.WifiAdmin;

public class SampleMainActivity extends Activity {
    private DeviceRestrictionManager mDeviceRestrictionManager = null;
    private DeviceControlManager mDeviceControlManager = null;
    private DevicePolicyManager mDevicePolicyManager = null;
    private ComponentName mAdminName = null;

    private TextView wifiStatusText;
    private Button wifiDisableBtn;
    private Button wifiEnableBtn;

    private TextView dropdownStatusText;
    private Button dropdownDisableBtn;
    private Button dropdownEnableBtn;

    private TextView settingStatusText;
    private Button settingDisableBtn;
    private Button settingEnableBtn;

    private Button shutdownDevice;
    private Button connectSSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        huaweiPush();

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mAdminName = new ComponentName(this, SampleDeviceReceiver.class);

        mDeviceRestrictionManager = new DeviceRestrictionManager();
        mDeviceControlManager = new DeviceControlManager();

        initSampleView();
        updateState();
        new SampleEula(this, mDevicePolicyManager, mAdminName).show();
    }

    private void initSampleView() {
        wifiStatusText = (TextView) findViewById(R.id.wifiStateTxt);
        wifiDisableBtn = (Button) findViewById(R.id.disableWifi);
        wifiEnableBtn = (Button) findViewById(R.id.enableWifi);

        dropdownStatusText = (TextView) findViewById(R.id.dropdownStateTxt);
        dropdownDisableBtn = (Button) findViewById(R.id.disableDropdown);
        dropdownEnableBtn = (Button) findViewById(R.id.enableDropdown);

        settingStatusText = (TextView) findViewById(R.id.settingStateTxt);
        settingDisableBtn = (Button) findViewById(R.id.disableSetting);
        settingEnableBtn = (Button) findViewById(R.id.enableSetting);

        shutdownDevice = (Button) findViewById(R.id.shutdownDevice);
        connectSSID = (Button) findViewById(R.id.connectSSID);

        wifiDisableBtn.setOnClickListener(new SampleOnClickListener());
        wifiEnableBtn.setOnClickListener(new SampleOnClickListener());
        dropdownDisableBtn.setOnClickListener(new SampleOnClickListener());
        dropdownEnableBtn.setOnClickListener(new SampleOnClickListener());
        settingDisableBtn.setOnClickListener(new SampleOnClickListener());
        settingEnableBtn.setOnClickListener(new SampleOnClickListener());

        shutdownDevice.setOnClickListener(new SampleOnClickListener());
        connectSSID.setOnClickListener(new SampleOnClickListener());
    }

    private void updateState() {
        if (!isActiveMe()) {
            wifiStatusText.setText(getString(R.string.state_not_actived));
            return;
        }
        boolean isWifiDisabled = false;
        try {
            isWifiDisabled = mDeviceRestrictionManager.isWifiDisabled(mAdminName);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        if (isWifiDisabled) {
            wifiStatusText.setText("已禁用");
        } else {
            wifiStatusText.setText("未禁用");
        }

        boolean isDropdownDisabled = false;
        try {
            isDropdownDisabled = mDeviceRestrictionManager.isStatusBarExpandPanelDisabled(mAdminName);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        if (isDropdownDisabled) {
            dropdownStatusText.setText("已禁用");
        } else {
            dropdownStatusText.setText("未禁用");
        }

//        boolean isSettingDisabled = false;
//        try {
//            isSettingDisabled = mDeviceRestrictionManager.isSettingsApplicationDisabled(mAdminName);
//        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(),
//                    Toast.LENGTH_SHORT).show();
//        }
//        if (isSettingDisabled) {
//            settingStatusText.setText("已禁用");
//        } else {
//            settingStatusText.setText("未禁用");
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateState();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean isActiveMe() {
        if (mDevicePolicyManager == null || !mDevicePolicyManager.isAdminActive(mAdminName)) {
            return false;
        } else {
            return true;
        }
    }

    private class SampleOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                doClick(v);
            } catch (Exception e) {
                e.printStackTrace();
                toast(e.getMessage());
            }
            updateState();
        }
    }

    private void doClick(View v) throws Exception {
        int id = v.getId();
        switch (id) {
            case R.id.disableWifi:
                mDeviceRestrictionManager.setWifiDisabled(mAdminName, true);
                break;
            case R.id.enableWifi:
                mDeviceRestrictionManager.setWifiDisabled(mAdminName, false);
                break;
            case R.id.disableDropdown:
                mDeviceRestrictionManager.setStatusBarExpandPanelDisabled(mAdminName, true);
                break;
            case R.id.enableDropdown:
                mDeviceRestrictionManager.setStatusBarExpandPanelDisabled(mAdminName, false);
                break;
            case R.id.disableSetting:
                mDeviceRestrictionManager.setSettingsApplicationDisabled(mAdminName, true);
                break;
            case R.id.enableSetting:
                mDeviceRestrictionManager.setSettingsApplicationDisabled(mAdminName, false);
                break;
            case R.id.shutdownDevice:
                mDeviceControlManager.shutdownDevice(mAdminName);
                break;
            case R.id.connectSSID:
                connectSSID();
                break;

        }
    }

    private void connectSSID() {
        String SSID = "KWHW2";
        String password = "KWF58888";


        boolean has = false;
        WifiAdmin admin = new WifiAdmin(this);
        admin.startScan();
        List<ScanResult> list = admin.getWifiList();
        for (int i = 0; i < list.size(); i++) {
            Log.d("test", " wifi = " + list.get(i).toString());
            if (list.get(i).SSID.equals(SSID)) {
                has = true;
            }
        }
        if (has) {
            Log.d("test", "有这个wifi");
            connectWifi(SSID, password);
        } else {
            Log.d("test", "xxxx");
        }
    }


    public void connectWifi(String ssid, String pwd) {
        WifiAdmin admin = new WifiAdmin(this);
        String capabilities = "[WPA-PSK-CCMP][WPA2-PSK-CCMP][ESS]";
        int type = 1;
        if (capabilities.contains("WEP")) {
            type = 2;
        } else if (capabilities.contains("WPA")) {
            type = 3;
        }
        Log.d("test", "type = " + type);
        if (type == 1) {
            String SSID = ssid;
            if (Build.VERSION.SDK_INT >= 21) {
                SSID = "" + SSID + "";
            } else {
                SSID = "\"" + SSID + "\"";
            }
            WifiConfiguration config = new WifiConfiguration();
            config.SSID = SSID;
            config.allowedKeyManagement
                    .set(WifiConfiguration.KeyMgmt.NONE);
            admin.addNetwork(config);
        } else {
            String SSID = ssid;
            if (Build.VERSION.SDK_INT >= 21) {
                SSID = "" + SSID + "";
            } else {
                SSID = "\"" + SSID + "\"";
            }
            admin.addNetwork(admin.CreateWifiInfo(SSID,
                    pwd, type));
        }
    }

    private void toast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());
    }
}