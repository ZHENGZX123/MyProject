package cn.kiway.launcher.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.launcher.R;
import cn.kiway.launcher.entity.App;
import cn.kiway.launcher.utils.Utils;

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
                    e.printStackTrace();
                    toast("启动异常");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String selectedApp = getSharedPreferences("kiway", 0).getString("app", "");
        if (TextUtils.isEmpty(selectedApp)) {
            return;
        }
        apps.clear();
        String[] splits = selectedApp.substring(0, selectedApp.length() - 1).split(",");
        for (String s : splits) {
            App a = Utils.getAppByPackageName(getPackageManager(), s);
            if (a != null) {
                apps.add(a);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void clickButton1(View view) {
        final EditText et = new EditText(this);
        et.setText("123456");
        et.setSingleLine();
        new AlertDialog.Builder(this).setTitle("请输入密码").setIcon(
                android.R.drawable.ic_dialog_info).setView(et
        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = et.getText().toString();
                if (input.equals("")) {
                    toast("密码不能为空");
                    return;
                }
                if (!input.equals("123456")) {
                    toast("密码错误");
                    return;
                }

                startActivity(new Intent(AppListActivity2.this, AppListActivity3.class));
            }
        }).show();
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
