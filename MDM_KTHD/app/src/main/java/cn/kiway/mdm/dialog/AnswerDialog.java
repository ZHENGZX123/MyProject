package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.kiway.mdm.App;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class AnswerDialog extends BaseDialog {

    public TextView answerTxt;
    public Button answerBtn;

    public AnswerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_answer);
        answerTxt = (TextView) findViewById(R.id.answer_staute);
        answerBtn = (Button) findViewById(R.id.answer_btn);
        answerBtn.setOnClickListener(this);
        fullWindowCenter();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.answer_btn:
                try {
                    App.instance.mRemoteInterface.callbackMessage("qiangda");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                this.dismiss();
                break;
        }
    }
}
