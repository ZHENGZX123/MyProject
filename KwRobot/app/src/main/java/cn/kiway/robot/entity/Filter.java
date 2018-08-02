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
    public int type;

    public Filter(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Filter(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
