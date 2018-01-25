package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/15.
 */

public class UserInfoActivity extends BaseActivity {
    TextView account, userName,versionCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        initView1();
    }
    void initView1() {
        account = (TextView) findViewById(R.id.account);
        userName = (TextView) findViewById(R.id.userName);
        versionCode= (TextView) findViewById(R.id.versionCode);
        account.setText("学号：" + getSharedPreferences("kiwaykthd", 0).getString("studentNumber", ""));
        userName.setText("姓名:" + getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
        versionCode.setText(Utils.getCurrentVersion(this));
    }

    public void Before(View view) {
        finish();
    }
}
