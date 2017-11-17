package cn.kiway.mdm.mdm;

import android.os.Build;

import cn.kiway.mdm.adapter.HuaweiMDMAdapter;
import cn.kiway.mdm.adapter.IMDMAdapter;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MDMHelper {

    private static IMDMAdapter mAdapter;

    public static IMDMAdapter getAdapter() {
        if (mAdapter == null) {
            //根据平板类型来选择用哪个Adapter
            if (Build.BRAND.equals("HUAWEI")) {
                mAdapter = new HuaweiMDMAdapter();
            } else {
               // mAdapter = new JiChengShiXunAdapter();
                mAdapter = new HuaweiMDMAdapter();
            }
        }
        return mAdapter;
    }
}
