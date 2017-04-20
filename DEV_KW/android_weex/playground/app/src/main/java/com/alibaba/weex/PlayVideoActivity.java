package com.alibaba.weex;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.wjc.R;

public class PlayVideoActivity extends Activity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private VideoView mvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvv = (VideoView) findViewById(R.id.mvv);
        mvv.setOnPreparedListener(this);
        mvv.setOnErrorListener(this);
        mvv.setOnCompletionListener(this);
        mvv.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("test", "completion");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d("test", "error");
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("test", "prepare");
        mvv.start();
        mvv.seekTo(getIntent().getIntExtra("position", 0));
    }

    @Override
    public void onBackPressed() {
        setResult(888, new Intent().putExtra("position", mvv.getCurrentPosition()));
        super.onBackPressed();
    }
}
