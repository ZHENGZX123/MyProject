package cn.kiway.classcard.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import cn.kiway.classcard.R;
import cn.kiway.classcard.fragment.AttendanceFragment;
import cn.kiway.classcard.fragment.BaseFragment;
import cn.kiway.classcard.fragment.ClassCircleFragment;
import cn.kiway.classcard.fragment.ClassShiftFragment;
import cn.kiway.classcard.fragment.HomeFragment;
import cn.kiway.classcard.fragment.HomeSchoolFragment;
import cn.kiway.classcard.fragment.SchoolNewsFragment;
import cn.kiway.classcard.fragment.SettingFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup rg;
    BaseFragment homeFragment, schooclNewsFragment, classCircleFragment, attendaceFragment, homeSchoolFragment,
            classShiftFragment, settingFragment;
    FragmentManager fragmentManager;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void initView() {
        super.initView();
        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, homeFragment, homeFragment);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.fragment, homeFragment, "homeFragment");
        } else if (homeFragment.isAdded() && homeFragment.isHidden()) {
            transaction.show(homeFragment);
        }
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int pg = 0;
        switch (checkedId) {
            case R.id.rb1:
                pg = 0;
                break;
            case R.id.rb2:
                pg = 1;
                break;
            case R.id.rb3:
                pg = 2;
                break;
            case R.id.rb4:
                pg = 3;
                break;
            case R.id.rb5:
                pg = 4;
                break;
            case R.id.rb6:
                pg = 5;
                break;
            case R.id.rb7:
                pg = 6;
                break;
        }
        if (page > pg) {// 动画
            transaction.setCustomAnimations(R.anim.silde_in_left,
                    R.anim.silde_out_right);
        } else {
            transaction.setCustomAnimations(R.anim.silde_in_right,
                    R.anim.silde_out_left);
        }
        page = pg;
        hideFragment(transaction, homeFragment, schooclNewsFragment, classCircleFragment, attendaceFragment,
                homeSchoolFragment,
                classShiftFragment, settingFragment);
        switch (checkedId) {
            case R.id.rb1:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment, homeFragment, "homeFragment");
                } else if (homeFragment.isAdded() && homeFragment.isHidden()) {
                    transaction.show(homeFragment);
                }
                break;
            case R.id.rb2:
                if (schooclNewsFragment == null) {
                    schooclNewsFragment = new SchoolNewsFragment();
                    transaction.add(R.id.fragment, schooclNewsFragment, "schooclNewsFragment");
                } else if (schooclNewsFragment.isAdded() && schooclNewsFragment.isHidden()) {
                    transaction.show(schooclNewsFragment);
                }
                break;
            case R.id.rb3:
                if (classCircleFragment == null) {
                    classCircleFragment = new ClassCircleFragment();
                    transaction.add(R.id.fragment, classCircleFragment, "classCircleFragment");
                } else if (classCircleFragment.isAdded() && classCircleFragment.isHidden()) {
                    transaction.show(classCircleFragment);
                }
                break;
            case R.id.rb4:
                if (attendaceFragment == null) {
                    attendaceFragment = new AttendanceFragment();
                    transaction.add(R.id.fragment, attendaceFragment, "attendaceFragment");
                } else if (attendaceFragment.isAdded() && attendaceFragment.isHidden()) {
                    transaction.show(attendaceFragment);
                }
                break;
            case R.id.rb5:
                if (homeSchoolFragment == null) {
                    homeSchoolFragment = new HomeSchoolFragment();
                    transaction.add(R.id.fragment, homeSchoolFragment, "homeSchoolFragment");
                } else if (homeSchoolFragment.isAdded() && homeSchoolFragment.isHidden()) {
                    transaction.show(homeSchoolFragment);
                }
                break;
            case R.id.rb6:
                if (classShiftFragment == null) {
                    classShiftFragment = new ClassShiftFragment();
                    transaction.add(R.id.fragment, classShiftFragment, "classShiftFragment");
                } else if (classShiftFragment.isAdded() && classShiftFragment.isHidden()) {
                    transaction.show(classShiftFragment);
                }
                break;
            case R.id.rb7:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.fragment, settingFragment, "settingFragment");
                } else if (settingFragment.isAdded() && settingFragment.isHidden()) {
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏Fragment
     *
     * @param transaction
     * @param fragments
     */
    public void hideFragment(FragmentTransaction transaction,
                             Fragment... fragments) {
        if (transaction == null)
            return;
        if (fragments == null || fragments.length == 0)
            return;
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                transaction.hide(fragment);
        }
    }
}
