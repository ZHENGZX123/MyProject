package com.android.kiway.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.android.kiway.KWApp;
import com.android.kiway.entity.SMS;
import com.android.kiway.utils.MyDBHelper;
import com.android.kiway.utils.Utils;

import static com.android.kiway.KWApp.MSG_SMS;

/**
 * Created by Administrator on 2017/11/10.
 */

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("test", "接收");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            if (messages.length > 0) {
                String msgBody = messages[0].getMessageBody();
                String msgAddress = messages[0].getOriginatingAddress();
                long msgDate = messages[0].getTimestampMillis();
                Log.e("test", "message from: " + msgAddress + ", message body: " + msgBody
                        + ", message date: " + msgDate);

                //使用来电白名单、来电黑名单
                boolean enable = Utils.checkCallEnable(context, msgAddress);
                if (!enable) {
                    Log.d("test", "该短信被拦截");
                    return;
                }
                Toast.makeText(context, "接到新的短信", Toast.LENGTH_SHORT).show();

                SMS s = new SMS();
                s.phone = msgAddress;
                s.content = msgBody;
                s.time = "" + msgDate;
                s.froms = 1;
                new MyDBHelper(context).addSMS(s);

                if (KWApp.instance != null) {
                    Message m = new Message();
                    m.what = MSG_SMS;
                    m.obj = messages[0];
                    KWApp.instance.mHandler.sendMessage(m);
                }
            }
        }
    }
}
