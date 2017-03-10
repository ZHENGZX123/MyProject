package com.alibaba.weex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RadioButton;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

public class MyRadioButton extends WXComponent<RadioButton> {

    public MyRadioButton(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected RadioButton initComponentHostView(@NonNull Context context) {
        RadioButton view = new RadioButton(context);
        return view;
    }

    @WXComponentProp(name = "setchecked")
    public void setChecked(boolean c) {
        ((RadioButton) getHostView()).setChecked(c);
    }

    @WXComponentProp(name = "settext")
    public void setChecked(String s) {
        ((RadioButton) getHostView()).setText(s);
    }


}
