package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isfirst
        renderPage(WXFileUtils.readFileInZip("/mnt/sdcard/kiway/teacher/weex/tab0.zip/yjpt/weex_jzd/login.js"),
                "file:///mnt/sdcard/kiway/teacher/weex/tab0.zip/yjpt/weex_jzd/",
                "tab0.zip");
    }

}
