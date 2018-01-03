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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.entity.InStallAllApp;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.clientUrl;
import static cn.kiway.mdm.utils.AppReceiverIn.INSTALL_SUCCESS;
import static cn.kiway.mdm.utils.AppReceiverIn.PACKAGENAME;
import static cn.kiway.mdm.utils.AppReceiverIn.REMOVE_SUCCESS;
import static cn.kiway.mdm.utils.FileACache.ListFileName;


public class AppListActivity3 extends BaseActivity {

    private ListView lv;
    private ArrayList<InStallAllApp> apps = new ArrayList<>();
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
        getAppData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InStallAllApp a = apps.get(position);
                a.selected = !a.selected;
                Intent intent = new Intent();
                intent.putExtra(PACKAGENAME, a.packages);
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
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                InStallAllApp a = apps.get(position);
                Intent intent = new Intent(AppListActivity3.this, TimeSetActivity.class);
                intent.putExtra("app", a);
                startActivity(intent);
                return false;
            }
        });
        //apps = Utils.scanLocalInstallAppList(this, true);   //这里要过滤掉默认APP、网络APP
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
                holder.time = (TextView) rowView.findViewById(R.id.time);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            InStallAllApp app = apps.get(position);
            holder.name.setText(app.appName);
            if (app.record != null) {
                holder.time.setText("可用时段:" + app.getRecord().optString("startTime") + "-" + app.getRecord().optString
                        ("endTime"));
            } else {
                holder.time.setText("");
            }
            if (app.selected) {
                holder.cb.setVisibility(View.VISIBLE);
            } else {
                holder.cb.setVisibility(View.INVISIBLE);
            }
            holder.iv.setImageDrawable(Utils.getIconByPackageName(getPackageManager(), app.packages));
            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
            public TextView name, time;
            public ImageView cb;
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public InStallAllApp getItem(int arg0) {
            return apps.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    public String getTimeString(JSONArray array) throws JSONException {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < array.length(); i++) {
            String startTime = array.optJSONObject(i).getString("startTime");
            String endTime = array.optJSONObject(i).getString("endTime");
            if (i == 0) {
                buffer.append(startTime + "-" + endTime);
            } else {
                buffer.append(";" + startTime + "-" + endTime);
            }
        }
        return buffer.toString();
    }


    //下面获取app使用时间
    public void getAppData() {
        new Thread() {
            @Override
            public void run() {
                try {//+
                    HttpGet httpRequest = new HttpGet(clientUrl +
                            "device/parent/findAppInstallation?imei=" + Utils.getIMEI(AppListActivity3.this));
                    httpRequest.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    JSONObject data = new JSONObject(ret);
                    if (data.optInt("statusCode") == 200) {
                        JSONArray array = data.optJSONArray("data");
                        ArrayList<InStallAllApp> appsd = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            InStallAllApp inapp = new InStallAllApp();
                            JSONObject item = array.optJSONObject(i);
                            inapp.appName = item.optString("appName");
                            inapp.versionNum = item.optString("versionNum");
                            inapp.icon = item.optString("icon");
                            inapp.ids = item.optString("id");
                            inapp.packages = item.optString("packages");
                            inapp.versionName = item.optString("versionName");
                            inapp.category = item.optString("category");
                            inapp.createDate = item.optString("createDate");
                            if (item.optJSONObject("record") != null)
                                inapp.record = item.optJSONObject("record").toString();
                            inapp.flag = item.optString("flag");
                            inapp.imei = item.optString("imei");
                            if (!inapp.packages.equals("cn.kiway.mdm"))
                                appsd.add(inapp);
                        }
                        new MyDBHelper(AppListActivity3.this).deleteAllInstallApp();
                        new MyDBHelper(AppListActivity3.this).addInstallApp(appsd);
                        notifyView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                notifyView();
            }
        }.start();
    }

    private void notifyView() {
        apps = new MyDBHelper(this).getALLInStallAllApp();
        for (InStallAllApp a : apps) {//标识选中状态
            if (allListData.toString().contains(a.packages))
                a.selected = true;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        notifyView();
    }
}
