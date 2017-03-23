package cn.kiway;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.apache.http.cookie.Cookie;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.activity.BaseActivity;

import cn.kiway.utils.Logger;
import cn.kiway.utils.SharedPreferencesUtil;

/**
 * 应用程序
 */
public class App extends Application {
    /**

     * 当前网络名字
     */
    public String nowWifi;
    /**
     * 是否初始化
     */
    boolean isInit;
    /**
     * session
     */
    public Cookie cookie;
    /**
     * 上课tcp的ip地址
     */
    public String SessionIp;
    private static App app = null;
    private Display display;
    /**
     * 开启的界面视图集合
     */
    public ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();
//    public WifiAdmin admin;
//    public List<ClassModel> classModels = new ArrayList<ClassModel>();
    public boolean isHotSesson;
    public int classPosition;
//    VideoModel videoModel;//上课model，在盒子是热点的时候用到  TeacherTableAdapter
    public Boolean isChangeWifi;
    public boolean isStartPhotos() {
        return isStartPhotos;
    }

    public Boolean getChangeWifi() {
        return isChangeWifi;
    }

    public void setChangeWifi(Boolean changeWifi) {
        isChangeWifi = changeWifi;
    }

    public void setStartPhotos(boolean startPhotos) {
        isStartPhotos = startPhotos;
    }

    boolean isStartPhotos;//是否进入到选择照片
//    public VideoModel getVideoModel() {return videoModel;}
//    public void setVideoModel(VideoModel videoModel) {
//        this.videoModel = videoModel;
//    }
//    public HandlerClient client;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("APP开始",System.currentTimeMillis()+"");
        Logger.TAG = getPackageName();
        // getNioSocketConnector();
        app = this;
        if (display == null) {
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }

        Log.i("APP结束",System.currentTimeMillis()+"");
    }

    public static App getInstance() {
        return app;
    }

    public String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }

    /**
     * 结束所有界面
     */
    public void finishAllAct() {
        if (activities != null) {
            for (Activity activity : activities) {
                if (!activity.isFinishing())
                    activity.finish();
            }
        }
    }

    public boolean isInit() {
        return isInit;
    }
    public void setInit(boolean isInit) {
        this.isInit = isInit;
    }
    public void setNowWifi(String nowWifi) {
        this.nowWifi = nowWifi;
    }
    public String getNowWifi() {
        return this.nowWifi;
    }
    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
    public Cookie getCookie() {return this.cookie;}
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
    public void setSessionIp(String SessionIp) {
        this.SessionIp = SessionIp;
    }
    public String getSessionIp() {
        return this.SessionIp;
    }

    public void init() {
        // 设置该CrashHandler为程序的默认处理器
        //UnCeHandler catchExcep = new UnCeHandler(this);
        //Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
    public String getUid() {
        return SharedPreferencesUtil.getString(this,IUrContant.LOGIN_URL);
    }
}
