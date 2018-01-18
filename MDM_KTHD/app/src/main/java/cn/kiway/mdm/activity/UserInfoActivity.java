package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/15.
 */

public class UserInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("用户信息");
    }
}
