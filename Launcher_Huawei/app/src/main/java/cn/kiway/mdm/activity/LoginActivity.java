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
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.School;

/**
 * Created by Administrator on 2017/10/17.
 */

public class LoginActivity extends BaseActivity {


    private Button schollBtn;
    private EditText classET;
    private EditText codeET;
    private EditText nameET;
    private School mSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        schollBtn = (Button) findViewById(R.id.schollBtn);
        classET = (EditText) findViewById(R.id.classET);
        codeET = (EditText) findViewById(R.id.codeET);
        nameET = (EditText) findViewById(R.id.nameET);

        //1.省市县、选学校、选班级

        //2.注册到后台:学校，学号，姓名，设备IMEI，华为token,手机品牌型号
        //Utils.register(context, tm.getDeviceId(), token);
        String brand = Build.BRAND;
        Log.d("test", "brand = " + brand);
        String model = Build.MODEL;
        Log.d("test", "model = " + model);
    }

    public void selectSchool(View view) {
        startActivityForResult(new Intent(this, EditAeraActivity.class), 999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == 999) {
            mSchool = (School) data.getSerializableExtra("school");
            Log.d("test", "mSchool = " + mSchool.toString());
            schollBtn.setText(mSchool.name);
        }
    }

    public void clickLogin(View view) {
        //1.客户端校验
        if (mSchool == null) {
            toast("请选择学校");
            return;
        }

        String code = codeET.getText().toString();
        String name = nameET.getText().toString();
        if (TextUtils.isEmpty(code)) {
            toast("请填写学号");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            toast("请填写姓名");
            return;
        }
        //2.提交数据
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = "";
            String param = "";
            StringEntity stringEntity = new StringEntity(param, "utf-8");
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "post onSuccess = " + ret);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
        }
    }
}
