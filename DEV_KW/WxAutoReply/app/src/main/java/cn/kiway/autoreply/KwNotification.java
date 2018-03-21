package cn.kiway.autoreply;

import android.app.PendingIntent;

/**
 * Created by Administrator on 2018/3/21.
 */

public class KwNotification {
    public PendingIntent pi;
    public String senderName;
    public String senderContent;

    public KwNotification(String senderName, String senderContent, PendingIntent pi) {
        this.pi = pi;
        this.senderName = senderName;
        this.senderContent = senderContent;
    }
}
