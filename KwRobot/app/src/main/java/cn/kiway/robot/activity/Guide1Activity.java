package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kiway.robot.R;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Guide1Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);
    }

    public void clickStart(View view) {
        if (isServiceEnabled()) {
            toast("服务已开启");
            startActivity(new Intent(Guide1Activity.this, Guide2Activity.class));
            finish();
        } else {
            doStartService();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isServiceEnabled()) {
                    toast("服务已开启");
                    startActivity(new Intent(Guide1Activity.this, Guide2Activity.class));
                    finish();
                } else {
                    toast("服务未开启");
                }
            }
        }, 1000);
    }
}
