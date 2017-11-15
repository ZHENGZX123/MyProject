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

import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.hprose.socket.KwHproseClient;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.activity.MainActivity.USAGE_STATS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.ANSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.DISMISS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.REPONSEDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.SIGNDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.UNSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.YUXUNFANWENJLU;
import static cn.kiway.mdm.hprose.socket.MessageType.ANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.SIGN;
import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ShowMessageDailog extends Dialog implements View.OnClickListener, DialogInterface.OnShowListener {
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
        switch (view.getId()) {
            case R.id.ok:
                switch (messageId) {
                    case DISMISS:
                        break;
                    case YUXUNFANWENJLU:
                        ((MainActivity) context).startActivityForResult(
                                new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                                USAGE_STATS);
                        break;
                    case SIGNDIALOG:
                        try {
                            JSONObject da = new JSONObject();
                            da.put("msgType", SIGN);
                            da.put("userId", Utils.getIMEI(getContext()));
                            da.put("msg", "签到");
                            if (KwHproseClient.helloClient != null)
                                KwHproseClient.helloClient.sign(da.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case REPONSEDIALOG:
                        try {
                            JSONObject data = new JSONObject();
                            data.put("userId", Utils.getIMEI(getContext()));
                            data.put("msg", "1");
                            data.put("msgType", SUREREPONSE);
                            if (KwHproseClient.helloClient != null)
                                KwHproseClient.helloClient.reponse(data.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case ANSWERDIALOG:
                        try {
                            JSONObject da = new JSONObject();
                            da.put("msgType", ANSWER);
                            da.put("userId", Utils.getIMEI(getContext()));
                            da.put("msg", "1");
                            if (KwHproseClient.helloClient != null)
                                KwHproseClient.helloClient.sign(da.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case UNSWERDIALOG:
                        dismiss();
                        break;
                }
                break;
            case R.id.ok2:
                switch (messageId) {
                    case REPONSEDIALOG:
                        try {
                            JSONObject data = new JSONObject();
                            data.put("userId", Utils.getIMEI(getContext()));
                            data.put("msg", "0");
                            data.put("msgType", SUREREPONSE);
                            if (KwHproseClient.helloClient != null)
                                KwHproseClient.helloClient.reponse(data.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case ANSWERDIALOG:
                        try {
                            JSONObject da = new JSONObject();
                            da.put("msgType", ANSWER);
                            da.put("userId", Utils.getIMEI(getContext()));
                            da.put("msg", "0");
                            if (KwHproseClient.helloClient != null)
                                KwHproseClient.helloClient.sign(da.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case UNSWERDIALOG:
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
        if (messageId == REPONSEDIALOG||messageId==ANSWERDIALOG) {
            findViewById(R.id.ok2).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ok2).setVisibility(View.GONE);
        }
    }

}
