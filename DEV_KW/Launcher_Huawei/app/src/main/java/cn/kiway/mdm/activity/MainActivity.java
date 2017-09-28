package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import cn.kiway.mdm.R;

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

    public void clickButton1(View v) {

    }

    public void clickButton2(View v) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            toast("当前已锁定");
            return;
        }
        toast("开始锁定");
        getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
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