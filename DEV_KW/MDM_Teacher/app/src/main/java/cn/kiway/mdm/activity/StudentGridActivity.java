package cn.kiway.mdm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.teacher.R.id.count;
import static cn.kiway.mdm.util.Utils.check301;

//该页面做多个地方使用
//首页:TYPE_DIANMING
//点名答:TYPE_DIANMINGDA
//统计:TYPE_TONGJI
public class StudentGridActivity extends BaseActivity {

    public static final int TYPE_DIANMING = 1;
    public static final int TYPE_DIANMINGDA = 2;
    public static final int TYPE_TONGJI = 3;

    private Button ok;
    private RelativeLayout toolsRL;

    private int type;
    private GridView gv;
    private MyAdapter adapter;

    private ArrayList<Student> students = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grid);

        type = getIntent().getIntExtra("type", 0);

        initView();
        initData();
        initListener();
    }

    public void initView() {
        super.initView();
        titleName.setText("二年级一班");
        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        toolsRL = (RelativeLayout) findViewById(R.id.toolsRL);
        ok = (Button) findViewById(R.id.ok);
        if (type == TYPE_DIANMINGDA) {
            ok.setVisibility(View.VISIBLE);
            toolsRL.setVisibility(View.GONE);
        } else if (type == TYPE_TONGJI) {
            showTongjidialog();
        }
    }

    public void clickOK(View v) {
        ArrayList<Student> selectStudents = new ArrayList<>();
        for (Student s : students) {
            if (s.selected) {
                selectStudents.add(s);
            }
        }
        if (selectStudents.size() == 0) {
            toast("请选择至少一个学生");
            return;
        }
        startActivity(new Intent(this, ResultActivity1.class).putExtra("type", TYPE_QUESTION_DIANMINGDA).putExtra("students", selectStudents).putExtra("questionTime", getIntent().getIntExtra("questionTime", 0)));
        finish();
    }

    public void initData() {
        try {
            showPD();
            String url = KWApplication.clientUrl + "/device/teacher/class/students";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    dismissPD();
                    try {
                        JSONArray data = new JSONObject(ret).optJSONArray("data");
                        students = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Student>>() {
                        }.getType());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    if (!Utils.check301(StudentGridActivity.this, s, "students")) {
                        toast("请求失败，请稍后再试");
                        dismissPD();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }

    private void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = students.get(position);
                if (type == TYPE_DIANMING) {
                    toast(s.name + (s.come ? "到了" : "没到"));
                } else if (type == TYPE_DIANMINGDA) {
                    //选中的
                    s.selected = !s.selected;
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(StudentGridActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.icon = (ImageView) rowView.findViewById(R.id.icon);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Student s = students.get(position);
            holder.name.setText(s.name);
            //TODO avatar

            if (type == TYPE_DIANMING) {
                if (s.come) {
                    holder.icon.setImageResource(R.drawable.icon2);
                } else {
                    holder.icon.setImageResource(R.drawable.icon1);
                }
            } else if (type == TYPE_DIANMINGDA) {
                if (s.selected) {
                    holder.icon.setImageResource(R.drawable.icon2);
                } else {
                    holder.icon.setImageResource(R.drawable.icon1);
                }
            } else if (type == TYPE_TONGJI) {
                //明白是1，没明白是2，未回复是0
                if (s.known == 0) {
                    holder.icon.setImageResource(R.drawable.icon1);
                } else if (s.known == 1) {
                    holder.icon.setImageResource(R.drawable.icon2);
                } else if (s.known == 2) {
                    holder.icon.setImageResource(R.drawable.icon3);
                }
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public ImageView icon;
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int arg0) {
            return students.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

    }

    //点名对话框
    private Dialog dialog_dianming;
    private Button dianmingBtn;
    private TextView count_dianming;
    private TextView time_dianming;
    private int totalcount_dianming = 300;

    @Override
    public void dm(View view) {
        dialog_dianming = new Dialog(this, R.style.popupDialog);
        dialog_dianming.setContentView(R.layout.dialog_dianming);
        dialog_dianming.show();
        dianmingBtn = (Button) dialog_dianming.findViewById(R.id.dianming);
        count_dianming = (TextView) dialog_dianming.findViewById(count);
        count_dianming.setText(0 + "/" + students.size());
        time_dianming = (TextView) dialog_dianming.findViewById(R.id.time);

        dianmingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dianmingBtn.getText().toString().equals("开始点名")) {
                    //1.发送点名请求并实时刷新界面
                    doStartSign();
                } else {
                    //点名结束，跳到上课
                    doEndSign();
                }
            }
        });
    }

    private void doEndSign() {
        mHandler.removeMessages(TYPE_DIANMING);
        dialog_dianming.dismiss();
        startActivity(new Intent(StudentGridActivity.this, CourseListActivity.class).putExtra("students", students));
        finish();
    }

    public void doStartSign() {
        try {
            //1.发“点名”推送命令
            showPD();
            String url = KWApplication.clientUrl + "/device/push/teacher/sign/order";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.post(StudentGridActivity.this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "dianmingBtn onSuccess = " + ret);
                    dismissPD();
                    dianmingBtn.setBackgroundResource(R.drawable.dianmingbutton2);
                    dianmingBtn.setText("结束点名");
                    //开始点名、对话框不可关闭
                    dialog_dianming.setCancelable(false);
                    dialog_dianming.setCanceledOnTouchOutside(false);
                    //开始倒计时
                    mHandler.sendEmptyMessageDelayed(TYPE_DIANMING, 1000);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "dianmingBtn onFailure = " + s);
                    if (!check301(StudentGridActivity.this, s, "dianmingBtn")) {
                        toast("请求失败，请稍后再试");
                        dismissPD();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }


    //统计对话框
    private Dialog dialog_tongji;
    private TextView count_tongji;
    private TextView time_tongji;
    private int totalcount_tongji = 120;

    private void showTongjidialog() {
        dialog_tongji = new Dialog(this, R.style.popupDialog);
        dialog_tongji.setContentView(R.layout.dialog_tongji2);
        dialog_tongji.setCancelable(false);
        dialog_tongji.setCanceledOnTouchOutside(false);
        dialog_tongji.show();
        Button dianmingBtn = (Button) dialog_tongji.findViewById(R.id.dianming);
        count_tongji = (TextView) dialog_tongji.findViewById(count);
        count_tongji.setText(0 + "/" + students.size());
        time_tongji = (TextView) dialog_tongji.findViewById(R.id.time);

        mHandler.sendEmptyMessageDelayed(TYPE_TONGJI, 1000);
        dianmingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束统计
                doEndTongji();
            }
        });
    }

    private void doEndTongji() {
        mHandler.removeMessages(TYPE_TONGJI);
        dialog_tongji.dismiss();
        finish();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == TYPE_DIANMING) {
                totalcount_dianming--;
                time_dianming.setText("倒计时 " + Utils.secToTime(totalcount_dianming));
                if (totalcount_dianming > 0) {
                    mHandler.sendEmptyMessageDelayed(TYPE_DIANMING, 1000);
                } else {
                    toast("点名结束");
                    //这里可以弹出点名结果
                    doEndSign();
                }
            } else if (what == TYPE_TONGJI) {
                totalcount_tongji--;
                time_tongji.setText("倒计时 " + Utils.secToTime(totalcount_tongji));
                if (totalcount_tongji > 0) {
                    mHandler.sendEmptyMessageDelayed(TYPE_TONGJI, 1000);
                } else {
                    toast("统计结束");
                    //这里可以弹出统计结果
                    doEndTongji();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


}