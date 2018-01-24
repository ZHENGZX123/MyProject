package com.android.kiway.windows;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.android.launcher3.R;


/**
 * Project Name:GetTopActivity
 * Class description:
 * Create User:wxb
 * Create Time:2016/4/23 20:47
 */
public class LockSreenService extends Service {
    public static final String TAG = "kiway";
    public long last;

    /**
     * 窗口管理器
     */
    WindowManager wm;
    /**
     * 布局属性
     */
    WindowManager.LayoutParams params;
    /**
     * 窗口尺寸属性
     */
    static DisplayMetrics displayMetrics;
    /**
     * 弹窗呈现的视图
     */
    View view;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service is create.");
        displayMetrics = getResources().getDisplayMetrics();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.height = displayMetrics.heightPixels;
        params.width = displayMetrics.widthPixels;
        params.gravity = Gravity.CENTER;
        view = LayoutInflater.from(this).inflate(R.layout.activity_screen, null);
        //测试用，打包需屏蔽
//        view.findViewById(R.id.dd).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KWApp.instance.mHandler.sendEmptyMessage(MSG_UNLOCK);
//            }
//        });
        wm.addView(view, params);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Service is startcommand.");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (view.getParent() != null)
            wm.removeView(view);//移除窗口
        super.onDestroy();
        Log.i(TAG, "Service is stop.");
    }
}
