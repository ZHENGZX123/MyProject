package cn.kiway.classcard.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.kiway.classcard.R;

/**
 * Created by Administrator on 2019/3/5.
 * 走班
 */

public class ClassShiftFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_shift, null);
        return view;
    }
}
