package cn.kiway.mdm.scoket.scoket.tcp.hprose;


import android.os.Message;

import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.utils.Logger;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.ANSWER;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SIGN;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SUREREPONSE;

/**
 * Created by Administrator on 2017/11/9.
 */

public class AttendClass {
    static AccpectMessageHander acpMessageHander;

    public static void setAccpectMessageHander(AccpectMessageHander accpectMessageHander) {
        acpMessageHander = accpectMessageHander;
    }

    public static void sign(String content) {
        if (acpMessageHander == null)
            return;
        Message message = new Message();
        message.what = SIGN;
        message.obj = content;
        acpMessageHander.sendMessage(message);
        Logger.log("Come Sign" + content);
    }//签到ww

    public static void answerqusetion(String content) {
        if (acpMessageHander == null)
            return;
        Message message = new Message();
        message.what = ANSWER;
        message.obj = content;
        acpMessageHander.sendMessage(message);
        Logger.log(content + "来抢答");
    }//上课抢答

    public static void reponse(String content) {
        Message message = new Message();
        message.what = SUREREPONSE;
        message.obj = content;
        acpMessageHander.sendMessage(message);
    }//确认是否听懂

    public static void message(String userId, String message) {
        Logger.log(userId + "发来了：：" + message);
    }//发送消息

}
