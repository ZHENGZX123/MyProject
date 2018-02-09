package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.mdm.App;
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
    private Fenxi fenxi;
    private TextView titleName, qusetionType, content, answerTV, content2;
    private LinearLayout imgLL, imgLL2;
    private LinearLayout ll_answer;

    private ImageView answerIV;
    private GridView answerGV;

    private Button prev, next;

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
        content = (TextView) findViewById(R.id.content);
        imgLL = (LinearLayout) findViewById(R.id.imgLL);
        ll_answer = (LinearLayout) findViewById(R.id.ll_answer);
        content2 = (TextView) findViewById(R.id.content2);
        imgLL2 = (LinearLayout) findViewById(R.id.imgLL2);

        answerGV = (GridView) findViewById(R.id.answerGV);
        answerTV = (TextView) findViewById(R.id.answerTV);
        answerIV = (ImageView) findViewById(R.id.answerIV);
        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);

        if (fenxi.type == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        } else if (fenxi.type == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        } else if (fenxi.type == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
        } else if (fenxi.type == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
        }
    }

    public void loadData() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            String url = getAnalysisDetial + fenxi.id;
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
        imgLL.removeAllViews();
        String img = data.optString("questionImg");
        if (!TextUtils.isEmpty(img)) {
            String imgs[] = img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                final String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.showBigImage(FenxiDetailActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, App.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL.addView(iv, lp);
            }
        }

        content2.setText(data.optString("answerContent"));
        imgLL2.removeAllViews();
        img = data.optString(data.optString("answerImg"));
        if (!TextUtils.isEmpty(img)) {
            String imgs[] = img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                final String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.showBigImage(FenxiDetailActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, App.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL2.addView(iv, lp);
            }
        }
    }

    public void showAnswer(View view) {
        if (ll_answer.isShown()) {
            ll_answer.setVisibility(View.GONE);
        } else {
            ll_answer.setVisibility(View.VISIBLE);
        }
    }

    public void prev(View view) {

    }

    public void next(View view) {

    }
}
