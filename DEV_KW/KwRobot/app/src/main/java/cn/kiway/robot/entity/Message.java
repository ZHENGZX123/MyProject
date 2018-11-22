package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/6/13.
 */

public class Message {

    public int type;//1文字
    public long createTime;
    public String talker;
    public String remark;
    public String content;
    public String imgPath;


    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", createTime=" + createTime +
                ", talker='" + talker + '\'' +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", imgPath='" + imgPath + '\'' +
                '}';
    }
}
