package com.android.kiway.zbus;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author yimin
 */
public class PushMessageVo {
    private String installationId;
    private String title;
    private String description;
    private String message;
    private String appId;
    /** 应用模块*/
    private String module;
    /** 要推送给那些用户的用户id*/
    private Set<String> userId;
    /** 发送时间 延迟发送才需要*/
    private Date sendTime;
    /** 发送人id 可选*/
    private String senderId;
    /** 额外参数*/
    private Map<String, Object> extras;
    /** 推送消息的类型 single:单发 notification:广播推送 zbus (目前只做这三种)*/
    private String pushType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Set<String> getUserId() {
        return userId;
    }

    public void setUserId(Set<String> userId) {
        this.userId = userId;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Map<String, Object> getExtras() {
        return extras;
    }

    public void setExtras(Map<String, Object> extras) {
        this.extras = extras;
    }

    public String getPushType() {
        return pushType;
    }

    public void setPushType(String pushType) {
        this.pushType = pushType;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }
}
