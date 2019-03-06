package cn.kiway.classcard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/6.
 */

public class TodaySessionAdapter extends BaseAdapter {
    TodaySessionHolder holder;
    Context context;

    public TodaySessionAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_todaysession, null);
            holder = new TodaySessionHolder();
            holder.tx1 = convertView.findViewById(R.id.tx1);
            holder.tx2 = convertView.findViewById(R.id.tx2);
            holder.tx3 = convertView.findViewById(R.id.tx3);
            holder.tx4 = convertView.findViewById(R.id.tx4);
            convertView.setTag(holder);
        } else {
            holder = (TodaySessionHolder) convertView.getTag();
        }
        if (position == 5) {
            holder.tx2.setVisibility(View.GONE);
            holder.tx3.setVisibility(View.GONE);
            holder.tx4.setVisibility(View.GONE);
        } else {
            holder.tx2.setVisibility(View.VISIBLE);
            holder.tx3.setVisibility(View.VISIBLE);
            holder.tx4.setVisibility(View.VISIBLE);
        }
        if (position < 5) {
            holder.tx1.setText(position + 1 + "");
        } else if (position > 5) {
            holder.tx1.setText(position+"");
        } else if (position == 5) {
            holder.tx1.setText("午  休");
        }
        return convertView;
    }

    public class TodaySessionHolder {
        private TextView tx1, tx2, tx3, tx4;
    }
}
