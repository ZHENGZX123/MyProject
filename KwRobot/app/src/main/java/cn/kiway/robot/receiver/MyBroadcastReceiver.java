package cn.kiway.robot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.kiway.robot.db.MyDBHelper;

/**
 * Created by Administrator on 2018/5/25.
 */

public class MyBroadcastReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        String content=intent.getStringExtra("content");
        String talker=intent.getStringExtra("talker");
        long createTime=intent.getLongExtra("createTime",System.currentTimeMillis());
        int talkerType=intent.getIntExtra("talkerType",1);
        int isSend=intent.getIntExtra("isSend",0);
        new MyDBHelper(context).addMessage(content,talker,createTime,talkerType,isSend);
        new MyDBHelper(context).getMessages();
    }
}
