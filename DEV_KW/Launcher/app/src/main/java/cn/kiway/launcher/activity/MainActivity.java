package cn.kiway.launcher.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.kiway.launcher.R;
import cn.kiway.launcher.utils.Utils;

import static cn.kiway.launcher.utils.Utils.SYS_EMUI;

public class MainActivity extends BaseActivity {

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
        //1.开启线程
        locked = true;
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
        startThread();
        //2.清除后台app
        clearOtherApp();
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

                //清除当前默认launcher,有时候会闪退。。。
//                ArrayList<IntentFilter> intentList = new ArrayList<IntentFilter>();
//                ArrayList<ComponentName> cnList = new ArrayList<ComponentName>();
//                getPackageManager().getPreferredActivities(intentList, cnList, null);
//                IntentFilter dhIF;
//                for (int i = 0; i < cnList.size(); i++) {
//                    dhIF = intentList.get(i);
//                    if (dhIF.hasAction(Intent.ACTION_MAIN) &&
//                            dhIF.hasCategory(Intent.CATEGORY_HOME)) {
//                        getPackageManager().clearPackagePreferredActivities(cnList.get(i).getPackageName());
//                    }
//                }
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
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //1.收回状态栏
                    Utils.collapse(getApplicationContext());
                    //2.判断当前运行的app
                    boolean ret = Utils.checkCurrentApp(getApplicationContext());
                    if (!ret) {
                        startActivity(new Intent(getApplicationContext(), cn.kiway.launcher.activity.MainActivity.class));
                    }
                    locked = getSharedPreferences("kiway", 0).getBoolean("locked", false);
                }
            }
        }.start();
    }

    private void clearOtherApp() {
       /* //To change body of implemented methods use File | Settings | File Templates.
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Log.d("test", "process name : " + appProcessInfo.processName);
                //importance 该进程的重要程度  分为几个级别，数值越低就越重要。
                Log.d("test", "importance : " + appProcessInfo.importance);
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.d("test", "It will be killed, package name : " + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
        }*/
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "Main onDestroy");
    }
}