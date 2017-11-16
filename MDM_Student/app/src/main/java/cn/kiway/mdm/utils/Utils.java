package cn.kiway.mdm.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.huawei.android.pushagent.api.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.activity.ComposeSmsActivity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.ScreenActivity;
import cn.kiway.mdm.activity.SendSMSActivity;
import cn.kiway.mdm.activity.SettingActivity;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.entity.AppCharge;
import cn.kiway.mdm.entity.Call;
import cn.kiway.mdm.entity.Network;
import cn.kiway.mdm.entity.Wifi;
import cn.kiway.mdm.mdm.MDMHelper;

import static android.content.Context.WIFI_SERVICE;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_APP;
import static cn.kiway.mdm.KWApp.MSG_LAUNCH_MDM;
import static cn.kiway.mdm.KWApp.MSG_LOCK;
import static cn.kiway.mdm.KWApp.server;
import static java.lang.System.currentTimeMillis;

/**
 * Created by Administrator on 2017/6/8.
 */

public class Utils {

    public static String TAG = "SampleUtils";


    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

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

    public static boolean isAppInstall(Context c, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = c.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<App> scanLocalInstallAppList(PackageManager packageManager) {
        ArrayList<App> apps = new ArrayList<>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);

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
                a.versionName = packageInfo.versionName;
                a.versionCode = packageInfo.versionCode;
                if ((packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) <= 0) {
                    // 第三方应用
                    a.category = 2;
                } else {
                    //系统应用
                    a.category = 1;
                }
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

    /**
     * 06
     * 通过包名获取应用程序的名称。
     * 07
     *
     * @param context     08
     *                    Context对象。
     *                    09
     * @param packageName 10
     *                    包名。
     *                    11
     * @return 返回包名所对应的应用程序的名称。
     * 12
     */
    public static String getProgramNameByPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        String name = null;
        try {
            name = pm.getApplicationLabel(pm.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
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

    public static void connectSSID(MainActivity c, String SSID, String password) {
        Log.d("test", "connectSSID SSID = " + SSID + " , password = " + password);
//        SSID = "KWHW2_5G";
//        password = "KWF58888";
        if (TextUtils.isEmpty(SSID)) {
            return;
        }
        if (TextUtils.isEmpty(password)) {
            return;
        }
        //0.判断当前连接的是不是这个
        String currentSSID = getCurrentSSID(c);
        Log.d("test", "currentSSID = " + currentSSID);
        if (currentSSID.equals(SSID)) {
            Log.d("test", "当前连接着这个wifi");
            return;
        }
        //1.先打开位置服务
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MDMHelper.getAdapter().turnOnGPS(true);
            }
        });
        //2.搜索附近wifi
        boolean has = false;
        WifiAdmin admin = new WifiAdmin(c);
        admin.startScan();
        List<ScanResult> list = admin.getWifiList();
        Log.d("test", "搜索到附近的wifi个数" + list.size());
        for (int i = 0; i < list.size(); i++) {
            //Log.d("test", "附近的 wifi = " + list.get(i).toString());
            if (list.get(i).SSID.equals(SSID)) {
                has = true;
                break;
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

    public static String getIMEI(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imei = c.getSharedPreferences("kiway", 0).getString("imei", "");
            if (TextUtils.isEmpty(imei)) {
                imei = genIMEI();
                c.getSharedPreferences("kiway", 0).edit().putString("imei", imei).commit();
            }
        }
        Log.d("test", "IMEI = " + imei);
        return imei;
    }

    public static String genIMEI() {// calculator IMEI
        int r1 = 1000000 + new java.util.Random().nextInt(9000000);
        int r2 = 1000000 + new java.util.Random().nextInt(9000000);
        String input = r1 + "" + r2;
        char[] ch = input.toCharArray();
        int a = 0, b = 0;
        for (int i = 0; i < ch.length; i++) {
            int tt = Integer.parseInt(ch[i] + "");
            if (i % 2 == 0) {
                a = a + tt;
            } else {
                int temp = tt * 2;
                b = b + temp / 10 + temp % 10;
            }
        }
        int last = (a + b) % 10;
        if (last == 0) {
            last = 0;
        } else {
            last = 10 - last;
        }
        return input + last;
    }

    public static void uploadLocation(final MainActivity c, final double longitude, final double latitude) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    JSONArray array = new JSONArray();
                    JSONObject o1 = new JSONObject();
                    o1.put("imei", Utils.getIMEI(c));
                    o1.put("longitude", "" + longitude);
                    o1.put("latitude", "" + latitude);
                    o1.put("operation", "GPS");
                    array.put(o1);
                    Log.d("test", "location array = " + array.toString());
                    StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                    String url = server + "device/locationTrack";
                    Log.d("test", "locationTrack = " + url);
                    client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "locationTrack onSuccess = " + ret);
                            check301(c, ret);
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

    public static void deviceRuntime(final Activity c, final String flag, final boolean check301) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String imei = getIMEI(c);
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                            client.setTimeout(10000);
                            JSONArray array = new JSONArray();
                            JSONObject param = new JSONObject();
                            param.put("imei", imei);
                            param.put("flag", flag);
                            param.put("operation", "submitData");
                            array.put(param);
                            Log.d("test", "array = " + array.toString());
                            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                            String url = server + "device/deviceRuntime";
                            Log.d("test", "deviceRuntime = " + url);
                            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "deviceRuntime onSuccess = " + ret);
                                    if (check301) {
                                        check301(c, ret);
                                    }
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

    public static void exceptions(final MainActivity c) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("imei", getIMEI(c));
            param.put("ip", getIP(c));
            param.put("className", "MainActivity");
            param.put("message", "NullPointException At line 76");
            param.put("operation", "submitData");
            Log.d("test", "param = " + param.toString());
            String url = server + "device/exceptions";
            Log.d("test", "exceptions = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "exceptions onSuccess = " + ret);
                    check301(c, ret);
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
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    String url = server + "device/appCharge?imei=" + getIMEI(c);
                    Log.d("test", "appCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "appCharge onSuccess = " + ret);
                            check301(c, ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<AppCharge> networks = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<AppCharge>>() {
                                }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteAppcharge(null);
                                for (AppCharge n : networks) {
                                    new MyDBHelper(c).addAppcharge(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    String url = server + "device/networkDeviceCharge?imei=" + getIMEI(c);
                    Log.d("test", "networkDeviceCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "networkDeviceCharge onSuccess = " + ret);
                            check301(c, ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Network> networks = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Network>>() {
                                }.getType());
                                //1.存进数据库里
                                new MyDBHelper(c).deleteNetwork(null);
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
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/wifi";
                    Log.d("test", "wifi = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "wifi onSuccess = " + ret);
                            check301(c, ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Wifi> wifis = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Wifi>>() {
                                }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteWifi(null);
                                for (Wifi n : wifis) {
                                    new MyDBHelper(c).addWifi(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

    public synchronized static void checkWifis(final MainActivity m) {
        if (m == null) {
            Log.d("test", "没有界面，推过来也没用");
            return;
        }
        try {
            ArrayList<Wifi> wifis = new MyDBHelper(m).getAllWifis();
            Log.d("test", "wifis = " + wifis);

            final ArrayList<Wifi> rightWifis = new ArrayList();
            for (Wifi w : wifis) {
                String timeRange = w.timeRange;
                JSONArray array = new JSONArray(timeRange);
                int count = array.length();
                if (count == 0) {
                    w.inTimeRange = true;
                } else {
                    for (int i = 0; i < count; i++) {
                        JSONObject o = array.getJSONObject(i);
                        String startTime = o.getString("startTime");
                        String endTime = o.getString("endTime");
                        w.inTimeRange = checkInTimes(startTime, endTime);
                        if (w.inTimeRange) {
                            rightWifis.add(w);
                        }
                    }
                }
            }
            final int in_count = rightWifis.size();
            if (in_count == 0) {
                Log.d("test", "没有一个wifi在当前时间段");
                return;
            }
            Log.d("test", "有" + in_count + "个wifi在当前时间段");
            //如果有N个，怎么办呢
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < in_count; i++) {
                        Wifi w = rightWifis.get(i);
                        Utils.connectSSID(m, w.name, w.password);
                        try {
                            sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (Utils.getCurrentSSID(m).equals(w.name)) {
                            break;
                        }
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized static void checkAppCharges(MainActivity m) {
        if (m == null) {
            Log.d("test", "没有界面，推过来也没用");
            return;
        }
        try {
            //1.检查type
            ArrayList<AppCharge> apps_type0 = new MyDBHelper(m).getAllAppCharges(0);
            Log.d("test", "apps_type0 = " + apps_type0.size());
            ArrayList<AppCharge> apps_type1 = new MyDBHelper(m).getAllAppCharges(1);
            Log.d("test", "apps_type1 = " + apps_type1.size());
            ArrayList<AppCharge> apps_type2 = new MyDBHelper(m).getAllAppCharges(2);
            Log.d("test", "apps_type2 = " + apps_type2.size());
            ArrayList<App> installApps = scanLocalInstallAppList(m.getPackageManager());
            for (AppCharge ac : apps_type0) {
                //必须安装的APP，如果没有安装，要安装上去，注意重复下载的问题。
                boolean installed = false;
                for (App a : installApps) {
                    if (a.packageName.equals(ac.packages)) {
                        installed = true;
                        break;
                    }
                }
                if (installed) {
                    Log.d("test", ac.name + "_" + ac.packages + "已安装");
                } else {
                    Log.d("test", ac.name + "_" + ac.packages + "未安装");
                    //下载安装
                    APKInstaller.install(m, ac.packages, ac.url, ac.name, ac.version);
                }
                //TODO 版本更新
            }
            final ArrayList<String> whiteList = new ArrayList<>();
            for (AppCharge ac : apps_type1) {
                //白名单，调用华为的API
                whiteList.add(ac.packages);
            }
            m.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<String> currentList = MDMHelper.getAdapter().getInstallPackageWhiteList();
                    if (currentList.size() > 0) {
                        MDMHelper.getAdapter().removeInstallPackageWhiteList(currentList);
                    }
                    MDMHelper.getAdapter().addInstallPackageWhiteList(whiteList);
                }
            });
            for (final AppCharge ac : apps_type2) {
                //黑名单，卸载黑名单里的APP
                m.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MDMHelper.getAdapter().uninstallPackage(ac.packages);
                    }
                });
            }
            //2.检查使用时间段
            String runningAPP = Utils.getRunningAPP(m);
            Log.d("test", "runningAPP = " + runningAPP);
            AppCharge app = new MyDBHelper(m).getAppChargesByPackage(runningAPP);
            if (app != null) {
                String timeRange = app.timeRange;// [{start end}{start end}]
                Log.d("test", "timeRange = " + timeRange);
                JSONArray array = new JSONArray(timeRange);
                int count = array.length();
                boolean in = false;
                if (count == 0) {
                    in = true;
                } else {
                    for (int i = 0; i < count; i++) {
                        JSONObject o = array.getJSONObject(i);
                        String startTime = o.getString("startTime");
                        String endTime = o.getString("endTime");
                        in = Utils.checkInTimes(startTime, endTime);
                        if (in) {
                            break;
                        }
                    }
                }
                if (!in) {
                    Intent intent = m.getPackageManager().getLaunchIntentForPackage("cn.kiway.mdm");
                    m.startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //startTime小于endTime
    public static boolean checkInTimes(String startTime, String endTime) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String current = sdf.format(new Date());
        Date dt1 = sdf.parse(startTime);
        Date dt2 = sdf.parse(endTime);
        Date dtNow = sdf.parse(current);
        if (dtNow.getTime() > dt1.getTime() && dtNow.getTime() < dt2.getTime()) {
            return true;
        }
        return false;
    }

    //startTime可以大于endTime
    public static boolean checkInTimes2(String startTime, String endTime)
            throws ParseException {
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        String current = sdf.format(new Date());
        Date dt1 = sdf.parse(startTime);
        Date dt2 = sdf.parse(endTime);
        Date dt3 = sdf.parse("23:59");
        Date dt4 = sdf.parse("00:00");
        Date dtNow = sdf.parse(current);
        if (dt2.getTime() > dt1.getTime()) {
            if (dtNow.getTime() > dt1.getTime()
                    && dtNow.getTime() < dt2.getTime()) {
                return true;
            }
        } else {
            if ((dtNow.getTime() > dt1.getTime() && dtNow.getTime() < dt3
                    .getTime())
                    || (dtNow.getTime() > dt4.getTime() && dtNow.getTime() < dt2
                    .getTime())) {
                return true;
            }
        }
        return false;
    }

    public static void huaweiPush(final Context c) {
        new Thread() {
            @Override
            public void run() {
                PushManager.requestToken(c);
                Log.d("huawei", "try to get Token ,current packageName is " + c.getPackageName());
            }
        }.start();
    }

    public static String getRunningAPP(Context context) {
        String packageName = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = currentTimeMillis();
                //获取60秒之内的应用数据
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 10 * 1000, now);//60
                //取得最近运行的一个app，即当前运行的app
                if ((stats != null) && (!stats.isEmpty())) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    packageName = stats.get(j).getPackageName();
                }
            }
        } else {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = activityManager.getRunningTasks(1).get(0).topActivity;
            packageName = cn.getPackageName();
        }
        Log.d("aaa", "packageName = " + packageName);
        return packageName;
    }

    public static Thread temporary_appThread;
    public static String packageName = null;
    public static String url = null;
    public static String name = null;
    public static String version = null;

    public static void launchApp(final Context c, final JSONObject data) {
        JSONArray content = data.optJSONArray("content");
        try {
            packageName = content.getJSONObject(0).getString("packages");
            url = content.getJSONObject(0).getString("url");
            name = content.getJSONObject(0).getString("name");
            version = content.getJSONObject(0).getString("version");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (temporary_appThread != null) {
            return;
        }
        temporary_appThread = new Thread() {
            @Override
            public void run() {
                while (KWApp.temporary_app) {
                    try {
                        if (!isAppInstall(c, packageName)) {
                            //下载安装
                            APKInstaller.install(MainActivity.instance, packageName, url, name, version);
                            sleep(3000);
                            continue;
                        }
                        String runningAPP = Utils.getRunningAPP(c);
                        if (!runningAPP.equals(packageName)) {
                            Intent intent = c.getPackageManager().getLaunchIntentForPackage(packageName);
                            c.startActivity(intent);
                        }
                        sleep(1000);
                    } catch (Exception e) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                temporary_appThread = null;
            }
        };
        temporary_appThread.start();
    }

    public static void uploadApp(final MainActivity c) {
        if (!NetworkUtil.isWifi(c)) {
            return;
        }
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //判断是不是wifi环境
                //1.上报APP列表
                //2.上传APP图标
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    String url = server + "device/appInstallation";
                    Log.d("test", "applist url = " + url);
                    JSONArray array = new JSONArray();
                    ArrayList<App> installApps = scanLocalInstallAppList(c.getPackageManager());
                    int count = installApps.size();
                    String imei = Utils.getIMEI(c);
                    for (int i = 0; i < count; i++) {
                        JSONObject o1 = new JSONObject();
                        App a = installApps.get(i);
                        o1.put("imei", imei);
                        o1.put("appName", a.name);
                        o1.put("packages", a.packageName);
                        o1.put("versionName", a.versionName);
                        o1.put("versionCode", a.versionCode);
                        o1.put("category", a.category);
                        array.put(o1);
                    }
                    Log.d("test", "applist array = " + array.toString());
                    StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                    client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "applist onSuccess = " + ret);
                            check301(c, ret);
                            String today = getToday();
                            c.getSharedPreferences("kiway", 0).edit().putBoolean(today, true).commit();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "applist onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static String getToday() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(d);
        return today;
    }

    public static String getCurrentSSID(MainActivity c) {
        WifiManager wifiManager = (WifiManager) c.getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String currentSSID = wifiInfo.getSSID().replace("\"", "");
        return currentSSID;
    }

    public static void openFile(Context context, String filePath) {
        Log.d("test", "openFile filepath = " + filePath);
        filePath = "file://" + filePath;
        String filetype = filePath.split("\\.")[1].toLowerCase();
        Log.d("test", "filetype = " + filetype);
        String typeOpenFile = "*";
        if (filetype.equals("pdf"))
            typeOpenFile = "application/pdf";
        else if (filetype.equals("ppt") || filetype.equals("pptx"))
            typeOpenFile = "application/vnd.ms-powerpoint";
        else if (filetype.equals("doc") || filetype.equals("docx") || filetype.equals("docm") || filetype.equals("dotx") || filetype
                .equals("dotm"))
            typeOpenFile = "application/msword";
        else if (filetype.equals("xlsx") || filetype.equals("xlsm") || filetype.equals("xltx"))
            typeOpenFile = "application/vnd.ms-excel";
        else if (filetype.equals("mp3") || filetype.equals("amr") || filetype.equals("ogg") || filetype.equals("wav")) {
            typeOpenFile = "audio/*";
        } else if (filetype.equals("mp4") || filetype.equals("3gp") || filetype.equals("avi") || filetype.equals("rmvb") || filetype
                .equals("mpg") | filetype.equals("rm") || filetype.equals("flv")) {
            typeOpenFile = "video/*";
        } else if (filetype.equals("swf")) {
            typeOpenFile = "application/x-shockwave-flash";
        } else if (filetype.equals("jpg") || filetype.equals("jpeg") || filetype.equals("png")) {
            typeOpenFile = "image/*";
        } else if (filetype.equals("apk")) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            context.startActivity(intent);
            return;
        }
        Log.d("test", "typeOpenFile = " + typeOpenFile);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse(filePath), typeOpenFile);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (typeOpenFile.equals("video/*") || typeOpenFile.equals("audio/*"))
                Toast.makeText(context, "手机没有安装相关的播放器，请下载安装", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "手机没有安装相关的办公软件，请下载安装", Toast.LENGTH_SHORT).show();
        }
    }

    public static void appFunction(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/appFunction?imei=" + getIMEI(c);
                    Log.d("test", "appFunction = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "appFunction onSuccess = " + ret);
                            check301(c, ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                int count = data.length();
                                for (int i = 0; i < count; i++) {
                                    JSONObject o = data.getJSONObject(i);
                                    String commandT = o.optString("command");
                                    int flag = o.optInt("flag");
                                    c.getSharedPreferences("kiway", 0).edit().putInt("flag_" + commandT, flag).commit();
                                }
                                KWApp.instance.excuteFlagCommand();
                            } catch (Exception e) {
                                Log.d("test", "e = " + e.toString());
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "appFunction onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void logout(final SettingActivity c) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                            client.setTimeout(10000);
                            String url = server + "device/logout";
                            Log.d("test", "url = " + url);
                            RequestParams param = new RequestParams();
                            param.put("operation", "invalidate");
                            Log.d("test", "param = " + param.toString());
                            client.post(c, url, param, new TextHttpResponseHandler() {

                                @Override
                                public void onSuccess(int arg0, Header[] arg1, String ret) {
                                    Log.d("test", "logout success");
                                }

                                @Override
                                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                                    Log.d("test", "logout failure");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("test", "exception = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void installationPush(Context c, final String token, final String imei) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            if (TextUtils.isEmpty(xtoken)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            client.setTimeout(10000);
            Log.d("test", "huaweitoken = " + token);
            JSONObject param = new JSONObject();
            param.put("appId", "2747ffbb3cfca89d0084d3d95fe42c3f");
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", token);
            param.put("module", "student");
            Log.d("test", "param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = server + "push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public static void uninstallPush(final SettingActivity c) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                            if (TextUtils.isEmpty(xtoken)) {
                                return;
                            }
                            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
                            Log.d("test", "huaweitoken = " + token);
                            AsyncHttpClient client = new AsyncHttpClient();
                            Log.d("test", "xtoken = " + xtoken);
                            client.addHeader("x-auth-token", xtoken);
                            client.setTimeout(10000);
                            RequestParams param = new RequestParams();
                            param.put("type", "huawei");
                            param.put("imei", getIMEI(c));
                            param.put("token", token);
                            Log.d("test", "param = " + param.toString());
                            String url = server + "device/uninstall";
                            Log.d("test", "uninstallPush = " + url);
                            client.post(c, url, param, new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "uninstallPush onSuccess = " + ret);
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    Log.d("test", "uninstallPush onFailure = " + s);
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

    private static boolean is301 = false;

    public static boolean check301(final Activity c, String result) {
        if (c == null) {
            return false;
        }
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        try {
            int statusCode = new JSONObject(result).optInt("statusCode");
            if (statusCode != 301) {
                return false;
            }
            Log.d("test", "301 happen");
            if (is301) {
                return true;
            }
            is301 = true;

            final String imei = Utils.getIMEI(c);
            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = server + "device/login";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("schoolId", c.getSharedPreferences("kiway", 0).getString("schoolId", ""));
            param.put("classId", c.getSharedPreferences("kiway", 0).getString("classId", ""));
            param.put("studentNumber", c.getSharedPreferences("kiway", 0).getString("studentNumber", ""));
            param.put("name", c.getSharedPreferences("kiway", 0).getString("name", ""));
            param.put("mobileModel", Build.MODEL);
            param.put("mobileBrand", Build.BRAND);
            param.put("IMEI", imei);
            param.put("id", "");
            param.put("platform", "Android");
            param.put("token", token);
            param.put("operation", "login");
            Log.d("test", "param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "login onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        String token = o.getJSONObject("data").getString("token");
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                    } catch (Exception e) {
                    }
                    is301 = false;
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "login failure");
                    is301 = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            is301 = false;
        }

        return true;
    }

    public static void resetFunctions(Context context) {
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_camera", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_snapshot", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_location", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_dataconnectivity", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_microphone", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_restore", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_ap", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_app_open", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_usb", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_wifi", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_systemupdate", 0).commit();
        context.getSharedPreferences("kiway", 0).edit().putInt("flag_bluetooth", 0).commit();
    }


    public static void hideSoftInput(Context c, IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static long dateToLong(String currentTime) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = sdf.parse(currentTime);
        long lTime = dt.getTime();
        return lTime;
    }

    public static String longToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(Long.parseLong(time));
        String str = sdf.format(date);
        return str;
    }

    public static void checkTemperary(MainActivity c) {
        if (KWApp.instance == null) {
            return;
        }
        if (KWApp.instance.currentActivity == null) {
            return;
        }
        try {
            //1.锁屏
            long lock_time = c.getSharedPreferences("kiway", 0).getLong("lock_time", 0L);
            if (lock_time != 0) {
                long currentTime = currentTimeMillis();
                if (currentTime < (lock_time + 60 * 60 * 1000)) {
                    //自动锁屏
                    if (!(KWApp.instance.currentActivity instanceof ScreenActivity)) {
                        Message m = new Message();
                        m.what = MSG_LOCK;
                        KWApp.instance.mHandler.sendMessage(m);
                    }
                } else {
                    //自动解锁屏幕
                    if (KWApp.instance.currentActivity instanceof ScreenActivity) {
                        Message m = new Message();
                        m.what = MSG_LOCK;
                        KWApp.instance.mHandler.sendMessage(m);
                        c.getSharedPreferences("kiway", 0).edit().putLong("lock_time", 0).commit();
                    }
                }
            }
            //2.管控
            long app_time = c.getSharedPreferences("kiway", 0).getLong("app_time", 0L);
            String app_data = c.getSharedPreferences("kiway", 0).getString("app_data", "");
            if (app_time != 0) {
                JSONObject data = new JSONObject(app_data);
                long currentTime = currentTimeMillis();
                if (currentTime < (app_time + 60 * 60 * 1000)) {
                    //自动管控
                    Message m = new Message();
                    m.what = MSG_LAUNCH_APP;
                    m.obj = data;
                    KWApp.instance.mHandler.sendMessage(m);
                } else {
                    //自动解除管控
                    Message m = new Message();
                    m.what = MSG_LAUNCH_MDM;
                    KWApp.instance.mHandler.sendMessage(m);
                    c.getSharedPreferences("kiway", 0).edit().putLong("app_time", 0).commit();
                    c.getSharedPreferences("kiway", 0).edit().putString("app_data", "").commit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkInAppcharges(MainActivity m, String packageName) {
        ArrayList<AppCharge> apps_type0 = new MyDBHelper(m).getAllAppCharges(0);
        ArrayList<AppCharge> apps_type1 = new MyDBHelper(m).getAllAppCharges(1);
        boolean in = false;
        for (AppCharge a : apps_type0) {
            if (a.packages.equals(packageName)) {
                in = true;
                break;
            }
        }
        for (AppCharge a : apps_type1) {
            if (a.packages.equals(packageName)) {
                in = true;
                break;
            }
        }
        return in;
    }

    public static Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static AlertDialog bindDialog;

    public static void showBindDialog(final Activity m, JSONObject data) {
        if (m == null) {
            return;
        }
        String token = data.optString("token");

        if (bindDialog != null && bindDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(m, AlertDialog.THEME_HOLO_LIGHT);
        builder.setMessage("家长要绑定这个帐号，是否同意？");
        builder.setTitle("提示");
        final String finalToken = token;
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doBind(m, 1, finalToken);
            }
        });
        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doBind(m, 2, finalToken);
            }
        });
        bindDialog = builder.create();
        //bindDialog.setCancelable(false);
        //bindDialog.setCanceledOnTouchOutside(false);
        bindDialog.show();
    }

    private static void doBind(final Activity c, final int flag, final String token) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    String xauthtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    Log.d("test", "xauthtoken = " + xauthtoken);
                    client.addHeader("x-auth-token", xauthtoken);
                    client.setTimeout(10000);
                    String url = server + "device/student/response";
                    Log.d("test", "doBind = " + url);
                    RequestParams param = new RequestParams();
                    param.put("flag", flag);
                    param.put("token", token);
                    Log.d("test", "doBind param = " + param.toString());
                    client.post(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "doBind onSuccess = " + ret);
                            boolean happen301 = check301(c, ret);
                            if (happen301) {
                                Log.d("test", "超时了，再次请求doBind");
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        doBind(c, flag, token);
                                    }
                                }.start();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "doBind onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    //0：没有设置network 1：白名单启用  2：黑名单启用
    public static int getEnable_Network(Context c) {
        //1.一个都没有，返回0
        ArrayList<Network> networks = new MyDBHelper(c).getAllNetworks(0);
        if (networks.size() == 0) {
            return 0;
        }
        //2.找到enable=1的type
        int enable_type = 0;
        for (Network n : networks) {
            if (n.enable == 1) {
                enable_type = n.type;
                break;
            }
        }
        //如果都没有，那就返回0
        return enable_type;
    }

    private static AlertDialog closerDialog;

    public static void showCloserDialog(Activity m) {
        if (m == null) {
            return;
        }

        if (closerDialog != null && closerDialog.isShowing()) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(m, AlertDialog.THEME_HOLO_LIGHT);
        builder.setMessage("靠太近了对视力不好哦");
        builder.setTitle("提示");
        builder.setPositiveButton("知道了", null);
        closerDialog = builder.create();
        //closerDialog.setCancelable(false);
        //closerDialog.setCanceledOnTouchOutside(false);
        closerDialog = builder.create();
        closerDialog.show();
    }

    public static void hideCloserDialog() {
        if (closerDialog == null) {
            return;
        }
        closerDialog.dismiss();
    }

    public static void getCalls(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = server + "device/calls?imei=" + getIMEI(c);
                    Log.d("test", "calls = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "calls onSuccess = " + ret);
                            check301(c, ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Call> calls = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Call>>() {
                                }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteCall(null);
                                for (Call n : calls) {
                                    new MyDBHelper(c).addCall(n);
                                }
                                //TODO 这里要调用一下华为的方法
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "calls onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    //超出10分钟，命令无效
    public static boolean checkCommandAvailable(String commandTime) throws Exception {
        long commandTime_l = dateToLong(commandTime);
        long currentTime = System.currentTimeMillis();
        if (currentTime > (commandTime_l + 10 * 60 * 1000)) {
            Log.d("test", "checkCommandAvailable false!!!");
            return false;
        }
        return true;
    }


    //0：没有设置来电 1：白名单启用  2：黑名单启用
    public static int getEnable_Call_Come(Context c) {
        //1.一个都没有，返回0
        ArrayList<Call> calls = new MyDBHelper(c).getAllCalls(1);
        if (calls.size() == 0) {
            return 0;
        }
        //2.找到enable=1的type
        int enable_type = 0;
        for (Call n : calls) {
            if (n.enable == 1) {
                enable_type = n.type;
                break;
            }
        }
        //如果都没有，那就返回0
        return enable_type;
    }

    public static void call(Activity c, String number) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(intent);

        Utils.childOperation(c, "calls", "打电话给" + number);
    }


    public static void childOperation(final Activity c, String type, String message) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("IMEI", getIMEI(c));
            param.put("type", type);
            param.put("message", message);
            Log.d("test", "param = " + param.toString());
            String url = server + "device/parent/child/operation";
            Log.d("test", "childOperation = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "childOperation onSuccess = " + ret);
                    check301(c, ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "childOperation onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public static void showSMSDialog(final Activity m, SmsMessage sms) {
        if (m == null) {
            return;
        }
        if (m instanceof ComposeSmsActivity) {
            ((ComposeSmsActivity) m).refresh();
            return;
        }
        if (m instanceof SendSMSActivity) {
            ((SendSMSActivity) m).refresh();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(m, AlertDialog.THEME_HOLO_LIGHT);
        builder.setMessage("您有新的短信");
        builder.setTitle("提示");
        builder.setPositiveButton("前往查看", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m.startActivity(new Intent(m, ComposeSmsActivity.class));
            }
        });
        builder.setNegativeButton("好的", null);
        AlertDialog bindDialog = builder.create();
        bindDialog.show();
    }

    public static void checkShutDown(MainActivity m) {
        if (m == null) {
            return;
        }
        try {
            String shutdown_startTime = m.getSharedPreferences("kiway", 0).getString("shutdown_startTime", "");
            String shutdown_endTime = m.getSharedPreferences("kiway", 0).getString("shutdown_endTime", "");
            Log.d("test", "checkShutDown = " + shutdown_startTime + "-" + shutdown_endTime);
            if (TextUtils.isEmpty(shutdown_startTime)) {
                return;
            }
            if (TextUtils.isEmpty(shutdown_endTime)) {
                return;
            }
            boolean in = checkInTimes2(shutdown_startTime, shutdown_endTime);
            if (!in) {
                return;
            }
            Log.d("test", "当前时间要关机。。。。。。");
            //TODO 要结合时间表。。。
            m.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //MDMHelper.getAdapter().shutdownDevice();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean checkCallEnable(Context c, String incomingNumber) {
        //true不用挂断，false挂断

        int enable_type = Utils.getEnable_Call_Come(c);
        Log.d("test", "enable_type = " + enable_type);
        if (enable_type == 0) {
            Log.d("test", "后台没有设置过call，或者设置过都清除了");
            return true;
        }
        ArrayList<Call> calls = new MyDBHelper(c).getAllCalls(1);
        //1.白名单启用
        if (enable_type == 1) {
            boolean in = false;
            for (Call n : calls) {
                if (incomingNumber.equals(n.phone)) {
                    in = true;
                    break;
                }
            }
            return in;
        }
        //黑名单启用
        if (enable_type == 2) {
            boolean in = false;
            for (Call n : calls) {
                if (incomingNumber.equals(n.phone)) {
                    in = true;
                    break;
                }
            }
            return !in;
        }
        return false;
    }

}
