package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //isfirst
        renderPage(WXFileUtils.readFileInZip("/mnt/sdcard/kiway/parent/weex/ParentTab0.zip/yjpt/weex_jzd/login.js"),
                "file:///mnt/sdcard/kiway/parent/weex/ParentTab0.zip/yjpt/weex_jzd/",
                "ParentTab0.zip");
    }

}
