package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/9/1.
 */

public class Command {

    public String command;
    public String SSID;
    public String password;

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", SSID='" + SSID + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
