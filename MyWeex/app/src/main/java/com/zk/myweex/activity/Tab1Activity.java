package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class Tab1Activity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        renderPage(WXFileUtils.loadAsset("weex/tab1.js", this), "file://assets/");

//        Log.d("test", "tab1 = " + mInstance.getInstanceId());

        load("tab1.zip");
    }


}
