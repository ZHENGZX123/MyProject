package cn.kiway.autoreply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kiway.autoreply.R;

/**
 * Created by Administrator on 2018/3/23.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {


        startActivity(new Intent(this, MainActivity.class));
    }
}
