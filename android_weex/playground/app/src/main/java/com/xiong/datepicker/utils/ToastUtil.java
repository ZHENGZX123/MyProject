package com.xiong.datepicker.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.taobao.weex.WXEnvironment;


public class ToastUtil {

    public static void show(Object obj) {
        if (obj != null) {
            Toast toast = Toast.makeText(WXEnvironment.sApplication, obj.toString(), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(WXEnvironment.sApplication, "null 空字符", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

}
