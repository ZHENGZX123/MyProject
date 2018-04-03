package cn.kiway.robot.guard.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import cn.kiway.wx.aidl.ClientCallback;
import cn.kiway.wx.aidl.RemoteInterface;

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
            Log.e(TAG, "Service registClientCallback()" + callback);
        }

        @Override
        public void unRegistClientCallback(ClientCallback callback) throws RemoteException {
            //服务端取消注册回调
            clientCallback = null;
            Log.e(TAG, "Service unRegistClientCallback()");
        }

        @Override
        public void clientMessage(String b) throws RemoteException {
            Log.e(TAG, "来自客户端的消息" + b);
        }

    };

    /**
     * 发消息给客户端
     */
    public static void sendClientMsg(String s) {
        if (clientCallback == null)
            return;
        try {
            clientCallback.accpterMessage(s);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

