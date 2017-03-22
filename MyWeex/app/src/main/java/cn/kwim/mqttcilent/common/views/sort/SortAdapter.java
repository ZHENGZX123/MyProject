package cn.kwim.mqttcilent.common.views.sort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.zk.myweex.R;


public class SortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Context mContext;
	
	public SortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.im_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.title_view = (View)view.findViewById(R.id.catalog_view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
			viewHolder.title_view.setVisibility(View.VISIBLE);
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
			viewHolder.title_view.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(mContext, "删除群成员", Toast.LENGTH_SHORT).show();
				
				return false;
			}
		});
		viewHolder.tvTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
////				if(((GroupMemberActivity)mContext).getType()!=null&&((GroupMemberActivity)mContext).getType().equals("ChatActivity")){
//					Intent intent = new Intent();
//					intent.putExtra("name", list.get(position).getName());
//					
//					((GroupMemberActivity)mContext).setResult(((GroupMemberActivity)mContext).RESULT_OK, intent);
//					((GroupMemberActivity)mContext).finish();
////				}else{
					//Intent intent = new Intent(mContext, GroupMemberInfoActivity.class);
					//intent.putExtra(FriendInfoActiviy.USER_ID, mContent.getId());
					//((Activity)mContext).startActivity(intent);
//				}
				
			}
		});
		
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		
		return view;

	}
	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		View title_view;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii�?
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母�?#代替�?
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

	@Override
	public Object[] getSections() {
		return null;
	}
}