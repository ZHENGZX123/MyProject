package cn.kiway.robot.guard.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class GuideService extends Service {

    private boolean stop = false;

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
                    boolean isRun = isRun(GuideService.this.getApplicationContext());
                    Log.d("test", "isRun = " + isRun);
                    if (!isRun) {
                        Log.d("test", "启动机器人");
                        Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
                        startActivity(intent);
                    }
                }
            }
        }.start();
    }

    public boolean isRun(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = "cn.kiway.robot";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                Log.i("ActivityService isRun()", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }

        Log.i("ActivityService isRun()", "com.ad 程序   ...isAppRunning......" + isAppRunning);
        return isAppRunning;
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
