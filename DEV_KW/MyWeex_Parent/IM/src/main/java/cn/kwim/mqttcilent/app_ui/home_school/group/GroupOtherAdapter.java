package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.Yjpty.R;


/**
 * Created by Administrator on 2017/1/5.
 */

public class GroupOtherAdapter extends BaseAdapter {
    private List<Object> lst;
    private LayoutInflater inflater;
    private Context context;

    public GroupOtherAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        lst = new ArrayList<Object>();
        lst.add("1");
        lst.add("1");
        lst.add("1");
        lst.add("1");
    }

    @Override
    public int getCount() {
        return lst!=null?lst.size():0;
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(viewHolder==null){
            convertView = inflater.inflate(R.layout.im_item_group_other, null);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    static class ViewHolder{

    }
}
