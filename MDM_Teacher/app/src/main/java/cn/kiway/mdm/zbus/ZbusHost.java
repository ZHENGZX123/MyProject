package cn.kiway.mdm.zbus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.Student;
import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import cn.kiway.web.kthd.zbus.vo.PushRecordVo;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static String zbusHost = "192.168.8.161";
    public static String zbusPost = "15555";


    public static void shangke(Activity c, OnListener onListener) {
        try {
            for (Student s : KWApplication.students) {
                String title = "上课";
                String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
                String msg = new JSONObject().put("data", new JSONObject().put("command", "shangke").put("ip", "").put("platform", "android")).toString();
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
                if (onListener != null) {
                    onListener.onSuccess();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void xiake(Context c, OnListener onListener) {
        try {
            for (Student s : KWApplication.students) {
                String title = "下课";
                String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
                String msg = new JSONObject().put("data", new JSONObject().put("command", "xiake").put("ip", "").put("platform", "android")).toString();
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
                if (onListener != null) {
                    onListener.onSuccess();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    private static void doSendMsg(String title, String userId, String msg, String studentTopic) throws
            Exception {
        PushRecordVo vo = new PushRecordVo();
        vo.setTitle(title);
        vo.setUserType(1);//发送方：1老师 2学生 3家长 4管理员
        vo.setSenderId(userId);
        vo.setMessage(msg);
        Log.d("test", "发送给学生 = " + studentTopic);
        ZbusUtils.sendMsg(studentTopic, vo);
    }
}
