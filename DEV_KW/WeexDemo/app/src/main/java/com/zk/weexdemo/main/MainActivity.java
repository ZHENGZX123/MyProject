package com.zk.weexdemo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zk.weexdemo.content.ContentActivity;
import com.zk.weexdemo.main.bean.Module;
import com.zk.weexdemo.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private MyAdapter adapter;
    private ArrayList<Module> modules = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Module m = modules.get(position);
                m.jsName = "" + position;//TODO
                startActivity(new Intent(MainActivity.this, ContentActivity.class).putExtra("module", m));
            }
        });
    }

    public void clickAdd(View v) {
        modules.add(new Module());
        adapter.notifyDataSetChanged();
    }

    public void clickDelete(View v) {
        if (modules.size() > 0) {
            modules.remove(modules.size() - 1);
            adapter.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return modules.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_gridview, null);
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Module m = modules.get(position);
            holder.title.setText("模块" + position);
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public final class ViewHolder {
            public TextView title;
        }
    }

}
