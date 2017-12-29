package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import cn.kiway.mdm.teacher.R;


/**
 * Created by Administrator on 2017/12/29.
 */

public class ResultActivity extends BaseActivity {

    private int type;
    private RelativeLayout type12RL;
    private RelativeLayout type3RL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        type = getIntent().getIntExtra("type", 1);

        initView();
    }

    @Override
    public void initView() {
        super.initView();

        type12RL = (RelativeLayout) findViewById(R.id.type12RL);
        type3RL = (RelativeLayout) findViewById(R.id.type3RL);

        if (type == 1) {
            titleName.setText("抢答结果");
            type12RL.setVisibility(View.VISIBLE);
        } else if (type == 2) {
            titleName.setText("抽答结果");
            type12RL.setVisibility(View.VISIBLE);
        } else if (type == 3) {
            titleName.setText("测评结果");
            type12RL.setVisibility(View.GONE);
        }
    }
}
