package cn.kiway.mdm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.App;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AppListAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private ArrayList<App> apps;
    int index, num;

    public AppListAdapter(Context context, ArrayList<App> apps, int position, int num) {
        inflater = LayoutInflater.from(context);
        this.apps = apps;
        this.index = position;
        this.num = num;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.item_grid_app, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) rowView.findViewById(R.id.iv);
            holder.name = (TextView) rowView.findViewById(R.id.name);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        final int pos = position + index * num;//假设mPageSiez
        App app = apps.get(pos);
        holder.name.setText(app.name);
        if (app.icon == null) {
            holder.iv.setImageResource(R.mipmap.ic_launcher);
        } else {
            holder.iv.setImageDrawable(app.icon);
        }
        return rowView;
    }

    public class ViewHolder {
        public ImageView iv;
        public TextView name;
    }

    @Override
    public int getCount() {
       // return apps.size();
        return apps.size() > (index + 1) * num ?
                num : (apps.size() - index * num);
    }

    @Override
    public App getItem(int arg0) {
        return apps.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
}
