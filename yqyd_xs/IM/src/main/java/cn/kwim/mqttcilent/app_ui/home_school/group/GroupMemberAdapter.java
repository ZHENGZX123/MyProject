package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.Yjpty.R;
import cn.kiway.utils.ViewUtil;
import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;

/**
 * Created by Administrator on 2017/1/10.
 */

public class GroupMemberAdapter extends BaseAdapter {
    private List<GroupListMember> lst;
    private Context context;
    private LayoutInflater mInflater;

    public GroupMemberAdapter(Context context){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.lst = new ArrayList<>();

    }

    public void setLst(List<GroupListMember> lst){
        if(this.lst ==null){
            this.lst = new ArrayList<>();
        }
        this.lst = lst;
        notifyDataSetChanged();

    }
    @Override
    public int getCount() {
        return lst!=null?lst.size():0;
    }

    @Override
    public Object getItem(int i) {
        return lst.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GroupListMember groupListMember = lst.get(i);
        ViewHolder viewHolder = null;
        if(viewHolder == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.im_item,null);
            viewHolder.name= (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.title_view = (View)view.findViewById(R.id.catalog_view);
            //viewHodler.name = (TextView) view.findViewById(R.id.friend_name);
           // viewHodler.header = (ImageView) view.findViewById(R.id.friend_header);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();

        }
        int section = getSectionForPosition(i);
        if(i == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(groupListMember.getSortLetters());
            viewHolder.title_view.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
            viewHolder.title_view.setVisibility(View.GONE);
        }
        viewHolder.name.setText(groupListMember.getName());
        if(ViewUtil.getContent(viewHolder.name).equals(""))
            viewHolder.name.setText("匿名用户"+(i+1));
        return view;
    }
    static class ViewHolder{
        TextView name;
        ImageView header;
        TextView tvLetter;
        View title_view;

    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return lst.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = lst.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

}
