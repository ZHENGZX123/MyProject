package cn.kiway.mdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.ByteBuffer;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.NotifyMsgActivity;
import cn.kiway.mdm.activity.ScreenActivity;
import cn.kiway.mdm.db.MyDBHelper;
import cn.kiway.mdm.hprose.hprose.net.KwConnection;
import cn.kiway.mdm.hprose.hprose.net.KwConntectionCallback;
import cn.kiway.mdm.hprose.socket.KwHproseClient;
import cn.kiway.mdm.hprose.socket.Logger;
import cn.kiway.mdm.hprose.socket.MessageType;
import cn.kiway.mdm.hprose.socket.actions.ActionsMessageHandle;
import cn.kiway.mdm.hprose.socket.tcp.Client;
import cn.kiway.mdm.hprose.socket.tcp.HandlerClient;
import cn.kiway.mdm.utils.Utils;
import hprose.net.TimeoutType;
import studentsession.kiway.cn.mdmaidl.KiwayApplication;

/**
 * Created by Administrator on 2017/12/4.
 */

public class App extends KiwayApplication {


    public static App instance;
    public HandlerClient client;
    public boolean isAttenClass = false;
    public String teacherIp = "";
    public int connectNumber = 0;
    public boolean isIos = false;
    public Activity currentActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        connectService(mClientCallback);
    }


    public void onClass(JSONObject data) {//上课
        isAttenClass = false;
        teacherIp = data.optString("ip");
        if (data.optString("platform").equals("IOS"))
            isIos = true;
        else
            isIos = false;
        connectTcp(teacherIp);
        connectNumber = 0;
        MainActivity.instantce.onConnect();
    }

    public void connectTcp(String ip) {//开始连接判断
        if (ip == null || currentActivity == null || isAttenClass)
            return;
        if (!Utils.ping(currentActivity, ip)) {//这个判断方法不太靠谱
            showMessage("无法连接上课，请确认在同个wifi下");
            return;
        }
        if (isIos) {//ios tcp连接
            connectIOS(ip);
        } else {//
            if (client != null && client.isConnect()) {
                client.close();
                client = null;
            }
            Message msg = new Message();
            msg.what = MSG_CONNECT;
            mHandler.sendMessage(msg);
        }
    }

    public void connectIOS(String ip) {
        if (client != null && client.isConnect())
            return;
        KwHproseClient.stop();
        client = new HandlerClient();
        client.connectTCP(ip, new Client.TcpMessageCallBack() {
            @Override
            public void accpetMessage(String message) throws Exception {
                if (currentActivity == null) return;
                ActionsMessageHandle.MessageHandle(currentActivity, message);
            }

            @Override
            public void connectTcpSuccess() throws Exception {
                JSONObject da = new JSONObject();
                isAttenClass = true;
                try {
                    da.put("msgType", MessageType.LOGIN);
                    da.put("userId", Utils.getIMEI(App.this));
                    da.put("msg", "我是新用户");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MainActivity.instantce.UdpClose();
                client.sendTCP(da.toString());
                showMessage("IOS 上课连接完成");
                mHandler.sendEmptyMessage(MSG_HOME_DIS);
                mHandler.sendEmptyMessageDelayed(MSG_XIAKE, 60 * 1000 * 45);
                MainActivity.instantce.UdpClose();
            }

            @Override
            public void connectTcpFailed() throws Exception {
                showMessage("IOS 上课连接失败");
                isAttenClass = false;
                mHandler.sendEmptyMessage(MSG_HOME_TURE);
            }

            @Override
            public void disconnectTcp() throws Exception {
                showMessage("IOS 连接掉线");
                isAttenClass = false;
            }
        });
    }


    public static final int MSG_CONNECT = 0x000;//上课连接
    public static final int MSG_XIAKE = MSG_CONNECT + 1;//下课
    public static final int MSG_LOCKONCLASS = MSG_CONNECT + 2;//上课锁屏
    public static final int MSG_UNLOCK = MSG_CONNECT + 3;//解锁
    public static final int MSG_HOME_DIS = MSG_CONNECT + 4;//禁用home
    public static final int MSG_HOME_TURE=MSG_CONNECT+5;//开启home


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CONNECT) {//android hprose 上课连接件
                try {
                    KwHproseClient.connect((BaseActivity) currentActivity, teacherIp, Utils.getIMEI(currentActivity),
                            new KwConntectionCallback() {
                                @Override
                                public void onConnect(KwConnection conn) {
                                    Logger.log("正在连接");
                                }

                                @Override
                                public void onConnected(KwConnection conn) {
                                    isAttenClass = true;
                                    showMessage("上课连接完成");
                                    sendEmptyMessage(MSG_HOME_DIS);
                                  sendEmptyMessageDelayed(MSG_XIAKE, 60 * 1000 * 45);
                                    MainActivity.instantce.UdpClose();
                                }

                                @Override
                                public void onReceived(KwConnection conn, ByteBuffer data, Integer id) {
                                }

                                @Override
                                public void onClose() {
                                    Logger.log("连接关闭");
                                    isAttenClass = false;
                                   sendEmptyMessage(MSG_HOME_TURE);
                                }

                                @Override
                                public void onError(KwConnection conn, Exception e) {
                                    Logger.log("连接错误" + e);
                                    isAttenClass = false;
                                    if (connectNumber > 5)
                                        isAttenClass = false;
                                    connectNumber++;
                                    if (isAttenClass)
                                        connectTcp(teacherIp);
                                }

                                @Override
                                public void onTimeout(KwConnection conn, TimeoutType type) {
                                    isAttenClass = false;
                                    sendEmptyMessage(MSG_HOME_TURE);
                                }
                            });
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            } else if (msg.what == MSG_LOCKONCLASS) {//锁屏
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    return;
                }
                startActivity(new Intent(getApplicationContext(), ScreenActivity.class).addFlags(Intent
                        .FLAG_ACTIVITY_NEW_TASK));
            } else if (msg.what == MSG_UNLOCK) {//解锁
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    currentActivity.finish();
                }
            } else if (msg.what == MSG_XIAKE) {//
                showMessage("同学们,到点下课啦");
               sendEmptyMessage(MSG_HOME_TURE);
            }else if(msg.what==MSG_HOME_DIS){//禁用home
                try {
                    mRemoteInterface.setHomeButtonDisabled(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            else if(msg.what==MSG_HOME_TURE){//开启home
                try {
                    mRemoteInterface.setHomeButtonDisabled(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    //    客户端的ClientCallback对象
    //    在服务端注册后服务端可以调用客户端方法
    private ClientCallback.Stub mClientCallback = new ClientCallback.Stub() {
        @Override
        public void goOutClass() throws RemoteException {
            showMessage("下课啦，同学们");
            isAttenClass = false;
            connectNumber = 0;
            if (isIos) {
                if (client != null)
                    client.close();
                client = null;
            } else {
                KwHproseClient.stop();
            }
            mHandler.sendEmptyMessage(MSG_HOME_TURE);
        }

        @Override
        public void accpterMessage(String msg) throws RemoteException {
            try {
                JSONObject data = new JSONObject(msg);
                if (currentActivity != null) {
                    ((BaseActivity) currentActivity).NotifyShow(data.optJSONObject("content")
                            .optString("title"), data.optJSONObject("content").optString("content"), "发送人：" + (
                            data.optJSONObject("content").optString("sendName")));
                    new MyDBHelper(currentActivity).addNofityMessage(data.optJSONObject("content"));
                    if (App.instance.currentActivity != null && App.instance.currentActivity instanceof
                            NotifyMsgActivity) {
                        ((NotifyMsgActivity) App.instance.currentActivity).refreshUI();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void attendClass(String msg) throws RemoteException {
            try {
                onClass(new JSONObject(msg));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public void showMessage(final String message) {
        if (currentActivity != null)
            ((BaseActivity) currentActivity).showMessage(message);
    }
}
