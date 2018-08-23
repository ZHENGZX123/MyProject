package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;

import cn.kiway.robot.R;
import cn.kiway.robot.util.Utils;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Guide3Activity extends BaseActivity {

    private static final int QRSCAN = 6666;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide3);
    }

    public void clickStart(View view) {
        startActivityForResult(new Intent(Guide3Activity.this, CaptureActivity.class), QRSCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QRSCAN) {
            if (data == null) {
                return;
            }
            String result = data.getStringExtra("result");
            Log.d("test", "result = " + result);
            String decryptStr = Utils.decrypt(result);
            toast("decryptStr = " + decryptStr);

            //执行登录，登录完跳去MainActivity
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
