package com.alibaba.weex.extend.module;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;


public class MyOpenURL extends WXModule {

    private static final String WEEX_CATEGORY = "com.taobao.android.intent.category.WEEX";
    private static final String WEEX_ACTION = "com.taobao.android.intent.action.WEEX";


    @JSMethod(uiThread = true)
    public void openURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        mWXSDKInstance.getContext().startActivity(intent);
    }
}
