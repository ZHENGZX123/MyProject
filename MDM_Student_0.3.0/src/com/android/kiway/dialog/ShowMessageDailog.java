package com.android.kiway.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.android.kiway.KWApp;
import com.android.launcher3.R;

import com.android.kiway.activity.MainActivity;
import com.android.kiway.utils.HttpUtil;

import static com.android.kiway.activity.MainActivity.USAGE_STATS;
import static com.android.kiway.dialog.ShowMessageDailog.MessageId.DISMISS;
import static com.android.kiway.dialog.ShowMessageDailog.MessageId.PARENT_BIND;
import static com.android.kiway.dialog.ShowMessageDailog.MessageId.YUXUNFANWENJLU;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ShowMessageDailog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    TextView textView;
    String message;
    int messageId;
    Context context;
    Button ok1, ok2;

    public static class MessageId {
        public static final int DISMISS = 0;//消失
        public static final int YUXUNFANWENJLU = DISMISS + 1;//允许访问记录
        public static final int PARENT_BIND = YUXUNFANWENJLU + 1;
    }

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

    String token;

    public void setToken(String token) {
        this.token = token;
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
        ok1 = (Button) findViewById(R.id.ok);
        ok2 = (Button) findViewById(R.id.ok2);
        findViewById(R.id.ok).setOnClickListener(this);
        findViewById(R.id.ok2).setOnClickListener(this);
        setOnShowListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                switch (messageId) {
                    case YUXUNFANWENJLU:
                        ((MainActivity) context).startActivityForResult(
                                new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                                USAGE_STATS);
                        break;
                    case PARENT_BIND:
                        HttpUtil.doBind(KWApp.instance.currentActivity, 1, token);
                        break;
                }
                break;
            case R.id.ok2:
                switch (messageId) {
                    case YUXUNFANWENJLU:
                        break;
                    case PARENT_BIND:
                        HttpUtil.doBind(KWApp.instance.currentActivity, 2, token);
                        break;
                }
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
        if (messageId == PARENT_BIND) {
            ok2.setText("不同意");
            ok1.setText("同意");
            ok2.setVisibility(View.VISIBLE);
        } else {
            ok1.setText("确定");
            ok2.setVisibility(View.GONE);
        }
    }
}
