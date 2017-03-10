package com.alibaba.weex.extend.module;

import android.app.Activity;
import android.content.Intent;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import cn.xdeveloper.regionselector.RegionSelectActivity;
import cn.xdeveloper.regionselector.util.DBCopyUtil;

public class MyRegion extends WXModule {
    private JSCallback callback;

    @JSMethod(uiThread = true)
    public void selectRegion(JSCallback callback) {

        this.callback = callback;
        DBCopyUtil.copyDataBaseFromAssets(mWXSDKInstance.getContext(), "region.db");
        ((Activity) mWXSDKInstance.getContext()).startActivityForResult(new Intent(mWXSDKInstance.getContext(), RegionSelectActivity.class), 100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == 200) {
            String province = data.getStringExtra(RegionSelectActivity.REGION_PROVINCE);
            String city = data.getStringExtra(RegionSelectActivity.REGION_CITY);
            String area = data.getStringExtra(RegionSelectActivity.REGION_AREA);

            callback.invoke(province + city + area);
        }
    }

}
