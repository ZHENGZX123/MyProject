package cn.kiway.mdm.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
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
import cn.kiway.mdm.entity.TeachingContentVo;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.HttpDownload;
import cn.kiway.mdm.util.Utils;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static android.R.attr.type;
import static android.R.id.list;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_DIANMINGDA;
import static cn.kiway.mdm.activity.StudentGridActivity.TYPE_TONGJI;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE0;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE_DOC;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE_END;
import static cn.kiway.mdm.teacher.R.id.close;
import static cn.kiway.mdm.util.FileUtils.DOWNFILEPATH;
import static cn.kiway.mdm.util.ResultMessage.RECORD_REQUEST_CODE;
import static cn.kiway.mdm.util.Utils.check301;
import static io.netty.util.concurrent.FastThreadLocal.size;

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
        //bind放这里好像不对。。。
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
        }
    }

    @Override
    public void onBackPressed() {
        if (x5FileLayout.isShown()) {
            readerView.onStop();
            readerView = null;
            x5FileLayout.removeAllViews();
            x5FileLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
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

    private boolean tuiping = false;

    public void tuiping(View view) {
        //先接入声网
        if (tuiping) {
            toast("结束推屏");
            mRtcEngine.leaveChannel();
            stopCapture();
        } else {
            toast("开始推屏");
            initModules();
            startCapture();
            mRtcEngine.joinChannel(null, "kiway", "", 0);
        }
        tuiping = !tuiping;
    }

    public void chaping(View view) {
        //查看学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, MultiScreenActivity.class));
    }

    public void suoping(View view) {
        //锁定学生屏幕，需要获取学生列表。
    }

    public void wenjian(View view) {
        //给学生发文件，需要获取学生列表。
    }

    public void shezhi(View view) {
        //设置？？？不知道是什么。测试用
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
                int count = 0;
                for (KnowledgePoint kp : course.knowledgePoints) {
                    if (kp.selected) {
                        count++;
                    }
                }
                if (count == 0) {
                    toast("请选择一个知识点");
                    return;
                }
                dialog.dismiss();
                //发送选中的知识点
                startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type", TYPE_TONGJI));
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

    private int questionTime = 120;

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
        timeEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    plus.setVisibility(View.VISIBLE);
                    settime.setVisibility(View.VISIBLE);
                    minus.setVisibility(View.VISIBLE);
                } else {
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

        ListView lv = (ListView) dialog.findViewById(R.id.lv);
        QuestionAdapter adapter = new QuestionAdapter();
        lv.setAdapter(adapter);

        kaishidati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (Question q : course.questions) {
                    if (q.selected) {
                        count++;
                    }
                }
                if (count == 0) {
                    toast("请选择一个问题");
                    return;
                }

                dialog.dismiss();
                if (type == TYPE_QUESTION_DIANMINGDA) {
                    //点名答要手动选人
                    startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type", TYPE_DIANMINGDA).putExtra("questionTime", questionTime));
                } else if (type == TYPE_QUESTION_SUIJICHOUDA) {
                    //随机抽答，弹出抽取框
                    showChoudaqiDialog();
                } else if (type == TYPE_QUESTION_QIANGDA) {
                    //TODO 抢答1个学生
                    ArrayList<Student> selectStudents = new ArrayList<>();
                    selectStudents.add(students.get(0));
                    startActivity(new Intent(Course0Activity.this, ResultActivity1.class).putExtra("type", type).putExtra("students", selectStudents).putExtra("questionTime", questionTime));
                } else {
                    //测评是全班的
                    startActivity(new Intent(Course0Activity.this, ResultActivity1.class).putExtra("type", type).putExtra("students", students).putExtra("questionTime", questionTime));
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

    private void showChoudaqiDialog() {
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
                new Thread() {
                    @Override
                    public void run() {
                        int rand = new Random().nextInt(100);
                        for (int i = 0; i < rand; i++) {
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Student s = students.get(i % students.size());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    name.setText(s.name);
                                }
                            });
                            if (i == rand - 1) {
                                toast("随机到的学生是：" + s.name);
                                ArrayList<Student> selectStudents = new ArrayList<>();//getRandomStudents();
                                selectStudents.add(s);
                                startActivity(new Intent(Course0Activity.this, ResultActivity1.class).putExtra("type", TYPE_QUESTION_SUIJICHOUDA).putExtra("students", selectStudents).putExtra("questionTime", questionTime));
                            }
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
    private RecordService recordService;

    public void startRecord() {
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void stopRecord() {
        if (recordService.isRunning()) {
            recordService.stopRecord();
        }
        //上传视频文件。。。
        //上传课程记录。。。
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

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
                holder.line2.setVisibility(View.VISIBLE);
                //add content0
                addContent0(holder, s.teachingContentVo);
            } else if (s.type == TYPE_DOC) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.VISIBLE);
                holder.type_2RL.setVisibility(View.GONE);
                holder.ball.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);

                //add content1
                addContent1(holder);

            } else if (s.type == TYPE_END) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.GONE);
                holder.type_2RL.setVisibility(View.VISIBLE);
                holder.ball.setVisibility(View.GONE);
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
                    Utils.xiake(Course0Activity.this);
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
                        //放大显示
                        ViewPagerActivity.sDrawables = new String[]{imageUrl};
                        Intent intent = new Intent(Course0Activity.this, ViewPagerActivity.class);
                        intent.putExtra("position", 0);
                        startActivity(intent);
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

    private class QuestionAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public QuestionAdapter() {
            inflater = LayoutInflater.from(Course0Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_question, null);
                holder = new ViewHolder();

                holder.imgLL = (LinearLayout) rowView.findViewById(R.id.imgLL);
                holder.content = (TextView) rowView.findViewById(R.id.content);
                holder.select = (CheckBox) rowView.findViewById(R.id.select);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Question s = course.questions.get(position);
            holder.content.setText(s.content);
            holder.select.setChecked(s.selected);
            holder.select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.selected = !s.selected;
                    notifyDataSetChanged();
                }
            });

            holder.imgLL.removeAllViews();
            if (!TextUtils.isEmpty(s.img)) {
                String imgs[] = s.img.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    String imageUrl = imgs[i];
                    ImageView iv = new ImageView(Course0Activity.this);
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //放大显示
                            ViewPagerActivity.sDrawables = new String[]{imageUrl};
                            Intent intent = new Intent(Course0Activity.this, ViewPagerActivity.class);
                            intent.putExtra("position", 0);
                            startActivity(intent);
                        }
                    });
                    ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10, 10, 10, 10);
                    holder.imgLL.addView(iv, lp);
                }
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView content;
            public CheckBox select;
            public LinearLayout imgLL;
        }

        @Override
        public int getCount() {
            return course.questions.size();
        }

        @Override
        public Question getItem(int arg0) {
            return course.questions.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }
}
