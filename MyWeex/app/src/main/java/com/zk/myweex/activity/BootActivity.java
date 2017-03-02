package com.zk.myweex.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zk.myweex.R;
import com.zk.myweex.mqttclient.MqttInstance;


public class BootActivity extends Activity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_base);

        MqttInstance.getInstance().conMqtt("13510530146", "123456", null);

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(BootActivity.this, MainActivity.class));
                finish();
            }
        }.start();

    }
}
