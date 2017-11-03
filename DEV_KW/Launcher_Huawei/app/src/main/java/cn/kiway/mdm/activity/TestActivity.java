package cn.kiway.mdm.activity;

import android.os.Bundle;
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
        //1.设置默认桌面
        MDMHelper.getAdapter().clearDefaultLauncher();
        //2.关闭settings.慎用！！！
        //MDMHelper.getAdapter().setSettingsApplicationDisabled(false);
        //4.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(false);
        //5.禁止USB，调试期间可以关闭
        //MDMHelper.getAdapter().setUSBDataDisabled(false);
        //6.禁用一些物理键盘
        MDMHelper.getAdapter().setTaskButtonDisabled(false);
        MDMHelper.getAdapter().setHomeButtonDisabled(false);
        MDMHelper.getAdapter().setBackButtonDisabled(false);
        //MDMHelper.getAdapter().setVpnDisabled(true); //这个失效。
        //MDMHelper.getAdapter().setTimeAndDateSetDisabled(false);//这个失效
        MDMHelper.getAdapter().setWifiDisabled(false);
    }
}
