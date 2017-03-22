package cn.kwim.mqttcilent.mqttclient;

import com.google.gson.Gson;

import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;

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
        PushInterface client = MqttInstance.getInstance().getPushInterface();
        if (client != null) {
            String json = client.addClassGroup(classId, className);
            Converse converse = new Gson().fromJson(json, Converse.class);
            if (converse.getStatusCode().equals("200")) {
                String jsonGroup = client.getGroupList();
                MainListDao.saveGroupList(jsonGroup, DaoType.SESSTIONTYPE.GROUP);
            }
        }
    }

    /**
     * 删除班级群
     *
     * @param classId
     */
    public static void deleteClassGroup(String classId) {
        if (classId != null) {
            MainListDao.deleteClassGroup(classId);
        }
    }
    /**
     * 删除班级群
     *
     * @param groupId
     */
    public static void deleteGroup(String groupId) {
        if (groupId != null) {
            MainListDao.deleteGroup(groupId);
        }
    }
    public static void logout() {
        MqttInstance.getInstance().getPushInterface().logout();
    }
}
