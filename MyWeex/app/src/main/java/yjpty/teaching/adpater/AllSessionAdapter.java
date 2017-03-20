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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zk.myweex.R;

import java.util.ArrayList;
import java.util.List;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.acitivity.SessionDbDetailActivity;
import yjpty.teaching.fragment.AllSessionFragment;
import yjpty.teaching.model.VideoCateMode;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;


public class AllSessionAdapter extends ArrayAdapter<VideoCateMode> implements
        OnClickListener {
    BaseActivity activity;
    SessionDbHolder holder;
    public List<VideoCateMode> list;// 显示在界面上的数据
    public List<VideoCateMode> listData;// 原始全部数据
    private MyFilter mFilter;
    AllSessionFragment fragment;

    public AllSessionAdapter(Context context, List<VideoCateMode> list, AllSessionFragment fragment) {
        super(context, -1);
        this.activity = (BaseActivity) context;
        this.list = list;
        this.fragment = fragment;
        listData = new ArrayList<VideoCateMode>();
        inflater = LayoutInflater.from(getContext());
    }

    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = ViewUtil.inflate(activity, R.layout.yjpty_session_db_list_item);
//            view = inflater.inflate(R.layout.yjpty_session_db_list_item, null);
            holder = new SessionDbHolder();
            holder.videoImg = ViewUtil.findViewById(view, R.id.pic);
            holder.videoName = ViewUtil.findViewById(view, R.id.name);
            holder.videoType = ViewUtil.findViewById(view, R.id.type_txt);
            holder.videoCount = ViewUtil.findViewById(view, R.id.cout);
            view.setTag(holder);
        } else {
            holder = (SessionDbHolder) view.getTag();
        }
        VideoCateMode model = list.get(position);
        ViewUtil.setContent(holder.videoName, model.getName());
        ViewUtil.setContent(holder.videoType, model.getType());
        ViewUtil.setContent(holder.videoCount, model.getReadCount() + "/" + model.getAllCount());
        holder.videoImg.setImageURI(model.getPreview());
        view.setTag(R.id.bundle_params, position);
        view.setOnClickListener(this);
        return view;
    }

    class SessionDbHolder {
        /**
         * 视频图像
         */
        SimpleDraweeView videoImg;
        /**
         * 视频名字
         */
        TextView videoName;
        /**
         * 视频类型
         */
        TextView videoType;
        /**
         * 视频进度
         */
        TextView videoCount;
    }

    @Override
    public void onClick(View v) {
        int postion = Integer.parseInt(v.getTag(R.id.bundle_params).toString());
        VideoCateMode model = list.get(postion);
        Bundle bundle = new Bundle();
        bundle.putSerializable(IConstant.BUNDLE_PARAMS, model);
        activity.startActivity(SessionDbDetailActivity.class, bundle);
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
            List<VideoCateMode> newValues = new ArrayList<VideoCateMode>();
            String filterString = constraint.toString().trim().toLowerCase();
            // 如果搜索框内容为空，就恢复原始数据
            if (TextUtils.isEmpty(filterString)) {
                newValues = listData;
            } else {
                // 过滤出新数据
                for (VideoCateMode str : listData) {
                    if ((-1 != str.getName().toLowerCase()
                            .indexOf(filterString)) || -1 != str.getType().toLowerCase()
                            .indexOf(filterString)) {
                        newValues.add(str);
                    }
                }
            }
            results.values = newValues;
            results.count = newValues.size();
            if (fragment != null)
                fragment.hasSessionData();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            list = (List<VideoCateMode>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged(); // 通知数据发生了改变
                if (fragment != null)
                    fragment.hasSessionData();
            } else {
                notifyDataSetInvalidated(); // 通知数据失效
            }
        }
    }
}
