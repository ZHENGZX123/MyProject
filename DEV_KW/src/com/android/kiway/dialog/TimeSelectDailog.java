package com.android.kiway.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.android.launcher3.R;

import com.android.kiway.view.RangeSeekBar;

/**
 * Created by Administrator on 2017/10/12.
 */

public class TimeSelectDailog extends Dialog implements View.OnClickListener {
    RangeSeekBar rangeSeekBar;
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    int leftValue, rightValue;


    public TimeSelectDailog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    String packageName;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time_select);
        fullWindowCenter();
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.RangeSeekBar);
        rangeSeekBar.setOnRangeChangeListener(new RangeSeekBar.OnRangeChangeListener() {
            @Override
            public void onRangeChange(int leftValue, int rightValue) {
                TimeSelectDailog.this.leftValue = leftValue;
                TimeSelectDailog.this.rightValue = rightValue;
            }
        });
        findViewById(R.id.ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getContext().getSharedPreferences("kiway", 0).edit().putString(packageName, leftValue + "-" + rightValue).commit();
        dismiss();
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }
}
