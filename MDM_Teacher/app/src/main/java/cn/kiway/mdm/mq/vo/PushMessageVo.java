//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.kiway.mdm.mq.vo;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class PushMessageVo {
    private String installationId;
    private String title;
    private String description;
    private String message;
    private String appId;
    private String module;
    private Set<String> userId;
    private Date sendTime;
    private String senderId;
    private Map<String, Object> extras;
    private String pushType;

    public PushMessageVo() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Set<String> getUserId() {
        return this.userId;
    }

    public void setUserId(Set<String> userId) {
        this.userId = userId;
    }

    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Map<String, Object> getExtras() {
        return this.extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    public String getPushType() {
        return this.pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getInstallationId() {
        return this.installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }
}
