package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/4/16.
 */

public class ZbusRecv {

    public String msg;
    public boolean realReply;

    public ZbusRecv(String msg, boolean realReply) {
        this.msg = msg;
        this.realReply = realReply;
    }

    @Override
    public String toString() {
        return "ZbusRecv{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
