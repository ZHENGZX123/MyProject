package com.alibaba.weex.extend.module;

import android.media.MediaRecorder;
import android.util.Log;

import com.alibaba.weex.extend.view.RecordPlayer;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import java.io.File;
import java.io.IOException;


public class MyAudioRecorderPlayer extends WXModule {

    private File recordFile;
    private MediaRecorder mediaRecorder;
    private RecordPlayer player;

    public MyAudioRecorderPlayer() {
        recordFile = new File("/mnt/sdcard", "kk.amr");
    }

    @JSMethod(uiThread = true)
    public void startRecord() {
        Log.d("test", "startRecord");

        mediaRecorder = new MediaRecorder();
        // 判断，若当前文件已存在，则删除
        if (recordFile.exists()) {
            recordFile.delete();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile(recordFile.getAbsolutePath());
        try {
            // 准备好开始录音
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JSMethod(uiThread = true)
    public void stopRecord() {
        Log.d("test", "stopRecord");
        if (recordFile != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
        }
    }


    @JSMethod(uiThread = true)
    public void startPlay() {
        Log.d("test", "startPlay");
        player = new RecordPlayer(mWXSDKInstance.getContext());
        player.playRecordFile(recordFile);
    }

    @JSMethod(uiThread = true)
    public void stopPlay() {
        Log.d("test", "stopPlay");
        player = new RecordPlayer(mWXSDKInstance.getContext());
        player.stopPalyer();
    }

}
