package cn.kwim.mqttcilent.app_ui.home_school;

import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.kiway.IConstant;
import cn.kiway.activity.BaseActivity;
//import cn.kiway.common.ViewPhotosActivity;
import cn.kiway.utils.SharedPreferencesUtil;
import cn.kiway.utils.ViewUtil;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import cn.kwim.mqttcilent.common.http.OkhttpUtils;
import cn.kwim.mqttcilent.common.http.PercentListener;
import cn.kwim.mqttcilent.common.utils.DateUtil;
import cn.kwim.mqttcilent.common.utils.Utils;
import cn.kwim.mqttcilent.common.views.ProgressImageView;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;

import static com.zk.myweex.R.id;
import static com.zk.myweex.R.layout;
import static com.zk.myweex.R.string;

public class ChatAdapter extends BaseAdapter {

    private List<Message> lst;
    private Context context;
    private LayoutInflater mInflater;
    private boolean isdelete;

    private static final int TEXTR = 1;
    private static final int TEXTL = 2;
    private static final int PUSHR = 3;
    private static final int PUSHL = 4;
    private static final int FILER = 5;
    private static final int FILEL = 6;
    private static final int IMAGER = 7;
    private static final int IMAGEL = 8;
    private static final int VOICER = 9;
    private static final int VOICEL = 10;
    private static final int HOMEWORKR = 11;
    private static final int HOMEWORKL = 12;
    private static final int NOTICER = 13;
    private static final int NOTICEL = 14;
    private static final int ACTIVITYR = 15;
    private static final int ACTIVITYL = 16;
    private static final int LOCATIONR = 17;
    private static final int LOCATIONL = 18;
    private static final int TOUSERR = 19;
    private static final int TOUSERL = 20;
    private static final int LINKR = 21;
    private static final int LINKL = 22;
    private static final int MUSICR = 23;
    private static final int MUSICL = 24;
    private static final int RECALL = 25;


    public ChatAdapter(Context context) {
        this.lst = new ArrayList<>();
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 更新lst 实现
     */
    public void setLst(List<Message> list) {
        this.lst = list;
        notifyDataSetChanged();
    }

    public boolean getIsdelete() {
        return isdelete;

    }

    public void setIsdelete(boolean isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * 添加数据
     */
    public void addSendLst(Message message) {
        //BUG TODO
        if (this.lst == null) {
            this.lst = new ArrayList<>();
        }
        this.lst.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return lst != null ? lst.size() : 0;
    }

    @Override
    public Object getItem(int position) {

        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) getItem(position);
        System.out.print("=============" + message.toString());
        if (message.getMessageType().equals(DaoType.TYPY.TEXT)) {
            if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                return TEXTR;
            } else {
                return TEXTL;
            }
        } else if (message.getMessageType().equals(DaoType.TYPY.HOMEWORK)) {
            if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                return HOMEWORKR;
            } else {
                return HOMEWORKL;
            }

        } else if (message.getMessageType().equals(DaoType.TYPY.NOTICE)) {
            if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                return NOTICER;
            } else {
                return NOTICEL;
            }
        } else if (message.getMessageType().equals(DaoType.TYPY.IMAGE)) {
            if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                return IMAGER;
            } else {
                return IMAGEL;
            }
        } else if (message.getMessageType().equals(DaoType.TYPY.RECALLMSG)) {
            return RECALL;
        }
        return TEXTL;
    }

    @Override
    public int getViewTypeCount() {

        return 26;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Message message = lst.get(position);
        ViewHolder viewHolder = null;
        int type = getItemViewType(position);
        System.out.print("===================" + position);
        Log.i("=============", position + "");
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            //    convertView = mInflater.inflate(R.layout.im_a_chat_item_txt_right, null);
            switch (type) {
                case TEXTR: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_txt_right, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    viewHolder.resend = (TextView) convertView.findViewById(id.resend);
                    setViewHolder(convertView, viewHolder);

                    break;
                }
                case TEXTL: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_txt_left, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    setViewHolder(convertView, viewHolder);
                    break;
                }
                case HOMEWORKR: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_homework_right, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    viewHolder.resend = (TextView) convertView.findViewById(id.resend);
                    setViewHolder(convertView, viewHolder);
                    break;
                }
                case HOMEWORKL: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_homework_left, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    setViewHolder(convertView, viewHolder);
                    break;
                }
                case NOTICER: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_notice_right, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    viewHolder.resend = (TextView) convertView.findViewById(id.resend);
                    setViewHolder(convertView, viewHolder);
                    break;
                }
                case NOTICEL: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_notice_left, null);
                    viewHolder.tv_chatcontent = (TextView) convertView.findViewById(id.tv_chatcontent);
                    setViewHolder(convertView, viewHolder);
                    break;
                }
                case IMAGER: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_image_right, null);
                    setViewHolder(convertView, viewHolder);
                    viewHolder.resend = (TextView) convertView.findViewById(id.resend);
                    viewHolder.imageView = (ProgressImageView) convertView.findViewById(id.iv_chatcontent);
                    break;
                }
                case IMAGEL: {
                    convertView = mInflater.inflate(layout.im_a_chat_item_image_left, null);
                    setViewHolder(convertView, viewHolder);
                    viewHolder.iv_chatcontent = (SimpleDraweeView) convertView.findViewById(id.iv_chatcontent);
                    break;
                }
                case RECALL: {
//                    viewHolder.resend = (TextView) convertView.findViewById(R.id.resend);
                    convertView = mInflater.inflate(layout.im_a_chat_item_chexiao, null);
                    viewHolder.time = (TextView) convertView.findViewById(id.timestamp);
                }
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
         Uri uri = Uri.parse(message.getSendLogo());
        switch (type) {
            case TEXTR: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                if (message.getSendLogo().equals("null")||message.getSendLogo().equals(""))
                    uri = Uri.parse(SharedPreferencesUtil.getString(context,IConstant.USER_PIC));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + "1");
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                if (message.getIsSendOk().equals(DaoType.ISSENDOK.NO)) {
                    viewHolder.resend.setVisibility(View.VISIBLE);
                }
                break;
            }
            case TEXTL: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + "3");
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                break;
            }
            case HOMEWORKR: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                if (message.getSendLogo().equals("null")||message.getSendLogo().equals(""))
                    uri = Uri.parse(SharedPreferencesUtil.getString(context,IConstant.USER_PIC));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + type);
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                break;
            }
            case HOMEWORKL: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + "3");
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                break;
            }
            case NOTICER: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                if (message.getSendLogo().equals("null")||message.getSendLogo().equals(""))
                    uri = Uri.parse(SharedPreferencesUtil.getString(context,IConstant.USER_PIC));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + type);
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                break;
            }
            case NOTICEL: {
                viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                setView(message, viewHolder, uri, position);
                viewHolder.tv_chatcontent.setLongClickable(true);
                viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + "3");
                viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                break;
            }
            case IMAGER: {
                String url = null;
                if (message.getSendLogo().equals("null")||message.getSendLogo().equals(""))
                    uri = Uri.parse(SharedPreferencesUtil.getString(context,IConstant.USER_PIC));
                setView(message, viewHolder, uri, position);
                if (message.getIsSendOk().equals(DaoType.ISSENDOK.OK)) {
                    viewHolder.imageView.setProgress(100);
                    url = OkhttpUtils.base_pic_url + message.getMsg();
                    final Uri urii = Uri.parse(url);
                    viewHolder.imageView.setImageURI(urii);
                } else {
                    Uri urii;
                    if (message.getMsg().startsWith("content://")) {
                        urii = Uri.parse(message.getMsg());
                    } else {
                        urii = Uri.parse("file://" + message.getMsg());
                    }

                    viewHolder.imageView.setImageURI(urii);
                    setPercent(viewHolder, urii, message.getMeassgeId(), message.getId());
                    url = OkhttpUtils.base_pic_url + message.getMsg();

                    Log.i("url1", "111" + url);
                }
                viewHolder.imageView.setLongClickable(true);
                viewHolder.imageView.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() + "$"
                        + message.getReadType() + "$" + type);
                viewHolder.imageView.setOnCreateContextMenuListener(mListViewContextMenuListener);
                setOnclickImage(viewHolder, url);

                break;
            }
            case IMAGEL: {
                setView(message, viewHolder, uri, position);
                final Uri urii = Uri.parse(OkhttpUtils.base_pic_url + message.getMsg());
                viewHolder.iv_chatcontent.setImageURI(urii);
                viewHolder.iv_chatcontent.setLongClickable(true);
                viewHolder.iv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                        "$" + message.getReadType() + "$" + "2");
                viewHolder.iv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                viewHolder.iv_chatcontent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(context, PicturePreviewActivity.class);
//                        intent.putExtra("url", OkhttpUtils.base_pic_url + message.getMsg());
//                        intent.putExtra("indentify", context.getResources().getIdentifier("im_ic_picture_loadfailed",
//                                "drawable",
//                                context.getPackageName()));
//                        ((Activity) context).startActivity(intent);
                        BaseActivity activity = (BaseActivity) context;
                        List<String> list = new ArrayList<String>();
                        list.add(OkhttpUtils.base_pic_url + message.getMsg());
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(IConstant.BUNDLE_PARAMS, (ArrayList<String>) list);
                        bundle.putInt(IConstant.BUNDLE_PARAMS1, 1);
//                        activity.startActivity(ViewPhotosActivity.class, bundle);
                    }
                });
                break;
            }
            case RECALL: {
                viewHolder.time.setText(message.getSendName() + "撤回了一条消息");
                break;
            }

        }
        return convertView;
    }

    private void setOnclickImage(ViewHolder viewHolder, final String url) {
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, PicturePreviewActivity.class);
//                intent.putExtra("url", url);
//                intent.putExtra("indentify", context.getResources().getIdentifier("im_ic_picture_loadfailed",
//                        "drawable",
//                        context.getPackageName()));
//                ((Activity) context).startActivity(intent);
                BaseActivity activity = (BaseActivity) context;
                List<String> list = new ArrayList<String>();
                list.add(url);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList(IConstant.BUNDLE_PARAMS, (ArrayList<String>) list);
                bundle.putInt(IConstant.BUNDLE_PARAMS1, 1);
//                activity.startActivity(ViewPhotosActivity.class, bundle);
            }
        });
    }

    String path;

    private synchronized void setPercent(final ViewHolder viewHolder, Uri uri, final String messageId, final String
            groupId) {

        Log.i("Uri", uri.toString());
        if (uri.toString().startsWith("content://")) {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(1);
        } else {
            path = uri.toString().replace("file://", "");
        }
        if (Utils.getFileSize(path) > 100 * 1024) {
            Bitmap bitmap = ViewUtil.getSmallBitmap(path);
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            FileOutputStream fout = null;
            File file = new File("/sdcard/pintu/");
            file.mkdirs();
            String filename = file.getPath() + name;
            try {
                fout = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            path = filename;
        }
        OkhttpUtils.upLoadImg(path, new PercentListener() {
            @Override
            public void onPercent(final String percent) {
                Log.i("onPercent", percent);
                viewHolder.imageView.setProgress(Integer.parseInt(percent));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (percent.equals("100")) {
                    File file = new File(path);
                    Log.i("获得文件大小Tag", file.length() + "");
                    file.delete();
                }

            }

            @Override
            public void onReponse(String response) {
                try {
                    Converse converse = new Gson().fromJson(response, Converse.class);
                    if (converse.getStatusCode().equals("200")) {
                        List list = (List) converse.getData();
                        Map map = (Map) list.get(0);
                        String key = MqttInstance.getInstance().getPushInterface().sendMessage(groupId,
                                "{\"msg\":\"" + map.get("url").toString() + "\",\"type\":\"image\"}", "2");
                        Converse converse1 = new Gson().fromJson(key, Converse.class);
                        Map map1 = (Map) converse1.getData();
                        MessageDao.sendOk(messageId, map1.get("msgid").toString().replace(".0", ""), map.get("url")
                                .toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setView(Message message, ViewHolder viewHolder, Uri uri, int position) {
        if (position % 15 == 0) {
            viewHolder.time.setText(DateUtil.getTimeDisplay(DateUtil.getDate(Long.parseLong(message.getTime()))));
            viewHolder.time.setVisibility(View.VISIBLE);
        } else
            viewHolder.time.setVisibility(View.GONE);
        viewHolder.name.setText(message.getSendName());
        if (ViewUtil.getContent(viewHolder.name).equals("null"))
            viewHolder.name.setText("匿名");
        viewHolder.header.setImageURI(uri);
    }

    private void setViewHolder(View convertView, ViewHolder viewHolder) {
        viewHolder.time = (TextView) convertView.findViewById(id.timestamp);
        viewHolder.name = (TextView) convertView.findViewById(id.name);
        viewHolder.header = (SimpleDraweeView) convertView.findViewById(id.iv_userhead);
    }

    class ViewHolder {
        TextView tv_chatcontent;
        TextView time;
        TextView name;
        SimpleDraweeView header;
        ProgressImageView imageView;
        SimpleDraweeView iv_chatcontent;
        TextView resend;
    }

    /**
     * 过滤 显示GIF 动画
     *
     * @param gifTextView
     * @param content
     * @return
     */
    public static SpannableString handler(final TextView gifTextView, String content, Context context) {

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

    // context menu
    private static final int menu_copy = 101;
    private static final int menu_delete = 102;
    private static final int menu_chehui = 103;
    public static String selid = "-1";
    public static View view = null;
    private View.OnCreateContextMenuListener mListViewContextMenuListener = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            selid = v.getTag() + "";
            view = v;
            String s[] = selid.split("\\$");
            if (s[s.length - 1].equals("1")) {
                addMenuItem(menu, menu_copy, 0, string.copy);
                addMenuItem(menu, menu_delete, 0, string.delete);
                addMenuItem(menu, menu_chehui, 0, string.chehui);

            } else if (s[s.length - 1].equals("2")) {
//                addMenuItem(menu, menu_copy, 0, R.string.copy); //添加菜单(菜单,id,排序序号,名字) 多个会出现滚动条
                addMenuItem(menu, menu_delete, 0, string.delete);
                //addMenuItem(menu, menu_copy, 0, R.string.copy);
            } else if (s[s.length - 1].equals("3")) {
                addMenuItem(menu, menu_copy, 0, string.copy);
                addMenuItem(menu, menu_delete, 0, string.delete);

            } else {
                addMenuItem(menu, menu_delete, 0, string.delete);
                addMenuItem(menu, menu_chehui, 0, string.chehui);
            }
        }
    };

    private void addMenuItem(Menu menu, int itemId, int order, int string) {
        addMenuItem(menu, itemId, order, string, -1);
    }

    private void addMenuItem(Menu menu, int itemId, int order, int string, int iconRes) {
        MenuItem item = menu.add(0, itemId, order, string).setOnMenuItemClickListener(menuItemClick);
        if (iconRes > 0) {
            item.setIcon(iconRes);
        }
    }

    private MenuItem.OnMenuItemClickListener menuItemClick = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int itemId = item.getItemId();
            String obj[] = selid.split("\\$");
            switch (itemId) {
                case menu_copy:
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(lst.get(Integer.parseInt(obj[0])).getMsg());
                    break;
                case menu_delete:
                    MessageDao.clearChatDataById(obj[1]);
                    lst.remove(Integer.parseInt(obj[0]));
                    setIsdelete(true);
                    notifyDataSetChanged();
                    break;
                case menu_chehui:
                    String content = MqttInstance.getInstance().getPushInterface().recallMessage(obj[1]);
                    Converse converse = new Gson().fromJson(content, Converse.class);
                    if (converse.getStatusCode().equals("200")) {
                        Map map = (Map) converse.getData();
                        Object ob = map.get("status").toString();
                        if (ob != null && ob.toString().equals("fail")) {
                            String errinfo = map.get("errinfo").toString();
                            Toast.makeText(context, errinfo, Toast.LENGTH_SHORT).show();
                        } else {
                            MessageDao.recallMsg(obj[1]);
                        }
                    }
                    break;
                default:
                    return false;
            }

            return true;
        }
    };

}
