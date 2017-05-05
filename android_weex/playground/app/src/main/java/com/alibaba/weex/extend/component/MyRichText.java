package com.alibaba.weex.extend.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXFileUtils;

import java.net.URL;


public class MyRichText extends WXComponent<TextView> {

    private TextView tv;

    public MyRichText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected TextView initComponentHostView(@NonNull Context context) {
        tv = new TextView(context);
//    view.setMovementMethod(LinkMovementMethod.getInstance());
        return tv;
    }

    @WXComponentProp(name = "txt")
    public void setTelLink(String txt) {
//    SpannableString spannable=new SpannableString(tel);
//    spannable.setSpan(new URLSpan("tel:"+tel),0,tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//    ((TextView) getHostView()).setText(spannable);
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        txt = WXFileUtils.loadAsset("test.txt", getContext());
        txt = "<html><body>" + txt + "</body></html>";
        tv.setText(Html.fromHtml(txt, imgGetter, null));
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Log.d("test", "source = " + source);
            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                drawable = Drawable.createFromStream(url.openStream(), "");  //获取网路图片
                Log.d("test", "drawable = null ? " + (drawable == null));
            } catch (Exception e) {
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };
}
