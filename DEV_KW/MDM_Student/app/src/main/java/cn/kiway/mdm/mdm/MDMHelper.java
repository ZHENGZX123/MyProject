package cn.kiway.mdm.mdm;

import android.os.Build;

import cn.kiway.mdm.adapter.HuaweiMDMAdapter;
import cn.kiway.mdm.adapter.IMDMAdapter;
import cn.kiway.mdm.adapter.JiChengShiXunAdapter;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MDMHelper {

    private static IMDMAdapter mAdapter;

    public static IMDMAdapter getAdapter() {
        if (mAdapter == null) {
            //根据平板类型来选择用哪个Adapter
            if (Build.MODEL.equals("rk3288")) {
                mAdapter = new JiChengShiXunAdapter();
            } else {
                mAdapter = new HuaweiMDMAdapter();
            }
        }
        return mAdapter;
    }
}
