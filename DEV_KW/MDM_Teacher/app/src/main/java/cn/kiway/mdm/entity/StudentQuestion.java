package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2018/1/22.
 */

public class StudentQuestion {

    public int type;//1文字2语音
    public String content;//文字内容
    public int duration;//音频长度
    public String filepath;//音频文件位置
    public String time;//发送时间
    public String studentName;//发送人名字
    public String studentAvatar;//发送人头像
    public String className;//班级名字

    public StudentQuestion(int type, String content, int duration, String filepath, String time, String studentName, String studentAvatar, String className) {
        this.type = type;
        this.content = content;
        this.duration = duration;
        this.filepath = filepath;
        this.time = time;
        this.studentName = studentName;
        this.studentAvatar = studentAvatar;
        this.className = className;
    }
}
