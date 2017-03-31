package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        renderPage(WXFileUtils.loadAsset("yjpt/weex_jzd/class-main-page.js", this), "", "tab0.zip");
        renderPage(WXFileUtils.readFileInZip("/mnt/sdcard/kiway/teacher/weex/testvue.zip/dist/index.weex.js"), "", "testvue.zip");
    }


}
