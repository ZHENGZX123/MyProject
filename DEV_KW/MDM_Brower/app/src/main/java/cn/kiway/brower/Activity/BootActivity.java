package cn.kiway.brower.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.kiway.entity.Network;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import cn.kiway.brower.Database.RecordAction;
import cn.kiway.brower.Ninja.R;

import static cn.kiway.brower.Activity.BrowserActivity.all_network;
import static cn.kiway.brower.Activity.BrowserActivity.enable_type;
import static cn.kiway.brower.utils.HttpUtil.clientUrl;

/**
 * Created by Administrator on 2018/3/16.
 */

public class BootActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_boot);
        getAppData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getWebUrl();
            }
        },1500);
    }

    private void getWebUrl() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-auth-token",getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
        client.setTimeout(10000);
        String url = clientUrl + "device/parent/child/operation";
        client.get(this, url, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                Log.d("test", "childOperation onSuccess = " + ret);
                RecordAction.addGridView(BootActivity.this,"开维教育", "http://mdm.kiway.cn:8083/static/front/index.html#/device-list", "http://www.hishop.com" +
                        ".cn/uploads/150914/21678-150914112244436.jpg");
                RecordAction.addGridView(BootActivity.this,"开维教育", "http://mdm.kiway.cn:8083/static/front/index.html#/device-list", "http://www.hishop.com" +
                        ".cn/uploads/150914/21678-150914112244436.jpg");
                RecordAction.addGridView(BootActivity.this,"开维教育", "http://www.kiway.cn/", "http://www.hishop.com" +
                        ".cn/uploads/150914/21678-150914112244436.jpg");
                startActivity(new Intent(BootActivity.this,BrowserActivity.class));
                finish();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.d("test", "childOperation onFailure = " + s);
                startActivity(new Intent(BootActivity.this,BrowserActivity.class));
                finish();
            }

        });
    }

    protected void getAppData() {
        Intent intent = getIntent();
        if (intent != null) {
            enable_type = intent.getIntExtra("enable_type", -1);
            all_network = (ArrayList<Network>) intent.getSerializableExtra("all_network");
            getSharedPreferences("kiway", 0).edit().putString("x-auth-token", intent.getStringExtra("x-auth-token"))
                    .commit();
            Log.e("BrowerActivity:::::", "enable_type====" + enable_type);
            Log.e("BrowerActivity:::::", "all_network====" + all_network);
            Log.e("BrowerActivity:::::", "x-auth-token====" + intent.getStringExtra("x-auth-token"));
        } else {
            Log.e("BrowerActivity:::::", "没有传值");
        }
    }


}
