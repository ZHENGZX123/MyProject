package cn.kiway.autoreply.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;

import java.util.List;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.service.AutoReplyService;
import cn.kiway.wx.reply.utils.ZbusUtils;

import static cn.kiway.autoreply.entity.Action.TYPE_TEST;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;
    private Button star;

    public static final int MSG_NETWORK_OK = 1;
    public static final int MSG_NETWORK_ERR = 2;
    public static final int MSG_HEARTBEAT = 3;

    //AccessibilityService 管理
    private AccessibilityManager accessibilityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        star = (Button) findViewById(R.id.star);
        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    private boolean isServiceEnabled() {
        List<AccessibilityServiceInfo> accessibilityServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            if (info.getId().equals(getPackageName() + "/.service.AutoReplyService")) {
                return true;
            }
        }
        return false;
    }

    public void start(View view) {
        try {
            toast("选择“开维客服机器人“-选择“打开”");
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        } catch (Exception e) {
            e.printStackTrace();
            toast("该手机不支持微信自动回复功能");
        }
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
                //RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                //rl_nonet.setVisibility(View.GONE);
                Log.d("test", "有网络");
                if (AutoReplyService.instance != null) {
                    AutoReplyService.instance.initZbus();
                    sendEmptyMessageDelayed(MSG_HEARTBEAT, 1000);
                }
            } else if (msg.what == MSG_NETWORK_ERR) {
                //RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                //rl_nonet.setVisibility(View.VISIBLE);
                Log.d("test", "无网络");
                ZbusUtils.close();
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
}