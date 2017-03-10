package com.alibaba.weex.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class JavaScriptObject {
    private Context mContxt;

    public JavaScriptObject(Context mContxt) {
        this.mContxt = mContxt;
    }

    @JavascriptInterface
    public void toast(String txt) {
        Toast.makeText(mContxt, txt, Toast.LENGTH_SHORT).show();
    }

}
