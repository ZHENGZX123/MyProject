package cn.kiway.mdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import cn.kiway.aidl.ClientCallback;
import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.activity.ScreenActivity;
import studentsession.kiway.cn.mdmaidl.KiwayApplication;

/**
 * Created by Administrator on 2017/12/4.
 */

public class App extends KiwayApplication {


    public static App instance;
    public boolean isAttenClass = false;
    public Activity currentActivity;

    public static final String PATH = "/mnt/sdcard/kiway_mdm_kthd/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //xutils
        x.Ext.init(this);
        connectService(mClientCallback);
    }


    public static final int MSG_CONNECT = 0x000;//上课连接
    public static final int MSG_XIAKE = MSG_CONNECT + 1;//下课

    public static final int MSG_LOCKONCLASS = MSG_CONNECT + 2;//上课锁屏
    public static final int MSG_UNLOCK = MSG_CONNECT + 3;//解锁
    public static final int MSG_HOME_DIS = MSG_CONNECT + 4;//禁用home
    public static final int MSG_HOME_TURE = MSG_CONNECT + 5;//开启home
    public static final int MSG_TIME_OUT = MSG_CONNECT + 6;//上课超时判断


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOCKONCLASS) {//锁屏
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    return;
                }
                startActivity(new Intent(getApplicationContext(), ScreenActivity.class).addFlags(Intent
                        .FLAG_ACTIVITY_NEW_TASK));
            } else if (msg.what == MSG_UNLOCK) {//解锁
                if (currentActivity != null && currentActivity instanceof ScreenActivity) {
                    currentActivity.finish();
                }
            } else if (msg.what == MSG_HOME_DIS) {//禁用home
                try {
                    if (mRemoteInterface != null)
                        mRemoteInterface.setHomeButtonDisabled(true);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (msg.what == MSG_HOME_TURE) {//开启home
                try {
                    if (mRemoteInterface != null)
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
            mHandler.removeMessages(MSG_TIME_OUT);
            mHandler.sendEmptyMessage(MSG_HOME_TURE);
        }

        @Override
        public void accpterMessage(String msg, String token) throws RemoteException {
            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
            try {
                JSONObject data = new JSONObject(msg).optJSONObject("data");
                if (data.optString("command").equals("question")) {//回答问题的
                    ((BaseActivity) currentActivity).onQuestion(data);

                    //zbusTeacherTopic = data.optString("userId");
                } else if (data.optString("command").equals("sign")) {//签到
                    ((BaseActivity) currentActivity).onSign();
                    //zbusTeacherTopic = data.optString("userId");
                } else if (data.optString("command").equals("responsePush")) {//知识点统计
                    ((BaseActivity) currentActivity).onResponsePush();
                    //zbusTeacherTopic = data.optString("userId");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void attendClass(String msg) throws RemoteException {

        }
    };
}
