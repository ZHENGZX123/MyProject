package cn.kiway.mdm.hprose.screen;


import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.mdm.MDMHelper;


public class FxService extends Service {


    public static String ip = "192.168.31.144";
    public static boolean canSendImage = false;
    Handler handler = new Handler();
    Runnable runnable;
    private DatagramSocket client;
    private Thread image_thread;
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;
    byte b[] = new byte[5120];


    public static boolean isCanSendImage() {
        return canSendImage;
    }

    public static void setCanSendImage(boolean canSendImage) {
        FxService.canSendImage = canSendImage;
    }

    public static void setIp(String ip) {
        FxService.ip = ip;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.log("oncreat");
        setCanSendImage(true);
        initDate();
        try {
            if (client == null) {
                client = new DatagramSocket();
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
    }

    private void initDate() {
        runnable = new Runnable() {
            @Override
            public void run() {
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {
                        startCapture();
                    }
                }, 0);
            }
        };
        handler.postDelayed(runnable, 0);
    }

    // 发送图片线程类
    class Image_Thread implements Runnable {
        Bitmap bitmap;
        Image_Thread(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
        @Override
        public void run() {
            try {
                int port = 26891;
                InetAddress addr = InetAddress
                        .getByName(ip);
                sendImage2(this.bitmap, addr, port);
            } catch (UnknownHostException e) {
                e.printStackTrace();
                Logger.log(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log(e.getMessage());
            } finally {
                startCapture();
            }
        }
    }

    /**
     * 图片转换为文件流分片并发送
     *
     * @Author ccj
     */
    public void sendImage2(Bitmap bitmap, InetAddress ip, int port) throws FileNotFoundException {
        if (bitmap != null && canSendImage) {
            Logger.log("-->发送图片开始");
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bitmap_c = ImageUtils.compressImage(bitmap);
                bitmap_c.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                Logger.log("图片大小：" + baos.toByteArray().length / 1024 + "K");
                InputStream in = new ByteArrayInputStream(baos.toByteArray());
                int n = -1;
                while ((n = in.read(b)) != -1) {
                    in.hashCode();
                    datagramPacket = new DatagramPacket(b, b.length, ip, port);
                    datagramSocket = new DatagramSocket();
                    datagramSocket.send(datagramPacket);
                }
                baos.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log(e.getMessage());
            }
            if (datagramSocket != null)
                datagramSocket.close();
            String end = ";!";
            datagramPacket = new DatagramPacket(end.getBytes(), end.getBytes().length, ip, port);
            try {
                datagramSocket = new DatagramSocket();
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log(e.getMessage());
            }
            Logger.log(ip + ";" + port);
            datagramSocket.close();
        }
        //MyLog.i("-->发送图片结束");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void SendImage(Bitmap bitmap) {
        image_thread = new Thread(new Image_Thread(bitmap));
        image_thread.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startCapture() {//截屏
        if (!isCanSendImage())
            return;
        SendImage(MDMHelper.getAdapter().captureScreen());
    }
}
