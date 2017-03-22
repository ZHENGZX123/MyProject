package cn.kwim.mqttcilent.app_ui.home_school.friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.common.cache.javabean.FriendList;
import io.realm.RealmResults;

/**
 * Created by hmg on 2017/1/6.
 */

public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private RealmResults<FriendList> results = null;
    private LayoutInflater mInflater;

    public FriendListAdapter(Context context, RealmResults<FriendList> results){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.results = results;

    }

    @Override
    public int getCount() {
        return results!=null?results.size():0;
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        FriendList friendList = results.get(i);
        ViewHolder viewHolder =null;
        if(view==null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_friend_list,null);
            viewHolder.friendname = (TextView)view.findViewById(R.id.friend_name);
            viewHolder.friendheader = (ImageView)view.findViewById(R.id.friend_header);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.friendname.setText(friendList.getFriendnick());

        return view;
    }
    class ViewHolder{
        TextView friendname;
        ImageView friendheader;
    }
}
