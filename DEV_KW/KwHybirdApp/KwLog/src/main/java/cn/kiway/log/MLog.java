package cn.kiway.log;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MLog {
    public static boolean DEBUG = true;

    public static void d(String tag, String value) {
        if (DEBUG) {
            Log.d(tag, value);
        }
    }
}
