package com.zk.myweex.activity;

import android.view.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;

import cn.kwim.mqttcilent.mqttclient.MqttInstance;

//4个tab用的
public class SubActivity extends WXBaseActivity {

    private boolean mCanQuit;
    private Timer mTimer;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCanQuit) {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            MqttInstance.getInstance().getPushInterface().logout();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                finish();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }.start();
            } else {
                toast("再按一次退出");
                mCanQuit = true;
                if (mTimer == null) {
                    mTimer = new Timer();
                }
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mCanQuit = false;
                    }
                }, 2000);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
