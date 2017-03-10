package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.weex.extend.view.wheelview.OnWheelChangedListener;
import com.alibaba.weex.extend.view.wheelview.WheelView;
import com.alibaba.weex.extend.view.wheelview.adapter.ArrayWheelAdapter;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.Random;


public class MyWheelView extends WXComponent<WheelView> {

    public MyWheelView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected WheelView initComponentHostView(@NonNull Context context) {
        WheelView wv = new WheelView(context);

        String[] ymdData = new String[10];
        for (int i = 0; i < ymdData.length; i++) {
            ymdData[i] = new Random().nextInt() + "";
        }

        ArrayWheelAdapter<String> weekAdapter = new ArrayWheelAdapter<>(context, ymdData);
        wv.setViewAdapter(weekAdapter);
        weekAdapter.setTextSize(18);
        wv.setCyclic(true);
        wv.setCurrentItem(0);
        wv.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });
        return wv;
    }

    @WXComponentProp(name = "tel")
    public void setTelLink(String tel) {
//        ((WheelView) getHostView()).setText(spannable);
    }
}
