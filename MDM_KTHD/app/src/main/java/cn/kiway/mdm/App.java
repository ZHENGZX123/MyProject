package cn.kiway.mdm;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

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
    public static final String serverUrl = "http://192.168.8.161:8083/";
    public static final String clientUrl = "http://192.168.8.161:8085/";

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
    public ClientCallback.Stub mClientCallback = new ClientCallback.Stub() {
        @Override
        public void goOutClass() throws RemoteException {
            //下课
            mHandler.removeMessages(MSG_TIME_OUT);
            mHandler.sendEmptyMessage(MSG_HOME_TURE);
        }

        @Override
        public void accpterMessage(String msg, String token) throws RemoteException {
            Log.d("test", "accpterMessage msg = " + msg + "");
            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
            try {
                JSONObject data = new JSONObject(msg);
                String command = data.optString("command");
                if (command.equals("question")) {//回答问题的
                    ((BaseActivity) currentActivity).onQuestion(data);
                } else if (command.equals("sign")) {//签到
                    ((BaseActivity) currentActivity).onSign();
                } else if (command.equals("tongji")) {//知识点统计
                    String knowledge = data.optString("knowledge");
                    ((BaseActivity) currentActivity).onTongji(knowledge);
                } else if (command.equals("qiangda")) {
                    ((BaseActivity) currentActivity).onQiangda();
                } else if (command.equals("qiangdaResult")) {
                    int result = data.optInt("result");
                    String qiangdaStudentName = data.optString("qiangdaStudentName");
                    ((BaseActivity) currentActivity).onQiangdaResult(result, qiangdaStudentName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void attendClass(String msg) throws RemoteException {
            //上课
        }
    };
}
