package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/6/13.
 */

public class Message {
    public int type;
    public long createTime;
    public String talker;//nickname, remark
    public String content;

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", createTime=" + createTime +
                ", talker='" + talker + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
