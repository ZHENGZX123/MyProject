package com.zk.myweex.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKInstance;
import com.zk.myweex.R;

public class WebViewActivity extends WXBaseActivity {

    private RelativeLayout rl;
    private EditText et;
    private Button go;
    private TextView title;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果是自己设置contentview，就要setcontainer
        setContentView(R.layout.activity_webview);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et = (EditText) findViewById(R.id.et);
        go = (Button) findViewById(R.id.go);
        rl = (RelativeLayout) findViewById(R.id.rl);
        title = (TextView) findViewById(R.id.title);
        pb = (ProgressBar) findViewById(R.id.pb);

        String url = getSharedPreferences("webview", 0).getString("url", "http://120.24.84.206/weex/0/0.js");
        et.setText(url);
        renderPageByURL(url);
    }

    public void clickInput(View v) {
        rl.setVisibility(View.VISIBLE);
    }

    public void clickGo(View view) {
        String url = et.getText().toString();
        if (url.equals("")) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!url.startsWith("http")) {
            Toast.makeText(this, "请以http或https开头", Toast.LENGTH_SHORT).show();
            return;
        }


        mInstance.destroy();
        RenderContainer renderContainer = new RenderContainer(this);
        mInstance = new WXSDKInstance(this);
        mInstance.setRenderContainer(renderContainer);
        mInstance.registerRenderListener(this);
        mInstance.setBundleUrl(url);
        mInstance.setTrackComponent(true);
        renderPageByURL(url);

        rl.setVisibility(View.GONE);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.d("test", "webview onRenderSuccess");
        super.onRenderSuccess(instance, width, height);
        String url = et.getText().toString();
        getSharedPreferences("webview", 0).edit().putString("url", url).commit();
//        WebViewActivity.this.title.setText(view.getTitle());
    }
}
