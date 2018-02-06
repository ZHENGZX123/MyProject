package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.xutils.common.util.DensityUtil;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.AttendItem;
import cn.kiway.mdm.entity.KnowledgeCountResult;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;

/**
 * Created by Administrator on 2018/1/17.
 */
//统计:知识点没接口、测评有接口
public class AttendTongjiActivity extends BaseActivity {

    private int type;//1知识点统计 2问题统计

    private TextView rate;
    private TextView people1;
    private TextView people2;
    private TextView people3;

    private View color1;
    private View color2;
    private View color3;

    private TextView group;
    private TextView group1;
    private TextView group2;
    private TextView group3;

    private AttendItem item;


    private int current = 0;//当前显示项

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_tongji);

        type = getIntent().getIntExtra("type", 1);
        item = (AttendItem) getIntent().getSerializableExtra("item");

        initView();

        if (type == 1) {
            int p1 = 0;
            int p2 = 0;
            int p3 = 0;
            for (KnowledgeCountResult kcr : item.results) {
                if (kcr.status == 0) {
                    p2++;
                } else if (kcr.status == 1) {
                    p1++;
                } else if (kcr.status == 2) {
                    p3++;
                }
            }
            setChartData(p1, p2, p3);
        } else if (type == 2) {
            //从服务器拿结果
            initData();
        }
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("知识点统计结果");
        rate = (TextView) findViewById(R.id.rate);
        people1 = (TextView) findViewById(R.id.people1);
        people2 = (TextView) findViewById(R.id.people2);
        people3 = (TextView) findViewById(R.id.people3);

        group = (TextView) findViewById(R.id.group);
        group1 = (TextView) findViewById(R.id.group1);
        group2 = (TextView) findViewById(R.id.group2);
        group3 = (TextView) findViewById(R.id.group3);

        if (type == 1) {
            group.setText("已掌握");
            group1.setText("已掌握");
            group2.setText("未掌握");
            group3.setText("以响应");
        } else if (type == 2) {
            group.setText("正确率");
            group1.setText("回答正确");
            group2.setText("回答错误");
            group3.setText("为作答");
        }

        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
    }

    public void initData() {
        Question question = item.questions.get(current);
        String id = question.examinationId;

        showPD();
        try {
            String url = KWApplication.clientUrl + "/device/teacher/course/examination/result?examinationId=" + id;
            Log.d("test", "examination/resul url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "examination/resul  onSuccess = " + ret);
                    hidePD();
                    setChartData(8, 10, 12);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    hidePD();
                    if (!Utils.check301(AttendTongjiActivity.this, s, "cepingTongji")) {
                        toast("请求失败，请稍后再试");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
    }

    private void setChartData(int p1, int p2, int p3) {
        int total = p1 + p2 + p3;
        float rate1 = (float) p1 / total;
        float rate2 = (float) p2 / total;
        float rate3 = (float) p3 / total;

        int totalHeight = DensityUtil.dip2px(200);
        int height1 = (int) (totalHeight * rate1);
        int height2 = (int) (totalHeight * rate2);
        int height3 = (int) (totalHeight * rate3);

        color1.getLayoutParams().height = height1;
        color2.getLayoutParams().height = height2;
        color3.getLayoutParams().height = height3;

        people1.setText(p1 + "人");
        people2.setText(p2 + "人");
        people3.setText(p3 + "人");

        rate.setText("" + (rate1 * 100));
    }

}
