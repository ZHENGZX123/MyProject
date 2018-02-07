package cn.kiway.mdm.zbus;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.activity.Course0Activity;
import cn.kiway.mdm.activity.ResultActivity;
import cn.kiway.mdm.activity.ResultDetailActivity;
import cn.kiway.mdm.activity.StudentGridActivity;
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

            if (command.equals("shangke")) {
                shangke(studentIMEI);
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
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void shangke(String studentIMEI) {
        if (!(KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }

        ((StudentGridActivity) KWApplication.currentActivity).onelineOneStudent(studentIMEI);
    }

    private void qiangda(String studentIMEI) {
        if (!(KWApplication.currentActivity instanceof Course0Activity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }
        ((Course0Activity) KWApplication.currentActivity).qiangdaOneStudent(studentIMEI);
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
