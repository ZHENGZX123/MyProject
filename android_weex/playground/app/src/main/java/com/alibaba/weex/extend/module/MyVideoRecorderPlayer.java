package com.alibaba.weex.extend.module;

import android.content.Intent;
import android.util.Log;

import com.alibaba.weex.VideoRecordActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


public class MyVideoRecorderPlayer extends WXModule {


    @JSMethod(uiThread = true)
    public void startRecord() {
        Log.d("test", "startRecord");


        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), VideoRecordActivity.class));

    }

    @JSMethod(uiThread = true)
    public void startPlay() {
        Log.d("test", "startPlay");
    }


}
