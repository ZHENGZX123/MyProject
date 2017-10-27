package cn.kiway.mdm.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import cn.kiway.mdm.KWApp;

/**
 * Created by Administrator on 2017/6/9.
 */

public class BaseActivity extends Activity {

    public ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");
    }

    @Override
    protected void onStart() {
        super.onStart();
        KWApp.instance.currentActivity = this;
    }

    public void showPD() {
        pd.show();
    }

    public void dismissPD() {
        pd.dismiss();
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
