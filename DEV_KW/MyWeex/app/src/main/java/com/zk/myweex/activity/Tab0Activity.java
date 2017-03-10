package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.zk.myweex.R;

public class Tab0Activity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//      renderPage(WXFileUtils.loadAsset("yjpt_py/weex/login.js", this), "file://assets/");
//        renderPage(WXFileUtils.loadAsset("yjpt/weex_jzd/index.js", this), "file://assets/");

        load("tab0.zip");
//        Log.d("test", "tab0 = " + mInstance.getInstanceId());
    }
}