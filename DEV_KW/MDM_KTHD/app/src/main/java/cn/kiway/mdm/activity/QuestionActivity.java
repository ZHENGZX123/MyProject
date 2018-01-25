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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.nanchen.compresshelper.CompressHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.kiway.mdm.App;
import cn.kiway.mdm.model.AnswerVo;
import cn.kiway.mdm.model.Choice;
import cn.kiway.mdm.model.Question;
import cn.kiway.mdm.utils.JsAndroidInterface2;
import cn.kiway.mdm.utils.MyWebViewClient;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

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
    private WebView answerWV;
    private MyAdapter adapter;
    private ArrayList<Choice> choices = new ArrayList<>();
    private Button submitBtn;

    private boolean submited;
    private boolean finished;

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
        load();
    }

    @Override
    public void initView() {
        super.initView();

        submitBtn = (Button) findViewById(R.id.submit);
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

        answerGV = (GridView) findViewById(R.id.answerGV);
        answerET = (EditText) findViewById(R.id.answerET);
        answerWV = (WebView) findViewById(R.id.answerWV);
        adapter = new MyAdapter();
        answerGV.setAdapter(adapter);


        if (questionType == TYPE_QUESTION_DIANMINGDA) {
            titleName.setText("点名答");
        } else if (questionType == TYPE_QUESTION_QIANGDA) {
            titleName.setText("抢答");
        } else if (questionType == TYPE_QUESTION_SUIJICHOUDA) {
            titleName.setText("抽答");
        } else if (questionType == TYPE_QUESTION_CEPING) {
            titleName.setText("测评");
            ok.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.GONE);
        }
    }


    private void initData() {
        if (questionTime == 0) {
            mHandler.sendEmptyMessageDelayed(0, 1000);
        } else {
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }

        //跨域问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            answerWV.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } else {
            try {
                Class<?> clazz = answerWV.getSettings().getClass();
                Method method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", boolean.class);
                if (method != null) {
                    method.invoke(answerWV.getSettings(), true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        WebSettings settings = answerWV.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        //settings.setDomStorageEnabled(true); 画板不能用这个属性
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        answerWV.setWebViewClient(new MyWebViewClient());
        answerWV.setVerticalScrollBarEnabled(false);
        answerWV.setWebChromeClient(new WebChromeClient());
        answerWV.addJavascriptInterface(new JsAndroidInterface2(this), "wx");
    }


    private void initListener() {
    }


    public void prev(View view) {
        current--;
        refresh();
    }

    public void next(View view) {
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
        } else if (q.type == Question.TYPE_EMPTY) {
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
        //学生提交答案区域
        if (q.type == Question.TYPE_SINGLE || q.type == Question.TYPE_MULTI) {
            answerGV.setVisibility(View.VISIBLE);
            answerET.setVisibility(View.GONE);
            answerWV.setVisibility(View.GONE);
            choices.clear();
            String choose[] = q.options.replace("\"", "").replace("[", "").replace("]", "").split(",");
            for (String temp : choose) {
                choices.add(new Choice(temp, false));
            }
            adapter.notifyDataSetChanged();
        } else if (q.type == Question.TYPE_EMPTY) {
            answerGV.setVisibility(View.GONE);
            answerET.setVisibility(View.VISIBLE);
            answerWV.setVisibility(View.GONE);
        } else if (q.type == Question.TYPE_JUDGE) {
            answerGV.setVisibility(View.VISIBLE);
            answerET.setVisibility(View.GONE);
            answerWV.setVisibility(View.GONE);
            choices.clear();
            choices.add(new Choice("对", false));
            choices.add(new Choice("错", false));
            adapter.notifyDataSetChanged();
        } else if (q.type == Question.TYPE_ESSAY) {
            answerGV.setVisibility(View.GONE);
            answerET.setVisibility(View.GONE);
            answerWV.setVisibility(View.VISIBLE);
        }
    }

    private void load() {
        answerWV.clearCache(true);
        String url = "file:///android_asset/whiteboard/index.html";
        Log.d("test", "url = " + url);
        answerWV.loadUrl(url);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                questionTime++;
            } else if (msg.what == 1) {
                if (questionTime > 0) {
                    questionTime--;
                } else {
                    //时间到
                    submited = true;
                }
            }
            time.setText("时间：" + Utils.secToTime(questionTime));
            mHandler.sendEmptyMessageDelayed(msg.what, 1000);
        }
    };

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
            View roanswerWView = convertView;
            ViewHolder holder;
            if (roanswerWView == null) {
                roanswerWView = inflater.inflate(R.layout.item_answer, null);
                holder = new ViewHolder();

                holder.choose = (TextView) roanswerWView.findViewById(R.id.choose);

                roanswerWView.setTag(holder);
            } else {
                holder = (ViewHolder) roanswerWView.getTag();
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

            return roanswerWView;
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

            answerWV.loadUrl("javascript:selectPhotoCallback('file://" + path + "')");
        }
    }

    public void clickSubmit(View view) {
        if (submited) {
            toast("你已经提交过了，请不要重复提交");
            return;
        }
        //整理答案
        String answer = "answer_xxxx";
        boolean ret = Utils.sendToServer(answer);
        if (ret) {
            submited = true;
            mHandler.removeCallbacksAndMessages(null);
        } else {
            toast("提交失败");
        }
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

    @Override
    public void clickBack(View view) {
        if (!finished) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage("是否退出本次问答/测评？")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    })
                    .setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
            return;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
