package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/10/26.
 */

public class Wifi {

    public String id;
    public String name;
    public String password;
    public String timeRange;
    public int level;

    @Override
    public String toString() {
        return "Wifi{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", timeRange='" + timeRange + '\'' +
                ", level=" + level +
                '}';
    }
}
