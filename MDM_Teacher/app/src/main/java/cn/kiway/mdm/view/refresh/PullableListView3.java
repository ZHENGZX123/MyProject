package cn.kiway.mdm.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//不能下拉
public class PullableListView3 extends ListView implements Pullable {

    public PullableListView3(Context context) {
        super(context);
    }

    public PullableListView3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableListView3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        return false;
    }

    @Override
    public boolean canPullUp() {
        if (getCount() == 0) {
            // 没有item的时候是否也可以上拉加载
            return false;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null && getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
        return false;
    }

}
