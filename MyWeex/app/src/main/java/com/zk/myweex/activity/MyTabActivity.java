package com.zk.myweex.activity;

import android.os.Bundle;

import cn.kiway.entity.TabEntity;
import io.realm.Realm;

/**
 * Created by Administrator on 2017/3/7.
 */

//动态tab使用
public class MyTabActivity extends SubActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getIntent().getIntExtra("position", 0);
        TabEntity tab = Realm.getDefaultInstance().where(TabEntity.class).findAll().get(position);
        load(tab.id + ".zip");
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        mInstance.fireGlobalEventCallback("refresh", new HashMap<String, Object>());
//    }
}
