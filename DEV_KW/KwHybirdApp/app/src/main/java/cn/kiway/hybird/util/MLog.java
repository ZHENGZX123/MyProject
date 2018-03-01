package cn.kiway.hybird.util;

import android.util.Log;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MLog {
    public static void d(String tag, String value) {
        if (Configue.DEBUG) {
            Log.d(tag, value);
        }
    }
}
