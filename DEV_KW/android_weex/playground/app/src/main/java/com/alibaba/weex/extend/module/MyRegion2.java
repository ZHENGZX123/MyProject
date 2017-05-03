package com.alibaba.weex.extend.module;

import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.xiong.datepicker.utils.ChooseCityInterface;
import com.xiong.datepicker.utils.ChooseCityUtil;

public class MyRegion2 extends WXModule {
    private JSCallback callback;

    @JSMethod(uiThread = true)
    public void selectRegion(JSCallback callback) {
        this.callback = callback;

        final ChooseCityUtil cityUtil = new ChooseCityUtil();
        String[] oldCityArray = {"广东", "深圳", "南山"};
        cityUtil.createDialog(mWXSDKInstance.getContext(), oldCityArray, new ChooseCityInterface() {
            @Override
            public void sure(String[] newCityArray) {
                Log.d("test", "" + newCityArray[0] + "-" + newCityArray[1] + "-" + newCityArray[2]);
            }
        });
    }
}
