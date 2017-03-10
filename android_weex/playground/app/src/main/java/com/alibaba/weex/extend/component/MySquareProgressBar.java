package com.alibaba.weex.extend.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.weex.extend.view.SquareProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;


public class MySquareProgressBar extends WXComponent<SquareProgressBar> {

    public MySquareProgressBar(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected SquareProgressBar initComponentHostView(@NonNull Context context) {
        final SquareProgressBar spb = new SquareProgressBar(context);

        ImageLoader.getInstance().loadImage("http://www.kiway.cn/images/421c.jpg", new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override

            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spb.setImage(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        spb.setColor("#C9C9C9");
        spb.setWidth(8);
        return spb;
    }

    @WXComponentProp(name = "progress")
    public void setProgress(int progress) {
        ((SquareProgressBar) getHostView()).setProgress(progress);
    }

    @WXComponentProp(name = "max")
    public void setMax(int max) {
//        ((SquareProgressBar) getHostView()).setMax(max);
    }



}
