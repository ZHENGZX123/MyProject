package cn.kwim.mqttcilent.common.cache.dao;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.FriendList;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hmg on 2017/1/5.、
 * <p>
 * 处理好友列表
 */

public class FriendListdao extends Dao {

    /**
     * 保存好友信息
     */
    public static void saveFriendList(String json) {
        Converse converse = new Gson().fromJson(json, Converse.class);
        if (converse.getStatusCode().equals("200")) {
            System.out.println("获得好友信息" + converse.getData());
            List list = (List) converse.getData();
            Realm realm = getRealm();
            realm.beginTransaction();
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                FriendList friendList = new FriendList();
                String friendid = map.get("friendid").toString().replace(".0","");
                friendList.setFriendid(friendid);
                friendList.setFlags(""+map.get("flags"));
                friendList.setFriendnick(""+map.get("friendnick"));
                friendList.setKey(""+Dao.getKey(friendid));
                friendList.setLogo(""+map.get("im_logo"));
                friendList.setRemark(""+map.get("remark"));
                friendList.setSignature(""+map.get("signature"));
                realm.copyToRealmOrUpdate(friendList);

            }
            realm.commitTransaction();
            realm.close();
        }
    }
    /**
     * 获取好友信息
     */
    public static RealmResults<FriendList> getFriendList() {

        Realm realm = getRealm();
        RealmResults<FriendList> results = realm.where(FriendList.class)
                .findAll();
        Log.i("获取好友信息", results.toString());
        return results;
    }
}
