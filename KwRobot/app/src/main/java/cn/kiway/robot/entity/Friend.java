package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/5/25.
 */

public class Friend {

    public String nickname;
    public String remark;
    public String wxId;
    public String wxNo;

    public Friend(String nickname, String remark, String wxId, String wxNo) {
        this.nickname = nickname;
        this.remark = remark;
        this.wxId = wxId;
        this.wxNo = wxNo;
    }
}
