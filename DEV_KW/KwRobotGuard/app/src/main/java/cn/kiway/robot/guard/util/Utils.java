package cn.kiway.robot.guard.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

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

    public static boolean checkProcess(String packName) {
        // 自己写shell运行
        boolean checkProcess = false;
        if (!checkProcess) {
            // Android5.0.2监控
            Process process = null;
            try {
                String shell = "ps | grep " + packName;
                String[] command = {"sh", "-c", shell};
                process = Runtime
                        .getRuntime()
                        .exec(command);
                InputStream in = process.getInputStream();
                OutputStream out = process.getOutputStream();
                String str = IOUtils.toString(in);
                Log.e("app_run:", str);
                if (str.equals("") | Pattern.compile(Pattern.quote(packName), Pattern.CASE_INSENSITIVE).matcher(str)
                        .find()) {
                    checkProcess = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (process != null) {
                    process.destroy();
                }
            }
        }
        return checkProcess;
    }
}
