package cn.kiway.robot.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;

import cn.kiway.robot.KWApplication;
import io.netty.util.internal.StringUtil;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Utils {

    public static String getIMEI(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        Log.d("test", "IMEI = " + imei);
        return imei;
    }

    public static boolean isInfilters(Context c, String sender) {
        String f = c.getSharedPreferences("filters", 0).getString("filters", "");
        String filters[] = f.split("===");
        if (filters.length == 0) {
            return false;
        }
        for (String temp : filters) {
            if (StringUtil.isNullOrEmpty(temp)) {
                continue;
            }
            if (temp.equals(sender)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGetPic(Context c, String content) {
        if (!content.equals("[图片]")) {
            return true;
        }
        return c.getSharedPreferences("getPic", 0).getBoolean("getPic", true);
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

    public static String getParentRemark(Context c) {
        String parentId = FileUtils.readSDCardFile(KWApplication.ROOT + "parent.txt", c);
        if (TextUtils.isEmpty(parentId)) {
            parentId = "1";
        } else {
            int id = Integer.parseInt(parentId);
            parentId = "" + (id + 1);
        }
        FileUtils.saveFile(parentId, "parent.txt");
        return parentId;
    }

    public static String getForwardFrom(Context c) {
        return c.getSharedPreferences("forwardfrom", 0).getString("forwardfrom", "wxid_cokkmqud47e121的接口测试号");//转发使者 wxid_cokkmqud47e121的接口测试号
    }

    public static String getFCFrom(Context c) {
        return c.getSharedPreferences("FCFrom", 0).getString("FCFrom", "朋友圈使者");
    }

    public static String getToday() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        String today = "" + year + month + date;
        return today;
    }
}
