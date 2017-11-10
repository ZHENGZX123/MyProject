package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Class;

import static cn.kiway.mdm.KWApp.server;

/**
 * Created by Administrator on 2017/10/17.
 */

public class ClassListActivity extends BaseActivity {

    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<Class> classes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classlist);

        lv1 = (ListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        initData();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Class s = classes.get(i);
                setResult(9999, new Intent().putExtra("class", s));
                finish();
            }
        });
    }

    private void initData() {
        String area = getIntent().getStringExtra("schoolId");
        //根据area获取学校列表
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = server + "common/class?schoolId=" + area;
            Log.d("test", "Class url = " + url);
            client.get(this, url, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    dismissPD();
                    Log.d("test", "Class onSuccess = " + ret);
                    try {
                        JSONArray data = new JSONObject(ret).getJSONArray("data");
                        classes = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Class>>() {
                        }.getType());
                        if (classes.size() == 0) {
                            toast("该学校没有班级");
                            return;
                        }
                        adapter1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    dismissPD();
                    Log.d("test", "Class onFailure = " + s);
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(ClassListActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_class, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final Class s = classes.get(position);
            holder.name.setText(s.name);
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
        }

        @Override
        public int getCount() {
            return classes.size();
        }

        @Override
        public Class getItem(int arg0) {
            return classes.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
