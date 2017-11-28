package cn.kiway.mdm.hprose.socket.tcp.util;

/**
 * Created by Administrator on 2015/9/16.
 */
public class NETConfig {
    public static boolean NET_DEBUG = true;
    public static String TAG = "NETConfig";
    public static int NET_PORT = 30100;//端口号
    public static int NET_TIMEOUT = 5000;//连接时间

    public static class NETEvent {
        public static final int SOCKET_EVENT_CONNECT_TCP = 0x01;
        public static final int SOCKET_EVENT_BIND_TCP = 0x02;
        public static final int SOCKET_EVENT_CONNECT_UDP = 0x03;
        public static final int SOCKET_EVENT_BIND_UDP = 0x04;
        public static final int SOCKET_EVENT_SEND_TCP_DATA = 0x05;
        public static final int SOCKET_EVENT_SEND_UDP_DATA = 0x06;
        public static final int SOCKET_EVENT_CLOSE = 0x07;
    }
}
