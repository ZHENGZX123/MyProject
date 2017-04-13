package yjpty.teaching;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kwim.mqttcilent.KwApplication;
import yjpty.teaching.model.ClassModel;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.IConstant;

/**
 * Created by Administrator on 2017/3/17.
 */

public class App extends KwApplication{
    public HandlerClient client;
    public List<ClassModel> classModels = new ArrayList<ClassModel>();
    /**

     * 当前网络名字
     */
    public String nowWifi;
    /**
     * 上课tcp的ip地址
     */
    public String SessionIp;
    VideoModel videoModel;//上课model，在盒子是热点的时候用到  TeacherTableAdapter
    public boolean isHotSesson;
    public int classPosition;

    public HandlerClient getClient() {
        return client;
    }

    public String getNowWifi() {
        return nowWifi;
    }

    public void setNowWifi(String nowWifi) {
        this.nowWifi = nowWifi;
    }

    public String getSessionIp() {
        return SessionIp;
    }

    public void setSessionIp(String sessionIp) {
        SessionIp = sessionIp;
    }

    public VideoModel getVideoModel() {
        return videoModel;
    }

    public void setVideoModel(VideoModel videoModel) {
        this.videoModel = videoModel;
    }

    public boolean isHotSesson() {
        return isHotSesson;
    }

    public void setHotSesson(boolean hotSesson) {
        isHotSesson = hotSesson;
    }

    public int getClassPosition() {
        return classPosition;
    }

    public void setClassPosition(int classPosition) {
        this.classPosition = classPosition;
    }

    public static App getApp() {
        return app;
    }

    public static void setApp(App app) {
        App.app = app;
    }

    public Boolean getChangeWifi() {
        return isChangeWifi;
    }

    public void setChangeWifi(Boolean changeWifi) {
        isChangeWifi = changeWifi;
    }

    public void setClient(HandlerClient client) {
        this.client = client;
    }

    private static App app = null;
    public Boolean isChangeWifi;
    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).setMaxCacheSize(2 * 1024 * 2014)
                .setBaseDirectoryName(IConstant.DOWNLOAD_PHOTO_FLODER)
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return getCacheDir();
                    }
                }).build();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
        app = this;
    }
}
