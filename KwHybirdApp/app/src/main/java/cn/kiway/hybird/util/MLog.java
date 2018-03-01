package cn.kiway.hybird.util;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MLog {

    private static final boolean DEBUG = true;

    public static void d(String tag, String value) {
        if (DEBUG) {
            Log.d(tag, value);
        }
    }
}
