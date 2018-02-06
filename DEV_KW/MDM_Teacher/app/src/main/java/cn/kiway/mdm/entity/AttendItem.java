package cn.kiway.mdm.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/6.
 */

public class AttendItem implements Serializable {

    public int type;
    public String title;
    public String time;

    public ArrayList<KnowledgeCountResult> results;
    public ArrayList<Question> questions;
}
