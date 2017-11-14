package cn.kiway.mdm.scoket.scoket.tcp.netty;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

/**
 * Created by pangff on 16/1/16.
 */
public class HproseChannelMapStatic {

    private static Map<String, SocketChannel> channelMap = new ConcurrentHashMap();


    public static void addChannel(String id, SocketChannel socketChannel) {
        channelMap.put(id, socketChannel);
    }

    public static SocketChannel getChannel(String clientId) {
        return channelMap.get(clientId);

    }

    public static String removeChannel(SocketChannel socketChannel) {
        for (Map.Entry entry : channelMap.entrySet()) {
            if (entry.getValue() == socketChannel) {
                channelMap.remove(entry.getKey());
                return entry.getKey().toString();
            }
        }
        return "";
    }

    public static void sendAllChannel(String msg) {
        for (Map.Entry entry : channelMap.entrySet()) {
            ((Channel) entry.getValue()).writeAndFlush(msg);
        }
    }

    public static void removeAll() {
        channelMap = new ConcurrentHashMap();
    }
}
