package cn.kiway.homework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/7/5.
 */

public class Utils {
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
}
