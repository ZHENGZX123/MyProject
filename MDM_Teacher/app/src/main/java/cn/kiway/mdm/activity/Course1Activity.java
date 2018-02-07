package cn.kiway.mdm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.AttendItem;
import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.entity.KnowledgeCountResult;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.teacher.R;

import static cn.kiway.mdm.util.Utils.check301;
import static cn.kiway.mdm.util.Utils.showBigImage;

/**
 * Created by Administrator on 2017/12/28.
 */
public class Course1Activity extends BaseActivity {

    private ListView lv;
    private CourseAdapter adapter;
    private ArrayList<AttendItem> items = new ArrayList<>();

    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course1);

        course = (Course) getIntent().getSerializableExtra("course");

        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        toolsRL.setVisibility(View.GONE);
        videoBtn.setVisibility(View.VISIBLE);

        titleName.setText(course.name);

        lv = (ListView) findViewById(R.id.lv);
        adapter = new CourseAdapter();
        lv.setAdapter(adapter);
    }

    public void initData() {
        //1.知识点详情
        try {
            showPD();
            String url = KWApplication.clientUrl + "/device/teacher/course/" + course.id + "/attend";
            Log.d("test", "url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "course onSuccess = " + ret);
                    hidePD();
                    items.clear();
                    try {
                        JSONObject data = new JSONObject(ret).getJSONObject("data");
                        Iterator it = data.keys();
                        while (it.hasNext()) {
                            String key = (String) it.next();
                            int type = Integer.parseInt(key.split("#")[1]);
                            AttendItem item = new AttendItem();
                            item.type = type;
                            item.time = key.split("#")[0];
                            if (type == 1) {
                                JSONObject valueObj = data.getJSONObject(key);
                                String name = valueObj.getString("name");
                                item.title = name;
                            } else if (type == 2) {
                                //统计结果
                                JSONObject valueObj = data.getJSONObject(key);
                                JSONArray array = valueObj.getJSONArray("knowledgeCountResult");
                                item.results = new GsonBuilder().create().fromJson(array.toString(), new TypeToken<List<KnowledgeCountResult>>() {
                                }.getType());
                                item.title = "知识点：" + item.results.get(0).knowledgeName;
                            } else {
                                JSONArray valueObj = data.getJSONArray(key);
                                item.questions = new GsonBuilder().create().fromJson(valueObj.toString(), new TypeToken<ArrayList<Question>>() {
                                }.getType());
                                item.title = "问答/测评";
                            }
                            //检查重复的type1，确定type1都在上面？？？
                            int count_type1 = 0;
                            for (AttendItem i : items) {
                                if (i.type == 1) {
                                    count_type1++;
                                }
                            }
                            if (count_type1 > 1) {
                                for (int i = 0; i < count_type1 - 1; i++) {
                                    items.remove(0);
                                }
                            }
                            items.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "course onFailure = " + s);
                    if (!check301(Course1Activity.this, s, "coursedetail1")) {
                        toast("请求失败，请稍后再试");
                        hidePD();
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
            hidePD();
        }
    }

    private class CourseAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public CourseAdapter() {
            inflater = LayoutInflater.from(Course1Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_attend, null);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.clock = (ImageView) rowView.findViewById(R.id.clock);
                holder.ball = (ImageView) rowView.findViewById(R.id.ball);
                holder.line = (TextView) rowView.findViewById(R.id.line);
                holder.line2 = (TextView) rowView.findViewById(R.id.line2);
                holder.date1 = (TextView) rowView.findViewById(R.id.date1);
                holder.date2 = (TextView) rowView.findViewById(R.id.date2);
                holder.type2RL = (LinearLayout) rowView.findViewById(R.id.type2RL);
                holder.type345RL = (LinearLayout) rowView.findViewById(R.id.type345RL);
                holder.type6RL = (LinearLayout) rowView.findViewById(R.id.type6RL);
                holder.tongji = (Button) rowView.findViewById(R.id.tongji);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            if (position == 0) {
                holder.clock.setVisibility(View.VISIBLE);
            } else {
                holder.clock.setVisibility(View.GONE);
            }

            AttendItem item = items.get(position);
            int type = item.type;
            if (type == 1) {
                holder.type2RL.setVisibility(View.GONE);
                holder.type345RL.setVisibility(View.GONE);
                holder.type6RL.setVisibility(View.GONE);
            } else if (type == 2) {
                holder.type2RL.setVisibility(View.VISIBLE);
                holder.type345RL.setVisibility(View.GONE);
                holder.tongji.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Course1Activity.this, TongjiActivity.class).putExtra("type", 1).putExtra("item", item));
                    }
                });
                holder.type6RL.setVisibility(View.GONE);
            } else if (type == 3 || type == 4 || type == 5) {
                //题目
                holder.type2RL.setVisibility(View.GONE);
                holder.type345RL.setVisibility(View.VISIBLE);
                holder.type6RL.setVisibility(View.GONE);
                addContent345(holder, item);
            } else if (type == 6) {
                holder.type2RL.setVisibility(View.GONE);
                holder.type345RL.setVisibility(View.GONE);
                holder.type6RL.setVisibility(View.VISIBLE);
                addContent6(holder, item);
            }

            holder.title.setText(item.title);
            holder.date1.setText(item.time.split(" ")[0]);
            holder.date2.setText(item.time.split(" ")[1].split("\\.")[0]);
            return rowView;
        }

        public class ViewHolder {
            public TextView title;
            public ImageView clock;
            public TextView line;
            public TextView line2;
            public ImageView ball;
            public TextView date1;
            public TextView date2;
            public LinearLayout type2RL;
            public LinearLayout type345RL;
            public LinearLayout type6RL;
            public Button tongji;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public AttendItem getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }


        private void addContent345(ViewHolder holder, AttendItem item) {
            holder.type345RL.removeAllViews();

            Question s = item.questions.get(0);
            LinearLayout layout_question345 = (LinearLayout) inflater.inflate(R.layout.layout_question345, null);
            //问题
            TextView content = (TextView) layout_question345.findViewById(R.id.content);
            LinearLayout imgLL = (LinearLayout) layout_question345.findViewById(R.id.imgLL);
            content.setText("题目：" + s.content);
            if (!TextUtils.isEmpty(s.img)) {
                String imgs[] = s.img.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    String imageUrl = imgs[i];
                    ImageView iv = new ImageView(Course1Activity.this);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showBigImage(Course1Activity.this, new String[]{imageUrl}, 0);
                        }
                    });
                    ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions
                            ());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 10, 10, 10);
                    imgLL.addView(iv, lp);
                }
            }
            //答案
            TextView content2 = (TextView) layout_question345.findViewById(R.id.content2);
            LinearLayout imgLL2 = (LinearLayout) layout_question345.findViewById(R.id.imgLL2);
            content2.setText(s.userExaminationResultVo.name + "的答案：" + s.userExaminationResultVo.content);
            if (!TextUtils.isEmpty(s.img)) {
                String imgs[] = s.img.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    String imageUrl = imgs[i];
                    ImageView iv = new ImageView(Course1Activity.this);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showBigImage(Course1Activity.this, new String[]{imageUrl}, 0);
                        }
                    });
                    ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions
                            ());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 10, 10, 10);
                    imgLL2.addView(iv, lp);
                }
            }

            holder.type345RL.addView(layout_question345);
        }

        private void addContent6(ViewHolder holder, AttendItem item) {
            holder.type6RL.removeAllViews();

            //这里只显示问题。。。是一个列表
            LinearLayout layout_question6 = (LinearLayout) inflater.inflate(R.layout.layout_question6, null);
            LinearLayout questionLL = (LinearLayout) layout_question6.findViewById(R.id.questionLL);
            //循环添加问题即可
            for (Question q : item.questions) {
                TextView tv = new TextView(Course1Activity.this);
                tv.setText("问题：" + q.content);
                tv.setTextColor(Color.parseColor("#6699ff"));
                questionLL.addView(tv);
            }
            Button ceping = (Button) layout_question6.findViewById(R.id.ceping);
            ceping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Course1Activity.this, TongjiActivity.class).putExtra("type", 2).putExtra("item", item));
                }
            });

            holder.type6RL.addView(layout_question6);
        }
    }

    public void clickVideo(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }
}
