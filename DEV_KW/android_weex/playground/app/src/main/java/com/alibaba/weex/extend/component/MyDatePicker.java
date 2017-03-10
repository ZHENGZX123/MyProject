package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.DatePicker;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.Calendar;


public class MyDatePicker extends WXComponent<DatePicker> {

    public MyDatePicker(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected DatePicker initComponentHostView(@NonNull Context context) {
        final DatePicker picker = new DatePicker(context);
        picker.setCalendarViewShown(false);
        picker.setSpinnersShown(true);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        picker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("test", year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        });

        return picker;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {

    }
}
