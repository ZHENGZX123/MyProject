package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Course2Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course2);


    }

    public void testVideo(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
