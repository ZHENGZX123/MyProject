package cn.kiway.launcher.phone.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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

import cn.kiway.launcher.phone.R;
import cn.kiway.launcher.phone.entity.App;
import cn.kiway.launcher.phone.utils.Utils;


public class AppListActivity extends BaseActivity {

    private GridView gv;
    private ArrayList<App> apps = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);

        findViewById(R.id.button1).setVisibility(View.GONE);

        gv = (GridView) findViewById(R.id.gv);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
        a1.name = "幼教平台";
        a1.packageName = "cn.kiway.Yjptj";
        a1.icon = Utils.getIconByPackageName(getPackageManager(), a1);
        apps.add(a1);
        App a2 = new App();
        a2.name = "一起阅读";
        a2.packageName = "cn.kiway.yiqiyuedu";
        a2.icon = Utils.getIconByPackageName(getPackageManager(), a2);
        apps.add(a2);
        App a3 = new App();
        a3.name = "快乐作业";
        a3.packageName = "cn.kiway.klzy";
        a3.icon = Utils.getIconByPackageName(getPackageManager(), a3);
        apps.add(a3);
        App a4 = new App();
        a4.name = "宝安通";
        a4.packageName = "cn.kiway.baoantong_vue";
        a4.icon = Utils.getIconByPackageName(getPackageManager(), a4);
        apps.add(a4);
        App a5 = new App();
        a5.name = "家校通";
        a5.packageName = "";
        a5.icon = Utils.getIconByPackageName(getPackageManager(), a5);
        apps.add(a5);
        App a6 = new App();
        a6.name = "协同课堂";
        a6.packageName = "cn.kiway.txkt";
        a6.icon = Utils.getIconByPackageName(getPackageManager(), a6);
        apps.add(a6);
        App a7 = new App();
        a7.name = "童趣玩";
        a7.packageName = "";
        a7.icon = Utils.getIconByPackageName(getPackageManager(), a7);
        apps.add(a7);
        adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(AppListActivity.this);
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
            if (app.icon == null) {
                holder.iv.setImageResource(R.mipmap.ic_launcher);
            } else {
                holder.iv.setImageDrawable(app.icon);
            }

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
