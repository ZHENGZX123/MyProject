package com.android.kiway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.kiway.entity.SMS;
import com.android.kiway.utils.Utils;
import com.android.launcher3.R;

import java.util.List;


/**
 * 消息ListView的Adapter
 *
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter {

    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;// 收到对方的消息
        int IMVT_TO_MSG = 1;// 自己发送出去的消息
    }

    private static final int ITEMCOUNT = 2;// 消息类型的总数
    private List<SMS> coll;// 消息对象数组
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<SMS> coll) {
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
     */
    public int getItemViewType(int position) {
        SMS entity = coll.get(position);
        if (entity.froms == 1) {//收到的消息
            return IMsgViewType.IMVT_COM_MSG;
        } else {//自己发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    /**
     * Item类型的总数
     */
    public int getViewTypeCount() {
        return ITEMCOUNT;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        SMS entity = coll.get(position);
        boolean isComMsg = (entity.froms == 1);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (isComMsg) {
                convertView = mInflater.inflate(
                        R.layout.chatting_item_msg_text_left, null);
            } else {
                convertView = mInflater.inflate(
                        R.layout.chatting_item_msg_text_right, null);
            }
            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView
                    .findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView
                    .findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_chatcontent);
            viewHolder.isComMsg = isComMsg;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(Utils.longToDate(entity.time));
        viewHolder.tvUserName.setText(entity.name);
        viewHolder.tvContent.setText(entity.content);
        return convertView;
    }

    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }

}
