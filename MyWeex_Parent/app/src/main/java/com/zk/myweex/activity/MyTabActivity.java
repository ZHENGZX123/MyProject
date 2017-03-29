package com.zk.myweex.activity;

import android.os.Bundle;

import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.MyDBHelper;

/**
 * Created by Administrator on 2017/3/7.
 */

//动态tab使用
public class MyTabActivity extends SubActivity {

    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread() {
            @Override
            public void run() {
                int position = getIntent().getIntExtra("position", 0);
                TabEntity tab = new MyDBHelper(getApplicationContext()).getAllTabEntity().get(position);
                try {
                    load(tab.idStr + ".zip");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

}
