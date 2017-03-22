package cn.kwim.mqttcilent.common.cache.javabean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by Administrator on 2017/1/9.
 */

@RealmClass
public class MainList extends RealmObject {
    @PrimaryKey
    private String key;   //标识唯一Key
    @Required
    private String Id;  // 标识
    @Required
    private String userId;  //用户Id
    @Required
    private String msg;   //内容
    @Required
    private String number; //未读消息数量
    @Required
    private String name; //名称
    @Required
    private String time; //时间
    @Required
    private String sendType; //区别群和个人
    @Required
    private String logo; //头像
    @Required
    private String msgType; //消息类型
    @Required
    private String sendName; //发送人
    @Required
    private String ug_type; //群标识 （1为班级群，0为普通群）
    @Required
    private String classId;//班级id

    @Override
    public String toString() {
        return "MainList{" +
                "key='" + key + '\'' +
                ", Id='" + Id + '\'' +
                ", userId='" + userId + '\'' +
                ", msg='" + msg + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", sendType='" + sendType + '\'' +
                ", logo='" + logo + '\'' +
                ", msgType='" + msgType + '\'' +
                ", sendName='" + sendName + '\'' +
                ", ug_type='" + ug_type + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }


    public String getUg_type() {
        return ug_type;
    }

    public void setUg_type(String ug_type) {
        this.ug_type = ug_type;
    }


    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String groupName) {
        this.name = groupName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
}
