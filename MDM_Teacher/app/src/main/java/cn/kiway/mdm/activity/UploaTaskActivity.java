package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.entity.UploadTask;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.MyDBHelper;

import static cn.kiway.mdm.teacher.R.id.status;


/**
 * Created by Administrator on 2018/3/12.
 */

public class UploaTaskActivity extends BaseActivity {

    private Course course;
    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<UploadTask> tasks = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadtask);

        course = (Course) getIntent().getSerializableExtra("course");

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText(course.name + "的上传任务");

        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);

        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tasks = new MyDBHelper(UploaTaskActivity.this).getTasksByCourseID(course.id);
            adapter.notifyDataSetChanged();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(UploaTaskActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_task, null);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.status = (TextView) rowView.findViewById(status);
                holder.pb = (ProgressBar) rowView.findViewById(R.id.pb);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            UploadTask ut = tasks.get(position);
            holder.title.setText("第" + (position + 1) + "部分");
            if (ut.status == UploadTask.STATUS_START || ut.status == UploadTask.STATUS_UPLOADING) {
                holder.status.setText("已上传" + ut.progress + "%");
            } else {
                holder.status.setText("上传完成");
            }
            holder.pb.setProgress(ut.progress);

            return rowView;
        }

        public class ViewHolder {
            public TextView title;
            public TextView status;
            public ProgressBar pb;
        }

        @Override
        public int getCount() {
            return tasks.size();
        }

        @Override
        public UploadTask getItem(int arg0) {
            return tasks.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
