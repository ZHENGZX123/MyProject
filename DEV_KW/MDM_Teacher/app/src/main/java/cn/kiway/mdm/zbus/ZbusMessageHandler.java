package cn.kiway.mdm.zbus;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.kiway.mdm.KWApplication;
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
        String msg = message.getBodyString();
        Log.d("test", "getBodyString = " + message.getBodyString());

        //{"student":"890406562707861","command":"sign"}
        try {
            JSONObject o = new JSONObject(msg);
            String command = o.optString("command");
            String student = o.optString("student");

            if (command.equals("sign")) {
                sign(student);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sign(String student) {
        if (!(KWApplication.currentActivity instanceof StudentGridActivity)) {
            Log.d("test", "不是当前页面，信息可能已过期");
            return;
        }

        ((StudentGridActivity) KWApplication.currentActivity).signStudent(student);
    }
}
