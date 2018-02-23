package cn.kiway.mdm.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;

import cn.kiway.mdm.activity.WhiteBoardActivity;
import cn.kiway.mdm.util.Utils;

/**
 * Created by Administrator on 2018/1/18.
 */

public class JsAndroidInterface2 {

    private WhiteBoardActivity activity;

    public JsAndroidInterface2(WhiteBoardActivity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public String setBackgroundImage() {
        Log.d("test", "setBackgroundImage is called");
        return "";
    }

    @JavascriptInterface
    public void saveBase64(String param) {
        Log.d("test", "saveBase64 is called , param = " + param);
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(param.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            String filename = System.currentTimeMillis() + ".png";
            Utils.saveBitmap(bitmap, filename);
            activity.toast("截图保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
