package com.alibaba.weex;

import android.os.Bundle;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.wjc.R;

public class TestActivity2 extends AbstractWeexActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        renderPageByURL(getIntent().getStringExtra("url"));
    }
}
