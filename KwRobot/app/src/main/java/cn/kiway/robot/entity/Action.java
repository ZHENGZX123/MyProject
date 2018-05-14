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
    public static final int TYPE_MOMENT = 100;

    public static final int TYPE_SET_FORWARDTO = 100;//设置消息转发对象：公众号、消息收集人，都是这个设置。

    public static final int TYPE_PUBLIC_ACCONT_FORWARDING = 101;//需要转发给指定的人
    public static final int TYPE_COLLECTOR_FORWARDING = 102;//发到消息收集群

    //朋友圈使者，目前暂时用不到了
    public static final int TYPE_FRIEND_CIRCLER = 99;//需要转发到朋友圈
    public static final int TYPE_SET_FRIEND_CIRCLER_REMARK = 103;//设置朋友圈转发备注

    public static final int TYPE_REQUEST_FRIEND = 104;//好友请求
    public static final int TYPE_AUTO_MATCH = 109;//自动匹配的消息
    public static final int TYPE_BACK_DOOR = 110;//机器人后门用
    public static final int TYPE_CLEAR_ZOMBIE_FAN = 111;//清理僵尸粉
    public static final int TYPE_GET_ALL_FRIENDS = 112;//重新计算好友数量
    public static final int TYPE_CREATE_GROUP_CHAT = 113;//发起群聊
    public static final int TYPE_ADD_GROUP_PEOPLE = 114;//拉人进群
    public static final int TYPE_DELETE_GROUP_PEOPLE = 115;//踢人出群
    public static final int TYPE_FIX_GROUP_NAME = 116;//修改群名称
    public static final int TYPE_FIX_GROUP_NOTICE = 117;//修改群公告
    public static final int TYPE_GROUP_CHAT = 118;//修改群公告
    public static final int TYPE_AT_GROUP_PEOPLE = 119;//艾特某人
    public static final int TYPE_DELETE_MOMENT = 120;//踢人出群
    public static final int TYPE_ADD_FRIEND = 121;//主动搜索并添加好友
    public static final int TYPE_MISSING_FISH = 122;//漏网之鱼
    public static final int TYPE_FIX_NICKNAME = 123;//修改昵称
    public static final int TYPE_FIX_ICON = 124;//修改头像
    public static final int TYPE_CHECK_NEW_VERSION = 125;//检查新版本
    public static final int TYPE_NEARBY_PEOPLE = 126;//附近的人

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
