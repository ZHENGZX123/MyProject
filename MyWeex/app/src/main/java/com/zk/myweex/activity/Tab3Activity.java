package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.R;
import com.zk.myweex.entity.HttpCache;

import io.realm.Realm;
import io.realm.RealmResults;

public class Tab3Activity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        renderPage(WXFileUtils.loadAsset("weex/tab3.js", this), "file://assets/");

//        load("tab3.zip");


        RealmResults<HttpCache> caches = Realm.getDefaultInstance().where(HttpCache.class).findAll();
        Log.d("test", "caches count = " + caches.size());
        for (HttpCache c : caches) {
            Log.d("test", c.toString());
        }

    }
}
