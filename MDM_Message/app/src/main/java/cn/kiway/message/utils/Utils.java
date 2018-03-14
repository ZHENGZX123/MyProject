package cn.kiway.message.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static cn.kiway.message.utils.Constant.clientUrl;

/**
 * Created by Administrator on 2018/3/13.
 */

public class Utils {
    private static boolean is301 = false;

    public static String longToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date(Long.parseLong(time));
        String str = sdf.format(date);
        return str;
    }

    public static boolean check301(final Activity c, String result) {
        if (c == null) {
            return false;
        }
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        try {
            int statusCode = new JSONObject(result).optInt("statusCode");
            if (statusCode != 301) {
                return false;
            }
            Log.d("test", "301 happen");
            if (is301) {
                return true;
            }
            is301 = true;
            //TODO 重新登录后没有再次请求上一个接口。。。
            final String imei = Utils.getIMEI(c);
            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = clientUrl + "device/student/login";
            Log.d("test", "relogin url = " + url);
            RequestParams param = new RequestParams();
            param.put("schoolId", c.getSharedPreferences("kiway", 0).getString("schoolId", ""));
            param.put("classId", c.getSharedPreferences("kiway", 0).getString("classId", ""));
            param.put("studentNumber", c.getSharedPreferences("kiway", 0).getString("studentNumber", ""));
            param.put("name", c.getSharedPreferences("kiway", 0).getString("name", ""));
            param.put("mobileModel", Build.MODEL);
            param.put("mobileBrand", Build.BRAND);
            param.put("IMEI", imei);
            param.put("platform", "Android");
            param.put("token", token);
            param.put("operation", "login");
            param.put("froms", "studentDevice");
            Log.d("test", "relogin param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "relogin  onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        String token = o.getJSONObject("data").getString("token");
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                    } catch (Exception e) {
                    }
                    is301 = false;
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "relogin  failure");
                    is301 = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "relogin exception = " + e.toString());
            is301 = false;
        }

        return true;
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
