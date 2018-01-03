package cn.kiway.mdm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/29.
 */

public class KnowledgePoint implements Serializable {

    public static final int TYPE0 = 0;  //标准知识点
    public static final int TYPE1 = 1;  //附件doc ppt
    public static final int TYPE_END = 2;//结束按钮

    public String id;
    public String courseId;
    public String content;
    public String img;
    public String createDate;
    public String questions;
    public ArrayList<TeachingContentVO> teachingContentVo;

    public int type;
}
