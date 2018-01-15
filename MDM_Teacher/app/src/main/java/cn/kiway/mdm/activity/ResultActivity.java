package cn.kiway.mdm.activity;

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


/**
 * Created by Administrator on 2017/12/29.
 */

public class ResultActivity extends BaseActivity {

    private int type;
    private RelativeLayout type12RL;
    private RelativeLayout type3RL;

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Student> students = new ArrayList<>();

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

        if (type == 1) {
            titleName.setText("抢答结果");
            type12RL.setVisibility(View.VISIBLE);
            type3RL.setVisibility(View.GONE);
        } else if (type == 2) {
            titleName.setText("抽答结果");
            type12RL.setVisibility(View.VISIBLE);
            type3RL.setVisibility(View.GONE);
        } else if (type == 3) {
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
}