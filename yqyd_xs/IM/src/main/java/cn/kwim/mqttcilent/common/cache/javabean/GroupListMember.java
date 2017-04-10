package cn.kwim.mqttcilent.common.cache.javabean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by Administrator on 2017/1/10.
 */

@RealmClass
public class GroupListMember extends RealmObject{
    @PrimaryKey
    private String key;
    @Required
    private String name;
    @Required
    private String groupId;
    @Required
    private String header;
    @Required
    private String userId;
    @Required
    private String id;
    @Required
    private String sortLetters;  //显示数据拼音的首字母

    @Override
    public String toString() {
        return "GroupListMember{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", groupId='" + groupId + '\'' +
                ", header='" + header + '\'' +
                ", userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", sortLetters='" + sortLetters + '\'' +
                '}';
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
