package com.android.kiway.broadcast;

/**
 * Created by Administrator on 2018/10/31.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.android.kiway.KWApp;
import com.android.kiway.activity.MainActivity;
import com.android.kiway.entity.App;
import com.android.kiway.zbus.ZbusHost;

import java.io.IOException;

import static com.android.kiway.KWApp.MSG_INITZHUS;

/**
 * @author :huangxianfeng on 2016/12/6.
 *         监听网络的变化
 */
public class NetworkReceiver extends BroadcastReceiver {
    private final static String TAG = "网络状态";

    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //监听wifi的连接状态即是否连接的一个有效的无线路由
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (parcelableExtra != null) {
                // 获取联网状态的NetWorkInfo对象
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                //获取的State对象则代表着连接成功与否等状态
                NetworkInfo.State state = networkInfo.getState();
                //判断网络是否已经连接
                boolean isConnected = state == NetworkInfo.State.CONNECTED;
                Log.i(TAG, "isConnected:" + isConnected);
                if (isConnected) {
                    if (KWApp.instance != null)
                        KWApp.instance.mHandler.sendEmptyMessage(MSG_INITZHUS);
                }
            }
        }
    }
}

