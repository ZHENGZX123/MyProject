package com.alibaba.weex.extend.module;

import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

public class MyToast extends WXModule {

  @JSMethod(uiThread = true)
  public void showToast(String msg) {
    Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_SHORT).show();
  }
}
