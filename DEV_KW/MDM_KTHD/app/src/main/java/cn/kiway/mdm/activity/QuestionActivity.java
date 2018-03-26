package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.mdm.App;
import cn.kiway.mdm.model.AnswerVo;
import cn.kiway.mdm.model.Choice;
import cn.kiway.mdm.model.Question;
import cn.kiway.mdm.utils.Constant;
import cn.kiway.mdm.utils.JsAndroidInterface2;
import cn.kiway.mdm.utils.MyWebViewClient;
import cn.kiway.mdm.utils.UploadUtil;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.model.Question.TYPE_EMPTY;
import static cn.kiway.mdm.model.Question.TYPE_ESSAY;

/**
 * Created by Administrator on 2018/1/3.
 */

public class QuestionActivity extends BaseActivity {

    public static final int TYPE_QUESTION_DIANMINGDA = 1;
    public static final int TYPE_QUESTION_QIANGDA = 2;
    public static final int TYPE_QUESTION_SUIJICHOUDA = 3;
    public static final int TYPE_QUESTION_CEPING = 4;

    private int questionType;
    private int questionTime;
    private ArrayList<Question> questions;
    public static final int SELECT_PHOTO = 8888;

    private int current = 0;

    private TextView time;
    private Button prev;
    private Button next;
    private TextView type;
    private TextView content;
    private TextView content2;
    private LinearLayout imgLL;
    private LinearLayout imgLL2;
    private Button showAnswerBtn;
    private LinearLayout ll_answer;
    private GridView answerGV;
    private EditText answerET;
    private RelativeLayout answerWV;
    private RelativeLayout wvContainer;
    private MyAdapter adapter;
    private ArrayList<Choice> choices = new ArrayList<>();

    private boolean submited;//是否提交
    private boolean collected;//是否批改
    private boolean timeup;//是否时间到


    private WebView currentWV;
    private ArrayList<JsAndroidInterface2> jsList = new ArrayList<>();

    private ImageButton right;
    private ImageButton wrong;
    private LinearLayout ll_teacher_judge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionType = getIntent().getIntExtra("questionType", 0);
        questionTime = getIntent().getIntExtra("questionTime", 0);
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");

        initView();
        initData();
        initListener();
        refresh();
    }

    @Override
    public void initView() {
        super.initView();
        time = (TextView) findViewById(R.id.time);
        prev = (Button) findViewById(R.id.prev);
        next = (Button) findViewById(R.id.next);
        type = (TextView) findViewById(R.id.type);
        content = (TextView) findViewById(R.id.content);
        content2 = (TextView) findViewById(R.id.content2);
        imgLL = (LinearLayout) findViewById(R.id.imgLL);
        imgLL2 = (LinearLayout) findViewById(R.id.imgLL2);
        showAnswerBtn = (Button) findViewById(R.id.showAnswerBtn);
        ll_answer = (LinearLayout) findViewById(R.id.ll_answer);
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);
        ll_teacher_judge = (LinearLayout) findViewById(R.id.ll_teacher_judge);
        answerGV = (GridView) findViewById(R.id.answerGV);
        answerET = (EditText) findViewById(R.id.answerET);
        answerWV = (RelativeLayout) findViewById(R.id.answerWV);
        wvContainer = (RelativeLayout) findViewById(R.id.wvContainer);
        adapter = new MyAdapter();
        answerGV.setAdapter(adapter);

        //右上角提交按钮
        ok.setVisibility(View.VISIBLE);

        if (questionType == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
        } else if (questionType == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
        } else if (questionType == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
        } else if (questionType == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
        }
    }

    private void initData() {
        if (questionTime == 0) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }

        //动态添加webview
        int count = 0;
        for (Question q : questions) {
            if (q.type == Question.TYPE_ESSAY) {
                q.wbIndex = count;
                count++;
            }
        }
        if (count == 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            WebView wv = new WebView(this);
            setWebview(wv);
            wvContainer.addView(wv, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            String url = "file:///android_asset/whiteboard/index.html";
            Log.d("test", "url = " + url);
            wv.loadUrl(url);
        }
    }

    private void setWebview(WebView wv) {
        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = wv.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(wv.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        //settings.setDomStorageEnabled(true); 画板不能用这个属性
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        wv.setWebViewClient(new MyWebViewClient());
        wv.setVerticalScrollBarEnabled(false);
        wv.setWebChromeClient(new WebChromeClient());

        JsAndroidInterface2 js = new JsAndroidInterface2(this);
        wv.addJavascriptInterface(js, "wx");
        jsList.add(js);
    }

    private void initListener() {
    }

    public void prev(View view) {
        saveStudentAnswer();
        current--;
        refresh();
    }

    public void next(View view) {
        saveStudentAnswer();
        current++;
        refresh();
    }

    private void refresh() {
        prev.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        if (current == 0) {
            prev.setVisibility(View.GONE);
        }
        if (current == questions.size() - 1) {
            next.setVisibility(View.GONE);
        }

        //问题区域
        final Question q = questions.get(current);
        if (q.type == Question.TYPE_SINGLE) {
            type.setText("单选题");
        } else if (q.type == Question.TYPE_MULTI) {
            type.setText("多选题");
        } else if (q.type == TYPE_EMPTY) {
            type.setText("填空题");
        } else if (q.type == Question.TYPE_JUDGE) {
            type.setText("判断题");
        } else if (q.type == Question.TYPE_ESSAY) {
            type.setText("问答题");
        }
        content.setText(q.content);
        imgLL.removeAllViews();
        if (!TextUtils.isEmpty(q.img)) {
            String imgs[] = q.img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                final String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.showBigImage(QuestionActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, App.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL.addView(iv, lp);
            }
        }
        //标准答案区域
        AnswerVo a = q.answerVo;
        content2.setText(a.content);
        imgLL2.removeAllViews();
        if (!TextUtils.isEmpty(a.img)) {
            String imgs[] = a.img.split(",");
            for (int i = 0; i < imgs.length; i++) {
                final String imageUrl = imgs[i];
                ImageView iv = new ImageView(this);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.showBigImage(QuestionActivity.this, new String[]{imageUrl}, 0);
                    }
                });
                ImageLoader.getInstance().displayImage(imgs[i], iv, App.getLoaderOptions());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10, 10, 10, 10);
                imgLL2.addView(iv, lp);
            }
        }

        //reset
        if (collected) {
            ll_teacher_judge.setVisibility(View.VISIBLE);
            if (q.teacherJudge == 1) {
                right.setBackgroundResource(R.drawable.right1);
                wrong.setBackgroundResource(R.drawable.wrong2);
            } else if (q.teacherJudge == 2) {
                right.setBackgroundResource(R.drawable.right2);
                wrong.setBackgroundResource(R.drawable.wrong1);
            }
        } else {
            ll_teacher_judge.setVisibility(View.GONE);
        }

        String studentAnswer = q.studentAnswer;
        //学生提交答案区域
        if (q.type == Question.TYPE_SINGLE || q.type == Question.TYPE_MULTI) {
            answerGV.setVisibility(View.VISIBLE);
            answerET.setVisibility(View.INVISIBLE);
            answerWV.setVisibility(View.INVISIBLE);
            choices.clear();
            String choose[] = q.options.replace("\"", "").replace("[", "").replace("]", "").split(",");
            for (String temp : choose) {
                if (studentAnswer.contains(temp)) {
                    choices.add(new Choice(temp, true));
                } else {
                    choices.add(new Choice(temp, false));
                }
            }
            adapter.notifyDataSetChanged();
        } else if (q.type == TYPE_EMPTY) {
            answerGV.setVisibility(View.INVISIBLE);
            answerET.setVisibility(View.VISIBLE);
            answerWV.setVisibility(View.INVISIBLE);
            answerET.setText(studentAnswer);
        } else if (q.type == Question.TYPE_JUDGE) {
            answerGV.setVisibility(View.VISIBLE);
            answerET.setVisibility(View.INVISIBLE);
            answerWV.setVisibility(View.INVISIBLE);
            choices.clear();
            if (studentAnswer.contains("对")) {
                choices.add(new Choice("对", true));
                choices.add(new Choice("错", false));
            } else if (studentAnswer.contains("错")) {
                choices.add(new Choice("对", false));
                choices.add(new Choice("错", true));
            } else {
                choices.add(new Choice("对", false));
                choices.add(new Choice("错", false));
            }
            adapter.notifyDataSetChanged();
        } else if (q.type == Question.TYPE_ESSAY) {
            answerGV.setVisibility(View.INVISIBLE);
            answerET.setVisibility(View.INVISIBLE);
            answerWV.setVisibility(View.VISIBLE);
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
                    questionTimeup(false);
                }
            }
            time.setText("时间：" + Utils.secToTime(questionTime));
        }
    };

    public void questionTimeup(final boolean fromTeacher) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeup = true;
                mHandler.removeCallbacksAndMessages(null);
                if (fromTeacher) {
                } else {
                    toast("答题时间结束");
                }
                ok.setVisibility(View.GONE);
                forbid();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(QuestionActivity.this);
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.item_answer, null);
                holder = new ViewHolder();

                holder.choose = (TextView) view.findViewById(R.id.choose);

                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Choice c = choices.get(position);
            holder.choose.setText(c.content);
            if (c.selected) {
                holder.choose.setBackgroundResource(R.drawable.green);
            } else {
                holder.choose.setBackgroundResource(R.drawable.gray);
            }

            holder.choose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Question q = questions.get(current);
                    if (q.type == Question.TYPE_SINGLE || q.type == Question.TYPE_JUDGE) {
                        for (Choice c : choices) {
                            c.selected = false;
                        }
                        Choice c = choices.get(position);
                        c.selected = true;
                        adapter.notifyDataSetChanged();
                    } else if (q.type == Question.TYPE_MULTI) {
                        Choice c = choices.get(position);
                        c.selected = !c.selected;
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            return view;
        }

        public class ViewHolder {
            public TextView choose;
        }

        @Override
        public int getCount() {
            return choices.size();
        }

        @Override
        public Choice getItem(int arg0) {
            return choices.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data == null) {
                return;
            }
            boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
            ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (!isOrig) {
                Log.d("test", "压缩前大小" + new File(images.get(0).path).length());
                File newFile = CompressHelper.getDefault(this).compressToFile(new File(images.get(0).path));
                images.get(0).path = newFile.getAbsolutePath();
                images.get(0).size = newFile.length();
                Log.d("test", "压缩后大小" + images.get(0).size);
            }
            String path = images.get(0).path;
            currentWV.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        }
    }

    private int getImageCount = 0;

    public void clickSubmit(View view) throws JSONException {
        if (submited) {
            toast("你已经提交过了，请不要重复提交");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("提示");
        builder.setMessage("确定要提交答题结果吗？提交后无法修改");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //1.save答案
                saveStudentAnswer();

                if (jsList.size() == 0) {
                    doSubmitAnswer();
                    return;
                }
                //2.如果有问答题，先上传图片
                //size*10秒计时

                new Thread() {
                    @Override
                    public void run() {
                        showPD();
                        getImageCount = 0;
                        for (final Question q : questions) {
                            if (q.type == Question.TYPE_ESSAY) {
                                final WebView wv = (WebView) wvContainer.getChildAt(q.wbIndex);
                                jsList.get(q.wbIndex).setListener(new JsAndroidInterface2.OnListener() {
                                    @Override
                                    public void onImage(String filepath) {
                                        Log.d("test", "onImage = " + filepath);
                                        String token = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                                        String result = UploadUtil.uploadFile(filepath, Constant.clientUrl + "common/file?x-auth-token=" + token, new File(filepath).getName());
                                        String url = null;
                                        try {
                                            url = new JSONObject(result).getJSONObject("data").getString("url");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            toast("上传图片失败");
                                            hidePD();
                                            return;
                                        }
                                        q.studentAnswer = url;
                                        getImageCount++;
                                        if (getImageCount == jsList.size()) {
                                            //3.上传
                                            hidePD();
                                            doSubmitAnswer();
                                        }
                                    }
                                });
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        wv.loadUrl("javascript:getImage()");
                                    }
                                });
                            }
                        }

                    }
                }.start();
            }
        });
        builder.show();
    }

    private void doSubmitAnswer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONArray answerArray = new JSONArray();
                    for (Question q : questions) {
                        JSONObject answerObj = new JSONObject();
                        answerObj.put("qid", q.id);
                        answerObj.put("qtype", q.type);
                        answerObj.put("qanswer", q.studentAnswer);
                        answerArray.put(answerObj);
                    }
                    Log.d("test", "打印学生答题情况:" + answerArray.toString());
                    //整理答案
                    String answer = "answer_" + answerArray.toString();
                    boolean ret = Utils.sendToServer(getApplicationContext(), answer);
                    if (ret) {
                        //1.禁止答题
                        forbid();
                        //2.修改UI
                        submited = true;
                        mHandler.removeCallbacksAndMessages(null);
                        ok.setVisibility(View.GONE);
                        //3.自动批改
                        boolean auto = true;
                        for (Question q : questions) {
                            if (q.type == TYPE_EMPTY || q.type == TYPE_ESSAY) {
                                auto = false;
                            }
                        }
                        if (auto) {
                            toast("提交答案成功，已经自动批改，请查看结果");
                            collected = true;
                            for (Question q : questions) {
                                q.teacherJudge = autoTeacherJudge(q);
                            }
                            refresh();
                        } else {
                            toast("提交答案成功，等待老师批改");
                        }
                    } else {
                        toast("提交答案失败");
                    }
                    hidePD();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void forbid() {
        findViewById(R.id.forbid).setVisibility(View.VISIBLE);
    }

    public void clickForbid(View view) {
        toast("不能再作答了");
    }

    private int autoTeacherJudge(Question q) {
        if (q.answerVo.content.replace("[", "").replace("]", "").replace("\"", "").replace(",", "").equals(q.studentAnswer)) {
            return 2;
        }
        return 1;
    }

    //保存学生答题的答案，上下切换的时候显示用。。。
    private void saveStudentAnswer() {
        Question q = questions.get(current);
        q.studentAnswer = "";
        if (q.type == Question.TYPE_SINGLE) {
            String temp = "";
            for (Choice c : choices) {
                if (c.selected) {
                    temp = c.content;
                }
            }
            q.studentAnswer = temp;
        } else if (q.type == Question.TYPE_MULTI) {
            String temp2 = "";
            for (Choice c : choices) {
                if (c.selected) {
                    temp2 += c.content;
                }
            }
            q.studentAnswer = temp2;
        } else if (q.type == TYPE_EMPTY) {
            String temp2 = answerET.getText().toString().trim();
            q.studentAnswer = temp2;
        } else if (q.type == Question.TYPE_JUDGE) {
            String temp = "";
            for (Choice c : choices) {
                if (c.selected) {
                    temp = c.content;
                }
            }
            q.studentAnswer = temp;
        } else if (q.type == Question.TYPE_ESSAY) {
            q.studentAnswer = "保存的截图地址";
        }
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        if (wvContainer.getVisibility() == View.VISIBLE) {
            wvContainer.setVisibility(View.INVISIBLE);
            return;
        }
        if (!timeup && !submited) {
            toast("你尚未提交答案，不能退出本次问答/测评");
            return;
        }
        if (!timeup && !collected) {
            toast("老师还没有批改，不能退出本次问答/测评");
            return;
        }
        finish();
    }

    public void setCollection(final String collection) {
        collected = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toast("老师已经批改了，请查看结果吧");
                try {
                    JSONArray array = new JSONArray(collection);
                    int count = array.length();
                    for (int i = 0; i < count; i++) {
                        JSONObject o = array.getJSONObject(i);
                        String qid = o.getString("qid");
                        int teacherJudge = o.getInt("teacherJudge");
                        //首先要保持顺序正确
                        Question q = questions.get(i);
                        q.teacherJudge = teacherJudge;
                    }

                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "老师批改的和本次问题对应不上。。。有问题");
                }
            }
        });
    }


    public void showWV(View view) {
        wvContainer.setVisibility(View.VISIBLE);
        int count = wvContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            wvContainer.getChildAt(i).setVisibility(View.INVISIBLE);
        }
        Question q = questions.get(current);
        currentWV = (WebView) wvContainer.getChildAt(q.wbIndex);
        currentWV.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
