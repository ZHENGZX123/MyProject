package cn.kiway.robot.entity;

import android.app.PendingIntent;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Action {

    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_LINK = 99;//需要转发到朋友圈
    public static final int TYPE_TRANSMIT = 100;//需要转发给指定的人
    public static final int TYPE_SET_FORWARDING = 101;//设置消息转发对象
    public static final int TYPE_SET_REMARK = 102;//设置朋友圈转发备注
    public static final int TYPE_REQUEST_FRIEND = 103;//好友请求
    public static final int TYPE_TEST = 9999;

    public PendingIntent intent;
    public String sender;
    public String content;
    public ArrayList<ReturnMessage> returnMessages = new ArrayList<>();
    public int receiveType;
    public boolean uploaded;//图片


}
