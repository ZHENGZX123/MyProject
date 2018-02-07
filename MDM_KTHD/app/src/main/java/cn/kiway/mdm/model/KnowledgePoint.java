package cn.kiway.mdm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/29.
 */

public class KnowledgePoint implements Serializable {

    public static final int TYPE0 = 0;  //标准知识点
    public static final int TYPE_DOC = 1;  //附件doc ppt
    public static final int TYPE_END = 2;//结束按钮

    public String id;
    public String courseId;
    public String content;
    public String createDate;
    public TeachingContentVo teachingContentVo;

    public int type;//我自己用的。。。
    public boolean selected;//我自己用的。。。

    @Override
    public String toString() {
        return "KnowledgePoint{" +
                "id='" + id + '\'' +
                ", courseId='" + courseId + '\'' +
                ", content='" + content + '\'' +
                ", createDate='" + createDate + '\'' +
                ", teachingContentVo=" + teachingContentVo +
                ", type=" + type +
                ", selected=" + selected +
                '}';
    }
}
