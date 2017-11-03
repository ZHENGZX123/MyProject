package cn.kiway.mdm.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.Class;
import cn.kiway.mdm.entity.School;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.server;

/**
 * Created by Administrator on 2017/10/17.
 */

public class LoginActivity extends BaseActivity {

    private Button schollBtn;
    private Button classBtn;
    private EditText codeET;
    private EditText nameET;
    private School mSchool;
    private Class mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        schollBtn = (Button) findViewById(R.id.schollBtn);
        classBtn = (Button) findViewById(R.id.classBtn);
        codeET = (EditText) findViewById(R.id.codeET);
        nameET = (EditText) findViewById(R.id.nameET);
        //1.省市县、选学校、选班级
        //2.注册到后台:学校，学号，姓名，设备IMEI，华为token,手机品牌型号
        String brand = Build.BRAND;
        Log.d("test", "brand = " + brand);
        String model = Build.MODEL;
        Log.d("test", "model = " + model);
    }

    public void selectSchool(View view) {
        startActivityForResult(new Intent(this, EditAeraActivity.class), 999);
    }

    public void selectClass(View view) {
        if (mSchool == null) {
            toast("请先选择学校");
            return;
        }
        startActivityForResult(new Intent(this, ClassListActivity.class).putExtra("schoolId", mSchool.schoolId), 9999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 999) {
            mSchool = (School) data.getSerializableExtra("school");
            Log.d("test", "mSchool = " + mSchool.toString());
            schollBtn.setText(mSchool.name);
        } else if (requestCode == 9999 && resultCode == 9999) {
            mClass = (Class) data.getSerializableExtra("class");
            classBtn.setText(mClass.name);
        }
    }

    public void clickLogin(View view) {
        //1.客户端校验
        if (mSchool == null) {
            toast("请选择学校");
            return;
        }
        if (mClass == null) {
            toast("请选择班级");
            return;
        }

        final String code = codeET.getText().toString();
        if (TextUtils.isEmpty(code)) {
            toast("请填写学号");
            return;
        }
        final String name = nameET.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请填写姓名");
            return;
        }
        final String imei = Utils.getIMEI(this);

        String token = getSharedPreferences("kiway", 0).getString("token", "");
        Log.d("test", "token = " + token);
        //2.提交数据
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = server + "device/login";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.put("classId", mClass.id);
            param.put("studentNumber", code);
            param.put("mobileModel", Build.MODEL);
            param.put("mobileBrand", Build.BRAND);
            param.put("schoolId", mSchool.schoolId);
            param.put("name", name);
            param.put("IMEI", imei);
            param.put("id", "");
            param.put("platform", "Android");
            param.put("token", token);
            param.put("operation", "login");
            Log.d("test", "param = " + param.toString());
            client.post(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "post onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        String token = o.getJSONObject("data").getString("token");
                        Log.d("test", "login get token = " + token);
                        if (StatusCode == 200) {
                            toast("登录成功");
                            Utils.deviceRuntime(LoginActivity.this, imei, "1");
                            getSharedPreferences("kiway", 0).edit().putBoolean("login", true).commit();
                            getSharedPreferences("kiway", 0).edit().putString("username", name).commit();
                            getSharedPreferences("kiway", 0).edit().putString("studentNumber", code).commit();
                            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            toast("登录失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("请求失败，请稍后再试");
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
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
