package cn.kiway.classcard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ClassNotifyAdapter extends BaseAdapter {
    Context context;

    public ClassNotifyAdapter(Context context) {
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

        return LayoutInflater.from(context).inflate(R.layout.item_class_notify, null);
    }
}
