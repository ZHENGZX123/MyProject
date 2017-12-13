package cn.kiway.mdm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import cn.kiway.mdm.R;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/10/13.
 */

public class SettingActivity extends BaseActivity {

    private TextView mode;
    private ImageView codeIV;
    private RelativeLayout codeRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mode = (TextView) findViewById(R.id.lock);
        codeIV = (ImageView) findViewById(R.id.codeIV);
        codeRL = (RelativeLayout) findViewById(R.id.codeRL);
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            mode.setText("当前已锁定");
        } else {
            mode.setText("当前未锁定");
        }
    }

    public void Lock(View view) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();
            mode.setText("当前未锁定");
            unlock();
        } else {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
            mode.setText("当前已锁定");
            lock();
        }
    }

    public void NotifyMsg(View view) {
        startActivity(new Intent(this, NotifyMsgActivity.class));
    }

    public void custom(View view) {
        startActivity(new Intent(this, AppListActivity3.class));
    }

    public void Code(View view) {
        String schoolId = getSharedPreferences("kiway", 0).getString("schoolId", "");
        String classId = getSharedPreferences("kiway", 0).getString("classId", "");
        String studentNumber = getSharedPreferences("kiway", 0).getString("studentNumber", "");
        String name = getSharedPreferences("kiway", 0).getString("name", "");
        String token = getSharedPreferences("huawei", 0).getString("token", "");
        try {
            JSONObject content = new JSONObject();
            content.put("schoolId", schoolId);
            content.put("classId", classId);
            content.put("studentNumber", studentNumber);
            content.put("name", name);
            content.put("imei", Utils.getIMEI(this));
            content.put("token", token);
            Bitmap b = Utils.createQRImage(content.toString(), 400, 400);
            codeIV.setImageBitmap(b);
            codeRL.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CodeIV(View view) {
        codeRL.setVisibility(View.GONE);
    }

    public void Password(View view) {
        startActivity(new Intent(this, PassWordActivity.class));
    }

    public void Logout(View view) {
        setResult(999);
        FileACache.deleteListCache();
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
                new MyDBHelper(SettingActivity.this).deleteSMS(null);
                new MyDBHelper(SettingActivity.this).deleteFile();
                new MyDBHelper(SettingActivity.this).deleteNotifyMessage();
                //4.退出登录要解锁。。。
                unlock();
            }
        }.start();
    }

    public void Grade(View view) {
        toast("娱乐积分200分，可以兑换200分钟娱乐时间");
    }

    public void Before(View view) {
        finish();
    }

    public void File(View view) {
        startActivity(new Intent(this, FileListActivity.class));
    }

    public void onSetting(View view) {
        startActivity(new Intent(this, SystemSetupActivity.class));
    }

}
