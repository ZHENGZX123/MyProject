package cn.kiway.mdm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/3.
 */

public class Question implements Serializable {

    public static final int TYPE_SINGLE = 1;
    public static final int TYPE_MULTI = 2;
    public static final int TYPE_EMPTY = 3;
    public static final int TYPE_JUDGE = 4;
    public static final int TYPE_ESSAY = 5;

    public String courseId;
    public String content;
    public String img;
    public String options;//选择题用到
    public String id;
    public int type;    // 1是单选题 2是多选题 3填空题  4判断题  5问答题
    public String operation;//
    public String createDate;
    public AnswerVo answerVo;

    public String studentAnswer = "";//学生做题的答案

    @Override
    public String toString() {
        return "Question{" +
                "courseId='" + courseId + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", options='" + options + '\'' +
                ", id='" + id + '\'' +
                ", type=" + type +
                ", operation='" + operation + '\'' +
                ", createDate='" + createDate + '\'' +
                ", answerVo=" + answerVo +
                '}';
    }
}
