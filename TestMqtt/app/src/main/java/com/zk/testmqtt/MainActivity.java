package com.zk.testmqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqttclient.MqttInstance;
import mqttclient.mq.Conf;
import mqttclient.mq.HproseMqttClient;
import mqttclient.mq.PushInterface;
import mqttclient.mq.TopicProcessService;


public class MainActivity extends AppCompatActivity {

    public interface RegisterType {
        String MESSAGE = "message";
        String RECALLMESSAGE = "recallMessage";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化mqtt
        Conf.getInstance().init(this);
        //mqttSdk 初始化
        MqttInstance.init(this);
    }


    public void clickStart(View view) {
        new Thread() {
            @Override
            public void run() {
                MqttInstance.getInstance().conMqtt("13510530146", "123456", new MqttInstance.LoginImlisener() {
                    @Override
                    public void isLogin() {
                        Log.d("test", "isLogin");
                        return;
                    }
                });
                HproseMqttClient client = MqttInstance.getInstance().getHproseMqttClient();
                if (!MqttInstance.getInstance().getType()) {
                    //登录失败
                    Log.d("test", "登录失败");
                } else {
                    Log.d("test", "登录成功");
                    PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
                    if (pushInterface != null) {
                        //1.获取个人信息
                        String userInfo = pushInterface.getUserInfo();
                        Log.d("test", "userInfo = " + userInfo);

                        //2.获取群组信息
                        String groupInfo = pushInterface.getGroupList();
                        Log.d("test", "groupInfo = " + groupInfo);

                        //3.获取实时聊天信息
                        client.register(RegisterType.MESSAGE, new TopicProcessService() {
                            @Override
                            public void process(String topic, MqttMessage message, String time) {
                                Log.d("test", "process1 message =" + message);//接到聊天消息。。。。

                            }
                        });
                        //4.监听撤回消息
                        client.register(RegisterType.RECALLMESSAGE, new TopicProcessService() {
                            @Override
                            public void process(String topic, MqttMessage message, String time) {
                                Log.d("test", "process2 message =" + message);
                            }
                        });
                    }
                }
            }
        }.start();
    }
}