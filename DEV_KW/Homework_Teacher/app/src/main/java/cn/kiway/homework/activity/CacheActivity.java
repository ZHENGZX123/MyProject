package cn.kiway.homework.activity;

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

import cn.kiway.homework.entity.Resource;
import cn.kiway.homework.teacher.R;

public class CacheActivity extends BaseActivity {

    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<Resource> Resources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);

        //假数据
        Resource s1 = new Resource();
        s1.name = "grade1";
        Resources.add(s1);
        Resource s2 = new Resource();
        s2.name = "grade2";
        Resources.add(s2);
        Resource s3 = new Resource();
        s3.name = "grade3";
        Resources.add(s3);
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resource s = Resources.get(position);
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(CacheActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_resource, null);
                holder = new ViewHolder();

                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                holder.name = (TextView) rowView.findViewById(R.id.name);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            Resource s = Resources.get(position);
            holder.name.setText(s.name);
            holder.iv.setImageResource(R.mipmap.ic_launcher);

            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
            public TextView name;
        }

        @Override
        public int getCount() {
            return Resources.size();
        }

        @Override
        public Resource getItem(int arg0) {
            return Resources.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
