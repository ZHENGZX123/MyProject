package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.App;
import cn.kiway.mdm.db.MyDBHelper;
import cn.kiway.mdm.model.FileModel;
import cn.kiway.mdm.model.StudentQuestion;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.NetworkUtil;
import cn.kiway.mdm.utils.Utils;
import io.agora.openlive.model.ConstantApp;
import io.agora.openlive.ui.LiveRoomActivity;
import io.agora.rtc.Constants;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.IContants.CHECK_VERSION_URL;
import static cn.kiway.mdm.utils.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {

    public static MainActivity instantce;
    private Dialog dialog_download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantce = this;
        App.instance.connectService(App.instance.mClientCallback);
        setContentView(R.layout.activity_main);
        initView();
        getAppData();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("开维互动学生端");
        backRL.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        App.instance.connectService(App.instance.mClientCallback);
    }


    //个人信息
    public void onInfo(View view) {
        startActivity(new Intent(this, UserInfoActivity.class));
        //startPlayer();
        //startActivity(new Intent(this, AnswerQuestionsAcitivity.class));
    }

    public void onCourse(View view) {
        startActivity(new Intent(this, Course2Activity.class));
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void logout(View view) {

    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void onDis(View view) {
        findViewById(R.id.connect).setVisibility(View.GONE);
    }

    public void onUdp(View view) {
    }

    public void fenxi(View view) {
        startActivity(new Intent(this, FenxiActivity.class));
    }

    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            String shangke = intent.getStringExtra("shangke");
            Logger.log("shangke::::::" + shangke);
            if (shangke != null && !shangke.equals("")) {

            } else {
                checkNewVersion();
            }
        } else {
            checkNewVersion();
        }
    }


    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpGet httpRequest = new HttpGet(CHECK_VERSION_URL);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");
                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
                        downloadSilently(apkUrl, apkVersion);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_zhkt_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(getString(R.string.new_version)).setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog_download.dismiss();
                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = savedFilePath;
                        mHandler.sendMessage(msg);
                    }
                }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog_download.show();
    }

    ////////////////以上是版本更新
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (app.isAttenClass) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //--------------------------------------------------2.0新增--------------------------

    public void startPlayer() {
        //1.开始播放教师推屏
        Intent i = new Intent(MainActivity.this, LiveRoomActivity.class);
        i.putExtra(ConstantApp.ACTION_KEY_CROLE, Constants.CLIENT_ROLE_AUDIENCE);
        i.putExtra(ConstantApp.ACTION_KEY_ROOM_NAME, "kiway");
        startActivity(i);
    }

    public void clickSnapshot(View view) {
        //保存截图到本地
        getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bitmap = getWindow().getDecorView().getDrawingCache();
        String time = System.currentTimeMillis() + "";
        String fileName = time + ".png";
        Utils.saveBitmap(bitmap, fileName, App.PATH);
        toast("请在我的文件里查看");
        new MyDBHelper(this).addFile(new FileModel(fileName, App.PATH + fileName, time, "snapshot"));
    }

    private ArrayList<StudentQuestion> studentQuestions = new ArrayList<>();
    private StudentQuestionAdapter questionAdapter;

    public void clickAsk(View view) {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_ask);
        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        Button close = (Button) dialog.findViewById(R.id.close);
        ImageButton mic = (ImageButton) dialog.findViewById(R.id.mic);
        ListView lv = (ListView) dialog.findViewById(R.id.lv);
        final EditText content = (EditText) dialog.findViewById(R.id.content);
        Button send = (Button) dialog.findViewById(R.id.send);

        questionAdapter = new StudentQuestionAdapter();
        lv.setAdapter(questionAdapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //录制声音
                record();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentStr = content.getText().toString();
                if (TextUtils.isEmpty(contentStr)) {
                    toast("内容不能为空");
                    return;
                }
                StudentQuestion q = new StudentQuestion();
                q.type = 1;
                q.content = contentStr;
                studentQuestions.add(q);
                questionAdapter.notifyDataSetChanged();
                content.setText("");
            }
        });
    }

    public void record() {

        startRecord();
        final Dialog dialog = new Dialog(MainActivity.this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_voice);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final Button voice = (Button) dialog.findViewById(R.id.voice);
        final TextView time = (TextView) dialog.findViewById(R.id.time);
        voice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                stopRecord();
                dialog.dismiss();
            }
        });
        new Thread() {
            @Override
            public void run() {
                int duration = 0;
                while (dialog.isShowing()) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    duration++;
                    final int finalDuration = duration;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            time.setText(Utils.getTimeFormLong(finalDuration * 1000));
                        }
                    });
                }
            }
        }.start();
    }

    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;

    //录音
    private void startRecord() {
        try {
            // 判断，若当前文件已存在，则删除
            String path = App.PATH + "voice/";
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            recordFile = new File(path + System.currentTimeMillis() + ".mp3");
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setAudioChannels(2);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);//输出格式
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);//编码格式
            mediaRecorder.setAudioEncodingBitRate(16);
            mediaRecorder.setOutputFile(recordFile.getAbsolutePath());

            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            toast("录制声音失败，请检查SDcard");
        }
        start = System.currentTimeMillis();
    }

    private void stopRecord() {
        if (recordFile == null) {
            return;
        }
        if (mediaRecorder == null) {
            return;
        }
        mediaRecorder.stop();

        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("录制时间太短");
            return;
        }
        //1.上传录制文件

        //2.刷新界面
        StudentQuestion q = new StudentQuestion();
        q.type = 2;
        q.duration = duration;
        q.filepath = recordFile.getAbsolutePath();
        studentQuestions.add(q);
        questionAdapter.notifyDataSetChanged();
    }


    private class StudentQuestionAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public StudentQuestionAdapter() {
            inflater = LayoutInflater.from(MainActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student_question, null);
                holder = new ViewHolder();

                holder.rl_voice = (RelativeLayout) rowView.findViewById(R.id.rl_voice);
                holder.rl_txt = (RelativeLayout) rowView.findViewById(R.id.rl_txt);
                holder.content = (TextView) rowView.findViewById(R.id.content);
                holder.duration = (TextView) rowView.findViewById(R.id.duration);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final StudentQuestion q = studentQuestions.get(position);
            if (q.type == 1) {
                holder.rl_txt.setVisibility(View.VISIBLE);
                holder.rl_voice.setVisibility(View.GONE);
                holder.content.setText(q.content);
            } else if (q.type == 2) {
                holder.rl_txt.setVisibility(View.GONE);
                holder.rl_voice.setVisibility(View.VISIBLE);
                holder.duration.setText(q.duration + "秒");
                holder.rl_voice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filepath = q.filepath;
                        Uri uri = Uri.fromFile(new File(filepath));
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, uri);
                        mediaPlayer.start();
                    }
                });
            }

            return rowView;
        }

        public class ViewHolder {
            public RelativeLayout rl_voice;
            public RelativeLayout rl_txt;
            public TextView content;
            public TextView duration;
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
