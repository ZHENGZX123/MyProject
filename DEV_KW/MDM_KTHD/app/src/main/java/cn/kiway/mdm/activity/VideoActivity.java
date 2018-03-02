package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.util.Log;

import com.superplayer.library.SuperPlayer;

import java.util.ArrayList;

import cn.kiway.mdm.model.Video;
import studentsession.kiway.cn.mdm_studentsession.R;


public class VideoActivity extends BaseActivity implements SuperPlayer.OnNetChangeListener {

    private SuperPlayer player;
    private ArrayList<Video> videos;
    private String title;
    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        player = (SuperPlayer) findViewById(R.id.view_super_player);

        videos = (ArrayList<Video>) getIntent().getSerializableExtra("videos");
        title = getIntent().getStringExtra("title");

        startPlay();
    }

    private void startPlay() {
        Video v = videos.get(current);
        Log.d("test", "url = " + v.url);
        player.setLive(false);//true：表示直播地址；false表示点播地址
        player.setScaleType(SuperPlayer.SCALETYPE_FILLPARENT);
        player.setTitle(title).play(v.url);//设置视频的titleName
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        toast(R.string.network_wifi);
    }

    @Override
    public void onMobile() {
        toast(R.string.network_mobile);
    }

    @Override
    public void onDisConnect() {
        toast(R.string.network_dis);
    }

    @Override
    public void onNoAvailable() {
        toast(R.string.network_error);
    }
}
