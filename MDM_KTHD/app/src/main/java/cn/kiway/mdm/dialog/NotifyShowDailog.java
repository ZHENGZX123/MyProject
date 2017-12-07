package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import studentsession.kiway.cn.mdm_studentsession.R;


/**
 * Created by Administrator on 2017/10/12.
 */

public class NotifyShowDailog extends Dialog implements View.OnClickListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    String title;
    String message;
    String name;

    public NotifyShowDailog(Context context, String title, String message, String name) {
        super(context, R.style.LoadingDialog);
        this.title = title;
        this.name = name;
        this.message = message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_notify_show);
        TextView titleTx = (TextView) findViewById(R.id.title);
        TextView messageTx = (TextView) findViewById(R.id.message);
        TextView nameTx = (TextView) findViewById(R.id.name);
        titleTx.setText(title);
        messageTx.setText(message);
        nameTx.setText(name);
        fullWindowCenter();
        setCancelable(false);
        findViewById(R.id.ok).setOnClickListener(this);
    }


    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                dismiss();
                break;
        }
    }
}
