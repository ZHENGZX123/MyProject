package com.alibaba.weex.extend.component;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.alibaba.weex.extend.view.CircleProgressBar3;
import com.alibaba.weex.utils.DensityUtil;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class MyProgressBar3 extends WXComponent<CircleProgressBar3> {

    public MyProgressBar3(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected CircleProgressBar3 initComponentHostView(@NonNull Context context) {
        CircleProgressBar3 cpb = new CircleProgressBar3(context);
        cpb.setTextSize(DensityUtil.dip2px(getContext(), 18.0f));
        cpb.setTextColor(Color.BLACK);
        cpb.setRoundWidth(30.0f);
        return cpb;
    }

    @WXComponentProp(name = "progress")
    public void setProgress(int progress) {
        ((CircleProgressBar3) getHostView()).setProgress(progress);
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {
        ((CircleProgressBar3) getHostView()).setMax(max);
    }

}
