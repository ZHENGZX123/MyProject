package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/11/9.
 */

public class Call {

    public String id;
    public String name;//名字
    public String phone;//号码
    public int type;    //1白名单2黑名单
    public int froms;  //打入还是打出  //1打入 2打出
    public int enable; //1启用0不启用
    public String operation;

    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", type=" + type +
                ", froms=" + froms +
                ", enable=" + enable +
                '}';
    }
}
