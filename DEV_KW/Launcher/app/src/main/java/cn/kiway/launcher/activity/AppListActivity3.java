package cn.kiway.launcher.activity;

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

import cn.kiway.launcher.R;
import cn.kiway.launcher.entity.App;
import cn.kiway.launcher.utils.Utils;

import static cn.kiway.launcher.R.id.cb;
import static cn.kiway.launcher.utils.Constant.otherApps;

public class AppListActivity3 extends BaseActivity {

    private ListView lv;
    private ArrayList<App> apps = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list3);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App a = apps.get(position);
                a.selected = !a.selected;
                adapter.notifyDataSetChanged();
            }
        });
        apps = Utils.scanLocalInstallAppList(getPackageManager());
        adapter.notifyDataSetChanged();
    }

    public void clickButton1(View view) {
        int count = 0;
        for (App a : apps) {
            if (a.selected) {
                count++;
            }
        }
        if (count == 0) {
            toast("请至少选择一个");
            return;
        }
        String temp = "";
        otherApps.clear();
        for (int i = 0; i < apps.size(); i++) {
            App a = apps.get(i);
            if (a.selected) {
                otherApps.add(a);
                temp += a.packageName + ",";
            }
        }
        //1.存入db
        getSharedPreferences("kiway", 0).edit().putString("app", temp).commit();
        //2.apps
        finish();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(AppListActivity3.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_list_app, null);
                holder = new ViewHolder();

                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.cb = (ImageView) rowView.findViewById(cb);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            App app = apps.get(position);
            holder.name.setText(app.name);
            if (app.selected) {
                holder.cb.setVisibility(View.VISIBLE);
            } else {
                holder.cb.setVisibility(View.INVISIBLE);
            }
            holder.iv.setImageDrawable(app.icon);

            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
            public TextView name;
            public ImageView cb;
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public App getItem(int arg0) {
            return apps.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

}
