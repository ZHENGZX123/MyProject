package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;

import cn.kiway.classcard.R;
import cn.kiway.classcard.adpater.ClassMienListAdapter;
import cn.kiway.classcard.adpater.ClassNotifyAdapter;
import cn.kiway.classcard.adpater.SessionAdapter;
import cn.kiway.classcard.adpater.ViewPageAdapter;
import cn.kiway.classcard.view.HorizontalListView;
import cn.kiway.classcard.view.ZoomOutPageTransformer;

/**
 * Created by Administrator on 2019/3/5.
 * 班级圈
 */

public class ClassCircleFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    ListView listView;
    GridView gridView;
    RadioGroup rg;
    HorizontalListView horizontalListView;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_circle, null);
        initView();
        return view;
    }

    public void initView() {
        rg = view.findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        listView = view.findViewById(R.id.listview);
        listView.setAdapter(new ClassNotifyAdapter(getContext()));
        gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(new SessionAdapter(getContext()));
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        //viewPager.setPageMargin(10);//控制两幅图之间的间距
        viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        viewPager.setAdapter(new ViewPageAdapter(getContext()));
        horizontalListView = view.findViewById(R.id.listview_h);
        horizontalListView.setAdapter(new ClassMienListAdapter(getContext()));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                view.findViewById(R.id.listview).setVisibility(View.VISIBLE);
                view.findViewById(R.id.session).setVisibility(View.GONE);
                view.findViewById(R.id.mien).setVisibility(View.GONE);
                break;
            case R.id.rb2:
                view.findViewById(R.id.session).setVisibility(View.VISIBLE);
                view.findViewById(R.id.listview).setVisibility(View.GONE);
                view.findViewById(R.id.mien).setVisibility(View.GONE);
                break;
            case R.id.rb3:
                view.findViewById(R.id.session).setVisibility(View.VISIBLE);
                view.findViewById(R.id.listview).setVisibility(View.GONE);
                view.findViewById(R.id.mien).setVisibility(View.GONE);
                break;
            case R.id.rb4:
                view.findViewById(R.id.mien).setVisibility(View.VISIBLE);
                view.findViewById(R.id.listview_h).setVisibility(View.VISIBLE);
                view.findViewById(R.id.listview).setVisibility(View.GONE);
                view.findViewById(R.id.session).setVisibility(View.GONE);
                break;
            case R.id.rb5:
                view.findViewById(R.id.session).setVisibility(View.VISIBLE);
                view.findViewById(R.id.listview).setVisibility(View.GONE);
                view.findViewById(R.id.mien).setVisibility(View.GONE);
                view.findViewById(R.id.listview_h).setVisibility(View.GONE);
                break;
            case R.id.rb6:
                view.findViewById(R.id.listview).setVisibility(View.VISIBLE);
                view.findViewById(R.id.session).setVisibility(View.GONE);
                view.findViewById(R.id.mien).setVisibility(View.GONE);
                break;
        }
    }
}
