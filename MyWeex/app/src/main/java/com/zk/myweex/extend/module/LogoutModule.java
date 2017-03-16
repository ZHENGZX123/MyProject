package com.zk.myweex.extend.module;

import android.content.Intent;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.activity.BootActivity;

/**
 * Created by Administrator on 2017/3/10.
 */

public class LogoutModule extends WXModule {

    @JSMethod(uiThread = true)
    public void logoutSuccess(JSCallback callback) {
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), BootActivity.class));
        callback.invoke("logoutSuccess is called");
    }


}
