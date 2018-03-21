package cn.kiway.autoreply;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import cn.kiway.zbus.utils.ZbusUtils;
import io.zbus.mq.Broker;
import io.zbus.mq.Producer;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initZbus();
    }


    //初始化zbus
    public void initZbus() {
        Log.d("test", "initZbus");
        new Thread() {
            @Override
            public void run() {
                try {
                    String userId = Utils.getIMEI(getApplicationContext());
                    if (TextUtils.isEmpty(userId)) {
                        return;
                    }
                    Broker broker = new Broker(Constant.zbusHost + ":" + Constant.zbusPost);
                    Producer p = new Producer(broker);
                    ZbusUtils.init(broker, p);
                    String topic = "kiway_push_" + userId;
                    Log.d("test", "topic = " + topic);
                    ZbusUtils.consumeMsgs(topic, new ZbusMessageHandler(), Constant.zbusHost + ":"
                            + Constant.zbusPost);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ZbusUtils.close();
    }
}