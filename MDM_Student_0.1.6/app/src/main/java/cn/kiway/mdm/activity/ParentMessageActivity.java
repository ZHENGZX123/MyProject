package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Message;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.clientUrl;
import static cn.kiway.mdm.utils.HttpUtil.check301;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ParentMessageActivity extends BaseActivity {

    private ListView lv1;
    private MyAdapter adapter1;
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_msg);

        lv1 = (ListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        getLeaveMsg();
    }


    public void getLeaveMsg() {
        showPD();
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            String url = clientUrl + "device/student/leaveMsg?currentPage=1&pageSize=100";
            Log.d("test", "leaveMsg = " + url);
            client.get(this, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "leaveMsg onSuccess = " + ret);
                    if (!check301(ParentMessageActivity.this, ret)) {
                        try {
                            JSONArray data = new JSONObject(ret).getJSONArray("data");
                            messages = new GsonBuilder().create().fromJson(data.toString(), new
                                    TypeToken<List<Message>>() {
                                    }.getType());
                            adapter1.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("请求失败，请稍后再试");
                        }
                    }
                    dismissPD();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    if (!check301(ParentMessageActivity.this, s)) {
                        toast("请求失败，请稍后再试");
                    }
                    Log.d("test", "leaveMsg onFailure = " + s);
                    dismissPD();
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
            toast("请求失败，请稍后再试");
        }
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(ParentMessageActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_message, null);
                holder = new ViewHolder();
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.content = (TextView) rowView.findViewById(R.id.content);
                holder.time = (TextView) rowView.findViewById(R.id.time);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            final Message s = messages.get(position);
            holder.content.setText(s.content);
            holder.time.setText(Utils.longToDate(s.createDate));
            return rowView;
        }


        public class ViewHolder {
            public TextView name;
            public TextView content;
            public TextView time;
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Override
        public Message getItem(int arg0) {
            return messages.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    public void Before(View view) {
        finish();
    }
}
