package cn.kiway.autoreply;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static cn.kiway.autoreply.Action.TYPE_TEST;

public class MainActivity extends Activity {

    private EditText name;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test", "main oncreate");
        setContentView(R.layout.activity_main);

        instance = this;
        name = (EditText) findViewById(R.id.name);
    }

    public void start(View view) {
        String nameStr = name.getText().toString();
        if (TextUtils.isEmpty(nameStr)) {
            toast("名字不能为空");
            return;
        }
        try {
            toast("选择开维客服机器人-打开");
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(accessibleIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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