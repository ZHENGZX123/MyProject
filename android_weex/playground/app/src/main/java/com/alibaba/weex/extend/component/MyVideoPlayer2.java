package com.alibaba.weex.extend.component;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.alibaba.weex.PlayVideoActivity;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class MyVideoPlayer2 extends WXComponent<View> implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private VideoView mVvv;
    private GridView gv;
    private Button full;
    private String currentUrl;

    public MyVideoPlayer2(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected View initComponentHostView(@NonNull Context context) {
        View view = View.inflate(getContext(), R.layout.layout_video2, null);

        mVvv = (VideoView) view.findViewById(R.id.mvv);
        gv = (GridView) view.findViewById(R.id.gv);
        full = (Button) view.findViewById(R.id.full);

        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video v = videos.get(i);
                currentUrl = v.url;
                mVvv.setVideoURI(Uri.parse(v.url));
            }
        });

        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVvv.pause();
                Intent i = new Intent(getContext(), PlayVideoActivity.class);
                i.putExtra("url", currentUrl);
                i.putExtra("position", mVvv.getCurrentPosition());
                ((Activity) getContext()).startActivityForResult(i, 888);
            }
        });

        return view;
    }

    private MyAdapter adapter;
    private ArrayList<Video> videos = new ArrayList<>();

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        Log.d("test", "url = " + url);
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
        Log.d("test", "为毛回来就调用prepare");
        if (backFromFull) {
            backFromFull = false;
            mVvv.start();
            mVvv.seekTo(position);
        } else {
            mVvv.start();
        }
    }

    private boolean backFromFull = false;
    private int position = 0;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "onActivityResult , requestcode = " + requestCode + " , resultcode = " + resultCode);

        backFromFull = true;
        position = data.getIntExtra("position", 0);
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
