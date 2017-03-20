package com.zk.myweex.activity;

import android.content.Intent;
import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("yjpt/weex_jzd/login.js", this), "file://assets/yjpt/weex_jzd/", null);


        if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
            startActivity(new Intent(this, MainActivity2.class));
            finish();
        }
    }

}
