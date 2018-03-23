package cn.kiway.autoreply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.entity.Action;
import cn.kiway.autoreply.service.AutoReplyService;
import cn.kiway.zbus.utils.ZbusUtils;

import static cn.kiway.autoreply.entity.Action.TYPE_TEST;

public class MainActivity extends BaseActivity {

    public static MainActivity instance;

    public static final int MSG_NETWORK_OK = 1;
    public static final int MSG_NETWORK_ERR = 2;
    public static final int MSG_HEARTBEAT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;
    }

    public void start(View view) {
        try {
            toast("选择“开维客服机器人“-选择“打开”");
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout(View view) {

    }

    public void test(View v) throws Exception {
        Action a = new Action();
        a.sender = "test";
        a.content = "content";
        a.receiveType = TYPE_TEST;
        AutoReplyService.instance.sendMsgToServer(9999, a);
    }


    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_NETWORK_OK) {
                //RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                //rl_nonet.setVisibility(View.GONE);
                Log.d("test", "有网络");
                AutoReplyService.instance.initZbus();
                sendEmptyMessageDelayed(MSG_HEARTBEAT, 1000);
            } else if (msg.what == MSG_NETWORK_ERR) {
                //RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
                //rl_nonet.setVisibility(View.VISIBLE);
                Log.d("test", "无网络");
                ZbusUtils.close();
            } else if (msg.what == MSG_HEARTBEAT) {
                for (int i = 0; i < 3; i++) {
                    Action a = new Action();
                    a.sender = "test";
                    a.content = "content";
                    a.receiveType = TYPE_TEST;
                    AutoReplyService.instance.sendMsgToServer(9999, a);
                }
            }
        }
    };
}