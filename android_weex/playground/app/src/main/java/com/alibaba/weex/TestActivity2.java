package com.alibaba.weex;

import android.os.Bundle;
import android.view.ViewGroup;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.wjc.R;

public class TestActivity2 extends AbstractWeexActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        getSupportActionBar().hide();
        setContainer((ViewGroup) findViewById(R.id.index_container));
        renderPageByURL(getIntent().getStringExtra("url"));
    }
}
