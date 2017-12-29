package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.kiway.mdm.teacher.R;


/**
 * Created by Administrator on 2017/12/29.
 */

public class WhiteBoardActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiteboard);

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("画笔");
    }
}
