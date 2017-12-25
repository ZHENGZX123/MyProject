package cn.kiway.mdm.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {

    public void toast(final String txt) {
        Log.d("test", "toast is called");
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void toast(final int txt) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                Toast.makeText(BaseActivity.this, txt, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
