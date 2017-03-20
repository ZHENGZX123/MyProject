package yjpty.teaching.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zk.myweex.R;

import java.util.ArrayList;
import java.util.List;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.util.ViewUtil;


/**
 * 选择学校列表适配器
 * */
@SuppressLint("InflateParams")
public class SpinnerAdapter extends BaseAdapter {
	public List<String> list = new ArrayList<>();
	BaseActivity activity;


	public SpinnerAdapter(Context context, int type) {
		this.activity = (BaseActivity) context;
		list.add(activity.resources.getString(R.string.open_wifi));
		list.add(activity.resources.getString(R.string.WEP));
		list.add(activity.resources.getString(R.string.WPA));
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertview, ViewGroup group) {
		LayoutInflater _LayoutInflater = LayoutInflater.from(activity);
		convertview = _LayoutInflater.inflate(R.layout.yjpty_spinner_list_item, null);
		if (convertview != null) {
			TextView textView = (TextView) convertview.findViewById(R.id.text1);
			textView.setText(list.get(position));
			if (list.size() == 1) {
				ViewUtil.setArroundDrawable(textView, -1, -1, -1, -1);
			} else {
				ViewUtil.setArroundDrawable(textView, -1, -1,
						R.drawable.yjpty_icon_go, -1);
			}
		}
		return convertview;
	}
}
