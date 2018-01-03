package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/29.
 */

public class KnowledgePoint implements Serializable {

    public static final int TYPE_END = -1;//结束课程
    public static final int TYPE0 = 0;//文字图片
    public static final int TYPE1 = 1;//doc ppt

    public String id;
    public String courseId;
    public String content;
    public String img;
    public String createDate;
    public String questions;
    public String teachingContentVo;


    public int type;
}
