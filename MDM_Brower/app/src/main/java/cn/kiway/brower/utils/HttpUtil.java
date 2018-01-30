package cn.kiway.brower.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;


/**
 * Created by Administrator on 2018/1/10.
 */

public class HttpUtil {
    public static final String clientUrl = "http://192.168.8.161:8085/";

    public static void childOperation(final Activity c, String type, String message) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("IMEI", getIMEI(c));
            param.put("type", type);
            param.put("message", message);
            param.put("froms", "studentDevice");
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "device/parent/child/operation";
            Log.d("test", "childOperation = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "childOperation onSuccess = " + ret);

                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "childOperation onFailure = " + s);

                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }
    public static String getIMEI(Context c) {
        String imei = FileUtils.readSDCardFile("/mnt/sdcard/kiway_mdm_student/imei.txt", c);
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

}
