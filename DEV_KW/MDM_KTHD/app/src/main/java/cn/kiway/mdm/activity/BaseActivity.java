package cn.kiway.mdm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import cn.kiway.mdm.App;
import cn.kiway.mdm.dialog.QiangdaDialog;
import cn.kiway.mdm.dialog.KnowledgeDialog;
import cn.kiway.mdm.dialog.MyProgressDialog;
import cn.kiway.mdm.dialog.NotifyShowDialog;
import cn.kiway.mdm.dialog.SignDialog;
import cn.kiway.mdm.model.Question;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/4.
 */

public class BaseActivity extends Activity {

    public App app;
    public QiangdaDialog qiangdaDialog;
    public SignDialog signDialog;
    public KnowledgeDialog knowDialog;
    public TextView titleName;
    public RelativeLayout backRL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.instance.currentActivity = this;
        app = (App) getApplicationContext();
    }

    public void initView() {
        backRL = (RelativeLayout) findViewById(R.id.backRL);
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
                NotifyShowDialog notifyShowDailog = new NotifyShowDialog(BaseActivity.this, title, message, name);
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
                int questionType = data.optInt("questionType");
                String questionStr = data.optString("questions");
                Log.d("test", "questionStr = " + questionStr);
                List<Question> questions = new GsonBuilder().create().fromJson(questionStr, new TypeToken<List<Question>>() {
                }.getType());
                Log.d("test", "questions = " + questions);
                startActivity(new Intent(BaseActivity.this, AnswerQuestionsAcitivity.class));
            }
        });
    }

    public void onQiangda() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                qiangdaDialog = new QiangdaDialog(BaseActivity.this);
                qiangdaDialog.show();
            }
        });
    }

    public void onQiangdaResult(final int result, final String qiangdaStudentName) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (qiangdaDialog == null) {
                    return;
                }
                qiangdaDialog.show();
                if (result == 1) {
                    qiangdaDialog.setQiangdaResult("恭喜你抢到了答题权");
                } else {
                    qiangdaDialog.setQiangdaResult(qiangdaStudentName + "抢到了答题权");
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

    public void onTongji(final String knowledge) {//知识点反馈
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                knowDialog = new KnowledgeDialog(BaseActivity.this, knowledge);
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
