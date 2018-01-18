package cn.kiway.mdm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import cn.kiway.mdm.App;
import cn.kiway.mdm.dialog.AnswerDialog;
import cn.kiway.mdm.dialog.KnowledgeStatistics;
import cn.kiway.mdm.dialog.MyProgressDialog;
import cn.kiway.mdm.dialog.NotifyShowDailog;
import cn.kiway.mdm.dialog.SignDialog;
import cn.kiway.mdm.dialog.SmokeAnswerDialog;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BaseActivity extends Activity {

    public App app;
    AnswerDialog answerDialog;
    SmokeAnswerDialog smokeAnswerDialog;
    SignDialog signDialog;
    KnowledgeStatistics knowDialog;

    public TextView titleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.currentActivity = this;
        app = (App) getApplicationContext();
    }

    public void initView() {
        titleName = (TextView) findViewById(R.id.titleName);
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

    public void onQuestion(final JSONObject data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (data.optString("command").equals("question")) {
                    if (data.optJSONObject("content").optInt("questionType") == 1) {//点名答
                    } else if (data.optJSONObject("content").optInt("questionType") == 2) {//抢答
                        answerDialog = new AnswerDialog(BaseActivity.this);
                        answerDialog.show();
                    } else if (data.optJSONObject("content").optInt("questionType") == 3) {//随机抽签
                        smokeAnswerDialog = new SmokeAnswerDialog(BaseActivity.this);
                        smokeAnswerDialog.show();
                    } else if (data.optJSONObject("content").optInt("questionType") == 4) {//测评
                    }
                }
            }
        });
    }

    public void onSign() {//签到
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signDialog = new SignDialog(BaseActivity.this);
                signDialog.show();
            }
        });
    }

    public void onResponsePush() {//知识点反馈
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                knowDialog = new KnowledgeStatistics(BaseActivity.this);
                knowDialog.show();
            }
        });
    }

    public void clickBack(View view) {
        finish();
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
