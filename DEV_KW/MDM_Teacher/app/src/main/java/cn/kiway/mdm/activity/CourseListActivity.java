package cn.kiway.mdm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;


public class CourseListActivity extends BaseActivity {

    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courselist);

        initView();
        iniListener();
    }

    public void initView() {
        super.initView();

        toolsRL.setVisibility(View.GONE);

        titleName.setText("上课");
        lv = (ListView) findViewById(R.id.courseLV);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void iniListener() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course c = courses.get(position);
                if (c.attendCourse == 0) {
                    //未上课
                    startActivity(new Intent(CourseListActivity.this, Course0Activity.class).putExtra("course", c));
                } else if (c.attendCourse == 1) {
                    //已上课
                    startActivity(new Intent(CourseListActivity.this, Course1Activity.class).putExtra("course", c));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public void initData() {
        //TODO后台不排序的话，分页没法做啊。
        showPD();
        try {
            String url = KWApplication.clientUrl + "/device/teacher/course/attend?currentPage=1&pageSize=1024";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    hidePD();
                    try {
                        JSONArray list = new JSONObject(ret).getJSONObject("data").getJSONArray("list");
                        courses = new GsonBuilder().create().fromJson(list.toString(), new TypeToken<List<Course>>() {
                        }.getType());
                        //过滤一下"草稿"
                        Iterator<Course> it = courses.iterator();
                        while (it.hasNext()) {
                            Course c = it.next();
                            if (c.type == 2) {
                                it.remove();
                            }
                        }
                        //排序
                        Collections.sort(courses, new Comparator<Course>() {
                            @Override
                            public int compare(Course o1, Course o2) {
                                int diff = o1.attendCourse - o2.attendCourse;
                                if (diff != 0) {
                                    return diff;
                                }
                                int diff2 = (int) (Long.parseLong(o2.createDate) - Long.parseLong(o1.createDate));
                                return diff2;
                            }
                        });
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    hidePD();
                    if (!Utils.check301(CourseListActivity.this, s, "courselist")) {
                        toast("请求失败，请稍后再试");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(CourseListActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_course, null);
                holder = new ViewHolder();

                holder.title1 = (TextView) rowView.findViewById(R.id.title1);
                holder.title2 = (TextView) rowView.findViewById(R.id.title2);
                holder.yishangke = (ImageView) rowView.findViewById(R.id.yishangke);
                holder.clock = (ImageView) rowView.findViewById(R.id.clock);
                holder.ball = (ImageView) rowView.findViewById(R.id.ball);
                holder.line2 = (TextView) rowView.findViewById(R.id.line2);
                holder.line0 = (TextView) rowView.findViewById(R.id.line0);

                holder.date1 = (TextView) rowView.findViewById(R.id.date1);
                holder.date2 = (TextView) rowView.findViewById(R.id.date2);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            if (position == 0) {
                holder.clock.setVisibility(View.VISIBLE);
                holder.line0.setVisibility(View.VISIBLE);
            } else {
                holder.clock.setVisibility(View.GONE);
                holder.line0.setVisibility(View.GONE);
            }

            final Course s = courses.get(position);
            if (s.attendCourse == 0) {
                holder.title1.setTextColor(Color.parseColor("#6699ff"));
                holder.title2.setTextColor(Color.parseColor("#6699ff"));
                holder.yishangke.setVisibility(View.GONE);
                holder.ball.setImageResource(R.drawable.ball2);
                holder.line2.setBackgroundColor(Color.parseColor("#6699ff"));
                holder.date1.setVisibility(View.GONE);
                holder.date2.setVisibility(View.GONE);
            } else if (s.attendCourse == 1) {
                holder.title1.setTextColor(Color.parseColor("#cccccc"));
                holder.title2.setTextColor(Color.parseColor("#cccccc"));
                holder.yishangke.setVisibility(View.VISIBLE);
                holder.ball.setImageResource(R.drawable.ball1);
                holder.line2.setBackgroundColor(Color.parseColor("#cccccc"));
                holder.date1.setVisibility(View.VISIBLE);
                holder.date2.setVisibility(View.VISIBLE);

                holder.date1.setText(Utils.longToDate2(s.createDate));
                holder.date2.setText(Utils.longToDate3(s.createDate));
            }

            holder.title1.setText(s.name);
            String subtitle = "";
            int count = s.knowledgePoints.size();
            for (int i = 0; i < count; i++) {
                KnowledgePoint kp = s.knowledgePoints.get(i);
                subtitle += "知识点" + (i + 1) + "：" + kp.content + "\n";
            }
            holder.title2.setText(subtitle);
            return rowView;
        }

        public class ViewHolder {
            public TextView title1;
            public TextView title2;
            public ImageView yishangke;
            public ImageView clock;
            public TextView line0;
            public TextView line2;
            public ImageView ball;
            public TextView date1;
            public TextView date2;

        }

        @Override
        public int getCount() {
            return courses.size();
        }

        @Override
        public Course getItem(int arg0) {
            return courses.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

}
