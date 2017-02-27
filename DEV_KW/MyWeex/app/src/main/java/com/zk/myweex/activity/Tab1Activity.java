package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class Tab1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        renderPage(WXFileUtils.loadAsset("yjpt/tab1.js", this), "file://assets/");

    }
}
