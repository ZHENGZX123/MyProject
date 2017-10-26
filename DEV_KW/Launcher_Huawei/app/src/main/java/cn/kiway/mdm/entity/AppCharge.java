package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/10/26.
 */

public class AppCharge {

    public String id;
    public String name;
    public int type;
    public String timeRange;
    public String version;
    public String packages;
    public String url;

    @Override
    public String toString() {
        return "AppCharge{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", timeRange='" + timeRange + '\'' +
                ", version='" + version + '\'' +
                ", packages='" + packages + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
