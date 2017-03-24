package yjpty.teaching.util;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import yjpty.teaching.App;


/**
 * 应用工具集
 *
 * @author Zao
 */
public class AppUtil {
    public static App getApplication(Context context) {
        return (App) context.getApplicationContext();

    }

    /**
     * 验证是否在wifi环境下
     */
    public static boolean isWifiActive(Context icontext) {
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI")
                            && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取网咯的 加密方式
     */
    public static int getCipherType(Context context, String ssid) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> list = wifiManager.getScanResults();
        for (ScanResult scResult : list) {
            if (!TextUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                String capabilities = scResult.capabilities;
                Log.i("hefeng", "capabilities=" + capabilities);
                if (!TextUtils.isEmpty(capabilities)) {
                    if (capabilities.contains("WPA")
                            || capabilities.contains("wpa")) {
                        Log.i("hefeng", "wpa");
                        return 3;
                    } else if (capabilities.contains("WEP")
                            || capabilities.contains("wep")) {
                        Log.i("hefeng", "wep");
                        return 2;
                    } else {
                        Log.i("hefeng", "no");
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public static String getWifiServerIp(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            StringBuilder sb = new StringBuilder();
            sb.append(i & 0xFF).append(".");
            sb.append((i >> 8) & 0xFF).append(".");
            sb.append((i >> 16) & 0xFF).append(".");
            sb.append((i >> 24) & 0xFF);
            return sb.toString();
        } catch (Exception ex) {
            return " 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!\n" + ex.getMessage();
        }
    }


    /**
     * 获取当前网络名字
     */
    public static String getConnectWifiSsid(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSSID() == null)
            return "";
        return wifiInfo.getSSID().replace("\"", "");// 手机适配//在魅族手机获取的名字带有双引号，这边去除
    }



    /**
     * 手机震动
     *
     * @param context      上下文
     * @param milliseconds 震动时长
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context
                .getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

}
