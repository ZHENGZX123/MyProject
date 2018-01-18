package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import studentsession.kiway.cn.mdm_studentsession.R;


/**
 * Created by Administrator on 2018/1/18.
 */
public class FenxiActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxi);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("课堂分析");
    }
}
