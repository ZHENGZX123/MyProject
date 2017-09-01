package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huawei.android.pushagent.api.PushManager;

import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.util.SampleEula;

/**
 * Created by Administrator on 2017/9/1.
 */

public class BaseActivity extends Activity {
    public ComponentName mAdminName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        huaweiPush();

        mAdminName = new ComponentName(this, SampleDeviceReceiver.class);
        new SampleEula(this, (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE), mAdminName).show();
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
