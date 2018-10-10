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
    public String masterWxNo;


    public ArrayList<GroupPeople> peoples;

    public Group(String clientGroupId, String groupName) {
        this.clientGroupId = clientGroupId;
        this.groupName = groupName;
    }

    public Group(String clientGroupId, String groupName, int type, String master, String masterWxNo) {
        this.clientGroupId = clientGroupId;
        this.groupName = groupName;
        this.type = type;
        this.master = master;
        this.masterWxNo = masterWxNo;
    }

    @Override
    public String toString() {
        return "Group{" +
                "clientGroupId='" + clientGroupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", type=" + type +
                ", master='" + master + '\'' +
                ", masterWxNo='" + masterWxNo + '\'' +
                '}';
    }
}
