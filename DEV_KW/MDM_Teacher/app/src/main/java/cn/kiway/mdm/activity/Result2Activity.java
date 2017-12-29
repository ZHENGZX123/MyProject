package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.kiway.mdm.teacher.R;


/**
 * Created by Administrator on 2017/12/29.
 */

//班级统计
public class Result2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("班级统计结果");

    }

    private void initData() {
    }


}
