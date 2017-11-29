package cn.kiway.mdm.scoket;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

import cn.kiway.mdm.scoket.scoket.tcp.netty.PushServer;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.scoket.utils.WifiUtils;
import cn.kiway.mdm.teacher.R;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.OFFSCREENSHARE;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SCREENSHARE;


/**
 * Created by Administrator on 2017/5/18.
 */

public class ScreenActivity extends Activity {

    ImageView imageView;
    Context mContext;
    int comebackTime = 30;
    private final int COMPLETED = 0x111;
    Thread receiveThread;
    Runnable runnable4;
    private int IMAGE_PORT = 26891;
    private DatagramPacket datagramPacket;
    private DatagramSocket datagramSocket;
    byte b[] = new byte[5120];
    //向UI线程发送消息
    private Handler handler, handler4;
    boolean isRun = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        mContext = this;
        imageView = (ImageView) findViewById(R.id.iv_show);
        Logger.log("running start");
        handler = new MyHandler();
        isRun = true;
        receiveThread = new Thread(new Runnable() {//启动图像监听的数据线程
            @Override
            public void run() {
                Logger.log("running start...");
                while (isRun) {
                    try {
                        receive();
                    } catch (Exception e) {
                        Logger.log(e.getMessage());
                    }
                }
            }
        });
        receiveThread.start();
       // Toast.makeText(this, "初始化完成", Toast.LENGTH_SHORT).show();
        sendMsg();//初始化完成在发送命令
        handler4 = new Handler();
        runnable4 = new Runnable() {
            @Override
            public void run() {
                comebackTime--;
                if (comebackTime < 0) {
                    // 需要做的事:发送消息
                    Message message = new Message();
                    message.what = 104;
                    handler.sendMessage(message);
                }
                handler4.postDelayed(runnable4, 1000);
            }
        };
        handler4.postDelayed(runnable4, 1000);
    }

    void sendMsg() {
        try {
            JSONObject da = new JSONObject();
            da.put("msgType", SCREENSHARE);
            da.put("msg", WifiUtils.getIPAddress(this));
            PushServer.hproseSrv.push(getIntent().getStringExtra("clientId") + "owner", da.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void OffSrceen(View view) {
        try {
            JSONObject da = new JSONObject();
            da.put("msgType", OFFSCREENSHARE);
            da.put("msg", WifiUtils.getIPAddress(this));
            PushServer.hproseSrv.push(getIntent().getStringExtra("clientId") + "owner", da.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, getString(R.string.close_Screen), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public byte[] receive() throws SocketException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (datagramSocket == null) {
            datagramSocket = new DatagramSocket(null);
            datagramSocket.setReuseAddress(true);
            datagramSocket.bind(new InetSocketAddress(IMAGE_PORT));
        } else
            try {
                datagramSocket = new DatagramSocket(IMAGE_PORT);
            } catch (SocketException e) {
            }
        while (true) {
            datagramPacket = new DatagramPacket(b, b.length);
            try {
                datagramSocket.receive(datagramPacket);
                comebackTime = 30;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logger.log("recive");
            String msg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            if (msg.startsWith(";!")) {
                Logger.log("接收到所有数据");
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap = Bytes2Bitmap(baos.toByteArray());
                byte data[] = baos.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Message message = new Message();
                message.what = COMPLETED;
                message.obj = bitmap;
                handler.sendMessage(message);
                break;
            } else {
                baos.write(datagramPacket.getData(), 0, datagramPacket.getLength());
            }
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();

    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                bitmap = (Bitmap) msg.obj;
                if (bitmap != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(bitmap);
                    Logger.log("连接还在");
                }
                super.handleMessage(msg);
            } else if (msg.what == 104) {
                imageView.setVisibility(View.GONE);
                Toast.makeText(ScreenActivity.this, getString(R.string.con), Toast.LENGTH_SHORT).show();
                finish();
                Logger.log("连接断线了");
            }
        }
    }

    Bitmap bitmap = null;

    Handler uiHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.obj != null)
                Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            if (bitmap != null && msg.obj.equals("UI")) {
                Toast.makeText(mContext, getString(R.string.update_pic), Toast.LENGTH_SHORT).show();
                imageView.setImageBitmap(bitmap);
            }
            super.handleMessage(msg);
        }
    };

    // byte[]转换成Bitmap
    public Bitmap Bytes2Bitmap(byte[] b) {
        try {
            if (b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
        } catch (Exception e) {
            Message message = new Message();
            message.obj = "Bytes2Bitmap";
            uiHandler.sendMessage(message);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler4.removeCallbacks(runnable4);
        receiveThread.interrupt();
        isRun = false;
    }
}
