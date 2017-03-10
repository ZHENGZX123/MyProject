package com.alibaba.weex.extend.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

//只能输入小数点后两位，感觉有bug啊，日啊
public class MoneyEditText extends EditText {

    private final TextWatcher mTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d("test", "onTextChanged s = " + s.toString());
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                    setText(s);
                    setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                //如果第一个是.那么添加一个0
                s = "0" + s;
                setText(s);
                setSelection(2);
            }

            if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    setText(s.subSequence(0, 1));
                    setSelection(1);
                    return;
                }
            }
        }
    };

    public MoneyEditText(Context context) {
        super(context);
        addTextChangedListener(mTextWatcher);
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(mTextWatcher);
    }

}