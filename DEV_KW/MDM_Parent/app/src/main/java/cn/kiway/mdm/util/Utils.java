package cn.kiway.mdm.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import cn.kiway.mdm.activity.MainActivity;

/**
 * Created by Administrator on 2017/7/5.
 */

public class Utils {

    /**
     * 判断是否平板设备
     *
     * @param context
     * @return true:平板,false:手机
     */
    public static boolean isTabletDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getCurrentVersion(Context c) {
        String versionName = "1.0.0";
        try {
            PackageInfo pkg = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
            versionName = pkg.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getTimeFormLong(long time) {
        if (time <= 0) {
            return "00:00";
        }
        int secondnd = (int) ((time / 1000) / 60);
        int million = (int) ((time / 1000) % 60);
        String f = secondnd >= 10 ? String.valueOf(secondnd) : "0" + String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0" + String.valueOf(million);
        return f + ":" + m;
    }

    public static void checkNetWork(Context context, boolean reConnect) {
        //获取手机的连接服务管理器，这里是连接管理器类
        try {
            boolean available = NetworkUtil.isNetworkAvailable(context);
            Message msg = new Message();
            if (available) {
                msg.what = 1;
                msg.arg1 = 1;
                msg.arg2 = reConnect ? 1 : 0;
            } else {
                msg.what = 1;
                msg.arg1 = 0;
                msg.arg2 = reConnect ? 1 : 0;
            }
            MainActivity.instance.mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
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

}
