package cn.kiway.autoreply;

import android.app.PendingIntent;

/**
 * Created by Administrator on 2018/3/21.
 */

public class Action {

    public static final int TYPE_TXT = 0;
    public static final int TYPE_IMG = 1;

    public PendingIntent intent;
    public String sender;
    public String content;
    public String reply;
    public int type;

    public String sendTime;
    public String replyTime;

}
