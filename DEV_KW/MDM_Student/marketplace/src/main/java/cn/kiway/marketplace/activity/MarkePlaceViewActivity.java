package cn.kiway.marketplace.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.fragment.MarkePlaceFragment;

public class MarkePlaceViewActivity extends FragmentActivity {

    private ViewPager viewPager;
    String[] strings = new String[]{"全部", "休闲", "教育", "游戏", "体育", "竞技"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marke_activity_mainview);
        initContentView();
    }

    public void onDown(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void onBefore(View view) {
        finish();
    }

    public void initContentView() {
        Resources resources = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, getTheme()));
        }
        viewPager = (ViewPager) findViewById(R.id.download_viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new MarkePlaceFragment();
            }

            @Override
            public int getCount() {
                return strings.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return strings[position];
            }
        });
        ((TabLayout) findViewById(R.id.download_tabs)).setupWithViewPager(viewPager);
    }

}
