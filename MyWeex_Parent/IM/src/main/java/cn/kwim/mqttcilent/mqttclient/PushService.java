package cn.kwim.mqttcilent.mqttclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Map;

//import cn.kiway.login.LoadingActivity;
import cn.kiway.utils.SharedPreferencesUtil;
import cn.kwim.mqttcilent.LoginActivity;
import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.dao.Dao;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.mqttclient.mq.HproseMqttClient;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import cn.kwim.mqttcilent.mqttclient.mq.TopicProcessService;

public class PushService extends Service {
    public PushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //启动服务执行此方法
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        final String name = intent.getStringExtra(LoginActivity.USERNAME);
        final String pwd = intent.getStringExtra(LoginActivity.PWD);
        new Thread() {
            @Override
            public void run() {
                MqttInstance.getInstance().conMqtt(name, pwd, new MqttInstance.LoginImlisener() {
                    @Override
                    public void isLogin() {
                        Log.d("test", "isLogin");
                        return;
                    }
                });
                HproseMqttClient client = MqttInstance.getInstance().getHproseMqttClient();
                if (client == null) {
                    //登录失败
                    Log.d("test", "登录失败");
                } else {
                    Log.d("test", "登录成功");
                    try {
                        PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
                        //登陆成功后必须先执行此方法
                        getUserInfo(pushInterface);
                        //同步数据，根据幼教园丁
                        //TODO 做数据同处理：幼教园丁
                        if (pushInterface != null) {
                            MainListDao.saveGroupList(pushInterface.getGroupList(), DaoType.SESSTIONTYPE.GROUP);
                            client.register(RegisterType.MESSAGE, new TopicProcessService() {
                                @Override
                                public void process(String topic, MqttMessage message, String time) {
                                    Log.d("test", "process1 = " + message);
                                    MessageDao.saveRecMessage(message.toString());
                                    Map map = new Gson().fromJson(message.toString(), Map.class);
                                    String id = map.get("recvid").toString().replace(".0", "");
                                    String sendtype = map.get("sendtype").toString();
                                    String content = map.get("msg").toString();
                                    String type = map.get("type").toString();
                                    String name = map.get("formnick").toString();
                                    int num = MessageDao.unreadCount(id, sendtype);
                                    MainListDao.updateGroupList(num + "", Dao.getKey(id), content, type, name);
                                }
                            });
                            //监听撤回消息
                            client.register(RegisterType.RECALLMESSAGE, new TopicProcessService() {
                                @Override
                                public void process(String topic, MqttMessage message, String time) {
                                    Log.d("test", "process2 = " + message);
                                    Map map = new Gson().fromJson(message.toString(), Map.class);
                                    String msgId = map.get("msgid").toString().replace(".0", "");
                                    MessageDao.recallMsg(msgId);
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        LoginActivity.instance.IntentActivty();
                    }

                }


            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    //个人信息添加
    private void getUserInfo(PushInterface pushInterface) {

        try {
            String userInfo = pushInterface.getUserInfo();
            Log.i("个人信息", userInfo + "sssssssss");
            Converse converse = new Gson().fromJson(userInfo, Converse.class);
            if (converse.getStatusCode().equals("200")) {
                Map map = (Map) converse.getData();
                Global.getInstance().setLogo(map.get("logo").toString());
                Global.getInstance().setUserId(map.get("uid").toString().replace(".0", ""));
                Global.getInstance().setGender(map.get("gender").toString());
                Global.getInstance().setNickName(map.get("nickname").toString());
                SharedPreferencesUtil.save(this, Global.GL_NICKNAME,// 保存用户名与密码
                        map.get("nickname").toString());
                SharedPreferencesUtil.save(this, Global.GL_LOGO,
                        map.get("logo").toString());
                SharedPreferencesUtil.save(this, Global.GL_GENDER,
                        map.get("gender").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    interface RegisterType {
        String MESSAGE = "message";
        String RECALLMESSAGE = "recallMessage";
    }

    interface CallBackListener {
        void callBack();
    }
}
