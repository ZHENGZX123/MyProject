package cn.kiway.launcher.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.kiway.launcher.activity.MainActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private final String action_boot = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(final Context context, Intent intent) {

        if (intent.getAction().equals(action_boot)) {
            Log.d("test", "开机启动啦");
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }


}