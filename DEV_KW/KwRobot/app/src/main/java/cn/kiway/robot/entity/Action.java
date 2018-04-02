package cn.kiway.robot.entity;

import android.app.PendingIntent;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Action {

    public static final int TYPE_TXT = 1;
    public static final int TYPE_IMAGE = 2;
    public static final int TYPE_LINK = 99;//需要转发到朋友圈
    public static final int TYPE_TRANSMIT = 100;//需要转发给指定的人
    public static final int TYPE_SET_FORWARDING = 101;//设置转发对象
    
    public static final int TYPE_TEST = 9999;

    public PendingIntent intent;
    public String sender;
    public String content;
    public String reply;
    public int receiveType;
    public boolean uploaded;//图片

}
