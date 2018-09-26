package cn.kiway.mdm.zbus;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.kiway.mdm.activity.MainActivity;

import static cn.kiway.mdm.activity.MainActivity.sendCommandCallback;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler {

    public ZbusMessageHandler() {
    }

    public void handle(String temp) throws IOException {
        Log.d("test", "getBodyString = " + temp);
        //{"studentIMEI":"890406562707861","command":"online"}
        //{"studentIMEI":"890406562707861","command":"snapshot_url"}
        try {
            String msg = new JSONObject(temp).optString("message");
            JSONObject o = new JSONObject(msg);
            final String command = o.optString("command");
            final String studentIMEI = o.optString("studentIMEI");
            if (command.startsWith("online") || command.startsWith("snapshot")) {
                MainActivity.instance.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.instance.wv.loadUrl(sendCommandCallback.replace("studentIMEI", studentIMEI).replace("command", command));
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
