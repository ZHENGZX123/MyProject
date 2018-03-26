package cn.kiway.mdm.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.App;
import cn.kiway.mdm.model.StudentQuestion;
import cn.kiway.mdm.utils.Constant;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.UploadUtil;
import cn.kiway.mdm.utils.Utils;
import io.agora.openlive.ui.LiveRoomActivity;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.HttpUtil.uploadFile;

/**
 * Created by Administrator on 2018/3/7.
 */

public class KwLivingRoomActivity extends LiveRoomActivity {

    public static KwLivingRoomActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_room);
        instance = this;

    }


    //---------------------------2.0新增--------------------------
    String fileName;

    public void clickSnapshot(View view) {
        //保存截图到本地
        //FIXME 换一种写法。
        getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bitmap = getWindow().getDecorView().getDrawingCache();
        String time = System.currentTimeMillis() + "";
        fileName = time + ".png";
        Utils.saveBitmap(bitmap, fileName, App.ROOT);
        toast("请在我的文件里查看");
        new Thread() {
            @Override
            public void run() {
                final String ret = UploadUtil.uploadFile(App.ROOT + fileName, uploadFile, fileName);
                if (TextUtils.isEmpty(ret)) {
                    toast("上传失败");
                    return;
                }
                try {
                    JSONObject obj = new JSONObject(ret);
                    if (obj.optInt("statusCode") != 200) {
                        toast("上传失败");
                        return;
                    }
                    Logger.log("::::::::" + obj);
                    String url = obj.optJSONObject("data").optString("url");

                    Message message = new Message();
                    message.what = 1;
                    message.obj = url;
                    MainActivity.instantce.mHandler.sendMessage(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private ArrayList<StudentQuestion> studentQuestions = new ArrayList<>();
    private StudentQuestionAdapter questionAdapter;

    public void clickAsk(View view) {
        if (!App.instance.isAttenClass) {
            toast("只有上课期间才可以提问");
            return;
        }

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
                try {
                    String contentStr = content.getText().toString();
                    if (TextUtils.isEmpty(contentStr)) {
                        toast("内容不能为空");
                        return;
                    }
                    if (contentStr.length() > 30) {
                        toast("不能超过30个字符");
                        return;
                    }
                    //1.使用zbus发送
                    JSONObject o = new JSONObject();
                    o.put("type", 1);
                    o.put("content", contentStr);
                    o.put("time", System.currentTimeMillis());
                    o.put("name", getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
                    o.put("avatar", getSharedPreferences("kiway", 0).getString("userUrl", ""));
                    boolean ret = Utils.sendToServer(getApplicationContext(), "question_" + o.toString());
                    if (!ret) {
                        toast("提问失败");
                        return;
                    }
                    //2.刷新界面
                    StudentQuestion q = new StudentQuestion();
                    q.type = 1;
                    q.content = contentStr;
                    studentQuestions.add(q);
                    questionAdapter.notifyDataSetChanged();
                    content.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void record() {

        startRecord();
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
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
            String path = App.ROOT + "voice/";
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

        new Thread() {
            @Override
            public void run() {
                //1.上传录制文件
                String token = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                String result = UploadUtil.uploadFile(recordFile.getAbsolutePath(), Constant.clientUrl +
                        "common/file?x-auth-token=" + token, recordFile.getName());
                try {
                    String url = new JSONObject(result).getJSONObject("data").getString("url");
                    JSONObject o = new JSONObject();
                    o.put("type", 2);
                    o.put("content", url);
                    o.put("time", System.currentTimeMillis());
                    o.put("name", getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
                    o.put("avatar", getSharedPreferences("kiway", 0).getString("userUrl", ""));
                    o.put("duration", duration);
                    boolean ret = Utils.sendToServer(getApplicationContext(), "question_" + o.toString());
                    if (!ret) {
                        toast("提问失败");
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //2.刷新界面
                            StudentQuestion q = new StudentQuestion();
                            q.type = 2;
                            q.duration = duration;
                            q.filepath = recordFile.getAbsolutePath();
                            studentQuestions.add(q);
                            questionAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast("发送录音失败");
                    return;
                }
            }
        }.start();
    }


    private class StudentQuestionAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public StudentQuestionAdapter() {
            inflater = LayoutInflater.from(KwLivingRoomActivity.this);
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
                        MediaPlayer mediaPlayer = MediaPlayer.create(KwLivingRoomActivity.this, uri);
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

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(KwLivingRoomActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
