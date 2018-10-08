package cn.kiway.mdm.zbus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.rabbitmq.client.Channel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.mdm.util.Utils;

import static cn.kiway.mdm.WXApplication.zbusHost;
import static cn.kiway.mdm.WXApplication.zbusPort;


/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static RabbitMQUtils consumeUtil;
    public static List<Channel> channels = new ArrayList<>();

    public static void closeMQ() {
        if (consumeUtil != null) {
            consumeUtil.close();
        }

        for (Channel channel : channels) {
            try {
                channel.abort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMsg(final Activity c, final String command, final ArrayList<String> students, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "上课";
                    String userId = Utils.getIMEI(c);
                    String msg = new JSONObject().put("data", new JSONObject().put("command", command).put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
                    doSendMsg(c, title, userId, msg, students);
                    if (onListener != null) {
                        onListener.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (onListener != null) {
                        onListener.onFailure();
                    }
                }
            }
        }.start();
    }

    private static void doSendMsg(Context c, final String title, String userId, String msg, ArrayList<String> students) throws
            Exception {
        //topic : 老师的deviceId#userId
        final String topic = Utils.getIMEI(c) + "#" + userId;
        String url = zbusHost + ":" + zbusPort;
        final PushMessageVo pushMessageVo = new PushMessageVo();
        pushMessageVo.setDescription(title);
        pushMessageVo.setTitle(title);
        pushMessageVo.setMessage(msg);
        pushMessageVo.setAppId("f2ec1fb69b27c7ab5260d2eb7cd95dea");
        pushMessageVo.setModule("student");
        Set<String> userIds = new HashSet<>();
        for (String s : students) {
            userIds.add(s);//token
        }
        pushMessageVo.setUserId(userIds);//学生token
        pushMessageVo.setSenderId(userId);//老师的userId
        pushMessageVo.setPushType("zbus");
        Log.e("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Channel channel = consumeUtil.createChannel(topic, topic);
                    consumeUtil.sendMsg(pushMessageVo, channel);
                    channel.abort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
