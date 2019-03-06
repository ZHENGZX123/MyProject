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

public class WeekSessionAdapter extends BaseAdapter {
    TodaySessionHolder holder;
    Context context;

    public WeekSessionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 9;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_weeksession, null);
            holder = new TodaySessionHolder();
            holder.tx1 = convertView.findViewById(R.id.tx1);
            holder.tx2 = convertView.findViewById(R.id.tx2);
            holder.tx3 = convertView.findViewById(R.id.tx3);
            holder.tx4 = convertView.findViewById(R.id.tx4);
            holder.tx5 = convertView.findViewById(R.id.tx5);
            holder.tx6 = convertView.findViewById(R.id.tx6);
            convertView.setTag(holder);
        } else {
            holder = (TodaySessionHolder) convertView.getTag();
        }
        if (position == 5) {
            holder.tx2.setVisibility(View.GONE);
            holder.tx3.setVisibility(View.GONE);
            holder.tx4.setVisibility(View.GONE);
            holder.tx5.setVisibility(View.GONE);
            holder.tx6.setVisibility(View.GONE);
        } else {
            holder.tx2.setVisibility(View.VISIBLE);
            holder.tx3.setVisibility(View.VISIBLE);
            holder.tx4.setVisibility(View.VISIBLE);
            holder.tx5.setVisibility(View.VISIBLE);
            holder.tx6.setVisibility(View.VISIBLE);
        }
        switch (position) {
            case 0:
                holder.tx1.setText("1(08:00~08:40)");
                break;
            case 1:
                holder.tx1.setText("2(08:50~09:30)");
                break;
            case 2:
                holder.tx1.setText("3(08:40~10:20)");
                break;
            case 3:
                holder.tx1.setText("4(10:30~11:10)");
                break;
            case 4:
                holder.tx1.setText("5(11:20~12:00)");
                break;
            case 5:
                holder.tx1.setText("午休");
                break;
            case 6:
                holder.tx1.setText("6(14:30~15:10)");
                break;
            case 7:
                holder.tx1.setText("7(15:20~16:00)");
                break;
            case 8:
                holder.tx1.setText("8(16:10~16:50)");
                break;

        }
        holder.tx2.setText("历史\n10班");
        Utils.setTextSpannableColor(context, holder.tx2, holder.tx2.getText().toString());
        return convertView;
    }

    public class TodaySessionHolder {
        private TextView tx1, tx2, tx3, tx4, tx5, tx6;
    }
}
