package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;

import cn.kiway.mdm.entity.AttendItem;
import cn.kiway.mdm.entity.KnowledgeCountResult;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.UserExaminationResultVo;
import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2018/1/17.
 */
//统计:知识点没接口、测评有接口
public class TongjiActivity extends BaseActivity {

    private int type;//1知识点统计 2问题统计

    private TextView hint;
    private TextView content;

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

    private TextView indexTV;
    private LinearLayout chartLL;
    private RelativeLayout bottomRL;

    private AttendItem item;

    private int current = 0;//当前显示项

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji);

        type = getIntent().getIntExtra("type", 1);
        item = (AttendItem) getIntent().getSerializableExtra("item");

        initView();
        refresh();
    }

    private void refresh() {
        int p1 = 0;
        int p2 = 0;
        int p3 = 0;
        if (type == 1) {
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
            indexTV.setText((current + 1) + "/" + item.questions.size());
            Question q = item.questions.get(current);
            if (q.type == Question.TYPE_SINGLE) {
                content.setText("单选题");
            } else if (q.type == Question.TYPE_MULTI) {
                content.setText("多选题");
            } else if (q.type == Question.TYPE_EMPTY) {
                content.setText("填空题");
            } else if (q.type == Question.TYPE_JUDGE) {
                content.setText("判断题");
            } else if (q.type == Question.TYPE_ESSAY) {
                content.setText("问答题");
            }
            for (UserExaminationResultVo vo : q.userExaminationResultVos) {
                if (vo.status == 0) {
                    p2++;
                } else if (vo.status == 1) {
                    p1++;
                } else if (vo.status == 2) {
                    p3++;
                }
            }
            setChartData(p1, p2, p3);
        }
    }

    @Override
    public void initView() {
        super.initView();

        rate = (TextView) findViewById(R.id.rate);
        people1 = (TextView) findViewById(R.id.people1);
        people2 = (TextView) findViewById(R.id.people2);
        people3 = (TextView) findViewById(R.id.people3);

        group = (TextView) findViewById(R.id.group);
        group1 = (TextView) findViewById(R.id.group1);
        group2 = (TextView) findViewById(R.id.group2);
        group3 = (TextView) findViewById(R.id.group3);

        indexTV = (TextView) findViewById(R.id.indexTV);
        bottomRL = (RelativeLayout) findViewById(R.id.bottomRL);
        chartLL = (LinearLayout) findViewById(R.id.chartLL);

        hint = (TextView) findViewById(R.id.hint);
        content = (TextView) findViewById(R.id.content);

        if (type == 1) {
            group.setText("已掌握");
            group1.setText("已掌握");
            group2.setText("未掌握");
            group3.setText("未响应");
            bottomRL.setVisibility(View.GONE);
            hint.setText("知识点统计：");
            content.setText(item.results.get(0).knowledgeName);
            titleName.setText("知识点统计结果");
        } else if (type == 2) {
            group.setText("正确率");
            group1.setText("回答正确");
            group2.setText("回答错误");
            group3.setText("未作答");
            hint.setText("题目类型：");
            titleName.setText("测评统计结果");
        }

        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
    }

    private void setChartData(int p1, int p2, int p3) {
        int total = p1 + p2 + p3;
        float rate1 = (float) p1 / total;
        float rate2 = (float) p2 / total;
        float rate3 = (float) p3 / total;

        int diff = 150;
        if (type == 2) {
            diff = 220;
        }
        int totalHeight = (DensityUtil.getScreenHeight() - DensityUtil.dip2px(diff)) / 2; //DensityUtil.dip2px(100);//高度最好这样计算：总高度-上下栏
        int height1 = (int) (totalHeight * rate1);
        int height2 = (int) (totalHeight * rate2);
        int height3 = (int) (totalHeight * rate3);

        color1.getLayoutParams().height = (height1 == 0 ? 1 : height1);
        color2.getLayoutParams().height = (height2 == 0 ? 1 : height2);
        color3.getLayoutParams().height = (height3 == 0 ? 1 : height3);

        people1.setText(p1 + "人");
        people2.setText(p2 + "人");
        people3.setText(p3 + "人");

        rate.setText(String.format("%.2f", rate1 * 100) + "%");
    }

    public void clickPrev(View v) {
        if (current == 0) {
            toast("没有上一个");
            return;
        }
        current--;
        refresh();
    }

    public void clickNext(View v) {
        if (current == item.questions.size() - 1) {
            toast("没有下一个");
            return;
        }
        current++;
        refresh();
    }

}
