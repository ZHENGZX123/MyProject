package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Constant;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.RoundedImageView;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_CEPING;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_QIANGDA;
import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_SUIJICHOUDA;
import static cn.kiway.mdm.entity.Question.TYPE_EMPTY;
import static cn.kiway.mdm.entity.Question.TYPE_ESSAY;
import static cn.kiway.mdm.teacher.R.id.stop;


/**
 * Created by Administrator on 2017/12/29.
 */

//问答反馈、测评反馈用这个页面
public class ResultActivity extends BaseActivity {

    private int type;
    private LinearLayout unSubmitLL;
    private TextView time;
    private TextView hint;
    private TextView unSubmitCount;
    private TextView unSubmitName;
    private Button stopBtn;

    private GridView gv;
    private MyAdapter adapter;
    private int questionTime;
    private boolean timeup; //时间到


    public static ArrayList<Student> students;
    public static ArrayList<Question> questions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        type = getIntent().getIntExtra("type", 1);
        students = (ArrayList<Student>) getIntent().getSerializableExtra("students");
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        questionTime = getIntent().getIntExtra("questionTime", 0);

        //给question设置studentAnswers和teacherJudges。
        int studentCount = students.size();
        for (Question q : questions) {
            for (int i = 0; i < studentCount; i++) {
                q.studentAnswers.add("");
                q.teacherJudges.add(0);
            }
        }

        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();

        unSubmitLL = (LinearLayout) findViewById(R.id.unSubmitLL);
        time = (TextView) findViewById(R.id.time);
        hint = (TextView) findViewById(R.id.hint);
        unSubmitCount = (TextView) findViewById(R.id.unSubmitCount);
        unSubmitCount.setText("剩余" + students.size() + "人未作答");
        unSubmitName = (TextView) findViewById(R.id.unSubmitName);
        unSubmitName.setMovementMethod(new ScrollingMovementMethod());
        stopBtn = (Button) findViewById(stop);

        if (type == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
            unSubmitCount.setVisibility(View.GONE);
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
            unSubmitCount.setVisibility(View.GONE);
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
            unSubmitCount.setVisibility(View.GONE);
            unSubmitLL.setVisibility(View.GONE);
            hint.setText("答题进行中......");
        } else if (type == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
            unSubmitCount.setVisibility(View.VISIBLE);
            unSubmitLL.setVisibility(View.VISIBLE);
            hint.setText("测评进行中......");
        }

        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = students.get(position);
                if (!s.submited) {
                    toast("该学生未提交答案");
                    return;
                }
                startActivityForResult(new Intent(ResultActivity.this, ResultDetailActivity.class).putExtra("studentIndex", position), 8888);
            }
        });

        refreshUI();
    }

    private void refreshUI() {
        if (unSubmitLL.getVisibility() == View.GONE) {
            return;
        }
        int unSubmit = 0;
        String temp = "";
        for (Student s : students) {
            if (!s.submited) {
                unSubmit++;
                temp += s.name + "、";
            }
        }
        unSubmitCount.setText("剩余" + unSubmit + "人未作答");
        if (temp.length() > 0) {
            unSubmitName.setText(temp.substring(0, temp.length() - 1));
        }
    }


    private void initData() {
        if (questionTime == 0) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                questionTime++;
                mHandler.sendEmptyMessageDelayed(msg.what, 1000);
            } else if (msg.what == 1) {
                if (questionTime > 0) {
                    questionTime--;
                    mHandler.sendEmptyMessageDelayed(msg.what, 1000);
                } else {
                    timeup = true;
                    mHandler.removeCallbacksAndMessages(null);
                    stop(null);
                }
            }
            time.setText(Utils.secToTime(questionTime));
        }
    };

    public void getOneSubmit(final String studentIMEI, final String answer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //1.刷新界面
                int studentCount = students.size();
                for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) {
                    Student s = students.get(studentIndex);
                    if (s.imei.equals(studentIMEI)) {
                        try {
                            s.submited = true;
                            JSONArray array = new JSONArray(answer);
                            int count = array.length();
                            for (int i = 0; i < count; i++) {
                                JSONObject o = array.getJSONObject(i);
                                String qanswer = o.getString("qanswer");
                                Question q = questions.get(i);
                                q.studentAnswers.set(studentIndex, qanswer);
                            }
                            //2.自动批改
                            boolean auto = true;
                            for (Question q : questions) {
                                if (q.type == TYPE_EMPTY || q.type == TYPE_ESSAY) {
                                    auto = false;
                                }
                            }
                            if (auto) {
                                for (Question q : questions) {
                                    q.teacherJudges.set(studentIndex, autoTeacherJudge(q, studentIndex));
                                }
                                s.collected = true;
                            }
                        } catch (Exception e) {
                            Log.d("test", "学生提交的和本次问题对应不上。。。有问题");
                            e.printStackTrace();
                        }
                    }
                }
                adapter.notifyDataSetChanged();

                //3.如果所有学生已经提交，停止答题。
                boolean allSubmit = true;
                for (Student s : students) {
                    allSubmit = allSubmit & s.submited;
                }
                if (allSubmit) {
                    timeup = true;
                    mHandler.removeCallbacksAndMessages(null);
                    stopBtn.setBackgroundResource(R.drawable.endbutton_false);
                    if (type == TYPE_QUESTION_CEPING) {
                        hint.setText("测评结束");
                    } else {
                        hint.setText("答题结束");
                    }
                }

                refreshUI();
            }
        });
    }

    private int autoTeacherJudge(Question q, int studentIndex) {
        if (q.answerVo.content.replace("[", "").replace("]", "").replace("\"", "").replace(",", "").equals(q.studentAnswers.get(studentIndex))) {
            return 2;
        }
        return 1;
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(ResultActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.icon = (RoundedImageView) rowView.findViewById(R.id.icon);
                holder.collected = (ImageView) rowView.findViewById(R.id.collected);
                holder.cover = (ImageView) rowView.findViewById(R.id.cover);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Student s = students.get(position);
            holder.name.setText(s.name);
            if (TextUtils.isEmpty(s.avatar)) {
                s.avatar = "drawable://" + R.drawable.icon;
            }
            ImageLoader.getInstance().displayImage(s.avatar, holder.icon, KWApplication.getLoaderOptions());

            //绿色表示已答题提交，灰色表示还未作答的
            if (s.submited) {
                holder.cover.setVisibility(View.VISIBLE);
                holder.cover.setImageResource(R.drawable.icon2);
            } else {
                holder.cover.setVisibility(View.GONE);
            }
            if (s.collected) {
                holder.collected.setVisibility(View.VISIBLE);
            } else {
                holder.collected.setVisibility(View.GONE);
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public RoundedImageView icon;
            public ImageView collected;
            public ImageView cover;
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

    public void stop(View view) {
        toast("时间到，停止作答");
        questionTime = 0;
        timeup = true;
        mHandler.removeCallbacksAndMessages(null);
        stopBtn.setBackgroundResource(R.drawable.endbutton_false);
        sendTimeupCommand();
    }

    public void uploadResult() {
        try {
            String url = Constant.clientUrl + "/device/teacher/course/student/result";
            Log.d("test", "question url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            String param = null;
            if (type == TYPE_QUESTION_CEPING) {
                JSONArray array = new JSONArray();
                for (Question q : questions) {
                    JSONObject o2 = new JSONObject();
                    o2.put("pushType", type);
                    o2.put("questionId", q.id);
                    o2.put("courseId", q.courseId);
                    o2.put("questionContent", q.answerVo.content);//正确答案
                    o2.put("questionImg", q.img);
                    o2.put("questionOptions", q.operation);
                    JSONArray temp = new JSONArray();
                    int studentCount = students.size();
                    for (int studentIndex = 0; studentIndex < studentCount; studentIndex++) {
                        Student s = students.get(studentIndex);
                        JSONObject o = new JSONObject();
                        o.put("imei", s.imei);
                        o.put("name", s.name);
                        o.put("content", q.studentAnswers.get(studentIndex));
                        int status = 0;
                        if (q.teacherJudges.get(studentIndex) == 1) {
                            status = 0;
                        } else if (q.teacherJudges.get(studentIndex) == 2) {
                            status = 1;
                        } else {
                            status = 2;
                        }
                        o.put("status", status);//0错误 1正确 2未作答
                        o.put("type", q.type);//问题本身类型
                        temp.put(o);
                    }
                    o2.put("students", temp);
                    array.put(o2);
                }
                param = array.toString();
            } else {
                //其他
                JSONArray array = new JSONArray();
                Student s = students.get(0);
                Question q = questions.get(0);
                JSONObject o = new JSONObject();
                o.put("imei", s.imei);
                o.put("name", s.name);
                o.put("pushType", type);
                o.put("content", q.studentAnswers.get(0));//学生答案
                o.put("questionContent", q.answerVo.content);//正确答案
                o.put("questionId", q.id);
                o.put("courseId", q.courseId);
                o.put("questionImg", q.img);
                o.put("questionOptions", q.operation);
                int status = 0;
                if (q.teacherJudges.get(0) == 1) {
                    status = 0;
                } else if (q.teacherJudges.get(0) == 2) {
                    status = 1;
                }
                o.put("status", status);//0错误 1正确 2未作答
                o.put("type", q.type);//问题本身类型
                o.put("question", new JSONArray());
                array.put(o);
                param = array.toString();
            }
            Log.d("test", "question param array = " + param);
            StringEntity stringEntity = new StringEntity(param, "utf-8");
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        JSONArray data = o.getJSONArray("data");
                        int size = data.length();
                        StringBuilder ping = new StringBuilder();
                        String courseId = "";
                        for (int i = 0; i < size; i++) {
                            String temp = data.getJSONObject(i).toString().replace("{", "").replace("\"", "").replace("}", "");
                            courseId = temp.split(":")[0];
                            String examinationId = temp.split(":")[1];
                            ping.append(examinationId).append(",");
                        }
                        ping.deleteCharAt(ping.length() - 1);
                        Utils.courseOperation(ResultActivity.this, courseId, type + 2, ping.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    Utils.check301(ResultActivity.this, s, "questionResult");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        //1.答题是否结束
        if (!timeup) {
            toast("答题尚未结束，请先停止作答");
            return;
        }
        //2.TODO 提示未提交的

        //3.提示没有批改的
        boolean allCollect = true;
        for (Student s : students) {
            if (s.submited) {//只查看已经提交的。
                allCollect = allCollect | s.collected;
            }
        }
        if (!allCollect) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage("有学生答案尚未批改，是否退出本次问答/测评")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            sendEndCommand();
                        }
                    })
                    .setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }

        //如果是老师中断的话，不用提交结果
        uploadResult();
        finish();
    }

    private void sendTimeupCommand() {
        ZbusHost.questionTimeup(ResultActivity.this, students, new OnListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private void sendEndCommand() {
        ZbusHost.questionEnd(ResultActivity.this, students, new OnListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFailure() {
                toast("发送退出命令失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 8888 && resultCode == 8888) {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void qiangdaOneStudent(final String studentIMEI) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Student s = getStudentByIMEIFromClass(studentIMEI);
                if (s == null) {
                    Log.d("test", "神秘学生，不可能");
                    return;
                }
                Log.d("test", "已经有其他人抢到了，点了也没有用");
                //给他们发送抢答失败消息
                String qiangdaStudentName = students.get(0).name;
                ZbusHost.qiangdaResult(ResultActivity.this, s, 0, qiangdaStudentName, null);
            }
        });
    }

    private Student getStudentByIMEIFromClass(String imei) {
        for (Student s : KWApplication.students) {
            if (s.imei.equals(imei)) {
                return s;
            }
        }
        return null;
    }
}
