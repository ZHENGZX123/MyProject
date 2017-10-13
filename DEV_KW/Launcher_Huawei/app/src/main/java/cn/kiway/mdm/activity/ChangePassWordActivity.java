package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.kiway.mdm.R;

/**
 * Created by Administrator on 2017/10/12.
 */

public class ChangePassWordActivity extends BaseActivity {
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editText1 = (EditText) findViewById(R.id.new_password);
        editText2 = (EditText) findViewById(R.id.new_password1);
    }

    public void Ok(View view) {
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!editText1.getText().toString().equals(editText2.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        getSharedPreferences("kiway", 0).edit().putString("password", editText1.getText().toString()).commit();
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Before(View view) {
        finish();
    }
}
