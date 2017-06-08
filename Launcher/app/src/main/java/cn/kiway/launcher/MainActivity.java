package cn.kiway.launcher;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

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
                Intent paramIntent = new Intent("android.intent.action.MAIN");
                paramIntent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
                paramIntent.addCategory("android.intent.category.DEFAULT");
                paramIntent.addCategory("android.intent.category.HOME");
                startActivity(paramIntent);
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
    }

    public void clickButton3(View v) {
        if (!flag) {
            Toast.makeText(MainActivity.this, "当前未锁定", Toast.LENGTH_SHORT).show();
            return;
        }
        //弹出密码框，密码正确就解锁
        final EditText et = new EditText(this);
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
                //回到默认桌面

                Intent paramIntent = new Intent("android.intent.action.MAIN");
                paramIntent.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
                paramIntent.addCategory("android.intent.category.DEFAULT");
                paramIntent.addCategory("android.intent.category.HOME");
                startActivity(paramIntent);
            }
        }).show();
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
//        Log.d("test", "currentPackageName = " + currentPackageName);
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