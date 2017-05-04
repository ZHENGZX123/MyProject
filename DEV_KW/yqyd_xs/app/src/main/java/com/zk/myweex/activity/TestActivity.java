package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renderPage(WXFileUtils.loadAsset("login_backup.js", this), "", "");

//        renderPage(WXFileUtils.loadAsset("index.weex.js", this), "", "");

//        renderPage(WXFileUtils.loadAsset("yqyd/dist/tab0.js", this), "", "");

//        String path = WXApplication.PATH + "yqydTab0.zip/yqyd/dist/login.js";
//        renderPage(WXFileUtils.readFileInZip(path),
//                "file://" + WXApplication.PATH + "yqydTab0.zip/yqyd/dist/",
//                "yqydTab0.zip");
    }

}
