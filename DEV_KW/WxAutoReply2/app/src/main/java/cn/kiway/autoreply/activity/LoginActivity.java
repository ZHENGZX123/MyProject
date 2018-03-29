package cn.kiway.autoreply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.util.Utils;

import static cn.kiway.autoreply.util.Constant.clientUrl;

/**
 * Created by Administrator on 2018/3/23.
 */

public class LoginActivity extends BaseActivity {


    private EditText usernameET;
    private EditText passwordET;
    private EditText nameET;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = (EditText) findViewById(R.id.username);
        passwordET = (EditText) findViewById(R.id.password);
        nameET = (EditText) findViewById(R.id.name);
        if (getSharedPreferences("kiway", 0).getBoolean("login", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    public void login(View view) {
        //1.客户端校验

        final String username = usernameET.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            toast("请填写帐号");
            return;
        }
        final String password = passwordET.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toast("请填写密码");
            return;
        }
        final String name = nameET.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请填写昵称");
            return;
        }
        final String imei = Utils.getIMEI(this);

        //2.提交数据
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = clientUrl + "/robot/login";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("imei", imei);
            param.put("userName", username);
            param.put("password", password);
            param.put("name", name);
            Log.d("test", "param = " + param.toString());
            client.post(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "login onSuccess = " + ret);
                    String errorMsg = "";
                    try {
                        JSONObject o = new JSONObject(ret);
                        int statusCode = o.optInt("statusCode");
                        errorMsg = o.optString("errorMsg");
                        String token = o.getJSONObject("data").getString("token");
                        String robotId = o.getJSONObject("data").getString("robotId");
                        Log.d("test", "login get token = " + token);
                        if (statusCode == 200) {
                            toast("登录成功");

                            getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
                            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                            getSharedPreferences("kiway", 0).edit().putString("username", username).commit();
                            getSharedPreferences("kiway", 0).edit().putString("password", password).commit();
                            getSharedPreferences("kiway", 0).edit().putString("name", name).commit();
                            getSharedPreferences("kiway", 0).edit().putString("robotId", robotId).commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            toast("登录失败：" + errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("登录失败：" + errorMsg);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + ret);
                    dismissPD();
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }
}
