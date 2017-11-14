package cn.kiway.mdm.scoket.scoket.tcp.netty;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

import cn.kiway.mdm.hprose.hprose.net.KwHproseTcpServer;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.hprose.AttendClass;
import cn.kiway.mdm.scoket.scoket.tcp.hprose.PushClass;
import cn.kiway.mdm.scoket.utils.Logger;


/**
 * netty推送服务端
 */
public class PushServer {

    public static NettyServerBootstrap nettyServerBootstrap;
    public static KwHproseTcpServer hproseSrv;
    public final static String FilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kiwaymdm";

    public static void start(AccpectMessageHander accpectMessageHander) {
        try {
            nettyServerBootstrap = new NettyServerBootstrap(accpectMessageHander, 30100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (nettyServerBootstrap != null)
            nettyServerBootstrap.stop();
        nettyServerBootstrap = null;
    }

    public static void startHprose(AccpectMessageHander accpectMessageHander) {
        try {
            AttendClass.setAccpectMessageHander(accpectMessageHander);
            PushClass.setAccpectMessageHander(accpectMessageHander);
            HproseChannelMapStatic.removeAll();//初始化话
            hproseSrv = new KwHproseTcpServer("0.0.0.0", 30100, accpectMessageHander);
            hproseSrv.add(AttendClass.class);
            hproseSrv.add(PushClass.class);
            hproseSrv.start();
            File file=new File(FilePath);
            if (!file.exists())
                file.mkdirs();
            hproseSrv.setSharedir(FilePath + "/");
            Logger.log("开启：：：hprose server start---------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopHprose() {
        if (hproseSrv != null)
            hproseSrv.stop();
        hproseSrv = null;
        AttendClass.setAccpectMessageHander(null);
        PushClass.setAccpectMessageHander(null);
        Logger.log("关闭：：：hprose server stop---------------");
    }
}

