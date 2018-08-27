package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/8/24.
 */

public class GroupPeople {
    public String wxId;
    public String displayname;

    public GroupPeople(String wxId, String displayname) {
        this.wxId = wxId;
        this.displayname = displayname;
    }

    @Override
    public String toString() {
        return "GroupPeople{" +
                "wxId='" + wxId + '\'' +
                ", displayname='" + displayname + '\'' +
                '}';
    }
}
