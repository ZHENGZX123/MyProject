package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.WXApplication;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String loginPath = WXFileUtils.findLoginJS(WXApplication.PATH);
        Log.d("test", "loginPath = " + loginPath);
        renderPage(WXFileUtils.readFileInZip(loginPath),
                "file://" + loginPath.replace("login.js", ""),
                "tab0.zip");
    }

}
