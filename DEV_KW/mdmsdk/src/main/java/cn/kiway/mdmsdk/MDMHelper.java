package cn.kiway.mdmsdk;

import android.os.Build;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MDMHelper {

    private static IMDMAdapter mAdapter;

    public static IMDMAdapter getAdapter() {
        if (mAdapter == null) {
            //根据平板类型来选择用哪个Adapter
            Log.d("test", "Model = " + Build.MODEL);
            if (Build.MODEL.equals("rk3288")) {
                mAdapter = new JiChengShiXunAdapter();
            } else if (Build.MODEL.equals("D13B")) {
                mAdapter = new HuaruianMDMAdapter();
            } else {
                mAdapter = new HuaweiMDMAdapter();
            }
        }
        return mAdapter;
    }
}
