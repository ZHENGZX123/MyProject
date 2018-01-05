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

import cn.kiway.mdm.KWApp;
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

        int flag_camera = getSharedPreferences("kiway", 0).getInt("flag_camera", 1);
        int flag_snapshot = getSharedPreferences("kiway", 0).getInt("flag_snapshot", 1);
        int flag_location = getSharedPreferences("kiway", 0).getInt("flag_location", 1);
        int flag_dataconnectivity = getSharedPreferences("kiway", 0).getInt("flag_dataconnectivity", 1);
        int flag_microphone = getSharedPreferences("kiway", 0).getInt("flag_microphone", 1);
        int flag_restore = getSharedPreferences("kiway", 0).getInt("flag_restore", 1);
        int flag_ap = getSharedPreferences("kiway", 0).getInt("flag_ap", 1);
        int flag_app_open = getSharedPreferences("kiway", 0).getInt("flag_app_open", 1);
        int flag_usb = getSharedPreferences("kiway", 0).getInt("flag_usb", 1);
        int flag_wifi = getSharedPreferences("kiway", 0).getInt("flag_allowWifi", 1);
        int flag_systemupdate = getSharedPreferences("kiway", 0).getInt("flag_systemupdate", 1);
        int flag_bluetooth = getSharedPreferences("kiway", 0).getInt("flag_bluetooth", 1);

        functions.add(new Function(1, "使用相机", flag_camera == 1));
        functions.add(new Function(2, "屏幕捕捉", flag_snapshot == 1));
        functions.add(new Function(3, "使用GPS", flag_location == 1));
        functions.add(new Function(4, "使用移动数据", flag_dataconnectivity == 1));
        functions.add(new Function(5, "使用麦克风", flag_microphone == 1));
        functions.add(new Function(6, "允许重置系统", flag_restore == 1));
        functions.add(new Function(7, "便携式热点", flag_ap == 1));
        functions.add(new Function(8, "应用打开", flag_app_open == 1));
        functions.add(new Function(9, "开发者选项", flag_usb == 1));
        functions.add(new Function(10, "允许WLAN", flag_wifi == 1));
        functions.add(new Function(11, "系统更新", flag_systemupdate == 1));
        functions.add(new Function(12, "允许使用蓝牙", flag_bluetooth == 1));

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
            if (s.enable) {
                holder.enable.setBackgroundResource(R.drawable.enable1);
            } else {
                holder.enable.setBackgroundResource(R.drawable.enable2);
            }

            holder.enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = s.id;
                    int value = 1;
                    if (s.enable) {
                        value = 0;
                    } else {
                        value = 1;
                    }
                    s.enable = !s.enable;
                    switch (id) {
                        case 1:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_camera", value).commit();
                            break;
                        case 2:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_snapshot", value).commit();
                            break;
                        case 3:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_location", value).commit();
                            break;
                        case 4:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_dataconnectivity", value).commit();
                            break;
                        case 5:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_microphone", value).commit();
                            break;
                        case 6:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_restore", value).commit();
                            break;
                        case 7:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_ap", value).commit();
                            break;
                        case 8:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_app_open", value).commit();
                            break;
                        case 9:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_usb", value).commit();
                            break;
                        case 10:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_allowWifi", value).commit();
                            break;
                        case 11:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_systemupdate", value).commit();
                            break;
                        case 12:
                            getSharedPreferences("kiway", 0).edit().putInt("flag_bluetooth", value).commit();
                            break;
                    }
                    KWApp.instance.excuteFlagCommand();
                    notifyDataSetChanged();
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
