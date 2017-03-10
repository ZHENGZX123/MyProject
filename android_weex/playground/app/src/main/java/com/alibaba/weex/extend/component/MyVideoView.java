package com.alibaba.weex.extend.component;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MyVideoView extends WXComponent<VideoView> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView mVvv;

    public MyVideoView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected VideoView initComponentHostView(@NonNull Context context) {
        mVvv = new VideoView(context);
        return mVvv;
    }

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        Log.d("test", "url = " + url);
        mVvv.setVideoURI(Uri.parse(url));
        mVvv.setMediaController(new MediaController(getContext()));
        mVvv.setOnPreparedListener(this);
        mVvv.setOnErrorListener(this);
        mVvv.setOnCompletionListener(this);
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("test", "completion");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.d("test", "error");
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("test", "prepare");
        mVvv.start();
    }
}
