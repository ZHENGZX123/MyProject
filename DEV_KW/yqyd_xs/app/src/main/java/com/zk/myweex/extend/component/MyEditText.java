package com.zk.myweex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.HashMap;

import cn.kiway.yiqiyuedu.R;

public class MyEditText extends WXComponent<View> {
    private View view;
    private ClearEditText input;
    private Button cancel;

    public MyEditText(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        view = View.inflate(getContext(), R.layout.layout_edittext, null);
        input = (ClearEditText) view.findViewById(R.id.input);
        cancel = (Button) view.findViewById(R.id.cancel);

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            //发送请求
                            String content = input.getText().toString().trim();
                            KeyBoardUtils.closeKeybord(input, getContext());
                            if (TextUtils.isEmpty(content)) {
                                return true;
                            }
                            HashMap map = new HashMap();
                            map.put("content", content);
                            fireEvent("search", map);
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input.setText("");
                KeyBoardUtils.closeKeybord(input, getContext());
                fireEvent("cancel", new HashMap());
            }
        });
        return view;
    }

    @WXComponentProp(name = "content")
    public void setContent(String content) {
        Log.d("test", this.toString() + " content = " + content);
        if (TextUtils.isEmpty(content)) {
            return;
        }
        KeyBoardUtils.closeKeybord(input, getContext());
        input.setText(content);
        input.setSelection(content.length());
        HashMap map = new HashMap();
        map.put("content", content);
        fireEvent("search", map);
    }
}
