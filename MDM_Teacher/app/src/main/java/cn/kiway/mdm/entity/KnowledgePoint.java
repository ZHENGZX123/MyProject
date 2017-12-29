package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2017/12/29.
 */

public class KnowledgePoint {

    public static final int TYPE_END = -1;//结束课程
    public static final int TYPE0 = 0;//文字图片
    public static final int TYPE1 = 1;//doc ppt

    public int id;
    public String title;
    public int type;
}
