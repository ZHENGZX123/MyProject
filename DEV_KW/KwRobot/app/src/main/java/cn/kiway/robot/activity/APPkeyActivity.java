package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cn.kiway.robot.R;
import cn.kiway.robot.util.AESUtil;

import static cn.kiway.robot.util.Constant.PASSWORD;

/**
 * Created by Administrator on 2018/6/5.
 */

public class APPkeyActivity extends BaseActivity {

    private EditText appkeyET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appkey);
        appkeyET = (EditText) findViewById(R.id.appkeyET);
        String appkey = getSharedPreferences("appkey", 0).getString("appkey", "");
        if (!TextUtils.isEmpty(appkey)) {
            appkeyET.setText(AESUtil.decrypt(appkey, PASSWORD));
        }
    }

    public void validate(View view) {
        String appkey = appkeyET.getText().toString();
        if (TextUtils.isEmpty(appkey)) {
            toast("不能为空");
            return;
        }
        getSharedPreferences("appkey", 0).edit().putString("appkey", AESUtil.encrypt(appkey, PASSWORD)).commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
