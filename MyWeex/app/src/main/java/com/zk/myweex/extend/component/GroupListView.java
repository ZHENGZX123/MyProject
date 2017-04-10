package com.zk.myweex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;
import com.zk.myweex.utils.ScreenUtil;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Map;

import cn.kiway.utils.SharedPreferencesUtil;
import cn.kwim.mqttcilent.app_ui.fragment.HomeSchoolAdapter;
import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.dao.Dao;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.mq.HproseMqttClient;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import cn.kwim.mqttcilent.mqttclient.mq.TopicProcessService;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class GroupListView extends WXComponent<ListView> {

    private ListView lv;
    private HomeSchoolAdapter adapter;

    public GroupListView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ListView initComponentHostView(@NonNull Context context) {
        Log.d("mqtt", "ListView initComponentHostView");
        this.lv = new ListView(context);
        this.lv.setBackgroundColor(Color.WHITE);
        this.lv.setFocusable(false);
        this.lv.setFocusableInTouchMode(false);
        this.adapter = new HomeSchoolAdapter(getContext());
        this.lv.setAdapter(this.adapter);

        initData();
        addListener();
        return this.lv;
    }

    private void addListener() {
        Realm realm = Realm.getDefaultInstance();
        RealmChangeListener<Realm> realmListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                refreshUI();
            }
        };
        realm.addChangeListener(realmListener);
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {

                refreshUI();

                String userName = getContext().getSharedPreferences("kiway", 0).getString("userName", "");
                String userPwd = getContext().getSharedPreferences("kiway", 0).getString("userPwd", "");
                MqttInstance.getInstance().conMqtt(userName, userPwd, new MqttInstance.LoginImlisener() {
                    @Override
                    public void isLogin() {
                        Log.d("mqtt", "isLogin");
                        return;
                    }
                });
                HproseMqttClient client = MqttInstance.getInstance().getHproseMqttClient();
                if (!MqttInstance.getInstance().getType()) {
                    //登录失败
                    Log.d("mqtt", "登录失败");
                } else {
                    Log.d("mqtt", "登录成功");
                    try {
                        PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
                        if (pushInterface != null) {
                            //1.userinfo
                            getUserInfo(pushInterface.getUserInfo());
                            //2.grouplist
                            getGroupInfo(pushInterface.getGroupList());
                            refreshUI();

                            //3.注册回调
                            client.register(RegisterType.MESSAGE, new TopicProcessService() {
                                @Override
                                public void process(String topic, MqttMessage message, String time) {
                                    Log.d("mqtt", "process1 message =" + message);//接到聊天消息。。。。
                                    MessageDao.saveRecMessage(message.toString());
                                    Map map = new Gson().fromJson(message.toString(), Map.class);
                                    String id = map.get("recvid").toString().replace(".0", "");
                                    String sendtype = map.get("sendtype").toString();
                                    String content = map.get("msg").toString();
                                    String type = map.get("type").toString();
                                    String name = map.get("formnick").toString();
                                    int num = MessageDao.unreadCount(id, sendtype);
                                    MainListDao.updateGroupList(num + "", Dao.getKey(id), content, type, name);

                                    refreshUI();
                                }
                            });
                            //监听撤回消息
                            client.register(RegisterType.RECALLMESSAGE, new TopicProcessService() {
                                @Override
                                public void process(String topic, MqttMessage message, String time) {
                                    Log.d("mqtt", "process2 message =" + message);
                                    Map map = new Gson().fromJson(message.toString(), Map.class);
                                    String msgId = map.get("msgid").toString().replace(".0", "");
                                    MessageDao.recallMsg(msgId);
                                }
                            });
                        } else {
                            Log.d("mqtt", "pushInterface null");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        }.start();
    }

    private void getGroupInfo(String groupList) {
        Log.d("mqtt", "groupList = " + groupList);
        MainListDao.saveGroupList(groupList, DaoType.SESSTIONTYPE.GROUP);
    }

    private void refreshUI() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取到数据之后，刷新列表
                try {
                    Global.getInstance().setUserId(SharedPreferencesUtil.getString(getContext(), Global.GL_UID));

                    RealmResults<MainList> list = MainListDao.getMainList();
                    if (list != null) {
                        adapter.setList(list);
                        int width = ScreenUtil.getDisplayWidth((AppCompatActivity) getContext());
                        int height = ScreenUtil.getDisplayHeight((AppCompatActivity) getContext()) - getComponentSize().top;
                        GroupListView.this.setHostLayoutParams(getHostView(), width, height, 0, 0, 0, 0);
                    } else {
                        Log.d("mqtt", "list is null");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface RegisterType {
        String MESSAGE = "message";
        String RECALLMESSAGE = "recallMessage";
    }


    private void getUserInfo(String userInfo) {
        try {
            Log.d("mqtt", "userInfo = " + userInfo);
            if (userInfo == null || userInfo.equals("")) {
                return;
            }
            Converse converse = new Gson().fromJson(userInfo, Converse.class);
            if (converse.getStatusCode().equals("200")) {
                Map map = (Map) converse.getData();
                Global.getInstance().setLogo(map.get("logo").toString());
                Global.getInstance().setUserId(map.get("uid").toString().replace(".0", ""));
                Global.getInstance().setGender(map.get("gender").toString());
                Global.getInstance().setNickName(map.get("nickname").toString());

                SharedPreferencesUtil.save(getContext(), Global.GL_NICKNAME,// 保存用户名与密码
                        map.get("nickname").toString());
                SharedPreferencesUtil.save(getContext(), Global.GL_LOGO,
                        map.get("logo").toString());
                SharedPreferencesUtil.save(getContext(), Global.GL_GENDER,
                        map.get("gender").toString());
                SharedPreferencesUtil.save(getContext(), Global.GL_UID,// 保存用户名与密码
                        map.get("uid").toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
