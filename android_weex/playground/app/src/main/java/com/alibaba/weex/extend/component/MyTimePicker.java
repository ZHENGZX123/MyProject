package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TimePicker;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class MyTimePicker extends WXComponent<TimePicker> {

    public MyTimePicker(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected TimePicker initComponentHostView(@NonNull Context context) {
        final TimePicker picker = new TimePicker(context);
        picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                Log.d("test", hourOfDay + "-" + minute);
            }
        });

        return picker;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {

    }
}
