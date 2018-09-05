package cn.kiway.robot.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.kiway.robot.R;
import cn.kiway.robot.util.Utils;

import static cn.kiway.robot.util.Constant.clientUrl;

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


            doLogin(this, name, wxNo, decryptStr);
        }
    }

    private void doLogin(final Context c, final String name, final String wxNo, String decryptStr) {
        try {
            JSONObject o = new JSONObject(decryptStr);
            String tenantId = o.optString("tenantId");

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = clientUrl + "/robot/newLogin";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("wxNo", wxNo);
            param.put("name", name);
            param.put("tenantId", tenantId);
            Log.d("test", "param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "login onSuccess = " + ret);
                    //登录成功
                    try {
                        JSONObject o = new JSONObject(ret);
                        int statusCode = o.optInt("statusCode");
                        if (statusCode != 200) {
                            toast("登录失败");
                            return;
                        }
                        JSONObject data = o.optJSONObject("data");
                        String token = data.optString("token");
                        String areaCode = data.optString("areaCode");
                        String robotId = data.optString("robotId");
                        String userName = data.optString("userName");
                        String password = data.optString("password");
                        c.getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("username", userName).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("robotId", robotId).commit();
                        c.getSharedPreferences("kiway", 0).edit().putString("areaCode", areaCode).commit();
                        startActivity(new Intent(Guide4Activity.this, MainActivity.class).putExtra("newLogin", true));
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + ret);
                    //登录失败原因
                    toast("登录失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("登录失败");
        }
    }


}
