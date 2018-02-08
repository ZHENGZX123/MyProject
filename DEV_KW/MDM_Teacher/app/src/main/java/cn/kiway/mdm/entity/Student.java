package cn.kiway.mdm.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/28.
 */

public class Student implements Serializable {

    public String id;
    public String name;
    public String classId;
    public String mobileBrand;
    public String studentNumber;
    public String mobileModel;
    public String schoolId;
    public String platform;
    public String token;
    public String defaultPassword;
    public String lastLoginDate;
    public String createDate;
    public String imei;
    public String avatar;

    public boolean online;//0没在线 1在线了
    public boolean come; //0没到 1到了
    public int known = 2;//0没明白，1是明白，未回复是2
    public boolean selected;
    public boolean locked;//锁定
    public boolean submited;//是否提交答案
    public boolean collected;//是否已经批改
}
