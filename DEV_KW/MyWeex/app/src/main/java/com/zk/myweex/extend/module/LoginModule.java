package com.zk.myweex.extend.module;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.zk.myweex.activity.MainActivity;

/**
 * Created by Administrator on 2017/3/10.
 */

public class LoginModule extends WXModule {

    @JSMethod(uiThread = true)
    public void loginSuccess(JSCallback callback) {
        mWXSDKInstance.getContext().startActivity(new Intent(mWXSDKInstance.getContext(), MainActivity.class));
        ((Activity) mWXSDKInstance.getContext()).finish();
        callback.invoke("loginSuccess is called");
    }

    @JSMethod(uiThread = true)
    public void loginFailure() {
        Toast.makeText(mWXSDKInstance.getContext(), "登录失败", Toast.LENGTH_SHORT).show();
    }
}
