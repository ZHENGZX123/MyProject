package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("yjpt/weex/login.js", this), "file://assets/");
//        renderPage(WXFileUtils.loadAsset("yjpt_py_0314/weex_jzd/login.js", this), "file://assets/");
    }
}
