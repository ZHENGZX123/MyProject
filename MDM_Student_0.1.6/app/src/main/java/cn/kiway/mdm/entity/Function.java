package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2018/1/5.
 */

public class Function {

    public int id;
    public String name;
    public boolean enable;

    public Function(int id, String name, boolean enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }
}
