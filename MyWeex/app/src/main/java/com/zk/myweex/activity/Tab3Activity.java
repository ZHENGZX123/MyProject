package com.zk.myweex.activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.zk.myweex.R;

public class Tab3Activity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getSupportActionBar().hide();

        load("tab3.zip");

//        renderPage(WXFileUtils.loadAsset("weex/tab3.js", this), "file://assets/");
//        RealmResults<HttpCache> caches = Realm.getDefaultInstance().where(HttpCache.class).findAll();
//        Log.d("test", "caches count = " + caches.size());
//        for (HttpCache c : caches) {
//            Log.d("test", c.toString());
//        }

    }
}
