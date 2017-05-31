package cn.kiway.baoantong.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Logger {
    static String packName = "cn.kiway.bat";
    static boolean isLog = true;

    public static void log(Object message) {
        if (isLog)
            Log.e(packName, message.toString());
    }
}
