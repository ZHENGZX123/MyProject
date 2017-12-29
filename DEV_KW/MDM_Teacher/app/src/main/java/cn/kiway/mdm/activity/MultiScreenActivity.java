package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;


//选学生的页面，要不要都合成同一个？
public class MultiScreenActivity extends BaseActivity {

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_screen);

        initView();
        initData();
        initListener();
    }

    public void initView() {
        super.initView();

        titleName.setText("多屏查看");

        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            Student s = new Student();
            s.name = "学生" + i;
            students.add(s);
        }
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = students.get(position);
                //查看具体学生的屏幕
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(MultiScreenActivity.this);
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
