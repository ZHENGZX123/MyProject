package cn.kwim.mqttcilent.common.cache.dao;

import java.util.ArrayList;
import java.util.List;

import cn.kwim.mqttcilent.common.cache.javabean.GroupContent;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by hmg on 2017/1/9.
 */
public class GroupContentDao extends Dao {

    /**
     *  保存自己发送的数据信息
     * @param key  唯一Key
     * @param userId 当前用户Id，解耦
     * @param content 发送内容
     * @param type 内容类型
     * @param groupId 发送群Id
     */
    public static GroupContent saveSendGroupContent(String key, String userId, String content, String type, String groupId){
        GroupContent groupContent = new GroupContent();

        groupContent.setKey(key);
        groupContent.setGroupId(groupId);
        groupContent.setIsPlay(DaoType.ISPLAY.NOPLAY);
        groupContent.setStatus(DaoType.READ.ISREAD);
        groupContent.setIsMy(DaoType.ISMY.ISMY);
        groupContent.setMsg(content);
        groupContent.setSendorHeader("");
        groupContent.setSendorId(userId);
        groupContent.setSendorName("hehe");
        groupContent.setTime(System.currentTimeMillis()+"");
        groupContent.setUserId(userId);
        groupContent.setType(type);
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(groupContent);
        realm.commitTransaction();
        realm.close();

        return groupContent;
    }

    /**
     * 保存实时接收的信息(TEST)
     * @param content 从服务端获得json 字符串
     */
    public static void saveRecGroupContent(String content){
        Realm realm = getRealm();
        GroupContent groupContent = new GroupContent();
       // Converse converse = new Gson().fromJson(content,Converse.class);
        realm.beginTransaction();
        //if(converse.getStatusCode().equals("200")){
          //  Map map = (Map) converse.getData();
            groupContent.setKey("2222222"+content);
            groupContent.setGroupId("1");
            groupContent.setIsPlay(DaoType.ISPLAY.UNPLAY);
            groupContent.setStatus(DaoType.READ.UNREAD);
            groupContent.setIsMy(DaoType.ISMY.UN_ISMY);
            groupContent.setMsg(content);
            groupContent.setSendorHeader("");
            groupContent.setSendorId("");
            groupContent.setSendorName("hehe");
            groupContent.setTime(System.currentTimeMillis()+"");
            groupContent.setUserId("");
            groupContent.setType(DaoType.TYPY.TEXT);
            realm.copyToRealmOrUpdate(groupContent);

       // }
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 分页获取数据
     * @param count
     * @param page
     */
    public static List<GroupContent> gerLimitMessage(int count, int page, String groupId){
        List<GroupContent> list = new ArrayList<GroupContent>();
        Realm realm = getRealm();
        RealmResults<GroupContent> results = realm.where(GroupContent.class).equalTo("groupId",groupId)
                .findAll();

        int i = page==1?0:(page-1)*count;
        if(i<results.size()){
            for (; i < results.size(); i++) {
                list.add(results.get(i));
            }
            return list;
        }
        return null;
    }
}
