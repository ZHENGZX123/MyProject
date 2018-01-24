package cn.kiway.mdm.zbus;

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


    public static void shangke(Context c) {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (Student s : KWApplication.students) {
                        PushRecordVo vo = new PushRecordVo();
                        vo.setTitle("上课");
                        vo.setUserType(1);//发送方：1老师 2学生 3家长 4管理员
                        vo.setSenderId(c.getSharedPreferences("kiway", 0).getString("userId", ""));
                        vo.setMessage(new JSONObject().put("data", new JSONObject().put("command", "shangke").put("ip", "").put("platform", "android")).toString());
                        String studentTopic = "kiwayMDM_student_" + s.imei;
                        Log.d("test", "发送给学生 = " + studentTopic);
                        ZbusUtils.sendMsg(studentTopic, vo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void xiake(Context c) {
        new Thread() {
            @Override
            public void run() {
                try {
                    for (Student s : KWApplication.students) {
                        PushRecordVo vo = new PushRecordVo();
                        vo.setTitle("下课");
                        vo.setUserType(1);//发送方：1老师 2学生 3家长 4管理员
                        vo.setSenderId(c.getSharedPreferences("kiway", 0).getString("userId", ""));
                        vo.setMessage(new JSONObject().put("data", new JSONObject().put("command", "xiake").put("ip", "").put("platform", "android")).toString());
                        String studentTopic = "kiwayMDM_student_" + s.imei;
                        Log.d("test", "发送给学生 = " + studentTopic);
                        ZbusUtils.sendMsg(studentTopic, vo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
