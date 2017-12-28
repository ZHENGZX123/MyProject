package cn.kiway.mdm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.teacher.R;


public class CourseListActivity extends BaseActivity {


    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);

        initView();
        initData();
        iniListener();
    }

    public void initView() {
        super.initView();
        titleName.setText("上课");
        lv = (ListView) findViewById(R.id.courseLV);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void iniListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course c = courses.get(position);
                if (c.status == 0) {
                    //未上课
                    startActivity(new Intent(CourseListActivity.this, Course0Activity.class));
                } else if (c.status == 2) {
                    //已上课
                    startActivity(new Intent(CourseListActivity.this, Course2Activity.class));
                }
            }
        });
    }

    private void initData() {
        Course c1 = new Course();
        c1.title1 = "1.1 正数和负数、数轴";
        c1.title2 = "知识点1：用正数、负数表示具有相反意义的事\n知识点2：数轴的概念及画法";
        c1.status = 0;
        Course c2 = new Course();
        c2.title1 = "1.2 绝对值";
        c2.title2 = "知识点1：绝对值的意义";
        c2.status = 2;

        courses.add(c1);
        courses.add(c2);
        adapter.notifyDataSetChanged();
    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(CourseListActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_course, null);
                holder = new ViewHolder();

                holder.title1 = (TextView) rowView.findViewById(R.id.title1);
                holder.title2 = (TextView) rowView.findViewById(R.id.title2);
                holder.yishangke = (ImageView) rowView.findViewById(R.id.yishangke);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final Course s = courses.get(position);
            if (s.status == 0) {
                holder.title1.setTextColor(Color.parseColor("#6699ff"));
                holder.title2.setTextColor(Color.parseColor("#6699ff"));
                holder.yishangke.setVisibility(View.GONE);
            } else {
                holder.title1.setTextColor(Color.parseColor("#cccccc"));
                holder.title2.setTextColor(Color.parseColor("#cccccc"));
                holder.yishangke.setVisibility(View.VISIBLE);
            }
            holder.title1.setText(s.title1);
            holder.title2.setText(s.title2);
            return rowView;
        }

        public class ViewHolder {
            public TextView title1;
            public TextView title2;
            public ImageView yishangke;
        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Course getItem(int arg0) {
            return courses.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

}
