package com.alibaba.weex.extend.component;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.weex.extend.view.AlwaysMarqueeTextView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

public class MyAlwaysMarqueeTextView extends WXComponent<AlwaysMarqueeTextView> {

    public MyAlwaysMarqueeTextView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected AlwaysMarqueeTextView initComponentHostView(@NonNull Context context) {
        AlwaysMarqueeTextView view = new AlwaysMarqueeTextView(context);
        return view;
    }

    @WXComponentProp(name = "tel")
    public void setTelLink(String tel) {
        AlwaysMarqueeTextView view = ((AlwaysMarqueeTextView) getHostView());
        view.setText(tel);
        view.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        view.setFocusableInTouchMode(true);
        view.setMarqueeRepeatLimit(-1);
        view.setSingleLine();
        view.setTextColor(Color.RED);
    }
}
