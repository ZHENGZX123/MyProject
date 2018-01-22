package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import studentsession.kiway.cn.mdm_studentsession.R;


/**
 * Created by Administrator on 2018/1/18.
 */
public class Course2Activity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("已上课程");
    }
}
