package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.WXApplication;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        renderPage(WXFileUtils.loadAsset("yqyd/dist/tab0.js", this), "", "");

        String path = WXApplication.PATH + "yqydTab0.zip/yqyd/dist/tab0.js";
        renderPage(WXFileUtils.readFileInZip(path),
                "file://" + WXApplication.PATH + "yqydTab0.zip/yqyd/dist/",
                "yqydTab0.zip");
    }

}
