package com.android.kiway.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.kiway.entity.Call;
import com.android.kiway.utils.MyDBHelper;
import com.android.kiway.utils.Utils;
import com.android.launcher3.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/10.
 */

public class CallActivity extends BaseActivity {

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
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == calls.size() - 1) {
                    //紧急电话
                    startActivity(new Intent(CallActivity.this, UrgentCallActivity.class));
                } else {
                    Call c = calls.get(position);
                    Utils.call(CallActivity.this, c.phone);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        //获取去电，只要白名单
        calls.clear();
        ArrayList<Call> temp = new MyDBHelper(this).getAllCalls(2);
        Log.d("test", "temp = " + temp.toString());
        for (Call c : temp) {
            if (c.type == 1) {
                calls.add(c);
            }
        }

        //加上紧急号码
        Call c = new Call();
        c.name = "紧急电话";
        calls.add(c);

        adapter1.notifyDataSetChanged();
    }

    public void refreshUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(CallActivity.this);
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
