package cn.kiway.launcher.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

import cn.kiway.launcher.R;
import cn.kiway.launcher.utils.Utils;

import static cn.kiway.launcher.utils.Utils.SYS_EMUI;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void clickButton1(View v) {
        if (flag) {
            Toast.makeText(this, "请先解锁再执行该操作", Toast.LENGTH_SHORT).show();
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
        if (flag) {
            Toast.makeText(MainActivity.this, "当前已锁定", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(MainActivity.this, "开始锁定", Toast.LENGTH_SHORT).show();
        flag = true;
        startThread();
        //3.清除后台app
        clearOtherApp();
    }

    public void clickButton3(View v) {
        //进入APP列表
        startActivity(new Intent(this, AppListActivity.class));
    }

    public void clickButton4(View v) {
        if (!flag) {
            Toast.makeText(MainActivity.this, "当前未锁定", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "请输入解锁密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!input.equals("123456")) {
                    Toast.makeText(MainActivity.this, "解锁密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "已经解锁", Toast.LENGTH_SHORT).show();
                flag = false;

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


    private boolean flag = false;

    private void startThread() {
        new Thread() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //1.收回状态栏
                    collapse();
                    //2.收回程序栈
                    boolean ret = isRunningForeground(getApplicationContext());
                    if (!ret) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
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

    public void collapse() {
        try {
            Object service = getSystemService("statusbar");
            Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
            if (Build.VERSION.SDK_INT <= 16) {
                Method collapse = statusbarManager.getMethod("collapse");
                collapse.invoke(service);
            } else {
                Method collapse2 = statusbarManager.getMethod("collapsePanels");
                collapse2.invoke(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        Log.d("aaa", "currentPackageName = " + currentPackageName);
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals("cn.kiway.launcher")) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (flag) {
            return;
        }
        super.onBackPressed();
    }


}