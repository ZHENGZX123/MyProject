package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import cn.kiway.mdm.teacher.R;


public class VideoActivity extends BaseActivity {

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//        url = getIntent().getStringExtra("url");
        url = "/mnt/sdcard/test.mp4";

        VideoView vv = (VideoView) findViewById(R.id.vv);
        MediaController mediaController = new MediaController(this);
        vv.setMediaController(mediaController);
        vv.setVideoPath(url);
        vv.start();
    }
}
