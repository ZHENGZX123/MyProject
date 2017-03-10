package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.zk.myweex.R;

public class Tab1Activity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



//        renderPage(WXFileUtils.loadAsset("weex/tab1.js", this), "file://assets/");

        load("tab1.zip");
        Log.d("test", "tab1 = " + mInstance.getInstanceId());
    }


}
