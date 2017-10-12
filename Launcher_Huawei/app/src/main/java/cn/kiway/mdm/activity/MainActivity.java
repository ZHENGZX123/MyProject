package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.mdm.MDMHelper;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");

        //1.设置初始密码
        String password = getSharedPreferences("kiway", 0).getString("password", "");
        Log.d("test", "pasword = " + password);
        if (TextUtils.isEmpty(password)) {
            final EditText et = new EditText(this);
            et.setText("123456");
            et.setSelection(et.getText().toString().length());
            et.setSingleLine();
            new AlertDialog.Builder(this).setTitle("请设置初始密码").setIcon(
                    android.R.drawable.ic_dialog_info).setView(et
            ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String input = et.getText().toString();
                    if (input.equals("")) {
                        toast("密码不能为空");
                        return;
                    }
                    getSharedPreferences("kiway", 0).edit().putString("password", input).commit();
                }
            }).setCancelable(false).show();
        }
    }

    public void clickButton2(View v) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("当前已锁定");
            return;
        }
        toast("开始锁定");
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();

        //初始化adapter
        ComponentName mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        MDMHelper.getAdapter().init(this, mAdminName);
        //1.设置默认桌面
        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.MainActivity");
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //3.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        MDMHelper.getAdapter().addPersistentApp(packages);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //5.禁止USB，慎用！！！
        MDMHelper.getAdapter().setUSBDataDisabled(true);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        MDMHelper.getAdapter().setHomeButtonDisabled(true);
        //MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
        //7.禁止修改时间
        MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);
    }

    public void clickButton3(View v) {
        //进入APP列表
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入学习园地");
            return;
        }
        startActivity(new Intent(this, AppListActivity.class));
    }

    public void clickButton4(View v) {
        if (!getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("请先锁定再进入其他应用");
            return;
        }
        startActivity(new Intent(this, AppListActivity2.class));
    }

    public void clickButton5(View v) {
        //弹出密码框，密码正确就解锁
        final EditText et = new EditText(this);
        et.setText("123456");
        et.setSingleLine();
        new AlertDialog.Builder(this).setTitle("请输入密码").setIcon(
                android.R.drawable.ic_dialog_info).setView(et
        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = et.getText().toString();
                if (input.equals("")) {
                    toast("密码不能为空");
                    return;
                }
                String password = getSharedPreferences("kiway", 0).getString("password", "");
                if (!input.equals(password)) {
                    toast("密码错误");
                    return;
                }
                toast("已经解锁");
                getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();


                //初始化adapter
                ComponentName mAdminName = new ComponentName(MainActivity.this, SampleDeviceReceiver.class);
                MDMHelper.getAdapter().init(MainActivity.this, mAdminName);
                //1.设置默认桌面
                MDMHelper.getAdapter().clearDefaultLauncher();
                //2.关闭settings.慎用！！！
                //MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
                //3.设置不可卸载
                List<String> packages = new ArrayList<>();
                packages.add("cn.kiway.mdm");
                MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
                MDMHelper.getAdapter().addPersistentApp(packages);
                //4.禁止下拉状态栏
                MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
                //5.禁止USB，慎用！！！
                MDMHelper.getAdapter().setUSBDataDisabled(false);
                //6.禁用一些物理键盘
                MDMHelper.getAdapter().setTaskButtonDisabled(false);
                MDMHelper.getAdapter().setHomeButtonDisabled(false);
                //MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
                //7.禁止修改时间
                MDMHelper.getAdapter().setTimeAndDateSetDisabled(true);


            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
    }
}