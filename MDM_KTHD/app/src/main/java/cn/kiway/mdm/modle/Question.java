package cn.kiway.mdm.modle;

/**
 * Created by Administrator on 2018/1/22.
 */

public class Question {

    public int id;
    public String content;
    public int type;//1.文字 2语音
    public int duration; //时长，文字是0
    public String filepath;//录制的声音存放位置
}
