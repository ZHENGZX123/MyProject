package cn.kwim.mqttcilent.common.cache.dao;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 2017/1/9.
 */

public class MainListDao extends Dao {

    /**
     * 保存群
     *
     * @param json 服务端传入json数据
     */
    public static void saveGroupList(String json, String sendType) {
        if (json == null || json.equals("")) {
            return;
        }
        Realm realm = getRealm();
        realm.beginTransaction();
        Converse converse = new Gson().fromJson(json, Converse.class);
        if (converse == null) {
            return;
        }
        if (converse.getStatusCode().equals("200")) {
            List<Object> list = (List) converse.getData();
            for (int i = 0; i < list.size(); i++) {
                MainList mainList = new MainList();
                Map<String, Object> map = (Map) list.get(i);
                mainList.setKey(getKey(map.get("ug_id").toString()));
                mainList.setUserId(Global.getInstance().getUserId() + "");
                mainList.setId(map.get("ug_id").toString().replace(".0", ""));
                mainList.setName(map.get("ug_name").toString());
                mainList.setMsg(map.get("ug_notice").toString());
                mainList.setLogo(map.get("ug_icon").toString());
                mainList.setUg_type(map.get("ug_type").toString());
                if (map.get("ug_classid") != null) {
                    mainList.setClassId(map.get("ug_classid").toString().replace(".0", ""));
                } else {
                    mainList.setClassId("-1");
                }
                mainList.setSendName(" ");
                mainList.setNumber("0");
                mainList.setTime(System.currentTimeMillis() + "");
                mainList.setSendType(sendType);
                mainList.setMsgType(DaoType.TYPY.TEXT);
                realm.copyToRealmOrUpdate(mainList);
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 保存一个班级群
     */
    public static void saveGroupList(String json) {
        Log.i("123456", json);
        MainList mainList = new MainList();
        Converse converse = new Gson().fromJson(json, Converse.class);
        if (converse.getStatusCode().equals("200")) {
            Map<String, Object> map = (Map) converse.getData();
            mainList.setKey(getKey(map.get("groupid").toString()));
            mainList.setUserId(Global.getInstance().getUserId() + "");
            mainList.setId(map.get("groupid").toString().replace(".0", ""));
            mainList.setName(map.get("groupname").toString());
            if (map.get("ug_classid") != null) {
                mainList.setClassId(map.get("ug_classid").toString().replace(".0", ""));
            } else {
                mainList.setClassId("-1");
            }
            mainList.setUg_type("1");
            mainList.setMsg("");
            mainList.setLogo("");
            mainList.setSendName("");
            mainList.setNumber("0");
            mainList.setTime(System.currentTimeMillis() + "");
            mainList.setSendType("1");
            mainList.setMsgType(DaoType.TYPY.TEXT);
            Realm realm = getRealm();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(mainList);
            realm.commitTransaction();
            realm.close();
        }
    }

    /**
     * 获得群
     */
    public static RealmResults<MainList> getMainList() {
        String userId = Global.getInstance()
                .getUserId();
        if (userId == null) {
            return null;
        }
        try {
            Realm realm = getRealm();
            RealmResults<MainList> results = realm.where(MainList.class).equalTo("userId", userId)
                    .findAll().sort("time", Sort.DESCENDING);
            Log.d("test", results.toString());
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得群
     */
    public static RealmResults<MainList> getMainZeroList() {
        try {
            Realm realm = getRealm();
            RealmResults<MainList> results = realm.where(MainList.class).equalTo("userId", "-178")
                    .findAll();
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        // Log.i("获得群列表", results.toString());
    }

    /**
     * 获得班级群列表
     */
    public static RealmResults<MainList> getClassGroup() {
        Realm realm = getRealm();
        RealmResults<MainList> results = realm.where(MainList.class).equalTo("userId", Global.getInstance().getUserId
                ()).equalTo("ug_type", "1")
                .findAll();
        return results;
    }

    /**
     * 更新群内容和未读消息的数量
     *
     * @param number
     * @param key
     * @param content
     */
    public static void updateGroupList(String number, String key, String content, String msgType, String name) {
        Realm realm = getRealm();
        MainList groupList = realm.where(MainList.class).equalTo("key", key).findFirst();
        realm.beginTransaction();
        groupList.setNumber(number);
        groupList.setMsg(content);
        groupList.setMsgType(msgType);
        groupList.setSendName(name);
        groupList.setTime(System.currentTimeMillis() + "");
        realm.copyToRealmOrUpdate(groupList);
        realm.commitTransaction();
    }

    /**
     * 更新群内容和未读消息的数量 HomeSchoolFragment.onStart
     *
     * @param number
     * @param key
     * @param content
     */
    public static void updateGroupListChat(String number, String key, String content, String msgType, String name) {
        Realm realm = getRealm();
        MainList groupList = realm.where(MainList.class).equalTo("key", key).findFirst();
        realm.beginTransaction();
        groupList.setNumber(number);
        groupList.setMsg(content);
        groupList.setMsgType(msgType);
        groupList.setSendName(name);
        realm.copyToRealmOrUpdate(groupList);
        realm.commitTransaction();
    }


    /**
     * 获取群名称
     */
    public static String getTitleName(String id, String sendType) {
        Realm realm = getRealm();
        MainList groupList = realm.where(MainList.class).equalTo("Id", id).equalTo("sendType", sendType)
                .equalTo("userId", Global.getInstance().getUserId()).findFirst();
        return groupList.getName();

    }

    /**
     * 删除班级群
     */
    public static void deleteClassGroup(String classId) {
        try {
            Realm realm = getRealm();
            final RealmResults<MainList> result = realm.where(MainList.class).equalTo("classId", classId).equalTo("userId", Global.getInstance().getUserId() + "").findAll();
            // final RealmResults<User> users = getUsers();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    result.deleteFromRealm(0); //删除班级群
                }
            });
        } catch (Exception e) {
        }
    }

    /**
     * 删除班级群
     */
    public static void deleteGroup(String groupId) {
        try {
            Realm realm = getRealm();
            final RealmResults<MainList> result = realm.where(MainList.class).equalTo("Id", groupId).findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    result.deleteFromRealm(0); //删除讨论组
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
