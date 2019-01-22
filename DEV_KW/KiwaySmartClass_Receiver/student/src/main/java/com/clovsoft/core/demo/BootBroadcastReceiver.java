package com.clovsoft.core.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.clovsoft.core.CDevice;

public class BootBroadcastReceiver extends BroadcastReceiver {
		
	@Override
	public void onReceive(Context context, Intent intent) {
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Intent i = new Intent(context, MainActivity.class);
			i.putExtra(MainActivity.EXTRA_ONLY_INIT_SDK, true);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}
}
