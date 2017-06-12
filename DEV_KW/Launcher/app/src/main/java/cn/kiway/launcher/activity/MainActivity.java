package cn.kiway.launcher.activity;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.kiway.launcher.R;
import cn.kiway.launcher.service.KWService;
import cn.kiway.launcher.utils.Utils;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static cn.kiway.launcher.utils.Utils.SYS_EMUI;

public class MainActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;
    private static final int MY_PERMISSIONS_USB_DEBUG = 1102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");


        //1、6.0系统权限申请
        requestRunTimePermission(
                new String[]{
                        WRITE_EXTERNAL_STORAGE,
                        CALL_PHONE,
                        SEND_SMS
                },
                mListener);

        //2.允许使用访问记录
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!hasPermission()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请您到设置页面打开权限：选择开维教育桌面--允许访问使用记录--打开");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(
                                new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                                MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        }

        //4.设置初始密码
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

        Utils.upgradeRootPermission(getPackageCodePath());

        //3.USB调试模式
        boolean enableAdb = (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
        if (enableAdb) {
            if (true) {
                return;
            }
            Log.d("test", "hasRoot  = " + Utils.hasRoot());
            if (Utils.hasRoot()) {
                Utils.execRootCmdSilent("setprop persist.sys.usb.config none");
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请您关闭USB调试功能：开发者选项--USB调试--取消打勾");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(
                                new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
                                MY_PERMISSIONS_USB_DEBUG);
                    }
                });
                builder.setCancelable(false);
                builder.create().show();
            }
        }


        //5.开机锁定
        startThread();
    }

    public void clickON(View v) {
        Utils.execRootCmdSilent("setprop persist.sys.usb.config mtp,adb");
    }

    public void clickOFF(View v) {
        Utils.execRootCmdSilent("setprop persist.sys.usb.config none");
    }

    public void clickButton1(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请选择“开维教育桌面”，并点击“始终”");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectLauncher();
            }
        });
        builder.create().show();
    }

    public void clickButton2(View v) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("当前已锁定");
            return;
        }
        toast("开始锁定");
        //开启线程
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
        startThread();
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
//        if (!locked) {
//            toast("当前未锁定");
//            return;
//        }
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

                //选择回原始的桌面
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("请选择您的原始桌面，并点击“始终”");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectLauncher();
                    }
                });
                builder.create().show();

            }
        }).show();
    }

    private void selectLauncher() {
        //华为手机要特殊处理。。。
        String SYS = Utils.getSystem();
        Log.d("test", "SYS  = " + SYS);
        if (SYS.equals(SYS_EMUI)) {
            Intent paramIntent = new Intent("android.intent.action.MAIN");
            paramIntent.setComponent(new ComponentName("com.huawei.android.internal.app", "com.huawei.android.internal.app.HwResolverActivity"));
            paramIntent.addCategory("android.intent.category.DEFAULT");
            paramIntent.addCategory("android.intent.category.HOME");
            startActivity(paramIntent);
        } else {
            Intent paramIntent = new Intent("android.intent.action.MAIN");
            paramIntent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
            paramIntent.addCategory("android.intent.category.DEFAULT");
            paramIntent.addCategory("android.intent.category.HOME");
            startActivity(paramIntent);
        }
    }


    private void startThread() {
        Intent intent = new Intent(MainActivity.this, KWService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
//        if (locked) {
//            return;
//        }
//        super.onBackPressed();
        //一直锁定返回键好了，不然感觉怪怪的
        return;
    }


    //检测用户是否对本app开启了“Apps with usage access”权限
    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS) {
            if (!hasPermission()) {
                toast("请务必设置权限");
                //若用户未开启权限，则引导用户开启“Apps with usage access”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        } else if (requestCode == MY_PERMISSIONS_USB_DEBUG) {
            boolean enableAdb = (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ADB_ENABLED, 0) > 0);
            if (enableAdb) {
                toast("请务必关闭USB调试");
                startActivityForResult(
                        new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
                        MY_PERMISSIONS_USB_DEBUG);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
    }
}