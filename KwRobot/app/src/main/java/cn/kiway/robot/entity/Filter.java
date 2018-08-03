package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/8/2.
 */

public class Filter {

    public static int TYPE_TRANSFER = 0;
    public static int TYPE_WODI = 1;
    public static int TYPE_OTHER = 2;

    public int id;
    public String name;
    public String wxNo;
    public int type;


    public Filter(String name, String wxNo, int type) {
        this.name = name;
        this.wxNo = wxNo;
        this.type = type;
    }

    public Filter(int id, String name, String wxNo, int type) {
        this.id = id;
        this.name = name;
        this.wxNo = wxNo;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "name='" + name + '\'' +
                ", wxNo='" + wxNo + '\'' +
                ", type=" + type +
                '}';
    }
}
