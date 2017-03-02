package com.zk.myweex.mqttclient;


import com.zk.myweex.mqttclient.mq.PushInterface;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MqttUtils {

    private static MqttUtils instance;

    public static MqttUtils getInstance() {
        if (instance == null) {
            instance = new MqttUtils();
        }
        return instance;
    }

    private MqttUtils() {

    }

    /**
     * 获取加群
     *
     * @param classId
     * @param className
     */
    public static void getClassInfo(String classId, String className) {
        try {

            PushInterface client = MqttInstance.getInstance().getPushInterface();
            if (client != null) {
                String json = client.addClassGroup(classId, className);
//                MainListDao.saveGroupList(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除班级群
     *
     * @param classId
     */
    public static void deleteClassGroup(String classId) {
        if (classId != null) {
//            MainListDao.deleteClassGroup(classId);
        }
    }

}
