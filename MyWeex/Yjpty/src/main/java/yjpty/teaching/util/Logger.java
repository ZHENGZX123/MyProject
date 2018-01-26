package yjpty.teaching.util;

import android.util.Log;

/**
 * 日志的打印
 * 
 * @author Zao
 * */
public class Logger {
	public static String TAG = "cn.kiway.Yjpty";
	public static final boolean log = true;

	public static void log(Object msg) {
		if (msg == null)
			return;
		if (log) {
			Log.e(TAG, msg.toString());
		}
	}
}