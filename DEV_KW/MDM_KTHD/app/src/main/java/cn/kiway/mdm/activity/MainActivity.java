package cn.kiway.mdm.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cn.kiway.mdm.dialog.UdpConnectDialog;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.modle.IpModel;
import studentsession.kiway.cn.mdm_studentsession.R;

public class MainActivity extends BaseActivity {

    public static MainActivity instantce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantce = this;
        setContentView(R.layout.activity_main);
        getAppData();
        udpConnectDialog = new UdpConnectDialog(this);
    }

    public void onInfo(View view) {//个人信息
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void onDis(View view){
        findViewById(R.id.connect).setVisibility(View.GONE);
    }
    public void onUdp(View view) {//连接不上开始udp 手动连
        UdpStart();
        udpConnectDialog.show();
        udpConnectDialog.setCancelable(false);
    }



    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            String shangke = intent.getStringExtra("shangke");
            Logger.log("shangke::::::"+shangke);
            if (shangke != null && !shangke.equals("")) {
                try {
                    app.onClass(new JSONObject(shangke));
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                findViewById(R.id.connect).setVisibility(View.VISIBLE);
            }
        }else {
            findViewById(R.id.connect).setVisibility(View.VISIBLE);
        }
    }




    WifiManager.MulticastLock lock;
    DatagramSocket udpSocket = null;
    DatagramPacket udpPacket = null;
    boolean isRun;
    String codeString;
    UdpConnectDialog udpConnectDialog;

    public void UdpStart() {//开始udp
        //红米手机接收不到udp广播,打开udp锁
        WifiManager manager = (WifiManager) getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        lock = manager.createMulticastLock("localWifi");
        new UDPClientThread().start();
    }

    public void UdpClose() {//关闭udp
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (udpConnectDialog != null)
                    udpConnectDialog.dismiss();
                findViewById(R.id.connect).setVisibility(View.GONE);
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
        isRun = false;
        if(udpSocket!=null)
        udpSocket.close();
        udpSocket=null;
    }

    public void onConnect() {//启动连接是显示界面
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.instantce.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                MainActivity.instantce.findViewById(R.id.connect).setVisibility(View.VISIBLE);
            }
        });
    }

    private class UDPClientThread extends Thread {//开启udp广播
        public UDPClientThread() {
            /* 开启线程 */
            System.out.println("监听广播开启");
            isRun = true;
        }

        @Override
        public void run() {
            byte[] data = new byte[256];
            try {
                udpSocket = new DatagramSocket(30200);
                udpPacket = new DatagramPacket(data, data.length);
            } catch (SocketException e1) {
                e1.printStackTrace();
            }
            while (isRun) {
                try {
                    lock.acquire();
                    udpSocket.receive(udpPacket);
                } catch (Exception e) {
                }
                if (udpPacket != null && null != udpPacket.getAddress()) {
                    codeString = new String(data, 0, udpPacket.getLength());
                    final String ip = udpPacket.getAddress().toString()
                            .substring(1);
                    Logger.log("ip:::" + ip + "\n内容" + codeString);

                    try {
                        JSONObject dc = new JSONObject(codeString);
                        final IpModel ipModel = new IpModel();
                        ipModel.ip = ip;
                        ipModel.className = dc.optString("className");
                        ipModel.platform = dc.optString("platform");
                        ipModel.time = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (System.currentTimeMillis() - time < 2000)
                                    return;
                                time = System.currentTimeMillis();
                                if (udpConnectDialog != null)
                                    udpConnectDialog.setList(ipModel);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                lock.release();
            }
        }
    }

    long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (app.isAttenClass) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
