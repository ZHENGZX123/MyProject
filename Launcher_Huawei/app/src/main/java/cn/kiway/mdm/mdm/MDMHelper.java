package cn.kiway.mdm.mdm;

import cn.kiway.mdm.adapter.HuaweiMDMAdapter;
import cn.kiway.mdm.adapter.IMDMAdapter;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MDMHelper {

    private static IMDMAdapter mAdapter;

    public static IMDMAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new HuaweiMDMAdapter();
        }
        return mAdapter;
    }


}
