package cn.kiway.baoantong.extend.module;

import android.app.Activity;
import android.view.View;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import cn.kiway.baoantong.WXApplication;
import cn.kiway.baoantong.activity.MainActivity;


public class SJEventModule extends WXModule {

    @JSMethod(uiThread = true)
    public void ShowTabbar() {
        try {
            MainActivity.main.bottom.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void HideTabbar() {
        try {
            MainActivity.main.bottom.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void finish() {
        long current = System.currentTimeMillis();
        if (WXApplication.time == 0) {
            WXApplication.time = current;
            toast("再按一次返回退出APP");
            return;
        }
        if ((current - WXApplication.time) < 2000) {
            WXApplication.time = 0;
            ((Activity) mWXSDKInstance.getContext()).finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            toast("再按一次返回退出APP");
            WXApplication.time = current;
        }
    }

}

