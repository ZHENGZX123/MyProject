package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.AnswerVo;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.teacher.R;

import static cn.kiway.mdm.util.Utils.showBigImage;

/**
 * Created by Administrator on 2018/1/16.
 */

public class ResultDetailActivity extends BaseActivity {

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
    private ArrayList<String> chooses = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);

        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");

        initView();
        refresh();
    }

    @Override
    public void initView() {
        super.initView();
        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        type = (TextView) findViewById(R.id.type);
        content = (TextView) findViewById(R.id.content);
        content2 = (TextView) findViewById(R.id.content2);
        imgLL = (LinearLayout) findViewById(R.id.imgLL);
        imgLL2 = (LinearLayout) findViewById(R.id.imgLL2);
        ll_answer = (LinearLayout) findViewById(R.id.ll_answer);

        answerGV = (GridView) findViewById(R.id.answerGV);
        answerTV = (TextView) findViewById(R.id.answerTV);
        answerIV = (ImageView) findViewById(R.id.answerIV);
        adapter = new MyAdapter();
        answerGV.setAdapter(adapter);
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
        //学生提交答案区域
        if (q.type == Question.TYPE_SINGLE || q.type == Question.TYPE_MULTI) {
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
            chooses.clear();
            chooses.add("A");
            chooses.add("B");
            chooses.add("C");
            chooses.add("D");
            adapter.notifyDataSetChanged();
        } else if (q.type == Question.TYPE_EMPTY) {
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.VISIBLE);
            answerIV.setVisibility(View.GONE);
        } else if (q.type == Question.TYPE_JUDGE) {
            answerGV.setVisibility(View.VISIBLE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.GONE);
            chooses.clear();
            chooses.add("对");
            chooses.add("错");
            adapter.notifyDataSetChanged();
        } else if (q.type == Question.TYPE_ESSAY) {
            answerGV.setVisibility(View.GONE);
            answerTV.setVisibility(View.GONE);
            answerIV.setVisibility(View.VISIBLE);
        }
    }

    public void right(View view) {
        Question q = questions.get(current);
        if (q.type != Question.TYPE_ESSAY) {
            toast("只有问题答题需要老师批改");
            return;
        }
    }

    public void wrong(View view) {
        Question q = questions.get(current);
        if (q.type != Question.TYPE_ESSAY) {
            toast("只有问题答题需要老师批改");
            return;
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

            String s = chooses.get(position);
            holder.choose.setText(s);

            return rowView;
        }

        public class ViewHolder {
            public TextView choose;
        }

        @Override
        public int getCount() {
            return chooses.size();
        }

        @Override
        public String getItem(int arg0) {
            return chooses.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
