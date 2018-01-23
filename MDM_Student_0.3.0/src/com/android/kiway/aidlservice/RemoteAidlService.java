package com.android.kiway.aidlservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.kiway.KWApp;
import com.android.kiway.utils.Logger;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.aidl.RemoteInterface;
import cn.kiway.mdmsdk.MDMHelper;

import static com.android.kiway.KWApp.MSG_LOCKONCLASS;
import static com.android.kiway.KWApp.MSG_UNLOCK;

/**
 * 服务端，利用AIDL与客户端通信
 */
public class RemoteAidlService extends Service {

    private static final String TAG = "AidlTest";

    //一个服务端可以对应多个客户端，即包含多个ClientCallback对象，
    //使用RemoteCallbackList可以在客户端意外断开连接时移除ClientCallback，防止DeadObjectException
    private static ClientCallback clientCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mRemoteInterface;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //服务结束是注意移除所有数据
        clientCallback = null;
    }

    /**
     * RemoteInterface.Stub为Android根据aidl文件生成的实现类，
     * 实现了RemoteInterface接口，间接实现了IBinder接口，
     * 客户端绑定时将mRemoteInterface对象返回给客户端，
     * 在服务端定义，在客户端调用
     */
    private RemoteInterface.Stub mRemoteInterface = new RemoteInterface.Stub() {

        @Override
        public void registClientCallback(ClientCallback callback) throws RemoteException {
            //向服务端注册回调
            clientCallback = callback;
            Log.d(TAG, "Service registClientCallback()");
        }

        @Override
        public void unRegistClientCallback(ClientCallback callback) throws RemoteException {
            //服务端取消注册回调
            clientCallback = null;
            Log.d(TAG, "Service unRegistClientCallback()");
        }

        @Override
        public void lockScreen(boolean b) throws RemoteException {
            Logger.log("LockScreen::::::::" + b);
            if (b) {
                KWApp.instance.mHandler.sendEmptyMessage(MSG_LOCKONCLASS);
            } else {
                getSharedPreferences("kiway", 0).edit().putLong("lock_time", 0).commit();
                KWApp.instance.mHandler.sendEmptyMessage(MSG_UNLOCK);
            }
        }

        @Override
        public void shareScreen(boolean i, String ip) throws RemoteException {
            Logger.log("LockScreen::::::::" + i);
        }

        @Override
        public void setHomeButtonDisabled(boolean disabled) throws RemoteException {
            Logger.log("setHomeButtonDisabled::" + disabled);
            MDMHelper.getAdapter().setHomeButtonDisabled(disabled);
        }

        @Override
        public boolean init(String key) throws RemoteException {
            Logger.log("init::::::::" + key);
            return false;
        }

        @Override
        public void callbackMessage(String msg) throws RemoteException {
            //课堂互动的相应消息
//            try {
//                ZbusUtils.sendMsg(zbusTeacherTopic, "抢答");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    };

    /**
     * 调客户端的下课方法
     */
    public static void goOutClass() {
        if (clientCallback == null)
            return;
        try {
            clientCallback.goOutClass();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调客户端的接收方法
     */
    public static void accpterMessage(Context context, String msg) {
        if (clientCallback == null)
            return;
        try {
            String token = context.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            clientCallback.accpterMessage(msg, token);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调客户端的接收方法
     */
    public static void attendClass(String msg) {
        if (clientCallback == null)
            return;
        try {
            clientCallback.attendClass(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

