package cn.kiway.yqyd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.kiway.yqyd.App;
import cn.kiway.yqyd.R;
import cn.kiway.yqyd.utils.SharedPreferencesUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static cn.kiway.yqyd.utils.HttpUploadFile.loginUrl;

/**
 * Created by Administrator on 2017/5/8.
 */

public class BootActivity extends Activity implements Animation.AnimationListener, Callback {
    ImageView Img;
    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_boot);
        Img = (ImageView) findViewById(R.id.img);
        setAnimmation(Img);
        app= (App) getApplicationContext();
    }

    void setAnimmation(View view) {
        //动画效果参数直接定义
        Animation animation = new AlphaAnimation(0.1f, 1.0f);
        if (SharedPreferencesUtil.getString(this, "userName").equals("")) {
            animation.setDuration(2000);
        } else {
            animation.setDuration(1000);
        }
        animation.setAnimationListener(this);
        view.setAnimation(animation);
    }

    void loading(String userName, String password) {
        final Request request = new Request.Builder()
                .url(loginUrl.replace("{userName}", userName) + password)
                .build();
        Call call = app.mOkHttpClient.newCall(request);
        call.enqueue(this);
    }

    void startLoadingActivity(final String showMessage) {
        if (showMessage != null && !showMessage.equals(""))
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BootActivity.this, showMessage, Toast.LENGTH_SHORT);
                }
            });
        finish();
        startActivity(new Intent(BootActivity.this, LoginActivity.class));
    }

    @Override
    public void onFailure(Call call, IOException e) {
        startLoadingActivity("登录失败，请重新登录");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.e("url",call.request().url().toString());
        try {
            final JSONObject data = new JSONObject(response.body().string());
            if (data.optInt("errcode") == 200) {
                startActivity(new Intent(this, WebViewActivity2.class).putExtra("token", SharedPreferencesUtil
                        .getString(this, "userName")));
                finish();
            } else {
                startLoadingActivity(data.optString("errmsg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (SharedPreferencesUtil.getString(this, "userName").equals("")) {
            startLoadingActivity(null);
        } else {
            loading(SharedPreferencesUtil.getString(this, "userName"), SharedPreferencesUtil.getString(this,
                    "passWord"));
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
