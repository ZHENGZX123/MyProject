package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.AnswerVo;
import cn.kiway.mdm.entity.Choice;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;

import static cn.kiway.mdm.util.Utils.showBigImage;

/**
 * Created by Administrator on 2018/1/16.
 */

public class ResultDetailActivity extends BaseActivity {

    private Student student;
    private ArrayList<Question> questions;
    private Button prev;
    private Button next;
    private int current = 0;
    private TextView type;
    private TextView content;
    private TextView content2;
    private LinearLayout imgLL;
    private LinearLayout imgLL2;
    private LinearLayout ll_answer;
    private GridView answerGV;
    private TextView answerTV;
    private ImageView answerIV;
    private MyAdapter adapter;
    private ArrayList<Choice> choices = new ArrayList<>();
    private ImageButton right;
    private ImageButton wrong;
    private Button ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        student = (Student) getIntent().getSerializableExtra("student");
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");

        initView();
        refresh();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText(student.name + "的答题结果反馈");

        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        type = (TextView) findViewById(R.id.type);
        content = (TextView) findViewById(R.id.content);
        content2 = (TextView) findViewById(R.id.content2);
        imgLL = (LinearLayout) findViewById(R.id.imgLL);
        imgLL2 = (LinearLayout) findViewById(R.id.imgLL2);
        ll_answer = (LinearLayout) findViewById(R.id.ll_answer);
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);

        answerGV = (GridView) findViewById(R.id.answerGV);
        answerTV = (TextView) findViewById(R.id.answerTV);
        answerIV = (ImageView) findViewById(R.id.answerIV);
        adapter = new MyAdapter();
        answerGV.setAdapter(adapter);

        if (!student.collected) {
            ok = (Button) findViewById(R.id.ok);
            ok.setText("批改");
            ok.setVisibility(View.VISIBLE);
        }
    }

    public void prev(View view) {
        current--;
        refresh();
    }

    public void next(View view) {
        current++;
        refresh();
    }

    public void showAnswer(View view) {
        if (ll_answer.isShown()) {
            ll_answer.setVisibility(View.GONE);
        } else {
            ll_answer.setVisibility(View.VISIBLE);
        }
    }

    private void refresh() {
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        if (current == 0) {
            prev.setVisibility(View.GONE);
        }
        if (current == questions.size() - 1) {
            next.setVisibility(View.GONE);
        }

        //问题区域
        Question q = questions.get(current);
        if (q.type == Question.TYPE_SINGLE) {
            type.setText("单选题");
        } else if (q.type == Question.TYPE_MULTI) {
            type.setText("多选题");
        } else if (q.type == Question.TYPE_EMPTY) {
            type.setText("填空题");
        } else if (q.type == Question.TYPE_JUDGE) {
            type.setText("判断题");
        } else if (q.type == Question.TYPE_ESSAY) {
            type.setText("问答题");
        }
        content.setText(q.content);
        imgLL.removeAllViews();
        if (!TextUtils.isEmpty(q.img)) {
            String imgs[] = q.img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBigImage(ResultDetailActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL.addView(iv, lp);
            }
        }
        //标准答案区域
        AnswerVo a = q.answerVo;
        content2.setText(a.content);
        imgLL2.removeAllViews();
        if (!TextUtils.isEmpty(a.img)) {
            String imgs[] = a.img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBigImage(ResultDetailActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL2.addView(iv, lp);
            }
        }

        //reset
        right.setBackgroundResource(R.drawable.right1);
        wrong.setBackgroundResource(R.drawable.wrong1);

        String studentAnswer = q.studentAnswer;
        //学生提交答案区域
        if (q.type == Question.TYPE_SINGLE || q.type == Question.TYPE_MULTI) {
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
            choices.clear();
            String choose[] = q.options.replace("\"", "").replace("[", "").replace("]", "").split(",");
            for (String temp : choose) {
                if (studentAnswer.contains(temp)) {
                    choices.add(new Choice(temp, true));
                } else {
                    choices.add(new Choice(temp, false));
                }
            }
            adapter.notifyDataSetChanged();
            //自动批改
            if (q.type == Question.TYPE_SINGLE) {
                if (q.answerVo.content.contains(studentAnswer)) {
                    right.setBackgroundResource(R.drawable.right2);
                    wrong.setBackgroundResource(R.drawable.wrong1);
                    q.teacherJudge = 2;
                } else {
                    right.setBackgroundResource(R.drawable.right1);
                    wrong.setBackgroundResource(R.drawable.wrong2);
                    q.teacherJudge = 1;
                }
            } else if (q.type == Question.TYPE_MULTI) {
                if (q.answerVo.content.replace("[", "").replace("]", "").replace("\"", "").replace(",", "").equals(studentAnswer)) {
                    right.setBackgroundResource(R.drawable.right2);
                    wrong.setBackgroundResource(R.drawable.wrong1);
                    q.teacherJudge = 2;
                } else {
                    right.setBackgroundResource(R.drawable.right1);
                    wrong.setBackgroundResource(R.drawable.wrong2);
                    q.teacherJudge = 1;
                }
            }
        } else if (q.type == Question.TYPE_EMPTY) {
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.VISIBLE);
            answerIV.setVisibility(View.GONE);
            answerTV.setText(studentAnswer);
            //老师来批改
        } else if (q.type == Question.TYPE_JUDGE) {
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
            choices.clear();
            if (studentAnswer.contains("对")) {
                choices.add(new Choice("对", true));
                choices.add(new Choice("错", false));
            } else if (studentAnswer.contains("错")) {
                choices.add(new Choice("对", false));
                choices.add(new Choice("错", true));
            } else {
                choices.add(new Choice("对", false));
                choices.add(new Choice("错", false));
            }
            adapter.notifyDataSetChanged();
            //自动批改
            if (q.answerVo.content.contains(studentAnswer)) {
                right.setBackgroundResource(R.drawable.right2);
                wrong.setBackgroundResource(R.drawable.wrong1);
                q.teacherJudge = 2;
            } else {
                right.setBackgroundResource(R.drawable.right1);
                wrong.setBackgroundResource(R.drawable.wrong2);
                q.teacherJudge = 1;
            }
        } else if (q.type == Question.TYPE_ESSAY) {
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(q.studentAnswer, answerIV, KWApplication.getLoaderOptions());
            answerIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.showBigImage(ResultDetailActivity.this, new String[]{q.studentAnswer}, 0);
                }
            });
            //老师来批改
        }

        //如果已经批改过了一次，直接赋值就好了。。。
        if (student.collected) {
            //利用collection给right和wrong赋值
            try {
                JSONArray array = new JSONArray(student.collection);
                JSONObject o = array.getJSONObject(current);
                int teacherJudge = o.getInt("qcollection");
                if (teacherJudge == 2) {
                    right.setBackgroundResource(R.drawable.right2);
                    wrong.setBackgroundResource(R.drawable.wrong1);
                } else if (teacherJudge == 1) {
                    right.setBackgroundResource(R.drawable.right1);
                    wrong.setBackgroundResource(R.drawable.wrong2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void right(View view) {
        if (student.collected) {
            return;
        }
        Question q = questions.get(current);
        if (q.type != Question.TYPE_ESSAY && q.type != Question.TYPE_EMPTY) {
            toast("只有填空题、问答题才需要老师批改");
            return;
        }
        right.setBackgroundResource(R.drawable.right2);
        wrong.setBackgroundResource(R.drawable.wrong1);
        q.teacherJudge = 2;
    }

    public void wrong(View view) {
        if (student.collected) {
            return;
        }
        Question q = questions.get(current);
        if (q.type != Question.TYPE_ESSAY && q.type != Question.TYPE_EMPTY) {
            toast("只有填空题、问答题才需要老师批改");
            return;
        }
        right.setBackgroundResource(R.drawable.right1);
        wrong.setBackgroundResource(R.drawable.wrong2);
        q.teacherJudge = 1;
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        if (student.collected) {
            finish();
            return;
        }
        for (Question q : questions) {
            if (q.teacherJudge == 0) {
                toast("该学生的答案有些未批改，请先批改");
                return;
            }
        }
    }

    public void clickOK(View view) {
        for (Question q : questions) {
            if (q.teacherJudge == 0) {
                toast("该学生的答案有些未批改，请先批改");
                return;
            }
        }
        try {
            JSONArray array = new JSONArray();
            for (Question q : questions) {
                JSONObject o = new JSONObject();
                o.put("qid", q.id);
                o.put("qtype", q.type);
                o.put("qcollection", q.teacherJudge);
                array.put(o);
            }
            //把批改的结果发给学生。
            ZbusHost.collection(ResultDetailActivity.this, student, array.toString(), new OnListener() {
                @Override
                public void onSuccess() {
                    toast("发送批改结果成功");
                    Intent in = new Intent();
                    in.putExtra("collected", true);
                    in.putExtra("studentIMEI", student.imei);
                    in.putExtra("collection", array.toString());
                    setResult(8888, in);
                    finish();
                }

                @Override
                public void onFailure() {
                    toast("发送批改结果失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(ResultDetailActivity.this);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_answer, null);
                holder = new ViewHolder();

                holder.choose = (TextView) rowView.findViewById(R.id.choose);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            Choice c = choices.get(position);
            holder.choose.setText(c.content);
            if (c.selected) {
                holder.choose.setBackgroundResource(R.drawable.green);
            } else {
                holder.choose.setBackgroundResource(R.drawable.gray);
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView choose;
        }

        @Override
        public int getCount() {
            return choices.size();
        }

        @Override
        public Choice getItem(int arg0) {
            return choices.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
