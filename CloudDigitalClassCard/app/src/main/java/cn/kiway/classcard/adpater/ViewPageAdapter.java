package cn.kiway.classcard.adpater;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 */

public class ViewPageAdapter extends PagerAdapter {
    Context context;

    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_img, container, false);
        ImageView img = view.findViewById(R.id.img_item_img);
        img.setImageDrawable(context.getDrawable(R.drawable.test));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
