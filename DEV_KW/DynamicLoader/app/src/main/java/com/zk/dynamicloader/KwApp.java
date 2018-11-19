package com.zk.dynamicloader;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2018/11/19.
 */

public class KwApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
