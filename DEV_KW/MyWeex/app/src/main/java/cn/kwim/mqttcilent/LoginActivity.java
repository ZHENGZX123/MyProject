package cn.kwim.mqttcilent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.common.utils.Md5;
import cn.kwim.mqttcilent.mqttclient.PushService;


public class LoginActivity extends BaseActivity implements View.OnClickListener{
    public static final String USERNAME = "username";
    public static final String PWD = "pwd";

    public static LoginActivity instance;
    private Button login_sub;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_login);
        initView();
        instance = this;
        //TODO 测试
        password.setText("123456");
    }
    public void initView() {
        login_sub = (Button) findViewById(R.id.login_sub);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login_sub.setOnClickListener(this);

    }
    /**
     * 保存登录账户名密码
     */
    private void saveLoginInfo(String userName, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE); //私有数据
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        password = Md5.MD5(password);
        editor.putString("userName", userName);
        editor.putString("password", password);
        editor.commit();//提交修改

    }
    /**
     * 从share中取数据
     */
    private void getLoginInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE); //私有数据
        //Global.getInstance().setUserName(sharedPreferences.getString("userName", ""));
        //Global.getInstance().setPassWord(sharedPreferences.getString("password", ""));

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_sub:
                String userName = username.getText().toString();
                String passWord = password.getText().toString();
                Intent intentService = new Intent(this, PushService.class);
                intentService.putExtra(USERNAME, userName);
                intentService.putExtra(PWD,passWord);

                saveLoginInfo(userName, passWord);
                startService(intentService);

                break;
            default:
                break;
        }
    }
    public void IntentActivty(){
        runOnUiThread(new  Runnable(){
            @Override
            public void run() {
                Intent  intent = new Intent(LoginActivity.this, KiwayMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
