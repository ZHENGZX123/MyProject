package cn.kiway.mdm.hprose.socket;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.hprose.hprose.net.KwConntectionCallback;
import cn.kiway.mdm.hprose.hprose.net.KwHproseTcpClient;
import cn.kiway.mdm.hprose.socket.actions.GroundActions;
import cn.kiway.mdm.hprose.socket.actions.OwnerActions;
import cn.kiway.mdm.hprose.socket.callback.PushClass;

/**
 * Created by Administrator on 2017/11/7.
 */


public class KwHproseClient {
    public static KwHproseTcpClient client;
    static MainActivity activity;
    public static PushClass helloClient;

    public static void connect(MainActivity activity2, String tcp, String userId, KwConntectionCallback
            conntectionCallback) throws Throwable {
        System.out.println("-----START------");
        activity = activity2;
        client = new KwHproseTcpClient("tcp://" + tcp + ":30100", conntectionCallback);
        //client = new KwHproseHttpClient("http://" + tcp + ":30100",conntectionCallback);
        helloClient = client.useService(PushClass.class);
        GroundActions ground = new GroundActions<>(activity2);
        OwnerActions owner = new OwnerActions<>(activity2);
        helloClient.publish(userId);
        if (!client.isSubscribed("ground"))
            client.subscribe("ground", ground);//班级组
        if (!client.isSubscribed(userId + "owner"))
            client.subscribe(userId + "owner", owner);//个人
        System.out.println("End");
    }

    public static void stop() {
        if (client != null)
            client.close();
        client = null;
    }
}

