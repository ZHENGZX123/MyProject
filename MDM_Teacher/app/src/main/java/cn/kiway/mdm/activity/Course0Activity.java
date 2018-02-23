package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yalantis.ucrop.UCrop;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
import cn.kiway.mdm.util.UploadUtil;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;

import static cn.kiway.mdm.KWApplication.clientUrl;
import static cn.kiway.mdm.KWApplication.students;
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
import static cn.kiway.mdm.web.JsAndroidInterface.REQUEST_ORIGINAL;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile2;

/**
 * Created by Administrator on 2017/12/28.
 */

//未上课
public class Course0Activity extends ScreenSharingActivity {

    private FrameLayout x5FileLayout;
    private TbsReaderView readerView;
    private ImageView tuipingIV;
    private ListView lv;
    private CourseAdapter adapter;
    private ArrayList<KnowledgePoint> knowledgePoints = new ArrayList<>();

    private Course course;
    public static final int TYPE_QUESTION_DIANMINGDA = 1;
    public static final int TYPE_QUESTION_QIANGDA = 2;
    public static final int TYPE_QUESTION_SUIJICHOUDA = 3;
    public static final int TYPE_QUESTION_CEPING = 4;

    //选择的题目
    private ArrayList<Question> selectQuestions;

    //录课
    private boolean recording = false;
    private ArrayList<String> recordFiles = new ArrayList<>();

    //学生提问
    private Dialog studentAskDialog;
    private ArrayList<StudentQuestion> studentQuestions = new ArrayList<>();
    private StudentQuestionAdapter sqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course0);
        course = (Course) getIntent().getSerializableExtra("course");

        Utils.courseOperation(this, course.id, 1, "");
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
        hideTool(1);
        hideTool(2);
    }

    public void initData() {
        //1.知识点详情
        try {
            showPD();
            String url = clientUrl + "/device/teacher/course/" + course.id;
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "course onSuccess = " + ret);
                    hidePD();
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
                    if (!check301(Course0Activity.this, s, "coursedetail0")) {
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

    private void initRecord() {
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == RECORD_REQUEST_CODE) {
            toast("开始录制本地视频");
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            KWApplication.recordService.setMediaProject(mediaProjection);
            KWApplication.recordService.startRecord();
            recording = true;
            recordFiles.add(KWApplication.recordService.output);
        } else if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null)
                sendFile(resultUri.getPath());
        } else if (requestCode == REQUEST_ORIGINAL) {
            cropImage(picPath);
        } else if (requestCode == requsetFile2) {
            List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            String filePath = list.get(0);
            sendFile(filePath);
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
            AlertDialog dialog = builder.setTitle("提示").setMessage("屏幕推送中，是否关闭")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            endTuiping();
                        }
                    }).setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }
        if (recording) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage("本地录课中，是否关闭")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            stopRecord();
                        }
                    }).setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }
        super.onBackPressed();
    }

    //-------------------------tools1----------------------

    public void jieping(View view) {
        String filePath = Utils.GetandSaveCurrentImage(this);
        if (!filePath.equals("")) {
            if (Utils.isImage(filePath)) {
                cropImage(filePath);
            }
        }
    }

    String picPath;

    public void paizhao(View view) {
        //众人通是拍照后，放在画布上。
        picPath = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_ORIGINAL);
    }

    public void cropImage(String filePath) {
        //需要裁剪的图片路径
        Uri sourceUri = Uri.fromFile(new File(filePath));
        //裁剪完毕的图片存放路径
        Uri destinationUri = Uri.fromFile(new File(filePath.split("\\.")[0] + "1." + filePath.split("\\.")[1]));
        UCrop.of(sourceUri, destinationUri) //定义路径
                .withAspectRatio(4, 3) //定义裁剪比例 4:3 ， 16:9
                .withMaxResultSize(500, 500) //定义裁剪图片宽高最大值
                .start(this);
    }

    public void huabi(View view) {
        startActivity(new Intent(this, WhiteBoardActivity.class));
    }

    private boolean tuiping;

    public void tuiping(View view) {
        //1.发送推屏命令
        if (tuiping) {
            sendTuipingcommand(0);
        } else {
            sendTuipingcommand(1);
        }
    }

    private void sendTuipingcommand(final int status) {
        showPD();
        ZbusHost.tuiping(this, status, new OnListener() {

            @Override
            public void onSuccess() {
                hidePD();
                if (status == 1) {
                    tuiping = true;
                    tuipingIV.setBackgroundResource(R.drawable.screen_control2);
                    startTuiping();
                } else {
                    tuiping = false;
                    tuipingIV.setBackgroundResource(R.drawable.screen_control1);
                    endTuiping();
                }
            }

            @Override
            public void onFailure() {
                hidePD();
                toast("发送推屏命令失败");
            }
        });
    }

    private void startTuiping() {
        toast("开始推屏");
        initModules();
        startCapture();
        String userId = getSharedPreferences("kiway", 0).getString("userId", "");
        mRtcEngine.joinChannel(null, userId, "", 0);
    }

    private void endTuiping() {
        if (mRtcEngine == null) {
            return;
        }
        toast("结束推屏");
        mRtcEngine.leaveChannel();
        stopCapture();
        //deInitModules();
    }

    public void chaping(View view) {
        if (tuiping) {
            toast("请先停止推屏");
            return;
        }
        //查看学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_CHAPING));
    }

    public void suoping(View view) {
        //锁定学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_SUOPING));
    }

    public void wenjian(View view) {
        //1.先选择一个文件
        // String selectFilePath = "/mnt/sdcard/1514424119655.png";
        // sendFile(selectFilePath);

        new LFilePicker()
                .withActivity(this)
                .withTitle(getString(R.string.filepath3))
                .withRequestCode(requsetFile2)
                .withMutilyMode(false)
                .start();
    }


    public void sendFile(String filePath) {
        toast(R.string.chooseStudent);
        //2.再选择学生
        startActivity(new Intent(this, StudentGridActivity.class).putExtra("type", TYPE_WENJIAN).putExtra("filePath",
                filePath));
    }

    public void shezhi(View view) {
        //设置？？？不知道是什么。
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
                startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type",
                        TYPE_TONGJI).putExtra("kps", selectKPs).putExtra("courseId", course.id));
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
    private TextView selectCount;
    private LinearLayout questionLL;

    private void selectQuestion(final int type) {
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
        final ImageButton plus = (ImageButton) dialog.findViewById(R.id.plus);
        final TextView settime = (TextView) dialog.findViewById(R.id.settime);
        final ImageButton minus = (ImageButton) dialog.findViewById(R.id.minus);
        RelativeLayout selectallRL = (RelativeLayout) dialog.findViewById(R.id.selectallRL);
        selectCount = (TextView) dialog.findViewById(R.id.selectCount);
        final CheckBox selectAll = (CheckBox) dialog.findViewById(R.id.selectAll);

        calculateSelectCount();

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
                    settime.setVisibility(View.GONE);
                    minus.setVisibility(View.GONE);
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
        for (final Question s : course.questions) {
            LinearLayout rowView = (LinearLayout) inflater.inflate(R.layout.item_question, null);
            LinearLayout imgLL = (LinearLayout) rowView.findViewById(R.id.imgLL);
            TextView content = (TextView) rowView.findViewById(R.id.content);
            CheckBox select = (CheckBox) rowView.findViewById(R.id.select);
            content.setText(s.content);
            select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    s.selected = isChecked;
                    if (type != TYPE_QUESTION_CEPING) {
                        //discheck others
                        if (isChecked) {
                            disCheckOthers(course.questions, s, questionLL, select);
                        }
                    }

                    //calculate
                    if (calculateSelectCount()) {
                        selectAll.setChecked(true);
                    } else {
                        selectAll.setChecked(false);
                    }
                }

                private void disCheckOthers(ArrayList<Question> questions, Question s, LinearLayout questionLL, CheckBox select) {
                    for (Question q : questions) {
                        if (q != s) {
                            q.selected = false;
                        }
                    }

                    int count = questionLL.getChildCount();
                    for (int i = 0; i < count; i++) {
                        LinearLayout rowView = (LinearLayout) questionLL.getChildAt(i);
                        CheckBox c = (CheckBox) rowView.findViewById(R.id.select);
                        if (c != select) {
                            c.setChecked(false);
                        }
                    }
                }
            });
            if (!TextUtils.isEmpty(s.img)) {
                String imgs[] = s.img.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    final String imageUrl = imgs[i];
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
            }
            questionLL.addView(rowView);

            kaishidati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectQuestions = new ArrayList<>();
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
                        startActivity(new Intent(Course0Activity.this, StudentGridActivity.class).putExtra("type",
                                TYPE_DIANMINGDA).putExtra("questionTime", questionTime).putExtra("questions",
                                selectQuestions));
                    } else if (type == TYPE_QUESTION_SUIJICHOUDA) {
                        //随机抽答，弹出抽取框
                        showChoudaqiDialog();
                    } else if (type == TYPE_QUESTION_QIANGDA) {
                        qiangdaStudentIMEI = "";
                        ZbusHost.qiangda(Course0Activity.this, new OnListener() {

                            @Override
                            public void onSuccess() {
                                toast("发送抢答命令成功");
                            }

                            @Override
                            public void onFailure() {
                                toast("发送抢答命令失败");
                            }
                        });
                    } else {
                        //测评是全班的，发送测评命令
                        ZbusHost.questions(Course0Activity.this, selectQuestions, questionTime, new OnListener() {
                            @Override
                            public void onSuccess() {
                                startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type",
                                        TYPE_QUESTION_CEPING).putExtra("students", students).putExtra("questionTime", questionTime)
                                        .putExtra("questions", selectQuestions));
                            }

                            @Override
                            public void onFailure() {
                                toast("发送测评命令失败");
                            }
                        });
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

    private String qiangdaStudentIMEI;

    public void qiangdaOneStudent(final String studentIMEI) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Student s = getStudentByIMEI(studentIMEI);
                if (s == null) {
                    Log.d("test", "神秘学生，不可能");
                    return;
                }
                if (!TextUtils.isEmpty(qiangdaStudentIMEI)) {
                    Log.d("test", "已经有其他人抢到了，点了也没有用");
                    //给他们发送抢答失败消息
                    String qiangdaStudentName = getStudentByIMEI(qiangdaStudentIMEI).name;
                    ZbusHost.qiangdaResult(Course0Activity.this, s, 0, qiangdaStudentName, null);
                    return;
                }
                qiangdaStudentIMEI = studentIMEI;
                //给他发送抢答成功
                ZbusHost.qiangdaResult(Course0Activity.this, s, 1, qiangdaStudentIMEI, new OnListener() {
                    @Override
                    public void onSuccess() {
                        toast("发送抢答命令成功");
                        //过2秒发题目
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sendQiangdaCommand(s);
                            }
                        }.start();
                    }

                    @Override
                    public void onFailure() {
                        toast("发送抢答命令失败");
                    }
                });
            }
        });
    }

    private Student getStudentByIMEI(String imei) {
        for (Student s : students) {
            if (s.imei.equals(imei)) {
                return s;
            }
        }
        return null;
    }

    private void setAllItemSelected(boolean isChecked) {
        int childcount = questionLL.getChildCount();
        for (int i = 0; i < childcount; i++) {
            CheckBox select = (CheckBox) questionLL.getChildAt(i).findViewById(R.id.select);
            select.setChecked(isChecked);
        }
        calculateSelectCount();
    }

    private boolean calculateSelectCount() {
        int count = 0;
        for (Question q : course.questions) {
            if (q.selected) {
                count++;
            }
        }
        selectCount.setText("选题（" + count + "/" + course.questions.size() + "）");
        return count == course.questions.size();
    }

    private void showChoudaqiDialog() {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_choudaqi);
        dialog.show();
        final TextView name = (TextView) dialog.findViewById(R.id.name);
        name.setText(students.get(0).name);
        Button close = (Button) dialog.findViewById(R.id.close);
        final Button start = (Button) dialog.findViewById(R.id.start);
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
                        final int rand = 25 + new Random().nextInt(25);
                        for (int i = 0; i < rand; i++) {
                            try {
                                sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            final Student s = students.get(i % students.size());
                            final int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    name.setText(s.name);
                                    if (finalI == rand - 1) {
                                        dialog.dismiss();
                                        toast("随机到的学生是：" + s.name);
                                        sendChoudaCommand(s);
                                    }
                                }
                            });
                        }
                    }
                }.start();
            }
        });
    }

    private void sendQiangdaCommand(final Student s) {
        showPD();
        Question q = selectQuestions.get(0);
        ZbusHost.question(this, s, q, 3, questionTime, new OnListener() {

            @Override
            public void onSuccess() {
                hidePD();
                //toast("发送抢答命令成功");
                ArrayList<Student> selectStudents = new ArrayList<>();
                selectStudents.add(s);
                toast(s.name + "抢答成功");
                startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type",
                        TYPE_QUESTION_QIANGDA).putExtra("students", selectStudents).putExtra("questionTime",
                        questionTime).putExtra("questions", selectQuestions));
            }

            @Override
            public void onFailure() {
                hidePD();
                toast("发送抢答命令失败");
            }
        });
    }

    private void sendChoudaCommand(final Student s) {
        showPD();
        Question q = selectQuestions.get(0);
        ZbusHost.question(this, s, q, 2, questionTime, new OnListener() {

            @Override
            public void onSuccess() {
                hidePD();
                //toast("发送上课命令成功");
                //点名答
                ArrayList<Student> selectStudents = new ArrayList<>();
                selectStudents.add(s);

                startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type",
                        TYPE_QUESTION_SUIJICHOUDA).putExtra("students", selectStudents).putExtra("questionTime",
                        getIntent().getIntExtra("questionTime", 0)).putExtra("questions", selectQuestions));
            }

            @Override
            public void onFailure() {
                hidePD();
                toast("发送抽答命令失败");
            }
        });
    }

    //-------------------------------录屏相关-----------------------------
    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;

    public void startRecord() {
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void stopRecord() {
        toast("结束录制本地视频");
        recording = false;
        if (KWApplication.recordService.isRunning()) {
            KWApplication.recordService.stopRecord();
        }
    }

    @Override
    public void rk(View view) {
        if (recording) {
            stopRecord();
        } else {
            startRecord();
        }
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

            holder.date1.setVisibility(View.GONE);
            holder.date2.setVisibility(View.GONE);

            holder.endBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Course0Activity.this, AlertDialog.THEME_HOLO_LIGHT);
                    AlertDialog dialog = builder.setTitle("提示").setMessage("是否结束本课程")
                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //1.停止推屏
                                    if (tuiping) {
                                        tuiping = false;
                                        tuipingIV.setBackgroundResource(R.drawable.screen_control1);
                                        endTuiping();
                                    }
                                    //2.停止录制
                                    if (recording) {
                                        stopRecord();
                                    }
                                    //3.结束课程、上传视频
                                    doEndClass();
                                }
                            }).setPositiveButton(android.R.string.cancel, null).create();
                    dialog.show();
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
            ArrayList<AttachInfoVo> infos = new GsonBuilder().create().fromJson(attachInfo, new
                    TypeToken<List<AttachInfoVo>>() {
                    }.getType());
            int count = infos.size();
            if (count == 0) {
                return;
            }
            for (int i = 0; i < count; i++) {
                final AttachInfoVo info = infos.get(i);
                LinearLayout layout_doc = (LinearLayout) inflater.inflate(R.layout.layout_doc, null);
                ImageView icon = (ImageView) layout_doc.findViewById(R.id.icon);
                //TODO icon
                TextView name = (TextView) layout_doc.findViewById(R.id.name);
                name.setText(info.fileName);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
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
                final String imageUrl = imgs[i];
                ImageView iv = new ImageView(Course0Activity.this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBigImage(Course0Activity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, KWApplication.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
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

    private void doEndClass() {
        ZbusHost.xiake(Course0Activity.this, null);
        Utils.endClass(Course0Activity.this, course.id);
        new Thread() {
            @Override
            public void run() {
                try {
                    for (String s : recordFiles) {
                        File file = new File(s);
                        String accessToken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                        final String ret = UploadUtil.uploadFile(file, clientUrl + "/common/file?x-auth-token=" +
                                accessToken, file.getName());
                        JSONObject obj = new JSONObject(ret);
                        String url = obj.optJSONObject("data").optString("url");
                        Utils.addVideoRecord(Course0Activity.this, course.id, url, "mp4");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void downloadAndOpenFile2(final String url, final String fileName) {
        //1.下载文件
        //2.打开播放
        final String folder = DOWNFILEPATH;
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        if (new File(folder + fileName).exists()) {
            //直接打开
            playMusic(folder + fileName);
            return;
        }
        showPD();
        new Thread() {
            @Override
            public void run() {
                int ret = new HttpDownload().downFile(url, folder, fileName);//开始下载
                hidePD();
                if (ret == -1) {//下载失败
                    toast("下载文件失败，请稍后再试");
                    return;
                }
                playMusic(folder + fileName);
            }
        }.start();
    }

    private void playMusic(String file) {
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(file);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.start();
    }

    public void downloadAndOpenFile(final String url, final String fileName) {
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
                hidePD();
                if (ret == -1) {//下载失败
                    toast("下载文件失败，请稍后再试");
                    return;
                }
                openFileByX5(folder + fileName);
            }
        }.start();
    }

    private void openFileByX5(final String filePath) {
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
                holder.contentRL = (RelativeLayout) rowView.findViewById(R.id.contentRL);

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
                holder.contentRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = q.filepath;
                        downloadAndOpenFile2(url, System.currentTimeMillis() + ".mp3");
                    }
                });
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView content;
            public TextView duration;
            public TextView name;
            public ImageView play;
            public ImageView avatar;
            public RelativeLayout contentRL;
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

    public void questiOneStudent(String studentIMEI, final String question) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject o = new JSONObject(question);
                    int type = o.optInt("type");
                    String content = o.optString("content");
                    String time = o.optString("time");
                    String name = o.optString("name");
                    String avatar = o.optString("avatar");
                    int duration = o.optInt("duration");
                    toast("收到学生提问");

                    if (studentAskDialog == null) {
                        studentAskDialog = new Dialog(Course0Activity.this, R.style.popupDialog);
                        studentAskDialog.setContentView(R.layout.dialog_ask);
                    }
                    if (!studentAskDialog.isShowing()) {
                        studentAskDialog.show();
                    }

                    Button close = (Button) studentAskDialog.findViewById(R.id.close);
                    ListView lv = (ListView) studentAskDialog.findViewById(R.id.lv);
                    sqAdapter = new StudentQuestionAdapter();
                    lv.setAdapter(sqAdapter);

                    if (type == 1) {
                        studentQuestions.add(new StudentQuestion(1, content, 0, "", time, name, avatar));
                    } else if (type == 2) {
                        studentQuestions.add(new StudentQuestion(2, "", duration, content, time, name, avatar));
                    }
                    sqAdapter.notifyDataSetChanged();

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            studentAskDialog.dismiss();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
