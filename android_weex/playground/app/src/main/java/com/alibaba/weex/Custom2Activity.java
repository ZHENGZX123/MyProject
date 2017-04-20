package com.alibaba.weex;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.taobao.weex.utils.WXFileUtils;
import com.wjc.R;

public class Custom2Activity extends AbstractWeexActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom2);
        setContainer((ViewGroup) findViewById(R.id.index_container));

        String name = getIntent().getStringExtra("file");

        renderPage(WXFileUtils.loadAsset("kiway/" + name + ".js", this), "file://assets/");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "custom2 onActivityResult");
        mInstance.onActivityResult(requestCode, resultCode, data);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("test", "onConfigurationChanged .......");
        super.onConfigurationChanged(newConfig);
        if (mInstance != null) {
            mInstance.onConfigurationChanged(newConfig);
        }
    }

}
