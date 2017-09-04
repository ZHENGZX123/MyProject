package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huawei.android.pushagent.api.PushManager;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.mdm.MDMHelper;

/**
 * Created by Administrator on 2017/9/1.
 */

public class BaseActivity extends Activity {
    public ComponentName mAdminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.华为推送
        huaweiPush();
        mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        //3.初始化adapter
        MDMHelper.getAdapter().init(this, mAdminName);
        //4.设置默认桌面
//        MDMHelper.getAdapter().setDefaultLauncher("cn.kiway.mdm", "cn.kiway.mdm.activity.MainActivity");
        //5.关闭settings.慎用！！！
//        MDMHelper.getAdapter().setSettingsApplicationDisabled(true);
        //6.设置不可卸载
        List<String> packages = new ArrayList<>();
        packages.add("cn.kiway.mdm");
        MDMHelper.getAdapter().addDisallowedUninstallPackages(packages);
        MDMHelper.getAdapter().addPersistentApp(packages);
        //7.禁止下拉状态栏
        MDMHelper.getAdapter().setStatusBarExpandPanelDisabled(true);
        //8.禁止USB，慎用！！！
//        MDMHelper.getAdapter().setUSBDataDisabled(true);
        MDMHelper.getAdapter().setTaskButtonDisabled(true);
        MDMHelper.getAdapter().setHomeButtonDisabled(true);
//        MDMHelper.getAdapter().setVpnDisabled(true); 这个失效。
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());
    }
}
