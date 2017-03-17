package com.zk.myweex.activity;

import android.os.Bundle;

import com.taobao.weex.utils.WXFileUtils;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderPage(WXFileUtils.loadAsset("yjpt/weex_jzd/login.js", this), "file://assets/yjpt/weex_jzd/", null);

//        renderPage(WXFileUtils.loadAsset("weex/tab1.js", this), "file://assets/", null);
    }

    @Override
    public void onResume() {
        super.onResume();


//        HashMap aa = new HashMap();
//        aa.put("aa", "aaa");
//
//
//        HashMap map = new HashMap();
//        map.put("test1", aa);
//        map.put("test2", "300");


//        mInstance.fireSuperGlobalEventCallback("refresh" , map);

//        mInstance.fireGlobalEventCallback("refresh", map);


    }
}
