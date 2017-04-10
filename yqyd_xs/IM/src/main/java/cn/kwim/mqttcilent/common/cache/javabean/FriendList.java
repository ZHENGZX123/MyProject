package cn.kwim.mqttcilent.common.cache.javabean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by hmg on 2017/1/5.
 *  好友列表实体类
 */
@RealmClass
public class FriendList extends RealmObject{
    //设置唯一标识
    @PrimaryKey
    private String key;

    @Required
    private String friendid;
    @Required
    private String signature;
    @Required
    private String friendnick;
    @Required
    private String flags;
    @Required
    private String logo;
    @Required
    private String remark;



    @Override
    public String toString() {
        return "FriendList{" +
                "friendid='" + friendid + '\'' +
                ", signature='" + signature + '\'' +
                ", friendnick='" + friendnick + '\'' +
                ", flags='" + flags + '\'' +
                ", im_logo='" + logo + '\'' +
                ", remark='" + remark + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getFriendnick() {
        return friendnick;
    }

    public void setFriendnick(String friendnick) {
        this.friendnick = friendnick;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
