package cn.kiway.autoreply.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import cn.kiway.autoreply.KWApplication;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Utils {

    public static String getIMEI(Context c) {
        String imei = FileUtils.readSDCardFile(KWApplication.ROOT + "/imei.txt", c);
        if (TextUtils.isEmpty(imei)) {
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
            if (TextUtils.isEmpty(imei)) {
                Log.d("test", "这个IMEI是生成的");
                imei = genIMEI();
            }
            FileUtils.saveFile(imei);
        }
        Log.d("test", "IMEI = " + imei);
        return imei;
    }

    public static String genIMEI() {
        int r1 = 1000000 + new java.util.Random().nextInt(9000000);
        int r2 = 1000000 + new java.util.Random().nextInt(9000000);
        String input = r1 + "" + r2;
        char[] ch = input.toCharArray();
        int a = 0, b = 0;
        for (int i = 0; i < ch.length; i++) {
            int tt = Integer.parseInt(ch[i] + "");
            if (i % 2 == 0) {
                a = a + tt;
            } else {
                int temp = tt * 2;
                b = b + temp / 10 + temp % 10;
            }
        }
        int last = (a + b) % 10;
        if (last == 0) {
            last = 0;
        } else {
            last = 10 - last;
        }
        return input + last;
    }
    public static boolean isForwrad(int msgType){
        if (msgType == 3//图片
                || msgType == 34//语音
                || msgType == 42//名片
                || msgType == 43//视频
                || msgType == 49) {//图文链接
            return true;
        }
        return false;
    }
}
