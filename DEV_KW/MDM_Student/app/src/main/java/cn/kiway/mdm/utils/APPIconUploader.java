package cn.kiway.mdm.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import cn.kiway.mdm.KWApp;

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
        Log.d("test", "UploadAPPIcon , packageName = " + packageName);
        //1.转成bitmap
        Bitmap b = drawableToBitmap(d);
        if (b == null) {
            Log.d("test", "b==null");
            return;
        }
        //2.存本地
        String localPath = saveMyBitmap(packageName, b);
        if (TextUtils.isEmpty(localPath)) {
            Log.d("test", "localPath==null");
            return;
        }
        //3.上传到服务器
        String token = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
        String result = UploadUtil.uploadFile(localPath, KWApp.serverUrl + "common/file?x-auth-token=" + token, packageName);
        //4.成功的话保存路径到icon
        try {
            String url = new JSONObject(result).getJSONObject("data").getString("url");
            c.getSharedPreferences("icon", 0).edit().putString(packageName, url).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
