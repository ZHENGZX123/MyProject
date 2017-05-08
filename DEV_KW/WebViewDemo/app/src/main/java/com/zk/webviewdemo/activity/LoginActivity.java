package com.zk.webviewdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.zk.webviewdemo.R;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLogin(View v) {
        startActivity(new Intent(this, WebViewActivity2.class).putExtra("token", "123456"));
        finish();
    }
}
