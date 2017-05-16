package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.taobao.weex.utils.WXFileUtils;
import com.zk.myweex.WXApplication;

import java.util.HashMap;

public class LoginActivity extends WXBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String loginPath = WXFileUtils.findLoginJS(WXApplication.PATH);
        Log.d("test", "loginPath = " + loginPath);
        renderPage(WXFileUtils.readFileInZip(loginPath),
                "file://" + loginPath.replace("login.js", ""),
                "yqydTab0.zip");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mInstance.fireGlobalEventCallback("clickback", new HashMap());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
