package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;

import com.taobao.weex.utils.WXFileUtils;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("weex/tab1.js", this), "file://assets/", null);

//        renderPage(WXFileUtils.loadAsset("yjpt_py_0314/weex_jzd/login.js", this), "file://assets/");
        Log.d("test", "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("test", "onresume");

        mInstance.fireGlobalEventCallback("refresh", null);
    }
}
