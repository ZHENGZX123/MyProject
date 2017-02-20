package com.zk.weexdemo;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.zk.weexdemo.util.WXImageAdapter;

/**
 * Created by Administrator on 2017/2/20.
 */

public class WXApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config = new InitConfig.Builder().setImgAdapter(new WXImageAdapter()).build();
        WXSDKEngine.initialize(this, config);
    }
}
