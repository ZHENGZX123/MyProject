package com.alibaba.weex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.HashMap;
import java.util.Map;

public class MyCheckBox extends WXComponent<CheckBox> {


    public MyCheckBox(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);

    }

    @Override
    protected CheckBox initComponentHostView(@NonNull Context context) {
        CheckBox cb = new CheckBox(context);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("test", "b = " + b);
                //回调给js
                Map<String, Object> params = new HashMap<>();
                params.put("test1", "test1");
                params.put("test2", "test2");
                fireEvent("test", params);


            }
        });
        return cb;
    }

    @WXComponentProp(name = "setchecked")
    public void setChecked(boolean c) {
        ((CheckBox) getHostView()).setChecked(c);
    }

    @WXComponentProp(name = "settext")
    public void setChecked(String s) {
        ((CheckBox) getHostView()).setText(s);
    }


}
