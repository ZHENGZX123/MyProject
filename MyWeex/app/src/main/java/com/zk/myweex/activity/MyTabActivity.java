package com.zk.myweex.activity;

import android.os.Bundle;

import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.MyDBHelper;

/**
 * Created by Administrator on 2017/3/7.
 */

//动态tab使用
public class MyTabActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position", 0);
        TabEntity tab = new MyDBHelper(getApplicationContext()).getAllTabEntity().get(position);
        load(tab.idStr + ".zip");
    }

}
