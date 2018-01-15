package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_0;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_1;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_2;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_3;


/**
 * Created by Administrator on 2017/12/29.
 */

//问答反馈
public class ResultActivity extends BaseActivity {

    private int type;
    private RelativeLayout type12RL;
    private RelativeLayout type3RL;

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Student> students = new ArrayList<>();

    private boolean finished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        type = getIntent().getIntExtra("type", 1);

        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();

        type12RL = (RelativeLayout) findViewById(R.id.type12RL);
        type3RL = (RelativeLayout) findViewById(R.id.type3RL);

        if (type == TYPE_QUESTION_0) {
            titleName.setText("点名答结果");
            type12RL.setVisibility(View.VISIBLE);
            type3RL.setVisibility(View.GONE);
        } else if (type == TYPE_QUESTION_1) {
            titleName.setText("抢答结果");
            type12RL.setVisibility(View.VISIBLE);
            type3RL.setVisibility(View.GONE);
        } else if (type == TYPE_QUESTION_2) {
            titleName.setText("抽答结果");
            type12RL.setVisibility(View.VISIBLE);
            type3RL.setVisibility(View.GONE);
        } else if (type == TYPE_QUESTION_3) {
            titleName.setText("测评结果");
            type12RL.setVisibility(View.GONE);
            type3RL.setVisibility(View.VISIBLE);
        }

        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Student s = new Student();
            s.name = "学生" + i;
            students.add(s);
        }
        adapter.notifyDataSetChanged();
    }

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

            if (s.status == 0) {
                holder.icon.setImageResource(R.drawable.icon1);
            } else {
                holder.icon.setImageResource(R.drawable.icon2);
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

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        if (!finished) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setMessage("是否退出？")
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
}
