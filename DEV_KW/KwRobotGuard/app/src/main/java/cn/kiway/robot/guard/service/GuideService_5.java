package cn.kiway.robot.guard.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.kiway.robot.guard.KWApplication;
import cn.kiway.robot.guard.util.FileUtils;
import cn.kiway.robot.guard.util.Utils;

import static com.jaredrummler.android.processes.ProcessManager.getRunningAppProcesses;

/**
 * Created by Administrator on 2018/4/2.
 */

public class GuideService_5 extends Service {

    private boolean stop = false;
    private int repeat = 0;
    List<AndroidAppProcess> processes = new ArrayList<>();

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
                        sleep(10 * 1000);

//                        boolean isRun1 = isRun(GuideService_5.this, "cn.kiway.robot");
//                        Log.d("test", "isRun1 = " + isRun1);
//                        boolean isRun2 = isRun(GuideService_5.this, "com.tencent.mm");
//                        Log.d("test", "isRun2 = " + isRun2);
//
//                        boolean isRun3 = isApplicationShowing(GuideService_5.this, "cn.kiway.robot");
//                        Log.d("test", "isRun3 = " + isRun3);
//                        boolean isRun4 = isApplicationShowing(GuideService_5.this, "com.tencent.mm");
//                        Log.d("test", "isRun4 = " + isRun4);

//                        //1.检查机器人是否启动
                        boolean isRun1 = isRun(GuideService_5.this, "cn.kiway.robot");
                        Log.d("test", "isRun1 = " + isRun1);
                        if (!isRun1) {
                            Log.d("test", "启动机器人");
                            Intent intent = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot");
                            startActivity(intent);
                        }
                        sleep(5000);
                        //2.检查微信是否启动
                        boolean isRun2 = isRun(GuideService_5.this, "com.tencent.mm");
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

                        //3. 检查微信是否一直在前台
                        boolean isWxInfront = isApplicationShowing(GuideService_5.this, "com.tencent.mm");
                        boolean robotActioning = getRobotActioningFlag();
                        Log.d("test", "isWxInfront = " + isWxInfront + " , robotActioning = " + robotActioning);
                        if (isWxInfront && !robotActioning) {
                            repeat++;
                            Log.d("test", "repeat = " + repeat);
                            if (repeat % 5 == 0) {
                                Intent intent2 = getPackageManager().getLaunchIntentForPackage("cn.kiway.robot.guard");
                                startActivity(intent2);
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
        String actioningFlag = FileUtils.readSDCardFile(KWApplication.ROOT_ROBOT + "actioningFlag.txt",
                GuideService_5.this.getApplicationContext());
        Log.d("test", "actioningFlag = " + actioningFlag);
        if (TextUtils.isEmpty(actioningFlag)) {
            return false;
        }
        return Boolean.parseBoolean(actioningFlag);
    }

    private boolean isRun(Context context, String pkgName) {
        boolean isRun = false;
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
//        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
//            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
//                    || processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE
//                    || processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND
//                    ) {
//                for (String activeProcess : processInfo.pkgList) {
//                    if (activeProcess.equals(pkgName)) {
//                        isRun = true;
//                    }
//                }
//            }
//        }
        processes = ProcessManager.getRunningAppProcesses();
        for (AndroidAppProcess process : processes) {
            if (pkgName.equals(process.getPackageName())) {
                isRun = true;
                break;
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            isRun = Utils.checkProcess(pkgName);
        }
        return isRun;
    }


    public boolean isUIProcess(String mainProcessName) {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    private boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isApplicationShowing(Context context, String packageName) {
//        boolean isShow = false;
//        List<AndroidAppProcess> processes = AndroidProcesses.getRunningAppProcesses();
//        for (AndroidAppProcess process : processes) {
//            if (packageName.equals(process.getPackageName())) {
//                isShow = true;
//                break;
//            }
//        }
        class RecentUseComparator implements Comparator<UsageStats> {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public int compare(UsageStats lhs, UsageStats rhs) {
                return (lhs.getLastTimeUsed() > rhs.getLastTimeUsed()) ? -1 : (lhs.getLastTimeUsed() == rhs
                        .getLastTimeUsed()) ? 0 : 1;
            }
        }
        RecentUseComparator mRecentComp = new RecentUseComparator();
        long ts = System.currentTimeMillis();
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> usageStats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - 1000 *
                10, ts);
        if (usageStats == null || usageStats.size() == 0) {
            return false;
        }
        Collections.sort(usageStats, mRecentComp);
        String currentTopPackage = usageStats.get(0).getPackageName();
        if (currentTopPackage.equals(packageName)) {
            return true;
        } else {
            return false;
        }
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
