package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import cn.kiway.mdm.entity.SMS;
import cn.kiway.mdm.utils.MyDBHelper;

public class ComposeSmsActivity extends BaseActivity {


    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<SMS> SMSs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_sms);

        lv1 = (ListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //点击回复短信
                SMS s = SMSs.get(position);
                Intent i = new Intent(ComposeSmsActivity.this, SendSMSActivity.class);
                i.putExtra("phone", s.phone);
                i.putExtra("name", s.name);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        SMSs.clear();
        ArrayList<SMS> temp = new MyDBHelper(this).getAllSMS(null);
        for (SMS s : temp) {
            if (!SMSs.toString().contains(s.phone)) {
                SMSs.add(s);
            }
        }
        for (SMS s : SMSs) {
            Call call = new MyDBHelper(this).getCallByPhone(s.phone);
            if (call != null) {
                s.name = call.name;
            }
        }
        adapter1.notifyDataSetChanged();
    }

    public void refresh() {
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
            inflater = LayoutInflater.from(ComposeSmsActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_sms, null);
                holder = new ViewHolder();
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.content = (TextView) rowView.findViewById(R.id.content);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final SMS s = SMSs.get(position);

            if (TextUtils.isEmpty(s.name)) {
                holder.name.setText(s.phone);
            } else {
                holder.name.setText(s.name);
            }
            holder.content.setText(s.content);
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public TextView content;
        }

        @Override
        public int getCount() {
            return SMSs.size();
        }

        @Override
        public SMS getItem(int arg0) {
            return SMSs.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
