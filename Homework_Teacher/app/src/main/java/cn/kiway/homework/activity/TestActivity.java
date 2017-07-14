package cn.kiway.homework.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;

import cn.kiway.homework.entity.KV;
import cn.kiway.homework.teacher.R;
import cn.kiway.homework.util.MyDBHelper;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void clickTest(View view) {
        //插入N条数据
        new Thread() {
            @Override
            public void run() {
                Log.d("test", "start insert");
                long start = System.currentTimeMillis();
                int count = 1000 * 100;//
                for (int i = 0; i < count; i++) {
                    Log.d("test", "insert i = " + i);
                    KV a = new KV();
                    a.k = "getquestion?id=" + i;
                    a.v = "fdsjafjfdksafoweifehjriajkfjdfnsadjfjkssssssssssssssssssssssssssssssseeeeeeeeeeeeeeeeerwqerfasdf{}{}{}{}{}{}{}{}{}{fdsafsaf";
                    new MyDBHelper(TestActivity.this).addKV(a);
                }
                Log.d("test", "insert over , time = " + (System.currentTimeMillis() - start));
            }
        }.start();
    }


    public void clickTest2(View view) {
        Log.d("test", "start insert");
        long start = System.currentTimeMillis();
        int r = new Random().nextInt(1000 * 100);
        KV a = new MyDBHelper(this).getKVByK("getquestion?id=" + r);
        Log.d("test", "a == null " + (a == null));
        Log.d("test", "search over , time = " + (System.currentTimeMillis() - start));
    }

    //1、读取json文件，插入数据库。表设计1和席舟一样  2KV型表
    //2.根据url读取数据库，简单测试。
    //3.生成json，要和服务器的一样
    //测试接口，1获取书列表；2根据书id获取课时列表；3根据课时id获取问题列表；
}

