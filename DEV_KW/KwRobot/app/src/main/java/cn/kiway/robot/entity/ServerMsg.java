package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/10/22.
 */

public class ServerMsg {

    public static final int ACTION_STATUS_0 = 0;
    public static final int ACTION_STATUS_1 = 1;
    public static final int ACTION_STATUS_2 = 2;
    public static final int ACTION_STATUS_3 = 3;

    public static final int TYPE_MQ = 1;
    public static final int TYPE_HTTP = 2;

    public int id;
    public int index;
    public String content;
    public String replyContent;
    public int status; //0默认   1.执行中  ,  2执行完，上传失败(未上传)  3，执行完，上传成功

    public long time;
    public int type;//1MQ  2HTTP

    public ServerMsg(int index, String content, String replyContent, int status, long time, int type) {
        this.index = index;
        this.content = content;
        this.replyContent = replyContent;
        this.status = status;
        this.time = time;
        this.type = type;
    }

    public ServerMsg(int id, int index, String content, String replyContent, int status, long time, int type) {
        this.id = id;
        this.index = index;
        this.content = content;
        this.replyContent = replyContent;
        this.status = status;
        this.time = time;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ServerMsg{" +
                "id=" + id +
                ", index=" + index +
                ", status=" + status +
                ", type=" + type +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", replyContent='" + replyContent + '\'' +
                '}';
    }
}
