package cn.kwim.mqttcilent.app_ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.Yjpty.R;
import cn.kwim.mqttcilent.app_ui.home_school.ChatActivity;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import io.realm.RealmResults;

public class HomeSchoolAdapter extends BaseAdapter {

    private Context context;
    private RealmResults<MainList> list = null;
    private LayoutInflater inflater;

    public HomeSchoolAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void setList(RealmResults<MainList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MainList mainList = list.get(position);
        Log.e("时间：----", mainList.getName() + ";:::" + mainList.getTime());
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.im_home_school_item, null);
            viewHolder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
            viewHolder.iv_header = (SimpleDraweeView) convertView.findViewById(R.id.iv_header);
            viewHolder.tv_class = (TextView) convertView.findViewById(R.id.tv_class);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.unread_group_msg = (TextView) convertView.findViewById(R.id.unread_group_msg);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_class);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mainList.getMsgType().equals(DaoType.TYPY.TEXT)) {
            if (!mainList.getMsg().isEmpty()) {
                viewHolder.tv_content.setText(handler(viewHolder.tv_content, mainList.getSendName() + "：" + mainList.getMsg(), context));
            } else {
                viewHolder.tv_content.setText("");
            }
        } else if (mainList.getMsgType().equals(DaoType.TYPY.IMAGE)) {
            viewHolder.tv_content.setText(mainList.getSendName() + "：【图片】");
        } else if (mainList.getMsgType().equals(DaoType.TYPY.TEXT)) {
            viewHolder.tv_content.setText(mainList.getSendName() + "：【通知】" + mainList.getMsg());

        } else if (mainList.getMsgType().equals(DaoType.TYPY.TEXT)) {
            viewHolder.tv_content.setText(mainList.getSendName() + "：【作业】" + mainList.getMsg());
        }

        viewHolder.tv_class.setText(mainList.getName());
        if (!mainList.getUg_type().equals("1"))
            viewHolder.tv_class.setText(mainList.getName() + "讨论组");
        if (mainList.getNumber().equals("0")) {
            viewHolder.unread_group_msg.setVisibility(View.GONE);
        } else {
            viewHolder.unread_group_msg.setVisibility(View.VISIBLE);
            viewHolder.unread_group_msg.setText(mainList.getNumber());

        }

        viewHolder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(ChatActivity.CHATTYPE, DaoType.SESSTIONTYPE.GROUP);
                intent.putExtra(ChatActivity.VTYPE, ChatActivity.CHAT);
                intent.putExtra(ChatActivity.GROUPID, mainList.getId());
                intent.putExtra(ChatActivity.QUNORTAO, mainList.getUg_type());
                ((Activity) context).startActivityForResult(intent, HomeSchoolFragment.UPDATE);
                //((Activity) context).overridePendingTransition(R.anim.im_slide_in_from_left,R.anim.im_slide_out_to_right);
                ((Activity) context).overridePendingTransition(R.anim.im_slide_in_from_right, R.anim.im_slide_out_to_left);
            }
        });

        Uri uri = Uri.parse(mainList.getLogo());
        viewHolder.iv_header.setImageURI(uri);

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout rl_item;
        SimpleDraweeView iv_header;
        TextView tv_class;
        TextView tv_content;
        TextView unread_group_msg;
        TextView time;
    }

    /**
     * 过滤 显示GIF 动画
     *
     * @param gifTextView
     * @param content
     * @return
     */
    public SpannableString handler(final TextView gifTextView, String content, Context context) {

        SpannableString sb = new SpannableString(content);
        String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
        String[] s = content.split("\\d{3}(.png\\]\\#)");

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);

        while (m.find()) {
            String tempText = m.group();
            try {
                String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
                try {
                    sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m
                            .start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
                 /*
                String num = tempText.substring("#[im_face/png/f_static_".length(), tempText.length() - ".png]#"
                .length());
                //if (s.length > 5) {
                    String png = "face/gif/f" + num + ".png";
                    sb.setSpan(
                            new ImageSpan(context, BitmapFactory
                                    .decodeStream(context.getAssets().open(png))), sb.length()
                                    - tempText.length(), sb.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

              } else {
                    String gif = "face/gif/f" + num + ".gif";
                    *//**
                 * 如果open这里不抛异常说明存在gif，则显示对应的gif
                 * 否则说明gif找不到，则显示png
                 * *//*
                    InputStream is = context.getAssets().open(gif);

                    sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is, new AnimatedGifDrawable
                    .UpdateListener() {
                                @Override
                                public void update() {
                                    gifTextView.postInvalidate();
                                }
                            })), m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    is.close();
                }*/
            } catch (Exception e) {
                String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
                try {
                    sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m
                            .start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return sb;
    }
}