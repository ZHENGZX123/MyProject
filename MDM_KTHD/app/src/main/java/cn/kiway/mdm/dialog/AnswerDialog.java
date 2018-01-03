package cn.kiway.mdm.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.zbus.ZbusHost.zbusTeacherTopic;

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
        fullWindowCenter();
    }

    //抢答后状态
    public void answerResult() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.answer_btn:
                if (answerBtn.getText().toString().equals("抢答")) {//处于抢答状态
                    try {
                        ZbusUtils.sendMsg(zbusTeacherTopic, "抢答");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
