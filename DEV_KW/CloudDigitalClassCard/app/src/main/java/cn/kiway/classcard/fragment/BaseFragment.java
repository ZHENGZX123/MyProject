package cn.kiway.classcard.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */

public class BaseFragment extends Fragment {
    protected View view;

    public BaseFragment() {
        super();
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
}
