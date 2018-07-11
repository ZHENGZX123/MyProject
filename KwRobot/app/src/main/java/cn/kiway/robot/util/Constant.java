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

    public static String host = "";
    public static Integer port = 0;
    public static String clientUrl = "";

    public static final int DEFAULT_RELEASE_TIME = 100 * 1000;
    public static final String HOUTAI = "开维后台";
    public static final String PASSWORD = "kiwayedukiway123";

    static {
        host = "robot.kiway.cn";
        port = 5676;
        clientUrl = "http://robot.kiway.cn";

//        host = "rbtest.kiway.cn";
//        port = 5672;
//        clientUrl = "http://rbtest.kiway.cn";
    }

    //NODE类型
    public static final String NODE_TEXTVIEW = "android.widget.TextView";
    public static final String NODE_BUTTON = "android.widget.Button";
    public static final String NODE_EDITTEXT = "android.widget.EditText";
    public static final String NODE_IMAGEVIEW = "android.widget.ImageView";
    public static final String NODE_FRAMELAYOUT = "android.widget.FrameLayout";
    public static final String NODE_RELATIVELAYOUT = "android.widget.RelativeLayout";
    public static final String NODE_LINEARLAYOUT = "android.widget.LinearLayout";
    public static final String NODE_IMAGEBUTTON = "android.widget.ImageButton";
    public static final String NODE_CHECKBOX = "android.widget.CheckBox";
    public static final String NODE_RADIOBUTTON = "android.widget.RadioButton";

    //点击类型
    public static final int CLICK_NONE = Integer.MIN_VALUE;
    public static final int CLICK_SELF = 0;
    public static final int CLICK_PARENT = Integer.MAX_VALUE;

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

    public static final String DEFAULT_WELCOME_TITLE = "感谢您添加招生客服，您可以发送您的问题进行人工咨询。为了减少您的等待，您可以按以下序号或关键字发送咨询招生相关问题。谢谢！";

    public static final String DEFAULT_VALIDATION = "您好，可以加个好友吗？";
    public static final String DEFAULT_BACKUP = "该微信的好友已经达上限";
    public static final int MAX_FRIENDS = 4900;
    public static final String DEFAULT_TRANSFER = "harry_wu2886";

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

    public static final String SEND_FRIEND_CIRCLE_CMD = "sendFriendCircleCmd";
    public static final String SEND_FRIEND_CIRCLE_REPLY_CMD = "sendFriendCircleReplyCmd";
    public static final String DELETE_FRIEND_CIRCLE_CMD = "deleteFriendCircleCmd";
    public static final String DELETE_FRIEND_CIRCLE_REPLY_CMD = "deleteFriendCircleReplyCmd";
    public static final String PERSION_NEARBY_CMD = "persionNearbyCmd";
    public static final String PERSION_NEARBY_REPLY_CMD = "persionNearbyReplyCmd";
    public static final String ADD_FRIEND_CMD = "addFriendCmd";
    public static final String ADD_FRIEND_REPLY_CMD = "addFriendReplyCmd";
    public static final String UPDATE_NICKNAME_CMD = "updateNickNameCmd";
    public static final String UPDATE_NICKNAME_REPLY_CMD = "updateNickNameReplyCmd";
    public static final String UPDATE_AVATAR_CMD = "updateAvatarCmd";
    public static final String UPDATE_AVATAR_REPLY_CMD = "updateAvatarReplyCmd";
    public static final String FORGET_FISH_CMD = "forgetFishCmd";
    public static final String FORGET_FISH_REPLY_CMD = "forgetFishReplyCmd";
    public static final String UPDATE_FRIEND_NICKNAME_CMD = "updateFriendNickNameCmd";
    public static final String UPDATE_FRIEND_NICKNAME_REPLY_CMD = "updateFriendNickNameReplyCmd";
    public static final String DELETE_FRIEND_CMD = "deleteFriendCmd";
    public static final String DELETE_FRIEND_REPLY_CMD = "deleteFriendReplyCmd";
    public static final String CREATE_GROUP_CHAT_CMD = "groupChatCmd";//发起群聊
    public static final String CREATE_GROUP_CHAT_REPLY_CMD = "groupChatReplyCmd";
    public static final String CHAT_IN_GROUP_CMD = "chatInGroupCmd";//群发消息
    public static final String INVITE_GROUP_CMD = "invitGroupCmd";//加人
    public static final String INVITE_GROUP_REPLY_CMD = "invitGroupReplyCmd";//加人
    public static final String TICK_PERSON_GROUP_CMD = "tickPersonGroupCmd";//踢人
    public static final String TICK_PERSON_GROUP_REPLY_CMD = "tickPersonGroupReplyCmd";//踢人
    public static final String UPDATE_GROUP_NOTICE_CMD = "updateGroupNoticeCmd";
    public static final String UPDATE_GROUP_NOTICE_REPLY_CMD = "updateGroupNoticeReplyCmd";
    public static final String UPDATE_GROUP_NAME_CMD = "updateGroupNameCmd";
    public static final String UPDATE_GROUP_NAME_REPLY_CMD = "updateGroupNameReplyCmd";
    public static final String DELETE_GROUP_CMD = "deleteGroupCmd";
    public static final String DELETE_GROUP_REPLY_CMD = "deleteGroupReplyCmd";
    public static final String SEND_BATCH_CMD = "groupSendBatchMessageCmd";
    public static final String SEND_BATCH_REPLY_CMD = "groupSendBatchMessageReplyCmd";
    public static final String AT_PERSONS_CMD = "atPersonsCmd";
    public static final String AT_PERSONS_REPLY_CMD = "atPersonsReplyCmd";
    public static final String UPGRADE_CMD = "upgradeCmd";
    public static final String AUTO_REPLY_CONTENT_CMD = "autoReplyContentCmd";
    public static final String AUTO_REPLY_CONTENT_REPLY_CMD = "autoReplyContentReplyCmd";

    public static Map<String, String> replies = new HashMap<>();

    static {
        replies.put(SEND_FRIEND_CIRCLE_CMD, SEND_FRIEND_CIRCLE_REPLY_CMD);
        replies.put(DELETE_FRIEND_CIRCLE_CMD, DELETE_FRIEND_CIRCLE_REPLY_CMD);
        replies.put(ADD_FRIEND_CMD, ADD_FRIEND_REPLY_CMD);
        replies.put(PERSION_NEARBY_CMD, PERSION_NEARBY_REPLY_CMD);
        replies.put(UPDATE_NICKNAME_CMD, UPDATE_NICKNAME_REPLY_CMD);
        replies.put(UPDATE_AVATAR_CMD, UPDATE_AVATAR_REPLY_CMD);
        replies.put(FORGET_FISH_CMD, FORGET_FISH_REPLY_CMD);
        replies.put(UPDATE_FRIEND_NICKNAME_CMD, UPDATE_FRIEND_NICKNAME_REPLY_CMD);
        replies.put(DELETE_FRIEND_CMD, DELETE_FRIEND_REPLY_CMD);
        replies.put(CREATE_GROUP_CHAT_CMD, CREATE_GROUP_CHAT_REPLY_CMD);
        replies.put(INVITE_GROUP_CMD, INVITE_GROUP_REPLY_CMD);
        replies.put(TICK_PERSON_GROUP_CMD, TICK_PERSON_GROUP_REPLY_CMD);
        replies.put(UPDATE_GROUP_NOTICE_CMD, UPDATE_GROUP_NOTICE_REPLY_CMD);
        replies.put(UPDATE_GROUP_NAME_CMD, UPDATE_GROUP_NAME_REPLY_CMD);
        replies.put(DELETE_GROUP_CMD, DELETE_GROUP_REPLY_CMD);
        replies.put(SEND_BATCH_CMD, SEND_BATCH_REPLY_CMD);
        replies.put(AT_PERSONS_CMD, AT_PERSONS_REPLY_CMD);
        replies.put(AUTO_REPLY_CONTENT_CMD, AUTO_REPLY_CONTENT_REPLY_CMD);
    }

    public static final String BACK_DOOR1 = "开维一本万利";
    public static final String BACK_DOOR2 = "开维前程似锦";
    private static final String BACK_DOOR4 = "清理僵尸粉";
    private static final String BACK_DOOR6 = "发起群聊";
    private static final String BACK_DOOR7 = "拉人入群";
    private static final String BACK_DOOR8 = "踢人出群";
    private static final String BACK_DOOR9 = "修改群名称";
    private static final String BACK_DOOR10 = "修改群公告";
    public static final String BACK_DOOR11 = "群发消息";
    private static final String BACK_DOOR12 = "艾特某人";
    private static final String BACK_DOOR13 = "删除朋友圈";
    private static final String BACK_DOOR14 = "添加朋友";
    private static final String BACK_DOOR15 = "漏网之鱼";
    private static final String BACK_DOOR16 = "修改昵称";
    private static final String BACK_DOOR17 = "修改头像";
    private static final String BACK_DOOR18 = "检查新版本";
    private static final String BACK_DOOR19 = "附近的人";
    private static final String BACK_DOOR20 = "解散群聊";
    private static final String BACK_DOOR21 = "删除朋友";
    private static final String BACK_DOOR22 = "发朋友圈";
    private static final String BACK_DOOR23 = "群发助手";
    private static final String BACK_DOOR24 = "修改好友昵称";


    //{"cmd": "群发消息","message":"1", "type":"1", "clientGroupId": "9189004002@chatroom"}  TODO新增type
    //{"cmd": "删除朋友","members":["执着","13267069058"]}
    //{"cmd": "群发助手","content":"1", "members":["浪翻云","胡翻翻"]}
    //{"cmd": "解散群聊","groupName": "最新111" }
    //{"cmd": "清理僵尸粉","start": "1","end":"20"}
    //{"cmd": "漏网之鱼"}
    //{"cmd": "添加朋友","members":["18626318013","13267069058"], "message":"你好，可以加个好友吗？"}
    //{"cmd": "查询好友数量"}
    //{"cmd": "发起群聊","members": ["5行","5之","执着"],"groupName": "111"}
    //{"cmd": "拉人入群","members": ["5行","5之"],"groupName": "111"}
    //{"cmd": "踢人出群","members": ["5行","5之"],"groupName": "111"}
    //{"cmd": "修改群公告","content": "群公告啊啊啊","groupName": "111"}
    //{"cmd": "修改群名称","content":"1","groupName": "111"}
    //{"cmd": "艾特某人","members": ["执着","朋友圈使者擦"],"groupName": "111jjj" , "content":"xxx"}
    //{"cmd": "删除朋友圈","content":"快捷键"}
    //{"cmd": "检查新版本"}
    //{"cmd": "修改昵称","newName":"客服888", "oldName":"客服888" , "me":true}
    //{"cmd": "修改头像","url":"http://pic.92to.com/201611/18/20150823115832203.jpg"}
    //{"cmd": "附近的人" , "content":"你好，很高兴认识你。" }

    public static Map<String, Integer> backdoors = new LinkedHashMap<>();

    static {
        backdoors.put(BACK_DOOR1, Action.TYPE_BACK_DOOR);
        backdoors.put(BACK_DOOR2, Action.TYPE_BACK_DOOR);
        backdoors.put(BACK_DOOR13, Action.TYPE_DELETE_MOMENT);
        backdoors.put(DELETE_FRIEND_CIRCLE_CMD, Action.TYPE_DELETE_MOMENT);
        backdoors.put(BACK_DOOR22, Action.TYPE_SEND_MOMENT);
        backdoors.put(SEND_FRIEND_CIRCLE_CMD, Action.TYPE_SEND_MOMENT);
        backdoors.put(BACK_DOOR14, Action.TYPE_ADD_FRIEND);
        backdoors.put(ADD_FRIEND_CMD, Action.TYPE_ADD_FRIEND);
        backdoors.put(BACK_DOOR16, Action.TYPE_FIX_NICKNAME);
        backdoors.put(UPDATE_NICKNAME_CMD, Action.TYPE_FIX_NICKNAME);
        backdoors.put(BACK_DOOR17, Action.TYPE_FIX_ICON);
        backdoors.put(UPDATE_AVATAR_CMD, Action.TYPE_FIX_ICON);
        backdoors.put(BACK_DOOR19, Action.TYPE_NEARBY_PEOPLE);
        backdoors.put(PERSION_NEARBY_CMD, Action.TYPE_NEARBY_PEOPLE);
        backdoors.put(BACK_DOOR15, Action.TYPE_MISSING_FISH);
        backdoors.put(FORGET_FISH_CMD, Action.TYPE_MISSING_FISH);
        backdoors.put(BACK_DOOR24, Action.TYPE_FIX_FRIEND_NICKNAME);
        backdoors.put(UPDATE_FRIEND_NICKNAME_CMD, Action.TYPE_FIX_FRIEND_NICKNAME);
        backdoors.put(BACK_DOOR21, Action.TYPE_DELETE_FRIEND);
        backdoors.put(DELETE_FRIEND_CMD, Action.TYPE_DELETE_FRIEND);
        backdoors.put(BACK_DOOR6, Action.TYPE_CREATE_GROUP_CHAT);
        backdoors.put(CREATE_GROUP_CHAT_CMD, Action.TYPE_CREATE_GROUP_CHAT);
        backdoors.put(BACK_DOOR11, Action.TYPE_GROUP_CHAT);
        backdoors.put(CHAT_IN_GROUP_CMD, Action.TYPE_GROUP_CHAT);
        backdoors.put(BACK_DOOR7, Action.TYPE_ADD_GROUP_PEOPLE);
        backdoors.put(INVITE_GROUP_CMD, Action.TYPE_ADD_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR8, Action.TYPE_DELETE_GROUP_PEOPLE);
        backdoors.put(TICK_PERSON_GROUP_CMD, Action.TYPE_DELETE_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR9, Action.TYPE_FIX_GROUP_NAME);
        backdoors.put(UPDATE_GROUP_NAME_CMD, Action.TYPE_FIX_GROUP_NAME);
        backdoors.put(BACK_DOOR10, Action.TYPE_FIX_GROUP_NOTICE);
        backdoors.put(UPDATE_GROUP_NOTICE_CMD, Action.TYPE_FIX_GROUP_NOTICE);
        backdoors.put(BACK_DOOR20, Action.TYPE_DELETE_GROUP_CHAT);
        backdoors.put(DELETE_GROUP_CMD, Action.TYPE_DELETE_GROUP_CHAT);
        backdoors.put(SEND_BATCH_CMD, Action.TYPE_SEND_BATCH);
        backdoors.put(BACK_DOOR12, Action.TYPE_AT_GROUP_PEOPLE);
        backdoors.put(AT_PERSONS_CMD, Action.TYPE_AT_GROUP_PEOPLE);
        backdoors.put(BACK_DOOR18, Action.TYPE_CHECK_NEW_VERSION);
        backdoors.put(UPGRADE_CMD, Action.TYPE_CHECK_NEW_VERSION);

        backdoors.put(BACK_DOOR4, Action.TYPE_CLEAR_ZOMBIE_FAN);
        backdoors.put(BACK_DOOR23, Action.TYPE_GROUP_SEND_HELPER);
    }

    //机器人工作原理
    public static final int SKILL_USE_NOTIFICATION = 0;
    public static final int SKILL_USE_DATABASE = 1;
    public static final int SKILL_USE_MIXED = 2;
    public static int useSkill = SKILL_USE_NOTIFICATION;
}
