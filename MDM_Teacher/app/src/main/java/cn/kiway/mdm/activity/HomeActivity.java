package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.kiway.mdm.teacher.R;


//首页：点名、上课
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    public void dianming(View view) {
        //点名结束，跳到上课
        startActivity(new Intent(this, CourseListActivity.class));
    }

}
