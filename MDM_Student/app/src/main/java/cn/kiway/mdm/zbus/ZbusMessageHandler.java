package cn.kiway.mdm.zbus;

import java.io.IOException;

import cn.kiway.mdm.utils.Logger;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler implements MessageHandler {
    @Override
    public void handle(Message message, MqClient mqClient) throws IOException {
        Logger.log("zbus::::::::"+message.getBodyString());
    }
}
