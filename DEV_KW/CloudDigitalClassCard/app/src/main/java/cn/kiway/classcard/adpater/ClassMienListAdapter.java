package cn.kiway.classcard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ClassMienListAdapter extends BaseAdapter {
    ClassMienHolder holder;
    Context context;

    public ClassMienListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mien_list, null);
            holder = new ClassMienHolder();
            holder.img = convertView.findViewById(R.id.img);
            holder.title = convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ClassMienHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ClassMienHolder {
        public TextView title;
        public ImageView img;
    }
}
