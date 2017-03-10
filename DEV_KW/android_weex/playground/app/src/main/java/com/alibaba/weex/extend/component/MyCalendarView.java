package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.CalendarView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class MyCalendarView extends WXComponent<CalendarView> {

    public MyCalendarView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected CalendarView initComponentHostView(@NonNull Context context) {
        final CalendarView cv = new CalendarView(context);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                Log.d("test", year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        });
        return cv;
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {

    }
}
