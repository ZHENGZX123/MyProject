package cn.kiway.homework.activity;

import android.os.Bundle;
import android.view.View;

import cn.kiway.homework.teacher.R;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void clickTest(View view) {
        //1、读取json文件，插入数据库

        //2.根据url读取数据库，简单测试。

        //3.生成json，要和服务器的一样


    }
}
