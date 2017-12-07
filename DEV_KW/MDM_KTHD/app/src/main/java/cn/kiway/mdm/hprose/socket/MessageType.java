package cn.kiway.mdm.hprose.socket;

/**
 * Created by Administrator on 2017/11/1.
 */

public class MessageType {

    public final static int LOGIN = 1;//登录
    public final static int LOGINOUT = LOGIN + 1;//有用户离开或者掉线了
    public final static int SENDMSG = LOGINOUT + 1;//发送消息（普通的文字消息）

    public final static int SIGN = SENDMSG + 1;//签到
    public final static int ANSWER = SIGN + 1;//抢答
    public final static int UNANSWER = ANSWER + 1;//抢答结束

    public final static int LOCKSCREEN = UNANSWER + 1;//锁屏
    public final static int SOLUTIONSCREE = LOCKSCREEN + 1;//解屏

    public final static int SCREENSHARE = SOLUTIONSCREE + 1;//打开屏幕共享
    public final static int OFFSCREENSHARE = SCREENSHARE + 1;//关闭屏幕共享

    public final static int SUREREPONSE = OFFSCREENSHARE + 1;//确实是否听懂，是与否在 msg中体现，1听懂，0听不懂

    public final static int FILE_SHAR = SUREREPONSE + 1;//文件分享

    public final static int OFF_MOBLIE = FILE_SHAR + 1;//关机
    public final static int REON_MOBLIE = OFF_MOBLIE + 1;//重启

    public final static int USE_CAMARE = REON_MOBLIE + 1;//是否使用相机  msg 0不 1使
    public final static int USE_WIFI = USE_CAMARE + 1;//是否使用WIFI  msg 0不 1使
    public final static int USE_4G = USE_WIFI + 1;//是否使用移动数据  msg 0不 1使
    public final static int USE_BULEBOOH = USE_4G + 1;//是否使用蓝牙  msg 0不 1使
    public final static int USE_MICROPHONE = USE_BULEBOOH + 1;//是否使用麦克风  msg 0不 1使
    public final static int USE_SOUND = USE_MICROPHONE + 1;//是否使用声音 msg 0不 1使
    public final static int USE_SYSTEM_UPDATE = USE_SOUND + 1;//是否使用系统更新
    public final static int USE_WIFI_HOTSPOT = USE_SYSTEM_UPDATE + 1;//是否使用便携式热点 msg 0不 1使
    public final static int USE_ADDITIONAL_FUNCTION = USE_WIFI_HOTSPOT + 1;//是否使用辅助功能 msg 0不 1使
    public final static int SCREEN = USE_ADDITIONAL_FUNCTION + 1;//横屏 msg 0横屏 1竖屏

    public final static int SHARE_FILE = SCREEN + 1;//分享文件



}
