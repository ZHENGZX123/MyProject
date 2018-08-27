package cn.kiway.robot.entity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/5/29.
 */

public class Group {

    public String clientGroupId;
    public String groupName;
    public int type;
    public String master;

    public ArrayList<GroupPeople> peoples;

    public Group(String clientGroupId, String groupName, int type, String master) {
        this.clientGroupId = clientGroupId;
        this.groupName = groupName;
        this.type = type;
        this.master = master;
    }

    @Override
    public String toString() {
        return "Group{" +
                "clientGroupId='" + clientGroupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", type=" + type +
                ", master='" + master + '\'' +
                '}';
    }
}
