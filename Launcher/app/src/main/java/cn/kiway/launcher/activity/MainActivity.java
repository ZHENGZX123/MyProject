package cn.kiway.launcher.activity;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import cn.kiway.launcher.R;
import cn.kiway.launcher.service.KWService;
import cn.kiway.launcher.utils.Utils;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
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
                        WRITE_EXTERNAL_STORAGE,
                        CALL_PHONE,
                        SEND_SMS
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

        //判断电话和短信app是哪个
        ArrayList<IntentFilter> intentList = new ArrayList<>();
        ArrayList<ComponentName> cnList = new ArrayList<>();
        getPackageManager().getPreferredActivities(intentList, cnList, null);
        for (int i = 0; i < cnList.size(); i++) {
            IntentFilter dhIF = intentList.get(i);
            if (dhIF.hasAction(Intent.ACTION_CALL)) {
                String name = cnList.get(i).getPackageName();
                Log.d("test", "name = " + name);
            }
        }
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
                if (!input.equals("123456")) {
                    toast("密码错误");
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
        //1.检查locked
        if (!locked) {
            return;
        }
        //2.开启服务
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
    }
}