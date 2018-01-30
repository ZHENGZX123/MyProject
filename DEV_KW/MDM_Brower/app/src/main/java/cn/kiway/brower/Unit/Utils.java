package cn.kiway.brower.Unit;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2018/1/30.
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
