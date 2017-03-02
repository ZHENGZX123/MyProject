package com.zk.myweex.extend.module;

import android.util.Log;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.utils.NetworkUtil;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MyHttp extends WXModule {

    @JSMethod(uiThread = true)
    public void doGet(String url, String param, JSCallback callback) {
        try {
            if (!NetworkUtil.isNetworkAvailable(mWXSDKInstance.getContext())) {
                callback.invoke("error");
                return;
            }
            //访问网络，缓存返回值，返回字符串
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
        }
    }


    public void doPost(String url, String param, JSCallback callback) {

    }


    private void toast(String txt) {
        Toast.makeText(mWXSDKInstance.getContext(), txt, Toast.LENGTH_SHORT).show();
    }

}
