package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_CEPING;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_QIANGDA;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_SUIJICHOUDA;


/**
 * Created by Administrator on 2017/12/29.
 */

//问答反馈、测评反馈用这个页面
public class ResultActivity extends BaseActivity {

    private int type;
    private LinearLayout unSubmitLL;
    private TextView time;
    private TextView hint;
    private TextView unSubmitCount;
    private TextView unSubmitName;

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Student> students;
    private ArrayList<Question> questions;
    private int questionTime;

    private boolean finished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        type = getIntent().getIntExtra("type", 1);
        students = (ArrayList<Student>) getIntent().getSerializableExtra("students");
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        questionTime = getIntent().getIntExtra("questionTime", 0);

        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();

        unSubmitLL = (LinearLayout) findViewById(R.id.unSubmitLL);
        time = (TextView) findViewById(R.id.time);
        hint = (TextView) findViewById(R.id.hint);
        unSubmitCount = (TextView) findViewById(R.id.unSubmitCount);
        unSubmitCount.setText("剩余" + students.size() + "人未作答");
        unSubmitName = (TextView) findViewById(R.id.unSubmitName);
        unSubmitName.setMovementMethod(new ScrollingMovementMethod());

        if (type == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
            unSubmitLL.setVisibility(View.VISIBLE);
            hint.setText("测评进行中......");
        }

        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = students.get(position);
//                if (!s.submit) {
//                    toast("该学生未提交答案");
//                    return;
//                }
                startActivity(new Intent(ResultActivity.this, ResultDetailActivity.class).putExtra("questions", questions).putExtra("student", s));
            }
        });
    }

    private void initData() {
        if (questionTime == 0) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                questionTime++;
            } else if (msg.what == 1) {
                if (questionTime > 0) {
                    questionTime--;
                } else {
                    finished = true;
                }
            }
            time.setText(Utils.secToTime(questionTime));
            mHandler.sendEmptyMessageDelayed(msg.what, 1000);
        }
    };

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(ResultActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.icon = (ImageView) rowView.findViewById(R.id.icon);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Student s = students.get(position);
            holder.name.setText(s.name);

            //绿色表示已答题提交，灰色表示还未作答的
            if (s.submit) {
                holder.icon.setImageResource(R.drawable.icon2);
            } else {
                holder.icon.setImageResource(R.drawable.icon1);
            }
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public ImageView icon;
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int arg0) {
            return students.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    public void stop(View view) {
        toast("停止答题");
        finished = true;
        questionTime = 0;
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        if (!finished) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage("是否退出本次问答/测评？")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    })
                    .setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
