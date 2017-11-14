package cn.kiway.mdm.hprose.socket.callback;

/**
 * Created by Administrator on 2017/11/9.
 */

public interface PushClass {
    void publish(String userId);//登录注册

    void sign(String content);//签到

    void reponse(String content);//响应回调

    void message(String content);//发送消息

    void answerqusetion(String content);//抢答
}

