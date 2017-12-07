package cn.kiway.mdm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import cn.kiway.mdm.db.MyDBHelper;
import cn.kiway.mdm.utils.FileUtils;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/11/16.
 */

public class FileListActivity extends BaseActivity {
    ListView listView;
    JSONArray array = new JSONArray();
    FileAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        listView = (ListView) findViewById(R.id.list_view);
        array = new MyDBHelper(this).getFile();
        adapter = new FileAdapter(this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileUtils.openFile(FileListActivity.this, array.optJSONObject(i).optString("filepath"));
            }
        });
    }

    public void Before(View view) {
        finish();
    }

    public void refreshUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                array = new MyDBHelper(FileListActivity.this).getNotifyMessage();
                adapter.notifyDataSetChanged();
            }
        });
    }

    public class FileAdapter extends ArrayAdapter {
        FileHolder holder;

        public FileAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                holder = new FileHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_file, null);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                convertView.setTag(holder);
            } else {
                holder = (FileHolder) convertView.getTag();
            }
            holder.name.setText(array.optJSONObject(position).optString("filename"));
            holder.time.setText(Utils.getDateField(Long.parseLong(array.optJSONObject(position).optString("time")), 9));
            return convertView;
        }

        @Override
        public int getCount() {
            return array.length();
        }

        public class FileHolder {
            TextView name, time;
        }
    }


}
