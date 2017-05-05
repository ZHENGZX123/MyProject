package com.alibaba.weex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wjc.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void click1(View view) {
        startActivity(new Intent(SplashActivity.this, IndexActivity.class));
    }

    public void click2(View view) {
        startActivity(new Intent(SplashActivity.this, CustomActivity.class));
    }

}
