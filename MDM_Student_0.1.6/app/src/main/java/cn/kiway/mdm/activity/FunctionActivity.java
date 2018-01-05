package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Function;

/**
 * Created by Administrator on 2017/11/10.
 */

public class FunctionActivity extends BaseActivity {

    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<Function> functions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        lv1 = (ListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);
    }

    public void Before(View v) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        ++
        adapter1.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(FunctionActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_function, null);
                holder = new ViewHolder();
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.enable = (Button) rowView.findViewById(R.id.enable);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final Function s = functions.get(position);
            holder.name.setText(s.name);

            holder.enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (s.enable) {

                    } else {

                    }
                }
            });
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public Button enable;
        }

        @Override
        public int getCount() {
            return functions.size();
        }

        @Override
        public Function getItem(int arg0) {
            return functions.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
