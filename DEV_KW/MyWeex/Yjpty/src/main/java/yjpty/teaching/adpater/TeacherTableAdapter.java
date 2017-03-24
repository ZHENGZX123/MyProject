package yjpty.teaching.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yjpty.teaching.R;
import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.acitivity.OnSessionActivity;
import yjpty.teaching.dialog.LoginDialog;
import yjpty.teaching.http.BaseHttpHandler;
import yjpty.teaching.http.HttpHandler;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.SharedPreferencesUtil;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.wifi.WifiAdmin;


public class TeacherTableAdapter extends ArrayAdapter<VideoModel>
        implements
        OnClickListener, HttpHandler {
    BaseActivity activity;
    TeacherTableHolder holder;
    boolean isAttendClass;// 1 上课 2 看详情
    public List<VideoModel> list;// 显示在界面上的数据
    public List<VideoModel> listData;// 原始全部数据
    private MyFilter mFilter;
    /**
     * 请求回调
     */
    protected BaseHttpHandler adapterHandler = new BaseHttpHandler(this) {
    };
    /**
     * 等待框
     */
    public static LoginDialog dialog;
    /**
     * 是否为看视频
     */
    boolean view;
    public int position;
    boolean isTeacherSession;

    public TeacherTableAdapter(Context context, List<VideoModel>
            list, boolean isTeacherSession) {
        super(context, -1);
        this.activity = (BaseActivity) context;
        this.list = list;
        listData = new ArrayList<VideoModel>();
        this.isTeacherSession = isTeacherSession;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.yjpty_teacher_table_list_item, null);
            holder = new TeacherTableHolder();
            holder.pic = ViewUtil.findViewById(view, R.id.pic);
            holder.videoName = ViewUtil.findViewById(view, R.id.name);
            holder.videoTime = ViewUtil.findViewById(view, R.id.time);
            holder.view = ViewUtil.findViewById(view, R.id.view);
            holder.look = ViewUtil.findViewById(view, R.id.look);
            holder.type = ViewUtil.findViewById(view, R.id.type);
            holder.typetxt = ViewUtil.findViewById(view, R.id
                    .type_txt);
            holder.isPlay = ViewUtil.findViewById(view, R.id.isplay);
            holder.Jw = ViewUtil.findViewById(view, R.id.jw);
            holder.sessionName = ViewUtil.findViewById(view, R.id
                    .session_name);
            view.setTag(holder);
        } else {
            holder = (TeacherTableHolder) view.getTag();
        }
//        if (isAttendClass) // 是否处于上课状态
//            holder.view.setVisibility(View.VISIBLE);
//        else
//            holder.view.setVisibility(View.GONE);
        VideoModel model = list.get(position);
        ViewUtil.setContent(holder.videoName, model.getName());// 名字
        if (model.getSessionName() == null || model.getSessionName
                ().equals("")) {
            holder.sessionName.setVisibility(View.GONE);
        } else {
            ViewUtil.setContent(holder.sessionName, model
                    .getSessionName());// 名字
            holder.sessionName.setVisibility(View.VISIBLE);
        }
        if (model.getReadCount() > 0)
            holder.isPlay.setVisibility(View.VISIBLE);
        else
            holder.isPlay.setVisibility(View.GONE);
        ViewUtil.setContent(holder.videoTime, model.getRequireTime
                () + " 分钟");// 所需时间
        // 视频图像
        holder.pic.setImageURI(model.getPreview());
        ViewUtil.setContent(holder.look, R.string.see_golde);// 目标
        view.setTag(R.id.bundle_params, position);
        holder.look.setTag(R.id.bundle_params, position);
        holder.look.setOnClickListener(this);
        holder.view.setTag(R.id.bundle_params, position);
        holder.view.setOnClickListener(this);
        view.setOnClickListener(this);
        return view;
    }


    static class TeacherTableHolder {
        /**
         * 视频图片
         */
        SimpleDraweeView pic;
        /**
         * 视频名称
         */
        TextView videoName;
        /**
         * 视频时长
         */
        TextView videoTime;
        /**
         * 视频按钮
         */
        ImageView view;
        /**
         * 查看按钮
         */
        TextView look;
        /**
         * 课程类型
         */
        ImageView type;
        /**
         * 类型文字
         */
        TextView typetxt;
        /**
         * 课时每天的分割线
         */
        TextView session_data;
        /**
         * 是否播放
         */
        TextView isPlay;
        /**
         * 结尾
         */
        TextView Jw;
        /**
         * 课程名字
         */
        TextView sessionName;
    }

    VideoModel model;

    @Override
    public void onClick(View v) {
        if (list.size() == 0)
            return;
        int postion = Integer.parseInt(v.getTag(R.id.bundle_params)
                .toString());
        model = list.get(postion);
        if (AppUtil.isWifiActive(activity)) // 检查是否为wifi 网咯
            // 在退出上课的地方判断时候用到
            activity.app.setNowWifi(AppUtil.getConnectWifiSsid
                    (activity));// 保存当前wifi名字
        else
            activity.app.setNowWifi("noWifi");// 不是wifi网咯
        int i = v.getId();
        if (i == R.id.look) {
            view = false;
            RequesetType = "&opType=1";

        } else if (i == R.id.view) {
            if (!activity.app.isHotSesson() && activity.app.client != null && !activity.app.client.isConnect()) {
                if (activity.app.client != null) {
                    activity.app.client.close();
                    activity.app.client.destory();
                    activity.app.client = null;
                }
                activity.app.client = new HandlerClient();
                activity.app.client.connectTCP(activity.app.getSessionIp());
                ViewUtil.showMessage(activity, R.string.lzs);
                return;
            }
            view = true;
            list.get(postion).setReadCount(list.get(postion).getReadCount() + 1);
            notifyDataSetChanged();
            RequesetType = "&opType=2";
            dialog = new LoginDialog(activity);
            dialog.setTitle(activity.resources.getString(R.string.sessioning));
            dialog.show();

        }
        if (isTeacherSession)
            RequesetType = RequesetType + "&sectionType=teacher";
        else
            RequesetType = RequesetType + "&sectionType=null";
        RequsetUrl = IUrContant.GET_ONE_COURSE.replace
                ("{sectionId}", model.getId() + "") + activity.app.classModels.get(activity.app.getClassPosition())
                .getId() + RequesetType;
        IConstant.HTTP_CONNECT_POOL.addRequest(RequsetUrl, null, adapterHandler, 2);
    }

    String RequsetUrl;
    String RequesetType;

    void statActivity() throws Exception {

        Bundle bundle = new Bundle();
        bundle.putSerializable(IConstant.BUNDLE_PARAMS, model);
        activity.app.setVideoModel(model);
        if (view) {// 看视频
            SharedPreferencesUtil.save(activity, IConstant
                    .IS_ON_CLASS, true);
            if (!activity.app.isHotSesson && activity.app.client.isConnect()) {
                if (dialog != null)
                    dialog.close();
                bundle.putBoolean(IConstant.BUNDLE_PARAMS1, true);
                activity.startActivity(OnSessionActivity.class, bundle);
            } else {
                WifiAdmin wa = new WifiAdmin(activity);// 连接wifi
                if (wa.getWifitate() != WifiAdmin.IS_OPENED)// 检查wifi是否打开
                    wa.openWifi();
                wa.addNetwork(wa.CreateWifiInfo(SharedPreferencesUtil.getString(activity, IConstant.WIFI_NEME
                                + activity.app.classModels.get(activity.app.getClassPosition()).getId()),
                        "12345678", 3));
            }
        } else {// 看教案
            bundle.putBoolean(IConstant.BUNDLE_PARAMS1, false);
            activity.startActivity(OnSessionActivity.class, bundle);
        }

    }

    @Override
    public void httpErr(HttpResponseModel message) throws Exception {

    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws
            Exception {
        if (message.getUrl().equals(RequsetUrl)) {
            try{
                JSONObject data = new JSONObject(new String(message.getResponse()));
                if (data.optInt("StatusCode") == 200) {
                    activity.mCache.put(IUrContant.GET_ONE_COURSE + model.getId(), data);
                    statActivity();
                } else if (data.optInt("StatusCode") == 500) {
                   // activity.loading();
                }
            } catch (Exception e){
               // activity.loading();
            }
        }
    }

    @Override
    public void HttpError(HttpResponseModel message) throws
            Exception {
        JSONObject da = activity.mCache.getAsJSONObject(IUrContant.GET_ONE_COURSE + model.getId());
        if (da != null) {
            try {
                statActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ViewUtil.showMessage(activity, activity.resources.getString(R.string.ser_bi));
        }
    }





    @Override
    public Filter getFilter() {
        if (null == mFilter) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    // 自定义Filter类
    class MyFilter extends Filter {

        @SuppressLint("DefaultLocale")
        @Override
        // 该方法在子线程中执行
        // 自定义过滤规则
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<VideoModel> newValues = new ArrayList<VideoModel>();
            String filterString = constraint.toString().trim()
                    .toLowerCase();
            // 如果搜索框内容为空，就恢复原始数据
            if (TextUtils.isEmpty(filterString)) {
                newValues = listData;
            } else {
                // 过滤出新数据
                for (VideoModel str : listData) {
                    if ((-1 != str.getName().toLowerCase()
                            .indexOf(filterString))
                            || (-1 != str.getSessionName()
                            .toLowerCase()
                            .indexOf(filterString))
                            || (-1 != str.getTypeName().toLowerCase()
                            .indexOf(filterString))) {
                        newValues.add(str);
                    }
                }
            }
            results.values = newValues;
            results.count = newValues.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<VideoModel>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged(); // 通知数据发生了改变
            } else {
                notifyDataSetInvalidated(); // 通知数据失效
            }
        }
    }
}
