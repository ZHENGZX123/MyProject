package com.zk.testmqtt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
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


        clickStart(null);
    }


    public void clickStart(View view) {
        new Thread() {
            @Override
            public void run() {
                MqttInstance.getInstance().conMqtt("18626318013", "123456", new MqttInstance.LoginImlisener() {
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

                        //2.获取群组信息（普通群+班级群） 253=测试
                        String grouplist = pushInterface.getGroupList();
                        JSONArray groups = new JSONObject(grouplist).getJSONArray("data");
                        Log.d("test", "group count = " + groups.length());
                        Log.d("test", "grouplist = " + grouplist);

                        for (int i = 0; i < groups.length(); i++) {
                            JSONObject g = groups.getJSONObject(i);
                            Log.d("test", "groupname = " + g.getString("ug_name") + " , groupid = " + g.getString("ug_id")
                                    + " , grouptype = " + g.getString("ug_type") + " , ispublic = " + g.getString("ug_ispublic")
                                    + " , isvalidate = " + g.get("ug_isvalidate"));
                        }

                        //2班级群组的信息 ,从班级群可以创建讨论组，也可以从好友列表创建讨论组
                        String getClassGroupList = pushInterface.getClassGroupList();
                        Log.d("test", "getClassGroupList = " + getClassGroupList);

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

                        //5. 根据群id获取群信息，其实和2一样
//                        String groupinfo = pushInterface.getGroupInfo("253");
//                        Log.d("test", "group 253 info = " + groupinfo);

                        //6. 获取群成员
                        String menberlist = pushInterface.groupMemberList("253");
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

                        //10. 别人加我为好友!  我加别人的呢？好像没有这个接口
                        String getAddFriendApply = pushInterface.getAddFriendApply();
                        Log.d("test", "getAddFriendApply = " + getAddFriendApply);


                        //11. 根据群名字，模糊查群。  包括班级群和讨论组。
                        //讨论组ug_classid=0 ，
                        String searchGroup = pushInterface.searchGroup("谷歌");//11
                        Log.d("test", "searchGroup = " + searchGroup);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //群聊
    public void sendMessageIngroup(View view) {
        // 253 测试
        String key = MqttInstance.getInstance().getPushInterface().sendMessage("253", "{\"msg\":\"" +
                "hello" + "\",\"type\":\"text\"}", "2");
        Log.d("test", "key = " + key);
    }

    //未读消息，是指离线后未收到的消息。并不包括 在线但尚未阅读的消息
    public void getUnReadMsg(View view) {
        //参数是什么意思
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
        String groupinfo = "{'name':'testtest','icon':'','notice':'','intro':'','type':'0','ispublic':'1','isvalidate':'0','maxnum':'1000'}";
        String friendlist = "[{'uid':'be8b70b0142e11e78969177444a6fd3d','nickname':'郑康'}]";
        Log.d("test", "creatGroup = " + pushInterface.creatGroup(groupinfo, friendlist));
    }


    public void updateGroupData(View view) {
        //type可以改吗
        JSONObject data = new JSONObject();
        try {
            data.put("groupid", "253");//testtest
            data.put("name", "test_test");
            data.put("icon", "");
            data.put("notice", "");
            data.put("intro", "");
            data.put("ispublic", "1");
            data.put("isvalidate", "0");
            data.put("maxnum", "1000");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("test", "updateGroupData = " + MqttInstance.getInstance().getPushInterface().updateGroupData(data.toString()));
    }

    // 13510530146 = 38bc2850092311e7bbb321aab2798d9f
    // 18626318013 = be8b70b0142e11e78969177444a6fd3d
    public void addFriend(View view) {
        String addFriend = pushInterface.addFriend("be8b70b0142e11e78969177444a6fd3d", "你好，我想和你做朋友");
        Log.d("test", "addFriend = " + addFriend);
    }

    public void confirmAddFriend(View view) {
        //等getAddFriendApply的bug搞定了再说
        Log.d("test", "confirmAddFriend = " + pushInterface.confirmAddFriend("id", "1"));
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