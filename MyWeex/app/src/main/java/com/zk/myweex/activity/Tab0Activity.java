package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;

public class Tab0Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab0);

        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();


        renderPage(WXFileUtils.loadAsset("yjpt/foo.weex.js", this), "file://assets/");

//        String path = WXApplication.PATH + "tab0.zip";
//        ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
//        if (new File(path).exists() && zip != null) {
//            Log.d("test", "存在，直接加载");
//            loadJSBundle(zipName);
//        } else {
//            Log.d("test", "不存在，下载");
//            downloadJSBundle(zipName);
//        }

    }

}
