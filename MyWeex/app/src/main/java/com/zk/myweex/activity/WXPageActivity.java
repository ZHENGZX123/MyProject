package com.zk.myweex.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.ui.component.NestedContainer;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXLogUtils;
import com.zk.myweex.R;
import com.zk.myweex.https.WXHttpManager;
import com.zk.myweex.https.WXHttpTask;
import com.zk.myweex.https.WXRequestListener;
import com.zk.myweex.utils.ScreenUtil;
import com.zk.myweex.utils.WXAnalyzerDelegate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


public class WXPageActivity extends AppCompatActivity implements IWXRenderListener, WXSDKInstance.NestedInstanceInterceptor {

    private static final String TAG = "WXPageActivity";
    public static final String WXPAGE = "wxpage";
    public static Activity wxPageActivityInstance;
    private ViewGroup mContainer;
    private WXSDKInstance mInstance;
    private Uri mUri;
    private HashMap mConfigMap = new HashMap<String, Object>();

    @Override
    public void onCreateNestInstance(WXSDKInstance instance, NestedContainer container) {
        Log.d(TAG, "Nested Instance created.");
    }

    private WXAnalyzerDelegate mWxAnalyzerDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxbase);
        setCurrentWxPageActivity(this);
        WXSDKEngine.setActivityNavBarSetter(new NavigatorAdapter());
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mUri = getIntent().getData();
        Bundle bundle = getIntent().getExtras();
        if (mUri == null && bundle == null) {
            Log.d("test", "错误页面");
        }
        if (bundle != null) {
            String bundleUrl = bundle.getString("bundleUrl");
            Log.i(TAG, "bundleUrl==" + bundleUrl);
            if (bundleUrl != null) {
                mConfigMap.put("bundleUrl", bundleUrl);
                mUri = Uri.parse(bundleUrl);
            }
        } else {
            mConfigMap.put("bundleUrl", mUri.toString());
        }
        if (mUri == null) {
            Toast.makeText(this, "the uri is empty!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Log.e("TestScript_Guide mUri==", mUri.toString());
        initUIAndData();

        if (WXPAGE.equals(mUri.getScheme())) {
            mUri = mUri.buildUpon().scheme("http").build();
            loadWXfromService(mUri.toString());
        } else if (TextUtils.equals("http", mUri.getScheme()) || TextUtils.equals("https", mUri.getScheme())) {
            String url = mUri.toString();
            loadWXfromService(url);
        } else {
            loadWXfromLocal(false);
        }
        mInstance.onActivityCreate();

        mWxAnalyzerDelegate = new WXAnalyzerDelegate(this);
        mWxAnalyzerDelegate.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onStart();
        }
    }

    private void loadWXfromLocal(boolean reload) {
        if (reload && mInstance != null) {
            mInstance.destroy();
            mInstance = null;
        }
        if (mInstance == null) {
            RenderContainer renderContainer = new RenderContainer(this);

            mInstance = new WXSDKInstance(this);
            mInstance.setRenderContainer(renderContainer);
            mInstance.registerRenderListener(this);
            mInstance.setNestedInstanceInterceptor(this);
            mInstance.setTrackComponent(true);
        }
        mContainer.post(new Runnable() {
            @Override
            public void run() {
                Activity ctx = WXPageActivity.this;
                Rect outRect = new Rect();
                ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
                String bundleUrl = mUri.toString();
                mConfigMap.put("bundleUrl", bundleUrl);

                Log.d("test", "bundleUrl = " + bundleUrl);


                if (bundleUrl.contains("/mnt/sdcard/")) {
                    //这里是跳页渲染，地址写死可能不行，要根据web传过来的路径，进行修改。如果js里面写了，也行。
                    String path = bundleUrl.replace("file://", "");
                    Log.d("test", "path1 = " + path);
                    if (path.contains("zip")) {
                        Log.d("test", "read zip");
                        mInstance.render(TAG, WXFileUtils.readFileInZip(path),
                                mConfigMap, null,
                                WXRenderStrategy.APPEND_ASYNC);
                    } else {
                        Log.d("test", "not a zip");
                        mInstance.render(TAG, WXFileUtils.readSDCardFile(path, WXPageActivity.this),
                                mConfigMap, null,
                                WXRenderStrategy.APPEND_ASYNC);
                    }
                } else {
                    String path = mUri.getScheme().equals("file") ? assembleFilePath(mUri) : mUri.toString();
                    Log.d("test", "path2 = " + path);
                    mInstance.render(TAG, WXFileUtils.loadAsset(path, WXPageActivity.this),
                            mConfigMap, null,
                            WXRenderStrategy.APPEND_ASYNC);
                }
            }
        });
    }

    private String assembleFilePath(Uri uri) {
        if (uri != null && uri.getPath() != null) {
            return uri.getPath().replaceFirst("/", "");
        }
        return "";
    }

    private void initUIAndData() {
        getSupportActionBar().hide();
        mContainer = (ViewGroup) findViewById(R.id.index_container);
    }

    private void loadWXfromService(final String url) {
        if (mInstance != null) {
            mInstance.destroy();
        }
        RenderContainer renderContainer = new RenderContainer(this);
        mContainer.addView(renderContainer);

        mInstance = new WXSDKInstance(this);
        mInstance.setRenderContainer(renderContainer);
        mInstance.registerRenderListener(this);
        mInstance.setNestedInstanceInterceptor(this);
        mInstance.setBundleUrl(url);
        mInstance.setTrackComponent(true);

        WXHttpTask httpTask = new WXHttpTask();
        httpTask.url = url;
        httpTask.requestListener = new WXRequestListener() {

            @Override
            public void onSuccess(WXHttpTask task) {
                Log.i(TAG, "into--[http:onSuccess] url:" + url);
                try {
                    mConfigMap.put("bundleUrl", url);
                    mInstance.render(TAG, new String(task.response.data, "utf-8"), mConfigMap, null, ScreenUtil.getDisplayWidth(WXPageActivity.this), ScreenUtil.getDisplayHeight(WXPageActivity.this), WXRenderStrategy.APPEND_ASYNC);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(WXHttpTask task) {
                Log.i(TAG, "into--[http:onError]");
                Toast.makeText(getApplicationContext(), "network error!", Toast.LENGTH_SHORT).show();
            }
        };

        WXHttpManager.getInstance().sendRequest(httpTask);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInstance != null) {
            mInstance.onActivityDestroy();
        }
        mContainer = null;
        if (wxPageActivityInstance == this) {
            wxPageActivityInstance = null;
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onDestroy();
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return (mWxAnalyzerDelegate != null && mWxAnalyzerDelegate.onKeyUp(keyCode, event)) || super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mInstance != null) {
            mInstance.onActivityResume();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onResume();
        }
    }

    public static Activity getCurrentWxPageActivity() {
        return wxPageActivityInstance;
    }

    public static void setCurrentWxPageActivity(Activity activity) {
        wxPageActivityInstance = activity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mInstance != null) {
            mInstance.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mInstance != null) {
            mInstance.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        WXLogUtils.e("into--[onViewCreated]");
        View wrappedView = null;
        if (mWxAnalyzerDelegate != null) {
            wrappedView = mWxAnalyzerDelegate.onWeexViewCreated(instance, view);
        }
        if (wrappedView != null) {
            view = wrappedView;
        }

        if (view.getParent() == null) {
            mContainer.addView(view);
        }
        mContainer.requestLayout();
        Log.d("WARenderListener", "renderSuccess");
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onWeexRenderSuccess(instance);
        }
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode,
                            String msg) {
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onException(instance, errCode, msg);
        }
        if (!TextUtils.isEmpty(errCode) && errCode.contains("|")) {
            String[] errCodeList = errCode.split("\\|");
            String code = errCodeList[1];
            String codeType = errCode.substring(0, errCode.indexOf("|"));

            if (TextUtils.equals("1", codeType)) {
                String errMsg = "codeType:" + codeType + "\n" + " errCode:" + code + "\n" + " ErrorInfo:" + msg;
                degradeAlert(errMsg);
                return;
            } else {
                Toast.makeText(getApplicationContext(), "errCode:" + errCode + " Render ERROR:" + msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void degradeAlert(String errMsg) {
        new AlertDialog.Builder(this)
                .setTitle("Downgrade success")
                .setMessage(errMsg)
                .setPositiveButton("OK", null)
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mInstance != null) {
            mInstance.onActivityPause();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mInstance != null) {
            mInstance.onActivityStop();
        }
        if (mWxAnalyzerDelegate != null) {
            mWxAnalyzerDelegate.onStop();
        }
    }


    private static class NavigatorAdapter implements IActivityNavBarSetter {

        @Override
        public boolean push(String param) {
            return false;
        }

        @Override
        public boolean pop(String param) {
            return false;
        }

        @Override
        public boolean setNavBarRightItem(String param) {
            return false;
        }

        @Override
        public boolean clearNavBarRightItem(String param) {
            return false;
        }

        @Override
        public boolean setNavBarLeftItem(String param) {
            return false;
        }

        @Override
        public boolean clearNavBarLeftItem(String param) {
            return false;
        }

        @Override
        public boolean setNavBarMoreItem(String param) {
            return false;
        }

        @Override
        public boolean clearNavBarMoreItem(String param) {
            return false;
        }

        @Override
        public boolean setNavBarTitle(String param) {
            return false;
        }
    }


}
