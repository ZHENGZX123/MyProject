package cn.kiway.klzy.webuitl;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2017/5/27.
 * js接口
 */

public class JsAndroidIntercrpt {
    Context context;

    public JsAndroidIntercrpt(Context context) {
        this.context = context;
    }
    /**
     * @param screen 1竖屏 2横屏
     * */
    @JavascriptInterface
    public void setRequestedOrientation(String screen) {
        if (Integer.parseInt(screen) == 1) {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
