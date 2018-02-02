package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2017/12/28.
 */
//这个listview暂时没写。
public class Course2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        videoBtn.setVisibility(View.VISIBLE);
    }

    public void clickVideo(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
