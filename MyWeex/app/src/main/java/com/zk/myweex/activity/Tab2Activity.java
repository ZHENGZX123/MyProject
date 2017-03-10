package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.zk.myweex.R;

public class Tab2Activity extends SubActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        load("tab2.zip");
        
//        renderPage(WXFileUtils.loadAsset("weex/mygridview.js", this), "file://assets/");
    }
}
