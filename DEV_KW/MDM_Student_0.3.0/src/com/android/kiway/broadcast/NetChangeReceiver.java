package com.android.kiway.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.android.kiway.activity.MainActivity;
import com.android.kiway.utils.NetworkUtil;


public class NetChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "NetChangeBroadcast onReceive");
        checkNetWork(context);
    }

    public void checkNetWork(Context context) {
        //获取手机的连接服务管理器，这里是连接管理器类
        try {
            boolean available = NetworkUtil.isNetworkAvailable(context);
            Log.d("test", "NetChangeBroadcast available = " + available);

            Message msg = new Message();
            msg.what = MainActivity.MSG_NETWORK;
            if (available) {
                msg.arg1 = 1;
            } else {
                msg.arg1 = 0;
            }
            MainActivity.instance.mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}