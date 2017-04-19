package com.alibaba.weex.extend.component;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;


//包括播放列表的播放器
public class MyVideoPlayer extends WXComponent<View> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView mVvv;

    public MyVideoPlayer(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        View view = View.inflate(getContext(), R.layout.layout_video, null);

        mVvv = (VideoView) view.findViewById(R.id.mvv);

        return view;
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
