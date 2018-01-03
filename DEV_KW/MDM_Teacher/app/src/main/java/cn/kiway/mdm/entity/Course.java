package cn.kiway.mdm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Course implements Serializable {

    public String id;
    public String name;
    public String attachInfo;//附件的info，是数组
    public String attach;//附件的url，用逗号隔开
    public String userId;
    public int type;//1未上课 2已上课
    public String createDate;
    public int attendCourse;
    public ArrayList<KnowledgePoint> knowledgePoints;
}
