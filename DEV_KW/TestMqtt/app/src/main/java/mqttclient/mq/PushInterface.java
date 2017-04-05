package mqttclient.mq;

/**
 * Created by Administrator on 2017/1/5.
 */

public interface PushInterface {
    //用户登录
    public String loginKWIM(String username, String password);

    //退出登录
    public String logout();

    //获取当前用户信息
    public String getUserInfo();

    //发送消息（不管单聊，群聊）
    public String sendMessage(String recvid, String msgcontent, String msgtype);

    //撤回信息
    public String recallMessage(String msgid);

    //获取未读信息数量
    public String getUnReadCount();

    //获取未读信息列表
    public String getUnReadMsg(String type, String objid);

    //修改消息状态(修改用户对应该条信息状态为已读)
    public String updateMsgStatus(String msgid);

    //查看消息读取状态
    public String getMsgReadStatus(String msgid);

    //获取会话列表
    public String getRequestSession();

    //会话设置 /取消置顶/删除会话(toptype:1置顶2取消置顶3删除会话)
    public String setRequestSession(String sessionid, String toptype);

    /**********************
     * 好友处理
     *************************************/
    //获取自己的好友列表
    public String getFriendList();

    //查询好友（可用http来代替避免增加Im查询负担）(应该是查询用户)
    public String getFriendInfo(String friendid);

    //模糊查好友（应该是查用户）
    public String searchFriend(String uname);

    //删除好友
    public String deleteFriend(String friendid);

    //根据好友Id请求
    public String addFriend(String friendid, String remark);

    //确认是否
    public String confirmAddFriend(String friendid, String type);

    //获取申请
    public String getAddFriendApply();

    //修改好友信息
    public String editFriendInfo(String friendInfo);

    //更新后台好友/群未读消息
    public String updateUnreadPersonData(String type, String objid);

    /**********************
     * 群处理
     *************************************/
    //发起群聊,选了几个用户之后，就可以创建一个组了。
    public String creatGroup(String groupinfo, String userlist);

    //获取群成员参数
    public String groupMemberList(String groupid);

    //发送请求群组列表
    public String getGroupList();

    //发送请求班级群组列表
    public String getClassGroupList();

    //发送请求群信息
    public String getGroupInfo(String groupid);

    //模糊查群
    public String searchGroup(String groupname);

    //主动加群
    public String addGroup(String groupid, String remark);

    //主动加班级群
    public String addClassGroup(String classid, String remark);

    //群主确认时候添加此人
    public String groupCreatorConfirm(String groupid, String userid, String type);

    //退出群聊
    public String delGroup(String groupid);

    //获取添加进群申请
    public String getAddGroupApply();

    //清除群未读消息
    public String clearGroupUnread(String groupid);

    //发送修改群消息
    public String updateGroupData(String groupinfo);

    //群主解散群
    public String dissolutionGroup(String groupid);

    /**********************
     * 标签处理
     *************************************/
    //获取标签列表
    public String getFlagList();

    //获取标签用户列表
    public String getFlagUserList(String fid);

    //添加标签
    public String addFlagInfo(String fname, String userlist);

    //更新标签
    public String editFlagInfo(String fid, String fname, String userlist);

    //删除标签
    public String delFlagInfo(String fid);

    /**********************
     * 收藏处理
     *************************************/
    //查询收藏
    public String getCollectionList();

    //添加收藏
    public String addCollectionInfo(String collInfo);

    //更新收藏
    public String editCollectionInfo(String collInfo);

    //删除收藏
    public String delCollectionInfo(String fid);
}
