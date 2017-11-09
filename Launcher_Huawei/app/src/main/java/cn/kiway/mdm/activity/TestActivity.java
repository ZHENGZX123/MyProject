package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import cn.kiway.mdm.R;
import cn.kiway.mdm.mdm.MDMHelper;

/**
 * Created by Administrator on 2017/10/27.
 */

//救急页面
public class TestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void clickUnlock(View view) {
        unlock();
    }

    public void clickSetting(View view) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    public void clickUpgrade(View view) {
        MDMHelper.getAdapter().installPackage("/mnt/sdcard/test.apk");
    }
}
