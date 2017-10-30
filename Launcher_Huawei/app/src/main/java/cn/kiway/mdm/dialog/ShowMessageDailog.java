package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.MainActivity;

import static cn.kiway.mdm.activity.MainActivity.MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.DISMISS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.YUXUNFANWENJLU;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ShowMessageDailog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    TextView textView;
    String message;
    int messageId;
    Context context;


    public ShowMessageDailog(Context context) {
        super(context, R.style.LoadingDialog);
        this.context = context;
    }

    public void setShowMessage(String message) {
        this.message = message;
        this.messageId = DISMISS;
        if (textView != null)
            textView.setText(message);
    }

    public void setShowMessage(String message, int messageId) {
        this.message = message;
        this.messageId = messageId;
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
        switch (messageId) {
            case DISMISS:
                break;
            case YUXUNFANWENJLU:
                ((MainActivity) context).startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
                break;
        }
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

    public class MessageId {
        public static final int DISMISS = 0x0001;//消失
        public static final int YUXUNFANWENJLU = DISMISS + 1;//允许访问记录
    }

}
