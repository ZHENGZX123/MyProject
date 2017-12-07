package cn.kiway.mdm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import cn.kiway.mdm.App;
import cn.kiway.mdm.dialog.MyProgressDialog;
import cn.kiway.mdm.dialog.NotifyShowDailog;
import cn.kiway.mdm.dialog.ShowMessageDailog;

import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.ANSWERDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.DISMISS;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.REPONSEDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.SIGNDIALOG;
import static cn.kiway.mdm.dialog.ShowMessageDailog.MessageId.UNSWERDIALOG;
import static cn.kiway.mdm.hprose.socket.MessageType.ANSWER;
import static cn.kiway.mdm.hprose.socket.MessageType.SIGN;
import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;
import static cn.kiway.mdm.hprose.socket.MessageType.UNANSWER;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BaseActivity extends Activity {
   public App app;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.currentActivity = this;
        app = (App) getApplicationContext();
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.instance.currentActivity = this;
    }

    public void NotifyShow(final String title, final String message, final String name) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NotifyShowDailog notifyShowDailog = new NotifyShowDailog(BaseActivity.this, title, message, name);
                notifyShowDailog.show();
            }
        });
    }

    MyProgressDialog progressDialog;
    String proData = "";

    public void downloadFile(String data) {
        if (proData.equals(data) && progressDialog != null && progressDialog.isShowing())
            return;
        proData = data;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new MyProgressDialog(BaseActivity.this, proData);
                progressDialog.show();
            }
        });
    }

    public ShowMessageDailog showMessageDailog;
    int showI;

    public void Session(int i) {
        if (showI == i && showMessageDailog != null && showMessageDailog.isShowing())
            return;
        showI = i;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showMessageDailog = new ShowMessageDailog(BaseActivity.this);
                showMessageDailog.setCancelable(false);
                if (showI == SIGN) {
                    showMessageDailog.setShowMessage("老师上课签到，请你点击确定确认签到上课", SIGNDIALOG);
                } else if (showI == ANSWER) {
                    showMessageDailog.setShowMessage("老师有道题正在抢答，是否进去抢答", ANSWERDIALOG);
                } else if (showI == UNANSWER) {
                    showMessageDailog.setShowMessage("抢答结束了", UNSWERDIALOG);
                } else if (showI == SUREREPONSE) {
                    showMessageDailog.setShowMessage("同学们，老师这套题清楚了吗？", REPONSEDIALOG);
                }
                try {
                if (!showMessageDailog.isShowing())
                    showMessageDailog.show();
                }catch (WindowManager.BadTokenException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showMessageDailog = new ShowMessageDailog(BaseActivity.this);
                    showMessageDailog.setShowMessage(message, DISMISS);
                    showMessageDailog.show();
                }catch (WindowManager.BadTokenException e){
                    e.printStackTrace();
                }

            }
        });
    }
}
