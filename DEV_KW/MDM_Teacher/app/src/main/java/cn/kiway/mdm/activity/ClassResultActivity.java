package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2018/1/17.
 */

public class ClassResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_result);
        initView();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("班级统计");
    }
}
