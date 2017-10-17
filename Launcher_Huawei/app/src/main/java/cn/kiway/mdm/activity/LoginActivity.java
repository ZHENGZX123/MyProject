package cn.kiway.mdm.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.kiway.mdm.R;

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

    }
}
