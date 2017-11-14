package cn.kiway.mdm.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/10.
 */

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "SmsReceiver onReceive");
        Toast.makeText(context, "有新的短信", Toast.LENGTH_SHORT).show();
    }
}
