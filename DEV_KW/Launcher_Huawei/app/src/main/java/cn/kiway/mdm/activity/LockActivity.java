package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/10/13.
 */

public class LockActivity extends BaseActivity {
    TextView textView;
    TextView moshi;

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
        } else {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
            textView.setText("家长模式");
            moshi.setText("当前模式为：学生模式");
            Toast.makeText(this, "锁定成功", Toast.LENGTH_SHORT).show();
        }
    }

    public void Password(View view) {
        startActivity(new Intent(this, ChangePassWordActivity.class));
    }

    public void Before(View view) {
        finish();
    }

}
