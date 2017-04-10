package cn.kwim.mqttcilent.common.cache.javabean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by hmg on 2017/1/9.
 * 群好友表
 */

@RealmClass
public class GroupContent extends RealmObject {
    @PrimaryKey
    private String key;
    //当前用户Id
    @Required
    private String userId;
    @Required
    private String groupId;
    @Required
    private String msg;
    //标记已读未读
    @Required
    private String status;
    //标记发送人是否是子自己
    @Required
    private String isMy;
    //标记字段类型 普通文本还是音频，视频等
    @Required
    private String type;
    //时间
    @Required
    private String time;
    //发送者Id
    @Required
    private String sendorId;

    @Required
    private String sendorName;
    @Required
    private String sendorHeader;
    //判断是否播放，音频
    @Required
    private String isPlay;

    @Override
    public String toString() {
        return "GroupContent{" +
                "key='" + key + '\'' +
                ", userId='" + userId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", isMy='" + isMy + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", sendorId='" + sendorId + '\'' +
                ", sendorName='" + sendorName + '\'' +
                ", sendorHeader='" + sendorHeader + '\'' +
                ", isPlay='" + isPlay + '\'' +
                '}';
    }

    public String getIsPlay() {
        return isPlay;
    }

    public void setIsPlay(String isPlay) {
        this.isPlay = isPlay;
    }

    public String getSendorHeader() {
        return sendorHeader;
    }

    public void setSendorHeader(String sendorHeader) {
        this.sendorHeader = sendorHeader;
    }

    public String getSendorName() {
        return sendorName;
    }

    public void setSendorName(String sendorName) {
        this.sendorName = sendorName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsMy() {
        return isMy;
    }

    public void setIsMy(String isMy) {
        this.isMy = isMy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendorId() {
        return sendorId;
    }

    public void setSendorId(String sendorId) {
        this.sendorId = sendorId;
    }
}
