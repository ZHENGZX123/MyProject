package com.zk.myweex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.myweex.R;
import com.zk.myweex.mqttclient.MqttInstance;
import com.zk.myweex.mqttclient.mq.HproseMqttClient;


public class LoadingActivity extends Activity implements View.OnClickListener {
    ImageView img;
    public static LoadingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_loading);
        instance = this;
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initView() throws Exception {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.resiger).setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        findViewById(R.id.fooget_password).setOnClickListener(this);
        findViewById(R.id.scan).setOnClickListener(this);
        //  findViewById(R.id.fooget_password).setVisibility(View.VISIBLE);

        Button send = (Button) findViewById(R.id.send);
        send.setText(R.string.ghyzm);

        img = (ImageView) findViewById(R.id.scan);

        findViewById(R.id.send).setVisibility(View.GONE);
//
        ((EditText) findViewById(R.id.user_name)).setText("13510530146");
        ((EditText) findViewById(R.id.user_password)).setText("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
//                ViewUtil.hideKeyboard(this);
                if (findViewById(R.id.info).getVisibility() == View.VISIBLE)
                    findViewById(R.id.info).setVisibility(View.GONE);
//                if (!StringUtil.isMobileNum(ViewUtil.getContent(this,
//                        R.id.user_name))) {
//                    ViewUtil.setContent(this, R.id.info, R.string.right_tellphone);
//                    findViewById(R.id.info).setVisibility(View.VISIBLE);
//                    return;
//                }
                EditText pwdET = (EditText) findViewById(R.id.user_password);
                String pwd = pwdET.getText().toString();
                if (pwd.equals("")) {// 密码为空

                    TextView infoTV = (TextView) findViewById(R.id.info);
                    infoTV.setText(R.string.password);
//                    ViewUtil.setContent(this, , R.string.password);
                    findViewById(R.id.info).setVisibility(View.VISIBLE);
                    return;
                }
//                Map<String, String> map = new HashMap<>();
//                map.put("userName", ViewUtil.getContent(this, R.id.user_name));
//                map.put("password", ViewUtil.getContent(this, R.id.user_password));
//                map.put("userType", "1");
//                IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.LOGIN_URL, map, activityHandler, true, 1);
//                BaseHttpConnectPool.loodingDialog.show();
//                Intent intentService = new Intent(this, PushService.class);
                //intentService.putExtra(LoginActivity.USERNAME, ViewUtil.getContent(this, R.id.user_name));
                //intentService.putExtra(LoginActivity.PWD, ViewUtil.getContent(this, R.id.user_password));
                //保存
//                Log.i("启动service", "..........");
//                startService(intentService);

                new Thread() {
                    @Override
                    public void run() {
                        MqttInstance.getInstance().conMqtt("13510530146", "123456", null);
                        HproseMqttClient client = MqttInstance.getInstance().getHproseMqttClient();
                        if (client == null) {
                            Log.i("test", "服务器异常");
                            return;
                        }
                        if (!MqttInstance.getInstance().getType()) {
                            //登录失败
                            Log.i("test", "登录失败");
                        } else {
                            //登录成功
                            Log.d("test", "登录成功");
                            //userName
                            //password
//            userType
//            PushInterface pushInterface = MqttInstance.getInstance().getPushInterface();
                            //登陆成功后必须先执行此方法,获取个人信息
//            getUserInfo(pushInterface);
//            cn.kwim.mqttcilent.common.Global.getInstance().getUserId()
                            String token = MqttInstance.getInstance().getHproseMqttClient().getToken();
                            Log.d("test", "token = " + token);

                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                }.start();

                break;
            case R.id.resiger:
//                startActivity(ResigerActivity.class);// 注册
                break;
            case R.id.scan:// 发送验证码
                break;
            case R.id.fooget_password:
//                startActivity(ForgetPasswordActivity.class);
                break;
        }
    }

    private void saveLoginInfo() {
//        SharedPreferencesUtil.save(this, IConstant.USER_NAME,// 保存用户名与密码
//                ViewUtil.getContent(this, R.id.user_name));
//        SharedPreferencesUtil.save(this, IConstant.PASSWORD,
//                ViewUtil.getContent(this, R.id.user_password));
    }

    /**
     * 登录错误提示
     */
    public void loginRemind() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                BaseHttpConnectPool.loodingDialog.close();
//                ViewUtil.showMessage(LoadingActivity.this, "用户名或者密码错误");
            }
        });

    }

    /**
     * 服务器异常提示
     */
    public void serverExceptionRemind() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                BaseHttpConnectPool.loodingDialog.close();
//                Toast.makeText(LoadingActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
