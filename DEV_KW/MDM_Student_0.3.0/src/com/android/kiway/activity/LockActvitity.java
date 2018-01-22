package com.android.kiway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amnix.materiallockview.MaterialLockView;
import com.android.kiway.utils.DES;
import com.android.kiway.utils.HttpUtil;
import com.android.kiway.utils.Logger;
import com.android.launcher3.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 */

public class LockActvitity extends BaseActivity {
    boolean isLock;//解锁还是初始化密码
    private String firstPattern = "";
    private MaterialLockView materialLockView;
    private boolean isFirst = true;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        isLock = getIntent().getBooleanExtra("isLock", false);
        title = (TextView) findViewById(R.id.title);
        if (isLock) {
            title.setText("绘制图案解锁");
        } else {
            isFirst=true;
            title.setText("请绘制密码");
        }
        initView();
    }

    public void Before(View view) {
        finish();
    }

    void initView() {
        materialLockView = (MaterialLockView) findViewById(R.id.pattern);

        materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
            @Override
            public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                if (isLock) {//解锁
                    Logger.log("::::"+SimplePattern);
                    Logger.log("::::"+new DES().decrypt(getSharedPreferences("kiway", 0).getString
                            ("lockPattern", "")));
                    if (!SimplePattern.equals(new DES().decrypt(getSharedPreferences("kiway", 0).getString
                            ("lockPattern", "")))) {
                        materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                        title.setText("密码错误，请重新输入");
                    } else {
                        materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Correct);
                        startActivity(new Intent(LockActvitity.this, SettingActivity.class));
                        finish();
                    }
                } else {//初始化密码
                    if (isFirst) {
                        title.setText("请确定密码");
                        firstPattern = SimplePattern;
                        isFirst = false;
                    } else {
                        if (SimplePattern.equals(firstPattern)) {
                            //两次密码输入一致
                            materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                            materialLockView.clearPattern();
                            //1.存到本地
                            getSharedPreferences("kiway", 0).edit().putString("lockPattern", new DES().encrypt
                                    (SimplePattern))
                                    .commit();
                            getSharedPreferences("kiway", 0).edit().putBoolean("isLock", true).commit();
                            //2.存到中心
                            HttpUtil.updateDefaultPwd(LockActvitity.this, SimplePattern);
                            Toast.makeText(LockActvitity.this,"设置成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            isFirst = true;
                            title.setText("请重新绘制密码");
                            materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                        }
                    }
                }
                materialLockView.clearPattern();
                super.onPatternDetected(pattern, SimplePattern);
            }
        });
    }
}
