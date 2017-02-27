package com.zk.myweex.extend.module;

import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;


public class MyTab2 extends WXModule {


    @JSMethod(uiThread = true)
    public void loadjs(String jsName, JSCallback callback) {

        Toast.makeText(mWXSDKInstance.getContext(), " load js :" + jsName, Toast.LENGTH_SHORT).show();
    }


}

