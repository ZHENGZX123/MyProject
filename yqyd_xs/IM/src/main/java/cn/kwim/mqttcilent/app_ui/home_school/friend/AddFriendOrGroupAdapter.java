package cn.kwim.mqttcilent.app_ui.home_school.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kiway.Yjpty.R;
/**
 * Created by hmg on 2017/1/6.
 */

public class AddFriendOrGroupAdapter extends BaseAdapter{
    private Context context;
    private List<Object> list;
    private LayoutInflater mInflater;

    public AddFriendOrGroupAdapter(Context context){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list = new ArrayList<Object>();
    }

    public void setLst(List<Object> lst){
        this.list = lst;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Map map = (Map)list.get(i);
        ViewHolder viewHolder = null;
        if(viewHolder == null){

            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_friend_list,null);
            viewHolder.friendheader = (ImageView) view.findViewById(R.id.friend_header);
            viewHolder.friendname = (TextView) view.findViewById(R.id.friend_name);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.friendname.setText(map.get("nickname").toString());

        return view;
    }

    public static class ViewHolder{
        ImageView friendheader;
        TextView friendname;

    }

}
