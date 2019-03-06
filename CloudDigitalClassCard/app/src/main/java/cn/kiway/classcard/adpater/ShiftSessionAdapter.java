package cn.kiway.classcard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.kiway.classcard.R;
import cn.kiway.classcard.utils.Utils;

/**
 * Created by Administrator on 2019/3/6.
 */

public class ShiftSessionAdapter extends BaseAdapter {
    TodaySessionHolder holder;
    Context context;

    public ShiftSessionAdapter(Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_shiftsession, null);
            holder = new TodaySessionHolder();
            holder.tx1 = convertView.findViewById(R.id.tx1);
            holder.tx2 = convertView.findViewById(R.id.tx2);
            holder.tx3 = convertView.findViewById(R.id.tx3);
            holder.tx4 = convertView.findViewById(R.id.tx4);
            holder.tx5 = convertView.findViewById(R.id.tx5);
            holder.tx6 = convertView.findViewById(R.id.tx6);
            holder.tx7 = convertView.findViewById(R.id.tx7);
            holder.tx8 = convertView.findViewById(R.id.tx8);
            holder.tx9 = convertView.findViewById(R.id.tx9);
            convertView.setTag(holder);
        } else {
            holder = (TodaySessionHolder) convertView.getTag();
        }
        Utils.setTextSpannableSize(holder.tx1, holder.tx1.getText().toString());
        Utils.setTextSpannableSize(holder.tx2, holder.tx2.getText().toString());
        Utils.setTextSpannableSize(holder.tx3, holder.tx3.getText().toString());
        Utils.setTextSpannableSize(holder.tx4, holder.tx4.getText().toString());
        Utils.setTextSpannableSize(holder.tx5, holder.tx5.getText().toString());
        Utils.setTextSpannableSize(holder.tx6, holder.tx6.getText().toString());
        Utils.setTextSpannableSize(holder.tx7, holder.tx7.getText().toString());
        Utils.setTextSpannableSize(holder.tx8, holder.tx8.getText().toString());
        Utils.setTextSpannableSize(holder.tx9, holder.tx9.getText().toString());
        return convertView;
    }

    public class TodaySessionHolder {
        private TextView tx1, tx2, tx3, tx4, tx5, tx6, tx7, tx8, tx9;
    }
}
