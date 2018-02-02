package cn.kiway.homework.util;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MLog {

    private static final boolean DEBUG = false;

    public static void d(String tag, String value) {
        if (DEBUG) {
            Log.d(tag, value);
        }
    }
}
