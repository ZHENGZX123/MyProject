package com.zk.myweex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.kiway.yjpt.Parent.R;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity2.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                }

                finish();
            }
        }.start();
    }
}
