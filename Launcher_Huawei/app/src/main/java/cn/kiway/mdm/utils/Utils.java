package cn.kiway.mdm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.mdm.MDMHelper;

import static android.content.Context.WIFI_SERVICE;
import static cn.kiway.mdm.KWApp.server;

/**
 * Created by Administrator on 2017/6/8.
 */

public class Utils {

    public static String TAG = "SampleUtils";

    /**
     * Get help string from html file
     *
     * @param context:  Context object
     * @param filePath: html file path
     * @return string
     */
    public static String getStringFromHtmlFile(Context context, String filePath) {
        String result = "";
        if (null == context || null == filePath) {
            return result;
        }

        InputStream stream = null;
        BufferedReader reader = null;
        InputStreamReader streamReader = null;
        try {
            // Read html file into buffer
            stream = context.getAssets().open(filePath);
            streamReader = new InputStreamReader(stream, "utf-8");
            reader = new BufferedReader(streamReader);
            StringBuilder builder = new StringBuilder();
            String line = null;

            boolean readCurrentLine = true;
            // Read each line of the html file, and build a string.
            while ((line = reader.readLine()) != null) {
                // Don't read the Head tags when CSS styling is not supporeted.
                if (line.contains("<style")) {
                    readCurrentLine = false;
                } else if (line.contains("</style")) {
                    readCurrentLine = true;
                }
                if (readCurrentLine) {
                    builder.append(line).append("\n");
                }
            }
            result = builder.toString();
        } catch (FileNotFoundException ex) {
            Log.e(TAG, ex.getMessage());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
            if (null != streamReader) {
                try {
                    streamReader.close();
                } catch (IOException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
        }
        return result;
    }

    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static String getSystem() {
        String SYS = "";
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            }
            ;
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public static ArrayList<App> scanLocalInstallAppList(PackageManager packageManager) {
        ArrayList<App> apps = new ArrayList<>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 通过getPackageManager()的queryIntentActivities方法遍历,得到所有能打开的app的packageName
            List<ResolveInfo> resolveinfoList = packageManager
                    .queryIntentActivities(resolveIntent, 0);
            Set<String> allowPackages = new HashSet();
            for (ResolveInfo resolveInfo : resolveinfoList) {
                allowPackages.add(resolveInfo.activityInfo.packageName);
            }
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if (!allowPackages.contains(packageInfo.packageName)) {
                    continue;
                }
                //屏蔽掉系统设置
                if (packageInfo.packageName.equals("com.android.settings")
                        || packageInfo.packageName.equals("cn.kiway.mdm")) {
                    continue;
                }
                App a = new App();
                a.name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                a.packageName = packageInfo.packageName;
                // a.icon = (packageInfo.applicationInfo.loadIcon(packageManager));
                apps.add(a);
            }
        } catch (Exception e) {
            Log.e("test", "===============获取应用包信息失败");
        }
        return apps;
    }

    public static App getAppByPackageName(PackageManager packageManager, String packageName) {
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if (packageInfo.packageName.equals(packageName)) {
                    App a = new App();
                    a.name = packageInfo.applicationInfo.loadLabel(packageManager).toString();
                    a.packageName = packageInfo.packageName;
                    //  a.icon = (packageInfo.applicationInfo.loadIcon(packageManager));
                    return a;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Drawable getIconByPackageName(PackageManager packageManager, App app) {
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                if (packageInfo.packageName.equals(app.packageName)) {
                    return (packageInfo.applicationInfo.loadIcon(packageManager));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //判断有没有su文件
    public static boolean isRoot() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }
        } catch (Exception e) {
        }
        return root;
    }


    public static boolean hasRoot() {
        int i = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
        if (i != -1) {
            return true;
        }
        return false;
    }


    public static int execRootCmdSilent(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    (OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            int result = localProcess.exitValue();
            return (Integer) result;
        } catch (Exception localException) {
            localException.printStackTrace();
            return -1;
        }
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     *
     * @return 应用程序是/否获取Root权限
     */
    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    public static boolean rangeInDefined(int current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }

    public static String getDateField(long time, int filed) {
        String s = null;
        Date date = new Date(time);
        SimpleDateFormat sdf;
        try {
            switch (filed) {
                case 0:
                    sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 1:
                    sdf = new SimpleDateFormat("MM", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 2:
                    sdf = new SimpleDateFormat("dd", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 3:
                    sdf = new SimpleDateFormat("MM.yyyy", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 4:
                    sdf = new SimpleDateFormat("dd-MM HH:mm", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 5:
                    sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 6:
                    sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 7:
                    sdf = new SimpleDateFormat(" HH:mm MM-dd", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 8:
                    sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 9:
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 10:
                    sdf = new SimpleDateFormat("yyyy年-MM月-dd日 HH:mm:ss",
                            Locale.getDefault());
                    s = sdf.format(date);
                    break;
                case 11:
                    sdf = new SimpleDateFormat("HH", Locale.getDefault());
                    s = sdf.format(date);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static void connectSSID(Context c, String receive) {
        String SSID = "KWHW2";
        String password = "KWF58888";
        if (TextUtils.isEmpty(SSID)) {
            return;
        }
        if (TextUtils.isEmpty(password)) {
            return;
        }
        //0.判断当前连接的是不是这个
        WifiManager wifiManager = (WifiManager) c.getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String currentSSID = wifiInfo.getSSID().replace("\"", "");
        Log.d("test", "currentSSID = " + currentSSID);
        if (currentSSID.equals(SSID)) {
            Log.d("test", "目标正确，不用连了");
            return;
        }
        //1.先打开位置服务
        MDMHelper.getAdapter().turnOnGPS(true);
        //2.搜索附近wifi
        boolean has = false;
        WifiAdmin admin = new WifiAdmin(c);
        admin.startScan();
        List<ScanResult> list = admin.getWifiList();
        for (int i = 0; i < list.size(); i++) {
            Log.d("test", " wifi = " + list.get(i).toString());
            if (list.get(i).SSID.equals(SSID)) {
                has = true;
            }
        }
        //3.连接wifi
        if (has) {
            Log.d("test", "附近有这个wifi");
            connectWifi(admin, SSID, password);
        } else {
            Log.d("test", "附近没有这个wifi");
        }
    }


    public static void connectWifi(WifiAdmin admin, String ssid, String pwd) {
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

    public static void installAPP(Context context, String receive) {
        //2.查看对应包名，手机是否安装有该apk
        //3.如果没有，下载
        //4.静默安装
    }

    public static void uninstallAPP(Context context, String receive) {
        //2.查看对应包名，手机是否安装有该apk
        //3.如果有，静默卸载
    }

    public static void openAPP(Context context, String receive) {
        //2.查看对应包名，直接打开
    }

    public static String getIMEI(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        Log.d("test", "IMEI = " + imei);
        return imei;
    }

    public static void uploadLocation(final MainActivity c, final double longitude, final double latitude) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    //TODO 半小时一次
                    JSONArray array = new JSONArray();
                    JSONObject param = new JSONObject();
                    param.put("IMEI", Utils.getIMEI(c));
                    param.put("longitude", "" + longitude);
                    param.put("latitude", "" + latitude);
                    array.put(param);
                    Log.d("test", "array = " + array.toString());
                    StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                    String url = server + "device/locationTrack";
                    Log.d("test", "locationTrack = " + url);
                    client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "locationTrack onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "locationTrack onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void deviceRuntime(final Activity c, final String imei, final String flag) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.setTimeout(10000);
                            JSONArray array = new JSONArray();
                            JSONObject param = new JSONObject();
                            param.put("IMEI", imei);
                            param.put("flag", flag);
                            array.put(param);
                            Log.d("test", "array = " + array.toString());
                            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                            String url = server + "device/deviceRuntime";
                            Log.d("test", "deviceRuntime = " + url);
                            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "deviceRuntime onSuccess = " + ret);
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    Log.d("test", "deviceRuntime onFailure = " + s);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("test", "e = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void uploadException(MainActivity c) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("IMEI", getIMEI(c));
            param.put("ip", getIP(c));
            param.put("className", "MainActivity");
            param.put("message", "NullPointException At line 76");
            Log.d("test", "param = " + param.toString());
            String url = server + "device/exceptions";
            Log.d("test", "exceptions = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "exceptions onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "exceptions onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    private static String getIP(MainActivity c) {
        try {
            WifiManager wifi_service = (WifiManager) c.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            DhcpInfo dhcpInfo = wifi_service.getDhcpInfo();
            WifiInfo wifiinfo = wifi_service.getConnectionInfo();
            System.out.println("Wifi info----->" + wifiinfo.getIpAddress());
            System.out.println("DHCP info gateway----->" + Formatter.formatIpAddress(dhcpInfo.gateway));
            System.out.println("DHCP info netmask----->" + Formatter.formatIpAddress(dhcpInfo.netmask));
            // DhcpInfo中的ipAddress是一个int型的变量，通过Formatter将其转化为字符串IP地址
            String ip = Formatter.formatIpAddress(dhcpInfo.ipAddress);
            Log.d("test", "ip = " + ip);
            return ip;
        } catch (Exception ex) {
            Log.d("test", "getIp ex = " + ex.toString());
        }
        return "unknown";
    }

    public static void appCharge(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/appCharge";
                    Log.d("test", "appCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "appCharge onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "appCharge onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void networkDeviceCharge(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/networkDeviceCharge";
                    Log.d("test", "networkDeviceCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "networkDeviceCharge onSuccess = " + ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Network> networks = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Network>>() {
                                }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteNetwork();
                                for (Network n : networks) {
                                    new MyDBHelper(c).addNetwork(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "networkDeviceCharge onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void wifi(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/wifi";
                    Log.d("test", "wifi = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "wifi onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "wifi onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }
}
