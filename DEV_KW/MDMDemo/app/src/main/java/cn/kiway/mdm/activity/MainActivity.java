package cn.kiway.mdm.activity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;

import cn.kiway.mdm.R;
import cn.kiway.mdm.broadcast.SampleDeviceReceiver;
import cn.kiway.mdm.util.SampleEula;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);


    }
}
