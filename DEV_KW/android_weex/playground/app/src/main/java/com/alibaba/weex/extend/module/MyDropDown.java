package com.alibaba.weex.extend.module;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;
import com.wjc.R;

public class MyDropDown extends WXModule {

    @JSMethod(uiThread = true)
    public void showDropDown(String list, int x , int y , final JSCallback callback) {
        Log.d("test", "lig = " + list);
        View contentView = LayoutInflater.from(mWXSDKInstance.getContext()).inflate(
                R.layout.pop_window, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失
        popupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        Activity a = (Activity) mWXSDKInstance.getContext();
        View root = a.getWindow().getDecorView();
        popupWindow.showAtLocation(root, Gravity.TOP | Gravity.LEFT, x, y);


        contentView.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.invoke("一年级");
                popupWindow.dismiss();
            }
        });
    }


}
