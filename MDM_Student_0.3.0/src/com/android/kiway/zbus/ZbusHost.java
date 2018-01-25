package com.android.kiway.zbus;

import android.content.Context;
import android.util.Log;

import com.android.kiway.utils.Utils;

import org.json.JSONObject;

import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import cn.kiway.web.kthd.zbus.vo.PushRecordVo;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static String zbusHost = "192.168.8.161";
    public static String zbusPost = "15555";
    public static String teacherTopic = "";


    public static boolean doSendMsg(Context c, String cmd) {
        try {
            JSONObject obj = new JSONObject();
            obj.put("studentIMEI", Utils.getIMEI(c));
            obj.put("command", cmd);
            PushRecordVo vo = new PushRecordVo();
            vo.setTitle("课堂互动");
            vo.setUserType(2);//发送方：1老师 2学生 3家长 4管理员
            vo.setSenderId(Utils.getIMEI(c));
            vo.setMessage(obj.toString());
            Log.d("test", "发送给老师 = " + teacherTopic);
            ZbusUtils.sendMsg(teacherTopic, vo);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
