package cn.kiway.mdm.model;

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
    public int type;//1正式2草稿
    public String createDate;
    public int attendCourse;//0未上课1已上课
    public ArrayList<KnowledgePoint> knowledgePoints;
    public ArrayList<Question> questions;


}
