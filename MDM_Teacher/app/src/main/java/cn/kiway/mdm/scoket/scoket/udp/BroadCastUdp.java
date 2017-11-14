package cn.kiway.mdm.scoket.scoket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import cn.kiway.mdm.scoket.utils.Logger;


public class BroadCastUdp extends Thread {
    private String dataString;

    private static final int DEFAULT_PORT = 30200;// 发送广播的端口

    private static final int MAX_DATA_PACKET_LENGTH = 40;// 发送广播数据的最大长度

    private byte[] buffer = new byte[MAX_DATA_PACKET_LENGTH];// 发送的数据

    public  boolean isRun = true;// 是否发送广播

    private DatagramSocket udpSocket;// 广播

    DatagramPacket udpPacket = null;

    DatagramPacket dataPacket = null;

    public BroadCastUdp(String dataString) {
        this.dataString = dataString;
        isRun = true;
    }

    public void setMessageData(String data) {
        this.dataString = data;
    }

    public void run() {
        byte[] data1 = new byte[256];
        try {
            if (udpSocket == null)
                udpSocket = new DatagramSocket(DEFAULT_PORT);
            dataPacket = new DatagramPacket(buffer, MAX_DATA_PACKET_LENGTH);
            udpPacket = new DatagramPacket(data1, data1.length);
            byte[] data = dataString.getBytes();
            dataPacket.setData(data);
            dataPacket.setLength(data.length);
            dataPacket.setPort(DEFAULT_PORT);
            InetAddress broadcastAddr;
            broadcastAddr = InetAddress.getByName("255.255.255.255");
            dataPacket.setAddress(broadcastAddr);
        } catch (Exception e) {
            Logger.log("-----" + e.toString());
        }
        while (isRun) {
            try {
                udpSocket.send(dataPacket);
                udpSocket.receive(udpPacket);
                Logger.log("发送广播：：：" + dataString);
                sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
