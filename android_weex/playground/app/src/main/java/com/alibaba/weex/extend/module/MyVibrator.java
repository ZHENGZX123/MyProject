package com.alibaba.weex.extend.module;

import android.content.Context;
import android.os.Vibrator;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

public class MyVibrator extends WXModule {

    @JSMethod(uiThread = true)
    public void doVibrate() {
        Vibrator vibrator = (Vibrator) mWXSDKInstance.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
    }
}
