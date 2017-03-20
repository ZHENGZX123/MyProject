package yjpty.teaching;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import yjpty.teaching.model.ClassModel;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.tcpudp.HandlerClient;

/**
 * Created by Administrator on 2017/3/17.
 */

public class App extends Application{
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
        app = this;
    }
}
