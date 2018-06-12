package com.android.kiway.zbus;

import android.content.Context;
import android.util.Log;

import com.android.kiway.entity.Teacher;
import com.android.kiway.utils.Constant;
import com.android.kiway.utils.MyDBHelper;
import com.android.kiway.utils.Utils;
import com.rabbitmq.client.Channel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.wx.reply.utils.RabbitMQUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;

import static com.android.kiway.utils.Constant.APPID;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static RabbitMQUtils consumeUtil;
    public static List<Channel> channels = new ArrayList<>();

    public static void closeMQ() {
        if (consumeUtil != null) {
            consumeUtil.close();
            consumeUtil=null;
        }
        for (Channel channel : channels) {
            try {
                channel.abort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean doSendMsg(Context c, String cmd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("studentIMEI", Utils.getIMEI(c));
            obj.put("command", cmd);

            String title = "标题";
            String desc = "描述";
            final String msg = obj.toString();

            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            //topic : 上报的 deviceId#userId
            final String topic = Utils.getIMEI(c) + "#" + token;

            final PushMessageVo pushMessageVo = new PushMessageVo();
            pushMessageVo.setTitle(title);
            pushMessageVo.setDescription(desc);
            pushMessageVo.setMessage(msg);
            pushMessageVo.setAppId(APPID);
            pushMessageVo.setModule("student");
            Set<String> userIds = new HashSet<>();
            userIds.add(Constant.currentTeacher);
            pushMessageVo.setUserId(userIds);//老师的userid
            pushMessageVo.setSenderId(token);//学生的token
            pushMessageVo.setPushType("zbus");

            Log.d("test", "sendMSG " + msg.toString());
            //ZbusUtils.sendMsg(topic, pushMessageVo);

            new Thread() {
                @Override
                public void run() {
                   Channel channel = consumeUtil.createChannel(topic, topic);
                    try {
                        consumeUtil.sendMsg(pushMessageVo, channel);
                        channel.abort();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "doSendMsg e = " + e.toString());
        }

        return false;
    }

    public static boolean doSendMsg2(Context c, String cmd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("studentIMEI", Utils.getIMEI(c));
            obj.put("command", cmd);

            String title = "标题";
            String desc = "描述";
            final String msg = obj.toString();

            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            //topic : 上报的 deviceId#userId
            final String topic = Utils.getIMEI(c) + "#" + token;
            Log.d("test", "发送topic = " + topic);
            final PushMessageVo pushMessageVo = new PushMessageVo();
            pushMessageVo.setTitle(title);
            pushMessageVo.setDescription(desc);
            pushMessageVo.setMessage(msg);
            pushMessageVo.setAppId(APPID);
            pushMessageVo.setModule("student");

            //给教过自己的老师，都发一下
            ArrayList<Teacher> teachers = new MyDBHelper(c).getAllTeachers();
            if (teachers.size() == 0) {
                return true;
            }
            Set<String> userIds = new HashSet<>();
            for (Teacher t : teachers) {
                userIds.add(t.teacherID);
            }
            pushMessageVo.setUserId(userIds);//老师的userid
            pushMessageVo.setSenderId(token);//学生的token
            pushMessageVo.setPushType("zbus");
            Log.d("test", "sendMSG2 " + msg.toString());
            new Thread() {
                @Override
                public void run() {
                     Channel  channel = consumeUtil.createChannel(topic, topic);
                    try {
                        consumeUtil.sendMsg(pushMessageVo, channel);
                        channel.abort();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "doSendMsg2 e = " + e.toString());
        }

        return false;
    }
}
