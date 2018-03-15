package cn.kiway.mdm.zbus;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.Course0Activity;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.ResultActivity;
import cn.kiway.mdm.activity.ResultDetailActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
import cn.kiway.mdm.entity.Student;
import io.zbus.mq.Message;
import io.zbus.mq.MessageHandler;
import io.zbus.mq.MqClient;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler implements MessageHandler {

    @Override
    public void handle(Message message, MqClient mqClient) throws IOException {
        String temp = message.getBodyString();
        Log.d("test", "getBodyString = " + temp);
        //{"studentIMEI":"890406562707861","command":"sign"}
        try {
            String msg = new JSONObject(temp).optString("message");
            JSONObject o = new JSONObject(msg);
            String command = o.optString("command");
            String studentIMEI = o.optString("studentIMEI");

            if (command.equals("hello")) {
                hello(studentIMEI);
            } else if (command.equals("shangke")) {
                shangke(studentIMEI);
            } else if (command.startsWith("chaxun")) {
                chaxun(studentIMEI, command.split("_")[1], command.split("_")[2]);
            } else if (command.equals("sign")) {
                sign(studentIMEI);
            } else if (command.equals("tongji_know")) {
                tongji(studentIMEI, 1);
            } else if (command.equals("tongji_unknow")) {
                tongji(studentIMEI, 0);
            } else if (command.equals("qiangda")) {
                qiangda(studentIMEI);
            } else if (command.startsWith("answer")) {
                answer(studentIMEI, command.split("_")[1]);
            } else if (command.startsWith("question")) {
                question(studentIMEI, command.split("_")[1]);
            } else if (command.equals("heartbeat1")) {
                heartbeat();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void heartbeat() {
        if (KWApplication.currentActivity == null) {
            return;
        }
        ZbusHost.heartbeat(KWApplication.currentActivity, "heartbeat2");
    }

    private void chaxun(String studentIMEI, String type, String status) {
        if (!(KWApplication.currentActivity instanceof StudentGridActivity || KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }

        ((StudentGridActivity) KWApplication.currentActivity).chaxunOneSubmit(studentIMEI, Integer.parseInt(type), Integer.parseInt(status));
    }

    private void question(String studentIMEI, String question) {
        if (!(KWApplication.currentActivity instanceof Course0Activity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }
        ((Course0Activity) KWApplication.currentActivity).questiOneStudent(studentIMEI, question);
    }

    private void answer(String studentIMEI, String answer) {
        if (!(KWApplication.currentActivity instanceof ResultActivity || KWApplication.currentActivity instanceof ResultDetailActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }

        ((ResultActivity) KWApplication.currentActivity).getOneSubmit(studentIMEI, answer);
    }

    private void hello(String studentIMEI) {
        if (MainActivity.instance == null) {
            return;
        }
        for (Student s : KWApplication.students) {
            if (s.imei.equals(studentIMEI)) {
                ArrayList<Student> temp = new ArrayList<>();
                temp.add(s);
                ZbusHost.shangke(MainActivity.instance, "shangke", temp, null);
                return;
            }
        }
    }

    private void shangke(String studentIMEI) {
        for (Student s : KWApplication.students) {
            if (s.imei.equals(studentIMEI)) {
                s.online = true;
            }
        }
        if (!(KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }
        ((StudentGridActivity) KWApplication.currentActivity).onelineOneStudent(studentIMEI);
    }

    private void qiangda(String studentIMEI) {
        if (KWApplication.currentActivity instanceof Course0Activity) {
            ((Course0Activity) KWApplication.currentActivity).qiangdaOneStudent(studentIMEI);
        } else if (KWApplication.currentActivity instanceof ResultActivity) {
            ((ResultActivity) KWApplication.currentActivity).qiangdaOneStudent(studentIMEI);
        } else {
            Log.d("test", "不是当前页面，信息可能已过期");
        }
    }

    private void tongji(String studentIMEI, int known) {
        if (!(KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }
        ((StudentGridActivity) KWApplication.currentActivity).knowOneStudent(studentIMEI, known);
    }

    private void sign(String studentIMEI) {
        if (!(KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }
        ((StudentGridActivity) KWApplication.currentActivity).signOneStudent(studentIMEI);
    }


}
