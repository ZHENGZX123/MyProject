package com.android.kiway.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.kiway.entity.Message;
import com.android.kiway.utils.Utils;
import com.android.kiway.view.refresh.PullToRefreshLayout;
import com.android.kiway.view.refresh.PullableListView;
import com.android.launcher3.R;
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

import static com.android.kiway.utils.Constant.clientUrl;
import static com.android.kiway.utils.HttpUtil.check301;

/**
 * Created by Administrator on 2018/1/9.
 */

public class ParentMessageActivity extends BaseActivity {

    private int currentPage = 1;
    private int pageCount = 10;

    private PullToRefreshLayout pullToRefreshLayout;

    private PullableListView lv1;
    private MyAdapter adapter1;
    private ArrayList<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_msg);

        pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        lv1 = (PullableListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);

        getLeaveMsg();
    }

    public class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            currentPage = 1;
            getLeaveMsg();
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            // 加载操作
            currentPage += 1;
            getLeaveMsg();
        }
    }

    public void getLeaveMsg() {
        showPD();
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            String url = clientUrl + "device/student/leaveMsg?currentPage=" + currentPage + "&pageSize=" + pageCount;
            Log.d("test", "leaveMsg = " + url);
            client.get(this, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "leaveMsg onSuccess = " + ret);
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    if (!check301(ParentMessageActivity.this, ret)) {
                        try {
                            JSONArray data = new JSONObject(ret).getJSONArray("data");
                            ArrayList<Message> temp = new GsonBuilder().create().fromJson(data.toString(), new
                                    TypeToken<List<Message>>() {
                                    }.getType());
                            if (currentPage == 1) {
                                messages.clear();
                            }
                            int count = temp.size();
                            if (count == 0 && currentPage > 1) {
                                toast("没有更多数据");
                                currentPage -= 1;
                            }
                            messages.addAll(temp);
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
