package com.android.kiway.zbus;

import android.content.Context;
import android.util.Log;

import com.android.kiway.utils.Utils;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.kiway.zbus.utils.ZbusUtils;
import cn.kiway.zbus.vo.PushMessageVo;

import static com.android.kiway.utils.HttpUtil.APPID;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static String zbusHost = "192.168.8.161";
    public static String zbusPost = "15556";//15555
    public static String teacherUserId = "";


    public static boolean doSendMsg(Context c, String cmd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("studentIMEI", Utils.getIMEI(c));
            obj.put("command", cmd);

            String title = "标题";
            String msg = obj.toString();

            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            //final String topic, String message, final String url, final PushMessageVo vo
            //topic : 上报的 deviceId#userId
            String topic = Utils.getIMEI(c) + "#" + token;
            String url = zbusHost + ":" + zbusPost;
            PushMessageVo pushMessageVo = new PushMessageVo();
            pushMessageVo.setDescription(title);
            pushMessageVo.setTitle(title);
            pushMessageVo.setMessage(msg);
            pushMessageVo.setAppId(APPID);
            pushMessageVo.setModule("student");
            Set<String> userIds = new HashSet<>();
            userIds.add(teacherUserId);
            pushMessageVo.setUserId(userIds);//老师的userid
            pushMessageVo.setSenderId(token);//学生的token
            pushMessageVo.setPushType("zbus");

            ZbusUtils.sendMsg(topic, url, pushMessageVo);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "doSendMsg e = " + e.toString());
        }

        return false;
    }
}
