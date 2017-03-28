package cn.kwim.mqttcilent.common.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/12.
 */

public class Utils {

    /**
     *  将此类（2017-01-10 11:36:01）时间格式转化为String
     * @param time
     * @return
     */
    public static String dateToLong(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(time).getTime()+"";

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 将long型转化为年月日 时分秒的形式
     * @return
     */
    public static String longToDate(String time){
        Long times = Long.parseLong(time);
        Date d = new Date(times);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);

    }

    /**
     * 获得Mac地址
     * @param context
     * @return
     */
    public static String getMacAddress(Context context){
        String result = "";
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        //Log.i(TAG, "macAdd:" + result);
        return result;
    }
    /**
     * 隐藏软键盘
     */
    public static void  hideSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
    }
    /**
     * 获得文件大小
     * @param path
     */
    public static long getFileSize(String path){
        File file = new File(path);
        if(file!=null){
            Log.i("获得文件大小",file.length()+"sss");
            return file.length();
        }
        return 0;

    }
}
