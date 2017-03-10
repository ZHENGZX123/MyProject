package com.alibaba.weex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.weex.utils.WifiAdmin;
import com.google.zxing.client.android.CaptureActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.List;


public class MyWifiManager extends WXModule {


    @JSMethod(uiThread = true)
    public void showWifi(JSCallback callback) {

        WifiAdmin admin = new WifiAdmin(mWXSDKInstance.getContext());
        admin.startScan();
        List<ScanResult> list = admin.getWifiList();
        for (int i = 0; i < list.size(); i++) {
            Log.d("test", " wifi = " + list.get(i).toString());
        }
        callback.invokeAndKeepAlive("您的周边有" + list.size() + "个热点");
    }

    @JSMethod(uiThread = true)
    public void scan() {
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(new Intent(mWXSDKInstance.getContext(), CaptureActivity.class), 999);
    }


    @JSMethod(uiThread = true)
    public void connectWifi(String ssid, String pwd) {
        WifiAdmin admin = new WifiAdmin(mWXSDKInstance.getContext());

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "onActivityResult");
        if (data == null) {
            return;
        }
        String result = data.getStringExtra("result");
        Toast.makeText(mWXSDKInstance.getContext(), "扫描到的是" + result, Toast.LENGTH_SHORT).show();
    }


}

