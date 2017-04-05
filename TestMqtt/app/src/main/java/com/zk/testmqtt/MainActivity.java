package com.zk.testmqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

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
                    public void LoginFailure() {
                        Log.d("test", "登陆失败回调。。。");
                        return;
                    }
                });
                client = MqttInstance.getInstance().getHproseMqttClient();
                if (!MqttInstance.getInstance().isConnected()) {
                    //登录失败
                    Log.d("test", "登录失败");
                } else {
                    Log.d("test", "登录成功");
                    pushInterface = MqttInstance.getInstance().getPushInterface();
                    try {
                        if (pushInterface == null) {
                            Log.d("test", "rpc error");
                        }

                        //1.获取个人信息
                        String userInfo = pushInterface.getUserInfo();
                        Log.d("test", "userInfo = " + userInfo);

                        //2.获取群组信息 252 测试
                        String grouplist = pushInterface.getGroupList();
                        JSONArray groups = new JSONObject(grouplist).getJSONArray("data");
                        Log.d("test", "group count = " + groups.length());
                        Log.d("test", "grouplist = " + grouplist);

                        for (int i = 0; i < groups.length(); i++) {
                            Log.d("test", "groupname = " + groups.getJSONObject(i).getString("ug_name") + " , groupid = " + groups.getJSONObject(i).getString("ug_id"));
                        }

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
//                        String groupinfo = pushInterface.getGroupInfo("252");
//                        Log.d("test", "group 252 info = " + groupinfo);

                        //6. 获取群成员
                        String menberlist = pushInterface.groupMemberList("252");
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


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //群聊
    public void sendMessageIngroup(View view) {
        // 252 测试
        String key = MqttInstance.getInstance().getPushInterface().sendMessage("252", "{\"msg\":\"" +
                "hello" + "\",\"type\":\"text\"}", "2");
        Log.d("test", "key = " + key);
    }

    //未读消息，是指离线后未收到的消息。并不包括 在线但尚未阅读的消息
    public void getUnReadMsg(View view) {
        Log.d("test", "getUnReadMsg = " + pushInterface.getUnReadMsg("0", null));
    }


    //不是群主的话能不能解散群
    public void dissolutionGroup(View view) {
        Log.d("test", "dissolutionGroup " + pushInterface.dissolutionGroup("124"));
    }

    public void delGroup(View view) {
        Log.d("test", "delGroup " + pushInterface.delGroup("124"));
    }

    public void creatGroup(View view) {
        toast("还没做");
//        String names = "{'name':'testtest','icon':'','notice':'','intro':'','type':'','ispublic':'','isvalidate':'','maxnum':'1000'}";
//        pushInterface.creatGroup(names, list);
    }

    public void addFriend(View view) {
        toast("还没做");
//        pushInterface.addFriend("id", "你好，我想和你做朋友");
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pushInterface != null) {
            pushInterface.logout();
        }
    }
}