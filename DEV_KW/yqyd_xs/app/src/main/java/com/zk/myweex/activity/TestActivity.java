package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("index.weex.js", this), "", "");
    }

}
