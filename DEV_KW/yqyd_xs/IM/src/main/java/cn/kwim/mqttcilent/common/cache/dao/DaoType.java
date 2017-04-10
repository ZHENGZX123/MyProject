package cn.kwim.mqttcilent.common.cache.dao;

/**
 * Created by Administrator on 2017/1/9.
 */

public interface DaoType {
    /**
     * 聊天数据是否是登陆用户自己发送
     */
    interface ISMY{
        String ISMY = "isMy";
        String UN_ISMY = "un_ismy";
    }

    /**
     * 聊天数据类型
     */
    interface TYPY{
        String TEXT = "text";     //普通信息
        String PUSH = "push";//推送通知
        String FILE = "file";//文件信息
        String IMAGE = "image";//图片信息
        String VOICE = "voice";//音频信息
        String VIDEO = "im_video";//视频信息
        String HOMEWORK = "homework";//作业信息
        String NOTICE = "notice";//通知信息
        String ACTIVITY = "activity";//活动信息
        String LOCATION = "location";//位置信息
        String TOUSER = "touser";//@用户
        String LINK = "link"; //链接
        String MUSIC = "music";//音乐
        String RECALLMSG = "recallmsg"; //撤回
        String EXT = "ext"; //扩展

    }

    /**
     * 音频，视频等是否播放
     */
    interface ISPLAY{
        String ISPLAY = "isPaly"; //播放
        String UNPLAY = "unPaly"; //未播放
        String NOPLAY ="noPaly"; //非此类消息
    }

    /**
     * 是否已读
     */
    interface READ{
        String ISREAD = "isread"; //已读
        String UNREAD = "unread"; //未读
    }
    interface ISSENDOK{
        String OK = "ok";
        String NO = "no";
    }

    interface SESSTIONTYPE{
        //个人
        String PERSON ="1";
        //群
        String GROUP = "2";
        //应用号
        String OTHER = "3";
    }

}
