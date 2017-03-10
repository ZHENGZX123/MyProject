package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

public class MySeekbar extends WXComponent<SeekBar> {

    public MySeekbar(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected SeekBar initComponentHostView(@NonNull Context context) {
        SeekBar view = new SeekBar(context);
        return view;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {
        ((SeekBar) getHostView()).setMax(max);
    }

    @WXComponentProp(name = "current")
    public void setCurrent(int current) {
        ((SeekBar) getHostView()).setProgress(current);
    }
}
