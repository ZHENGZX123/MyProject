package cn.kiway.autoreply.entity;

import android.app.PendingIntent;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Action {

    public static final int TYPE_TXT = 0;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_TEST = 9999;

    public PendingIntent intent;
    public String sender;
    public String content;
    public String reply;
    public int receiveType;
    public boolean uploaded;

    public String sendTime;
    public String replyTime;

}
