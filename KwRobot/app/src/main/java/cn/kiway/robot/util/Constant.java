package cn.kiway.robot.util;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/2/23.
 */

public class Constant {


    public static final String APPID = "930a4b41b8c92d30f790a6bf01bfe78a";
    public static final String APPKEY = "c83f643092046c0328624fe59aeec6548dac256c";

    public static String zbusHost = "robot.kiway.cn";  //192.168.8.161  rbtest.kiway.cn robot.kiway.cn
    public static String zbusPort = "15555";//15555 25555

    public static String clientUrl = "http://robot.kiway.cn";  //"http://192.168.8.161:8081"; http://rbtest.kiway.cn http://robot.kiway.cn

    public static final String testImage = "http://upload.jnwb.net/2014/0311/1394514005639.jpg";

    static {

        zbusHost = "robot.kiway.cn";
        zbusPort = "15555";
        clientUrl = "http://robot.kiway.cn";

//        zbusHost = "rbtest.kiway.cn";
//        zbusPort = "25555";
//        clientUrl = "http://rbtest.kiway.cn";

//        zbusHost = "192.168.8.161";
//        zbusPort = "15555";
//        clientUrl = "http://192.168.8.161:8081";
    }

    public static HashMap<String, String> qas = new HashMap<>();

    static {
        qas.put("你好", "家长您好。");
        qas.put("您好", "家长您好。");
        qas.put("客服你好", "家长您好。");
        qas.put("客服您好", "家长您好。");
        qas.put("客服在吗", "家长您好，请问您要咨询什么问题？");
        qas.put("谢谢", "不用谢。欢迎下次再咨询。");
        /*qas.put("[图片]", "暂不支持图片咨询。");*/
        qas.put("[文件]", "暂不支持文件咨询。");
        qas.put("[视频]", "暂不支持视频咨询。");
        qas.put("[语音]", "暂不支持语音咨询。");
        qas.put("[位置]", "暂不支持位置咨询。");
        qas.put("[动画表情]", "暂不支持动画表情咨询。");

    }


    public static String BACK_DOOR1 = "开维一本万利";
    public static String BACK_DOOR2 = "开维前程似锦";
    public static String BACK_DOOR3 = "查询好友数量";
    public static String BACK_DOOR4 = "清理僵尸粉";

    public static HashMap<String, String> backdoors = new HashMap<>();

    static {
        backdoors.put(BACK_DOOR1, BACK_DOOR1);
        backdoors.put(BACK_DOOR2, BACK_DOOR2);
        backdoors.put(BACK_DOOR3, BACK_DOOR3);
//        backdoors.put(BACK_DOOR4, "");
    }

    public static final String HEART_BEAT_TESTER = "心跳测试使者";

    public static final String DEFAULT_WELCOME_TITLE = "感谢您添加招生客服，您可以发送您的问题进行人工咨询。为了减少您的等待，您可以按以下序号或关键字发送咨询招生相关问题。谢谢！";

    public static final String DEFAULT_WELCOME = "感谢您添加招生客服机器人，您可以按以下序号或关键字发送咨询招生相关问题，谢谢！\n" +
            "1、计生证明或者计划生育证明\n" +
            "2、租房或者住房\n" +
            "3、台湾或者香港\n" +
            "4、户籍\n" +
            "5、网上报名\n" +
            "6、验核材料\n" +
            "7、录取\n";
    public static final String DEFAULT_BUSY = "因为咨询人员较多，客服正忙，请耐心等待。";

    public static final String DEFAULT_OFFLINE = "客服已下线，请于工作时间8：30-22：00再咨询，或者您可以发送以下序号或关键字咨询：";

}
