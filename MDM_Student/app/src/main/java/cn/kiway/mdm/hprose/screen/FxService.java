package cn.kiway.mdm.hprose.screen;


import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.WindowManager;

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
import java.nio.ByteBuffer;

import cn.kiway.mdm.KWApp;
import cn.kiway.mdm.hprose.socket.Logger;


public class FxService extends Service {


    public static String ip = "192.168.31.144";
    public static boolean canSendImage = false;
    private MediaProjection mMediaProjection = null;
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
        createVirtualEnvironment();
        runnable = new Runnable() {
            @Override
            public void run() {
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    public void run() {
                        //start virtual
                        startVirtual();
                    }
                }, 0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaProjection!=null){
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }


    public void SendImage(Bitmap bitmap) {
        image_thread = new Thread(new Image_Thread(bitmap));
        image_thread.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startVirtual() {
        if (mMediaProjection != null) {
            Logger.log("want to display virtual");
            virtualDisplay();
        } else {
            Logger.log("start screen capture intent");
            Logger.log("want to build mediaprojection and display virtual");
            setUpMediaProjection();
            virtualDisplay();
        }
    }

    public Intent mResultData = null;
    public int mResultCode = 0;
    public MediaProjectionManager mMediaProjectionManager1 = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setUpMediaProjection() {
        mResultData = ((KWApp) getApplication()).getIntent();
        mResultCode = ((KWApp) getApplication()).getResult();
        mMediaProjectionManager1 = (MediaProjectionManager) getApplication().getSystemService(Context
                .MEDIA_PROJECTION_SERVICE);
        mMediaProjection = mMediaProjectionManager1.getMediaProjection(mResultCode, mResultData);
        Logger.log("mMediaProjection defined");
    }

    private VirtualDisplay mVirtualDisplay = null;
    private int windowWidth = 0;
    private int windowHeight = 0;
    private ImageReader mImageReader = null;
    private int mScreenDensity = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void virtualDisplay() {
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                windowWidth, windowHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startCapture() {
        if (!isCanSendImage())
            return;
        Image image = mImageReader.acquireLatestImage();
        Bitmap bitmap = null;
        if (image != null) {
            int width = image.getWidth();
            int height = image.getHeight();
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
            bitmap = ImageUtils.compressImage(bitmap);
            image.close();
            Logger.log("startCapture()...end");
        }
        SendImage(bitmap);
    }

    private WindowManager mWindowManager1 = null;
    private DisplayMetrics metrics = null;

    private void createVirtualEnvironment() {
        mMediaProjectionManager1 = (MediaProjectionManager) getApplication().getSystemService(Context
                .MEDIA_PROJECTION_SERVICE);
        mWindowManager1 = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        windowWidth = mWindowManager1.getDefaultDisplay().getWidth();
        windowHeight = mWindowManager1.getDefaultDisplay().getHeight();
        metrics = new DisplayMetrics();
        mWindowManager1.getDefaultDisplay().getMetrics(metrics);
        mScreenDensity = metrics.densityDpi;
        mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2); //ImageFormat.RGB_565
        Logger.log("prepared the virtual environment");
    }
}
