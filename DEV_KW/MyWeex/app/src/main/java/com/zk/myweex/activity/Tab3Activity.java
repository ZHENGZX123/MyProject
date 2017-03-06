package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class Tab3Activity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        renderPage(WXFileUtils.loadAsset("yjpt/tab3.js", this), "file://assets/");

//        load("tab3.zip");
    }
}
