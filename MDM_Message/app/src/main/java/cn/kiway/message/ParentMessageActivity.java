package cn.kiway.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import cn.kiway.message.mode.Message;
import cn.kiway.message.utils.Utils;
import cn.kiway.message.views.refresh.PullToRefreshLayout;
import cn.kiway.message.views.refresh.PullableListView;

import static cn.kiway.message.utils.Constant.clientUrl;
import static cn.kiway.message.utils.Utils.check301;


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
        getAppData();
        pullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        pullToRefreshLayout.setOnRefreshListener(new MyListener());
        lv1 = (PullableListView) findViewById(R.id.lv);
        adapter1 = new MyAdapter();
        lv1.setAdapter(adapter1);
        getLeaveMsg();
    }

    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getStringExtra("studentName") != null)//学生名字
                getSharedPreferences("kiway", 0).edit().putString("studentName", intent.getStringExtra
                        ("studentName")).commit();
            if (intent.getStringExtra("className") != null)//班级名字
                getSharedPreferences("kiway", 0).edit().putString("className", intent.getStringExtra
                        ("className")).commit();
            if (intent.getStringExtra("studentNumber") != null)//学号
                getSharedPreferences("kiway", 0).edit().putString("studentNumber", intent.getStringExtra
                        ("studentNumber")).commit();
            if (intent.getStringExtra("classId") != null)//班级Id
                getSharedPreferences("kiway", 0).edit().putString("classId", intent.getStringExtra
                        ("classId")).commit();
            if (intent.getStringExtra("schoolId") != null)//学校Id
                getSharedPreferences("kiway", 0).edit().putString("schoolId", intent.getStringExtra
                        ("schoolId")).commit();
            if (intent.getStringExtra("huaweiToken") != null)//华为token
                getSharedPreferences("kiway", 0).edit().putString("huaweiToken", intent.getStringExtra
                        ("huaweiToken")).commit();
            if (intent.getStringExtra("x-auth-token") != null)//x-auth-token
                getSharedPreferences("kiway", 0).edit().putString("x-auth-token", intent.getStringExtra
                        ("x-auth-token")).commit();
        }
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
                    Log.e("test", "leaveMsg onSuccess = " + ret);
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
                            if (adapter1.getCount() > 0) {
                                findViewById(R.id.refresh_view).setVisibility(View.VISIBLE);
                                findViewById(R.id.no_data).setVisibility(View.GONE);
                            } else {
                                findViewById(R.id.refresh_view).setVisibility(View.GONE);
                                findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            toast("请求失败，请稍后再试");
                            pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        }
                    }
                    dismissPD();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    if (!check301(ParentMessageActivity.this, s)) {
                        toast("请求失败，请稍后再试");
                    }
                    Log.e("test", "leaveMsg onFailure = " + s);
                    dismissPD();
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    if (adapter1.getCount() > 0) {
                        findViewById(R.id.refresh_view).setVisibility(View.VISIBLE);
                        findViewById(R.id.no_data).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.refresh_view).setVisibility(View.GONE);
                        findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                    }
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
