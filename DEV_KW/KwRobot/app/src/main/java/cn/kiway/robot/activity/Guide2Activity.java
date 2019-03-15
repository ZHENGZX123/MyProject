package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kiway.robot.R;

import static cn.kiway.robot.util.RootCmd.haveRoot;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Guide2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);
    }

    public void clickStart(View view) {
        new Thread() {
            @Override
            public void run() {
                boolean have = haveRoot();
                if (have) {
                    toast("已经拥有Root权限");
                    startActivity(new Intent(Guide2Activity.this, Guide3Activity.class));
                    finish();
                } else {
                    toast("尚未拥有Root权限");
                }
            }
        }.start();
    }
}
