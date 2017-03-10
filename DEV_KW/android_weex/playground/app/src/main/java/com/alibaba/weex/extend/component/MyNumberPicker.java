package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.NumberPicker;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class MyNumberPicker extends WXComponent<NumberPicker> {

    public MyNumberPicker(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected NumberPicker initComponentHostView(@NonNull Context context) {
        final NumberPicker picker = new NumberPicker(context);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                Log.d("test", "oldValue:" + oldVal + "   ; newValue: " + newVal);
            }
        });
        return picker;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {
        ((NumberPicker) getHostView()).setMaxValue(max);
    }

    @WXComponentProp(name = "min")
    public void setMin(int min) {
        ((NumberPicker) getHostView()).setMinValue(min);
    }

    @WXComponentProp(name = "value")
    public void setValue(int value) {
        ((NumberPicker) getHostView()).setValue(value);
    }
}
