package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import cn.kiway.classcard.R;
import cn.kiway.classcard.adpater.ShiftSessionAdapter;
import cn.kiway.classcard.adpater.TodaySessionAdapter;
import cn.kiway.classcard.adpater.WeekSessionAdapter;

/**
 * Created by Administrator on 2019/3/5.
 * 走班
 */

public class ClassShiftFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    RadioGroup rg, rg2;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_shift, null);
        initView();
        return view;
    }

    protected void initView() {
        rg = view.findViewById(R.id.rg);
        rg2 = view.findViewById(R.id.rg2);
        listView = view.findViewById(R.id.listview);
        rg.setOnCheckedChangeListener(this);
        rg2.setOnCheckedChangeListener(this);
        todaySession();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                todaySession();
                break;
            case R.id.rb2:
                weekSession();
                break;
            case R.id.rb3:
                classShift();
                break;
            case R.id.rb4:
                attendance();
            case R.id.r2b1:
                break;
            case R.id.r2b2:
                break;
            case R.id.r2b3:
                break;
            case R.id.r2b4:
                break;
            case R.id.r2b5:
                break;
        }
    }

    //今日课程
    private void todaySession() {
        view.findViewById(R.id.todaysession).setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        view.findViewById(R.id.week).setVisibility(View.GONE);
        view.findViewById(R.id.attendce).setVisibility(View.GONE);
        view.findViewById(R.id.shift).setVisibility(View.GONE);
        view.findViewById(R.id.shift2).setVisibility(View.GONE);
        listView.setAdapter(new TodaySessionAdapter(getContext()));
    }

    //本周课程
    private void weekSession() {
        view.findViewById(R.id.week).setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        view.findViewById(R.id.todaysession).setVisibility(View.GONE);
        view.findViewById(R.id.attendce).setVisibility(View.GONE);
        view.findViewById(R.id.shift).setVisibility(View.GONE);
        view.findViewById(R.id.shift2).setVisibility(View.GONE);
        listView.setAdapter(new WeekSessionAdapter(getContext()));
    }

    //走班
    private void classShift() {
        view.findViewById(R.id.week).setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        view.findViewById(R.id.todaysession).setVisibility(View.GONE);
        view.findViewById(R.id.attendce).setVisibility(View.GONE);
        view.findViewById(R.id.shift).setVisibility(View.VISIBLE);
        view.findViewById(R.id.shift2).setVisibility(View.VISIBLE);
        listView.setAdapter(new ShiftSessionAdapter(getContext()));
    }

    //考勤
    private void attendance() {
        view.findViewById(R.id.shift).setVisibility(View.GONE);
        view.findViewById(R.id.attendce).setVisibility(View.VISIBLE);
        view.findViewById(R.id.todaysession).setVisibility(View.GONE);
        view.findViewById(R.id.week).setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        view.findViewById(R.id.shift2).setVisibility(View.GONE);
    }
}
