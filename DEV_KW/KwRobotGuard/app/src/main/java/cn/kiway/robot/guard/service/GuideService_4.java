package cn.kiway.robot.guard.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import cn.kiway.robot.guard.KWApplication;
import cn.kiway.robot.guard.util.FileUtils;

/**
 * Created by Administrator on 2018/4/2.
 */

public class GuideService_4 extends Service {

    private boolean stop = false;
    private int repeat = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "GuideService onCreate");
        stop = false;
        repeat = 0;
        new Thread() {
            @Override
            public void run() {
                while (!stop) {
                    try {
                        Log.d("test", "guard is running ...");
                        sleep(60 * 1000);

//                        boolean isRun1 = isRun(GuideService_4.this, "cn.kiway.robot");
//                        Log.d("test", "isRun1 = " + isRun1);
//                        boolean isRun2 = isRun(GuideService_4.this, "com.tencent.mm");
//                        Log.d("test", "isRun2 = " + isRun2);
//
//                        boolean isRun3 = !isAppIsInBackground(GuideService_4.this, "cn.kiway.robot");
//                        Log.d("test", "isRun3 = " + isRun3);
//                        boolean isRun4 = !isAppIsInBackground(GuideService_4.this, "com.tencent.mm");
//                        Log.d("test", "isRun4 = " + isRun4);

                        //1.检查机器人是否启动
                        boolean isRun1 = isRun(GuideService_4.this, "cn.kiway.robot");
                        Log.d("test", "isRun1 = " + isRun1);
                        if (!isRun1) {
                            Log.d("test", "启动机器人");
                            Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
                            //startActivity(intent);
                        }
                        sleep(5000);
                        //2.检查微信是否启动
                        boolean isRun2 = isRun(GuideService_4.this, "com.tencent.mm");
                        Log.d("test", "isRun2 = " + isRun2);
                        if (!isRun2) {
                            Log.d("test", "启动微信");
                            Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                           // startActivity(intent);
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent2 = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot.guard");
                         //startActivity(intent2);
                        }

                        //3. 检查微信是否一直在前台
                        boolean isWxInfront = isRunInFront(GuideService_4.this, "com.tencent.mm");
                        boolean robotActioning = getRobotActioningFlag();
                        Log.d("test", "isWxInfront = " + isWxInfront + " , robotActioning = " + robotActioning);
                        if (isWxInfront && !robotActioning) {
                            repeat++;
                            Log.d("test", "repeat = " + repeat);
                            if (repeat % 5 == 0) {
                                Intent intent2 = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot.guard");
                                //startActivity(intent2);
                                repeat = 0;
                            }
                        } else {
                            repeat = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            sleep(5000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        continue;
                    }
                }
            }
        }.start();
    }

    private boolean getRobotActioningFlag() {
        String actioningFlag = FileUtils.readSDCardFile(KWApplication.ROOT_ROBOT + "actioningFlag.txt", GuideService_4.this.getApplicationContext());
        Log.d("test", "actioningFlag = " + actioningFlag);
        if (TextUtils.isEmpty(actioningFlag)) {
            return false;
        }
        return Boolean.parseBoolean(actioningFlag);
    }

    //前后台
    public boolean isRun(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    //
    public boolean isRunInFront(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String pkg = cn.getPackageName();
        if (TextUtils.isEmpty(pkg)) {
            return false;
        }
        return pkg.equals(packageName);
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
