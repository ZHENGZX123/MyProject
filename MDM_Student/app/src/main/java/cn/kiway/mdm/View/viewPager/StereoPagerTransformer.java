package cn.kiway.mdm.View.viewPager;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author wupanjie
 */

public class StereoPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        final float normalizedposition = Math.abs(Math.abs(position) - 1);
        page.setAlpha(normalizedposition);
    }

}
