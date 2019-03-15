package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kiway.robot.R;

/**
 * Created by Administrator on 2018/8/14.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class).putExtra("newLogin", true));
            finish();
        }
    }

    public void clickStart(View view) {
        startActivity(new Intent(this, Guide1Activity.class));
        finish();
    }
}
