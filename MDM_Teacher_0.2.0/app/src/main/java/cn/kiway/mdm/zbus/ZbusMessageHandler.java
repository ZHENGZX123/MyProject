package cn.kiway.mdm.zbus;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import cn.kiway.mdm.activity.MainActivity;

import static cn.kiway.mdm.activity.MainActivity.sendCommandCallback;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusMessageHandler {

    public static JSONArray callbackArray;

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
            if (command.startsWith("online")) {
                JSONObject student = new JSONObject().put("imei", studentIMEI);
                callbackArray.put(student);
            } else if (command.startsWith("snapshot")) {
                JSONObject student = new JSONObject().put("imei", studentIMEI).put("url", command.substring(command.indexOf("_") + 1));
                callbackArray.put(student);

                final JSONObject obj = new JSONObject();
                obj.put("snapshot", callbackArray);
                MainActivity.instance.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String js = sendCommandCallback.replace("callbackString", obj.toString());
                        Log.d("test", "js = " + js);
                        MainActivity.instance.wv.loadUrl(js);
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
