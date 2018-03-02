package cn.kiway.mdm.activity;

import android.os.Bundle;

import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.PlayListener;
import org.song.videoplayer.media.IjkMedia;
import org.song.videoplayer.rederview.IRenderView;

import java.util.ArrayList;

import cn.kiway.mdm.entity.Video;
import cn.kiway.mdm.teacher.R;


public class VideoActivity extends BaseActivity {
    DemoQSVideoView qsVideoView;
    private String name;
    private ArrayList<Video> videos = new ArrayList<>();
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videos = (ArrayList<Video>) getIntent().getSerializableExtra("videos");
        name = getIntent().getStringExtra("name");
        qsVideoView = (DemoQSVideoView) findViewById(R.id.qsVideoView);
        qsVideoView.setDecodeMedia(IjkMedia.class);//解码
        qsVideoView.setAspectRatio(IRenderView.AR_ASPECT_FILL_PARENT);//视频填充
        qsVideoView.getCoverImageView().setImageResource(R.mipmap.ic_launcher);//封面
        //设置监听
        qsVideoView.setPlayListener(new PlayListener() {
            @Override
            public void onStatus(int status) {//播放器的ui状态
                if (status == IVideoPlayer.STATE_AUTO_COMPLETE) {
                    if (position + 1 < videos.size()) {
                        position++;
                        playVideo(name, videos.get(position).url);
                    }else {
                        finish();
                    }
                }
            }

            @Override//全屏/普通...
            public void onMode(int mode) {

            }

            @Override//播放事件 初始化完成/缓冲/出错/...
            public void onEvent(int what, Integer... extra) {

            }

        });
        //进入全屏的模式 0横屏 1竖屏 2传感器自动横竖屏 3根据视频比例自动确定横竖屏      -1什么都不做
        qsVideoView.enterFullMode = 3;
        playVideo(name, videos.get(position).url);
    }

    protected void playVideo(String name, String videoUrl) {
        qsVideoView.release();
        qsVideoView.setUp(videoUrl, name);
        qsVideoView.play();
    }

    @Override
    public void onBackPressed() {
        if (qsVideoView.onBackPressed())
            return;
        super.onBackPressed();
    }
}
