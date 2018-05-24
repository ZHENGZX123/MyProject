package cn.kiway.robot.entity;

/**
 * Created by Administrator on 2018/5/21.
 */

public class AddFriend {

    public static final int STATUS_NOT_ADD = 0;
    public static final int STATUS_ADDING = 1;//添加中，未回复
    public static final int STATUS_ADD_SUCCESS = 2;//对方同意，添加成功

    public static final int STATUS_NOT_EXISTED = 3;//该用户不存在


    public int id;
    public String requesttime;
    public String phone;
    public String remark;
    public int status;

    @Override
    public String toString() {
        return "AddFriend{" +
                "id=" + id +
                ", requesttime='" + requesttime + '\'' +
                ", phone='" + phone + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                '}';
    }
}
