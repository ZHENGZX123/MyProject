package cn.kiway.autoreply.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.kiway.autoreply.R;
import cn.kiway.autoreply.activity.BaseActivity;

/**
 * Created by Administrator on 2018/4/2.
 */

public class LvCarIdsDailogAdapter extends BaseAdapter {
    private BaseActivity activity;
    JSONArray array = new JSONArray();
    boolean isRoom;
    LVCarIdHolder holder;

    public LvCarIdsDailogAdapter(BaseActivity activity, JSONArray array, boolean isRoom) {
        this.activity = activity;
        this.array = array;
        this.isRoom = isRoom;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        return array.optJSONObject(i);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new LVCarIdHolder();
            view = View.inflate(activity, R.layout.carids_dialog_item, null);
            holder.tvCarId = (TextView) view.findViewById(R.id.tv_carId);
            view.setTag(holder);
        } else {
            holder = (LVCarIdHolder) view.getTag();
        }
        String content = "";
        JSONObject item = array.optJSONObject(i);
        if (isRoom) {
            if (!item.optString("nickname").equals("")) {//备注
                content = item.optString("nickname");
            } else {
                content = item.optString("displayname");
            }
        } else {
            if (!item.optString("remark").equals("")) {//备注
                content = item.optString("remark");
            } else {
                content = item.optString("nickname");
            }
        }
        holder.tvCarId.setText(content);
        return view;
    }

    public class LVCarIdHolder {
        public TextView tvCarId;
    }
}
