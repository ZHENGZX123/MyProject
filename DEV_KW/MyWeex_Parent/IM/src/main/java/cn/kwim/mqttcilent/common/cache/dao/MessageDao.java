package cn.kwim.mqttcilent.common.cache.dao;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import cn.kwim.mqttcilent.common.utils.L;
import cn.kwim.mqttcilent.common.utils.Utils;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by hmg on 2017/1/11.
 */

public class MessageDao extends Dao {

    /**
     * 处理用户实时推送的数据
     * 一般推送就一条数据
     *
     * @param json
     */
    public static void saveRecMessage(String json) {

        try {
            String sendLogo = "";
            Map map = new Gson().fromJson(json, Map.class);
            String messageId = map.get("msgid").toString().replace(".0", "");
            String messageType = map.get("type").toString();
            String msg = map.get("msg").toString();
            String sendId = map.get("formid").toString().replace(".0", "");
            String sendName = map.get("formnick").toString();
            if (null != map.get("im_logo")) {
                sendLogo = map.get("im_logo").toString();
            }
            String type = map.get("sendtype").toString();
            String id = map.get("recvid").toString().replace(".0", "");
            Message message = setMessage(messageId, DaoType.ISMY.UN_ISMY, messageType, msg
                    , sendId, sendName, sendLogo, type, id, System.currentTimeMillis() + "", DaoType.READ.UNREAD, DaoType.STATUS.SUCCESS);
            Realm realm = getRealm();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(message);
            realm.commitTransaction();
            realm.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理从服务器拉取的未读消息
     *
     * @param json 服务器拉取的未读消息
     */
    public static void saveTSUnreadMessage(String json) {
        Converse converse = new Gson().fromJson(json, Converse.class);
        if (converse.getStatusCode().equals("200")) {
            List list = (List) converse.getData();
            for (int i = 0; i < list.size(); i++) {
                String sendLogo = "";
                String sendName = "";
                Map map = (Map) list.get(i);
                String stringMsg = map.get("msg").toString().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
                Map mapMsg = new Gson().fromJson(stringMsg, Map.class);
                String messageId = map.get("id").toString().replace(".0", "");
                String messageType = mapMsg.get("type").toString();
                String msg = mapMsg.get("msg").toString();
                String sendId = map.get("fuid").toString().replace(".0", "");
                if (null != map.get("nickname")) {
                    sendName = map.get("nickname").toString();
                }
                if (null != map.get("flogo")) {
                    sendLogo = map.get("flogo").toString();
                }
                String type = map.get("sendtype").toString();
                String id = map.get("tuid").toString().replace(".0", "");
                String time = Utils.dateToLong(map.get("time").toString());
                Message message = setMessage(messageId, DaoType.ISMY.UN_ISMY, messageType, msg
                        , sendId, sendName, sendLogo, type, id, time, DaoType.READ.UNREAD, DaoType.STATUS.SUCCESS);
                Realm realm = getRealm();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(message);
                realm.commitTransaction();
                realm.close();

            }
        }

    }

    /**
     * 保存发送消息
     *
     * @param messageId
     * @param messageType
     * @param msg
     * @param sendName
     * @param type
     * @param id
     * @param time
     */
    public static Message saveSendMessage(String messageId, String messageType,
                                          String msg,
                                          String sendName, String type,
                                          String id, String time, String sendok) {
        Realm realm = getRealm();
        Message message = setMessage(messageId, DaoType.ISMY.ISMY, messageType, msg
                , Global.getInstance().getUserId(), sendName, Global.getInstance().getLogo(), type
                , id, time, DaoType.READ.ISREAD, sendok);
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(message);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;

    }

    /**
     * 构建 message表
     *
     * @param messageId   消息Id
     * @param isMy        是否是自己发送
     * @param messageType 消息类型
     * @param msg         消息
     * @param sendId      发送人Id(可以是app用户)
     * @param sendName    发送人名称 （可以是app本身用户）
     * @param sendLogo    发送人头像 (可以使app本身用户)
     * @param type        标识群还是个人好友
     * @param id          标识 如果Type 为群则是群Id type为好友则是好友ID
     * @param time        时间
     * @param readType    标记已读未读
     */
    public static Message setMessage(String messageId, String isMy, String messageType
            , String msg, String sendId, String sendName, String sendLogo, String type
            , String id, String time, String readType, String sendok) {
        Message message = new Message();
        message.setMeassgeId(messageId);
        message.setUserId(Global.getInstance().getUserId());
        message.setIsMy(isMy);
        message.setMessageType(messageType);
        message.setMsg(msg);
        message.setSendId(sendId);
        message.setSendName(sendName);
        message.setSendLogo(sendLogo);
        message.setType(type);
        message.setId(id);
        message.setTime(time);
        message.setReadType(readType);
        message.setIsSendOk(sendok);
        message.setKey(messageId);
        return message;
    }

    /**
     * 分页拉取数据
     *
     * @param count 每页消息的总数
     * @param page  第几页
     * @param id    获取的Id (用户Id，还是群Id)
     * @param type  判断群消息  还是好友消息
     * @return
     */
    public static List<Message> getLimitMessage(int count, int page, String id, String type) {
        List<Message> list = new ArrayList<>();
        Realm realm = getRealm();
        RealmResults<Message> results = realm.where(Message.class).equalTo("id", id).equalTo("userId", Global.getInstance().getUserId()).equalTo("type", type)
                .findAll();
        results = results.sort("time", Sort.DESCENDING);
        L.i("getLimitMessage", results.toString());
        int i = count * page;
        if (count * (page - 1) >= results.size()) {
            return null;
        }
        for (int j = 0; j < i; j++) {
            if (j < results.size()) {
                list.add(0, results.get(j));
            } else {
                break;
            }
        }
        if (list != null && list.size() != 0) {
            return list;
        }
        return null;
    }

    /**
     * 获取最后一条数据
     */
    public static Message getLastContent(String id, String type) {
        List<Message> lst = getLimitMessage(10, 1, id, type);

        if (lst != null && lst.size() != 0) {
            Message message = lst.get(lst.size() - 1);
            Log.i("TAG获取最后一条数据", message.toString());
            return message;
        }
        return null;

    }

    /**
     * 更新未读消息
     *
     * @param id   好友Id 或者群Id
     * @param type 判断好友还是群
     */
    public static void updateUnreadMessage(String id, String type) {
        Realm realm = getRealm();
        RealmResults<Message> results = realm.where(Message.class).equalTo("id", id).equalTo("type", type).equalTo("userId", Global.getInstance().getUserId())
                .findAll();
        realm.beginTransaction();
        for (int i = 0; i < results.size(); i++) {
            Message message = results.get(i);
            message.setReadType(DaoType.READ.ISREAD);
            realm.copyToRealmOrUpdate(message);
        }
        realm.commitTransaction();
        realm.close();

    }

    /**
     * 获取某一个未读消息的总数
     *
     * @param id   群id 或者好友id
     * @param type 判断 是群还是好友
     */
    public static int unreadCount(String id, String type) {
        Realm realm = getRealm();
        RealmResults<Message> results = realm.where(Message.class).equalTo("id", id).equalTo("type", type).equalTo("userId", Global.getInstance().getUserId())
                .equalTo("readType", DaoType.READ.UNREAD)
                .findAll();
        Log.i("unreadCount", results.size() + results.toString());
        return results.size();
    }

    /**
     * 更新发送数据Id
     *
     * @param messageId 之前的id
     * @param updateId  从服务器获取的Id
     */
    public static void sendSuccess(String messageId, String updateId, String msg) {
        Realm realm = getRealm();
        Message message = realm.where(Message.class).equalTo("meassgeId", messageId).findFirst();
        realm.beginTransaction();
        message.setIsSendOk(DaoType.STATUS.SUCCESS);
        message.setKey(updateId);
        message.setMsg(msg);
        realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
        realm.close();
    }

    public static void sendFailure(String messageId, String msg) {
        Realm realm = getRealm();
        Message message = realm.where(Message.class).equalTo("meassgeId", messageId).findFirst();
        realm.beginTransaction();
        message.setIsSendOk(DaoType.STATUS.FAILURE);
        message.setMsg(msg);
        realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
        realm.close();
    }

    public static void resend(String messageId, String msg) {
        Realm realm = getRealm();
        Message message = realm.where(Message.class).equalTo("meassgeId", messageId).findFirst();
        realm.beginTransaction();
        message.setIsSendOk(DaoType.STATUS.SENDING);
        message.setMsg(msg);
        realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
        realm.close();
    }


    /**
     * 获取分类数据
     *
     * @param id
     * @param type
     * @param messageType
     */
    public static List<Message> getFLData(String id, String type, String messageType) {
        List<Message> list = new ArrayList<>();
        Realm realm = getRealm();
        RealmResults<Message> results = realm.where(Message.class).equalTo("id", id)
                .equalTo("type", type).equalTo("userId", Global.getInstance().getUserId()).equalTo("messageType", messageType).findAll();

        results = results.sort("time", Sort.DESCENDING);
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i));
        }
        return list;
    }

    /**
     * 清除聊天一条记录
     */
    public static void clearChatDataById(String key) {
        Realm realm = getRealm();
        final RealmResults<Message> results = realm.where(Message.class).equalTo("key", key).findAll();
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                results.deleteFirstFromRealm();
            }
        });
    }

    /**
     * 撤销一条消息
     */
    public static void recallMsg(String key) {
        Realm realm = getRealm();
        Message message = realm.where(Message.class).equalTo("key", key).findFirst();
        realm.beginTransaction();
        message.setIsSendOk(DaoType.STATUS.SUCCESS);
        message.setMessageType(DaoType.TYPY.RECALLMSG);
        realm.copyToRealmOrUpdate(message);
        realm.commitTransaction();
        realm.close();

    }

    /**
     * 清除某一个群或者某一个
     */
    public static void clearChatData(String id, String type) {
        Realm realm = getRealm();
        final RealmResults<Message> results = realm.where(Message.class).equalTo("id", id).equalTo("type", type)
                .equalTo("userId", Global.getInstance().getUserId()).findAll();
        realm.executeTransaction(new Realm.Transaction() {

            @Override
            public void execute(Realm realm) {
                results.deleteFirstFromRealm();
            }
        });

    }

}
