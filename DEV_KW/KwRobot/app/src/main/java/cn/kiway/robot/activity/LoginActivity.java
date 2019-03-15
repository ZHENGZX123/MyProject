package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.kiway.robot.R;
import cn.kiway.robot.util.MyListener;
import cn.kiway.robot.util.Utils;

/**
 * Created by Administrator on 2018/3/23.
 */

public class LoginActivity extends BaseActivity {


    private EditText usernameET;
    private EditText passwordET;
    private EditText nameET;
    private EditText wxNoET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        nameET = (EditText) findViewById(R.id.name);
        wxNoET = (EditText) findViewById(R.id.wxNo);

        usernameET.setText(getSharedPreferences("kiway", 0).getString("username", ""));
        passwordET.setText(getSharedPreferences("kiway", 0).getString("password", ""));
        wxNoET.setText(getSharedPreferences("kiway", 0).getString("wxNo", ""));
        nameET.setText(getSharedPreferences("kiway", 0).getString("name", ""));

        if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void login(View view) {
        //1.客户端校验
        final String username = usernameET.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            toast("请填写帐号");
            return;
        }
        final String password = passwordET.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toast("请填写密码");
            return;
        }
        final String name = nameET.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请填写微信昵称");
            return;
        }
        final String wxNo = wxNoET.getText().toString();
        if (TextUtils.isEmpty(wxNo)) {
            toast("请填写微信号");
            return;
        }
        //2.提交数据
        showPD();
        Utils.doLogin(this, username, password, name, wxNo, new MyListener() {
            @Override
            public void onResult(boolean success) {
                dismissPD();
                if (success) {
                    toast("登录成功");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    toast("登录失败");
                }
            }
        });
    }
}
