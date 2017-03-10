package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        renderPage(WXFileUtils.loadAsset("yjpt/weex/login.js", this), "file://assets/");
    }
}
