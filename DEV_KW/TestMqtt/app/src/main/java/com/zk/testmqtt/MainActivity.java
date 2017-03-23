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

    private HproseMqttClient client;
    private PushInterface pushInterface;

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
                client = MqttInstance.getInstance().getHproseMqttClient();
                if (!MqttInstance.getInstance().getType()) {
                    //登录失败
                    Log.d("test", "登录失败");
                } else {
                    Log.d("test", "登录成功");
                    pushInterface = MqttInstance.getInstance().getPushInterface();
                    if (pushInterface != null) {
                        //1.获取个人信息
                        String userInfo = pushInterface.getUserInfo();
                        Log.d("test", "userInfo = " + userInfo);

                        //2.获取群组信息 ug_id=108=开维小一1班
                        String grouplist = pushInterface.getGroupList();
                        Log.d("test", "grouplist = " + grouplist);

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

                        //5. 获取群信息。。。其实和2是一样的
                        String groupinfo = pushInterface.getGroupInfo("108");
                        Log.d("test", "group 108 info = " + groupinfo);

                        //6. 获取群成员
                        String menberlist = pushInterface.groupMemberList("108");
                        Log.d("test", "menberlist = " + menberlist);


                        //7. 获取自己的好友列表
                        String friendlist = pushInterface.getFriendList();
                        Log.d("test", "friendlist = " + friendlist);

                        //8.根据uid获取具体用户。参数是uid。有些id是11，有些id是长串
                        String friendinfo = pushInterface.getFriendInfo("a5a7c79009e011e7a51073e14c3043f0");
                        Log.d("test", "friendinfo = " + friendinfo);

                        //9. 模糊查用户,参数是nickname。返回是nickname包含"敏"字的人
                        String searchFriend = pushInterface.searchFriend("敏");
                        Log.d("test", "searchFriend = " + searchFriend);

                    }
                }
            }
        }.start();
    }

    public void clickAddFriend(View view) {
        pushInterface.addFriend("id", "你好，我想和你做朋友");
    }
}