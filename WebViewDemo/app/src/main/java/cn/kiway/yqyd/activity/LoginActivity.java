package cn.kiway.yqyd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.yqyd.R;
import cn.kiway.yqyd.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity implements Callback {
    public static String loginUrl = "http://yqyd.qgjydd.com/yqyd/app/teaLogin?username={userName}&password=";

    OkHttpClient mOkHttpClient = new OkHttpClient();
    EditText userName, password;
    TextView error;
    Button loadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    void initView() {
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        error = (TextView) findViewById(R.id.error);
        loadButton = (Button) findViewById(R.id.loadbutton);
    }

    public void clickLogin(View v) {
        error.setText("");
        if (userName.getText().length() <= 0) {
            error.setText("请输入用户名");
            return;
        }
        if (password.getText().length() <= 0) {
            error.setText("请输入密码");
            return;
        }
        loadButton.setText("登录中...");
        loading(userName.getText().toString(), password.getText().toString());
    }

    void loading(String userName, String password) {
        final Request request = new Request.Builder()
                .url(loginUrl.replace("{userName}", userName) + password)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            final JSONObject data = new JSONObject(response.body().string());
            if (data.optInt("errcode") == 200) {
                SharedPreferencesUtil.save(this, "userName", userName.getText().toString());
                SharedPreferencesUtil.save(this, "passWord", password.getText().toString());
                startActivity(new Intent(this, WebViewActivity2.class).putExtra("token", userName.getText().toString
                        ()));
                finish();
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadButton.setText("登录");
                        error.setText(data.optString("errmsg"));
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否为手机号码
     */
    public boolean isMobileNum(String mobiles) {
        Pattern p = Pattern
                .compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
