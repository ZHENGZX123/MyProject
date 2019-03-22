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

    public static final int DEFAULT_RELEASE_TIME = 200 * 1000;
    public static final String PASSWORD = "kiwayedukiway123";

    public static final int FLAG_PREACTION = 999;
    public static final int FLAG_ACTION = 9999;


    static {
        //正式
//        host = "robot.kiway.cn";
//        port = 5676;
//        clientUrl = "http://robot.kiway.cn";

        //2019招生版
        host = "zsrobot.kiway.cn";
        port = 25672;
        clientUrl = "http://zsrobot.kiway.cn";

        // 测试
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
        //0830注释
//        qas.put("你好", "家长您好。");
//        qas.put("您好", "家长您好。");
//        qas.put("客服你好", "家长您好。");
//        qas.put("客服您好", "家长您好。");
//        qas.put("客服在吗", "家长您好，请问您要咨询什么问题？");
//        qas.put("谢谢", "不用谢。欢迎下次再咨询。");
//        /*qas.put("[图片]", "暂不支持图片咨询。");*/
//        qas.put("[文件]", "暂不支持文件咨询。");
//        qas.put("[视频]", "暂不支持视频咨询。");
//        qas.put("[语音]", "暂不支持语音咨询。");
//        qas.put("[位置]", "暂不支持位置咨询。");
//        qas.put("[动画表情]", "暂不支持动画表情咨询。");
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
    public static final String AUTO_REPLY_CONTENT_CMD = "autoReplyContentCmd";//网页已经没有了
    public static final String AUTO_REPLY_CONTENT_REPLY_CMD = "autoReplyContentReplyCmd";
    public static final String CHECK_MOMENT_CMD = "checkMomentCmd";//目前后台没有
    public static final String CLEAR_CHAT_HISTORY_CMD = "clearChatHistoryCmd";
    public static final String INTERACT_MOMENT_CMD = "friendCircleCommentCmd";
    public static final String INTERACT_MOMENT_REPLY_CMD = "friendCircleCommentReplyCmd";
    public static final String NOTIFY_RESULT_CMD = "msgToWxNoCmd";
    public static final String SEND_SCRIPT_CMD = "sendScriptCmd";
    public static final String SEND_SCRIPT_REPLY_CMD = "sendScriptReplyCmd";
    public static final String ADD_PUBLIC_ACCOUNT_CMD = "addPublicAccountCmd";
    public static final String ADD_PUBLIC_ACCOUNT_REPLY_CMD = "addPublicAccountReplyCmd";
    public static final String SEARCH_PUBLIC_ACCOUNT_CMD = "searchPublicAccountCmd";
    public static final String SEARCH_PUBLIC_ACCOUNT_REPLY_CMD = "searchPublicAccountReplyCmd";
    public static final String REMOVE_WODI_CMD = "removeUnderCoverCmd";
    public static final String REMOVE_WODI_REPLY_CMD = "removeUnderCoverReplyCmd";
    public static final String SET_WODI_CMD = "setUnderCoverCmd";
    public static final String SET_WODI_REPLY_CMD = "setUnderCoverReplyCmd";
    public static final String SAVE_GROUP_CMD = "saveGroupCmd";
    public static final String SAVE_GROUP_REPLY_CMD = "saveGroupReplyCmd";
    public static final String GROUP_QRCODE_CMD = "createGroupQrcodeCmd";
    public static final String GROUP_QRCODE_REPLY_CMD = "createGroupQrcodeReplyCmd";
    public static final String TRANSFER_MASTER_CMD = "exchangeGroupMasterCmd";
    public static final String TRANSFER_MASTER_REPLY_CMD = "exchangeGroupMasterReplyCmd";
    public static final String TEST_CMD = "testCmd";
    public static final String TEST_REPLY_CMD = "testReplyCmd";
    public static final String BROWSE_MESSAGE_CMD = "browseMessageCmd";//这个后台没有

    public static final String UPDATE_BASEDATA_CMD = "updateBaseDate";
    public static final String INSERT_BASEDATA_CMD = "insertBaseDate";
    public static final String DELETE_BASEDATA_CMD = "deleteBaseDate";

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
        replies.put(INTERACT_MOMENT_CMD, INTERACT_MOMENT_REPLY_CMD);
        replies.put(SEND_SCRIPT_CMD, SEND_SCRIPT_REPLY_CMD);
        replies.put(ADD_PUBLIC_ACCOUNT_CMD, ADD_PUBLIC_ACCOUNT_REPLY_CMD);
        replies.put(SEARCH_PUBLIC_ACCOUNT_CMD, SEARCH_PUBLIC_ACCOUNT_REPLY_CMD);
        replies.put(REMOVE_WODI_CMD, REMOVE_WODI_REPLY_CMD);
        replies.put(SET_WODI_CMD, SET_WODI_REPLY_CMD);
        replies.put(SAVE_GROUP_CMD, SAVE_GROUP_REPLY_CMD);
        replies.put(GROUP_QRCODE_CMD, GROUP_QRCODE_REPLY_CMD);
        replies.put(TRANSFER_MASTER_CMD, TRANSFER_MASTER_REPLY_CMD);

    }

    public static final String BACK_DOOR1 = "开维一本万利";
    public static final String BACK_DOOR2 = "开维前程似锦";

    public static Map<String, Integer> backdoors = new LinkedHashMap<>();

    static {
        backdoors.put(BACK_DOOR1, Action.TYPE_BACK_DOOR);
        backdoors.put(BACK_DOOR2, Action.TYPE_BACK_DOOR);

        backdoors.put(DELETE_FRIEND_CIRCLE_CMD, Action.TYPE_DELETE_MOMENT);
        backdoors.put(SEND_FRIEND_CIRCLE_CMD, Action.TYPE_SEND_MOMENT);
        backdoors.put(ADD_FRIEND_CMD, Action.TYPE_ADD_FRIEND);
        backdoors.put(UPDATE_NICKNAME_CMD, Action.TYPE_FIX_NICKNAME);
        backdoors.put(UPDATE_AVATAR_CMD, Action.TYPE_FIX_ICON);
        backdoors.put(PERSION_NEARBY_CMD, Action.TYPE_NEARBY_PEOPLE);
        backdoors.put(FORGET_FISH_CMD, Action.TYPE_MISSING_FISH);
        backdoors.put(UPDATE_FRIEND_NICKNAME_CMD, Action.TYPE_FIX_FRIEND_NICKNAME);
        backdoors.put(DELETE_FRIEND_CMD, Action.TYPE_DELETE_FRIEND);
        backdoors.put(CREATE_GROUP_CHAT_CMD, Action.TYPE_CREATE_GROUP_CHAT);
        backdoors.put(CHAT_IN_GROUP_CMD, Action.TYPE_CHAT_IN_GROUP);
        backdoors.put(INVITE_GROUP_CMD, Action.TYPE_ADD_GROUP_PEOPLE);
        backdoors.put(TICK_PERSON_GROUP_CMD, Action.TYPE_DELETE_GROUP_PEOPLE);
        backdoors.put(UPDATE_GROUP_NAME_CMD, Action.TYPE_FIX_GROUP_NAME);
        backdoors.put(UPDATE_GROUP_NOTICE_CMD, Action.TYPE_FIX_GROUP_NOTICE);
        backdoors.put(DELETE_GROUP_CMD, Action.TYPE_DELETE_GROUP_CHAT);
        backdoors.put(SEND_BATCH_CMD, Action.TYPE_SEND_BATCH);
        backdoors.put(AT_PERSONS_CMD, Action.TYPE_AT_GROUP_PEOPLE);
        backdoors.put(CHECK_MOMENT_CMD, Action.TYPE_CHECK_MOMENT);
        backdoors.put(CLEAR_CHAT_HISTORY_CMD, Action.TYPE_CLEAR_CHAT_HISTORY);
        backdoors.put(INTERACT_MOMENT_CMD, Action.TYPE_INTERACT_MOMENT);
        backdoors.put(NOTIFY_RESULT_CMD, Action.TYPE_NOTIFY_RESULT);
        backdoors.put(SEND_SCRIPT_CMD, Action.TYPE_SCRIPT);
        backdoors.put(ADD_PUBLIC_ACCOUNT_CMD, Action.TYPE_ADD_PUBLIC_ACCOUNT);
        backdoors.put(SEARCH_PUBLIC_ACCOUNT_CMD, Action.TYPE_SEARCH_PUBLIC_ACCOUNT);
        backdoors.put(SAVE_GROUP_CMD, Action.TYPE_SAVE_GROUP);
        backdoors.put(GROUP_QRCODE_CMD, Action.TYPE_GROUP_QRCODE);
        backdoors.put(TRANSFER_MASTER_CMD, Action.TYPE_TRANSFER_MASTER);
        backdoors.put(UPGRADE_CMD, Action.TYPE_CHECK_NEW_VERSION);
        backdoors.put(UPDATE_BASEDATA_CMD, Action.TYPE_UPDATE_BASEDATA);
        backdoors.put(INSERT_BASEDATA_CMD, Action.TYPE_UPDATE_BASEDATA);
        backdoors.put(DELETE_BASEDATA_CMD, Action.TYPE_UPDATE_BASEDATA);
        backdoors.put(BROWSE_MESSAGE_CMD, Action.TYPE_BROWSER_MESSAGE);

    }

    //机器人角色
    public static final int ROLE_KEFU = 0;
    public static final int ROLE_WODI = 1;

}
