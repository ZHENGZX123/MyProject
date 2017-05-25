package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;

public class TestActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("tab1.js", this), "", "");

//        renderPage(WXFileUtils.loadAsset("index.weex.js", this), "", "");
//        renderPage(WXFileUtils.loadAsset("yqyd/dist/tab0.js", this), "", "");
//        String path = WXApplication.PATH + "yqydTab0.zip/yqyd/dist/login.js";
//        renderPage(WXFileUtils.readFileInZip(path),
//                "file://" + WXApplication.PATH + "yqydTab0.zip/yqyd/dist/",
//                "yqydTab0.zip");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mInstance.fireGlobalEventCallback("clickback", new HashMap());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
