package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.zk.myweex.R;

public class Tab2Activity extends SubActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        load("tab2.zip");
        
//        renderPage(WXFileUtils.loadAsset("weex/mygridview.js", this), "file://assets/");
    }
}
