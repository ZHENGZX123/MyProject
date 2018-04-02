package cn.kiway.robot.guard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import cn.kiway.robot.guard.service.GuideService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(this, GuideService.class);
        startService(i);
    }


}