package cn.kiway.brower.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.kiway.entity.Network;

import java.util.ArrayList;

import cn.kiway.brower.Activity.BrowserActivity;

import static cn.kiway.brower.Activity.BrowserActivity.all_network;

/**
 * Created by Administrator on 2018/4/16.
 */

public class MyBroadcastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //处理收到广播的逻辑：
        BrowserActivity.enable_type = intent.getIntExtra("enable_type", -1);
        all_network = (ArrayList<Network>) intent.getSerializableExtra("all_network");
        //Toast.makeText(context, all_network + "", Toast.LENGTH_SHORT).show();
    }

}
