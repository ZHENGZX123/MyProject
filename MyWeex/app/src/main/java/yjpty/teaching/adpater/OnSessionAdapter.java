package yjpty.teaching.adpater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zk.myweex.R;

import java.util.List;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.acitivity.OnSessionActivity;
import yjpty.teaching.model.OnClassModel;
import yjpty.teaching.util.ViewUtil;


public class OnSessionAdapter extends ArrayAdapter<String> {
	BaseActivity activity;
	public GoldHolder holder;
	public List<OnClassModel> list;// 视频id
	boolean isAttendClass;// 是否在上课
	Animation animation;// 播放动画
	LinearLayout.LayoutParams layoutParams;// 播放时候距底部的距离
	public static View viewSpace;

	public OnSessionAdapter(Context context, List<OnClassModel> list,
							boolean isAttendClass) {
		super(context, -1);
		this.activity = (BaseActivity) context;
		this.list = list;
		this.isAttendClass = isAttendClass;
		animation = AnimationUtils.loadAnimation(context,
				R.anim.yjpty_play_video_anim);// 播放时候的动画
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = ViewUtil.inflate(activity, R.layout.yjpty_gold_list_item);
			holder = new GoldHolder();
			holder.number = ViewUtil.findViewById(view, R.id.really_title);
			holder.content = ViewUtil.findViewById(view, R.id.really_content);
			holder.view = ViewUtil.findViewById(view, R.id.view);
			view.setTag(holder);
		} else {
			holder = (GoldHolder) view.getTag();
		}
		OnClassModel model = list.get(position);
		ViewUtil.setContent(holder.content, model.getContent());
		if (position + 1 < 10) {
			ViewUtil.setContent(holder.number, "0" + (position + 1));
		} else {
			ViewUtil.setContent(holder.number, "" + (position + 1));
		}
		if (isAttendClass) {// 是否在上课
			if (model.getBool()) {// 是否正在播放改视频 播放动画
				ViewUtil.setArroundDrawable(holder.number, -1, -1,
						R.drawable.yjpty_ic_av_play_arrow, -1);
				holder.number.startAnimation(animation);
			} else {
				ViewUtil.setArroundDrawable(holder.number, -1, -1, -1, -1);
				holder.number.clearAnimation();
			}
			if (position + 1 == list.size()
					&& activity.findViewById(R.id.layout1).getVisibility() == View.VISIBLE) {// 设置最后一个的底部大小
				holder.view.setVisibility(View.VISIBLE);
				layoutParams = new LinearLayout.LayoutParams(
						OnSessionActivity.width, OnSessionActivity.height);
				holder.view.setLayoutParams(layoutParams);
				viewSpace = holder.view;
			} else {
				holder.view.setVisibility(View.GONE);
			}
		} else {
			ViewUtil.setArroundDrawable(holder.number, -1, -1, -1, -1);
			holder.view.setVisibility(View.GONE);
		}
		return view;
	}

	static class GoldHolder {
		/**
		 * 第几个
		 * */
		TextView number;
		/**
		 * 内容
		 * */
		TextView content;
		/**
		 * 底部的view 空白部分
		 * */
		View view;
	}
}
