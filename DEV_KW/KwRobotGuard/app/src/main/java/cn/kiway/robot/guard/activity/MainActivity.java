package cn.kiway.robot.guard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.kiway.robot.guard.R;
import cn.kiway.robot.guard.service.GuideService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, GuideService.class);
        startService(i);
    }

    public void clickWechat(View v) {

    }

    public void clickRobot(View v) {

    }

    public void clickSettings(View v) {

    }

    @Override
    public void onBackPressed() {
    }
}
