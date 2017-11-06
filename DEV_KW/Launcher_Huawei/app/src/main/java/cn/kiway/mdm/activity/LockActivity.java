package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.kiway.mdm.R;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.server;

/**
 * Created by Administrator on 2017/10/13.
 */

public class LockActivity extends BaseActivity {
    private TextView textView;
    private TextView moshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        textView = (TextView) findViewById(R.id.lock);
        moshi = (TextView) findViewById(R.id.moshi);
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            textView.setText("家长模式");
            moshi.setText("当前模式为：学生模式");
        } else {
            textView.setText("学生模式");
            moshi.setText("当前模式为：家长模式");
        }
    }

    public void Lock(View view) {
        if (getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", false).commit();
            textView.setText("学生模式");
            moshi.setText("当前模式为：家长模式");
            Toast.makeText(this, "解锁成功", Toast.LENGTH_SHORT).show();
            unlock();
        } else {
            getSharedPreferences("kiway", 0).edit().putBoolean("locked", true).commit();
            textView.setText("家长模式");
            moshi.setText("当前模式为：学生模式");
            Toast.makeText(this, "锁定成功", Toast.LENGTH_SHORT).show();
            lock();
        }
    }

    public void Password(View view) {
        startActivity(new Intent(this, ChangePassWordActivity.class));
    }

    public void Logout(View view) {
        setResult(999);
        finish();
        try {
            //showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            String url = server + "device/logout";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("operation", "invalidate");
            Log.d("test", "param = " + param.toString());
            client.post(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "post onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        if (StatusCode == 200) {
                            String imei = Utils.getIMEI(LockActivity.this);
                            Utils.deviceRuntime(LockActivity.this, imei, "2");
                            getSharedPreferences("kiway", 0).edit().clear();
                            new MyDBHelper(LockActivity.this).deleteAppcharge(null);
                            new MyDBHelper(LockActivity.this).deleteWifi(null);
                            new MyDBHelper(LockActivity.this).deleteNetwork(null);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    dismissPD();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            dismissPD();
        }
    }

    public void Before(View view) {
        finish();
    }

}
