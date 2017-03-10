package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.alibaba.weex.extend.view.AutofitTextView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

public class MyAutofitTextView extends WXComponent<AutofitTextView> {

    public MyAutofitTextView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected AutofitTextView initComponentHostView(@NonNull Context context) {
        AutofitTextView view = new AutofitTextView(context);
        view.setSingleLine();
        view.setTextSize(30);
        view.setMinTextSize(1);
        return view;
    }

    @WXComponentProp(name = "tel")
    public void setTelLink(String tel) {
        ((TextView) getHostView()).setText(tel);
    }
}
