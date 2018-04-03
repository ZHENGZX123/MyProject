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
                        sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean isRun1 = isRun(GuideService.this, "cn.kiway.robot");
                    Log.d("test", "isRun1 = " + isRun1);
                    if (!isRun1) {
                        Log.d("test", "启动机器人");
                        //上报给易敏
                    }

                    boolean isRun2 = isRun(GuideService.this, "com.tencent.mm");
                    Log.d("test", "isRun2 = " + isRun2);
                    if (!isRun2) {
                        Log.d("test", "启动微信");
                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                        startActivity(intent);
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent2 = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot.guard");
                        startActivity(intent2);
                    }


                }
            }
        }.start();
    }

    public boolean isRun(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) || info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                break;
            }
        }
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
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop = true;
        Log.d("test", "GuideService onDestroy");
    }
}
