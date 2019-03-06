package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import cn.kiway.classcard.R;
import cn.kiway.classcard.adpater.AttendanceAdapter;

/**
 * Created by Administrator on 2019/3/5.
 * 考勤
 */

public class AttendanceFragment extends BaseFragment {
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_attendance, null);
        initView();
        return view;
    }

    protected void initView() {
        gridView = view.findViewById(R.id.gridView);
        gridView.setAdapter(new AttendanceAdapter(getContext()));
    }
}
