package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.BaseActivity;

public class GroupOtherActivity extends BaseActivity implements View.OnClickListener {

    private ListView list;
    private  ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_group_other);
        initView();
        setData();
    }
    public void setData() {
        GroupOtherAdapter adapter = new GroupOtherAdapter(GroupOtherActivity.this);
        list.setAdapter(adapter);
    }

    public void initView() {
        list = (ListView)findViewById(R.id.list);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            default:
                break;
        }

    }
}
