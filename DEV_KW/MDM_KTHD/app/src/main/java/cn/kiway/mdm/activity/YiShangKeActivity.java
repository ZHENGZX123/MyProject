package cn.kiway.mdm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.model.Course;
import cn.kiway.mdm.model.ZzxCourse;
import cn.kiway.mdm.model.knowledgePoints;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.HttpUtil.getYiShangkeUrl;

/**
 * Created by Administrator on 2018/2/5.
 */

public class YiShangKeActivity extends BaseActivity {
    TextView titleName;
    PullToRefreshListView listView;
    private ArrayList<ZzxCourse> allCourses = new ArrayList<>();//所有课程列表
    CourseAdapter courseAdapter;
    boolean isClear = true;//是否清除数据
    int Page = 1; //当前请求的页数
    int totalPage;//总的页数
    int totalRecord;//总的数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yishangke);
        initView();
        loadData();
    }


    @Override
    public void initView() {
        super.initView();
        titleName = (TextView) findViewById(R.id.titleName);
        titleName.setText("已上课程");
        listView = (PullToRefreshListView) findViewById(R.id.list_view);
        courseAdapter = new CourseAdapter(this);
        listView.setAdapter(courseAdapter);
        listView.setOnRefreshListener(mListViewOnRefreshListener2);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ZzxCourse course = allCourses.get(position - 1);
                Course course1 = new Course();
                course1.id = course.getId();
                course1.name = course.getName();
                startActivity(new Intent(YiShangKeActivity.this, Course1Activity.class).putExtra("course", course1));
            }
        });
    }

    public void loadData() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            String url = getYiShangkeUrl + Page;
            Logger.log(url);
            client.get(this, url, param, new
                    TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.e("test", "calls onSuccess = " + ret);
                            listView.onRefreshComplete();
                            try {
                                JSONObject data = new JSONObject(ret);
                                if (data.optInt("statusCode") == 201) {
                                } else if (data.optInt("statusCode") == 200) {
                                    initData(data.optJSONObject("data").optJSONArray("list"));
                                    totalPage = data.optJSONObject("data").optInt("totalPage");
                                    totalRecord = data.optJSONObject("data").optInt("totalRecord");
                                }
                                if (courseAdapter.getCount() > 0) {
                                    listView.setVisibility(View.VISIBLE);
                                    findViewById(R.id.no_data).setVisibility(View.GONE);
                                } else {
                                    listView.setVisibility(View.GONE);
                                    findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                            Log.d("test", "calls onFailure = " + ret);
                            Logger.log("::::::::::::onFailure" + ret);
                            if (ret != null && !ret.equals("")) {
                                try {
                                    JSONObject data = new JSONObject(ret);
                                    if (data.optInt("statusCode") != 200) {
                                        Utils.login(YiShangKeActivity.this, new Utils.ReLogin() {
                                            @Override
                                            public void onSuccess() {
                                                loadData();
                                            }

                                            @Override
                                            public void onFailure() {
                                                listView.onRefreshComplete();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                toast("请求失败，请稍后再试");
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public void initData(JSONArray array) {
        if (isClear)
            allCourses.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.optJSONObject(i);
            ZzxCourse course = new ZzxCourse();
            course.setAttachInfo(item.optString("attachInfo"));
            course.setName(item.optString("name"));
            course.setId(item.optString("id"));
            course.setAttach(item.optString("attach"));
            course.setUserId(item.optString("userId"));
            course.setType(item.optInt("type"));
            course.setCreateDate(item.optString("createDate"));
            course.setAttendCourse(item.optInt("attendCourse"));
            ArrayList<knowledgePoints> knowledgePointses = new ArrayList<>();//所有课程列表
            JSONArray array1 = item.optJSONArray("knowledgePoints");
            if (array1 != null && array.length() > 0)
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject item1 = array1.optJSONObject(j);
                    knowledgePoints knowledgePoints = new knowledgePoints();
                    knowledgePoints.setCreateDate(item1.optString("createDate"));
                    knowledgePoints.setContent(item1.optString("content"));
                    knowledgePoints.setId(item1.optString("id"));
                    knowledgePoints.setCourseId(item1.optString("courseId"));
                    knowledgePoints.setOperation(item1.optString("operation"));
                    knowledgePoints.setTeachingContentVo(item1.optString("teachingContentVo"));
                    knowledgePointses.add(knowledgePoints);
                }
            course.setKnowledgePoints(knowledgePointses);
            allCourses.add(course);
        }
        courseAdapter.notifyDataSetChanged();
    }

    public class CourseAdapter extends ArrayAdapter {
        CourseHolder holder;

        public CourseAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_course, null);
                holder = new CourseHolder();
                holder.date1 = (TextView) convertView.findViewById(R.id.date1);
                holder.date2 = (TextView) convertView.findViewById(R.id.date2);
                holder.title = (TextView) convertView.findViewById(R.id.title1);
                holder.content = (TextView) convertView.findViewById(R.id.title2);
                convertView.setTag(holder);
            } else {
                holder = (CourseHolder) convertView.getTag();
            }
            if (position == 0) {
                convertView.findViewById(R.id.clock).setVisibility(View.VISIBLE);
            } else {
                convertView.findViewById(R.id.clock).setVisibility(View.GONE);
            }
            ZzxCourse course = allCourses.get(position);
            holder.date1.setText(Utils.getDateField(Long.parseLong(course.getCreateDate()), 5));
            holder.date2.setText(Utils.getDateField(Long.parseLong(course.getCreateDate()), 0));
            holder.title.setText(course.getName());
            holder.content.setText(course.getknowledgeContent());
            return convertView;
        }

        @Override
        public int getCount() {
            return allCourses.size();
        }

        public class CourseHolder {
            TextView date1, date2;
            TextView content;
            TextView title;
        }
    }

    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase
            .OnRefreshListener2<ListView>() {

        /**
         * 下拉刷新回调
         * @param refreshView
         */
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            Page = 1;
            isClear = true;
            loadData();
        }

        /**
         * 上拉加载更多回调
         * @param refreshView
         */
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            if (Page >= totalPage) {
                Toast.makeText(YiShangKeActivity.this, "已加载全部", Toast.LENGTH_SHORT).show();
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                    }
                }, 1000);
                return;
            }
            Page++;
            isClear = false;
            loadData();
        }
    };
}
