package cn.kiway.mdm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.huawei.android.pushagent.api.PushManager;

/**
 * Created by Administrator on 2017/6/9.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void huaweiPush() {
        PushManager.requestToken(this);
        Log.i("huawei", "try to get Token ,current packageName is " + this.getPackageName());
    }

    protected void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }


}
