package cn.kiway.autoreply.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import cn.kiway.autoreply.KWApplication;

/**
 * Created by Administrator on 2018/3/23.
 */

public class BaseActivity extends Activity {

    public ProgressDialog pd;
KWApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app= (KWApplication) getApplicationContext();
        pd = new ProgressDialog(this);
        pd.setMessage("网络请求中");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void showPD() {
        pd.show();
    }

    public void dismissPD() {
        pd.dismiss();
    }

    public void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
