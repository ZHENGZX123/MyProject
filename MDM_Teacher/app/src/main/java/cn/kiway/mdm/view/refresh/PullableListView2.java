package cn.kiway.mdm.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//不能上拉
public class PullableListView2 extends ListView implements Pullable {

	public PullableListView2(Context context) {
		super(context);
	}

	public PullableListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableListView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		if (getCount() == 0) {
			// 没有item的时候也可以下拉刷新
			return true;
		} else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
			// 滑到ListView的顶部了
			return true;
		} else
			return false;
	}

	@Override
	public boolean canPullUp() {
		return false;
	}

}
