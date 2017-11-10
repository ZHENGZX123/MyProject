package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.R;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/10/13.
 */

public class SettingActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textView = (TextView) findViewById(R.id.lock);
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            textView.setText("家长模式");
        } else {
            textView.setText("学生模式");
        }
    }

    public void Lock(View view) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();
            textView.setText("学生模式");
            Toast.makeText(this, "解锁成功", Toast.LENGTH_SHORT).show();
            unlock();
        } else {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
            textView.setText("家长模式");
            Toast.makeText(this, "锁定成功", Toast.LENGTH_SHORT).show();
            lock();
        }
    }

    public void Password(View view) {
        startActivity(new Intent(this, PassWordActivity.class));
    }

    public void Logout(View view) {
        setResult(999);
        finish();

        new Thread() {
            @Override
            public void run() {
                //1.上报状态
                Utils.deviceRuntime(SettingActivity.this, "2", false);
                Utils.uninstallPush(SettingActivity.this);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //2.注销
                Utils.logout(SettingActivity.this);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //3.清数据
                getSharedPreferences("kiway", 0).edit().clear().commit();
                new MyDBHelper(SettingActivity.this).deleteAppcharge(null);
                new MyDBHelper(SettingActivity.this).deleteWifi(null);
                new MyDBHelper(SettingActivity.this).deleteNetwork(null);
                new MyDBHelper(SettingActivity.this).deleteCall(null);
            }
        }.start();
    }

    public void Before(View view) {
        finish();
    }

}
