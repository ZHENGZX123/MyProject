package cn.kiway.mdm.model;

/**
 * Created by Administrator on 2017/12/6.
 */

public class IpModel {
   public String ip;
    public String className;
    public String platform;
    public long time;

    @Override
    public String toString() {
        return "IpModel{" +
                "ip='" + ip + '\'' +
                ", className='" + className + '\'' +
                ", platform='" + platform + '\'' +
                ", time=" + time +
                '}';
    }
}
