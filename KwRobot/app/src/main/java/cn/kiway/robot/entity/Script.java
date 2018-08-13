package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/8/3.
 */

public class Script {

    public String content;
    public int time;
    public int type;

    public Script(String content, int time , int type) {
        this.content = content;
        this.time = time;
        this.type = type;
    }
}
