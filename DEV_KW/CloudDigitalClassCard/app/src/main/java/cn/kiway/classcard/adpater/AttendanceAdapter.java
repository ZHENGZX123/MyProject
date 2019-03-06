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
 * Created by Administrator on 2019/3/6.
 */

public class AttendanceAdapter extends BaseAdapter {
    Context context;
    AttendanceHolder holder;

    public AttendanceAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 80;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_attendance, null);
            holder = new AttendanceHolder();
            holder.img = convertView.findViewById(R.id.img);
            holder.name = convertView.findViewById(R.id.name);
            holder.time = convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (AttendanceHolder) convertView.getTag();
        }
        return convertView;
    }

    public class AttendanceHolder {
        public ImageView img;
        public TextView name, time;
    }
}
