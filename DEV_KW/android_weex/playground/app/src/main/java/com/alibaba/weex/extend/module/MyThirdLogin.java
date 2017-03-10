package com.alibaba.weex.extend.module;

import android.content.Intent;
import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class MyThirdLogin extends WXModule implements PlatformActionListener {

    @JSMethod(uiThread = true)
    public void doThirdLogin(JSCallback callback) {
        ShareSDK.initSDK(mWXSDKInstance.getContext());
        Platform platform = ShareSDK.getPlatform(Wechat.NAME);
        authorize(platform, false);
    }


    private void authorize(Platform plat, boolean closeSSO) {
        plat.setPlatformActionListener(this);
        // 关闭SSO授权
        plat.SSOSetting(closeSSO);
        plat.showUser(null);
    }

    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
        Log.d("test", "onComplete");
    }

    public void onError(Platform platform, int action, Throwable t) {
        Log.d("test", "onError");
    }

    public void onCancel(Platform platform, int action) {
        Log.d("test", "onCancel");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "mythirdlogin mythirdlogin");
    }
}
