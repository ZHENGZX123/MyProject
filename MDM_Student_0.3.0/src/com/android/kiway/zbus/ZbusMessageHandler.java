package com.android.kiway.zbus;

import android.util.Log;

import com.android.kiway.activity.MainActivity;

import java.io.IOException;

import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler implements MessageHandler {

    public ZbusMessageHandler(MainActivity activity) {

    }

    @Override
    public void handle(Message message, MqClient mqClient) throws IOException {


        Log.d("test", "message = " + message.getBodyString());
    }
}
