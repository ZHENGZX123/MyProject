package cn.kiway.homework.activity;

import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Random;

import cn.kiway.homework.entity.KV;
import cn.kiway.homework.teacher.R;
import cn.kiway.homework.util.FileUtils;
import cn.kiway.homework.util.MLog;
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
                MLog.d("test", "start insert");
                long start = System.currentTimeMillis();
                int count = 1000;//
                for (int i = 0; i < count; i++) {
                    MLog.d("test", "insert i = " + i);
                    KV a = new KV();
                    a.k = "getquestion?id=" + i;
                    a.v = "fdsjafjfdksafoweifehjriajkfjdfnsadjfjkssssssssssssssssssssssssssssssseeeeeeeeeeeeeeeeerwqerfasdf{}{}{}{}{}{}{}{}{}{fdsafsaf";
                    new MyDBHelper(TestActivity.this).addKV(a);
                }
                MLog.d("test", "insert over , time = " + (System.currentTimeMillis() - start));
            }
        }.start();
    }


    public void clickTest2(View view) {
        MLog.d("test", "start insert");
        long start = System.currentTimeMillis();
        int r = new Random().nextInt(1000);
        KV a = new MyDBHelper(this).getKVByK("getquestion?id=" + r);
        MLog.d("test", "a == null " + (a == null));
        MLog.d("test", "search over , time = " + (System.currentTimeMillis() - start));
    }

    //1、读取json文件，插入数据库。表设计1和席舟一样  2KV型表
    //2.根据url读取数据库，简单测试。
    //3.生成json，要和服务器的一样
    //测试接口，1获取书列表；2根据书id获取课时列表；3根据课时id获取问题列表；

    public void clickTest3(View view) throws Exception {


        String filepath = "/mnt/sdcard/books/1/data.json";
        String content = FileUtils.readSDCardFile(filepath, this);
        MLog.d("test", "content = " + content);

        JSONObject all = new JSONObject(content);
        Iterator<String> keys = all.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = all.getString(key);
            MLog.d("test", "key = " + key);
            MLog.d("test", "value = " + value);

            KV a = new KV();
            a.k = key;
            a.v = value;
            new MyDBHelper(this).addKV(a);
        }
        MLog.d("test", "插入sql完毕");
    }
}

