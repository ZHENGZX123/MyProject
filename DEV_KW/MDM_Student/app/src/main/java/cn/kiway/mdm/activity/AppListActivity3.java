package cn.kiway.mdm.activity;

import android.content.Intent;
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
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.AppReceiverIn.INSTALL_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.PACKAGENAME;
import static cn.kiway.mdm.utils.AppReceiverIn.REMOVE_SUCCESS;
import static cn.kiway.mdm.utils.FileACache.ListFileName;


public class AppListActivity3 extends BaseActivity {

    private ListView lv;
    private ArrayList<App> apps = new ArrayList<>();
    private MyAdapter adapter;
    public List<List<App>> allListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list3);
        lv = (ListView) findViewById(R.id.lv);
        allListData = new ArrayList(FileACache.loadListCache(this, ListFileName));
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App a = apps.get(position);
                a.selected = !a.selected;
                Intent intent = new Intent();
                intent.putExtra(PACKAGENAME, a.packageName);
                intent.putExtra("boolean", true);
                if (a.selected) {
                    intent.setAction(INSTALL_SUCCESS);
                } else {
                    intent.setAction(REMOVE_SUCCESS);
                }
                sendOrderedBroadcast(intent, null);
                adapter.notifyDataSetChanged();
            }
        });
//        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
//                App a = apps.get(position);
//                Intent intent=new Intent(AppListActivity3.this,TimeSetActivity.class);
//                intent.putExtra("app",a);
//                startActivity(intent);
//                return false;
//            }
//        });
        apps = Utils.scanLocalInstallAppList(this, true);   //这里要过滤掉默认APP、网络APP
        for (App a : apps) {//标识选中状态
            if (allListData.toString().contains(a.packageName))
                a.selected = true;
        }
        adapter.notifyDataSetChanged();
    }

    public void Before(View view) {
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
                holder.cb = (ImageView) rowView.findViewById(R.id.cb);
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
            holder.iv.setImageDrawable(Utils.getIconByPackageName(getPackageManager(), app.packageName));
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
