package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/12.
 */

public class AnswerVo implements Serializable {

    public String questionId;
    public String id;
    public String content;
    public String img;
    public String createDate;

    @Override
    public String toString() {
        return "AnswerVo{" +
                "questionId='" + questionId + '\'' +
                ", id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
