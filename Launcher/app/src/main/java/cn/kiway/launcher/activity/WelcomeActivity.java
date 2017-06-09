package cn.kiway.launcher.activity;

import android.content.Intent;
import android.os.Bundle;

import cn.kiway.launcher.R;


//欢迎页面不要了，有bug的
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void jump() {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }


}
