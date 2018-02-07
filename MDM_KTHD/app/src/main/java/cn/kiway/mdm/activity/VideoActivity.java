package cn.kiway.mdm.activity;

import android.os.Bundle;

import com.superplayer.library.SuperPlayer;

import cn.kiway.mdm.utils.Logger;
import studentsession.kiway.cn.mdm_studentsession.R;


public class VideoActivity extends BaseActivity implements SuperPlayer.OnNetChangeListener{

    private String url,name;
    private SuperPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        url = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");
        Logger.log(url);
        player = (SuperPlayer) findViewById(R.id.view_super_player);
        player.setLive(false);//true：表示直播地址；false表示点播地址
        player.setScaleType(SuperPlayer.SCALETYPE_FILLPARENT);
        //player.setTitle(name).play(Environment.getExternalStorageDirectory()+"/test.mp4");//设置视频的titleName
        player.setTitle(name).play(url);//设置视频的titleName
    }
    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        toast( R.string.network_wifi);
    }

    @Override
    public void onMobile() {
        toast( R.string.network_mobile);
    }

    @Override
    public void onDisConnect() {
        toast(  R.string.network_dis);
    }

    @Override
    public void onNoAvailable() {
        toast(  R.string.network_error);
    }
}
