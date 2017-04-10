package cn.kwim.mqttcilent.common.cache.javabean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by hmg on 2017/1/11.
 * 处理用户消息数据表
 */
@RealmClass
public class Message extends RealmObject{
    @PrimaryKey
    private String meassgeId;    //消息ID

    @Required
    private String userId;   // 当前用户ID

    @Required
    private String isMy;   //是否是自己发送

    @Required
    private String messageType; //消息类型

    @Required
    private String msg; //消息

    @Required
    private String sendId; //发送人Id

    @Required
    private String sendName; //发送人名称

    @Required
    private String sendLogo; //发送人头像

    @Required
    private String type;  //标识群还是个人好友

    @Required
    private String id;   //标识 如果Type 为群则是群Id type为好友则是好友ID

    @Required
    private String time;  //时间

    @Required
    private String readType; // 标记已读未读

    @Required
    private String isSendOk; //标记是否发送成功

    @Required
    private String key; //消息唯一Id

    public String getMeassgeId() {
        return meassgeId;
    }

    public void setMeassgeId(String meassgeId) {
        this.meassgeId = meassgeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsMy() {
        return isMy;
    }

    public void setIsMy(String isMy) {
        this.isMy = isMy;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendLogo() {
        return sendLogo;
    }

    public void setSendLogo(String sendLogo) {
        this.sendLogo = sendLogo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getIsSendOk() {
        return isSendOk;
    }

    public void setIsSendOk(String isSendOk) {
        this.isSendOk = isSendOk;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Message{" +
                "meassgeId='" + meassgeId + '\'' +
                ", userId='" + userId + '\'' +
                ", isMy='" + isMy + '\'' +
                ", messageType='" + messageType + '\'' +
                ", msg='" + msg + '\'' +
                ", sendId='" + sendId + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendLogo='" + sendLogo + '\'' +
                ", type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", readType='" + readType + '\'' +
                ", isSendOk='" + isSendOk + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
