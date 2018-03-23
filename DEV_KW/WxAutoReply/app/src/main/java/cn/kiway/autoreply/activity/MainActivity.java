package cn.kiway.autoreply.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import cn.kiway.autoreply.service.AutoReplyService;
import cn.kiway.autoreply.R;
import cn.kiway.autoreply.entity.Action;

import static cn.kiway.autoreply.entity.Action.TYPE_TEST;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}