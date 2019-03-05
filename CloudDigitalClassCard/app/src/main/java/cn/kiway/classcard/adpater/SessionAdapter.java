package cn.kiway.classcard.adpater;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 */

public class SessionAdapter extends BaseAdapter {
    Context context;
    SessionHolder holder;

    public SessionAdapter(Context context) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_session, null);
            holder = new SessionHolder();
            holder.content = convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (SessionHolder) convertView.getTag();
        }
        if (32 <= position && position <= 39) {
            if (position == 35) {
                holder.content.setText("午休");
                holder.content.setGravity(Gravity.RIGHT);
            } else {
                holder.content.setText("");
            }
        }
        return convertView;
    }

    public class SessionHolder {
        TextView content;
    }
}
