package cn.kiway.record;

import android.app.Activity;
import android.media.MediaRecorder;
import android.widget.Toast;

import java.io.File;

import cn.kiway.log.MLog;

/**
 * Created by Administrator on 2018/3/6.
 */

public class RecordAudioUtil {

    private MediaRecorder mediaRecorder;
    private File recordFile;
    private long start;
    private Activity act;

    public void init(Activity act) {
        this.act = act;
    }

    //录音
    private void startRecord() {
        try {
            // 判断，若当前文件已存在，则删除
            String path = "/mnt/sdcard/voice/";
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

    public String stopRecord() {
        if (recordFile == null) {
            return null;
        }
        if (mediaRecorder == null) {
            return null;
        }
        mediaRecorder.stop();
        final int duration = (int) (System.currentTimeMillis() - start) / 1000;
        if (duration < 1) {
            toast("太短了");
            return null;
        }
        return recordFile.getAbsolutePath();
    }


    public void toast(final String txt) {
        MLog.d("test", "toast is called");
        this.act.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(act, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
