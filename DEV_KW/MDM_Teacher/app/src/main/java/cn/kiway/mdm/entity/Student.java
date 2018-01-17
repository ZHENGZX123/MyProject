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
    
    public boolean come; //0没到 1到了
    public int known;
    public boolean submit;
    public boolean selected;
    public boolean locked;//锁定


}
