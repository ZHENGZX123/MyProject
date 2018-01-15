package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.kiway.mdm.R;
import cn.kiway.mdm.utils.DES;
import cn.kiway.mdm.utils.Utils;

/**
 * Created by Administrator on 2017/10/12.
 */

public class PassWordActivity extends BaseActivity {
    EditText editText1, editText2, oldEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editText1 = (EditText) findViewById(R.id.new_password);
        editText2 = (EditText) findViewById(R.id.new_password1);
        oldEdit = (EditText) findViewById(R.id.old_password);
    }

    public void Ok(View view) {
        if (oldEdit.getText().toString().equals("")) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!oldEdit.getText().toString().equals(new DES().decrypt(this.getSharedPreferences("kiway", 0).getString("password", "")))) {
            Toast.makeText(this, "原密码输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editText1.getText().toString().equals("") || editText2.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!editText1.getText().toString().equals(editText2.getText().toString())) {
            Toast.makeText(this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
            return;
        }
        String defaultPwd = editText1.getText().toString();
        getSharedPreferences("kiway", 0).edit().putString("password", new DES().encrypt(defaultPwd)).commit();
        Utils.updateDefaultPwd(this, defaultPwd);
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Before(View view) {
        finish();
    }
}