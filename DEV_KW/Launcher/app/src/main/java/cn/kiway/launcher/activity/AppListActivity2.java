package cn.kiway.launcher.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.launcher.R;
import cn.kiway.launcher.entity.App;

public class AppListActivity2 extends BaseActivity {

    private GridView gv;
    private ArrayList<App> apps = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        gv = (GridView) findViewById(R.id.gv);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Uri uri2 = Uri.parse("smsto:");
                    Intent intentMessage = new Intent(Intent.ACTION_VIEW, uri2);
                    startActivity(intentMessage);
                }
                
                if (true) {
                    return;
                }
                //跳转到其他APK
                try {
                    App a = apps.get(position);
                    String packageName = a.packageName;
                    if (TextUtils.isEmpty(packageName)) {
                        toast("包名错误");
                        return;
                    }
                    //1.判断app是否安装
                    boolean installed = isAppInstalled(getApplicationContext(), packageName);
                    if (!installed) {
                        toast("该APP未安装");
                        return;
                    }
                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(intent);
                } catch (Exception e) {
                    toast("启动异常");
                }
            }
        });

        App a1 = new App();
        a1.name = "电话";
        a1.packageName = "";
        apps.add(a1);
        App a2 = new App();
        a2.name = "短信";
        a2.packageName = "";
        apps.add(a2);
        adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(AppListActivity2.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_grid_app, null);
                holder = new ViewHolder();

                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                holder.name = (TextView) rowView.findViewById(R.id.name);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            App app = apps.get(position);
            holder.name.setText(app.name);

            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
            public TextView name;
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

    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }
}
