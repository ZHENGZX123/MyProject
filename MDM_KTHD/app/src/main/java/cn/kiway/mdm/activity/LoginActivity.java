package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/25.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void clickLogin(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
