package cn.kiway.mdm.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/11/1.
 */

public class Logger {
    public static boolean isLog = true;
    public static String isLogTag = "scoket";

    public static void log(Object object) {
        if (isLog&&object!=null)
            Log.e(isLogTag, object.toString());
    }
}
