package com.crazy.wang.photoscan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.crazy.wang.photoscan.R;
import com.crazy.wang.photoscan.util.DisplayImageOptionsUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author Cloudsoar(wangyb)
 * @datetime 2015-11-19 20:17 GMT+8
 * @email 395044952@qq.com
 */
public class GridAdapter extends ArrayAdapter {
    private Context mContext;
    private int mResource;
    List<String> mImgUrls;

    public GridAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mImgUrls = objects;
    }

    @Override
    public int getCount() {
        return mImgUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return mImgUrls.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(mResource, null);
            holder.mImageView = (ImageView) view.findViewById(R.id.img);
            ImageLoader.getInstance().displayImage(
                    mImgUrls.get(position), holder.mImageView, DisplayImageOptionsUtil.defaultOptions);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }

    class ViewHolder {
        ImageView mImageView;
    }
}
