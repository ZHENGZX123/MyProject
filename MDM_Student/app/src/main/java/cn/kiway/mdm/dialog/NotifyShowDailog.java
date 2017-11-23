package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/10/12.
 */

public class NotifyShowDailog extends Dialog implements View.OnClickListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);


    public NotifyShowDailog(Context context,String title,String message) {
        super(context, R.style.LoadingDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
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
