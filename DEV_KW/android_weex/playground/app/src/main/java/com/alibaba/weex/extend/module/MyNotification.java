package com.alibaba.weex.extend.module;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.wjc.R;
import com.alibaba.weex.SplashActivity;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

public class MyNotification extends WXModule {

    @JSMethod(uiThread = true)
    public void showNotification(String title, String content) {
        // 创建一个NotificationManager的引用
        NotificationManager notificationManager = (NotificationManager)
                mWXSDKInstance.getContext().getSystemService(android.content.Context.NOTIFICATION_SERVICE);

        // 设置通知的事件消息
        Intent notificationIntent = new Intent(mWXSDKInstance.getContext(), SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mWXSDKInstance.getContext(), 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(mWXSDKInstance.getContext())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setOngoing(true);
        Notification notification = builder.getNotification();
        // 把Notification传递给NotificationManager
        notificationManager.notify(0, notification);
    }
}
