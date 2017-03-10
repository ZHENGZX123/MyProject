package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;

import com.alibaba.weex.extend.view.MoneyEditText;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

public class MyMoneyEditText extends WXComponent<MoneyEditText> {

    public MyMoneyEditText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected MoneyEditText initComponentHostView(@NonNull Context context) {
        MoneyEditText view = new MoneyEditText(context);
        view.setHint("请填写金额");
        view.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        // 8194
        // TYPE_NUMBER_FLAG_DECIMAL 这个无效？
        return view;
    }

    @WXComponentProp(name = "tel")
    public void setTelLink(String tel) {
//    ((MoneyEditText) getHostView()).setText(tel);
    }
}
