package com.alibaba.weex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.weex.commons.AbstractWeexActivity;
import com.google.zxing.client.android.CaptureActivity;
import com.taobao.weex.RenderContainer;
import com.taobao.weex.WXRenderErrorCode;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.utils.WXFileUtils;
import com.taobao.weex.utils.WXSoInstallMgrSdk;
import com.wjc.R;

public class IndexActivity extends AbstractWeexActivity {

    private static final String TAG = "IndexActivity";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 0x1;
    private static final String DEFAULT_IP = "your_current_IP";
    private static String sCurrentIp = DEFAULT_IP; // your_current_IP

    private ProgressBar mProgressBar;
    private TextView mTipView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        setContainer((ViewGroup) findViewById(R.id.index_container));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);


        mProgressBar = (ProgressBar) findViewById(R.id.index_progressBar);
        mTipView = (TextView) findViewById(R.id.index_tip);
        mProgressBar.setVisibility(View.VISIBLE);
        mTipView.setVisibility(View.VISIBLE);


        if (!WXSoInstallMgrSdk.isCPUSupport()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mTipView.setText(R.string.cpu_not_support_tip);
            return;
        }
        RenderContainer renderContainer = new RenderContainer(this);
        //kiway/module/mycamera.js
        if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
            renderPage(WXFileUtils.loadAsset("index.js", this), getIndexUrl());
        } else {
            renderPageByURL(getIndexUrl());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
            getMenuInflater().inflate(R.menu.main_scan, menu);
        } else {
            getMenuInflater().inflate(R.menu.main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                if (!TextUtils.equals(sCurrentIp, DEFAULT_IP)) {
                    createWeexInstance();
                    renderPageByURL(getIndexUrl());
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.action_scan:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
                    } else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    }
                } else {
                    startActivity(new Intent(this, CaptureActivity.class));
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, CaptureActivity.class));
        } else {
            Toast.makeText(this, "request camara permission fail!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRenderSuccess(WXSDKInstance wxsdkInstance, int i, int i1) {
        super.onRenderSuccess(wxsdkInstance, i, i1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.GONE);
    }

    @Override
    public void onException(WXSDKInstance wxsdkInstance, String s, String s1) {
        super.onException(wxsdkInstance, s, s1);
        mProgressBar.setVisibility(View.GONE);
        mTipView.setVisibility(View.VISIBLE);
        if (TextUtils.equals(s, WXRenderErrorCode.WX_NETWORK_ERROR)) {
            mTipView.setText(R.string.index_tip);
        } else {
            mTipView.setText("render error:" + s1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private static String getIndexUrl() {
        return "http://" + sCurrentIp + ":12580/examples/build/index.js";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mInstance.onActivityResult(requestCode, resultCode, data);
    }
}

