package cn.kiway.mdm.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/1.
 */

public class APPIconUploader {

    public static String getAPPIcon(Context c, String packageName) {
        String iconUrl = c.getSharedPreferences("icon", 0).getString(packageName, "");
        Log.d("test", "getAPPIcon , packageName = " + packageName + " , iconUrl = " + iconUrl);
        return iconUrl;
    }

    public static void UploadAPPIcon(Context c, String packageName) {
        String iconUrl = c.getSharedPreferences("icon", 0).getString(packageName, "");
        if (!TextUtils.isEmpty(iconUrl)) {
            Log.d("test", "icon已存在");
            return;
        }
        PackageManager packageManager = c.getPackageManager();
        Drawable d = Utils.getIconByPackageName(packageManager, packageName);
        if (d == null) {
            Log.d("test", "获取不到app图标");
            return;
        }
        //上传到后台并且保存地址


    }

}
