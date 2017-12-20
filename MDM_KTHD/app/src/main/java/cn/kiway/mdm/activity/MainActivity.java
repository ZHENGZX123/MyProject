package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import cn.kiway.mdm.App;
import cn.kiway.mdm.dialog.UdpConnectDialog;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.modle.IpModel;
import cn.kiway.mdm.utils.NetworkUtil;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.hprose.socket.MessageType.SUREREPONSE;
import static cn.kiway.mdm.utils.IContants.CHECK_VERSION_URL;
import static cn.kiway.mdm.utils.Utils.getCurrentVersion;

public class MainActivity extends BaseActivity {

    public static MainActivity instantce;
    private Dialog dialog_download;
    WifiManager.MulticastLock lock;
    DatagramSocket udpSocket = null;
    DatagramPacket udpPacket = null;
    boolean isRun;
    String codeString;
    UdpConnectDialog udpConnectDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instantce = this;
        setContentView(R.layout.activity_main);
        getAppData();
        udpConnectDialog = new UdpConnectDialog(this);
        Logger.log(":::::::::::::::::" + Utils.getIMEI(this));
    }

    public void onInfo(View view) {//个人信息
        startActivity(new Intent(this,UserInfoActivity.class));
    }

    public void onFile(View view) {//查看文件
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void onMsg(View view) {//查看消息
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void onDis(View view) {
        findViewById(R.id.connect).setVisibility(View.GONE);
    }

    public void onUdp(View view) {//连接不上开始udp 手动连
        if (App.instance.currentActivity != null)
            ((BaseActivity) App.instance.currentActivity).Session(SUREREPONSE);
//        UdpStart();
//        udpConnectDialog.show();
//        udpConnectDialog.setCancelable(false);
    }


    private void getAppData() {//判断是手动打开还是推送打开
        Intent intent = getIntent();
        if (intent != null) {
            String shangke = intent.getStringExtra("shangke");
            Logger.log("shangke::::::" + shangke);
            if (shangke != null && !shangke.equals("")) {
                try {
                    app.onClass(new JSONObject(shangke));
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                findViewById(R.id.connect).setVisibility(View.VISIBLE);
                checkNewVersion();
            }
        } else {
            findViewById(R.id.connect).setVisibility(View.VISIBLE);
            checkNewVersion();
        }
    }


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
        if (udpSocket != null)
            udpSocket.close();
        udpSocket = null;
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
    public void onConnectTimeOut() {//启动连接失败显示界面
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.instantce.findViewById(R.id.progressBar).setVisibility(View.GONE);
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

    //下面是版本更新相关
    public void checkNewVersion() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpGet httpRequest = new HttpGet(CHECK_VERSION_URL);
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    Log.d("test", "new version = " + ret);
                    Message msg = new Message();
                    msg.what = 2;
                    msg.obj = ret;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(3);
                }
            }
        }.start();
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 2) {
                String ret = (String) msg.obj;
                try {
                    //1.apk更新
                    Log.d("test", "新版本返回值" + ret);
                    String apkVersion = new JSONObject(ret).getString("apkCode");
                    String apkUrl = new JSONObject(ret).getString("apkUrl");

                    String zipCode = new JSONObject(ret).getString("zipCode");
                    String zipUrl = new JSONObject(ret).getString("zipUrl");
                    if (getCurrentVersion(getApplicationContext()).compareTo(apkVersion) < 0) {
//                        showUpdateConfirmDialog(apkUrl);
                        downloadSilently(apkUrl, apkVersion);
                    }
//                   else {****************************暂时没有zip包更新 所以不用
//                        //如果APK没有最新版本，比较包的版本。如果内置包的版本号比较高，直接替换
//                        boolean flag = false;
//                        String currentPackage = getSharedPreferences("kiway", 0).getString("version_package",
// "0.0.1");
//                        if (currentPackage.compareTo(currentPackageVersion) < 0) {
//                            Log.d("test", "内置包的版本大，替换新包");
//                            getSharedPreferences("kiway", 0).edit().putBoolean("isFirst", true).commit();
//                            checkIsFirst();
//                            flag = true;
//                        }
//                        //替换完内置包之后，比较内置包和外包，如果版本号还是小了，更新外包
//                        currentPackage = getSharedPreferences("kiway", 0).getString("version_package", "0.0.1");
//                        String outer_package = zipCode;
//                        if (currentPackage.compareTo(outer_package) < 0) {//提示有新的包，下载新的包
//                            Log.d("test", "下载新的H5包");
//                            updatePackage(outer_package, zipUrl);
//                        } else {
//                            Log.d("test", "H5包是最新的");
//                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (msg.what == 4) {
                // 下载完成后安装
                String savedFilePath = (String) msg.obj;
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(savedFilePath)), "application/vnd.android.package-archive");
                startActivity(intent);
                finish();
            }
        }
    };

    private void downloadSilently(String apkUrl, String version) {
        boolean isWifi = NetworkUtil.isWifi(this);
        if (!isWifi) {
            Log.d("test", "不是wifi...");
            return;
        }
        final String savedFilePath = "/mnt/sdcard/cache/mdm_zhkt_" + version + ".apk";
        if (new File(savedFilePath).exists()) {
            Log.d("test", "该文件已经下载好了");
            askforInstall(savedFilePath);
            return;
        }
        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(savedFilePath);
        params.setAutoRename(false);
        params.setAutoResume(true);
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //成功后弹出对话框询问，是否安装
                askforInstall(savedFilePath);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void askforInstall(final String savedFilePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_HOLO_LIGHT);
        dialog_download = builder.setMessage(getString(R.string.new_version)).setNegativeButton(android.R.string.ok, new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        dialog_download.dismiss();
                        Message msg = new Message();
                        msg.what = 4;
                        msg.obj = savedFilePath;
                        mHandler.sendMessage(msg);
                    }
                }).setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog_download.show();
    }

    ////////////////以上是版本更新
    long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (app.isAttenClass) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
