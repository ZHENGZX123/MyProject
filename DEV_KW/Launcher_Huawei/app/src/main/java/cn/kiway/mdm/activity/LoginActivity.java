package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.School;

/**
 * Created by Administrator on 2017/10/17.
 */

public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //1.省市县、选学校、选班级

        //2.注册到后台:学校，学号，姓名，设备IMEI，华为token,手机品牌型号
        //Utils.register(context, tm.getDeviceId(), token);
        String brand = Build.BRAND;
        Log.d("test", "brand = " + brand);
        String model = Build.MODEL;
        Log.d("test", "model = " + model);
    }

    public void selectSchool(View view) {
        startActivityForResult(new Intent(this, EditAeraActivity.class), 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 999) {
            School s = (School) data.getSerializableExtra("school");
            Log.d("test", "s = " + s.toString());
        }
    }

    public void clickLogin(View view) {
        //1.客户端校验

        //2.提交数据


    }
}
