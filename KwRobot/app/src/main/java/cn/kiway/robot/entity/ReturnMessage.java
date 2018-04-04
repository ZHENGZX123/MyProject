package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/4/4.
 */

public class ReturnMessage {

    public int returnType;
    public String content;
    public boolean returnFinished;

    public ReturnMessage(int returnType, String content) {
        this.returnType = returnType;
        this.content = content;
    }

    public ReturnMessage() {
    }
}
