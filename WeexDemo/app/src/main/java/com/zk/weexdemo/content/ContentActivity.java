package com.zk.weexdemo.content;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;
import com.zk.weexdemo.main.bean.Module;
import com.zk.weexdemo.util.FileUtils;
import com.zk.weexdemo.util.HttpDownload;

import java.io.File;

public class ContentActivity extends AppCompatActivity implements IWXRenderListener {

    private WXSDKInstance mWXSDKInstance;
    private Module module;
    private String jsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_content);
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);

        module = (Module) getIntent().getSerializableExtra("module");


        jsPath = "/mnt/sdcard/weex/" + module.jsName + ".js";
        //1.判断本地是否有缓存
        if (new File(jsPath).exists()) {
            Log.d("test", "加载本地");
            //2.判断远程是否有新版本,如果有，下载新版本
            mWXSDKInstance.render("WXSample", FileUtils.loadFile(jsPath, this), null, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
        } else {
            Log.d("test", "下载jsbundle");
            downloadJSBundle(module);
        }
    }

    private void downloadJSBundle(final Module module) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpDownload httpDownload = new HttpDownload();
                int ret = httpDownload.downFile("http://120.24.84.206/weex/" + module.jsName + ".js", "/mnt/sdcard/weex/", module.jsName + ".js");
                Log.d("test", "下载返回值 ret = " + ret);
                if (ret != 0) {
                    Toast.makeText(ContentActivity.this, "加载模块失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Log.d("test", "加载本地");
                    String path = "/mnt/sdcard/weex/" + module.jsName + ".js";
                    mWXSDKInstance.render("WXSample", FileUtils.loadFile(path, ContentActivity.this), null, null, -1, -1, WXRenderStrategy.APPEND_ASYNC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {

    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWXSDKInstance != null) {
            mWXSDKInstance.onActivityDestroy();
        }
    }
}
