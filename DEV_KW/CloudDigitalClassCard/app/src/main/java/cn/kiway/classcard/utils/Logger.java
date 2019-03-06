package cn.kiway.classcard.utils;

import android.util.Log;

/**
 * Created by Administrator on 2019/1/25.
 * 日志打印
 */

public class Logger {
    static String TAG = "test";
    static boolean isLog = true;

    public static void d(Object obj) {
        if (isLog)
            Log.d(TAG, obj.toString());
    }

    public static void e(Object obj) {
        if (isLog)
            Log.d(TAG, obj.toString());
    }
}
