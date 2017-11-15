package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/11/15.
 */

public class SMS {
    public int id;
    public String phone;
    public String content;
    public String time;
    public int froms; //1收到的 2发出的

    public String name;//从call表查找name
}
