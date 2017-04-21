package com.alibaba.weex.extend.module;

import android.content.Intent;
import android.util.Log;

import com.alibaba.weex.PlayVideoActivity;
import com.alibaba.weex.VideoRecordActivity2;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


public class MyVideoRecorderPlayer extends WXModule {


    @JSMethod(uiThread = true)
    public void startRecord() {
        Log.d("test", "startRecord");
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), VideoRecordActivity2.class));
    }

    @JSMethod(uiThread = true)
    public void startPlay() {
        Log.d("test", "startPlay");

        String url = "/mnt/sdcard/huanxin.mp4";
        Intent i = new Intent(mWXSDKInstance.getContext(), PlayVideoActivity.class);
        i.putExtra("url", url);
        i.putExtra("position", 0);
        mWXSDKInstance.getContext().startActivity(i);
    }


}
