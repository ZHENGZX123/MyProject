package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.UndeclaredThrowableException;

import cn.kiway.mdm.App;
import cn.kiway.mdm.hprose.socket.KwHproseClient;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.App.isPublicNetwork;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.ANSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.DISMISS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.REPONSEDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.SIGNDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.UNSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.YUXUNFANWENJLU;
import static cn.kiway.mdm.hprose.socket.MessageType.ANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.SIGN;
import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;
import static cn.kiway.mdm.utils.IContants.REPONSE_URL;
import static cn.kiway.mdm.utils.IContants.SING_URL;


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

                    try {
                        JSONObject da = new JSONObject();
                        da.put("msgType", SIGN);
                        da.put("userId", Utils.getIMEI(getContext()));
                        da.put("msg", "签到");
                        if (isPublicNetwork) {
                            //TODO
                            uploadSign();
                        } else {
                            if (App.instance.isIos) {
                                App.instance.client.sendTCP(da.toString());
                            } else {
                                if (KwHproseClient.helloClient != null)
                                    KwHproseClient.helloClient.sign(da.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case REPONSEDIALOG://响应器回应听懂
                    try {
                        JSONObject data = new JSONObject();
                        data.put("userId", Utils.getIMEI(getContext()));
                        data.put("msg", "1");
                        data.put("msgType", SUREREPONSE);
                        if (isPublicNetwork) {
                            //TODO
                            uploadReponse(1 + "");
                        } else {
                            if (App.instance.isIos) {
                                App.instance.client.sendTCP(data.toString());
                            } else {
                                if (KwHproseClient.helloClient != null)
                                    KwHproseClient.helloClient.reponse(data.toString());
                            }
                        }
                    } catch (UndeclaredThrowableException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case REPONSEDIALOG + 100://响应器回应听不懂

                    try {
                        JSONObject data = new JSONObject();
                        data.put("userId", Utils.getIMEI(getContext()));
                        data.put("msg", "0");
                        data.put("msgType", SUREREPONSE);
                        if (isPublicNetwork) {
                            //TODO
                            uploadReponse(0 + "");
                        } else {
                            if (App.instance.isIos) {
                                App.instance.client.sendTCP(data.toString());
                            } else {
                                if (KwHproseClient.helloClient != null)
                                    KwHproseClient.helloClient.reponse(data.toString());
                            }
                        }
                    } catch (UndeclaredThrowableException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case ANSWERDIALOG://TODO 抢答
                    try {
                        JSONObject da = new JSONObject();
                        da.put("msgType", ANSWER);
                        da.put("userId", Utils.getIMEI(getContext()));
                        da.put("msg", "1");
                        if (isPublicNetwork) {
                            //TODO
                        } else {
                            if (App.instance.isIos) {
                                App.instance.client.sendTCP(da.toString());
                            } else {
                                if (KwHproseClient.helloClient != null)
                                    KwHproseClient.helloClient.answerqusetion(da.toString());
                            }
                        }
                    } catch (UndeclaredThrowableException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case ANSWERDIALOG + 100://TODO 不抢答
                    try {
                        JSONObject da = new JSONObject();
                        da.put("userId", Utils.getIMEI(getContext()));
                        da.put("msgType", ANSWER);
                        da.put("msg", "0");
                        if (isPublicNetwork) {
                            //TODO
                        } else {
                            if (App.instance.isIos) {
                                App.instance.client.sendTCP(da.toString());
                            } else {
                                if (KwHproseClient.helloClient != null)
                                    KwHproseClient.helloClient.answerqusetion(da.toString());
                            }
                        }
                    } catch (UndeclaredThrowableException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void uploadSign() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        client.addHeader("x-auth-token", context.getSharedPreferences("kiway", 0).getString("x-auth-token",
                ""));
        String url = SING_URL;
        Log.d("test", "url = " + url);
        RequestParams param = new RequestParams();
        param.put("msgType", SIGN + "");
        param.put("userId", Utils.getIMEI(context));
        param.put("msg", "签到");
        param.put("token", context.getSharedPreferences("kiway", 0).getString("Classtoken",
                ""));
        client.post(context, url, param, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, String ret) {
                Logger.log("::::::::::::" + ret);
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                Logger.log("::::::::::::" + ret);
            }
        });
    }

    public void uploadReponse(final String msg) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(10000);
        String url = REPONSE_URL;
        Log.d("test", "url = " + url);
        client.addHeader("x-auth-token", context.getSharedPreferences("kiway", 0).getString("x-auth-token",
                ""));
        RequestParams param = new RequestParams();
        param.put("msgType", SUREREPONSE + "");
        param.put("userId", Utils.getIMEI(context));
        param.put("msg", msg);
        param.put("token", context.getSharedPreferences("kiway", 0).getString("Classtoken",
                ""));
        client.post(context, url, param, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int arg0, Header[] arg1, String ret) {
                Logger.log("::::::::::::" + ret);
            }

            @Override
            public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                Logger.log("::::::::::::" + ret);
            }
        });

    }
}
