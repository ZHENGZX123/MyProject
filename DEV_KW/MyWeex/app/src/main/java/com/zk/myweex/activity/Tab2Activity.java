package com.zk.myweex.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class Tab2Activity extends BaseActivity {

    private int lastProgress;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();
        pd = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);

        renderPage(WXFileUtils.loadAsset("yjpt/tab2.js", this), "file://assets/");


    }


}
