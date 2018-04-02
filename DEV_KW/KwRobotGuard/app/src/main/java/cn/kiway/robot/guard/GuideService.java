package cn.kiway.robot.guard;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2018/4/2.
 */

public class GuideService extends Service {

    private boolean stop = false;
    private boolean first = true;
    private int lastCrashFileCount = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "GuideService onCreate");
        stop = false;
        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    Log.d("test", "guard is running ...");
                    try {
                        sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String path = "/mnt/sdcard/crash/kiway_robot/";
                    String[] files = new File(path).list();
                    if (files == null) {
                        continue;
                    }
                    if (first) {
                        lastCrashFileCount = files.length;
                        first = false;
                    }
                    int temp = files.length;
                    if (temp > lastCrashFileCount) {
                        Log.d("test", "crash文件新增");
                    } else if (temp == lastCrashFileCount) {
                        Log.d("test", "crash文件无变化");
                    } else {
                        Log.d("test", "crash减少");
                    }
                }
            }
        }.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("test", "GuideService onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test", "GuideService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.d("test", "GuideService onDestroy");
    }
}
