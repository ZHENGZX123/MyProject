package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.smtt.sdk.TbsReaderView;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.AttachInfoVo;
import cn.kiway.mdm.entity.Course;
import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.entity.StudentQuestion;
import cn.kiway.mdm.entity.TeachingContentVo;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;

import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_CHAPING;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_DIANMINGDA;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_SUOPING;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_TONGJI;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_WENJIAN;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE0;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE_DOC;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE_END;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.ResultMessage.RECORD_REQUEST_CODE;
import static cn.kiway.mdm.util.Utils.check301;
import static cn.kiway.mdm.util.Utils.showBigImage;

/**
 * Created by Administrator on 2017/12/28.
 */

//未上课
public class Course0Activity extends ScreenSharingActivity {

    private FrameLayout x5FileLayout;
    private TbsReaderView readerView;
    private ListView lv;
    private CourseAdapter adapter;
    private ArrayList<KnowledgePoint> knowledgePoints = new ArrayList<>();
    private Course course;
    private ArrayList<Student> students;

    public static final int TYPE_QUESTION_DIANMINGDA = 1;
    public static final int TYPE_QUESTION_QIANGDA = 2;
    public static final int TYPE_QUESTION_SUIJICHOUDA = 3;
    public static final int TYPE_QUESTION_CEPING = 4;

    private ImageView tuipingIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course0);

        course = (Course) getIntent().getSerializableExtra("course");
        students = (ArrayList<Student>) getIntent().getSerializableExtra("students");

        initView();
        initData();
        initRecord();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText(course.name);
        x5FileLayout = (FrameLayout) findViewById(R.id.x5FileLayout);
        lv = (ListView) findViewById(R.id.KnowledgePointLV);
        adapter = new CourseAdapter();
        lv.setAdapter(adapter);

        tuipingIV = (ImageView) findViewById(R.id.tuipingIV);
    }

    public void initData() {
        //1.知识点详情
        try {
            showPD();
            String url = KWApplication.clientUrl + "/device/teacher/course/" + course.id;
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "course onSuccess = " + ret);
                    dismissPD();
                    try {
                        JSONObject data = new JSONObject(ret).getJSONObject("data");
                        course = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<Course>() {
                        }.getType());
                        knowledgePoints.addAll(course.knowledgePoints);
                        //add attchment
                        if (!TextUtils.isEmpty(course.attach)) {
                            KnowledgePoint kp = new KnowledgePoint();
                            kp.type = TYPE_DOC;
                            knowledgePoints.add(kp);
                        }
                        //add end
                        KnowledgePoint end = new KnowledgePoint();
                        end.type = TYPE_END;
                        knowledgePoints.add(end);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "course onFailure = " + s);
                    if (!check301(Course0Activity.this, s, "coursedetail")) {
                        toast("请求失败，请稍后再试");
                        dismissPD();
                        finish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }

    private void initRecord() {
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            KWApplication.recordService.setMediaProject(mediaProjection);
            KWApplication.recordService.startRecord();
        }
    }

    @Override
    public void onBackPressed() {
        if (x5FileLayout.isShown()) {
            readerView.onStop();
            readerView = null;
            x5FileLayout.removeAllViews();
            x5FileLayout.setVisibility(View.GONE);
            return;
        }
        if (tuiping) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage("退出将会关闭屏幕推送")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            endTuiping();
                            Course0Activity.super.onBackPressed();
                        }
                    }).setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }
        super.onBackPressed();
    }

    //-------------------------tools1----------------------

    public void jieping(View view) {
        Utils.GetandSaveCurrentImage(this);
    }

    public void paizhao(View view) {
        //众人通是拍照后，放在画布上。
        String picPath = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivity(intent);
    }

    public void huabi(View view) {
        //等彭毅
        startActivity(new Intent(this, WhiteBoardActivity.class));
    }

    private boolean tuiping;

    public void tuiping(View view) {
        //先接入声网
        setTuipingIV();
        if (tuiping) {
            endTuiping();
        } else {
            startTuiping();
        }
        tuiping = !tuiping;
        setTuipingIV();
    }

    private void startTuiping() {
        toast("开始推屏");
        initModules();
        startCapture();
        mRtcEngine.joinChannel(null, "kiway", "", 0);
    }

    private void endTuiping() {
        toast("结束推屏");
        mRtcEngine.leaveChannel();
        stopCapture();
    }

    private void setTuipingIV() {
        if (tuiping) {
            tuipingIV.setBackgroundResource(R.drawable.screen_control2);
        } else {
            tuipingIV.setBackgroundResource(R.drawable.screen_control1);
        }
    }

    public void chaping(View view) {
        //查看学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_CHAPING));
    }

    public void suoping(View view) {
        //锁定学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_SUOPING));
    }

    public void wenjian(View view) {
        //1.先选择一个文件
        String selectFilePath = "/mnt/sdcard/test.jpg";
        //2.再选择学生
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_WENJIAN).putExtra("filePath", selectFilePath));
    }


    public void shezhi(View view) {
        //设置？？？不知道是什么。
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_ask);
        dialog.show();

        Button close = (Button) dialog.findViewById(R.id.close);
        ListView lv = (ListView) dialog.findViewById(R.id.lv);
        sqAdapter = new StudentQuestionAdapter();
        lv.setAdapter(sqAdapter);

        //假数据
        studentQuestions.add(new StudentQuestion(1, "老师好", 0, "", "1516669301000", "小明", ""));
        studentQuestions.add(new StudentQuestion(2, "", 5, "http://", "1516669301000", "小红", ""));
        studentQuestions.add(new StudentQuestion(1, "这里没有听懂", 0, "", "1516669301000", "小王", ""));
        sqAdapter.notifyDataSetChanged();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //-------------------------tools2----------------------
    public void tongji(View view) {
        Log.d("test", "course.knowledgePoints = " + course.knowledgePoints);
        //统计type=1的知识点的个数
        if (course.knowledgePoints == null || course.knowledgePoints.size() == 0) {
            toast("该课程暂无知识点");
            return;
        }
        //reset select
        for (KnowledgePoint kp : course.knowledgePoints) {
            kp.selected = false;
        }
        //知识点统计，给全班发送统计命令。
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_tongji1);
        dialog.show();
        Button tongji = (Button) dialog.findViewById(R.id.tongji);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button close = (Button) dialog.findViewById(R.id.close);

        ListView lv = (ListView) dialog.findViewById(R.id.lv);
        TongjiAdapter tjAdapter = new TongjiAdapter();
        lv.setAdapter(tjAdapter);

        tongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<KnowledgePoint> selectKPs = new ArrayList<>();
                for (KnowledgePoint kp : course.knowledgePoints) {
                    if (kp.selected) {
                        selectKPs.add(kp);
                    }
                }
                if (selectKPs.size() == 0) {
                    toast("请选择一个知识点");
                    return;
                }
                dialog.dismiss();
                //发送命令
                ZbusHost.tongji(Course0Activity.this, selectKPs.get(0), new OnListener() {

                    @Override
                    public void onSuccess() {
                        //toast("发送统计命令成功");
                        startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type", TYPE_TONGJI));
                    }

                    @Override
                    public void onFailure() {
                        toast("发送统计命令失败");
                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void dianmingda(View view) {
        //点名答，需要获取学生列表。
        //1.先选题目
        //2.再选学生
        selectQuestion(TYPE_QUESTION_DIANMINGDA);
    }

    public void qiangda(View view) {
        //抢答，给全班发送抢答命令。
        selectQuestion(TYPE_QUESTION_QIANGDA);
    }

    public void suijichouda(View view) {
        //随机抽答，随机找几个发命令。
        selectQuestion(TYPE_QUESTION_SUIJICHOUDA);
    }

    public void ceping(View view) {
        //测评，给全班发测评命令
        selectQuestion(TYPE_QUESTION_CEPING);
    }

    private int questionTime;
    private TextView selectCount;
    private LinearLayout questionLL;

    private void selectQuestion(int type) {
        Log.d("test", "course.questions = " + course.questions);
        if (course.questions == null || course.questions.size() == 0) {
            toast("该课程暂无题目");
            return;
        }
        //reset select
        for (Question q : course.questions) {
            q.selected = false;
        }

        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_select_question);
        dialog.show();
        Button kaishidati = (Button) dialog.findViewById(R.id.kaishidati);
        Button close = (Button) dialog.findViewById(R.id.close);
        CheckBox timeEnable = (CheckBox) dialog.findViewById(R.id.timeEnable);
        ImageButton plus = (ImageButton) dialog.findViewById(R.id.plus);
        TextView settime = (TextView) dialog.findViewById(R.id.settime);
        ImageButton minus = (ImageButton) dialog.findViewById(R.id.minus);
        RelativeLayout selectallRL = (RelativeLayout) dialog.findViewById(R.id.selectallRL);
        selectCount = (TextView) dialog.findViewById(R.id.selectCount);
        CheckBox selectAll = (CheckBox) dialog.findViewById(R.id.selectAll);

        setSelectCount();

        if (type == TYPE_QUESTION_CEPING) {
            selectallRL.setVisibility(View.VISIBLE);
        } else {
            selectallRL.setVisibility(View.GONE);
        }
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllItemSelected(selectAll.isChecked());
            }
        });

        timeEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    questionTime = 120;
                    plus.setVisibility(View.VISIBLE);
                    settime.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);
                } else {
                    questionTime = 0;
                    plus.setVisibility(View.GONE);
                    plus.setVisibility(View.GONE);
                    plus.setVisibility(View.GONE);
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最大上限未设置
                questionTime += 60;
                settime.setText(Utils.secToTime(questionTime));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //最少2分钟
                if (questionTime > 120) {
                    questionTime -= 60;
                    settime.setText(Utils.secToTime(questionTime));
                }
            }
        });

        questionLL = (LinearLayout) dialog.findViewById(R.id.questionLL);
        LayoutInflater inflater = LayoutInflater.from(this);
        for (Question s : course.questions) {
            LinearLayout rowView = (LinearLayout) inflater.inflate(R.layout.item_question, null);
            LinearLayout imgLL = (LinearLayout) rowView.findViewById(R.id.imgLL);
            TextView content = (TextView) rowView.findViewById(R.id.content);
            CheckBox select = (CheckBox) rowView.findViewById(R.id.select);
            content.setText(s.content);
            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    s.selected = isChecked;
                    if (setSelectCount()) {
                        selectAll.setChecked(true);
                    } else {
                        selectAll.setChecked(false);
                    }
                }
            });
            if (!TextUtils.isEmpty(s.img)) {
                String imgs[] = s.img.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    String imageUrl = imgs[i];
                    ImageView iv = new ImageView(Course0Activity.this);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showBigImage(Course0Activity.this, new String[]{imageUrl}, 0);
                        }
                    });
                    ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions
                            ());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 10, 10, 10);
                    imgLL.addView(iv, lp);
                }
                questionLL.addView(rowView);
            }

            kaishidati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Question> selectQuestions = new ArrayList<>();
                    for (Question q : course.questions) {
                        if (q.selected) {
                            selectQuestions.add(q);
                        }
                    }
                    if (selectQuestions.size() == 0) {
                        toast("请选择一个问题");
                        return;
                    }
                    dialog.dismiss();
                    if (type == TYPE_QUESTION_DIANMINGDA) {
                        //点名答要手动选人
                        startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type", TYPE_DIANMINGDA).putExtra("questionTime", questionTime).putExtra("questions", selectQuestions));
                    } else if (type == TYPE_QUESTION_SUIJICHOUDA) {
                        //随机抽答，弹出抽取框
                        showChoudaqiDialog(selectQuestions);
                    } else if (type == TYPE_QUESTION_QIANGDA) {
                        //TODO 抢答1个学生
                        ArrayList<Student> selectStudents = new ArrayList<>();
                        selectStudents.add(students.get(0));
                        toast(students.get(0).name + "抢答成功");
                        startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type", type).putExtra("students", selectStudents).putExtra("questionTime", questionTime).putExtra("questions", selectQuestions));
                    } else {
                        //测评是全班的
                        startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type", type).putExtra("students", students).putExtra("questionTime", questionTime).putExtra("questions", selectQuestions));
                    }
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void setAllItemSelected(boolean isChecked) {
        int childcount = questionLL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            CheckBox select = (CheckBox) questionLL.getChildAt(i).findViewById(R.id.select);
            select.setChecked(isChecked);
        }
        setSelectCount();
    }

    private boolean setSelectCount() {
        int count = 0;
        for (Question q : course.questions) {
            if (q.selected) {
                count++;
            }
        }
        selectCount.setText("选题（" + count + "/" + course.questions.size() + "）");
        return count == course.questions.size();
    }

    private void showChoudaqiDialog(ArrayList<Question> selectQuestions) {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_choudaqi);
        dialog.show();
        TextView name = (TextView) dialog.findViewById(R.id.name);
        name.setText(students.get(0).name);
        Button close = (Button) dialog.findViewById(R.id.close);
        Button start = (Button) dialog.findViewById(R.id.start);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setEnabled(false);
                new Thread() {
                    @Override
                    public void run() {
                        int rand = 25 + new Random().nextInt(25);
                        for (int i = 0; i < rand; i++) {
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Student s = students.get(i % students.size());
                            int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    name.setText(s.name);
                                    if (finalI == rand - 1) {
                                        dialog.dismiss();
                                        toast("随机到的学生是：" + s.name);
                                        ArrayList<Student> selectStudents = new ArrayList<>();//getRandomStudents();
                                        selectStudents.add(s);
                                        startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type", TYPE_QUESTION_SUIJICHOUDA).putExtra("students", selectStudents).putExtra("questionTime", questionTime).putExtra("questions", selectQuestions));
                                    }
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }

    private ArrayList<Student> getRandomStudents() {
        while (true) {
            ArrayList selectStudents = new ArrayList<Student>();
            for (Student s : students) {
                int rand = new Random().nextInt();
                if (rand % 2 == 0) {
                    selectStudents.add(s);
                }
            }
            if (selectStudents.size() != 0) {
                return selectStudents;
            }
        }
    }

    //-------------------------------录屏相关-----------------------------
    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;

    public void startRecord() {
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void stopRecord() {
        if (KWApplication.recordService.isRunning()) {
            KWApplication.recordService.stopRecord();
        }
        //上传视频文件。。。
        //上传课程记录。。。
    }
    //------------------------内容列表相关-------------------------------------------------

    private class CourseAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public CourseAdapter() {
            inflater = LayoutInflater.from(Course0Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_knowledge_point, null);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.clock = (ImageView) rowView.findViewById(R.id.clock);
                holder.type0RL = (LinearLayout) rowView.findViewById(R.id.type0RL);
                holder.type1RL = (LinearLayout) rowView.findViewById(R.id.type1RL);
                holder.type_2RL = (LinearLayout) rowView.findViewById(R.id.type_2RL);
                holder.endBtn = (Button) rowView.findViewById(R.id.endBtn);
                holder.ball = (ImageView) rowView.findViewById(R.id.ball);
                holder.line = (TextView) rowView.findViewById(R.id.line);
                holder.line2 = (TextView) rowView.findViewById(R.id.line2);
                holder.date1 = (TextView) rowView.findViewById(R.id.date1);
                holder.date2 = (TextView) rowView.findViewById(R.id.date2);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            if (position == 0) {
                holder.clock.setVisibility(View.VISIBLE);
            } else {
                holder.clock.setVisibility(View.GONE);
            }

            final KnowledgePoint s = knowledgePoints.get(position);
            holder.title.setText(s.content);

            if (s.type == TYPE0) {
                holder.type0RL.setVisibility(View.VISIBLE);
                holder.type1RL.setVisibility(View.GONE);
                holder.type_2RL.setVisibility(View.GONE);
                holder.ball.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                //add content0
                addContent0(holder, s.teachingContentVo);
            } else if (s.type == TYPE_DOC) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.VISIBLE);
                holder.type_2RL.setVisibility(View.GONE);
                holder.ball.setVisibility(View.VISIBLE);
                holder.line.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);

                //add content1
                addContent1(holder);

            } else if (s.type == TYPE_END) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.GONE);
                holder.type_2RL.setVisibility(View.VISIBLE);
                holder.ball.setVisibility(View.GONE);
                holder.line.setVisibility(View.GONE);
                holder.line2.setVisibility(View.GONE);
            }

            //FIXME
            if (position == knowledgePoints.size() - 1) {
                holder.date1.setVisibility(View.GONE);
                holder.date2.setVisibility(View.GONE);
            } else {
                holder.date1.setVisibility(View.VISIBLE);
                holder.date2.setVisibility(View.VISIBLE);
            }

            holder.endBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Utils.xiake(Course0Activity.this);
                    ZbusHost.xiake(Course0Activity.this, null);
                    Utils.endClass(Course0Activity.this, course.id);
                }
            });

            return rowView;
        }

        private void addContent1(ViewHolder holder) {
            holder.type1RL.removeAllViews();

            String attachInfo = course.attachInfo;
            if (TextUtils.isEmpty(attachInfo)) {
                return;
            }
            ArrayList<AttachInfoVo> infos = new GsonBuilder().create().fromJson(attachInfo, new TypeToken<List<AttachInfoVo>>() {
            }.getType());
            int count = infos.size();
            if (count == 0) {
                return;
            }
            for (int i = 0; i < count; i++) {
                AttachInfoVo info = infos.get(i);
                LinearLayout layout_doc = (LinearLayout) inflater.inflate(R.layout.layout_doc, null);
                ImageView icon = (ImageView) layout_doc.findViewById(R.id.icon);
                //TODO icon
                TextView name = (TextView) layout_doc.findViewById(R.id.name);
                name.setText(info.fileName);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                holder.type1RL.addView(layout_doc, lp);
                layout_doc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        downloadAndOpenFile(info.fileUrl, info.fileName);
                    }
                });
            }
        }

        private void addContent0(ViewHolder holder, TeachingContentVo teachingContentVo) {
            holder.type0RL.removeAllViews();
            //teachingContentVo.content
            TextView tv = new TextView(Course0Activity.this);
            tv.setTextColor(Color.WHITE);
            tv.setText(teachingContentVo.content);
            holder.type0RL.addView(tv);
            //teachingContentVo.img
            if (TextUtils.isEmpty(teachingContentVo.img)) {
                return;
            }
            String imgs[] = teachingContentVo.img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                String imageUrl = imgs[i];
                ImageView iv = new ImageView(Course0Activity.this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBigImage(Course0Activity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                holder.type0RL.addView(iv, lp);
            }
        }

        public class ViewHolder {
            public TextView title;
            public ImageView clock;
            public LinearLayout type0RL;
            public LinearLayout type1RL;
            public LinearLayout type_2RL;
            public Button endBtn;
            public TextView line;
            public TextView line2;
            public ImageView ball;
            public TextView date1;
            public TextView date2;
        }

        @Override
        public int getCount() {
            return knowledgePoints.size();
        }

        @Override
        public KnowledgePoint getItem(int arg0) {
            return knowledgePoints.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    public void downloadAndOpenFile(String url, String fileName) {
        //1.下载文件
        //2.打开播放

        final String folder = DOWNFILEPATH;
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        if (new File(folder + fileName).exists()) {
            //直接打开
            openFileByX5(folder + fileName);
            return;
        }
        showPD();
        new Thread() {
            @Override
            public void run() {
                int ret = new HttpDownload().downFile(url, folder, fileName);//开始下载
                dismissPD();
                if (ret == -1) {//下载失败
                    toast("下载文件失败，请稍后再试");
                    return;
                }
                openFileByX5(folder + fileName);
            }
        }.start();
    }

    private void openFileByX5(String filePath) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                x5FileLayout.setVisibility(View.VISIBLE);
                readerView = new TbsReaderView(Course0Activity.this, new TbsReaderView.ReaderCallback() {
                    @Override
                    public void onCallBackAction(Integer integer, Object o, Object o1) {

                    }
                });
                //通过bundle把文件传给x5,打开的事情交由x5处理
                Bundle bundle = new Bundle();
                //传递文件路径
                bundle.putString("filePath", filePath);
                //加载插件保存的路径
                bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
                //加载文件前的初始化工作,加载支持不同格式的插件
                boolean b = readerView.preOpen(Utils.getFileType(filePath), false);
                if (b) {
                    readerView.openFile(bundle);
                } else {
                    toast("打开文件失败");
                }
                x5FileLayout.addView(readerView);
            }
        });
    }


    private class TongjiAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public TongjiAdapter() {
            inflater = LayoutInflater.from(Course0Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_tongji, null);
                holder = new ViewHolder();

                holder.content = (TextView) rowView.findViewById(R.id.content);
                holder.select = (CheckBox) rowView.findViewById(R.id.select);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final KnowledgePoint s = course.knowledgePoints.get(position);
            holder.content.setText(s.content);
            holder.select.setChecked(s.selected);
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里是单选的...
                    for (KnowledgePoint kp : knowledgePoints) {
                        kp.selected = false;
                    }
                    s.selected = true;
                    notifyDataSetChanged();
                }
            });

            return rowView;
        }

        public class ViewHolder {
            public TextView content;
            public CheckBox select;
        }

        @Override
        public int getCount() {
            return course.knowledgePoints.size();
        }

        @Override
        public KnowledgePoint getItem(int arg0) {
            return course.knowledgePoints.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }


    private ArrayList<StudentQuestion> studentQuestions = new ArrayList<>();
    private StudentQuestionAdapter sqAdapter;

    private class StudentQuestionAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public StudentQuestionAdapter() {
            inflater = LayoutInflater.from(Course0Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student_question, null);
                holder = new ViewHolder();
                holder.content = (TextView) rowView.findViewById(R.id.content);
                holder.duration = (TextView) rowView.findViewById(R.id.duration);
                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.play = (ImageView) rowView.findViewById(R.id.play);
                holder.avatar = (ImageView) rowView.findViewById(R.id.avatar);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final StudentQuestion q = studentQuestions.get(position);
            holder.name.setText(q.studentName);
            ImageLoader.getInstance().displayImage(q.studentAvatar, holder.avatar, KWApplication.getLoaderOptions());
            if (q.type == 1) {
                holder.content.setVisibility(View.VISIBLE);
                holder.play.setVisibility(View.GONE);
                holder.duration.setVisibility(View.GONE);
                holder.content.setText(q.content);
            } else if (q.type == 2) {
                holder.content.setVisibility(View.GONE);
                holder.play.setVisibility(View.VISIBLE);
                holder.duration.setVisibility(View.VISIBLE);
                holder.duration.setText(q.duration + "秒");
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView content;
            public TextView duration;
            public TextView name;
            public ImageView play;
            public ImageView avatar;
        }

        @Override
        public int getCount() {
            return studentQuestions.size();
        }

        @Override
        public StudentQuestion getItem(int arg0) {
            return studentQuestions.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
