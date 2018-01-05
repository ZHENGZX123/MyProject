package cn.kiway.mdm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.util.DisplayMetrics;

/**
 * @author (作者名) chen jian
 * @version (版本标识) 1.0
 */
public class DspPackgeUtil {

    public static boolean isPackageExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }

        PackageInfo info = null;

        try {
            info = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            info = null;
        }

        return null == info ? false : true;
    }

    public static boolean isActivityExist(Context context, String pkgName, String activityFullName) {
        final Intent in = new Intent();
        in.setClassName(pkgName, activityFullName);
        if (context.getPackageManager().resolveActivity(in, 0) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void setScreenOrientation(Activity act) {
        final DisplayMetrics dm = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm.widthPixels < dm.heightPixels) {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
