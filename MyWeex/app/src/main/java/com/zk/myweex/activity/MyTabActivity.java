package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2017/3/7.
 */

//动态tab使用
public class MyTabActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = getIntent().getIntExtra("position", 0);
        Log.d("test", "position = " + position);
        load("tab" + position + ".zip");
    }

}
