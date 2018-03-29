package cn.kiway.autoreply.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easy.wtool.sdk.MessageEvent;
import com.easy.wtool.sdk.OnMessageListener;
import com.easy.wtool.sdk.OnTaskEndListener;
import com.easy.wtool.sdk.TaskEndEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.service.AutoReplyService;
import cn.kiway.autoreply.util.WxUtils;

import static cn.kiway.autoreply.entity.Action.TYPE_TEST;
import static cn.kiway.autoreply.util.WxUtils.wxRoomId1;
import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private Button star;

    public static final int MSG_NETWORK_OK = 1;
    public static final int MSG_NETWORK_ERR = 2;
    public static final int MSG_HEARTBEAT = 3;

    private TextView nameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        nameTV = (TextView) findViewById(R.id.name);
        star = (Button) findViewById(R.id.star);
        if (AutoReplyService.instance != null) {
            AutoReplyService.instance.initZbus();
        }
        startService(new Intent(this, AutoReplyService.class));
        // sendWxCardMsg();
        app.wToolSDK.setOnMessageListener(new OnMessageListener() {
            @Override
            public void messageEvent(MessageEvent messageEvent) {
                Log.e("----", messageEvent.toString());
            }
        });
        app.wToolSDK.setOnTaskEndListener(new OnTaskEndListener() {
            @Override
            public void taskEndEvent(TaskEndEvent taskEndEvent) {
                Log.e("----", taskEndEvent.toString());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
        updateServiceCount();
        getWxPeople();
        startListerWxMsg();
    }

    public void updateServiceCount() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String name = getSharedPreferences("kiway", 0).getString("name", "");
                int recvCount = getSharedPreferences("kiway", 0).getInt("recvCount", 0);
                int replyCount = getSharedPreferences("kiway", 0).getInt("replyCount", 0);
                nameTV.setText(name + "\n" + "接收次数：" + recvCount + "，回复次数：" + replyCount);
            }
        });
    }

    private boolean isServiceEnabled() {
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(Context
                .ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager
                .getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.service.AutoReplyService")) {
                return true;
            }
        }
        return false;
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    public void start(View view) {
//        try {
//            toast("选择“开维客服机器人“-选择“打开”");
//            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
//            startActivity(accessibleIntent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            toast("该手机不支持微信自动回复功能");
//        }
    }

    public void logout(View view) {
        getSharedPreferences("kiway", 0).edit().clear().commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void test(View v) {
        Action a = new Action();
        a.sender = "test";
        a.content = "content";
        a.receiveType = TYPE_TEST;
        if (AutoReplyService.instance != null) {
            AutoReplyService.instance.sendMsgToServer(9999, a);
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_NETWORK_OK) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.GONE);
                Log.d("test", "有网络");
                if (AutoReplyService.instance != null) {
                    AutoReplyService.instance.initZbus();
                    sendEmptyMessageDelayed(MSG_HEARTBEAT, 1000);
                }
            } else if (msg.what == MSG_NETWORK_ERR) {
                RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                rl_nonet.setVisibility(View.VISIBLE);
                Log.d("test", "无网络");
                //ZbusUtils.close();
            } else if (msg.what == MSG_HEARTBEAT) {
                test(null);
            }
        }
    };

    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            star.setText("服务已经开启");
            star.setEnabled(false);
        } else {
            star.setText("点击开启服务");
            star.setEnabled(true);
        }
    }

    private void getWxPeople() {
        try {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("type", 5);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("pageindex", 0);
            jsonTask.getJSONObject("content").put("pagecount", 0);
            String content = app.wToolSDK.sendTask(jsonTask.toString());
            final JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = new JSONArray();
            if (jsonObject.getInt("result") == 0) {
                jsonArray = jsonObject.getJSONArray("content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String nickname = app.wToolSDK.decodeValue(item.getString("nickname"));//解码
                    String remark = app.wToolSDK.decodeValue(item.getString("remark"));
                    String wxid = app.wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = app.wToolSDK.decodeValue(item.getString("wxno"));
                    item.put("nickname", nickname);//昵称
                    item.put("remark", remark);//备注
                    item.put("wxid", wxid);//微信id
                    item.put("wxno", wxno);//微信号
                    jsonArray.put(i, item);
                }
            }
            WxUtils.setWxPeopleList(jsonArray);
            Log.e("ccccc", jsonArray.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startListerWxMsg() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(1);
            jsonArray.put(2);
            jsonObject.put("talkertypes", jsonArray);
            jsonObject.put("froms", new JSONArray());
            jsonArray = new JSONArray();
            jsonArray.put(1);
            jsonArray.put(42);
            jsonObject.put("msgtypes", jsonArray);
            jsonObject.put("msgfilters", new JSONArray());
            String result = app.wToolSDK.startMessageListener(jsonObject.toString());
            jsonObject = new JSONObject(result);
            if (jsonObject.getInt("result") == 0) {
                Toast.makeText(MainActivity.this, "监听消息开启", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "监听消息开启失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "err", e);
        }
    }

    public void sendWxCardMsg() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("taskid", System.currentTimeMillis());
            jsonObject.put("content", new JSONObject());
            jsonObject.getJSONObject("content").put("talker", wxRoomId1);
            jsonObject.getJSONObject("content").put("timeout", -1);
            jsonObject.put("type", 49);//1文字 2图片 3语音 4 视频
            jsonObject.getJSONObject("content").put("text", "http://588ku.com/image/n-huabiyansetubiao.html");
            String result = app.wToolSDK.sendTask(jsonObject.toString());
            Log.e("cccc", result);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        app.wToolSDK.unload();
    }
}