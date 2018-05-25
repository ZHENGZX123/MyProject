package cn.kiway.robot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import cn.kiway.robot.service.AutoReplyService;

/**
 * Created by Administrator on 2018/5/24.
 */

public class SMS_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                for (Object pdu : pdus) {
                    //封装短信参数的对象
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
                    String number = sms.getOriginatingAddress();
                    String body = sms.getMessageBody();
                    //写自己的处理逻辑
                    Log.d("test", "number = " + number);
                    Log.d("test", "body = " + body);

                    if (number.equals("10010") && body.startsWith("尊敬的用户，您的手机或上网卡的号码为")) {
                        String myCellPhone = body.replace("尊敬的用户，您的手机或上网卡的号码为", "").substring(0, 11);
                        Log.d("test", "myCellPhone = " + myCellPhone);

                        AutoReplyService.instance.doFullFillCellPhone(myCellPhone);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}