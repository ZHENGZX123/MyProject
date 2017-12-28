package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.teacher.R;


public class CourseListActivity extends BaseActivity {

    private TextView title;

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

    private void initView() {
        title = (TextView) findViewById(R.id.titleName);
        title.setText("上课");
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
        c1.title = "正数和负数";
        c1.status = 0;
        Course c2 = new Course();
        c2.title = "绝对值";
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

                holder.title = (TextView) rowView.findViewById(R.id.title);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Course s = courses.get(position);
            holder.title.setText(s.title);

            return rowView;
        }

        public class ViewHolder {
            public TextView title;
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
