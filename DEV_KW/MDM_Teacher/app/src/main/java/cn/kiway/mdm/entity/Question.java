package cn.kiway.mdm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class Question implements Serializable {

    public String courseId;
    public String content;
    public String img;
    public String options;//选择题。。
    public String id;
    public int type;//(1:选择 2:问答 3:填空 4:判断 5:主观题)
    public String operation;//
    public String createDate;
    public AnswerVo answerVo;
    public UserExaminationResultVo userExaminationResultVo;//
    public List<UserExaminationResultVo> userExaminationResultVos;//

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
