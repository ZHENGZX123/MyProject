package cn.kiway.launcher.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.kiway.launcher.R;
import cn.kiway.launcher.utils.Utils;

import static cn.kiway.launcher.utils.Utils.SYS_EMUI;

public class MainActivity extends BaseActivity {

    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "Main onCreate");

        requestRunTimePermission(
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                },
                mListener);

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

        //开机后，立马锁定
        locked = getSharedPreferences("kiway", 0).getBoolean("locked", false);
        startThread();
    }

    public void clickButton1(View v) {
        if (locked) {
            toast("请先解锁再执行该操作");
            return;
        }
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
        if (locked) {
            toast("当前已锁定");
            return;
        }
        toast("开始锁定");
        //开启线程
        locked = true;
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
        startThread();
    }

    public void clickButton3(View v) {
        //进入APP列表
        if (!locked) {
            toast("请先锁定再进入学习园地");
            return;
        }
        startActivity(new Intent(this, AppListActivity.class));
    }

    public void clickButton4(View v) {
        if (!locked) {
            toast("当前未锁定");
            return;
        }
        //弹出密码框，密码正确就解锁
        final EditText et = new EditText(this);
        et.setText("123456");
        et.setSingleLine();
        new AlertDialog.Builder(this).setTitle("请输入解锁密码").setIcon(
                android.R.drawable.ic_dialog_info).setView(et
        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String input = et.getText().toString();
                if (input.equals("")) {
                    toast("请输入解锁密码");
                    return;
                }
                if (!input.equals("123456")) {
                    toast("解锁密码错误");
                    return;
                }
                toast("已经解锁");
                locked = false;
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


    private boolean locked;

    private void startThread() {
        //检查locked
        if (!locked) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                while (locked) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //1.收回状态栏
                    Utils.collapse(getApplicationContext());
                    //2.判断当前运行的app
                    boolean ret = Utils.checkCurrentApp(getApplicationContext());
                    if (!ret) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    locked = getSharedPreferences("kiway", 0).getBoolean("locked", false);
                }
            }
        }.start();
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
                //若用户未开启权限，则引导用户开启“Apps with usage access”权限
                startActivityForResult(
                        new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                        MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
    }
}