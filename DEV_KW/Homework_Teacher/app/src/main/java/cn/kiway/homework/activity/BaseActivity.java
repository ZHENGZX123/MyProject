package cn.kiway.homework.activity;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseActivity extends Activity {
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
