package com.zk.myweex.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zk.myweex.R;
import com.zk.myweex.mqttclient.MqttInstance;
import com.zk.myweex.mqttclient.mq.HproseMqttClient;


public class BootActivity extends Activity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_base);


        MqttInstance.getInstance().conMqtt("13510530146", "123456", null);

        HproseMqttClient client = MqttInstance.getInstance().getHproseMqttClient();
        if (client == null) {
            Log.i("test", "服务器异常");
            return;
        }
        if (!MqttInstance.getInstance().getType()) {
            //登录失败
            Log.i("test", "登录失败");
        } else {
            //登录成功
            Log.d("test", "登录成功");
            //userName
            //password
//            userType
//            PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
            //登陆成功后必须先执行此方法,获取个人信息
//            getUserInfo(pushInterface);
//            cn.kwim.mqttcilent.common.Global.getInstance().getUserId()

            String token = MqttInstance.getInstance().getHproseMqttClient().getToken();
            Log.d("test", "token = " + token);
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(BootActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }
}
