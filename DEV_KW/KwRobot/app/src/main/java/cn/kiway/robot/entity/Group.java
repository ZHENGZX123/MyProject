package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/5/29.
 */

public class Group {

    public String clientGroupId;
    public String groupName;

    public Group(String clientGroupId, String groupName) {
        this.clientGroupId = clientGroupId;
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Group{" +
                "clientGroupId='" + clientGroupId + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
