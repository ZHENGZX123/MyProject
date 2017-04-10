package yjpty.teaching.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.List;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.http.BaseHttpHandler;
import yjpty.teaching.http.HttpHandler;
import yjpty.teaching.http.HttpResponseModel;


/**
 * 帧布局基类
 *
 * @author YI
 */
public abstract class BaseFragment extends Fragment implements OnClickListener,
        HttpHandler {
    protected BaseActivity activity;
    protected View view;
    protected boolean isRefresh;
    protected BaseHttpHandler fragmentHandler = new BaseHttpHandler(this) {
    };

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 数据的加载
     */
    public void loadData() throws Exception {
    }

    ;

    @Override
    public void httpErr(HttpResponseModel message) throws Exception {

    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {

    }

    @Override
    public void HttpError(HttpResponseModel message) throws Exception {
    }

    public void hideFragment(FragmentTransaction transaction,
                             List<Fragment> fragments) {
        if (transaction == null)
            return;
        if (fragments == null || fragments.size() == 0)
            return;
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                transaction.hide(fragment);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
