package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.zbus.ZbusHost;
import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import cn.kiway.web.kthd.zbus.vo.PushRecordVo;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.teacher.R.id.count;
import static cn.kiway.mdm.util.Utils.check301;

//该页面做多个地方使用
//首页:TYPE_DIANMING
//点名答:TYPE_DIANMINGDA
//统计:TYPE_TONGJI
//分发文件:TYPE_WENJIAN
//查屏:TYPE_CHAPING
//锁屏:TYPE_SUOPING
public class StudentGridActivity extends BaseActivity {

    public static final int TYPE_DIANMING = 1;
    public static final int TYPE_DIANMINGDA = 2;
    public static final int TYPE_TONGJI = 3;
    public static final int TYPE_WENJIAN = 4;
    public static final int TYPE_CHAPING = 5;
    public static final int TYPE_SUOPING = 6;

    private Button ok;
    private Button all;
    private ImageButton lock;
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
        all = (Button) findViewById(R.id.all);
        lock = (ImageButton) findViewById(R.id.lock);

        if (type == TYPE_DIANMINGDA) {
            ok.setVisibility(View.VISIBLE);
            toolsRL.setVisibility(View.GONE);
        } else if (type == TYPE_TONGJI) {
            showTongjidialog();
        } else if (type == TYPE_WENJIAN) {
            ok.setVisibility(View.VISIBLE);
            all.setVisibility(View.VISIBLE);
            toolsRL.setVisibility(View.GONE);
        } else if (type == TYPE_SUOPING) {
            lock.setVisibility(View.VISIBLE);
            toolsRL.setVisibility(View.GONE);
        } else if (type == TYPE_CHAPING) {
            ok.setVisibility(View.GONE);
            toolsRL.setVisibility(View.GONE);
        }
    }

    private boolean selectAll = false;

    public void clickALL(View v) {
        selectAll = !selectAll;
        if (selectAll) {
            all.setText("取消");
        } else {
            all.setText("全选");
        }
        for (Student s : students) {
            s.selected = selectAll;
        }
        adapter.notifyDataSetChanged();
    }

    private boolean lockAll = false;

    public void clickLock(View v) {
        String message = "";
        if (lockAll) {
            message = "是否解锁全班学生的屏幕";
        } else {
            message = "是否锁定全班学生的屏幕";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //发送命令
                        //全锁或者全解锁
                        lockAll = !lockAll;
                        for (Student s : students) {
                            s.locked = lockAll;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).setPositiveButton(android.R.string.cancel, null).create();
        dialog.show();
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
        if (type == TYPE_DIANMINGDA) {
            //点名答
            startActivity(new Intent(this, ResultActivity.class).putExtra("type", TYPE_QUESTION_DIANMINGDA).putExtra("students", selectStudents).putExtra("questionTime", getIntent().getIntExtra("questionTime", 0)).putExtra("questions", getIntent().getSerializableExtra("questions")));
            finish();
        } else if (type == TYPE_WENJIAN) {
            //发送文件成功后finish
            finish();
        }
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
                        //1.刷新界面
                        adapter.notifyDataSetChanged();
                        //2.发送上课命令
                        KWApplication.students = students;
                        ZbusHost.shangke(StudentGridActivity.this);
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
                } else if (type == TYPE_DIANMINGDA || type == TYPE_WENJIAN) {
                    //选中的
                    s.selected = !s.selected;
                    adapter.notifyDataSetChanged();
                    //3.修改lockAll变量
                    selectAll = true;
                    for (Student temp : students) {
                        selectAll = selectAll & temp.selected;
                    }
                    if (selectAll) {
                        all.setText("取消");
                    } else {
                        all.setText("全选");
                    }
                } else if (type == TYPE_CHAPING) {
                    toast("查看" + s.name + "的屏幕");
                    //跳页...
                } else if (type == TYPE_SUOPING) {
                    String message;
                    if (s.locked) {
                        message = "是否解锁" + s.name + "的屏幕";
                    } else {
                        message = "是否锁定" + s.name + "的屏幕";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentGridActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                    AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //1.发送命令
                                    //2.刷新界面
                                    s.locked = !s.locked;
                                    adapter.notifyDataSetChanged();
                                    //3.修改lockAll变量
                                    lockAll = true;
                                    for (Student temp : students) {
                                        lockAll = lockAll & temp.locked;
                                    }
                                }
                            }).setPositiveButton(android.R.string.cancel, null).create();
                    dialog.show();
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
                holder.lock = (ImageView) rowView.findViewById(R.id.lock);

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
            } else if (type == TYPE_DIANMINGDA || type == TYPE_WENJIAN) {
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
            } else if (type == TYPE_SUOPING) {
                if (s.locked) {
                    holder.lock.setVisibility(View.VISIBLE);
                } else {
                    holder.lock.setVisibility(View.GONE);
                }
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public ImageView icon;
            public ImageView lock;
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

    @Override
    public void sk(View view) {
        //FIXME 不点名直接上课，哇咔咔
        startActivity(new Intent(StudentGridActivity.this, CourseListActivity.class).putExtra("students", students));
        finish();
    }

    @Override
    public void rk(View view) {
        startActivity(new Intent(StudentGridActivity.this, ClassResultActivity.class));
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

    public void doEndTongji() {
        //1.上报统计结果
        mHandler.removeMessages(TYPE_TONGJI);
        showPD();
        try {
            String url = KWApplication.clientUrl + "/device/teacher/course/student/knowledge/result";
            Log.d("test", "url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            JSONArray array = new JSONArray();
            JSONObject o1 = new JSONObject();
            o1.put("imei", "student imei");
            o1.put("countId", "zbus countId");
            o1.put("status", 0);//0没听懂 1听懂了
            array.put(o1);
            Log.d("test", "knowledge array = " + array.toString());
            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    dismissPD();
                    //2.关闭对话框并退出
                    dialog_tongji.dismiss();
                    finish();
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    dismissPD();
                    if (!Utils.check301(StudentGridActivity.this, s, "knowledgeCountResult")) {
                        toast("请求失败，请稍后再试");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
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
