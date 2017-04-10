package cn.kwim.mqttcilent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import io.realm.RealmConfiguration;

public class BaseActivity extends cn.kiway.activity.BaseActivity {
    private RealmConfiguration configuration ;

    private final BroadcastReceiver networkConnectionStatusBroadcastReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!(mobNetInfo != null && mobNetInfo.isConnected())
                    && !(wifiNetInfo != null && wifiNetInfo.isConnected()))
            {
                //处理断网重连机制  //TODO 服务端session异常
                //MqttInstance.setInstance();
            }else
            {

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkConnectionStatusBroadcastReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkConnectionStatusBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
