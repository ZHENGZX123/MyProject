package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.mdm.MDMHelper;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.server;

/**
 * Created by Administrator on 2017/10/13.
 */

public class LockActivity extends BaseActivity {
    private TextView textView;
    private TextView moshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        textView = (TextView) findViewById(R.id.lock);
        moshi = (TextView) findViewById(R.id.moshi);
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            textView.setText("家长模式");
            moshi.setText("当前模式为：学生模式");
        } else {
            textView.setText("学生模式");
            moshi.setText("当前模式为：家长模式");
        }
    }

    public void Lock(View view) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();
            textView.setText("学生模式");
            moshi.setText("当前模式为：家长模式");
            Toast.makeText(this, "解锁成功", Toast.LENGTH_SHORT).show();
            unlock();
        } else {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
            textView.setText("家长模式");
            moshi.setText("当前模式为：学生模式");
            Toast.makeText(this, "锁定成功", Toast.LENGTH_SHORT).show();
            lock();
        }
    }

    public void Password(View view) {
        startActivity(new Intent(this, ChangePassWordActivity.class));
    }

    public void Logout(View view) {
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = server + "device/logout";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            client.post(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "post onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        if (StatusCode == 200) {
                            toast("注销成功");
                            String imei = Utils.getIMEI(LockActivity.this);
                            Utils.deviceRuntime(LockActivity.this, imei, "2");
                            //TODO 这里有问题
                            getSharedPreferences("kiway", 0).edit().putBoolean("login", false).commit();
                            new MyDBHelper(LockActivity.this).deleteWifi();
                            new MyDBHelper(LockActivity.this).deleteNetwork();
                            setResult(999);
                            finish();
                        } else {
                            toast("注销失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("请求失败，请稍后再试");
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    dismissPD();
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }

    public void Before(View view) {
        finish();
    }

    private void lock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.MainActivity");
        //2.关闭settings.失效
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        //保持APP持续运行
        //MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(true);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        MDMHelper.getAdapter().setHomeButtonDisabled(true);
        //MDMHelper.getAdapter().setVpnDisabled(true); //这个失效。
        //7.禁止修改时间
        //MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);//这个失效
    }

    private void unlock() {
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        //MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(false);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(false);
        MDMHelper.getAdapter().setHomeButtonDisabled(false);
        //MDMHelper.getAdapter().setVpnDisabled(true); //这个失效。
        //MDMHelper.getAdapter().setTimeAndDateSetDisabled(false);//这个失效

        MDMHelper.getAdapter().setWifiDisabled(false);
    }
}
