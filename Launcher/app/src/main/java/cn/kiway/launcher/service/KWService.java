package cn.kiway.launcher.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import cn.kiway.launcher.activity.MainActivity;
import cn.kiway.launcher.utils.Utils;

/**
 * Project Name:GetTopActivity
 * Class description:
 * Create User:wxb
 * Create Time:2016/4/23 20:47
 */
public class KWService extends Service {
    public static final String TAG = "test";
    private MyThread myThread = null;

    private class MyThread extends Thread {

        private Context context;

        private MyThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            boolean locked = context.getSharedPreferences("kiway", 0).getBoolean("locked", false);
            while (locked) {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //1.收回状态栏
                Utils.collapse(context);
                //2.判断当前运行的app
                boolean ret = Utils.checkCurrentApp(context);
                if (!ret) {
                    context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    //mHandler.sendEmptyMessage(0);体验不太好
                }
                locked = context.getSharedPreferences("kiway", 0).getBoolean("locked", false);
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "锁定期间不能执行该操作", Toast.LENGTH_SHORT).show();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is create.");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "Service is start.");
        myThread = new MyThread(this);
        myThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service is startcommand.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service is stop.");
    }
}
