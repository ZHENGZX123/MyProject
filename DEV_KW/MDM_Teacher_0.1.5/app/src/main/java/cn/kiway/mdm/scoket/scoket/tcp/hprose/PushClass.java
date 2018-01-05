package cn.kiway.mdm.scoket.scoket.tcp.hprose;


import android.os.Message;

import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType;
import cn.kiway.mdm.scoket.utils.Logger;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer.hproseSrv;


/**
 * Created by Administrator on 2017/11/9.
 */

public class PushClass {
    static AccpectMessageHander acpMessageHander;

    public static void setAccpectMessageHander(AccpectMessageHander accpectMessageHander) {
        acpMessageHander = accpectMessageHander;
    }

    public static void publish(String userId) {
        if (hproseSrv != null) {
            Logger.log(userId + "登录来注册了");
            hproseSrv.publish(userId + "owner");
            hproseSrv.publish("ground");
            if (acpMessageHander != null) {
                Message message = new Message();
                message.what = MessageType.LOGIN;
                message.obj = userId;
                acpMessageHander.sendMessage(message);
            }
        }
    }
}
