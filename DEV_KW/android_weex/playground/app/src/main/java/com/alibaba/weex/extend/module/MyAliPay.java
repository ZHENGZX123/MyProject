package com.alibaba.weex.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

public class MyAliPay extends WXModule {

    @JSMethod(uiThread = true)
    public void dopay(String money, String content, JSCallback callback) {
        Log.d("test", "money = " + money);
        callback.invoke("没做，需要后台配合");
        //1.下单
        //2.支付
        //3.支付回调
    }
    
}
