package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2018/1/17.
 */
//班级测评统计
public class TongjiActivity2 extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji2);
        initView();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("知识点统计结果");
    }
}
