package cn.kiway.mdm;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.huawei.android.pushagent.api.PushManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/6/9.
 */

public class KWApp extends Application {

    public static KWApp instance;
    public static final String server = "http://192.168.8.161:8082/mdms/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        CrashHandler.getInstance().init(this);
        huaweiPush();
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.d("huawei", "try to get Token ,current packageName is " + this.getPackageName());
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String imei = Utils.getIMEI(getApplicationContext());
                installationPush(msg.obj.toString(), imei);
            }
        }
    };

    public void installationPush(final String token, final String imei) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            Log.d("test", "huaweitoken = " + token);
            JSONObject param = new JSONObject();
            param.put("appId", "c77b6c47dbcee47d7ffbc9461da0c82a");
            param.put("type", Build.TYPE);
            param.put("deviceId", token);
            param.put("userId", imei);
            param.put("module", "student");
            Log.d("test", "param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = server + "push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }
}
