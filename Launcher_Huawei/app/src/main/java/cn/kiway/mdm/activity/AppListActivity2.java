package cn.kiway.mdm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import cn.kiway.mdm.R;
import cn.kiway.mdm.dialog.CheckPassword;
import cn.kiway.mdm.dialog.ShowMessageDailog;
import cn.kiway.mdm.dialog.TimeSelectDailog;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.AppListUtils;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.Constant.otherApps;


public class AppListActivity2 extends BaseActivity implements CheckPassword.CheckPasswordCall {

    private GridView gv;
    private ArrayList<App> apps = new ArrayList<>();
    private MyAdapter adapter;
    TimeSelectDailog dailog;
    ShowMessageDailog showDialog;
    CheckPassword checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        gv = (GridView) findViewById(R.id.gv);
        dailog = new TimeSelectDailog(this);
        showDialog = new ShowMessageDailog(this);
        checkPassword = new CheckPassword(this, this);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    clickButton1(view);
                    return;
                }

                try {
                    App a = apps.get(position - 1);
                    String packageName = a.packageName;
                    Log.d("test", "packageName = " + packageName);
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
                    if (AppListUtils.isCheckAppTime(AppListActivity2.this, packageName)) {//判断是否处于使用时间
                        showDialog.setShowMessage(a.name + "的使用时间为" + getSharedPreferences("kiway", 0).getString
                                (packageName, "") + "时，目前该时间段无法使用");
                        showDialog.show();
                        return;
                    }
                    Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("启动异常");
                }
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0)
                    return false;
                App a = apps.get(position - 1);
                dailog.setPackageName(a.packageName);
                dailog.show();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setOtherApps();

        String selectedApp = getSharedPreferences("kiway", 0).getString("apps", "");
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

    private void setOtherApps() {
        String selectedApp = getSharedPreferences("kiway", 0).getString("apps", "");
        if (TextUtils.isEmpty(selectedApp)) {
            return;
        }
        otherApps.clear();
        String[] splits = selectedApp.substring(0, selectedApp.length() - 1).split(",");
        for (String s : splits) {
            App a = Utils.getAppByPackageName(getPackageManager(), s);
            if (a != null) {
                otherApps.add(a);
            }
        }
        Log.d("test", "setOtherApps , count = " + otherApps.size());
    }

    public void clickButton1(View view) {
        checkPassword.setView(null, 0);
        checkPassword.setCancelable(true);
        checkPassword.setTitle("请输入密码");
        checkPassword.show();
    }

    @Override
    public void success(View vx, int position) throws Exception {
        if (position == 0) {
            startActivity(new Intent(AppListActivity2.this, AppListActivity3.class));
        }
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
            if (position == 0) {
                holder.name.setText("添加应用");
                holder.iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
            } else {
                App app = apps.get(position - 1);
                holder.name.setText(app.name);
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
            return apps.size() + 1;
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
