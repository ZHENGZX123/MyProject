package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.zk.myweex.R;

/**
 * Created by Administrator on 2017/3/7.
 */

public class MyTabActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        int position = getIntent().getIntExtra("position", 0);
        Log.d("test", "position = " + position);
        load("tab" + position + ".zip");
    }

}
