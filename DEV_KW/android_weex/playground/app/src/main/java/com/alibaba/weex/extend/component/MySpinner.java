package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;

public class MySpinner extends WXComponent<Spinner> {

    public MySpinner(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected Spinner initComponentHostView(@NonNull Context context) {
        Spinner view = new Spinner(context);

        String[] mItems = getContext().getResources().getStringArray(R.array.languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapter(adapter);

        return view;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {
        //((Spinner) getHostView()).setMax(max);
    }

    @WXComponentProp(name = "current")
    public void setCurrent(int current) {
        //((Spinner) getHostView()).setProgress(current);
    }
}
