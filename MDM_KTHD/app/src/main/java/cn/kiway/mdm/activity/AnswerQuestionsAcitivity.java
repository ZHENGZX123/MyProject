package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import org.json.JSONObject;

import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2018/1/3.
 */

public class AnswerQuestionsAcitivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    JSONObject data = new JSONObject();
    RadioGroup choiceQuestion, TofF;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
//        try {
//            data = new JSONObject(getIntent().getStringExtra("data"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        initData();
    }

    void initView() {
        choiceQuestion = (RadioGroup) findViewById(R.id.choiceQuestion);
        TofF = (RadioGroup) findViewById(R.id.TofF);
        choiceQuestion.setOnCheckedChangeListener(this);
        TofF.setOnCheckedChangeListener(this);
    }

    void initData() {
        // int qusetionType = data.optJSONObject("content").optInt("type");
        int qusetionType = 1;
        switch (qusetionType) {
            case 1://选择
                findViewById(R.id.choiceQuestion).setVisibility(View.VISIBLE);
                break;
            case 2://问答
                break;
            case 3://填空
                findViewById(R.id.blankAnswers).setVisibility(View.VISIBLE);
                break;
            case 4://判断
                findViewById(R.id.TofF1).setVisibility(View.VISIBLE);
                break;
            case 5://主观题
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (radioGroup.getId()) {
            case R.id.TofF:
                break;
            case R.id.choiceQuestion:
                break;
        }
    }
}
