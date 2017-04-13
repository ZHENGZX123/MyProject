package yjpty.teaching.acitivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.zk.myweex.R;

import yjpty.teaching.dialog.ClearDataDialog;
import yjpty.teaching.fragment.AllSessionFragment;
import yjpty.teaching.fragment.BaseFragment;
import yjpty.teaching.fragment.KinectSessionFragment;
import yjpty.teaching.fragment.MySessionFragment;
import yjpty.teaching.util.ViewUtil;


public class TeachingPlansActivity extends BaseActivity implements
        OnCheckedChangeListener, ClearDataDialog.ClearDataCallBack {
    RadioGroup rg;
    BaseFragment sessionTableBaseFragment, sessionDbBaseFragment, kinectSessionFragment;
    int page = 0;
    ClearDataDialog dialog;
    int ViewLineHeight;//线条移动距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_teacher_plan);
        try {
            View v = ViewUtil.findViewById(this, R.id.previos_class);// 初始化退出的dialog
            dialog = new ClearDataDialog(this, this,
                    resources.getString(R.string.exit_session), v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initView();
            loadData();
            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() throws Exception {
        rg = ViewUtil.findViewById(this, R.id.rg);
        rg.setOnCheckedChangeListener(this);
        // 进来默认课程表
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, sessionTableBaseFragment,
                sessionDbBaseFragment, kinectSessionFragment);
        if (sessionTableBaseFragment == null) {
            sessionTableBaseFragment = new AllSessionFragment();
            transaction.add(R.id.fragment, sessionTableBaseFragment,
                    "sessionTableBaseFragment");
        } else if (sessionTableBaseFragment.isAdded()
                && sessionTableBaseFragment.isHidden()) {
            transaction.show(sessionTableBaseFragment);
        }
        transaction.commit();
        findViewById(R.id.previos_class).setOnClickListener(this);
        if (app.classModels.get(app
                .getClassPosition()).getIsActivateKinect().equals("1")) {
            findViewById(R.id.rb3).setVisibility(View.VISIBLE);
            ViewLineHeight = displayMetrics.widthPixels / 3;
            findViewById(R.id.view1).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.rb3).setVisibility(View.GONE);
            ViewLineHeight = displayMetrics.widthPixels / 2;
            findViewById(R.id.view1).setVisibility(View.GONE);
        }
    }

    @SuppressLint("CommitTransaction")
    @Override
    public void onCheckedChanged(RadioGroup rg, int checkId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int pg = 0;
        if (checkId == R.id.rb1) {
            pg = 0;
        } else if (checkId == R.id.rb2) {
            pg = 1;
        } else if (checkId == R.id.rb3) {
            pg = 2;
        }
        if (page > pg) {// 切换动画
            transaction.setCustomAnimations(R.anim.yjpty_silde_in_left,
                    R.anim.yjpty_silde_out_right);
            if (page - pg == 2)
                ViewUtil.moveFrontBg(findViewById(R.id.view), ViewLineHeight * page, 0, 0, 0);
            else
                ViewUtil.moveFrontBg(findViewById(R.id.view), ViewLineHeight * page, ViewLineHeight
                        * (page - 1), 0, 0);
        } else {
            transaction.setCustomAnimations(R.anim.yjpty_silde_in_right,
                    R.anim.yjpty_silde_out_left);
            if (pg - page == 2)
                ViewUtil.moveFrontBg(findViewById(R.id.view), 0, ViewLineHeight * 2, 0, 0);
            else
                ViewUtil.moveFrontBg(findViewById(R.id.view), ViewLineHeight * page, ViewLineHeight * (page + 1), 0, 0);
        }
        page = pg;
        hideFragment(transaction, sessionTableBaseFragment,
                sessionDbBaseFragment, kinectSessionFragment);
        if (checkId == R.id.rb1) {
            if (sessionTableBaseFragment == null) {
                sessionTableBaseFragment = new AllSessionFragment();
                transaction.add(R.id.fragment, sessionTableBaseFragment,
                        "sessionTableBaseFragment");
            } else if (sessionTableBaseFragment.isAdded()
                    && sessionTableBaseFragment.isHidden()) {
                transaction.show(sessionTableBaseFragment);
            }


        } else if (checkId == R.id.rb2) {
            if (sessionDbBaseFragment == null) {
                sessionDbBaseFragment = new MySessionFragment();
                transaction.add(R.id.fragment, sessionDbBaseFragment,
                        "sessionDbBaseFragment");
            } else if (sessionDbBaseFragment.isAdded()
                    && sessionDbBaseFragment.isHidden()) {
                transaction.show(sessionDbBaseFragment);
            }

        } else if (checkId == R.id.rb3) {
            if (kinectSessionFragment == null) {
                kinectSessionFragment = new KinectSessionFragment();
                transaction.add(R.id.fragment, kinectSessionFragment,
                        "KinectSessionFragment");
            } else if (kinectSessionFragment.isAdded()
                    && kinectSessionFragment.isHidden()) {
                transaction.show(kinectSessionFragment);
            }
        }
        transaction.commit();
    }

    @Override
    public void clearDataCallBack(View vx) throws Exception {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (dialog != null && !dialog.isShowing()) {// 退出上课的dialog
                dialog.show();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.previos_class) {
            if (dialog != null && !dialog.isShowing()) {// 退出上课的dialog
                dialog.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (app.client != null) {
            app.client.close();
            app.client.destory();
            app.client = null;
        }
    }
}