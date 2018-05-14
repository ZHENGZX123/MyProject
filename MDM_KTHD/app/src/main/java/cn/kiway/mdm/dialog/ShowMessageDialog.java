package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import cn.kiway.mdm.activity.QuestionActivity;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.ANSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.DISMISS;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.QUESTIONDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.REPONSEDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.SIGNDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.UNSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDialog.MessageId.YUXUNFANWENJLU;


/**
 * Created by Administrator on 2017/10/12.
 */

public class ShowMessageDialog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {
    protected LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

    TextView textView;
    String message;
    int messageId;
    Context context;

    public static class MessageId {
        public static final int DISMISS = 0;//消失
        public static final int YUXUNFANWENJLU = DISMISS + 1;//允许访问记录
        public static final int PUSHFILE = YUXUNFANWENJLU + 1;//允许访问记录
        public static final int SIGNDIALOG = PUSHFILE + 1;//签到
        public static final int REPONSEDIALOG = SIGNDIALOG + 1;//学生是否听懂
        public static final int ANSWERDIALOG = REPONSEDIALOG + 1;
        public static final int UNSWERDIALOG = ANSWERDIALOG + 1;
        public static final int QUESTIONDIALOG=UNSWERDIALOG+1;
    }

    public ShowMessageDialog(Context context) {
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
        findViewById(R.id.ok2).setOnClickListener(this);
        setOnShowListener(this);
    }

    @Override
    public void onClick(View view) {
        Message message = new Message();
        switch (view.getId()) {
            case R.id.ok:
                switch (messageId) {
                    case DISMISS:
                        break;
                    case YUXUNFANWENJLU:
                        break;
                    case SIGNDIALOG:
                        message.what = SIGNDIALOG;
                        handler.sendMessage(message);
                        break;
                    case REPONSEDIALOG:
                        message.what = REPONSEDIALOG;
                        handler.sendMessage(message);
                        break;
                    case ANSWERDIALOG:
                        message.what = ANSWERDIALOG;
                        handler.sendMessage(message);
                        break;
                    case UNSWERDIALOG:
                        dismiss();
                        break;
                    case QUESTIONDIALOG:
                        if (context instanceof QuestionActivity){
                            ((QuestionActivity) context).finish();
                        }
                        break;
                }
                break;
            case R.id.ok2:
                switch (messageId) {
                    case REPONSEDIALOG:
                        message.what = REPONSEDIALOG + 100;
                        handler.sendMessage(message);
                        break;
                    case ANSWERDIALOG:
                        message.what = ANSWERDIALOG + 100;
                        handler.sendMessage(message);
                        break;
                    case UNSWERDIALOG:
                        dismiss();
                        break;
                    case QUESTIONDIALOG:
                        dismiss();
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
        if (messageId == REPONSEDIALOG || messageId == ANSWERDIALOG) {
            findViewById(R.id.ok2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ok2).setVisibility(View.GONE);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SIGNDIALOG://签到
                    break;
                case REPONSEDIALOG://响应器回应听懂
                    break;
                case REPONSEDIALOG + 100://响应器回应听不懂
                    break;
                case ANSWERDIALOG://TODO 抢答
                    break;
                case ANSWERDIALOG + 100://TODO 不抢答
                    break;
            }
        }
    };

}
