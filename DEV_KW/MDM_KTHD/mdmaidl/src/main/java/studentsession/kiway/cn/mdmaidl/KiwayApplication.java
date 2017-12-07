package studentsession.kiway.cn.mdmaidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.aidl.RemoteInterface;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/12/6.
 */

public class KiwayApplication extends Application{

    //    服务端的RemoteInterface对象，绑定服务时创建
    public RemoteInterface mRemoteInterface = null;
    ClientCallback clientCallback;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 绑定服务后从服务端获取RemoteInterface对象
            mRemoteInterface = RemoteInterface.Stub.asInterface(service);
            if (clientCallback!=null)
            registCallback();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (clientCallback!=null)
            unRegistCallback();
            //与服务端意外断开时自动重连
            disConnectService();
            connectService();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //connectService();
    }

    private void connectService() {
        clientCallback=null;
        Intent intent = new Intent();
        intent.setAction("cn.kiway.mdm.aidlservice.RemoteAidlService");
        // Android 5.0以上需要设置包名
        intent.setPackage("cn.kiway.mdm");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    public void connectService(ClientCallback clientCallback) {
        Intent intent = new Intent();
        this.clientCallback=clientCallback;
        intent.setAction("cn.kiway.mdm.aidlservice.RemoteAidlService");
        // Android 5.0以上需要设置包名
        intent.setPackage("cn.kiway.mdm");
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private void disConnectService() {
        unbindService(mConnection);
    }

    /**
     * 向服务端注册回调，注册后服务端才能调用客户端方法
     */
    private void registCallback() {
        Log.d(TAG, "Client registCallback()");
        try {
            mRemoteInterface.registClientCallback(clientCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消注册
     */
    private void unRegistCallback() {
        Log.d(TAG, "Client unRegistCallback()");
        try {
                mRemoteInterface.registClientCallback(clientCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
