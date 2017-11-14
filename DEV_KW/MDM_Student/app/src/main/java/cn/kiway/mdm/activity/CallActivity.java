package cn.kiway.mdm.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Call;
import cn.kiway.mdm.utils.MyDBHelper;

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

        initData();

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Call c = calls.get(i);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + c.phone));
                startActivity(intent);
            }
        });
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
        adapter1.notifyDataSetChanged();
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
