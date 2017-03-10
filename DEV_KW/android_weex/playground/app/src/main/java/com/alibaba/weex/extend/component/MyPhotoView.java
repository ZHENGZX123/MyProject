package com.alibaba.weex.extend.component;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MyPhotoView extends WXComponent<ImageView> {

    private PhotoViewAttacher mAttacher;

    public MyPhotoView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        ImageView view = new ImageView(context);

        mAttacher = new PhotoViewAttacher(view);
        return view;
    }

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        Log.d("test", "url = " + url);
        final ImageView iv = ((ImageView) getHostView());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.getInstance().displayImage(url, iv, getLoaderOptions());

        mAttacher.update();
    }

    public DisplayImageOptions getLoaderOptions() {
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        displayImageOptionsBuilder.cacheInMemory(false);
        displayImageOptionsBuilder.cacheOnDisc(true);
        // RoundedBitmapDisplayer displayer = new RoundedBitmapDisplayer(10);
        // displayImageOptionsBuilder.displayer(displayer);
        DisplayImageOptions defaultDisplayImageOptions = displayImageOptionsBuilder
                .build();
        return defaultDisplayImageOptions;
    }
}
