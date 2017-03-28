package cn.kwim.mqttcilent.common.cache.dao;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/1/10.
 */

public class GroupMemberListDao extends Dao {

    public static void saveGroupListMember(String json, String groupId) {
        Log.i("保存群成员", json);
        Converse converse = new Gson().fromJson(json, Converse.class);
        Realm realm = getRealm();
        realm.beginTransaction();
        if (converse.getStatusCode().equals("200")) {
            List list = (List) converse.getData();
            if (list == null) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                GroupListMember groupListMember = new GroupListMember();
                Map map = (Map) list.get(i);
                String uid = "";
                try {
                    if (map.get("id") == null) {
                        uid = map.get("uid").toString().replace(".0", "");
                        if (map.get("nickname") != null)
                            groupListMember.setName(map.get("nickname").toString());
                        else{
                            if (map.get("name") != null)
                                groupListMember.setName(map.get("name").toString());
                            else
                                groupListMember.setName("匿名");
                        }
                    } else {
                        uid = map.get("id").toString().replace(".0", "");
                        if (map.get("name") != null)
                            groupListMember.setName(map.get("name").toString());
                        else{
                            if (map.get("nickname") != null)
                                groupListMember.setName(map.get("nickname").toString());
                            else
                                groupListMember.setName("匿名");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                groupListMember.setKey(getKey(uid));
                groupListMember.setUserId(uid);
                groupListMember.setGroupId(groupId);
                if (map.get("logo") != null)
                    groupListMember.setHeader(map.get("logo").toString());
                else
                    groupListMember.setHeader("");
                groupListMember.setId(Global.getInstance().getUserId());
                groupListMember.setSortLetters("a");
                realm.copyToRealmOrUpdate(groupListMember);
            }
        }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 获取群成员
     *
     * @param groupId
     * @return
     */
    public static List<GroupListMember> getGroupListMember(String groupId) {
        List<GroupListMember> list = new ArrayList<>();
        Realm realm = getRealm();
        RealmResults<GroupListMember> results = realm.where(GroupListMember.class).equalTo("groupId", groupId)
                .equalTo("id", Global.getInstance().getUserId())
                .findAll();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i));
        }
        return list;
    }

    /**
     * 修改首字母
     *
     * @param key    唯一Id
     * @param letter 首字母
     */
    public static void updateGrouplistMember(String key, String letter) {
        Realm realm = getRealm();
        GroupListMember results = realm.where(GroupListMember.class).equalTo("key", key)
                .findFirst();
        realm.beginTransaction();
        results.setSortLetters(letter);
        realm.copyToRealmOrUpdate(results);
        realm.commitTransaction();
        realm.close();
    }
}
