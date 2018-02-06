package cn.kiway.mdm.entity;

import java.io.Serializable;
import java.util.ArrayList;

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
    public String operation;//无用
    public String createDate;
    public AnswerVo answerVo;
    public UserExaminationResultVo userExaminationResultVo;//测评有用
    public ArrayList<UserExaminationResultVo> userExaminationResultVos;//其他问答有用

    public boolean selected;
    public String studentAnswer;
    public int teacherJudge; // 未批改0  错误1  正确2

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
                ", userExaminationResultVo=" + userExaminationResultVo +
                ", userExaminationResultVos=" + userExaminationResultVos +
                '}';
    }
}
