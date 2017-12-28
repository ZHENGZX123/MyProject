package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Course implements Serializable {

    public int id;
    public String title1;
    public String title2;
    public int status; //0未上课 1上课中 2已上课
}
