package cn.kiway.robot.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;

import org.json.JSONObject;

import cn.kiway.robot.R;
import cn.kiway.robot.util.MyListener;
import cn.kiway.robot.util.Utils;

/**
 * Created by Administrator on 2018/8/14.
 */

public class Guide4Activity extends BaseActivity {

    private static final int QRSCAN = 6666;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide4);
    }

    public void clickStart(View view) {
        startActivityForResult(new Intent(Guide4Activity.this, CaptureActivity.class), QRSCAN);
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
            String name = getSharedPreferences("kiway", 0).getString("name", "");
            String wxNo = getSharedPreferences("kiway", 0).getString("wxNo", "");
            try {
                JSONObject o = new JSONObject(decryptStr);
                String tenantId = o.optString("tenantId");
                getSharedPreferences("kiway", 0).edit().putString("tenantId", tenantId).commit();
                Utils.doNewLogin(this, name, wxNo, tenantId, new MyListener() {
                    @Override
                    public void onResult(boolean success) {
                        if (success) {
                            toast("登录成功");
                            startActivity(new Intent(Guide4Activity.this, MainActivity.class).putExtra("newLogin", true));
                            finish();
                        } else {
                            toast("登录失败");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
