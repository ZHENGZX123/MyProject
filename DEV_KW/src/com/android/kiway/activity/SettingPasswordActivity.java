package com.android.kiway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.launcher3.R;


/**
 * Created by Administrator on 2018/1/5.
 */

public class SettingPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_ps);
    }

    public void Before(View view) {
        finish();
    }

    public void NubLock(View view) {
        startActivity(new Intent(this, PassWordActivity.class));
    }

    public void Lock(View view) {
        startActivity(new Intent(this, LockActvitity.class).putExtra("isLock", false));
    }
}
