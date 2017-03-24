package yjpty.teaching.adpater;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import yjpty.teaching.R;
import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.acitivity.MipcaCaptureActivity;
import yjpty.teaching.acitivity.TeachingPlansActivity;
import yjpty.teaching.model.ClassModel;
import yjpty.teaching.model.HeziStautsModel;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;


public class HeizInfoAdapter extends ArrayAdapter<HeziStautsModel> implements
        OnClickListener {
    public List<HeziStautsModel> list;
    HeziInfoModel holder;
    BaseActivity activity;
    List<ClassModel> classModels;

    public HeizInfoAdapter(Context context, List<HeziStautsModel> list,
                           List<ClassModel> classModels) {
        super(context, -1);
        activity = (BaseActivity) context;
        this.list = list;
        this.classModels = classModels;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            holder = new HeziInfoModel();
            view = ViewUtil.inflate(activity, R.layout.yjpty_heziinfo_list_item);
            holder.className = ViewUtil.findViewById(view, R.id.class_name);
            holder.classGrade = ViewUtil.findViewById(view, R.id.class_grade);
            holder.heziCode = ViewUtil.findViewById(view, R.id.hezi_id);
            holder.heziInfo = ViewUtil.findViewById(view, R.id.hezi_info);
            view.setTag(holder);
        } else {
            holder = (HeziInfoModel) view.getTag();
        }
        HeziStautsModel model = list.get(position);
        ViewUtil.setContent(holder.className, model.getClassName());
        ViewUtil.setContent(holder.classGrade, yjpty.teaching.util.StringUtil.getResourse(activity, model.getGrade()));
        ViewUtil.setContent(holder.heziCode, model.getHeziCode());
        switch (model.getHeziType()) {
            case 1:
                ViewUtil.setContent(holder.heziInfo, activity.resources.getString(R.string.dj_session));
                holder.heziInfo.setBackgroundResource(R.drawable.yjpty_green_val);
                break;
            case 2:
                ViewUtil.setContent(holder.heziInfo, activity.resources.getString(R.string.scan_session));
                holder.heziInfo.setBackgroundResource(R.drawable.yjpty_gray_val);
                break;
        }
        view.setTag(R.id.bundle_params, position);
        view.setOnClickListener(this);
        return view;
    }

    class HeziInfoModel {
        TextView className;
        TextView classGrade;
        TextView heziCode;
        TextView heziInfo;
    }

    @Override
    public void onClick(View v) {
        int position = Integer.parseInt(v.getTag(R.id.bundle_params).toString());
        HeziStautsModel model = list.get(position);
        Bundle bundle = new Bundle();
        activity.app.setClassPosition(position);
        switch (model.getHeziType()) {
            case 1:
                if (model.getHeziResoures(activity).indexOf(model.getString(activity)) < 0) {
                    String string;
                    if (model.getHeziResoures(activity).equals(activity.resources.getString(R.string.no_resource))) {
                        string = "盒子没有资源";
                    } else {
                        string = "资源不对应";
                    }
                    ViewUtil.showMessage(activity, string);
                    return;
                }
                activity.app.setSessionIp(model.getHeziIP());
                activity.app.setHotSesson(false);
                activity.app.client = new HandlerClient();
                activity.app.client.connectTCP(activity.app.getSessionIp());
                bundle.putBoolean(IConstant.BUNDLE_PARAMS, true);// 1为上课 2看备课
                activity.startActivity(TeachingPlansActivity.class, bundle);
                activity.finish();
                break;
            case 2:
                bundle.putInt(IConstant.BUNDLE_PARAMS, 1);
                activity.startActivity(MipcaCaptureActivity.class, bundle);
                activity.finish();
                break;
        }
    }
}
