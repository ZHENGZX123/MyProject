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
            if (Build.MODEL.equals("rk3288") || Build.MODEL.equals("rk3368-P9")) {
                Log.d("test", "JiChengShiXunAdapter");
                mAdapter = new JiChengShiXunAdapter();
            } else if (Build.MODEL.equals("D13B") || Build.MODEL.equals("G13A") || Build.MODEL.equals("hra8163_tb_m")) {
                Log.d("test", "HuaruianMDMAdapter");
                mAdapter = new HuaruianMDMAdapter();
            } else if (Build.MODEL.equals("ZTE Q5-T")) {
                mAdapter = new ZTEMDMAdapter();
            } else {
                mAdapter = new HuaweiMDMAdapter();
            }
        }
        return mAdapter;
    }
}
