package cn.kwim.mqttcilent.mqttclient;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import cn.kwim.mqttcilent.mqttclient.mq.HproseMqttClient;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import cn.kwim.mqttcilent.mqttclient.mq.TopicProcessService;

/**
 * Created by hmg on 2017/1/6.
 * Mqtt 初始化连接
 */
public class MqttInstance {

    private static MqttInstance instance;
//    private boolean type = true;

    private HproseMqttClient client;

    public static MqttInstance getInstance() {
        if (instance == null) {
            instance = new MqttInstance();
        }
        return instance;
    }

    //如果登陆不成功 请再次登陆
    public void conMqtt(String name, String pwd, final LoginImlisener loginImlisener) {
        try {
//            type = true;
            client = new HproseMqttClient("yjpt", name, pwd, "2",
                    new TopicProcessService() {
                        @Override
                        public void process(String topic, MqttMessage message, String time) {
//                            type = false;
                            loginImlisener.isLogin();
                            return;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得连接回掉
     *
     * @return 如果返回值为null
     * 则表示链接失败或则登陆失败 查看conMqtt方法{@link java.lang.reflect.Method conMqtt}
     */

    public PushInterface getPushInterface() {
        try {
            if (client.useService(PushInterface.class) == null) {
                return null;
            } else {
//                return type ? client.useService(PushInterface.class) : null;
                return client.useService(PushInterface.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获得连接
     */
    public HproseMqttClient getHproseMqttClient() {
        if (client != null) {
            return client;
        } else {
            return null;
        }
    }

    public interface LoginImlisener {
        void isLogin();
    }

}
