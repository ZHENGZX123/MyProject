package mqttclient;

import android.content.Context;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import mqttclient.mq.HproseMqttClient;
import mqttclient.mq.PushInterface;
import mqttclient.mq.TopicProcessService;


/**
 * Created by hmg on 2017/1/6.
 * Mqtt 初始化连接
 */
public class MqttInstance {
    private static Context contexts;
    private static MqttInstance instance;
    private boolean type = true;

    private HproseMqttClient client;

    public static void init(Context context) {
        contexts = context;
    }

    public static MqttInstance getInstance() {
        if (instance == null) {
            instance = new MqttInstance();
        }
        return instance;
    }


    public boolean getType() {
        return type;
    }

    private MqttInstance() {
        
    }

    //如果登陆不成功 请再次登陆
    public void conMqtt(String name, String pwd, final LoginImlisener loginImlisener) {
        try {
            type = true;
            client = new HproseMqttClient("yjpt", name, pwd, "1",
                    new TopicProcessService() {
                        @Override
                        public void process(String topic, MqttMessage message, String time) {
                            type = false;
                            System.out.println("登录失败111111111");
                            System.out.println(topic);
                            System.out.println(time);
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
                return type ? client.useService(PushInterface.class) : null;
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
