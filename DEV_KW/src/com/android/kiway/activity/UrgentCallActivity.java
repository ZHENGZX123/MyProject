package com.android.kiway.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.kiway.entity.Call;
import com.android.kiway.utils.Utils;
import com.android.launcher3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/10.
 */

public class UrgentCallActivity extends BaseActivity {

    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<Call> calls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        lv1 = (ListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        initData();

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Call c = calls.get(position);
                Utils.call(UrgentCallActivity.this, c.phone);
            }
        });
    }

    private void initData() {
        calls.clear();
        //加上紧急号码
        Call c1 = new Call();
        c1.name = "火警";
        c1.phone = "119";
        calls.add(c1);

        Call c2 = new Call();
        c2.name = "报警";
        c2.phone = "110";
        calls.add(c2);

        Call c3 = new Call();
        c3.name = "急救";
        c3.phone = "120";
        calls.add(c3);

        adapter1.notifyDataSetChanged();
    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(UrgentCallActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_call, null);
                holder = new ViewHolder();
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.number = (TextView) rowView.findViewById(R.id.number);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final Call s = calls.get(position);
            holder.name.setText(s.name);
            holder.number.setText(s.phone);
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public TextView number;
        }

        @Override
        public int getCount() {
            return calls.size();
        }

        @Override
        public Call getItem(int arg0) {
            return calls.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
