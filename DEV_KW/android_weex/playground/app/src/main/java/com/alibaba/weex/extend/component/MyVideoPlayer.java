package com.alibaba.weex.extend.component;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;

import org.json.JSONArray;

import java.util.ArrayList;


//包括播放列表的播放器
public class MyVideoPlayer extends WXComponent<View> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView mVvv;
    private GridView gv;

    public MyVideoPlayer(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        View view = View.inflate(getContext(), R.layout.layout_video, null);

        mVvv = (VideoView) view.findViewById(R.id.mvv);
        gv = (GridView) view.findViewById(R.id.gv);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video v = videos.get(i);
                mVvv.setVideoURI(Uri.parse(v.url));
            }
        });

        return view;
    }

    private MyAdapter adapter;
    private ArrayList<Video> videos = new ArrayList<>();

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        Log.d("test", "url = " + url);

        mVvv.setMediaController(new MediaController(getContext()));
        mVvv.setOnPreparedListener(this);
        mVvv.setOnErrorListener(this);
        mVvv.setOnCompletionListener(this);

        try {
            JSONArray array = new JSONArray(url);
            for (int i = 0; i < array.length(); i++) {
                String temp = array.getString(i);
                Video v = new Video();
                v.url = temp;
                videos.add(v);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(getContext());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_gridview, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
        }

        @Override
        public int getCount() {
            return videos.size();
        }

        @Override
        public Video getItem(int arg0) {
            return videos.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

}
