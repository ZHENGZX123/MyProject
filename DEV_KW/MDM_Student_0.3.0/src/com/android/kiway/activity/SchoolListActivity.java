package com.android.kiway.activity;

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

import com.android.kiway.entity.School;
import com.android.launcher3.R;
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

import static com.android.kiway.utils.Constant.serverUrl;

/**
 * Created by Administrator on 2017/10/17.
 */

public class SchoolListActivity extends BaseActivity {

    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<School> schools = new ArrayList<>();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoollist);

        lv1 = (ListView) findViewById(R.id.lv);
        textView = (TextView) findViewById(R.id.title);
        textView.setText("请选择学校");
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        initData();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                School s = schools.get(i);
                setResult(999, new Intent().putExtra("school", s));
                finish();
            }
        });
    }

    public void Before(View view) {
        finish();
    }

    private void initData() {
        String area = getIntent().getStringExtra("area");
        String name = getIntent().getStringExtra("name");
        //如果关键字不为空，按关键字查找即可
        if (!name.equals("")) {
            area = "";
        }
        //根据area获取学校列表
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = serverUrl + "common/school?addr=" + area + "&name=" + name;
            Log.d("test", "school url = " + url);
            client.get(this, url, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    dismissPD();
                    Log.d("test", "school onSuccess = " + ret);
                    try {
                        JSONArray data = new JSONObject(ret).getJSONArray("data");
                        schools = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<School>>() {
                        }.getType());
                        if (schools.size() == 0) {
                            toast("该地区没有学校");
                            return;
                        }
                        adapter1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "school onFailure = " + s);
                    toast("请求失败，请稍后再试");
                    dismissPD();
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
            inflater = LayoutInflater.from(SchoolListActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_school, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final School s = schools.get(position);
            holder.name.setText(s.name);
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
        }

        @Override
        public int getCount() {
            return schools.size();
        }

        @Override
        public School getItem(int arg0) {
            return schools.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
