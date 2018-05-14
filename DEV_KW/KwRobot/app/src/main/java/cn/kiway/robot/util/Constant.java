package cn.kiway.robot.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.kiway.robot.entity.Action;

/**
 * Created by Administrator on 2018/2/23.
 */

public class Constant {


    public static final String APPID = "930a4b41b8c92d30f790a6bf01bfe78a";
    public static final String APPKEY = "c83f643092046c0328624fe59aeec6548dac256c";

    public static String host = "robot.kiway.cn";
    public static String port = "5676";

    public static String clientUrl = "http://robot.kiway.cn";

    public static final String testImage = "http://upload.jnwb.net/2014/0311/1394514005639.jpg";

    static {

//        host = "robot.kiway.cn";
//        port = "5676";
//        clientUrl = "http://robot.kiway.cn";

        host = "rbtest.kiway.cn";
        port = "5672";
        clientUrl = "http://rbtest.kiway.cn";

//        host = "192.168.8.161";
//        port = "15555";
//        clientUrl = "http://192.168.8.161:8081";
    }

    public static Map<String, String> qas = new HashMap<>();

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

    public static final String BACK_DOOR2 = "开维一本万利";
    public static final String BACK_DOOR3 = "开维前程似锦";

    private static final String BACK_DOOR4 = "清理僵尸粉";
    private static final String BACK_DOOR5 = "查询好友数量";
    private static final String BACK_DOOR6 = "发起群聊";
    private static final String BACK_DOOR7 = "拉人入群";
    private static final String BACK_DOOR8 = "踢人出群";
    private static final String BACK_DOOR9 = "修改群名称";
    private static final String BACK_DOOR10 = "修改群公告";
    private static final String BACK_DOOR11 = "群发消息";
    private static final String BACK_DOOR12 = "艾特某人";
    private static final String BACK_DOOR13 = "删除朋友圈";
    private static final String BACK_DOOR14 = "添加朋友";//电话号码或微信号
    private static final String BACK_DOOR15 = "漏网之鱼";
    private static final String BACK_DOOR16 = "修改昵称";
    private static final String BACK_DOOR17 = "修改头像";
    private static final String BACK_DOOR18 = "检查新版本";
    private static final String BACK_DOOR19 = "附近的人";


    //{"cmd": "群里拉人","groupName":"测试群","backdoor":true}
    //{"cmd": "漏网之鱼","backdoor":true}
    //{"cmd": "添加朋友","members":["18626318013","13267069058"], "content":"你好，可以加个好友吗？","backdoor":true}
    //{"cmd": "查询好友数量","backdoor":true}
    //{"cmd": "清理僵尸粉","start": "1","end":"20","backdoor":true}
    //{"cmd": "发起群聊","members": ["5行","5之","执着"],"groupName": "111","backdoor":true}
    //{"cmd": "拉人入群","members": ["5行","5之"],"groupName": "111","backdoor":true}
    //{"cmd": "踢人出群","members": ["5行","5之"],"groupName": "111","backdoor":true}
    //{"cmd": "修改群公告","content": "群公告啊啊啊","groupName": "222","backdoor":true}
    //{"cmd": "修改群名称","content":"1","groupName": "111","backdoor":true}
    //{"cmd": "群发消息","content":"1","groupName": "111","backdoor":true}
    //{"cmd": "艾特某人","members": ["执着","朋友圈使者擦"],"groupName": "222","backdoor":true}
    //{"cmd": "删除朋友圈","content":"密密麻麻","backdoor":true}
    //{"cmd": "修改昵称","newName":"我是客服888", "oldName":"客服888", "backdoor":true}
    //{"cmd": "修改头像","url":"http://upload.jnwb.net/2014/0311/1394514005639.jpg", "backdoor":true}
    //{"cmd": "检查新版本"}
    //{"cmd": "附近的人" , "content":"你好，很高兴认识你。" ,"backdoor":true}


    static Map<String, Integer> backdoors = new LinkedHashMap<>();

    static {
        backdoors.put(BACK_DOOR2, Action.TYPE_BACK_DOOR);
        backdoors.put(BACK_DOOR3, Action.TYPE_BACK_DOOR);

        backdoors.put(BACK_DOOR4, Action.TYPE_CLEAR_ZOMBIE_FAN);
        backdoors.put(BACK_DOOR5, Action.TYPE_GET_ALL_FRIENDS);
        backdoors.put(BACK_DOOR6, Action.TYPE_CREATE_GROUP_CHAT);
        backdoors.put(BACK_DOOR7, Action.TYPE_ADD_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR8, Action.TYPE_DELETE_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR9, Action.TYPE_FIX_GROUP_NAME);
        backdoors.put(BACK_DOOR10, Action.TYPE_FIX_GROUP_NOTICE);
        backdoors.put(BACK_DOOR11, Action.TYPE_GROUP_CHAT);
        backdoors.put(BACK_DOOR12, Action.TYPE_AT_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR13, Action.TYPE_DELETE_MOMENT);
        backdoors.put(BACK_DOOR14, Action.TYPE_ADD_FRIEND);
        backdoors.put(BACK_DOOR15, Action.TYPE_MISSING_FISH);
        backdoors.put(BACK_DOOR16, Action.TYPE_FIX_NICKNAME);
        backdoors.put(BACK_DOOR17, Action.TYPE_FIX_ICON);
        backdoors.put(BACK_DOOR18, Action.TYPE_CHECK_NEW_VERSION);
        backdoors.put(BACK_DOOR19, Action.TYPE_NEARBY_PEOPLE);
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
