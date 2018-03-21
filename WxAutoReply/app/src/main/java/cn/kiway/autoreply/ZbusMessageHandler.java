package cn.kiway.autoreply;

import android.util.Log;

import java.io.IOException;

import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler implements MessageHandler {

    @Override
    public void handle(Message message, MqClient mqClient) throws IOException {
        String temp = message.getBodyString();
        Log.d("test", "getBodyString = " + temp);
    }


}
