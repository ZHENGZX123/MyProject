package cn.kiway.mdm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import cn.kiway.mdm.R;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/11/24.
 */

public class NotifyMsgActivity extends BaseActivity {
    JSONArray array = new JSONArray();
    ListView listView;
    NotifyMsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        ((TextView) findViewById(R.id.title)).setText("消息列表");
        array = new MyDBHelper(this).getNotifyMessage();
        listView = (ListView) findViewById(R.id.list_view);
        listView.setDividerHeight(0);
        adapter = new NotifyMsgAdapter(this);
        listView.setAdapter(adapter);
    }

    public void refreshUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                array = new MyDBHelper(NotifyMsgActivity.this).getNotifyMessage();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void Before(View v) {
        finish();
    }

    public class NotifyMsgAdapter extends ArrayAdapter {
        NotifyHolder holder;

        public NotifyMsgAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notify_show, null);
                holder = new NotifyHolder();
                holder.message = (TextView) convertView.findViewById(R.id.message);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.sendName = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (NotifyHolder) convertView.getTag();
            }
            holder.sendName.setText(array.optJSONObject(position).optString("sendname")+ "\n" + Utils.getDateField(Long
                    .parseLong(array.optJSONObject(position).optString("time")), 9));
            holder.message.setText(array.optJSONObject(position).optString("message"));
            holder.title.setText(array.optJSONObject(position).optString("title") );

            return convertView;
        }

        class NotifyHolder {
            TextView title, message, sendName;
        }

        @Override
        public int getCount() {
            return array.length();
        }
    }
}
