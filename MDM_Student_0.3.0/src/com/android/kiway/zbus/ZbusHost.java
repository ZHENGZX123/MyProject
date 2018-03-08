package com.android.kiway.zbus;

import android.content.Context;
import android.util.Log;

import com.android.kiway.utils.Constant;
import com.android.kiway.utils.Utils;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import cn.kiway.zbus.utils.ZbusUtils;
import cn.kiway.zbus.vo.PushMessageVo;

import static com.android.kiway.utils.Constant.APPID;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {

    public static boolean doSendMsg(Context c, String cmd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("studentIMEI", Utils.getIMEI(c));
            obj.put("command", cmd);

            String title = "title";
            String desc = "desc";
            String msg = obj.toString();

            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            //final String topic, String message, final String url, final PushMessageVo vo
            //topic : 上报的 deviceId#userId
            String topic = Utils.getIMEI(c) + "#" + token;
            PushMessageVo pushMessageVo = new PushMessageVo();
            pushMessageVo.setTitle(title);
            pushMessageVo.setDescription(desc);
            pushMessageVo.setMessage(msg);
            pushMessageVo.setAppId(APPID);
            pushMessageVo.setModule("student");
            Set<String> userIds = new HashSet<>();
            userIds.add(Constant.teacherUserId);
            pushMessageVo.setUserId(userIds);//老师的userid
            pushMessageVo.setSenderId(token);//学生的token
            pushMessageVo.setPushType("zbus");

            Log.d("test", "sendMSG " + msg.toString());
            ZbusUtils.sendMsg(topic, pushMessageVo);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "doSendMsg e = " + e.toString());
        }

        return false;
    }
}
