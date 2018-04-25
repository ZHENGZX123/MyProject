package cn.kiway.robot.entity;

import android.app.PendingIntent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Action {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 3;// 图片3 语音34 名片42 视频43 收藏的表情47 转账49 文件50 链接52 笔记53
    public static final int TYPE_VIDEO = 43;
    public static final int TYPE_FILE = 50;
    public static final int TYPE_LINK = 52;
    public static final int TYPE_CARD = 42;
    //public static final int TYPE_GPS = 99;

    public static final int TYPE_SET_FORWARDTO = 100;//设置消息转发对象：公众号、消息收集人，都是这个设置。

    public static final int TYPE_PUBLIC_ACCONT_FORWARDING = 101;//需要转发给指定的人
    public static final int TYPE_COLLECTOR_FORWARDING = 102;//发到消息收集群

    public static final int TYPE_FRIEND_CIRCLER = 99;//需要转发到朋友圈
    public static final int TYPE_SET_FRIEND_CIRCLER_REMARK = 103;//设置朋友圈转发备注

    public static final int TYPE_REQUEST_FRIEND = 104;//好友请求
    public static final int TYPE_AUTO_MATCH = 109;//自动匹配的消息
    public static final int TYPE_BACK_DOOR = 110;//机器人后门用

    public PendingIntent intent;
    public String sender;
    public String content;
    public ArrayList<ReturnMessage> returnMessages = new ArrayList<>();
    public int actionType; //事件类型
    public boolean replied;

    @Override
    public String toString() {
        return "Action{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", returnMessages=" + returnMessages +
                ", actionType=" + actionType +
                ", replied=" + replied +
                '}';
    }
}
