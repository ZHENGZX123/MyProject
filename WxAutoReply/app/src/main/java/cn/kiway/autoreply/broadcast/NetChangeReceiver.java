package cn.kiway.autoreply.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import cn.kiway.autoreply.activity.MainActivity;
import cn.kiway.autoreply.util.NetworkUtil;


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
            if (available) {
                msg.what = MainActivity.MSG_NETWORK_OK;
                MainActivity.instance.mHandler.removeMessages(MainActivity.MSG_NETWORK_OK);
                MainActivity.instance.mHandler.sendMessageDelayed(msg, 3000);
            } else {
                msg.what = MainActivity.MSG_NETWORK_ERR;
                MainActivity.instance.mHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}