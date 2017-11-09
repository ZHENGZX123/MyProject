package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/11/9.
 */

public class Call {

    public String id;
    public String name;//名字
    public String number;//号码
    public int type;//黑名单还是白名单
    public int in_out;//打入还是打出  //1打入 2打出

    @Override
    public String toString() {
        return "Call{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", in_out=" + in_out +
                '}';
    }
}
