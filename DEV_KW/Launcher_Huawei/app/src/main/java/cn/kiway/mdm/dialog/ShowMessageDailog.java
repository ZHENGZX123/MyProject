package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ShowMessageDailog extends Dialog implements View.OnClickListener,DialogInterface.OnShowListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


    TextView textView;

    public ShowMessageDailog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    String message;

    public void setShowMessage(String message) {
        this.message = message;
        if (textView != null)
            textView.setText(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
        fullWindowCenter();
        textView = (TextView) findViewById(R.id.message);
        findViewById(R.id.ok).setOnClickListener(this);
        setOnShowListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        textView = (TextView) findViewById(R.id.message);
        if (textView != null)
            textView.setText(message);
    }
}
