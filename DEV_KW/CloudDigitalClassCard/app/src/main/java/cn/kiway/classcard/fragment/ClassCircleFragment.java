package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;

import cn.kiway.classcard.R;
import cn.kiway.classcard.adpater.ClassMienListAdapter;
import cn.kiway.classcard.adpater.ClassNotifyAdapter;
import cn.kiway.classcard.adpater.SessionAdapter;
import cn.kiway.classcard.adpater.ViewPageAdapter;
import cn.kiway.classcard.utils.Utils;
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
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageMargin(-10);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
       // viewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        viewPager.setAdapter(new ViewPageAdapter(getContext()));
        horizontalListView = view.findViewById(R.id.listview_h);
        horizontalListView.setAdapter(new ClassMienListAdapter(getContext()));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                classNotify();
                break;
            case R.id.rb2:
                classSession();
                break;
            case R.id.rb3:
                classClean();
                break;
            case R.id.rb4:
                classsMien();
                break;
            case R.id.rb5:
                classHonor();
                break;
            case R.id.rb6:
                classHomework();
                break;
        }
    }

    //班级通知
    protected void classNotify() {
        view.findViewById(R.id.listview).setVisibility(View.VISIBLE);
        view.findViewById(R.id.session).setVisibility(View.GONE);
        view.findViewById(R.id.mien).setVisibility(View.GONE);
        listView.removeHeaderView(headView);
    }

    //班级课表
    protected void classSession() {
        view.findViewById(R.id.session).setVisibility(View.VISIBLE);
        view.findViewById(R.id.listview).setVisibility(View.GONE);
        view.findViewById(R.id.mien).setVisibility(View.GONE);
    }

    //班级值日
    protected void classClean() {
        view.findViewById(R.id.session).setVisibility(View.VISIBLE);
        view.findViewById(R.id.listview).setVisibility(View.GONE);
        view.findViewById(R.id.mien).setVisibility(View.GONE);
    }

    //班级风采
    protected void classsMien() {
        view.findViewById(R.id.mien).setVisibility(View.VISIBLE);
        view.findViewById(R.id.listview_h).setVisibility(View.VISIBLE);
        view.findViewById(R.id.listview).setVisibility(View.GONE);
        view.findViewById(R.id.session).setVisibility(View.GONE);
    }

    //班级荣誉
    protected void classHonor() {
        view.findViewById(R.id.mien).setVisibility(View.VISIBLE);
        view.findViewById(R.id.session).setVisibility(View.GONE);
        view.findViewById(R.id.listview).setVisibility(View.GONE);
        view.findViewById(R.id.listview_h).setVisibility(View.GONE);
    }

    View headView;
    Spinner spinner1, spinner2;
    ArrayAdapter<String> adapter1, adapter2;

    //课后作业
    protected void classHomework() {
        view.findViewById(R.id.listview).setVisibility(View.VISIBLE);
        view.findViewById(R.id.session).setVisibility(View.GONE);
        view.findViewById(R.id.mien).setVisibility(View.GONE);
        if (listView.getHeaderViewsCount() <= 0) {
            headView = LayoutInflater.from(getContext()).inflate(R.layout.head_circle_listview, null);
            spinner1 = headView.findViewById(R.id.spinner1);
            spinner2 = headView.findViewById(R.id.spinner2);
            if (adapter1 == null) {
                adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                        Utils.getSpinner1List());
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
            if (adapter2 == null) {
                adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,
                        Utils.getSpinner1List2());
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
            spinner1.setAdapter(adapter1);
            spinner2.setAdapter(adapter2);
            listView.addHeaderView(headView);
        }
    }
}
