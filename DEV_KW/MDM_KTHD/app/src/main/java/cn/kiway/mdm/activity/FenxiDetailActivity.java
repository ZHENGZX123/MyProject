package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.mdm.model.Fenxi;
import cn.kiway.mdm.model.Question;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.activity.QuestionActivity.TYPE_QUESTION_CEPING;
import static cn.kiway.mdm.activity.QuestionActivity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.activity.QuestionActivity.TYPE_QUESTION_QIANGDA;
import static cn.kiway.mdm.activity.QuestionActivity.TYPE_QUESTION_SUIJICHOUDA;
import static cn.kiway.mdm.model.Question.TYPE_EMPTY;
import static cn.kiway.mdm.utils.HttpUtil.getAnalysisDetial;

/**
 * Created by Administrator on 2018/1/22.
 */

public class FenxiDetailActivity extends BaseActivity {
    Fenxi fenxi;
    TextView titleName, qusetionType, content, answerTV;
    ImageView status, answerIV;
    private GridView answerGV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxi_detail);
        fenxi = (Fenxi) getIntent().getSerializableExtra("fenxi");
        initView();
        loadData();
    }

    @Override
    public void initView() {
        super.initView();
        titleName = (TextView) findViewById(R.id.titleName);
        qusetionType = (TextView) findViewById(R.id.type);
        status = (ImageView) findViewById(R.id.status);
        content = (TextView) findViewById(R.id.content);
        answerGV = (GridView) findViewById(R.id.answerGV);
        answerTV = (TextView) findViewById(R.id.answerTV);
        answerIV = (ImageView) findViewById(R.id.answerIV);
        if (fenxi.getType() == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
        } else if (fenxi.getType() == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
        } else if (fenxi.getType() == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
        } else if (fenxi.getType() == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
        }

        if (fenxi.type == 0) {
            switch (fenxi.getStatus()) {
                case 0:
                    status.setBackground(getDrawable(R.drawable.u4620));
                    break;
                case 1:
                    status.setBackground(getDrawable(R.drawable.u4622));
                    break;
                case 2:
                    status.setBackground(getDrawable(R.drawable.u4624));
                    break;
            }
        } else {
            switch (fenxi.getStatus()) {
                case 0:
                    status.setBackground(getDrawable(R.drawable.u4632));
                    break;
                case 1:
                    status.setBackground(getDrawable(R.drawable.u4618));
                    break;
                case 2:
                    status.setBackground(getDrawable(R.drawable.u4624));
                    break;
            }
        }
    }

    public void loadData() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            String url = getAnalysisDetial + fenxi.getId();//截图资料;
            Logger.log(url);
            client.get(this, url, param, new
                    TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.e("test", "calls onSuccess = " + ret);
                            try {
                                JSONObject data = new JSONObject(ret);
                                if (data.optInt("statusCode") == 201) {
                                } else if (data.optInt("statusCode") == 200) {
                                    initData(data.optJSONObject("data"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                            Log.d("test", "calls onFailure = " + ret);
                            Logger.log("::::::::::::onFailure" + ret);
                            if (ret != null && !ret.equals("")) {
                                try {
                                    JSONObject data = new JSONObject(ret);
                                    if (data.optInt("statusCode") != 200) {
                                        Utils.login(FenxiDetailActivity.this, new Utils.ReLogin() {
                                            @Override
                                            public void onSuccess() {
                                                loadData();
                                            }

                                            @Override
                                            public void onFailure() {
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                toast("请求失败，请稍后再试");
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public void initData(JSONObject data) {
        int type = data.optInt("questionType");
        if (type == Question.TYPE_SINGLE) {
            qusetionType.setText("单选题");
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
        } else if (type == Question.TYPE_MULTI) {
            qusetionType.setText("多选题");
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
        } else if (type == TYPE_EMPTY) {
            qusetionType.setText("填空题");
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.VISIBLE);
            answerIV.setVisibility(View.GONE);
        } else if (type == Question.TYPE_JUDGE) {
            qusetionType.setText("判断题");
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
        } else if (type == Question.TYPE_ESSAY) {
            qusetionType.setText("问答题");
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.VISIBLE);
        }
        content.setText(data.optString("questionContent"));
    }
}
